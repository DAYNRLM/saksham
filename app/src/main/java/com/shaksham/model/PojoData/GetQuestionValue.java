package com.shaksham.model.PojoData;

public class GetQuestionValue {
    String questionId;
    String value;
    String typeId;
    String questionMainId;
    int position;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getQuestionMainId() {
        return questionMainId;
    }

    public void setQuestionMainId(String questionMainId) {
        this.questionMainId = questionMainId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
