package com.shaksham.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 20): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 20;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        AddedTrainingShgDataDao.createTable(db, ifNotExists);
        AddedTrainingShgMemberDataDao.createTable(db, ifNotExists);
        AddedTrainingShgModuleDataDao.createTable(db, ifNotExists);
        AddedTrainingsDataDao.createTable(db, ifNotExists);
        BaselineSyncDataDao.createTable(db, ifNotExists);
        BaslineQuestionSyncDataDao.createTable(db, ifNotExists);
        BlockDataDao.createTable(db, ifNotExists);
        BlockLevelDataDao.createTable(db, ifNotExists);
        EvaluationMasterLocationDataDao.createTable(db, ifNotExists);
        EvaluationMasterShgDataDao.createTable(db, ifNotExists);
        EvaluationMasterTrainingDataDao.createTable(db, ifNotExists);
        EvaluationSyncDataDao.createTable(db, ifNotExists);
        EvaluationSyncQuestionDataDao.createTable(db, ifNotExists);
        EvaluationSyncShgDataDao.createTable(db, ifNotExists);
        GpDataDao.createTable(db, ifNotExists);
        LoginInfoDao.createTable(db, ifNotExists);
        ModuleDataDao.createTable(db, ifNotExists);
        QuestionInfoDetailDao.createTable(db, ifNotExists);
        ShgDataDao.createTable(db, ifNotExists);
        ShgMemberDataDao.createTable(db, ifNotExists);
        ShgModuleDataDao.createTable(db, ifNotExists);
        TitleInfoDetailDao.createTable(db, ifNotExists);
        TrainingInfoDataDao.createTable(db, ifNotExists);
        TrainingLocationInfoDao.createTable(db, ifNotExists);
        TrainingModuleInfoDao.createTable(db, ifNotExists);
        TrainingShgAndMemberDataDao.createTable(db, ifNotExists);
        ViewReportDataDao.createTable(db, ifNotExists);
        ViewReportModuleDataDao.createTable(db, ifNotExists);
        ViewReportMonthDataDao.createTable(db, ifNotExists);
        ViewReportTrainingDataDao.createTable(db, ifNotExists);
        VillageDataDao.createTable(db, ifNotExists);
        WebRequestDataDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        AddedTrainingShgDataDao.dropTable(db, ifExists);
        AddedTrainingShgMemberDataDao.dropTable(db, ifExists);
        AddedTrainingShgModuleDataDao.dropTable(db, ifExists);
        AddedTrainingsDataDao.dropTable(db, ifExists);
        BaselineSyncDataDao.dropTable(db, ifExists);
        BaslineQuestionSyncDataDao.dropTable(db, ifExists);
        BlockDataDao.dropTable(db, ifExists);
        BlockLevelDataDao.dropTable(db, ifExists);
        EvaluationMasterLocationDataDao.dropTable(db, ifExists);
        EvaluationMasterShgDataDao.dropTable(db, ifExists);
        EvaluationMasterTrainingDataDao.dropTable(db, ifExists);
        EvaluationSyncDataDao.dropTable(db, ifExists);
        EvaluationSyncQuestionDataDao.dropTable(db, ifExists);
        EvaluationSyncShgDataDao.dropTable(db, ifExists);
        GpDataDao.dropTable(db, ifExists);
        LoginInfoDao.dropTable(db, ifExists);
        ModuleDataDao.dropTable(db, ifExists);
        QuestionInfoDetailDao.dropTable(db, ifExists);
        ShgDataDao.dropTable(db, ifExists);
        ShgMemberDataDao.dropTable(db, ifExists);
        ShgModuleDataDao.dropTable(db, ifExists);
        TitleInfoDetailDao.dropTable(db, ifExists);
        TrainingInfoDataDao.dropTable(db, ifExists);
        TrainingLocationInfoDao.dropTable(db, ifExists);
        TrainingModuleInfoDao.dropTable(db, ifExists);
        TrainingShgAndMemberDataDao.dropTable(db, ifExists);
        ViewReportDataDao.dropTable(db, ifExists);
        ViewReportModuleDataDao.dropTable(db, ifExists);
        ViewReportMonthDataDao.dropTable(db, ifExists);
        ViewReportTrainingDataDao.dropTable(db, ifExists);
        VillageDataDao.dropTable(db, ifExists);
        WebRequestDataDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AddedTrainingShgDataDao.class);
        registerDaoClass(AddedTrainingShgMemberDataDao.class);
        registerDaoClass(AddedTrainingShgModuleDataDao.class);
        registerDaoClass(AddedTrainingsDataDao.class);
        registerDaoClass(BaselineSyncDataDao.class);
        registerDaoClass(BaslineQuestionSyncDataDao.class);
        registerDaoClass(BlockDataDao.class);
        registerDaoClass(BlockLevelDataDao.class);
        registerDaoClass(EvaluationMasterLocationDataDao.class);
        registerDaoClass(EvaluationMasterShgDataDao.class);
        registerDaoClass(EvaluationMasterTrainingDataDao.class);
        registerDaoClass(EvaluationSyncDataDao.class);
        registerDaoClass(EvaluationSyncQuestionDataDao.class);
        registerDaoClass(EvaluationSyncShgDataDao.class);
        registerDaoClass(GpDataDao.class);
        registerDaoClass(LoginInfoDao.class);
        registerDaoClass(ModuleDataDao.class);
        registerDaoClass(QuestionInfoDetailDao.class);
        registerDaoClass(ShgDataDao.class);
        registerDaoClass(ShgMemberDataDao.class);
        registerDaoClass(ShgModuleDataDao.class);
        registerDaoClass(TitleInfoDetailDao.class);
        registerDaoClass(TrainingInfoDataDao.class);
        registerDaoClass(TrainingLocationInfoDao.class);
        registerDaoClass(TrainingModuleInfoDao.class);
        registerDaoClass(TrainingShgAndMemberDataDao.class);
        registerDaoClass(ViewReportDataDao.class);
        registerDaoClass(ViewReportModuleDataDao.class);
        registerDaoClass(ViewReportMonthDataDao.class);
        registerDaoClass(ViewReportTrainingDataDao.class);
        registerDaoClass(VillageDataDao.class);
        registerDaoClass(WebRequestDataDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
