package com.shaksham.model.PojoData;

public class AddShgPojo {
    String shgName;
    Boolean isChecked;
    String position;
    String noOfShg;
    String shgCode;

    public String getShgCode() {
        return shgCode;
    }

    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }

    public String getNoOfShg() {
        return noOfShg;
    }

    public void setNoOfShg(String noOfShg) {
        this.noOfShg = noOfShg;
    }



    private boolean isSelected;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getShgName() {
        return shgName;
    }

    public void setShgName(String shgName) {
        this.shgName = shgName;
    }



}
