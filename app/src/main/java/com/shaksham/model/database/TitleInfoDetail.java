package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class TitleInfoDetail {
    @Id(autoincrement = true)
    private Long autogenratedTitleId;

    private Long titleId;
    private String titleName;
    private String languageId;

    @ToMany(referencedJoinProperty = "titleId")
    private List<QuestionInfoDetail> questionDataList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 149869849)
    private transient TitleInfoDetailDao myDao;

    @Generated(hash = 689894148)
    public TitleInfoDetail(Long autogenratedTitleId, Long titleId, String titleName,
            String languageId) {
        this.autogenratedTitleId = autogenratedTitleId;
        this.titleId = titleId;
        this.titleName = titleName;
        this.languageId = languageId;
    }

    @Generated(hash = 1834179536)
    public TitleInfoDetail() {
    }

    public Long getAutogenratedTitleId() {
        return this.autogenratedTitleId;
    }

    public void setAutogenratedTitleId(Long autogenratedTitleId) {
        this.autogenratedTitleId = autogenratedTitleId;
    }

    public Long getTitleId() {
        return this.titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return this.titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 84332076)
    public List<QuestionInfoDetail> getQuestionDataList() {
        if (questionDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            QuestionInfoDetailDao targetDao = daoSession.getQuestionInfoDetailDao();
            List<QuestionInfoDetail> questionDataListNew = targetDao
                    ._queryTitleInfoDetail_QuestionDataList(autogenratedTitleId);
            synchronized (this) {
                if (questionDataList == null) {
                    questionDataList = questionDataListNew;
                }
            }
        }
        return questionDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 126644784)
    public synchronized void resetQuestionDataList() {
        questionDataList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 18871612)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTitleInfoDetailDao() : null;
    }

    public String getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }


}
