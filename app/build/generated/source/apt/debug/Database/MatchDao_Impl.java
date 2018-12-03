package Database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MatchDao_Impl implements MatchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMatch;

  public MatchDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMatch = new EntityInsertionAdapter<Match>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `match_table`(`matchid`,`owner`,`wisher`,`bookid`,`status`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Match value) {
        stmt.bindLong(1, value.getMatchId());
        stmt.bindLong(2, value.getMatchOwner());
        stmt.bindLong(3, value.getMatchWisher());
        stmt.bindLong(4, value.getMatchBookId());
        final int _tmp;
        _tmp = value.getMatchStatus() ? 1 : 0;
        stmt.bindLong(5, _tmp);
      }
    };
  }

  @Override
  public void insert(Match match) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMatch.insert(match);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Match>> getMatchByOwner(Integer uid) {
    final String _sql = "SELECT * from match_table WHERE owner = ? ORDER BY matchid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, uid);
    }
    return new ComputableLiveData<List<Match>>() {
      private Observer _observer;

      @Override
      protected List<Match> compute() {
        if (_observer == null) {
          _observer = new Observer("match_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMMatchId = _cursor.getColumnIndexOrThrow("matchid");
          final int _cursorIndexOfMMatchOwner = _cursor.getColumnIndexOrThrow("owner");
          final int _cursorIndexOfMMatchWisher = _cursor.getColumnIndexOrThrow("wisher");
          final int _cursorIndexOfMMatchBookId = _cursor.getColumnIndexOrThrow("bookid");
          final int _cursorIndexOfMMatchStatus = _cursor.getColumnIndexOrThrow("status");
          final List<Match> _result = new ArrayList<Match>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Match _item;
            _item = new Match();
            final int _tmpMMatchId;
            _tmpMMatchId = _cursor.getInt(_cursorIndexOfMMatchId);
            _item.setMatchId(_tmpMMatchId);
            final int _tmpMMatchOwner;
            _tmpMMatchOwner = _cursor.getInt(_cursorIndexOfMMatchOwner);
            _item.setMatchOwner(_tmpMMatchOwner);
            final int _tmpMMatchWisher;
            _tmpMMatchWisher = _cursor.getInt(_cursorIndexOfMMatchWisher);
            _item.setMatchWisher(_tmpMMatchWisher);
            final int _tmpMMatchBookId;
            _tmpMMatchBookId = _cursor.getInt(_cursorIndexOfMMatchBookId);
            _item.setMatchBookId(_tmpMMatchBookId);
            final boolean _tmpMMatchStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMMatchStatus);
            _tmpMMatchStatus = _tmp != 0;
            _item.setMatchStatus(_tmpMMatchStatus);
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
    }.getLiveData();
  }

  @Override
  public LiveData<List<Match>> getMatchByWisher(Integer uid) {
    final String _sql = "SELECT * from match_table WHERE wisher = ? ORDER BY matchid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, uid);
    }
    return new ComputableLiveData<List<Match>>() {
      private Observer _observer;

      @Override
      protected List<Match> compute() {
        if (_observer == null) {
          _observer = new Observer("match_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMMatchId = _cursor.getColumnIndexOrThrow("matchid");
          final int _cursorIndexOfMMatchOwner = _cursor.getColumnIndexOrThrow("owner");
          final int _cursorIndexOfMMatchWisher = _cursor.getColumnIndexOrThrow("wisher");
          final int _cursorIndexOfMMatchBookId = _cursor.getColumnIndexOrThrow("bookid");
          final int _cursorIndexOfMMatchStatus = _cursor.getColumnIndexOrThrow("status");
          final List<Match> _result = new ArrayList<Match>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Match _item;
            _item = new Match();
            final int _tmpMMatchId;
            _tmpMMatchId = _cursor.getInt(_cursorIndexOfMMatchId);
            _item.setMatchId(_tmpMMatchId);
            final int _tmpMMatchOwner;
            _tmpMMatchOwner = _cursor.getInt(_cursorIndexOfMMatchOwner);
            _item.setMatchOwner(_tmpMMatchOwner);
            final int _tmpMMatchWisher;
            _tmpMMatchWisher = _cursor.getInt(_cursorIndexOfMMatchWisher);
            _item.setMatchWisher(_tmpMMatchWisher);
            final int _tmpMMatchBookId;
            _tmpMMatchBookId = _cursor.getInt(_cursorIndexOfMMatchBookId);
            _item.setMatchBookId(_tmpMMatchBookId);
            final boolean _tmpMMatchStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMMatchStatus);
            _tmpMMatchStatus = _tmp != 0;
            _item.setMatchStatus(_tmpMMatchStatus);
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
    }.getLiveData();
  }

  @Override
  public Match getMatchById(Integer mid) {
    final String _sql = "SELECT * from match_table WHERE matchid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (mid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, mid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMMatchId = _cursor.getColumnIndexOrThrow("matchid");
      final int _cursorIndexOfMMatchOwner = _cursor.getColumnIndexOrThrow("owner");
      final int _cursorIndexOfMMatchWisher = _cursor.getColumnIndexOrThrow("wisher");
      final int _cursorIndexOfMMatchBookId = _cursor.getColumnIndexOrThrow("bookid");
      final int _cursorIndexOfMMatchStatus = _cursor.getColumnIndexOrThrow("status");
      final Match _result;
      if(_cursor.moveToFirst()) {
        _result = new Match();
        final int _tmpMMatchId;
        _tmpMMatchId = _cursor.getInt(_cursorIndexOfMMatchId);
        _result.setMatchId(_tmpMMatchId);
        final int _tmpMMatchOwner;
        _tmpMMatchOwner = _cursor.getInt(_cursorIndexOfMMatchOwner);
        _result.setMatchOwner(_tmpMMatchOwner);
        final int _tmpMMatchWisher;
        _tmpMMatchWisher = _cursor.getInt(_cursorIndexOfMMatchWisher);
        _result.setMatchWisher(_tmpMMatchWisher);
        final int _tmpMMatchBookId;
        _tmpMMatchBookId = _cursor.getInt(_cursorIndexOfMMatchBookId);
        _result.setMatchBookId(_tmpMMatchBookId);
        final boolean _tmpMMatchStatus;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfMMatchStatus);
        _tmpMMatchStatus = _tmp != 0;
        _result.setMatchStatus(_tmpMMatchStatus);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
