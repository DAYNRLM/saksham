package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class TrainingLocationInfo {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String date;
    private String blockId;
    private String gpId;
    private String villageId;
    private String selectedShgCount;
    private String memberParticipant;
    private String otherParticipant;
    private String totalParticipant;
    private byte[] image;
    private String gpsLocation;
    private String syncStatus;
    @Generated(hash = 2107157960)
    public TrainingLocationInfo(Long id, String trainingId, String date,
            String blockId, String gpId, String villageId, String selectedShgCount,
            String memberParticipant, String otherParticipant,
            String totalParticipant, byte[] image, String gpsLocation,
            String syncStatus) {
        this.id = id;
        this.trainingId = trainingId;
        this.date = date;
        this.blockId = blockId;
        this.gpId = gpId;
        this.villageId = villageId;
        this.selectedShgCount = selectedShgCount;
        this.memberParticipant = memberParticipant;
        this.otherParticipant = otherParticipant;
        this.totalParticipant = totalParticipant;
        this.image = image;
        this.gpsLocation = gpsLocation;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 756196340)
    public TrainingLocationInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTrainingId() {
        return this.trainingId;
    }
    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBlockId() {
        return this.blockId;
    }
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
    public String getGpId() {
        return this.gpId;
    }
    public void setGpId(String gpId) {
        this.gpId = gpId;
    }
    public String getVillageId() {
        return this.villageId;
    }
    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
    public String getSelectedShgCount() {
        return this.selectedShgCount;
    }
    public void setSelectedShgCount(String selectedShgCount) {
        this.selectedShgCount = selectedShgCount;
    }
    public String getMemberParticipant() {
        return this.memberParticipant;
    }
    public void setMemberParticipant(String memberParticipant) {
        this.memberParticipant = memberParticipant;
    }
    public String getOtherParticipant() {
        return this.otherParticipant;
    }
    public void setOtherParticipant(String otherParticipant) {
        this.otherParticipant = otherParticipant;
    }
    public String getTotalParticipant() {
        return this.totalParticipant;
    }
    public void setTotalParticipant(String totalParticipant) {
        this.totalParticipant = totalParticipant;
    }
    public byte[] getImage() {
        return this.image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getGpsLocation() {
        return this.gpsLocation;
    }
    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

}
