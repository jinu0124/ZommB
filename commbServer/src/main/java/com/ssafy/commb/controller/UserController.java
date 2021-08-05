package com.ssafy.commb.controller;

import com.ssafy.commb.common.QueryStringArgResolver;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.KeywordDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.jwt.SecurityService;
import com.ssafy.commb.service.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@Api("User Controller API V1")
public class UserController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private BookService bookService;

    @Autowired
    private KeywordService keywordService;

    @Value("${security.accesstoken}")
    private String accessToken;

    @Value("${security.refreshtoken}")
    private String refreshToken;

    // 회원관리(관리자) - (관리자)가 회원 정보 리스트 검색
    @GetMapping("")
    @ApiOperation(value = "(관리자)회원 정보 리스트 검색", response = UserDto.Response.class)
    public ResponseEntity<UserDto.ResponseList> findUserList(@RequestParam String nickname) {

        UserDto.ResponseList userResList = userService.getUsers(nickname);

        return new ResponseEntity<UserDto.ResponseList>(userResList, HttpStatus.OK);
    }

//    // 회원관리(관리자) - (관리자) 피드 삭제
//    @GetMapping("")
//    @ApiOperation(value = "(관리자)회원 정보 리스트 검색", response = UserDto.Response.class)
//    public ResponseEntity<UserDto.ResponseList> findUserList(@RequestParam String nickname) {
//
//        UserDto.ResponseList userResList = userService.getUsers(nickname);
//
//        return new ResponseEntity<UserDto.ResponseList>(userResList, HttpStatus.OK);
//    }

    // 회원가입/로그인 - 자체 회원가입
    @PostMapping("")
    @ApiOperation(value = "자체 회원가입")
    public ResponseEntity<Map<String, Integer>> singUp(@RequestBody @Valid MyDto.Request myReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(400).build();

        if (userService.isExistEmail(myReq.getEmail()))
            return ResponseEntity.status(409).build();

        Map<String, Integer> map = new HashMap<>();
        map.put("id", userService.joinUser(myReq));
        return new ResponseEntity<>(map, HttpStatus.valueOf(201));
    }

    // 회원가입/로그인 - Email 중복 확인
    @GetMapping("/email")
    @ApiOperation(value = "Email 중복 확인")
    public ResponseEntity duplicateEmail(@RequestParam String email) {
        if (userService.isExistEmail(email))
            return new ResponseEntity(HttpStatus.valueOf(400));

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 회원가입/로그인 - Email 인증
    @PostMapping("/confirm-email")
    @ApiOperation(value = "Email 인증")
    public ResponseEntity viewConfirmEmail(@RequestBody MyDto.TokenRequest myTokenReq){

        String token = userService.TokenGeneration(myTokenReq.getId(), myTokenReq.getEmail());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkEmailComplete")
    @ApiOperation(value = "Email 인증 확인")
    public void checkEmailComplete(@RequestParam String key, HttpServletResponse httpServletResponse) throws IOException {

        if(userService.confirmEmail(key)) {
//            RedirectUrlBuilder redirectUrl = new RedirectUrlBuilder();
//            redirectUrl.setContextPath("index.html");
            // Redirect를 어떻게 시키지.. 그냥 빈페이지에 인증되었다고만 적어두어도 괜찮나..??
            httpServletResponse.sendRedirect("localhost:8000/");
        }

//        return "메일 인증을 위한 토큰이 만료되었거나 유효하지 않아 인증에 실패하였습니다.";
    }

    // 회원가입/로그인 - 소셜 회원가입
    @GetMapping("/social/kakao")
    @ApiOperation(value = "소셜 회원가입", response = MyDto.Response.class)
    public ResponseEntity<MyDto.Response> kakaoLogin() {



        return new ResponseEntity<MyDto.Response>((MyDto.Response) null, HttpStatus.valueOf(201));
    }

    // 회원가입/로그인 - 자체 로그인
    @PostMapping("/login")
    @ApiOperation(value = "자체 로그인", response = MyDto.Response.class)
    public ResponseEntity<MyDto.Response> login(@RequestBody MyDto.LoginRequest myReq) {
        MyDto.Response myRes = userService.login(myReq);

        Map<String, Object> map = securityService.createToken(myRes.getData().getId());

        HttpHeaders resHeader = new HttpHeaders();
        resHeader.set(accessToken, (String) map.get("acToken"));
        resHeader.set(refreshToken, (String) map.get("rfToken"));

        return ResponseEntity.ok().headers(resHeader).body(myRes);
    }

    @GetMapping(value="/{userId}")
    @ApiOperation(value = "회원 정보 조회")
    public ResponseEntity<UserDto.Response> userInfo(@PathVariable Integer userId, HttpServletRequest request){
        UserDto.Response userRes = userService.getUserInfo(userId, request);

        return ResponseEntity.ok().body(userRes);
    }


    // 회원가입/로그인 - 비밀번호 찾기
//    @GetMapping("/{userId}")
//    @ApiOperation(value = "비밀번호 찾기 (미정)")
//    public Object findUser(@PathVariable("userId") Integer userId, HttpServletRequest request) {
//        request.setAttribute("userId", userId);                 // 테스트용
//
////        String token = userService.TokenGeneration(myTokenReq.getId(), myTokenReq.getEmail());
//
//
//        return null;
//    }

    // 회원가입/로그인 - 프로필 수정        // flag : 0:유지 , 1:수정, 2:삭제
    // @RequestBody 는 Json type으로 들어오는 객체를 파싱하는 역할 -> formData 형식에서는 사용치 않아야한다.
    @PostMapping("/{userId}")
    @ApiOperation(value="프로필 수정")
    public ResponseEntity updateUser(@PathVariable Integer userId,
                                     MyDto.ModifyRequest myReq,
                                     MultipartHttpServletRequest request) throws IOException, ServletException {
        if(myReq != null){
            if(myReq.getNickname().length() < 2 || myReq.getNickname().length() > 10) return new ResponseEntity(HttpStatus.valueOf(400));
        }
        else return ResponseEntity.status(400).build();

        MyDto.Response myRes = profileService.updateProfile(myReq, request);
        if( myRes == null ) return new ResponseEntity("알 수 없는 에러라서 백엔드에 다시 요청!", HttpStatus.valueOf(401));

        return ResponseEntity.ok().body(myRes);
    }

    // 회원가입/로그인 - 비밀번호 변경
    @PatchMapping("/{userId}")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity updateUserInfo(@PathVariable("userId") Integer userId,
                                         @RequestBody UserDto.ModifyPwRequest userReq,
                                        HttpServletRequest request) {
        if(userReq == null) return ResponseEntity.status(401).build();
        userService.validatePassword(userReq.getNewPassword());

//        request.setAttribute("userId", userId);                // 테스트용(Auto Interceptor WebConfig 적용 전)
        userService.updatePassword(userReq, request);

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 회원가입/로그인 - 회원 탈퇴
    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity deleteUser(HttpServletRequest request) {
        userService.deleteUser((int) request.getAttribute("userId"));
        System.out.println("탈퇴");
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 회원 프로필 - 1인 게시물(피드) 리스트 조회
    // 피드 게시물 리스트 조회
    @GetMapping("/{userId}/feeds")
    @ApiOperation(value = "1인 피드 리스트 조회", response = FeedDto.Response.class)
    public ResponseEntity<FeedDto.ResponseList> findUserFeed(
            @PathVariable("userId") Integer userId,
            HttpServletRequest request
    ) {
//        request.setAttribute("userId", 10000001);               // 테스트 용
        FeedDto.ResponseList feedResList = feedService.getUserFeed(userId, request);

        return new ResponseEntity<FeedDto.ResponseList>(feedResList, HttpStatus.OK);
    }

    // 회원의 게시물(피드) 수
    @GetMapping("/{userId}/feeds/cnt")
    @ApiOperation(value = "회원의 피드 수")
    public ResponseEntity<Map<String, Integer>> findUserFeedCnt(
            @PathVariable("userId") Integer userId
    ) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("cnt", feedService.getUserFeedCnt(userId));

        return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
    }

    // 서재/북카트 내 도서 검색
    @GetMapping("/{userId}/bookshelves")
    @ApiOperation(value = "북카트/서재 내 도서 검색", response = BookDto.Response.class)
    public ResponseEntity<BookDto.ResponseList> findUserBookShelvesList(
            @PathVariable("userId") Integer userId,
            @QueryStringArgResolver BookDto.BookShelfSearchRequest bookReq,
            HttpServletRequest request
    ) {
        BookDto.ResponseList bookResList = bookService.getBooksByName(bookReq, request);

        return ResponseEntity.ok().body(bookResList);
    }

    // 서재/북카트 책 추가
    @PostMapping("/{userId}/bookshelves")
    @ApiOperation(value = "북카트/서재 도서 추가")
    public ResponseEntity insertUserBookShelves(
            @PathVariable("userId") Integer userId,
            @RequestBody BookDto.RegisterRequest book,
            HttpServletRequest request
    ) {
        bookService.addMyShelf(book, request);
        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 읽은/읽을 책 수 반환
    @GetMapping("/{userId}/bookshelves/cnt")
    @ApiOperation(value = "읽은/읽을(서재/북카트) 도서 수", response = BookShelfCntDto.Response.class)
    public ResponseEntity<BookShelfCntDto.Response> findUserBookShelvesCnt(
            @PathVariable("userId") Integer userId
    ) {

        return new ResponseEntity<BookShelfCntDto.Response>(bookService.getUserReadCnt(userId), HttpStatus.OK);
    }

    // 서재 책 1권 삭제하기
    @DeleteMapping("/{userId}/bookshelves/{bookId}")
    @ApiOperation(value = "서재/북카트 도서 1권 삭제하기")
    public ResponseEntity deleteUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId,
            HttpServletRequest request
    ) {
        bookService.deleteBookInBookShelf(bookId, request);
        return ResponseEntity.status(204).build();
    }

    // 북카트에서 서재로 옮기기
    @PatchMapping("/{userId}/bookshelves/{bookId}")
    @ApiOperation(value = "북카트에서 서재로 도서 이동")
    public ResponseEntity updateUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId,
            HttpServletRequest request
    ) {
        bookService.moveBook(bookId, request);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 상단 바 도서 목록 조회
    @GetMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 목록 조회", response = BookDto.Response.class)
    public ResponseEntity<BookDto.ResponseList> findUserTopBar(
            @PathVariable("userId") Integer userId
    ) {
        BookDto.ResponseList bookResList = bookService.getTopBooks(userId);

        return new ResponseEntity<BookDto.ResponseList>(bookResList, HttpStatus.OK);
    }

    // 상단 바 도서 등록 : 서재에 있는 책인지 확인 후 등록(PRI KEY 조건으로 데이터 중복 시 throw 500)
    @PostMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 등록")
    public ResponseEntity InsertUserTopBar(
            @PathVariable("userId") Integer userId,
            @RequestBody BookDto.TopBarRegisterRequest bookReq,
            HttpServletRequest request
    ) {
        bookService.addBookTop(bookReq, request);
        return ResponseEntity.status(201).body(bookReq.getId());
    }

    // 상단 바 도서 전체 삭제
    @DeleteMapping("/{userId}/top-bar")
    @ApiOperation(value = "상단 바 도서 전체 삭제")
    public ResponseEntity deleteUserTopBarAll(
            @PathVariable("userId") Integer userId,
            HttpServletRequest request
    ) {
        bookService.deleteAllBookTop(request);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 상단바 도서 삭제
    @DeleteMapping("/{userId}/top-bar/{bookId}")
    @ApiOperation(value = "상단 바 도서 1권 삭제")
    public ResponseEntity deleteUserTopBar(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId,
            HttpServletRequest request
    ) {
        bookService.deleteBookTop(bookId, request);
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 친구 추천 목록 조회
    @GetMapping("/{userId}/follow-recommend")
    @ApiOperation(value = "친구 추천 목록 조회", response = UserDto.Response.class)
    public ResponseEntity<UserDto.ResponseList> findFollowRecommend(
            @PathVariable("userId") Integer userId,
            HttpServletRequest request
    ) {
        UserDto.ResponseList userResList = userService.followRecommend(request);

        return new ResponseEntity<UserDto.ResponseList>(userResList, HttpStatus.OK);
    }

    // 추천 키워드 목록
    @GetMapping("/{userId}/keyword-recommend")
    @ApiOperation(value = "추천 키워드 리스트", response = KeywordDto.Response.class)
    public ResponseEntity<KeywordDto.ResponseList> findKeywordRecommend(
            @PathVariable("userId") Integer userId,
            HttpServletRequest request
    ) {
        KeywordDto.ResponseList keyResList =  keywordService.keywordRecommend(request);
        return new ResponseEntity<KeywordDto.ResponseList>(keyResList, HttpStatus.OK);
    }

}
