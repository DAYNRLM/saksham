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
 * DAO for table "EVALUATION_MASTER_LOCATION_DATA".
*/
public class EvaluationMasterLocationDataDao extends AbstractDao<EvaluationMasterLocationData, Void> {

    public static final String TABLENAME = "EVALUATION_MASTER_LOCATION_DATA";

    /**
     * Properties of entity EvaluationMasterLocationData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BlockCode = new Property(0, String.class, "blockCode", false, "BLOCK_CODE");
        public final static Property GpCode = new Property(1, String.class, "gpCode", false, "GP_CODE");
        public final static Property VillageCode = new Property(2, String.class, "villageCode", false, "VILLAGE_CODE");
    }


    public EvaluationMasterLocationDataDao(DaoConfig config) {
        super(config);
    }
    
    public EvaluationMasterLocationDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EVALUATION_MASTER_LOCATION_DATA\" (" + //
                "\"BLOCK_CODE\" TEXT," + // 0: blockCode
                "\"GP_CODE\" TEXT," + // 1: gpCode
                "\"VILLAGE_CODE\" TEXT);"); // 2: villageCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EVALUATION_MASTER_LOCATION_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EvaluationMasterLocationData entity) {
        stmt.clearBindings();
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(1, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(2, gpCode);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(3, villageCode);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EvaluationMasterLocationData entity) {
        stmt.clearBindings();
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(1, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(2, gpCode);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(3, villageCode);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public EvaluationMasterLocationData readEntity(Cursor cursor, int offset) {
        EvaluationMasterLocationData entity = new EvaluationMasterLocationData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // blockCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // gpCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // villageCode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EvaluationMasterLocationData entity, int offset) {
        entity.setBlockCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGpCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVillageCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(EvaluationMasterLocationData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(EvaluationMasterLocationData entity) {
        return null;
    }

    @Override
    public boolean hasKey(EvaluationMasterLocationData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
