package com.shaksham.model.PojoData;

import java.util.ArrayList;
import java.util.List;

public class GetTextValue {
    String titleid;
    List<GetQuestionValue> getQuestionValues;



    public String getTitleid() {
        return titleid;
    }

    public void setTitleid(String titleid) {
        this.titleid = titleid;
    }

    public List<GetQuestionValue> getGetQuestionValues() {
        return getQuestionValues;
    }

    public void setGetQuestionValues(List<GetQuestionValue> getQuestionValues) {
        this.getQuestionValues = getQuestionValues;
    }
}
