package Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "match_table")
public class Match {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matchid")
    private int mMatchId;

    @ColumnInfo(name = "owner")
    private int mMatchOwner;

    @ColumnInfo(name = "wisher")
    private int mMatchWisher;

    @ColumnInfo(name = "bookid")
    private int mMatchBookId;

    @ColumnInfo(name = "status")
    private boolean mMatchStatus;

    public Match() {}

    public Match(int bookid, int owner, int wisher) {
        this.mMatchBookId = bookid;
        this.mMatchOwner = owner;
        this.mMatchWisher = wisher;
        this.mMatchStatus = false;
    }

    //getters
    public int getMatchId() {return this.mMatchId;}
    public int getMatchOwner() {return this.mMatchOwner;}
    public int getMatchWisher() {return this.mMatchWisher;}
    public boolean getMatchStatus() {return this.mMatchStatus;}
    public int getMatchBookId() {return this.mMatchBookId;}

    //setters
    public void setMatchId(int i) {
        this.mMatchId = i;
    }
    public void setMatchOwner(int i) {this.mMatchOwner = i;}
    public void setMatchWisher(int i) {this.mMatchWisher = i;}
    public void setMatchBookId(int i) {this.mMatchBookId = i;}
    public void setMatchStatus(boolean b) {
        this.mMatchStatus = b;
    }
}
