package Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Embedded;

import java.util.ArrayList;

@Entity(tableName = "user_table", indices = {@Index(value = {"username"}, unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    private int mUserId;

    @NonNull
    @ColumnInfo(name = "username")
    private String mUsername;

    @NonNull
    @ColumnInfo(name="password")
    private String mPassword;

    @Embedded
    private Profile mProfile;

    @ColumnInfo(name="matches")
    private ArrayList<Integer> mMatches;

    @ColumnInfo(name="wishlist")
    private ArrayList<Integer> mWishList;

    @ColumnInfo(name="ownedlist")
    private ArrayList<Integer> mOwnedList;

    public User(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
        this.mWishList = new ArrayList<>();
        this.mOwnedList = new ArrayList<>();
        this.mMatches = new ArrayList<>();
        this.mProfile = new Profile();
    }

    //getters
    public Integer getUserId(){return this.mUserId;}
    public String getUsername(){return this.mUsername;}
    public String getPassword(){return this.mPassword;}
    public ArrayList<Integer> getMatches(){return this.mMatches;}
    public ArrayList<Integer> getWishList(){return this.mWishList;}
    public ArrayList<Integer> getOwnedList(){return this.mOwnedList;}
    public Profile getProfile(){return this.mProfile;}

    //setters
    public void setUserId(int mUserId) { this.mUserId = mUserId; }

    public void setProfile(Profile p) {
        this.mProfile.setProfileBiography(p.getProfileBiography());
        this.mProfile.setProfileName(p.getProfileName());
        this.mProfile.setProfilePicture(p.getProfilePicture());
    }

    //Matches
    public void setMatches(ArrayList<Integer> mMatches) {this.mMatches = mMatches;}
    public void addMatches(Integer i) {this.mMatches.add(i);}

    //Wishlist
    public void setWishList(ArrayList<Integer> mWishList) {this.mWishList = mWishList; }
    public void addWishList(Integer bid) {this.mWishList.add(bid);}
    //remove from WishList according to book id
    public void removeWishList(Integer bid) {
        for (int i=0; i<this.mWishList.size(); i++) {
            if (this.mWishList.get(i) == bid) {
                this.mWishList.remove(i);
            }
        }
    }

    //OwnedList
    public void setOwnedList(ArrayList<Integer> mOwnedList) {this.mOwnedList = mOwnedList;}
    public void addOwnedList(Integer i) {this.mOwnedList.add(i);}
    public void removeOwnedList(Integer bid) {
        for (int i=0; i<this.mOwnedList.size(); i++) {
            if (this.mOwnedList.get(i) == bid) {
                this.mOwnedList.remove(i);
            }
        }
    }
}

