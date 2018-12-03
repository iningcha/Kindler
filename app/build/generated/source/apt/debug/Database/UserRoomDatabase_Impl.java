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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class UserRoomDatabase_Impl extends UserRoomDatabase {
  private volatile UserDao _userDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`userid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `password` TEXT NOT NULL, `matches` TEXT, `wishlist` TEXT, `ownedlist` TEXT, `name` TEXT, `bio` TEXT, `pic` TEXT)");
        _db.execSQL("CREATE UNIQUE INDEX `index_user_table_username` ON `user_table` (`username`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"43c186a37115710a9adf71e461440865\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `user_table`");
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
        final HashMap<String, TableInfo.Column> _columnsUserTable = new HashMap<String, TableInfo.Column>(9);
        _columnsUserTable.put("userid", new TableInfo.Column("userid", "INTEGER", true, 1));
        _columnsUserTable.put("username", new TableInfo.Column("username", "TEXT", true, 0));
        _columnsUserTable.put("password", new TableInfo.Column("password", "TEXT", true, 0));
        _columnsUserTable.put("matches", new TableInfo.Column("matches", "TEXT", false, 0));
        _columnsUserTable.put("wishlist", new TableInfo.Column("wishlist", "TEXT", false, 0));
        _columnsUserTable.put("ownedlist", new TableInfo.Column("ownedlist", "TEXT", false, 0));
        _columnsUserTable.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsUserTable.put("bio", new TableInfo.Column("bio", "TEXT", false, 0));
        _columnsUserTable.put("pic", new TableInfo.Column("pic", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserTable = new HashSet<TableInfo.Index>(1);
        _indicesUserTable.add(new TableInfo.Index("index_user_table_username", true, Arrays.asList("username")));
        final TableInfo _infoUserTable = new TableInfo("user_table", _columnsUserTable, _foreignKeysUserTable, _indicesUserTable);
        final TableInfo _existingUserTable = TableInfo.read(_db, "user_table");
        if (! _infoUserTable.equals(_existingUserTable)) {
          throw new IllegalStateException("Migration didn't properly handle user_table(Database.User).\n"
                  + " Expected:\n" + _infoUserTable + "\n"
                  + " Found:\n" + _existingUserTable);
        }
      }
    }, "43c186a37115710a9adf71e461440865", "7dc10ff8dfad0d3106880cd5d63465e3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "user_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `user_table`");
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
  public UserDao UserDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
