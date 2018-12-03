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
public class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final ListTypeConverter __listTypeConverter = new ListTypeConverter();

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `user_table`(`userid`,`username`,`password`,`matches`,`wishlist`,`ownedlist`,`name`,`bio`,`pic`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUserId());
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPassword());
        }
        final String _tmp;
        _tmp = __listTypeConverter.integerListToString(value.getMatches());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __listTypeConverter.integerListToString(value.getWishList());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = __listTypeConverter.integerListToString(value.getOwnedList());
        if (_tmp_2 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_2);
        }
        final Profile _tmpMProfile = value.getProfile();
        if(_tmpMProfile != null) {
          if (_tmpMProfile.mProfileName == null) {
            stmt.bindNull(7);
          } else {
            stmt.bindString(7, _tmpMProfile.mProfileName);
          }
          if (_tmpMProfile.mProfileBiography == null) {
            stmt.bindNull(8);
          } else {
            stmt.bindString(8, _tmpMProfile.mProfileBiography);
          }
          if (_tmpMProfile.mProfilePicture == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmpMProfile.mProfilePicture);
          }
        } else {
          stmt.bindNull(7);
          stmt.bindNull(8);
          stmt.bindNull(9);
        }
      }
    };
  }

  @Override
  public void insert(User user) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<User>> getAllUsers() {
    final String _sql = "SELECT * from user_table ORDER BY userid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<User>>() {
      private Observer _observer;

      @Override
      protected List<User> compute() {
        if (_observer == null) {
          _observer = new Observer("user_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMUserId = _cursor.getColumnIndexOrThrow("userid");
          final int _cursorIndexOfMUsername = _cursor.getColumnIndexOrThrow("username");
          final int _cursorIndexOfMPassword = _cursor.getColumnIndexOrThrow("password");
          final int _cursorIndexOfMMatches = _cursor.getColumnIndexOrThrow("matches");
          final int _cursorIndexOfMWishList = _cursor.getColumnIndexOrThrow("wishlist");
          final int _cursorIndexOfMOwnedList = _cursor.getColumnIndexOrThrow("ownedlist");
          final int _cursorIndexOfMProfileName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfMProfileBiography = _cursor.getColumnIndexOrThrow("bio");
          final int _cursorIndexOfMProfilePicture = _cursor.getColumnIndexOrThrow("pic");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final User _item;
            final String _tmpMUsername;
            _tmpMUsername = _cursor.getString(_cursorIndexOfMUsername);
            final String _tmpMPassword;
            _tmpMPassword = _cursor.getString(_cursorIndexOfMPassword);
            final Profile _tmpMProfile;
            if (! (_cursor.isNull(_cursorIndexOfMProfileName) && _cursor.isNull(_cursorIndexOfMProfileBiography) && _cursor.isNull(_cursorIndexOfMProfilePicture))) {
              _tmpMProfile = new Profile();
              _tmpMProfile.mProfileName = _cursor.getString(_cursorIndexOfMProfileName);
              _tmpMProfile.mProfileBiography = _cursor.getString(_cursorIndexOfMProfileBiography);
              _tmpMProfile.mProfilePicture = _cursor.getString(_cursorIndexOfMProfilePicture);
            }  else  {
              _tmpMProfile = null;
            }
            _item = new User(_tmpMUsername,_tmpMPassword);
            final Integer _tmpMUserId;
            _tmpMUserId = _cursor.getInt(_cursorIndexOfMUserId);
            _item.setUserId(_tmpMUserId);
            final ArrayList<Integer> _tmpMMatches;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMMatches);
            _tmpMMatches = __listTypeConverter.stringToIntegerList(_tmp);
            _item.setMatches(_tmpMMatches);
            final ArrayList<Integer> _tmpMWishList;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfMWishList);
            _tmpMWishList = __listTypeConverter.stringToIntegerList(_tmp_1);
            _item.setWishList(_tmpMWishList);
            final ArrayList<Integer> _tmpMOwnedList;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfMOwnedList);
            _tmpMOwnedList = __listTypeConverter.stringToIntegerList(_tmp_2);
            _item.setOwnedList(_tmpMOwnedList);
            _item.setProfile(_tmpMProfile);
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
  public List<User> auth(String un) {
    final String _sql = "SELECT * from user_table WHERE username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (un == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, un);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMUserId = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfMUsername = _cursor.getColumnIndexOrThrow("username");
      final int _cursorIndexOfMPassword = _cursor.getColumnIndexOrThrow("password");
      final int _cursorIndexOfMMatches = _cursor.getColumnIndexOrThrow("matches");
      final int _cursorIndexOfMWishList = _cursor.getColumnIndexOrThrow("wishlist");
      final int _cursorIndexOfMOwnedList = _cursor.getColumnIndexOrThrow("ownedlist");
      final int _cursorIndexOfMProfileName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfMProfileBiography = _cursor.getColumnIndexOrThrow("bio");
      final int _cursorIndexOfMProfilePicture = _cursor.getColumnIndexOrThrow("pic");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpMUsername;
        _tmpMUsername = _cursor.getString(_cursorIndexOfMUsername);
        final String _tmpMPassword;
        _tmpMPassword = _cursor.getString(_cursorIndexOfMPassword);
        final Profile _tmpMProfile;
        if (! (_cursor.isNull(_cursorIndexOfMProfileName) && _cursor.isNull(_cursorIndexOfMProfileBiography) && _cursor.isNull(_cursorIndexOfMProfilePicture))) {
          _tmpMProfile = new Profile();
          _tmpMProfile.mProfileName = _cursor.getString(_cursorIndexOfMProfileName);
          _tmpMProfile.mProfileBiography = _cursor.getString(_cursorIndexOfMProfileBiography);
          _tmpMProfile.mProfilePicture = _cursor.getString(_cursorIndexOfMProfilePicture);
        }  else  {
          _tmpMProfile = null;
        }
        _item = new User(_tmpMUsername,_tmpMPassword);
        final Integer _tmpMUserId;
        _tmpMUserId = _cursor.getInt(_cursorIndexOfMUserId);
        _item.setUserId(_tmpMUserId);
        final ArrayList<Integer> _tmpMMatches;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfMMatches);
        _tmpMMatches = __listTypeConverter.stringToIntegerList(_tmp);
        _item.setMatches(_tmpMMatches);
        final ArrayList<Integer> _tmpMWishList;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfMWishList);
        _tmpMWishList = __listTypeConverter.stringToIntegerList(_tmp_1);
        _item.setWishList(_tmpMWishList);
        final ArrayList<Integer> _tmpMOwnedList;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfMOwnedList);
        _tmpMOwnedList = __listTypeConverter.stringToIntegerList(_tmp_2);
        _item.setOwnedList(_tmpMOwnedList);
        _item.setProfile(_tmpMProfile);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<User> getCurrUser(String un) {
    final String _sql = "SELECT * from user_table WHERE username = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (un == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, un);
    }
    return new ComputableLiveData<User>() {
      private Observer _observer;

      @Override
      protected User compute() {
        if (_observer == null) {
          _observer = new Observer("user_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMUserId = _cursor.getColumnIndexOrThrow("userid");
          final int _cursorIndexOfMUsername = _cursor.getColumnIndexOrThrow("username");
          final int _cursorIndexOfMPassword = _cursor.getColumnIndexOrThrow("password");
          final int _cursorIndexOfMMatches = _cursor.getColumnIndexOrThrow("matches");
          final int _cursorIndexOfMWishList = _cursor.getColumnIndexOrThrow("wishlist");
          final int _cursorIndexOfMOwnedList = _cursor.getColumnIndexOrThrow("ownedlist");
          final int _cursorIndexOfMProfileName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfMProfileBiography = _cursor.getColumnIndexOrThrow("bio");
          final int _cursorIndexOfMProfilePicture = _cursor.getColumnIndexOrThrow("pic");
          final User _result;
          if(_cursor.moveToFirst()) {
            final String _tmpMUsername;
            _tmpMUsername = _cursor.getString(_cursorIndexOfMUsername);
            final String _tmpMPassword;
            _tmpMPassword = _cursor.getString(_cursorIndexOfMPassword);
            final Profile _tmpMProfile;
            if (! (_cursor.isNull(_cursorIndexOfMProfileName) && _cursor.isNull(_cursorIndexOfMProfileBiography) && _cursor.isNull(_cursorIndexOfMProfilePicture))) {
              _tmpMProfile = new Profile();
              _tmpMProfile.mProfileName = _cursor.getString(_cursorIndexOfMProfileName);
              _tmpMProfile.mProfileBiography = _cursor.getString(_cursorIndexOfMProfileBiography);
              _tmpMProfile.mProfilePicture = _cursor.getString(_cursorIndexOfMProfilePicture);
            }  else  {
              _tmpMProfile = null;
            }
            _result = new User(_tmpMUsername,_tmpMPassword);
            final Integer _tmpMUserId;
            _tmpMUserId = _cursor.getInt(_cursorIndexOfMUserId);
            _result.setUserId(_tmpMUserId);
            final ArrayList<Integer> _tmpMMatches;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMMatches);
            _tmpMMatches = __listTypeConverter.stringToIntegerList(_tmp);
            _result.setMatches(_tmpMMatches);
            final ArrayList<Integer> _tmpMWishList;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfMWishList);
            _tmpMWishList = __listTypeConverter.stringToIntegerList(_tmp_1);
            _result.setWishList(_tmpMWishList);
            final ArrayList<Integer> _tmpMOwnedList;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfMOwnedList);
            _tmpMOwnedList = __listTypeConverter.stringToIntegerList(_tmp_2);
            _result.setOwnedList(_tmpMOwnedList);
            _result.setProfile(_tmpMProfile);
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
    }.getLiveData();
  }
}
