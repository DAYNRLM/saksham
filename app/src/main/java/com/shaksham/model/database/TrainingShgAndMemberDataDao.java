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
 * DAO for table "TRAINING_SHG_AND_MEMBER_DATA".
*/
public class TrainingShgAndMemberDataDao extends AbstractDao<TrainingShgAndMemberData, Void> {

    public static final String TABLENAME = "TRAINING_SHG_AND_MEMBER_DATA";

    /**
     * Properties of entity TrainingShgAndMemberData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TrainingId = new Property(0, String.class, "trainingId", false, "TRAINING_ID");
        public final static Property ShgCode = new Property(1, String.class, "shgCode", false, "SHG_CODE");
        public final static Property ShgMemberCode = new Property(2, String.class, "shgMemberCode", false, "SHG_MEMBER_CODE");
        public final static Property SyncStatus = new Property(3, String.class, "syncStatus", false, "SYNC_STATUS");
    }


    public TrainingShgAndMemberDataDao(DaoConfig config) {
        super(config);
    }
    
    public TrainingShgAndMemberDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TRAINING_SHG_AND_MEMBER_DATA\" (" + //
                "\"TRAINING_ID\" TEXT," + // 0: trainingId
                "\"SHG_CODE\" TEXT," + // 1: shgCode
                "\"SHG_MEMBER_CODE\" TEXT," + // 2: shgMemberCode
                "\"SYNC_STATUS\" TEXT);"); // 3: syncStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TRAINING_SHG_AND_MEMBER_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TrainingShgAndMemberData entity) {
        stmt.clearBindings();
 
        String trainingId = entity.getTrainingId();
        if (trainingId != null) {
            stmt.bindString(1, trainingId);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(2, shgCode);
        }
 
        String shgMemberCode = entity.getShgMemberCode();
        if (shgMemberCode != null) {
            stmt.bindString(3, shgMemberCode);
        }
 
        String syncStatus = entity.getSyncStatus();
        if (syncStatus != null) {
            stmt.bindString(4, syncStatus);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TrainingShgAndMemberData entity) {
        stmt.clearBindings();
 
        String trainingId = entity.getTrainingId();
        if (trainingId != null) {
            stmt.bindString(1, trainingId);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(2, shgCode);
        }
 
        String shgMemberCode = entity.getShgMemberCode();
        if (shgMemberCode != null) {
            stmt.bindString(3, shgMemberCode);
        }
 
        String syncStatus = entity.getSyncStatus();
        if (syncStatus != null) {
            stmt.bindString(4, syncStatus);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TrainingShgAndMemberData readEntity(Cursor cursor, int offset) {
        TrainingShgAndMemberData entity = new TrainingShgAndMemberData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // trainingId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // shgCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // shgMemberCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // syncStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TrainingShgAndMemberData entity, int offset) {
        entity.setTrainingId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setShgCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setShgMemberCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSyncStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TrainingShgAndMemberData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TrainingShgAndMemberData entity) {
        return null;
    }

    @Override
    public boolean hasKey(TrainingShgAndMemberData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}