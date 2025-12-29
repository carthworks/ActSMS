package com.actsms.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.actsms.app.data.local.dao.ActionDao;
import com.actsms.app.data.local.dao.ActionDao_Impl;
import com.actsms.app.data.local.dao.ProcessedSmsDao;
import com.actsms.app.data.local.dao.ProcessedSmsDao_Impl;
import com.actsms.app.data.local.dao.SenderPreferenceDao;
import com.actsms.app.data.local.dao.SenderPreferenceDao_Impl;
import com.actsms.app.data.local.dao.UserBehaviorDao;
import com.actsms.app.data.local.dao.UserBehaviorDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ActSmsDatabase_Impl extends ActSmsDatabase {
  private volatile ActionDao _actionDao;

  private volatile ProcessedSmsDao _processedSmsDao;

  private volatile SenderPreferenceDao _senderPreferenceDao;

  private volatile UserBehaviorDao _userBehaviorDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `actions` (`id` TEXT NOT NULL, `smsId` TEXT NOT NULL, `category` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `amount` REAL, `dueDate` TEXT, `reminderTime` TEXT, `sender` TEXT NOT NULL, `urgencyScore` REAL NOT NULL, `confidence` REAL NOT NULL, `status` TEXT NOT NULL, `createdAt` TEXT NOT NULL, `completedAt` TEXT, `metadata` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `processed_sms` (`smsId` TEXT NOT NULL, `sender` TEXT NOT NULL, `bodyHash` TEXT NOT NULL, `processedAt` TEXT NOT NULL, `actionCreated` INTEGER NOT NULL, `actionId` TEXT, PRIMARY KEY(`smsId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sender_preferences` (`sender` TEXT NOT NULL, `isBlocked` INTEGER NOT NULL, `autoAccept` INTEGER NOT NULL, `customReminderMinutes` INTEGER, `preferredCategory` TEXT, `notes` TEXT, PRIMARY KEY(`sender`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_behavior` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `actionId` TEXT NOT NULL, `category` TEXT NOT NULL, `sender` TEXT NOT NULL, `userAction` TEXT NOT NULL, `actionTimestamp` TEXT NOT NULL, `reminderMinutesBeforeDue` INTEGER, `wasOnTime` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f0cf9949239f7e95081af6094ef279e3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `actions`");
        db.execSQL("DROP TABLE IF EXISTS `processed_sms`");
        db.execSQL("DROP TABLE IF EXISTS `sender_preferences`");
        db.execSQL("DROP TABLE IF EXISTS `user_behavior`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsActions = new HashMap<String, TableInfo.Column>(15);
        _columnsActions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("smsId", new TableInfo.Column("smsId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("amount", new TableInfo.Column("amount", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("dueDate", new TableInfo.Column("dueDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("reminderTime", new TableInfo.Column("reminderTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("sender", new TableInfo.Column("sender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("urgencyScore", new TableInfo.Column("urgencyScore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("confidence", new TableInfo.Column("confidence", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("createdAt", new TableInfo.Column("createdAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("completedAt", new TableInfo.Column("completedAt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActions.put("metadata", new TableInfo.Column("metadata", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysActions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesActions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoActions = new TableInfo("actions", _columnsActions, _foreignKeysActions, _indicesActions);
        final TableInfo _existingActions = TableInfo.read(db, "actions");
        if (!_infoActions.equals(_existingActions)) {
          return new RoomOpenHelper.ValidationResult(false, "actions(com.actsms.app.data.local.entity.ActionEntity).\n"
                  + " Expected:\n" + _infoActions + "\n"
                  + " Found:\n" + _existingActions);
        }
        final HashMap<String, TableInfo.Column> _columnsProcessedSms = new HashMap<String, TableInfo.Column>(6);
        _columnsProcessedSms.put("smsId", new TableInfo.Column("smsId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProcessedSms.put("sender", new TableInfo.Column("sender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProcessedSms.put("bodyHash", new TableInfo.Column("bodyHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProcessedSms.put("processedAt", new TableInfo.Column("processedAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProcessedSms.put("actionCreated", new TableInfo.Column("actionCreated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProcessedSms.put("actionId", new TableInfo.Column("actionId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProcessedSms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProcessedSms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProcessedSms = new TableInfo("processed_sms", _columnsProcessedSms, _foreignKeysProcessedSms, _indicesProcessedSms);
        final TableInfo _existingProcessedSms = TableInfo.read(db, "processed_sms");
        if (!_infoProcessedSms.equals(_existingProcessedSms)) {
          return new RoomOpenHelper.ValidationResult(false, "processed_sms(com.actsms.app.data.local.entity.ProcessedSmsEntity).\n"
                  + " Expected:\n" + _infoProcessedSms + "\n"
                  + " Found:\n" + _existingProcessedSms);
        }
        final HashMap<String, TableInfo.Column> _columnsSenderPreferences = new HashMap<String, TableInfo.Column>(6);
        _columnsSenderPreferences.put("sender", new TableInfo.Column("sender", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSenderPreferences.put("isBlocked", new TableInfo.Column("isBlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSenderPreferences.put("autoAccept", new TableInfo.Column("autoAccept", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSenderPreferences.put("customReminderMinutes", new TableInfo.Column("customReminderMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSenderPreferences.put("preferredCategory", new TableInfo.Column("preferredCategory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSenderPreferences.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSenderPreferences = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSenderPreferences = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSenderPreferences = new TableInfo("sender_preferences", _columnsSenderPreferences, _foreignKeysSenderPreferences, _indicesSenderPreferences);
        final TableInfo _existingSenderPreferences = TableInfo.read(db, "sender_preferences");
        if (!_infoSenderPreferences.equals(_existingSenderPreferences)) {
          return new RoomOpenHelper.ValidationResult(false, "sender_preferences(com.actsms.app.data.local.entity.SenderPreferenceEntity).\n"
                  + " Expected:\n" + _infoSenderPreferences + "\n"
                  + " Found:\n" + _existingSenderPreferences);
        }
        final HashMap<String, TableInfo.Column> _columnsUserBehavior = new HashMap<String, TableInfo.Column>(8);
        _columnsUserBehavior.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("actionId", new TableInfo.Column("actionId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("sender", new TableInfo.Column("sender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("userAction", new TableInfo.Column("userAction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("actionTimestamp", new TableInfo.Column("actionTimestamp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("reminderMinutesBeforeDue", new TableInfo.Column("reminderMinutesBeforeDue", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserBehavior.put("wasOnTime", new TableInfo.Column("wasOnTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserBehavior = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserBehavior = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserBehavior = new TableInfo("user_behavior", _columnsUserBehavior, _foreignKeysUserBehavior, _indicesUserBehavior);
        final TableInfo _existingUserBehavior = TableInfo.read(db, "user_behavior");
        if (!_infoUserBehavior.equals(_existingUserBehavior)) {
          return new RoomOpenHelper.ValidationResult(false, "user_behavior(com.actsms.app.data.local.entity.UserBehaviorEntity).\n"
                  + " Expected:\n" + _infoUserBehavior + "\n"
                  + " Found:\n" + _existingUserBehavior);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f0cf9949239f7e95081af6094ef279e3", "7860c1c0205f671e0b2b0bc199a46dd3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "actions","processed_sms","sender_preferences","user_behavior");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `actions`");
      _db.execSQL("DELETE FROM `processed_sms`");
      _db.execSQL("DELETE FROM `sender_preferences`");
      _db.execSQL("DELETE FROM `user_behavior`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ActionDao.class, ActionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProcessedSmsDao.class, ProcessedSmsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SenderPreferenceDao.class, SenderPreferenceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserBehaviorDao.class, UserBehaviorDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ActionDao actionDao() {
    if (_actionDao != null) {
      return _actionDao;
    } else {
      synchronized(this) {
        if(_actionDao == null) {
          _actionDao = new ActionDao_Impl(this);
        }
        return _actionDao;
      }
    }
  }

  @Override
  public ProcessedSmsDao processedSmsDao() {
    if (_processedSmsDao != null) {
      return _processedSmsDao;
    } else {
      synchronized(this) {
        if(_processedSmsDao == null) {
          _processedSmsDao = new ProcessedSmsDao_Impl(this);
        }
        return _processedSmsDao;
      }
    }
  }

  @Override
  public SenderPreferenceDao senderPreferenceDao() {
    if (_senderPreferenceDao != null) {
      return _senderPreferenceDao;
    } else {
      synchronized(this) {
        if(_senderPreferenceDao == null) {
          _senderPreferenceDao = new SenderPreferenceDao_Impl(this);
        }
        return _senderPreferenceDao;
      }
    }
  }

  @Override
  public UserBehaviorDao userBehaviorDao() {
    if (_userBehaviorDao != null) {
      return _userBehaviorDao;
    } else {
      synchronized(this) {
        if(_userBehaviorDao == null) {
          _userBehaviorDao = new UserBehaviorDao_Impl(this);
        }
        return _userBehaviorDao;
      }
    }
  }
}
