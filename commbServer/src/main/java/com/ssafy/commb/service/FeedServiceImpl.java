package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.model.Book;
import com.ssafy.commb.model.Feed;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedRepository feedRepository;

    private static final String uploadFolder = "upload";

    static String uploadPath = "C:" + File.separator + "SSAFY"
            + File.separator + "S05P12A602"
            + File.separator + "commbServer"
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static";

    public FeedDto uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException {

        User user = new User();
        Book book = new Book();
        Feed feed = new Feed();

//        user.setId((Integer) request.getAttribute("userId"));
        book.setId(feedReq.getBookId());
        feed.setUser(user);
        feed.setBook(book);
        feed.setContent(feedReq.getContent());
//        String fileUrl = fileUpload(uploadPath, request.getParts());
//        feed.setFileUrl(fileUrl);

        feedRepository.save(feed);

        UserDto userDto = new UserDto();
//        userDto.setId((Integer) request.getAttribute("userId"));

        BookDto bookDto = new BookDto();
        bookDto.setId(feedReq.getBookId());

        FeedDto myfeed = FeedDto.builder().user(userDto).book(bookDto).content(feedReq.getContent()).build();

        System.out.print(myfeed.getId());

        return myfeed;

    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    private String fileUpload(String uploadPath, Collection<Part> parts) {
        // 파일 업로드 작업
        File uploadDir = new File(uploadPath + File.separator +  uploadFolder);   // File upload Path
        if(!uploadDir.exists()) uploadDir.mkdir();

        String savingFileName = null;
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        for(Part part : parts){
            String fileName = getFileName(part);

            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기

            UUID uuid = UUID.randomUUID();
            String extension = FilenameUtils.getExtension(fileName);
            String date = sDate.format(new Date());

            // 물리 파일 쓰기 작업
            savingFileName = uuid + date + "." + extension;
            try {
                part.write(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }
        }
        return savingFileName;
    }

}
