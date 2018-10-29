package Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "book_table", indices = {@Index(value = {"bookname"}, unique = true)})
public class Book {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="bookid")
    private int mBookId;

    @NonNull
    @ColumnInfo(name="bookname")
    private String mBookName;

    @NonNull
    @ColumnInfo(name="bookpic")
    private String mBookPic;

    @ColumnInfo(name="wishuser")
    @TypeConverters(ListTypeConverter.class)
    private ArrayList<Integer> mWishUser;

    @ColumnInfo(name="owneduser")
    @TypeConverters(ListTypeConverter.class)
    private ArrayList<Integer> mOwnedUser;

    public Book(String bookName, String bookPic, ArrayList<Integer> wishUser, ArrayList<Integer> ownedUser) {
        this.mBookName = bookName;
        this.mBookPic = bookPic;
        this.mWishUser = wishUser;
        this.mOwnedUser = ownedUser;
    }

    //getters
    public int getBookId() {return this.mBookId;}
    public String getBookName() {return this.mBookName;}
    public String getBookPic() {return this.mBookPic;}
    public ArrayList<Integer> getWishUser() {return this.mWishUser; }
    public ArrayList<Integer> getOwnedUser() {return this.mOwnedUser; }

    //setters
    public void setBookId(int i) {
        this.mBookId = i;
    }
    public void addWishUser (Integer i) {
        this.mWishUser.add(i);
    }

    public void addOwnedUser (Integer i) {
        this.mOwnedUser.add(i);
    }

}
