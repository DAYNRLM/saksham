package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ViewReportTrainingData {

    @Id(autoincrement = true)
    private Long id = null;
    String monthCode;
    String year;
    String blockCode;
    String villageCode;
    String gpCode;
    String otherParticipant;
    String memberParticipant;
    String trainingCode;
    String dateOfTraining;
    String shgParticipant;
    @Generated(hash = 183050866)
    public ViewReportTrainingData(Long id, String monthCode, String year,
            String blockCode, String villageCode, String gpCode,
            String otherParticipant, String memberParticipant, String trainingCode,
            String dateOfTraining, String shgParticipant) {
        this.id = id;
        this.monthCode = monthCode;
        this.year = year;
        this.blockCode = blockCode;
        this.villageCode = villageCode;
        this.gpCode = gpCode;
        this.otherParticipant = otherParticipant;
        this.memberParticipant = memberParticipant;
        this.trainingCode = trainingCode;
        this.dateOfTraining = dateOfTraining;
        this.shgParticipant = shgParticipant;
    }
    @Generated(hash = 566690064)
    public ViewReportTrainingData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMonthCode() {
        return this.monthCode;
    }
    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getBlockCode() {
        return this.blockCode;
    }
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getGpCode() {
        return this.gpCode;
    }
    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }
    public String getOtherParticipant() {
        return this.otherParticipant;
    }
    public void setOtherParticipant(String otherParticipant) {
        this.otherParticipant = otherParticipant;
    }
    public String getMemberParticipant() {
        return this.memberParticipant;
    }
    public void setMemberParticipant(String memberParticipant) {
        this.memberParticipant = memberParticipant;
    }
    public String getTrainingCode() {
        return this.trainingCode;
    }
    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }
    public String getDateOfTraining() {
        return this.dateOfTraining;
    }
    public void setDateOfTraining(String dateOfTraining) {
        this.dateOfTraining = dateOfTraining;
    }
    public String getShgParticipant() {
        return this.shgParticipant;
    }
    public void setShgParticipant(String shgParticipant) {
        this.shgParticipant = shgParticipant;
    }


}
