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
public class BookRoomDatabase_Impl extends BookRoomDatabase {
  private volatile BookDao _bookDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `book_table` (`bookid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bookname` TEXT NOT NULL, `bookpic` TEXT NOT NULL, `wishuser` TEXT, `owneduser` TEXT)");
        _db.execSQL("CREATE UNIQUE INDEX `index_book_table_bookname` ON `book_table` (`bookname`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"6943003f91c4ddb34c8ea5fbaa5ffe60\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `book_table`");
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
        final HashMap<String, TableInfo.Column> _columnsBookTable = new HashMap<String, TableInfo.Column>(5);
        _columnsBookTable.put("bookid", new TableInfo.Column("bookid", "INTEGER", true, 1));
        _columnsBookTable.put("bookname", new TableInfo.Column("bookname", "TEXT", true, 0));
        _columnsBookTable.put("bookpic", new TableInfo.Column("bookpic", "TEXT", true, 0));
        _columnsBookTable.put("wishuser", new TableInfo.Column("wishuser", "TEXT", false, 0));
        _columnsBookTable.put("owneduser", new TableInfo.Column("owneduser", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookTable = new HashSet<TableInfo.Index>(1);
        _indicesBookTable.add(new TableInfo.Index("index_book_table_bookname", true, Arrays.asList("bookname")));
        final TableInfo _infoBookTable = new TableInfo("book_table", _columnsBookTable, _foreignKeysBookTable, _indicesBookTable);
        final TableInfo _existingBookTable = TableInfo.read(_db, "book_table");
        if (! _infoBookTable.equals(_existingBookTable)) {
          throw new IllegalStateException("Migration didn't properly handle book_table(Database.Book).\n"
                  + " Expected:\n" + _infoBookTable + "\n"
                  + " Found:\n" + _existingBookTable);
        }
      }
    }, "6943003f91c4ddb34c8ea5fbaa5ffe60", "53a77b248efaf5b0cee6cd522f7f734b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "book_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `book_table`");
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
  public BookDao BookDao() {
    if (_bookDao != null) {
      return _bookDao;
    } else {
      synchronized(this) {
        if(_bookDao == null) {
          _bookDao = new BookDao_Impl(this);
        }
        return _bookDao;
      }
    }
  }
}
