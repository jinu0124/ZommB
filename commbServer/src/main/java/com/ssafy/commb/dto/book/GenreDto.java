package com.ssafy.commb.dto.book;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GenreDto {
    private Map<String, String> Genre = new HashMap<>();

    public GenreDto(){
        Genre.put("00", "총류");
        Genre.put("01", "도서");
        Genre.put("02", "문헌정보");
        Genre.put("03", "백과사전");
        Genre.put("04", "수필");
        Genre.put("05", "간행");
        Genre.put("06", "학회");
        Genre.put("07", "언론");
        Genre.put("08", "총서");
        Genre.put("09", "향토자료");

        Genre.put("10", "철학");
        Genre.put("11", "형이상학");
        Genre.put("12", "");
        Genre.put("13", "철학");
        Genre.put("14", "경학");
        Genre.put("15", "아시아철학");
        Genre.put("16", "서양철학");
        Genre.put("17", "논리학");
        Genre.put("18", "심리학");
        Genre.put("19", "윤리학");

        Genre.put("20", "종교");
        Genre.put("21", "비교종교");
        Genre.put("22", "불교");
        Genre.put("23", "기독교");
        Genre.put("24", "도교");
        Genre.put("25", "천도교");
        Genre.put("26", "");
        Genre.put("27", "바라문교");
        Genre.put("28", "회교");
        Genre.put("29", "기타종교");

        Genre.put("30", "사회과학");
        Genre.put("31", "통계학");
        Genre.put("32", "경제학");
        Genre.put("33", "사회학");
        Genre.put("34", "정치학");
        Genre.put("35", "행정학");
        Genre.put("36", "법학");
        Genre.put("37", "교육학");
        Genre.put("38", "민속학");
        Genre.put("39", "국방");

        Genre.put("40", "자연과학");
        Genre.put("41", "수학");
        Genre.put("42", "물리");
        Genre.put("43", "화학");
        Genre.put("44", "천문학");
        Genre.put("45", "지학");
        Genre.put("46", "광물학");
        Genre.put("47", "생명과학");
        Genre.put("48", "식물학");
        Genre.put("49", "동물학");

        Genre.put("50", "기술과학");
        Genre.put("51", "의학");
        Genre.put("52", "수의학");
        Genre.put("53", "공학");
        Genre.put("54", "건축공학");
        Genre.put("55", "기계공학");
        Genre.put("56", "전기공학");
        Genre.put("57", "화학공학");
        Genre.put("58", "제조업");
        Genre.put("59", "생활과학");

        Genre.put("60", "예술");
        Genre.put("61", "건축술");
        Genre.put("62", "조각");
        Genre.put("63", "공예");
        Genre.put("64", "서예");
        Genre.put("65", "회화");
        Genre.put("66", "사진");
        Genre.put("67", "음악");
        Genre.put("68", "공연예술");
        Genre.put("69", "오락");

        Genre.put("70", "어학");
        Genre.put("71", "한국어");
        Genre.put("72", "중국어");
        Genre.put("73", "일본어");
        Genre.put("74", "영어");
        Genre.put("75", "독일어");
        Genre.put("76", "프랑스어");
        Genre.put("77", "스페인어");
        Genre.put("78", "이탈리아어");
        Genre.put("79", "기타언어");

        Genre.put("80", "문학");
        Genre.put("81", "한국문학");
        Genre.put("82", "중국문학");
        Genre.put("83", "일본문학");
        Genre.put("84", "영미문학");
        Genre.put("85", "독일문학");
        Genre.put("86", "프랑스문학");
        Genre.put("87", "스페인문학");
        Genre.put("88", "이탈리아문학");
        Genre.put("89", "기타언어");

        Genre.put("90", "역사, 지리, 관광");
        Genre.put("91", "아시아");
        Genre.put("92", "유럽");
        Genre.put("93", "아프리카");
        Genre.put("94", "북아메리카");
        Genre.put("95", "남아메리카");
        Genre.put("96", "오세아니아");
        Genre.put("97", "양극지방");
        Genre.put("98", "지리");
        Genre.put("99", "족보");


    }

}
