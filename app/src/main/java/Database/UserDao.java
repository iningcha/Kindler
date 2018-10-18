package Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * from user_table ORDER BY userid ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * from user_table WHERE username = :un AND password = :pw")
    List<User> auth(String un, String pw);

    @Query("SELECT * from user_table WHERE username = :un LIMIT 1")
    LiveData<User> getCurrUser(String un);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    //@Query("DELETE FROM user_table")
    //void deleteAll();
}
