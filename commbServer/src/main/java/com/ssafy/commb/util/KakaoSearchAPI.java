package com.ssafy.commb.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.KakaoSearchBookResponseDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KakaoSearchAPI {

    private static final String BOOK_SEARCH_URL = "https://dapi.kakao.com/v3/search/book";
    private static final String REST_API_KEY = "KakaoAK 0eb0afb2b825c2c3cf1db56e33c2a9d9";
    public KakaoSearchBookResponseDto search(BookDto.BookSearchRequest bookSearchRequest) throws IOException {

        // 검색 조건 : 전체, 제목, 작가, 키워드
        // 책의 수는 10개
        // 필요한 파라미터 Target, page
        // 가능한 타켓 : title, isbn, publisher, person

        StringBuilder params = new StringBuilder("?");
        params.append("query="+bookSearchRequest.getSearchWord());
        if(bookSearchRequest.getPage() != null) params.append("&page="+bookSearchRequest.getPage());
        if(bookSearchRequest.getSearchType() != null) params.append(("&target="+bookSearchRequest.getSearchType()));

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(BOOK_SEARCH_URL + params.toString());
        getRequest.addHeader("Authorization", REST_API_KEY); //KEY 입력

        HttpResponse response = httpClient.execute(getRequest);


        if (response.getStatusLine().getStatusCode() == 200) {
            ResponseHandler<String> handler = new BasicResponseHandler();

            ObjectMapper objMapper = new ObjectMapper();

            String body = handler.handleResponse(response);
            KakaoSearchBookResponseDto dto = objMapper.readValue(body,KakaoSearchBookResponseDto.class);

            return dto;
        } else {
            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
        }

        return null;
    }
}
