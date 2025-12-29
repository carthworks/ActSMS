package com.actsms.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.actsms.app.data.local.Converters;
import com.actsms.app.data.local.entity.UserActionType;
import com.actsms.app.data.local.entity.UserBehaviorEntity;
import com.actsms.app.domain.model.ActionCategory;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserBehaviorDao_Impl implements UserBehaviorDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserBehaviorEntity> __insertionAdapterOfUserBehaviorEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldRecords;

  public UserBehaviorDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserBehaviorEntity = new EntityInsertionAdapter<UserBehaviorEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `user_behavior` (`id`,`actionId`,`category`,`sender`,`userAction`,`actionTimestamp`,`reminderMinutesBeforeDue`,`wasOnTime`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserBehaviorEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getActionId());
        final String _tmp = __converters.fromActionCategory(entity.getCategory());
        statement.bindString(3, _tmp);
        statement.bindString(4, entity.getSender());
        final String _tmp_1 = __converters.fromUserActionType(entity.getUserAction());
        statement.bindString(5, _tmp_1);
        final String _tmp_2 = __converters.fromLocalDateTime(entity.getActionTimestamp());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        if (entity.getReminderMinutesBeforeDue() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getReminderMinutesBeforeDue());
        }
        final Integer _tmp_3 = entity.getWasOnTime() == null ? null : (entity.getWasOnTime() ? 1 : 0);
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
      }
    };
    this.__preparedStmtOfDeleteOldRecords = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_behavior WHERE actionTimestamp < datetime('now', '-90 days')";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final UserBehaviorEntity behavior,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserBehaviorEntity.insert(behavior);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldRecords(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldRecords.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldRecords.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<UserBehaviorEntity>> getByAction(final String actionId) {
    final String _sql = "SELECT * FROM user_behavior WHERE actionId = ? ORDER BY actionTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, actionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_behavior"}, new Callable<List<UserBehaviorEntity>>() {
      @Override
      @NonNull
      public List<UserBehaviorEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "actionId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfUserAction = CursorUtil.getColumnIndexOrThrow(_cursor, "userAction");
          final int _cursorIndexOfActionTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTimestamp");
          final int _cursorIndexOfReminderMinutesBeforeDue = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBeforeDue");
          final int _cursorIndexOfWasOnTime = CursorUtil.getColumnIndexOrThrow(_cursor, "wasOnTime");
          final List<UserBehaviorEntity> _result = new ArrayList<UserBehaviorEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserBehaviorEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpActionId;
            _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
            final ActionCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toActionCategory(_tmp);
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final UserActionType _tmpUserAction;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfUserAction);
            _tmpUserAction = __converters.toUserActionType(_tmp_1);
            final LocalDateTime _tmpActionTimestamp;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActionTimestamp)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActionTimestamp);
            }
            final LocalDateTime _tmp_3 = __converters.toLocalDateTime(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpActionTimestamp = _tmp_3;
            }
            final Integer _tmpReminderMinutesBeforeDue;
            if (_cursor.isNull(_cursorIndexOfReminderMinutesBeforeDue)) {
              _tmpReminderMinutesBeforeDue = null;
            } else {
              _tmpReminderMinutesBeforeDue = _cursor.getInt(_cursorIndexOfReminderMinutesBeforeDue);
            }
            final Boolean _tmpWasOnTime;
            final Integer _tmp_4;
            if (_cursor.isNull(_cursorIndexOfWasOnTime)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getInt(_cursorIndexOfWasOnTime);
            }
            _tmpWasOnTime = _tmp_4 == null ? null : _tmp_4 != 0;
            _item = new UserBehaviorEntity(_tmpId,_tmpActionId,_tmpCategory,_tmpSender,_tmpUserAction,_tmpActionTimestamp,_tmpReminderMinutesBeforeDue,_tmpWasOnTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<UserBehaviorEntity>> getBySender(final String sender, final int limit) {
    final String _sql = "SELECT * FROM user_behavior WHERE sender = ? ORDER BY actionTimestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_behavior"}, new Callable<List<UserBehaviorEntity>>() {
      @Override
      @NonNull
      public List<UserBehaviorEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "actionId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfUserAction = CursorUtil.getColumnIndexOrThrow(_cursor, "userAction");
          final int _cursorIndexOfActionTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTimestamp");
          final int _cursorIndexOfReminderMinutesBeforeDue = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBeforeDue");
          final int _cursorIndexOfWasOnTime = CursorUtil.getColumnIndexOrThrow(_cursor, "wasOnTime");
          final List<UserBehaviorEntity> _result = new ArrayList<UserBehaviorEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserBehaviorEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpActionId;
            _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
            final ActionCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toActionCategory(_tmp);
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final UserActionType _tmpUserAction;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfUserAction);
            _tmpUserAction = __converters.toUserActionType(_tmp_1);
            final LocalDateTime _tmpActionTimestamp;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActionTimestamp)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActionTimestamp);
            }
            final LocalDateTime _tmp_3 = __converters.toLocalDateTime(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpActionTimestamp = _tmp_3;
            }
            final Integer _tmpReminderMinutesBeforeDue;
            if (_cursor.isNull(_cursorIndexOfReminderMinutesBeforeDue)) {
              _tmpReminderMinutesBeforeDue = null;
            } else {
              _tmpReminderMinutesBeforeDue = _cursor.getInt(_cursorIndexOfReminderMinutesBeforeDue);
            }
            final Boolean _tmpWasOnTime;
            final Integer _tmp_4;
            if (_cursor.isNull(_cursorIndexOfWasOnTime)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getInt(_cursorIndexOfWasOnTime);
            }
            _tmpWasOnTime = _tmp_4 == null ? null : _tmp_4 != 0;
            _item = new UserBehaviorEntity(_tmpId,_tmpActionId,_tmpCategory,_tmpSender,_tmpUserAction,_tmpActionTimestamp,_tmpReminderMinutesBeforeDue,_tmpWasOnTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<UserBehaviorEntity>> getByCategory(final ActionCategory category,
      final int limit) {
    final String _sql = "SELECT * FROM user_behavior WHERE category = ? ORDER BY actionTimestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromActionCategory(category);
    _statement.bindString(_argIndex, _tmp);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_behavior"}, new Callable<List<UserBehaviorEntity>>() {
      @Override
      @NonNull
      public List<UserBehaviorEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "actionId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfUserAction = CursorUtil.getColumnIndexOrThrow(_cursor, "userAction");
          final int _cursorIndexOfActionTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTimestamp");
          final int _cursorIndexOfReminderMinutesBeforeDue = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBeforeDue");
          final int _cursorIndexOfWasOnTime = CursorUtil.getColumnIndexOrThrow(_cursor, "wasOnTime");
          final List<UserBehaviorEntity> _result = new ArrayList<UserBehaviorEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserBehaviorEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpActionId;
            _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
            final ActionCategory _tmpCategory;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toActionCategory(_tmp_1);
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final UserActionType _tmpUserAction;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfUserAction);
            _tmpUserAction = __converters.toUserActionType(_tmp_2);
            final LocalDateTime _tmpActionTimestamp;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfActionTimestamp)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfActionTimestamp);
            }
            final LocalDateTime _tmp_4 = __converters.toLocalDateTime(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpActionTimestamp = _tmp_4;
            }
            final Integer _tmpReminderMinutesBeforeDue;
            if (_cursor.isNull(_cursorIndexOfReminderMinutesBeforeDue)) {
              _tmpReminderMinutesBeforeDue = null;
            } else {
              _tmpReminderMinutesBeforeDue = _cursor.getInt(_cursorIndexOfReminderMinutesBeforeDue);
            }
            final Boolean _tmpWasOnTime;
            final Integer _tmp_5;
            if (_cursor.isNull(_cursorIndexOfWasOnTime)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getInt(_cursorIndexOfWasOnTime);
            }
            _tmpWasOnTime = _tmp_5 == null ? null : _tmp_5 != 0;
            _item = new UserBehaviorEntity(_tmpId,_tmpActionId,_tmpCategory,_tmpSender,_tmpUserAction,_tmpActionTimestamp,_tmpReminderMinutesBeforeDue,_tmpWasOnTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAverageReminderTimeForCategory(final ActionCategory category,
      final Continuation<? super Double> $completion) {
    final String _sql = "\n"
            + "        SELECT AVG(reminderMinutesBeforeDue) \n"
            + "        FROM user_behavior \n"
            + "        WHERE category = ? \n"
            + "        AND userAction IN ('ACCEPTED', 'COMPLETED')\n"
            + "        AND reminderMinutesBeforeDue IS NOT NULL\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromActionCategory(category);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getDouble(0);
            }
            _result = _tmp_1;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageReminderTimeForSender(final String sender,
      final Continuation<? super Double> $completion) {
    final String _sql = "\n"
            + "        SELECT AVG(reminderMinutesBeforeDue) \n"
            + "        FROM user_behavior \n"
            + "        WHERE sender = ? \n"
            + "        AND userAction IN ('ACCEPTED', 'COMPLETED')\n"
            + "        AND reminderMinutesBeforeDue IS NOT NULL\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAcceptanceRateForCategory(final ActionCategory category,
      final Continuation<? super Double> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) * 100.0 / (SELECT COUNT(*) FROM user_behavior WHERE category = ?)\n"
            + "        FROM user_behavior \n"
            + "        WHERE category = ? \n"
            + "        AND userAction IN ('ACCEPTED', 'COMPLETED')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromActionCategory(category);
    _statement.bindString(_argIndex, _tmp);
    _argIndex = 2;
    final String _tmp_1 = __converters.fromActionCategory(category);
    _statement.bindString(_argIndex, _tmp_1);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getDouble(0);
            }
            _result = _tmp_2;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAcceptanceRateForSender(final String sender,
      final Continuation<? super Double> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) * 100.0 / (SELECT COUNT(*) FROM user_behavior WHERE sender = ?)\n"
            + "        FROM user_behavior \n"
            + "        WHERE sender = ? \n"
            + "        AND userAction IN ('ACCEPTED', 'COMPLETED')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    _argIndex = 2;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM user_behavior";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
