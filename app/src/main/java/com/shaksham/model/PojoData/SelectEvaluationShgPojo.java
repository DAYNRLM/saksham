package com.shaksham.model.PojoData;

public class SelectEvaluationShgPojo {
    String nameOfShgs;
    String trainingModules;
    String dateOfTraining;
    String numberOfShg;

    public String getNumberOfShg() {
        return numberOfShg;
    }

    public void setNumberOfShg(String numberOfShg) {
        this.numberOfShg = numberOfShg;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    String position;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getNameOfShgs() {
        return nameOfShgs;
    }

    public void setNameOfShgs(String nameOfShgs) {
        this.nameOfShgs = nameOfShgs;
    }

    public String getTrainingModules() {
        return trainingModules;
    }

    public void setTrainingModules(String trainingModules) {
        this.trainingModules = trainingModules;
    }

    public String getDateOfTraining() {
        return dateOfTraining;
    }

    public void setDateOfTraining(String dateOfTraining) {
        this.dateOfTraining = dateOfTraining;
    }
}
