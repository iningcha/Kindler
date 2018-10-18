package Database;

import android.arch.persistence.room.ColumnInfo;

public class Profile {
    @ColumnInfo(name = "name")
    public String mProfileName;

    @ColumnInfo(name = "bio")
    public String mProfileBiography;

    @ColumnInfo(name = "pic")
    public String mProfilePicture;

    //getters
    public String getProfileName(){return this.mProfileName;}
    public String getProfileBiography(){return this.mProfileBiography;}
    public String getProfilePicture(){return this.mProfilePicture;}

    //setters
    public void setProfileName(String pn){this.mProfileName = pn;}
    public void setProfileBiography(String pb){this.mProfileBiography = pb;}
    public void setProfilePicture(String pp){this.mProfilePicture = pp;}
}
