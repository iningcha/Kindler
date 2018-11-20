package Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MatchDao {

    @Query("SELECT * from match_table WHERE owneduser = :uid ORDER BY matchid ASC")
    List<Match> getMatchByOwner(Integer uid);

    @Query("SELECT * from match_table WHERE wishuser = :uid ORDER BY matchid ASC")
    List<Match> getMatchByWisher(Integer uid);

//    @Query("SELECT owneduser, wishuser, bookid from book_table WHERE wishuser = :uid AND owneduser IS NOT NULL")
//    List<Match> getMatchByUserId(Integer uid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Match match);
}
