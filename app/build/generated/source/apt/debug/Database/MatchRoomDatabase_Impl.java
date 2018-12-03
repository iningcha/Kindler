package Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class MatchRoomDatabase_Impl extends MatchRoomDatabase {
  private volatile MatchDao _matchDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `match_table` (`matchid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `owner` INTEGER NOT NULL, `wisher` INTEGER NOT NULL, `bookid` INTEGER NOT NULL, `status` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"71896d0aa50898bb8f5bc5a22adb12da\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `match_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMatchTable = new HashMap<String, TableInfo.Column>(5);
        _columnsMatchTable.put("matchid", new TableInfo.Column("matchid", "INTEGER", true, 1));
        _columnsMatchTable.put("owner", new TableInfo.Column("owner", "INTEGER", true, 0));
        _columnsMatchTable.put("wisher", new TableInfo.Column("wisher", "INTEGER", true, 0));
        _columnsMatchTable.put("bookid", new TableInfo.Column("bookid", "INTEGER", true, 0));
        _columnsMatchTable.put("status", new TableInfo.Column("status", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMatchTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMatchTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMatchTable = new TableInfo("match_table", _columnsMatchTable, _foreignKeysMatchTable, _indicesMatchTable);
        final TableInfo _existingMatchTable = TableInfo.read(_db, "match_table");
        if (! _infoMatchTable.equals(_existingMatchTable)) {
          throw new IllegalStateException("Migration didn't properly handle match_table(Database.Match).\n"
                  + " Expected:\n" + _infoMatchTable + "\n"
                  + " Found:\n" + _existingMatchTable);
        }
      }
    }, "71896d0aa50898bb8f5bc5a22adb12da", "3b1c2741d14a5d1b5de17fa32c0fc8d4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "match_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `match_table`");
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
  public MatchDao MatchDao() {
    if (_matchDao != null) {
      return _matchDao;
    } else {
      synchronized(this) {
        if(_matchDao == null) {
          _matchDao = new MatchDao_Impl(this);
        }
        return _matchDao;
      }
    }
  }
}
