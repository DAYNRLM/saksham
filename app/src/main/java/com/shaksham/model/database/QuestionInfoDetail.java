package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class QuestionInfoDetail {
    @Id(autoincrement = true)
    private Long autogenratedQuestionId;

    private Long questionId;
    private String questionMainId;
    private String questionTypeId;
    private String questionName;
    private Long titleId;
    private String languageId;

    @ToOne(joinProperty = "titleId")
    private TitleInfoDetail titles;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1470546325)
    private transient QuestionInfoDetailDao myDao;

    @Generated(hash = 1120215222)
    public QuestionInfoDetail(Long autogenratedQuestionId, Long questionId,
            String questionMainId, String questionTypeId, String questionName, Long titleId,
            String languageId) {
        this.autogenratedQuestionId = autogenratedQuestionId;
        this.questionId = questionId;
        this.questionMainId = questionMainId;
        this.questionTypeId = questionTypeId;
        this.questionName = questionName;
        this.titleId = titleId;
        this.languageId = languageId;
    }

    @Generated(hash = 2086607409)
    public QuestionInfoDetail() {
    }

    public Long getAutogenratedQuestionId() {
        return this.autogenratedQuestionId;
    }

    public void setAutogenratedQuestionId(Long autogenratedQuestionId) {
        this.autogenratedQuestionId = autogenratedQuestionId;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return this.questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Long getTitleId() {
        return this.titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    @Generated(hash = 1821498360)
    private transient Long titles__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1895162123)
    public TitleInfoDetail getTitles() {
        Long __key = this.titleId;
        if (titles__resolvedKey == null || !titles__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TitleInfoDetailDao targetDao = daoSession.getTitleInfoDetailDao();
            TitleInfoDetail titlesNew = targetDao.load(__key);
            synchronized (this) {
                titles = titlesNew;
                titles__resolvedKey = __key;
            }
        }
        return titles;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1655504767)
    public void setTitles(TitleInfoDetail titles) {
        synchronized (this) {
            this.titles = titles;
            titleId = titles == null ? null : titles.getAutogenratedTitleId();
            titles__resolvedKey = titleId;
        }
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
    @Generated(hash = 1790847711)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQuestionInfoDetailDao() : null;
    }

    public String getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getQuestionMainId() {
        return this.questionMainId;
    }

    public void setQuestionMainId(String questionMainId) {
        this.questionMainId = questionMainId;
    }

    public String getQuestionTypeId() {
        return this.questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }


}
