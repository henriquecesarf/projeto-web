package com.seuprojeto.projeto_web.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {

    private String name;
    private Double fine1To4Days;
    private Double fine5To9Days;
    private Double fine10DaysOrMore;

    public CategoryRequest() {}

    public CategoryRequest(String name, Double fine1To4Days, Double fine5To9Days, Double fine10DaysOrMore) {
        this.name = name;
        this.fine1To4Days = fine1To4Days;
        this.fine5To9Days = fine5To9Days;
        this.fine10DaysOrMore = fine10DaysOrMore;
    }

}
