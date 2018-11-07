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

    @Query("SELECT * from user_table WHERE username = :un")
    List<User> auth(String un);

    @Query("SELECT * from user_table WHERE username = :un LIMIT 1")
    LiveData<User> getCurrUser(String un);

    @Query("SELECT * from user_table WHERE userid = :un")
    User getUser(int un);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);


    //@Query("DELETE FROM user_table")
    //void deleteAll();
}
