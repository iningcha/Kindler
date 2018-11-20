package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import Database.BookRepository.tempMatch;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * from book_table WHERE bookid = :bi LIMIT 1")
    List<Book> getBook(Integer bi);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Query("SELECT owneduser, wishuser, bookid from book_table WHERE wishuser = :uid AND owneduser IS NOT NULL")
    List<tempMatch> getMatchByUserId(Integer uid);
}
