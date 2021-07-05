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
 * DAO for table "BLOCK_LEVEL_DATA".
*/
public class BlockLevelDataDao extends AbstractDao<BlockLevelData, Void> {

    public static final String TABLENAME = "BLOCK_LEVEL_DATA";

    /**
     * Properties of entity BlockLevelData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BlockName = new Property(0, String.class, "blockName", false, "BLOCK_NAME");
        public final static Property BlockCode = new Property(1, String.class, "blockCode", false, "BLOCK_CODE");
        public final static Property GpCode = new Property(2, String.class, "gpCode", false, "GP_CODE");
        public final static Property GpName = new Property(3, String.class, "gpName", false, "GP_NAME");
        public final static Property VillageName = new Property(4, String.class, "villageName", false, "VILLAGE_NAME");
        public final static Property VillageCode = new Property(5, String.class, "villageCode", false, "VILLAGE_CODE");
        public final static Property ShgName = new Property(6, String.class, "shgName", false, "SHG_NAME");
        public final static Property ShgCode = new Property(7, String.class, "shgCode", false, "SHG_CODE");
        public final static Property BaseLineStatus = new Property(8, String.class, "baseLineStatus", false, "BASE_LINE_STATUS");
    }


    public BlockLevelDataDao(DaoConfig config) {
        super(config);
    }
    
    public BlockLevelDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BLOCK_LEVEL_DATA\" (" + //
                "\"BLOCK_NAME\" TEXT," + // 0: blockName
                "\"BLOCK_CODE\" TEXT," + // 1: blockCode
                "\"GP_CODE\" TEXT," + // 2: gpCode
                "\"GP_NAME\" TEXT," + // 3: gpName
                "\"VILLAGE_NAME\" TEXT," + // 4: villageName
                "\"VILLAGE_CODE\" TEXT," + // 5: villageCode
                "\"SHG_NAME\" TEXT," + // 6: shgName
                "\"SHG_CODE\" TEXT," + // 7: shgCode
                "\"BASE_LINE_STATUS\" TEXT);"); // 8: baseLineStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BLOCK_LEVEL_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BlockLevelData entity) {
        stmt.clearBindings();
 
        String blockName = entity.getBlockName();
        if (blockName != null) {
            stmt.bindString(1, blockName);
        }
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(2, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(3, gpCode);
        }
 
        String gpName = entity.getGpName();
        if (gpName != null) {
            stmt.bindString(4, gpName);
        }
 
        String villageName = entity.getVillageName();
        if (villageName != null) {
            stmt.bindString(5, villageName);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(6, villageCode);
        }
 
        String shgName = entity.getShgName();
        if (shgName != null) {
            stmt.bindString(7, shgName);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(8, shgCode);
        }
 
        String baseLineStatus = entity.getBaseLineStatus();
        if (baseLineStatus != null) {
            stmt.bindString(9, baseLineStatus);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BlockLevelData entity) {
        stmt.clearBindings();
 
        String blockName = entity.getBlockName();
        if (blockName != null) {
            stmt.bindString(1, blockName);
        }
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(2, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(3, gpCode);
        }
 
        String gpName = entity.getGpName();
        if (gpName != null) {
            stmt.bindString(4, gpName);
        }
 
        String villageName = entity.getVillageName();
        if (villageName != null) {
            stmt.bindString(5, villageName);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(6, villageCode);
        }
 
        String shgName = entity.getShgName();
        if (shgName != null) {
            stmt.bindString(7, shgName);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(8, shgCode);
        }
 
        String baseLineStatus = entity.getBaseLineStatus();
        if (baseLineStatus != null) {
            stmt.bindString(9, baseLineStatus);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public BlockLevelData readEntity(Cursor cursor, int offset) {
        BlockLevelData entity = new BlockLevelData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // blockName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // blockCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // gpCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gpName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // villageName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // villageCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // shgName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // shgCode
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // baseLineStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BlockLevelData entity, int offset) {
        entity.setBlockName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setBlockCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGpCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGpName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVillageName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVillageCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setShgName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setShgCode(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBaseLineStatus(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(BlockLevelData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(BlockLevelData entity) {
        return null;
    }

    @Override
    public boolean hasKey(BlockLevelData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
