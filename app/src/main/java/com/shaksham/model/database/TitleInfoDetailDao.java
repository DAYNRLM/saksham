package com.shaksham.model.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TITLE_INFO_DETAIL".
*/
public class TitleInfoDetailDao extends AbstractDao<TitleInfoDetail, Long> {

    public static final String TABLENAME = "TITLE_INFO_DETAIL";

    /**
     * Properties of entity TitleInfoDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AutogenratedTitleId = new Property(0, Long.class, "autogenratedTitleId", true, "_id");
        public final static Property TitleId = new Property(1, Long.class, "titleId", false, "TITLE_ID");
        public final static Property TitleName = new Property(2, String.class, "titleName", false, "TITLE_NAME");
        public final static Property LanguageId = new Property(3, String.class, "languageId", false, "LANGUAGE_ID");
    }

    private DaoSession daoSession;


    public TitleInfoDetailDao(DaoConfig config) {
        super(config);
    }
    
    public TitleInfoDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TITLE_INFO_DETAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: autogenratedTitleId
                "\"TITLE_ID\" INTEGER," + // 1: titleId
                "\"TITLE_NAME\" TEXT," + // 2: titleName
                "\"LANGUAGE_ID\" TEXT);"); // 3: languageId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TITLE_INFO_DETAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TitleInfoDetail entity) {
        stmt.clearBindings();
 
        Long autogenratedTitleId = entity.getAutogenratedTitleId();
        if (autogenratedTitleId != null) {
            stmt.bindLong(1, autogenratedTitleId);
        }
 
        Long titleId = entity.getTitleId();
        if (titleId != null) {
            stmt.bindLong(2, titleId);
        }
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(3, titleName);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(4, languageId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TitleInfoDetail entity) {
        stmt.clearBindings();
 
        Long autogenratedTitleId = entity.getAutogenratedTitleId();
        if (autogenratedTitleId != null) {
            stmt.bindLong(1, autogenratedTitleId);
        }
 
        Long titleId = entity.getTitleId();
        if (titleId != null) {
            stmt.bindLong(2, titleId);
        }
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(3, titleName);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(4, languageId);
        }
    }

    @Override
    protected final void attachEntity(TitleInfoDetail entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TitleInfoDetail readEntity(Cursor cursor, int offset) {
        TitleInfoDetail entity = new TitleInfoDetail( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // autogenratedTitleId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // titleId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // titleName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // languageId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TitleInfoDetail entity, int offset) {
        entity.setAutogenratedTitleId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitleId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTitleName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLanguageId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TitleInfoDetail entity, long rowId) {
        entity.setAutogenratedTitleId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TitleInfoDetail entity) {
        if(entity != null) {
            return entity.getAutogenratedTitleId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TitleInfoDetail entity) {
        return entity.getAutogenratedTitleId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
