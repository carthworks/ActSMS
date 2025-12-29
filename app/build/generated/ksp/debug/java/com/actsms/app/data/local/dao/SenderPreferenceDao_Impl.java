package com.actsms.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.actsms.app.data.local.Converters;
import com.actsms.app.data.local.entity.SenderPreferenceEntity;
import com.actsms.app.domain.model.ActionCategory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
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
public final class SenderPreferenceDao_Impl implements SenderPreferenceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SenderPreferenceEntity> __insertionAdapterOfSenderPreferenceEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SenderPreferenceEntity> __updateAdapterOfSenderPreferenceEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public SenderPreferenceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSenderPreferenceEntity = new EntityInsertionAdapter<SenderPreferenceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sender_preferences` (`sender`,`isBlocked`,`autoAccept`,`customReminderMinutes`,`preferredCategory`,`notes`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SenderPreferenceEntity entity) {
        statement.bindString(1, entity.getSender());
        final int _tmp = entity.isBlocked() ? 1 : 0;
        statement.bindLong(2, _tmp);
        final int _tmp_1 = entity.getAutoAccept() ? 1 : 0;
        statement.bindLong(3, _tmp_1);
        if (entity.getCustomReminderMinutes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCustomReminderMinutes());
        }
        final String _tmp_2;
        if (entity.getPreferredCategory() == null) {
          _tmp_2 = null;
        } else {
          _tmp_2 = __converters.fromActionCategory(entity.getPreferredCategory());
        }
        if (_tmp_2 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_2);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
      }
    };
    this.__updateAdapterOfSenderPreferenceEntity = new EntityDeletionOrUpdateAdapter<SenderPreferenceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `sender_preferences` SET `sender` = ?,`isBlocked` = ?,`autoAccept` = ?,`customReminderMinutes` = ?,`preferredCategory` = ?,`notes` = ? WHERE `sender` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SenderPreferenceEntity entity) {
        statement.bindString(1, entity.getSender());
        final int _tmp = entity.isBlocked() ? 1 : 0;
        statement.bindLong(2, _tmp);
        final int _tmp_1 = entity.getAutoAccept() ? 1 : 0;
        statement.bindLong(3, _tmp_1);
        if (entity.getCustomReminderMinutes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCustomReminderMinutes());
        }
        final String _tmp_2;
        if (entity.getPreferredCategory() == null) {
          _tmp_2 = null;
        } else {
          _tmp_2 = __converters.fromActionCategory(entity.getPreferredCategory());
        }
        if (_tmp_2 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_2);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindString(7, entity.getSender());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sender_preferences WHERE sender = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SenderPreferenceEntity preference,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSenderPreferenceEntity.insert(preference);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SenderPreferenceEntity preference,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSenderPreferenceEntity.handle(preference);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String sender, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, sender);
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
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getBySender(final String sender,
      final Continuation<? super SenderPreferenceEntity> $completion) {
    final String _sql = "SELECT * FROM sender_preferences WHERE sender = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SenderPreferenceEntity>() {
      @Override
      @Nullable
      public SenderPreferenceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfAutoAccept = CursorUtil.getColumnIndexOrThrow(_cursor, "autoAccept");
          final int _cursorIndexOfCustomReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "customReminderMinutes");
          final int _cursorIndexOfPreferredCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredCategory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final SenderPreferenceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpAutoAccept;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoAccept);
            _tmpAutoAccept = _tmp_1 != 0;
            final Integer _tmpCustomReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfCustomReminderMinutes)) {
              _tmpCustomReminderMinutes = null;
            } else {
              _tmpCustomReminderMinutes = _cursor.getInt(_cursorIndexOfCustomReminderMinutes);
            }
            final ActionCategory _tmpPreferredCategory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredCategory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredCategory);
            }
            if (_tmp_2 == null) {
              _tmpPreferredCategory = null;
            } else {
              _tmpPreferredCategory = __converters.toActionCategory(_tmp_2);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new SenderPreferenceEntity(_tmpSender,_tmpIsBlocked,_tmpAutoAccept,_tmpCustomReminderMinutes,_tmpPreferredCategory,_tmpNotes);
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
  public Flow<SenderPreferenceEntity> observeBySender(final String sender) {
    final String _sql = "SELECT * FROM sender_preferences WHERE sender = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sender_preferences"}, new Callable<SenderPreferenceEntity>() {
      @Override
      @Nullable
      public SenderPreferenceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfAutoAccept = CursorUtil.getColumnIndexOrThrow(_cursor, "autoAccept");
          final int _cursorIndexOfCustomReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "customReminderMinutes");
          final int _cursorIndexOfPreferredCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredCategory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final SenderPreferenceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpAutoAccept;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoAccept);
            _tmpAutoAccept = _tmp_1 != 0;
            final Integer _tmpCustomReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfCustomReminderMinutes)) {
              _tmpCustomReminderMinutes = null;
            } else {
              _tmpCustomReminderMinutes = _cursor.getInt(_cursorIndexOfCustomReminderMinutes);
            }
            final ActionCategory _tmpPreferredCategory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredCategory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredCategory);
            }
            if (_tmp_2 == null) {
              _tmpPreferredCategory = null;
            } else {
              _tmpPreferredCategory = __converters.toActionCategory(_tmp_2);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new SenderPreferenceEntity(_tmpSender,_tmpIsBlocked,_tmpAutoAccept,_tmpCustomReminderMinutes,_tmpPreferredCategory,_tmpNotes);
          } else {
            _result = null;
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
  public Flow<List<SenderPreferenceEntity>> getBlockedSenders() {
    final String _sql = "SELECT * FROM sender_preferences WHERE isBlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sender_preferences"}, new Callable<List<SenderPreferenceEntity>>() {
      @Override
      @NonNull
      public List<SenderPreferenceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfAutoAccept = CursorUtil.getColumnIndexOrThrow(_cursor, "autoAccept");
          final int _cursorIndexOfCustomReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "customReminderMinutes");
          final int _cursorIndexOfPreferredCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredCategory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<SenderPreferenceEntity> _result = new ArrayList<SenderPreferenceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SenderPreferenceEntity _item;
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpAutoAccept;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoAccept);
            _tmpAutoAccept = _tmp_1 != 0;
            final Integer _tmpCustomReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfCustomReminderMinutes)) {
              _tmpCustomReminderMinutes = null;
            } else {
              _tmpCustomReminderMinutes = _cursor.getInt(_cursorIndexOfCustomReminderMinutes);
            }
            final ActionCategory _tmpPreferredCategory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredCategory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredCategory);
            }
            if (_tmp_2 == null) {
              _tmpPreferredCategory = null;
            } else {
              _tmpPreferredCategory = __converters.toActionCategory(_tmp_2);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new SenderPreferenceEntity(_tmpSender,_tmpIsBlocked,_tmpAutoAccept,_tmpCustomReminderMinutes,_tmpPreferredCategory,_tmpNotes);
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
  public Flow<List<SenderPreferenceEntity>> getAutoAcceptSenders() {
    final String _sql = "SELECT * FROM sender_preferences WHERE autoAccept = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sender_preferences"}, new Callable<List<SenderPreferenceEntity>>() {
      @Override
      @NonNull
      public List<SenderPreferenceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfAutoAccept = CursorUtil.getColumnIndexOrThrow(_cursor, "autoAccept");
          final int _cursorIndexOfCustomReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "customReminderMinutes");
          final int _cursorIndexOfPreferredCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredCategory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<SenderPreferenceEntity> _result = new ArrayList<SenderPreferenceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SenderPreferenceEntity _item;
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpAutoAccept;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoAccept);
            _tmpAutoAccept = _tmp_1 != 0;
            final Integer _tmpCustomReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfCustomReminderMinutes)) {
              _tmpCustomReminderMinutes = null;
            } else {
              _tmpCustomReminderMinutes = _cursor.getInt(_cursorIndexOfCustomReminderMinutes);
            }
            final ActionCategory _tmpPreferredCategory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredCategory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredCategory);
            }
            if (_tmp_2 == null) {
              _tmpPreferredCategory = null;
            } else {
              _tmpPreferredCategory = __converters.toActionCategory(_tmp_2);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new SenderPreferenceEntity(_tmpSender,_tmpIsBlocked,_tmpAutoAccept,_tmpCustomReminderMinutes,_tmpPreferredCategory,_tmpNotes);
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
  public Flow<List<SenderPreferenceEntity>> getAllPreferences() {
    final String _sql = "SELECT * FROM sender_preferences ORDER BY sender ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sender_preferences"}, new Callable<List<SenderPreferenceEntity>>() {
      @Override
      @NonNull
      public List<SenderPreferenceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfAutoAccept = CursorUtil.getColumnIndexOrThrow(_cursor, "autoAccept");
          final int _cursorIndexOfCustomReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "customReminderMinutes");
          final int _cursorIndexOfPreferredCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredCategory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<SenderPreferenceEntity> _result = new ArrayList<SenderPreferenceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SenderPreferenceEntity _item;
            final String _tmpSender;
            _tmpSender = _cursor.getString(_cursorIndexOfSender);
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpAutoAccept;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoAccept);
            _tmpAutoAccept = _tmp_1 != 0;
            final Integer _tmpCustomReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfCustomReminderMinutes)) {
              _tmpCustomReminderMinutes = null;
            } else {
              _tmpCustomReminderMinutes = _cursor.getInt(_cursorIndexOfCustomReminderMinutes);
            }
            final ActionCategory _tmpPreferredCategory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredCategory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredCategory);
            }
            if (_tmp_2 == null) {
              _tmpPreferredCategory = null;
            } else {
              _tmpPreferredCategory = __converters.toActionCategory(_tmp_2);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new SenderPreferenceEntity(_tmpSender,_tmpIsBlocked,_tmpAutoAccept,_tmpCustomReminderMinutes,_tmpPreferredCategory,_tmpNotes);
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
  public Object getCustomReminderTime(final String sender,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT customReminderMinutes FROM sender_preferences WHERE sender = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getInt(0);
            }
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
  public Object getPreferredCategory(final String sender,
      final Continuation<? super ActionCategory> $completion) {
    final String _sql = "SELECT preferredCategory FROM sender_preferences WHERE sender = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sender);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ActionCategory>() {
      @Override
      @Nullable
      public ActionCategory call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final ActionCategory _result;
          if (_cursor.moveToFirst()) {
            final String _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(0);
            }
            if (_tmp == null) {
              _result = null;
            } else {
              _result = __converters.toActionCategory(_tmp);
            }
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
