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
 * DAO for table "MODULE_DATA".
*/
public class ModuleDataDao extends AbstractDao<ModuleData, Void> {

    public static final String TABLENAME = "MODULE_DATA";

    /**
     * Properties of entity ModuleData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ModuleCode = new Property(0, String.class, "moduleCode", false, "MODULE_CODE");
        public final static Property ModuleName = new Property(1, String.class, "moduleName", false, "MODULE_NAME");
        public final static Property LanguageId = new Property(2, String.class, "languageId", false, "LANGUAGE_ID");
    }


    public ModuleDataDao(DaoConfig config) {
        super(config);
    }
    
    public ModuleDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MODULE_DATA\" (" + //
                "\"MODULE_CODE\" TEXT," + // 0: moduleCode
                "\"MODULE_NAME\" TEXT," + // 1: moduleName
                "\"LANGUAGE_ID\" TEXT);"); // 2: languageId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MODULE_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ModuleData entity) {
        stmt.clearBindings();
 
        String moduleCode = entity.getModuleCode();
        if (moduleCode != null) {
            stmt.bindString(1, moduleCode);
        }
 
        String moduleName = entity.getModuleName();
        if (moduleName != null) {
            stmt.bindString(2, moduleName);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(3, languageId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ModuleData entity) {
        stmt.clearBindings();
 
        String moduleCode = entity.getModuleCode();
        if (moduleCode != null) {
            stmt.bindString(1, moduleCode);
        }
 
        String moduleName = entity.getModuleName();
        if (moduleName != null) {
            stmt.bindString(2, moduleName);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(3, languageId);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public ModuleData readEntity(Cursor cursor, int offset) {
        ModuleData entity = new ModuleData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // moduleCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // moduleName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // languageId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ModuleData entity, int offset) {
        entity.setModuleCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setModuleName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLanguageId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(ModuleData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(ModuleData entity) {
        return null;
    }

    @Override
    public boolean hasKey(ModuleData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}