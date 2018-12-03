package Database;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class BookDao_Impl implements BookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBook;

  private final ListTypeConverter __listTypeConverter = new ListTypeConverter();

  public BookDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBook = new EntityInsertionAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `book_table`(`bookid`,`bookname`,`bookpic`,`wishuser`,`owneduser`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        stmt.bindLong(1, value.getBookId());
        if (value.getBookName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBookName());
        }
        if (value.getBookPic() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBookPic());
        }
        final String _tmp;
        _tmp = __listTypeConverter.integerListToString(value.getWishUser());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __listTypeConverter.integerListToString(value.getOwnedUser());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
      }
    };
  }

  @Override
  public void insert(Book book) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfBook.insert(book);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Book> getBook(Integer bi) {
    final String _sql = "SELECT * from book_table WHERE bookid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bi == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, bi);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMBookId = _cursor.getColumnIndexOrThrow("bookid");
      final int _cursorIndexOfMBookName = _cursor.getColumnIndexOrThrow("bookname");
      final int _cursorIndexOfMBookPic = _cursor.getColumnIndexOrThrow("bookpic");
      final int _cursorIndexOfMWishUser = _cursor.getColumnIndexOrThrow("wishuser");
      final int _cursorIndexOfMOwnedUser = _cursor.getColumnIndexOrThrow("owneduser");
      final List<Book> _result = new ArrayList<Book>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Book _item;
        final String _tmpMBookName;
        _tmpMBookName = _cursor.getString(_cursorIndexOfMBookName);
        final String _tmpMBookPic;
        _tmpMBookPic = _cursor.getString(_cursorIndexOfMBookPic);
        _item = new Book(_tmpMBookName,_tmpMBookPic);
        final int _tmpMBookId;
        _tmpMBookId = _cursor.getInt(_cursorIndexOfMBookId);
        _item.setBookId(_tmpMBookId);
        final ArrayList<Integer> _tmpMWishUser;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfMWishUser);
        _tmpMWishUser = __listTypeConverter.stringToIntegerList(_tmp);
        _item.setWishUser(_tmpMWishUser);
        final ArrayList<Integer> _tmpMOwnedUser;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfMOwnedUser);
        _tmpMOwnedUser = __listTypeConverter.stringToIntegerList(_tmp_1);
        _item.setOwnedUser(_tmpMOwnedUser);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
