package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddedTrainingShgMemberData {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String shgCode;
    private String shgMemberCode;
    @Generated(hash = 944245838)
    public AddedTrainingShgMemberData(Long id, String trainingId, String shgCode,
            String shgMemberCode) {
        this.id = id;
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
    }
    @Generated(hash = 520833812)
    public AddedTrainingShgMemberData() {
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
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getShgMemberCode() {
        return this.shgMemberCode;
    }
    public void setShgMemberCode(String shgMemberCode) {
        this.shgMemberCode = shgMemberCode;
    }
    


}
