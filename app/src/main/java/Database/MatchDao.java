package Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface    MatchDao {

    @Query("SELECT * from match_table WHERE owner = :uid ORDER BY matchid ASC")
    LiveData<List<Match>> getMatchByOwner(Integer uid);

    @Query("SELECT * from match_table WHERE wisher = :uid ORDER BY matchid ASC")
    LiveData<List<Match>> getMatchByWisher(Integer uid);

    @Query("SELECT * from match_table WHERE matchid = :mid LIMIT 1")
    Match getMatchById(Integer mid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Match match);
}
