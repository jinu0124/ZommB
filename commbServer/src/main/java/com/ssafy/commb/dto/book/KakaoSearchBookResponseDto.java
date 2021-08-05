package com.ssafy.commb.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.ssafy.commb.model.Book;
import com.ssafy.commb.util.CustomOffsetDateTimeDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class KakaoSearchBookResponseDto {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private List<Document> documents;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @ToString
    public static class Meta{

        @JsonProperty("is_end")
        private Boolean isEnd;

        @JsonProperty("pageable_count")
        private Integer pagealbeCount;

        @JsonProperty("total_count")
        private Integer totalCount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @ToString
    public static class Document{

        private List<String> authors;

        private String contents;

        @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXX")
        @JsonDeserialize(using = CustomOffsetDateTimeDeserializer.class)
        private OffsetDateTime datetime;

        private String isbn;

        private Integer price;

        private String publisher;

        @JsonProperty("sale_price")
        private Integer salePrice;

        private String status;

        private String thumbnail;

        private String title;

        private List<String> translators;

        private String url;

        public Book convertBook(){



            return Book.builder()
                    .bookName(this.title)
                    .author(
                            this.authors.stream()
                            .collect(Collectors.joining(","))
                    )
                    .isbn(getIsbn13())
                    .publisher(this.publisher)
                    .description(this.contents)
                    .year(this.datetime.getYear())
                    .fileUrl(this.thumbnail)
                    .build();
        }
        // 10자리 13자리 ISBN 중 13자리를 반환한다.
        public String getIsbn13(){
            StringTokenizer st = new StringTokenizer(this.isbn);

            String isbnFirst = st.nextToken();
            String isbnSecond = null;
            String target = isbnFirst;
            try{
                isbnSecond = st.nextToken();
                if(isbnSecond != null && isbnSecond.length() == 13)
                    target = isbnSecond;
            } catch(NoSuchElementException e){

            }
            return target;
        }
    }
}
