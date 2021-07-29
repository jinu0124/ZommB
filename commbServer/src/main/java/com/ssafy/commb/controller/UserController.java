package com.ssafy.commb.controller;

import com.ssafy.commb.common.QueryStringArgResolver;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.KeywordDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;
import com.ssafy.commb.dto.feed.CommentDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.HashTagDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.dto.user.follow.FollowDto;
import com.ssafy.commb.dto.user.level.LevelDto;
import com.ssafy.commb.jwt.SecurityService;
import com.ssafy.commb.service.ProfileService;
import com.ssafy.commb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


//@CrossOrigin(
//		origins = "http://localhost:3000", // allowCredentials = "true" 일 경우, orogins="*" 는 X
//		allowCredentials = "true",
//		allowedHeaders = "*",
//		methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
//)
@RestController
@RequestMapping(value = "/users")
@Api("User Controller API V1")
public class UserController {


    // Dummy Data Set----------------------------------------------------------------------
    static final int id = 1;
    static final String nickname = "크루엘라";
    static final String url = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F484319%3Ftimestamp%3D20201124225204";
    static final String tag = "#오늘도 힘내세요";
    static final String content = "이것은 글입니다.";
    static final int cnt = 134;
    static Date date;
    static final boolean bool = true;
    static final String name = "트럼프";
    static final String genre = "공포";
    static final int year = 2021;
    static final float rate = 3.5f;
    static final String keyword = "키워드입니다";
    static final int bookmark = 4;
    static final int pencil = 2;

    static {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse("2021-07-31 10:12:15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // Dummy Data Set----------------------------------------------------------------------

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;


    @Value("${security.accesstoken}")
    private String accessToken;

    @Value("${security.refreshtoken}")
    private String refreshToken;


    // 회원관리(관리자) - (관리자)가 회원 정보 리스트 검색
    @GetMapping("")
    @ApiOperation(value = "(관리자)회원 정보 리스트 검색", response = UserDto.Response.class)
    public ResponseEntity<List<UserDto.Response>> findUserList(@RequestParam String nickname) {

        LevelDto level = LevelDto.builder().bookmark(bookmark).pencil(pencil).bookmarkOn(bool).pencilOn(bool).build();
        UserDto user = UserDto.builder().id(id).email("email").name(name).nickname(nickname).role("role").level(level).userFileUrl(url).build();

        UserDto.Response userRes = new UserDto.Response();
        userRes.setData(user);

        List<UserDto.Response> users = new ArrayList<>();
        users.add(userRes);

        return new ResponseEntity<List<UserDto.Response>>(users, HttpStatus.OK);
    }

    // 회원가입/로그인 - 자체 회원가입
    @PostMapping("")
    @ApiOperation(value = "자체 회원가입")
    public ResponseEntity singUp(@RequestBody @Valid MyDto.Request myReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.valueOf(400));

        if (userService.isExistEmail(myReq.getEmail()))
            return new ResponseEntity(HttpStatus.valueOf(409));

        userService.joinUser(myReq);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 회원가입/로그인 - Email 중복 확인
    @GetMapping("/email")
    @ApiOperation(value = "Email 중복 확인")
    public ResponseEntity duplicateEmail(@RequestParam String email) {
        if (userService.isExistEmail(email))
            return new ResponseEntity(HttpStatus.valueOf(404));

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 회원가입/로그인 - Email 인증
    @PostMapping("/confirm-email")
    @ApiOperation(value = "Email 인증")
    public String viewConfirmEmail(@RequestBody MyDto.TokenRequest myTokenReq){

        String token = userService.TokenGeneration(myTokenReq.getId(), myTokenReq.getEmail());
        userService.confirmEmail(token);

        return "redirect:/login";
    }

    // 회원가입/로그인 - 소셜 회원가입
    @GetMapping("/social/kakao")
    @ApiOperation(value = "소셜 회원가입", response = MyDto.Response.class)
    public ResponseEntity<MyDto.Response> kakaoLogin() {

        MyDto my = MyDto.builder().id(1).nickname(nickname).userFileUrl(url).build();
        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);

        return new ResponseEntity<MyDto.Response>(myRes, HttpStatus.valueOf(201));
    }

    // 회원가입/로그인 - 자체 로그인
    @PostMapping("/login")
    @ApiOperation(value = "자체 로그인", response = MyDto.Response.class)
    public ResponseEntity<MyDto> login(@RequestBody MyDto.LoginRequest myReq) {
        System.out.println("login");
        MyDto my = userService.login(myReq);
        if (my == null) return new ResponseEntity(HttpStatus.valueOf(401));

        int userId = 10000001;
        System.out.println(my.getId());
        Map<String, Object> map = securityService.createToken(my.getId());

        HttpHeaders resHeader = new HttpHeaders();

        resHeader.set(accessToken, (String) map.get("acToken"));
        resHeader.set(refreshToken, (String) map.get("rfToken"));

        return ResponseEntity.ok().headers(resHeader).body(my);
    }

    // 회원가입/로그인 - 비밀번호 찾기
    @GetMapping("/{userId}")
    @ApiOperation(value = "비밀번호 찾기 (미정)")
    public Object findUser(@PathVariable("userId") Integer userId) {

        // email 인증 flow 구현 뒤에 수정해야함!!!!!

        return null;
    }

    // 회원가입/로그인 - 프로필 수정        // flag : 0:유지 , 1:수정, 2:삭제
    // @RequestBody 는 Json type으로 들어오는 객체를 파싱하는 역할 -> formData 형식에서는 사용치 않아야한다.
    @PostMapping("/{userId}")
    @ApiOperation(value="프로필 수정")
    public ResponseEntity updateUser(@PathVariable Integer userId,
                                     MyDto.ModifyRequest myReq,
                                     MultipartHttpServletRequest request) throws IOException, ServletException {
        if(myReq != null){
            if(myReq.getNickname().length() < 4 || myReq.getNickname().length() > 10) return new ResponseEntity(HttpStatus.valueOf(400));
        }
        else return new ResponseEntity(HttpStatus.valueOf(400));

        if( !profileService.updateProfile(myReq, request) ) return new ResponseEntity(HttpStatus.valueOf(401));

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 회원가입/로그인 - 비밀번호 변경
    @PatchMapping("/{userId}")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity updateUserInfo(@PathVariable("userId") Integer userId,
                                         @RequestBody UserDto.ModifyPwRequest userReq) {


        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 회원가입/로그인 - 회원 탈퇴
    @DeleteMapping("/{userId}")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity deleteUser(@PathVariable("userId") Integer userId) {

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 회원 프로필 - 1인 게시물(피드) 리스트 조회
    // 피드 게시물 리스트 조회
    @GetMapping("/{userId}/feeds")
    @ApiOperation(value = "1인 피드 리스트 조회", response = FeedDto.Response.class)
    public ResponseEntity<List<FeedDto.Response>> findUserFeed(
            @PathVariable("userId") Integer userId
    ) {
        UserDto user = UserDto.builder().id(id).nickname(nickname).userFileUrl(url).build();
        BookDto book = BookDto.builder().id(id).bookName(name).build();

        List<HashTagDto> hashTags = new ArrayList<>();
        hashTags.add(HashTagDto.builder().tag(tag).build());

        List<CommentDto> comments = new ArrayList<>();
        comments.add(CommentDto.builder().id(id).content(content).userId(id).nickname(nickname).thumbCnt(cnt).createAt(date).isThumb(bool).isMod(bool).build());

        FeedDto feed = FeedDto.builder().id(id).createAt(date).content(content).isThumb(bool).thumbCnt(cnt).feedFileUrl(url).user(user).book(book).hashTags(hashTags).comments(comments).build();

        FeedDto.Response feedRes = new FeedDto.Response();
        feedRes.setData(feed);

        List<FeedDto.Response> feedResList = new ArrayList<>();
        feedResList.add(feedRes);

        return new ResponseEntity<List<FeedDto.Response>>(feedResList, HttpStatus.OK);
    }

    // 회원의 게시물(피드) 수
    @GetMapping("/{userId}/feeds/cnt")
    @ApiOperation(value = "회원의 피드 수")
    public ResponseEntity<Map<String, Integer>> findUserFeedCnt(
            @PathVariable("userId") Integer userId
    ) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("cnt", cnt);

        return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
    }

    // 서재/북카트 내 도서 검색
    @GetMapping("/{userId}/bookshelves")
    @ApiOperation(value = "북카트/서재 내 도서 검색", response = BookDto.Response.class)
    public ResponseEntity<List<BookDto.Response>> findUserBookShelvesList(
            @PathVariable("userId") Integer userId,
            @QueryStringArgResolver BookDto.BookShelfSearchRequest bookReq
    ) {
        BookDto bookDto = BookDto.builder().id(id).bookName(name).author(name).publisher(name).year(year)
                .genre(genre).isbn("1234567891230").bookFileUrl(url).readCnt(cnt).rate(rate).build();

        BookDto.Response bookRes = new BookDto.Response();
        bookRes.setData(bookDto);

        List<BookDto.Response> bookResList = new ArrayList<>();
        bookResList.add(bookRes);

        return new ResponseEntity<List<BookDto.Response>>(bookResList, HttpStatus.OK);
    }

    // 서재/북카트 책 추가
    @PostMapping("/{userId}/bookshelves")
    @ApiOperation(value = "북카트/서재 도서 추가")
    public ResponseEntity insertUserBookShelves(
            @PathVariable("userId") Integer userId,
            @RequestBody BookDto.RegisterRequest book
    ) {

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 읽은/읽을 책 수 반환
    @GetMapping("/{userId}/bookshelves/cnt")
    @ApiOperation(value = "읽은/읽을(서재/북카트) 도서 수", response = BookShelfCntDto.Response.class)
    public ResponseEntity<BookShelfCntDto.Response> findUserBookShelvesCnt(
            @PathVariable("userId") Integer userId
    ) {
        BookShelfCntDto bookShelfCnt = BookShelfCntDto.builder().libraryCnt(cnt).bookcartCnt(cnt).build();

        BookShelfCntDto.Response bookShelfCntRes = new BookShelfCntDto.Response();
        bookShelfCntRes.setData(bookShelfCnt);

        return new ResponseEntity<BookShelfCntDto.Response>(bookShelfCntRes, HttpStatus.OK);
    }

    // 서재 책 1권 삭제하기
    @DeleteMapping("/{userId}/bookshelves/{bookId}")
    @ApiOperation(value = "서재/북카트 도서 1권 삭제하기")
    public ResponseEntity deleteUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ) {
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 북카트에서 서재로 옮기기
    @PatchMapping("/{userId}/bookshelves/{bookId}")
    @ApiOperation(value = "북카트에서 서재로 도서 이동")
    public ResponseEntity updateUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ) {


        return new ResponseEntity(HttpStatus.OK);
    }

    // /users/2/top-bar
    // 상단 바 도서 목록 조회
    @GetMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 목록 조회", response = BookDto.Response.class)
    public ResponseEntity<List<BookDto.Response>> findUserTopBar(
            @PathVariable("userId") Integer userId
    ) {
        BookDto book = BookDto.builder().id(id).bookFileUrl(url).build();

        List<BookDto.Response> bookResList = new ArrayList<>();

        BookDto.Response bookRes = new BookDto.Response();
        bookRes.setData(book);

        bookResList.add(bookRes);

        return new ResponseEntity<List<BookDto.Response>>(bookResList, HttpStatus.OK);
    }

    // 상단 바 도서 등록
    @PostMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 등록")
    public ResponseEntity InsertUserTopBar(
            @PathVariable("userId") Integer userId,
            @RequestBody BookDto.TopBarRegisterRequest bookReq
    ) {

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 상단 바 도서 전체 삭제
    @DeleteMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 전체 삭제")
    public ResponseEntity deleteUserTopBarAll(
            @PathVariable("userId") Integer userId
    ) {


        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 상단바 도서 삭제
    @DeleteMapping("/{userId}/top-bar/{bookId}")
    @ApiOperation(value = "상단 바 도서 1권 삭제")
    public ResponseEntity deleteUserTopBar(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ) {


        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // /users/2/follow-recommend
    // 친구 추천 목록 조회
    @GetMapping("/{userId}/follow-recommend")
    @ApiOperation(value = "친구 추천 목록 조회", response = UserDto.Response.class)
    public ResponseEntity<List<UserDto.Response>> findFollowRecommend(
            @PathVariable("userId") Integer userId
    ) {

        FollowDto follow = FollowDto.builder().isFollow(bool).build();
        LevelDto level = LevelDto.builder().bookmark(bookmark).pencil(pencil).build();

        UserDto user = UserDto.builder().id(id).name(name).nickname(nickname).userFileUrl(url).follow(follow).level(level).build();

        UserDto.Response userRes = new UserDto.Response();
        userRes.setData(user);

        List<UserDto.Response> userResList = new ArrayList<>();
        userResList.add(userRes);

        return new ResponseEntity<List<UserDto.Response>>(userResList, HttpStatus.OK);
    }

    // /users/1/keyword-recommend
    // 추천 키워드 목록
    @GetMapping("/{userId}/keyword-recommend")
    @ApiOperation(value = "추천 키워드 리스트", response = KeywordDto.Response.class)
    public ResponseEntity<List<KeywordDto.Response>> findKeywordRecommend(
            @PathVariable("userId") Integer userId
    ) {
        KeywordDto keywordDto = KeywordDto.builder().id(id).keyword(keyword).cnt(cnt).build();
        KeywordDto.Response keywordRes = new KeywordDto.Response();
        keywordRes.setData(keywordDto);

        List<KeywordDto.Response> keywordResList = new ArrayList<KeywordDto.Response>();
        keywordResList.add(keywordRes);

        return new ResponseEntity<List<KeywordDto.Response>>(keywordResList, HttpStatus.OK);
    }

}
