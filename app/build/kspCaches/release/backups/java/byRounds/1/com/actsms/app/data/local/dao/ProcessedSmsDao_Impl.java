package com.actsms.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.actsms.app.data.local.Converters;
import com.actsms.app.data.local.entity.ProcessedSmsEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
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
public final class ProcessedSmsDao_Impl implements ProcessedSmsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProcessedSmsEntity> __insertionAdapterOfProcessedSmsEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldRecords;

  public ProcessedSmsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProcessedSmsEntity = new EntityInsertionAdapter<ProcessedSmsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `processed_sms` (`smsId`,`sender`,`bodyHash`,`processedAt`,`actionCreated`,`actionId`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProcessedSmsEntity entity) {
        statement.bindString(1, entity.getSmsId());
        statement.bindString(2, entity.getSender());
        statement.bindString(3, entity.getBodyHash());
        final String _tmp = __converters.fromLocalDateTime(entity.getProcessedAt());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final int _tmp_1 = entity.getActionCreated() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getActionId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getActionId());
        }
      }
    };
    this.__preparedStmtOfDeleteOldRecords = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM processed_sms WHERE processedAt < datetime('now', '-30 days')";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ProcessedSmsEntity processedSms,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProcessedSmsEntity.insertAndReturnId(processedSms);
          __db.setTransactionSuccessful();
          return _result;
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
  public Object isProcessed(final String smsId, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM processed_sms WHERE smsId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, smsId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
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
  public Object isDuplicate(final String sender, final String bodyHash,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM processed_sms WHERE bodyHash = ? AND sender = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bodyHash);
    _argIndex = 2;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
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
  public Flow<List<ProcessedSmsEntity>> getBySender(final String sender, final int limit) {
    final String _sql = "SELECT * FROM processed_sms WHERE sender = ? ORDER BY processedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"processed_sms"}, new Callable<List<ProcessedSmsEntity>>() {
      @Override
      @NonNull
      public List<ProcessedSmsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSmsId = CursorUtil.getColumnIndexOrThrow(_cursor, "smsId");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfBodyHash = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyHash");
          final int _cursorIndexOfProcessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "processedAt");
          final int _cursorIndexOfActionCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "actionCreated");
          final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "actionId");
          final List<ProcessedSmsEntity> _result = new ArrayList<ProcessedSmsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProcessedSmsEntity _item;
            final String _tmpSmsId;
            _tmpSmsId = _cursor.getString(_cursorIndexOfSmsId);
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final String _tmpBodyHash;
            _tmpBodyHash = _cursor.getString(_cursorIndexOfBodyHash);
            final LocalDateTime _tmpProcessedAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfProcessedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfProcessedAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpProcessedAt = _tmp_1;
            }
            final boolean _tmpActionCreated;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfActionCreated);
            _tmpActionCreated = _tmp_2 != 0;
            final String _tmpActionId;
            if (_cursor.isNull(_cursorIndexOfActionId)) {
              _tmpActionId = null;
            } else {
              _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
            }
            _item = new ProcessedSmsEntity(_tmpSmsId,_tmpSender,_tmpBodyHash,_tmpProcessedAt,_tmpActionCreated,_tmpActionId);
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
  public Flow<List<ProcessedSmsEntity>> getAllWithActions() {
    final String _sql = "SELECT * FROM processed_sms WHERE actionCreated = 1 ORDER BY processedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"processed_sms"}, new Callable<List<ProcessedSmsEntity>>() {
      @Override
      @NonNull
      public List<ProcessedSmsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSmsId = CursorUtil.getColumnIndexOrThrow(_cursor, "smsId");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfBodyHash = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyHash");
          final int _cursorIndexOfProcessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "processedAt");
          final int _cursorIndexOfActionCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "actionCreated");
          final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "actionId");
          final List<ProcessedSmsEntity> _result = new ArrayList<ProcessedSmsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProcessedSmsEntity _item;
            final String _tmpSmsId;
            _tmpSmsId = _cursor.getString(_cursorIndexOfSmsId);
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final String _tmpBodyHash;
            _tmpBodyHash = _cursor.getString(_cursorIndexOfBodyHash);
            final LocalDateTime _tmpProcessedAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfProcessedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfProcessedAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpProcessedAt = _tmp_1;
            }
            final boolean _tmpActionCreated;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfActionCreated);
            _tmpActionCreated = _tmp_2 != 0;
            final String _tmpActionId;
            if (_cursor.isNull(_cursorIndexOfActionId)) {
              _tmpActionId = null;
            } else {
              _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
            }
            _item = new ProcessedSmsEntity(_tmpSmsId,_tmpSender,_tmpBodyHash,_tmpProcessedAt,_tmpActionCreated,_tmpActionId);
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
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM processed_sms";
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
