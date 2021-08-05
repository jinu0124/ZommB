package com.ssafy.commb.dto.book;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GenreDto {
    private Map<String, String> Genre = new HashMap<>();

    public GenreDto(){
        Genre.put("000", "총류");
        Genre.put("010", "도서");
        Genre.put("020", "문헌정보");
        Genre.put("030", "백과사전");
        Genre.put("040", "수필");
        Genre.put("050", "간행");
        Genre.put("060", "학회");
        Genre.put("070", "언론");
        Genre.put("080", "총서");
        Genre.put("090", "향토자료");

        Genre.put("100", "철학");
        Genre.put("110", "형이상학");
        Genre.put("120", "");
        Genre.put("130", "철학");
        Genre.put("140", "경학");
        Genre.put("150", "아시아철학");
        Genre.put("160", "서양철학");
        Genre.put("170", "논리학");
        Genre.put("180", "심리학");
        Genre.put("190", "윤리학");

        Genre.put("200", "종교");
        Genre.put("210", "비교종교");
        Genre.put("220", "불교");
        Genre.put("230", "기독교");
        Genre.put("240", "도교");
        Genre.put("250", "천도교");
        Genre.put("260", "");
        Genre.put("270", "바라문교");
        Genre.put("280", "회교");
        Genre.put("290", "기타종교");

        Genre.put("300", "사회과학");
        Genre.put("310", "통계학");
        Genre.put("320", "경제학");
        Genre.put("330", "사회학");
        Genre.put("340", "정치학");
        Genre.put("350", "행정학");
        Genre.put("360", "법학");
        Genre.put("370", "교육학");
        Genre.put("380", "민속학");
        Genre.put("390", "국방");

        Genre.put("400", "자연과학");
        Genre.put("410", "수학");
        Genre.put("420", "물리");
        Genre.put("430", "화학");
        Genre.put("440", "천문학");
        Genre.put("450", "지학");
        Genre.put("460", "광물학");
        Genre.put("470", "생명과학");
        Genre.put("480", "식물학");
        Genre.put("490", "동물학");

        Genre.put("500", "기술과학");
        Genre.put("510", "의학");
        Genre.put("520", "수의학");
        Genre.put("530", "공학");
        Genre.put("540", "건축공학");
        Genre.put("550", "기계공학");
        Genre.put("560", "전기공학");
        Genre.put("570", "화학공학");
        Genre.put("580", "제조업");
        Genre.put("590", "생활과학");

        Genre.put("600", "예술");
        Genre.put("610", "건축술");
        Genre.put("620", "조각");
        Genre.put("630", "공예");
        Genre.put("640", "서예");
        Genre.put("650", "회화");
        Genre.put("660", "사진");
        Genre.put("670", "음악");
        Genre.put("680", "공연예술");
        Genre.put("690", "오락");

        Genre.put("700", "어학");
        Genre.put("710", "한국어");
        Genre.put("720", "중국어");
        Genre.put("730", "일본어");
        Genre.put("740", "영어");
        Genre.put("750", "독일어");
        Genre.put("760", "프랑스어");
        Genre.put("770", "스페인어");
        Genre.put("780", "이탈리아어");
        Genre.put("790", "기타언어");

        Genre.put("800", "문학");
        Genre.put("810", "한국문학");
        Genre.put("820", "중국문학");
        Genre.put("830", "일본문학");
        Genre.put("840", "영미문학");
        Genre.put("850", "독일문학");
        Genre.put("860", "프랑스문학");
        Genre.put("870", "스페인문학");
        Genre.put("880", "이탈리아문학");
        Genre.put("890", "기타언어");

        Genre.put("900", "역사, 지리, 관광");
        Genre.put("910", "아시아");
        Genre.put("920", "유럽");
        Genre.put("930", "아프리카");
        Genre.put("940", "북아메리카");
        Genre.put("950", "남아메리카");
        Genre.put("960", "오세아니아");
        Genre.put("970", "양극지방");
        Genre.put("980", "지리");
        Genre.put("990", "족보");


    }

}
