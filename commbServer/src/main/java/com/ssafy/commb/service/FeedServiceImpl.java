package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.*;
import com.ssafy.commb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ssafy.commb.dao.FeedDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private DailyEventRepository dailyEventRepository;

    @Autowired
    private WeeklyEventRepository weeklyEventRepository;

    @Autowired
    private FeedDao feedDao;

    @Autowired
    private S3Service S3service;

    @Autowired
    private FollowService followService;

    @PersistenceContext
    private EntityManager entityManager;

    public void uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException {

        User user = new User();
        Book book = new Book();
        Feed feed = new Feed();

        int userId = (Integer) request.getAttribute("userId");
        user.setId(userId);
        book.setId(feedReq.getBookId());
        feed.setUser(user);
        feed.setBook(book);
        feed.setCreateAt(new Date());
        feed.setContent(feedReq.getContents());

        Part part = S3service.extractFile(request.getParts()); // 파일 하나 받아옴
        String fileUrl = S3service.uploadS3(part, "feed");
        feed.setFileUrl(fileUrl);

        feedRepository.save(feed);


        // hashTag 추출 후 DB에 저장 & DailyEvent 참여 피드인지 확인
        List<String> Tags = extractHashTag(feedReq.getContents());

        Date[] dates = transformDay(feed.getCreateAt());
        List<DailyEvent> dailyEvent = dailyEventRepository.findAllByCreateAtBetween(dates[0], dates[1]);
        if (dailyEvent.size() == 0) System.err.println("DailyEvent가 없습니다!");
        if (dailyEvent.size() > 1) System.err.println("DailyEvent가 2개 이상 등록되어 있습니다!");

        String keyword = dailyEvent.get(0).getKeyword().getKeyword();
        Boolean checkDailyEvent = false;

        for (int i = 0; i < Tags.size(); i++) {
            HashTag hashTag = new HashTag();

            hashTag.setTag(Tags.get(i));
            hashTag.setFeed(feed);
            if (keyword.equals(Tags.get(i))) checkDailyEvent = true;

            hashTagRepository.save(hashTag);
        }


        // DailyEvent 참여 피드가 아닌경우 -> 처리 X
        // DailyEvent 참여 피드인 경우, 피드 생성 날짜 기준으로 그 날 작성한 모든 피드를 가져와서 해시태그랑 키워드를 비교해서 1개면(등록한 해당 피드) pencil +1
        int dailyEventFeedCnt = 0;
        if (checkDailyEvent) {
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(userId, dates[0], dates[1]);

            all:
            for (Feed f : feeds) {
                List<HashTag> hashTags = hashTagRepository.findByFeedId(f.getId());

                for (HashTag hashTag : hashTags) {
                    if (keyword.equals(hashTag.getTag())) {
                        dailyEventFeedCnt++;
                        if (dailyEventFeedCnt > 1) break all;
                    }
                }
            }

            if (dailyEventFeedCnt == 1) {
                User u = userRepository.findById(feed.getUser().getId()).get();
//                System.out.println(u.getPencil());
                u.setPencil(u.getPencil() + 1);
                userRepository.save(u);
            }
        }


        // WeeklyEvent 참여 피드가 아닌경우 -> 처리 X
        // WeeklyEvent 참여 피드인 경우, 피드 생성 날짜 기준으로 그 주 작성한 모든 피드를 가져와서 북 아이디랑 비교해서 1개면(등록한 해당 피드) bookmark +1
        dates = transformWeek(feed.getCreateAt());
        Optional<WeeklyEvent> weeklyEvent = weeklyEventRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(dates[0], dates[1]);
        if(!weeklyEvent.isPresent()) System.err.println("WeeklyEvent가 없습니다!");
        int eventBookId = weeklyEvent.get().getBook().getId();

        if (eventBookId == feed.getBook().getId()) {
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(userId, weeklyEvent.get().getStartDate(), weeklyEvent.get().getEndDate());
            int weeklyEventFeedCnt = 0;
            for (Feed f : feeds) {
                if (eventBookId == f.getBook().getId()) {
                    weeklyEventFeedCnt++;
                    if (weeklyEventFeedCnt > 1) break;
                }
            }

            if (weeklyEventFeedCnt == 1) {
                User u = userRepository.findById(feed.getUser().getId()).get();
                u.setBookmark(u.getBookmark() + 1);
                userRepository.save(u);
            }
        }
    }

    /**
     * 특정 유저의 피드 리스트 가져오기
     * @param userId : target 유저 ID
     * @param request : 자신 유저 ID
     * @return : 피드 리스트
     */
    @Override
    public FeedDto.ResponseList getUserFeed(int userId, int page, HttpServletRequest request) {

        List<FeedDto> feeds = feedDao.userFeed(userId, page, (Integer) request.getAttribute("userId"));

        if (feeds.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), (Integer) request.getAttribute("userId")));
        }

        return FeedDto.ResponseList.builder()
                .data(feeds)
                .build();
    }

    /**
     * 특정 유저의 피드 개수
     * @param userId : 유저 ID
     * @return : 개수 반환
     */
    @Override
    public int getUserFeedCnt(int userId) {
        return feedDao.userFeedCnt(userId);
    }

    public void modifyFeed(String content, int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "변경할 피드가 없습니다.");


        Date[] dates = transformDay(feed.get().getCreateAt());
        List<DailyEvent> dailyEvent = dailyEventRepository.findAllByCreateAtBetween(dates[0], dates[1]);
        if (dailyEvent.size() == 0) System.err.println("DailyEvent가 없습니다!");
        if (dailyEvent.size() > 1) System.err.println("DailyEvent가 2개 이상 등록되어 있습니다!");

        String keyword = dailyEvent.get(0).getKeyword().getKeyword();
        Boolean checkDailyEventBefore = false;

        // 변경 전 content로 등록한 hashTag 삭제 & DailyEvent 참여 피드인지 확인
        List<HashTag> hashTags = hashTagRepository.findByFeedId(feedId);
        for (HashTag hashTag : hashTags) {
            if(keyword.equals(hashTag.getTag())) checkDailyEventBefore = true;

            hashTagRepository.delete(hashTag);
        }

        // 변경된 content로 feed 업데이트 & DailyEvent 참여 피드인지 확인
        Boolean checkDailyEventAfter = false;
        feed.get().setContent(content);
        feedRepository.save(feed.get());

        List<String> Tags = extractHashTag(content);

        for (int i = 0; i < Tags.size(); i++) {
            HashTag hashTag = new HashTag();

            hashTag.setTag(Tags.get(i));
            hashTag.setFeed(feed.get());
            if (keyword.equals(Tags.get(i))) checkDailyEventAfter = true;

            hashTagRepository.save(hashTag);
        }

        // 이전 피드가 DailyEvent 참여 피드 X, 지금 피드도 DailyEvent 참여 피드 X -> 처리 안함
        // 이전 피드가 DailyEvent 참여 피드 X, 지금 피드 DailyEvent 참여 피드 O -> 오늘 날짜로 피드를 가져와서 해시태그랑 키워드를 비교해서 1개면(변경한 해당 피드) pencil +1
        User user = feed.get().getUser();
        int userId = user.getId();
        if(!checkDailyEventBefore && checkDailyEventAfter){
            int dailyEventFeedCnt = 0;
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(userId, dates[0], dates[1]);

            all:
            for (Feed f : feeds) {
                hashTags = hashTagRepository.findByFeedId(f.getId());

                for (HashTag hashTag : hashTags) {
                    if (keyword.equals(hashTag.getTag())) {
                        dailyEventFeedCnt++;
                        if (dailyEventFeedCnt > 1) break all;
                    }
                }
            }

            if (dailyEventFeedCnt == 1) {
                user.setPencil(user.getPencil() + 1);
                userRepository.save(user);
            }
        }
        // 이전 피드가 DailyEvent 참여 피드 O, 지금 피드가 DailyEvent 참여 피드 O -> 처리 안함
        // 이전 피드가 DailyEvent 참여 피드 O, 지금 피드가 DailyEvent 참여 피드 X -> 오늘 날짜로 피드를 가져와서 해시태그랑 키워드를 비교해서 0개면(변경한 해당 피드) pencil -1
        if(checkDailyEventBefore && !checkDailyEventAfter){
            int dailyEventFeedCnt = 0;
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(userId, dates[0], dates[1]);

            all:
            for (Feed f : feeds) {
                hashTags = hashTagRepository.findByFeedId(f.getId());

                for (HashTag hashTag : hashTags) {
                    if (keyword.equals(hashTag.getTag())) {
                        dailyEventFeedCnt++;
                        break all;
                    }
                }
            }

            if (dailyEventFeedCnt == 0) {
                user.setPencil(user.getPencil() - 1);
                userRepository.save(user);
            }
        }

        // bookId는 바뀌지 않아서 WeeklyEvent 참여 여부 바뀌지 않음!
    }

    public int getUserId(int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        return feed.get().getUser().getId();
    }

    public void deleteFeed(int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "삭제할 피드가 없습니다.");

        // DailyEvent 참여 피드가 아닌경우 -> 처리 X
        // DailyEvent 참여 피드인 경우, 피드 생성 날짜 기준으로 그 날 작성한 모든 피드를 가져와서 해시태그랑 키워드를 비교해서 1개면(등록한 해당 피드) pencil -1
        List<String> Tags = extractHashTag(feed.get().getContent());

        Date[] dates = transformDay(feed.get().getCreateAt());
        List<DailyEvent> dailyEvent = dailyEventRepository.findAllByCreateAtBetween(dates[0], dates[1]);
        if (dailyEvent.size() == 0) System.err.println("DailyEvent가 없습니다!");
        if (dailyEvent.size() > 1) System.err.println("DailyEvent가 2개 이상 등록되어 있습니다!");

        String keyword = dailyEvent.get(0).getKeyword().getKeyword();
        Boolean checkDailyEvent = false;
        for (int i = 0; i < Tags.size(); i++) {
            if (keyword.equals(Tags.get(i))) checkDailyEvent = true;
        }

        User user = feed.get().getUser();
        int dailyEventFeedCnt = 0;
        if (checkDailyEvent) {
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(user.getId(), dates[0], dates[1]);

            all:
            for (Feed f : feeds) {
                List<HashTag> hashTags = hashTagRepository.findByFeedId(f.getId());

                for (HashTag hashTag : hashTags) {
                    if (keyword.equals(hashTag.getTag())) {
                        dailyEventFeedCnt++;
                        if (dailyEventFeedCnt > 1) break all;
                    }
                }
            }

            if (dailyEventFeedCnt == 1) {
                user.setPencil(user.getPencil() - 1);
                userRepository.save(user);
            }
        }

        // WeeklyEvent 참여 피드가 아닌경우 -> 처리 X
        // WeeklyEvent 참여 피드인 경우, 피드 생성 날짜 기준으로 그 주 작성한 모든 피드를 가져와서 북 아이디랑 비교해서 1개면(등록한 해당 피드) bookmark +1
        dates = transformWeek(feed.get().getCreateAt());
        Optional<WeeklyEvent> weeklyEvent = weeklyEventRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(dates[0], dates[1]);
        if(!weeklyEvent.isPresent()) System.err.println("WeeklyEvent가 없습니다!");
        int eventBookId = weeklyEvent.get().getBook().getId();

        if (eventBookId == feed.get().getBook().getId()) {
            List<Feed> feeds = feedRepository.findByUserIdAndCreateAtBetween(user.getId(), weeklyEvent.get().getStartDate(), weeklyEvent.get().getEndDate());
            int weeklyEventFeedCnt = 0;
            for (Feed f : feeds) {
                if (eventBookId == f.getBook().getId()) {
                    weeklyEventFeedCnt++;
                    if (weeklyEventFeedCnt > 1) break;
                }
            }

            if (weeklyEventFeedCnt == 1) {
                user.setBookmark(user.getBookmark() - 1);
                userRepository.save(user);
            }
        }


        // 피드 삭제
        String url = feed.get().getFileUrl();
        S3service.deleteS3(url, "feed");

        feedRepository.deleteById(feedId);
    }

    public void reportFeed(int feedId, String reason, int userId) {
        Report report = new Report();

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "신고할 피드가 없습니다.");

        report.setFeed(feed.get());
        report.setUser(user.get());
        report.setReason(reason);

        reportRepository.save(report);

        Optional<List<Feed>> feedReported = reportRepository.findByFeedId(feedId);              // 신고 4회부터는 블럭처리
        feedReported.ifPresent(select -> {
            if(select.size() > 3) {
                feed.get().setBlocked(1);
                feedRepository.save(feed.get());
            }
        });
    }

    public FeedDto.ResponseList getFollowingFeeds(int page, int userId) {

        List<FeedDto> feeds = feedDao.getFollowingFeeds(page, userId);

        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), userId));
        }

        return FeedDto.ResponseList.builder()
                .data(feeds)
                .build();
    }

    public MyDto.ResponseList likeFeeds(int feedId, int page, int userId) {

        Optional<Feed> feed = feedRepository.findById(feedId);

        // 피드에 좋아요를 누른 유저 목록
        List<Thumb> thumbsUser = feed.get().getThumbsUser();

        List<User> users = new ArrayList<>();
        for (Thumb thumb : thumbsUser) {
            users.add(thumb.getUser());
        }

        List<MyDto> myDtoList = users
                .stream()
                .map(user -> {
                    User u = userRepository.findUserById(user.getId()).get();

                    return MyDto.builder()
                            .id(u.getId())
                            .nickname(u.getNickname())
                            .userFileUrl(u.getFileUrl())
                            .isFollow(followService.isFollow(userRepository.findUserById(userId).get(), u))
                            .build();
                }).sorted(Comparator.comparing(MyDto::getNickname)).sorted(Comparator.comparing(MyDto::getIsFollow).reversed())
                .skip(page * 50)
                .limit(50)
                .collect(Collectors.toList());

        MyDto.ResponseList myResList = MyDto.ResponseList.builder().data(myDtoList).build();

        if(myResList.getData().size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return myResList;
    }

    /**
     * 해시태그 피드 검색(FTS), 유사도 기준 정렬
     * @param searchWord : 검색 단어(들), spacebar 기준 검색어 파싱
     * @param userId : 유저 ID
     * @return : 검색 피드들 반환
     */
    public FeedDto.ResponseList getFeeds(String searchWord, int page, int userId) {
        StringBuilder dynamicQuery = new StringBuilder();
        String[] words = searchWord.split(" ");

        int countOfWords = words.length;
        if (countOfWords > 0) for (String word : words) dynamicQuery.append("\"").append(word).append("\" ");
        else dynamicQuery.append("\"\"");

        Map<String, Object> map = new HashMap<>();
        map.put("dynamicQuery", dynamicQuery.toString());
        map.put("userId", userId);
        map.put("countOfWords", countOfWords);
        map.put("page", page);
        List<FeedDto> feeds = feedDao.getFeeds(map);

        if (feeds.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), userId));
        }

        FeedDto.ResponseList feedResList = new FeedDto.ResponseList();
        feedResList.setData(feeds);

        return FeedDto.ResponseList.builder()
                .data(feeds)
                .build();
    }

    /**
     * FeedId를 토대로 기본적인 알림을 위한 피드 정보 반환, 피드가 존재하지 않을때는 null return
     * @param feedId : 피드 ID
     * @return : 피드 정보
     */
    @Override
    public FeedDto getFeedInfo(int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);

        AtomicReference<FeedDto> feedDto = new AtomicReference<>();

        feed.ifPresent(select -> {
            feedDto.set(FeedDto.builder()
                    .book(BookDto.builder()
                            .id(select.getBook().getId()).build())
                    .feedFileUrl(select.getFileUrl())
                    .id(select.getId())
                    .content(select.getContent())
                    .build());
        });

        return feedDto.get();
    }

    public List<String> extractHashTag(String content) {

        Pattern pattern = Pattern.compile("\\#([0-9a-zA-Z가-힣_]*)"); // 주어진 정규표현식으로부터 패턴을 만든다.
        Matcher matcher = pattern.matcher(content); // 대상 문자열이 패턴과 일치할 경우 true 반환
        String extractHashTag = null;
        List<String> hashTags = new ArrayList<>();

        while (matcher.find()) { // 대상 문자열이 패턴과 일치하는 경우 true 반환 후 그 위치로 이동
            extractHashTag = specialCharacter_replace(matcher.group()); // 매칭된 부분을 반환

            if (extractHashTag == null) break;
            if (!hashTags.contains(extractHashTag)) hashTags.add(extractHashTag);
        }

        return hashTags;
    }

    public String specialCharacter_replace(String hashTag) {
        hashTag = StringUtils.replace(hashTag, "#", "");

        if (hashTag.length() < 1) return null;

        return hashTag;
    }

    public Date[] transformDay(Date date) {
        // Date -> LocalDate
        LocalDate feedCreate = date.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDate();

        // feed createAt 기준 0시 0분 0초 ~ 23시 59분 59초
        LocalDateTime startDateTime = LocalDateTime.of(feedCreate, LocalTime.of(0, 0, 0));
        LocalDateTime endDateTime = LocalDateTime.of(feedCreate, LocalTime.of(23, 59, 59));

        // LocalDate -> Date
        Date startDate = java.sql.Timestamp.valueOf(startDateTime);
        Date endDate = java.sql.Timestamp.valueOf(endDateTime);

        Date[] dates = new Date[2];
        dates[0] = startDate;
        dates[1] = endDate;

        return dates;
    }

    public Date[] transformWeek(Date date) {
        // Date -> LocalDate
        LocalDate feedCreate = date.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDate();

        int year = feedCreate.getYear();
        int month = feedCreate.getMonthValue();
        int day = feedCreate.getDayOfMonth();
        int startDay, endDay;
        LocalDate start, end;
        LocalDateTime startDateTime, endDateTime;

        if (1 <= day && day <= 7) {
            startDay = 1;
            endDay = 7;
            start = LocalDate.of(year, month, startDay);
            end = LocalDate.of(year, month, endDay);
        } else if (8 <= day && day <= 14) {
            startDay = 8;
            endDay = 14;
            start = LocalDate.of(year, month, startDay);
            end = LocalDate.of(year, month, endDay);
        } else if (15 <= day && day <= 21) {
            startDay = 15;
            endDay = 21;
            start = LocalDate.of(year, month, startDay);
            end = LocalDate.of(year, month, endDay);
        } else {
            startDay = 22;
            endDay = 31;
            if (month == 2) endDay = 28; // 윤달도 고려...?
            else if (month == 4 || month == 6 || month == 9 || month == 11) endDay = 30;
            start = LocalDate.of(year, month, startDay);
            end = LocalDate.of(year, month, endDay);
        }
        startDateTime = LocalDateTime.of(start, LocalTime.of(0, 0, 0));
        endDateTime = LocalDateTime.of(end, LocalTime.of(23, 59, 59));

        // LocalDate -> Date
        Date startDate = java.sql.Timestamp.valueOf(startDateTime);
        Date endDate = java.sql.Timestamp.valueOf(endDateTime);

        Date[] dates = new Date[2];
        dates[0] = startDate;
        dates[1] = endDate;

        return dates;
    }

}
