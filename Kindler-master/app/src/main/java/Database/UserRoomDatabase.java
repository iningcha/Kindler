package Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Database(entities = {User.class}, version = 1)
@TypeConverters({ListTypeConverter.class})
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao UserDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile UserRoomDatabase INSTANCE;

    static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "User_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more Users, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;

        PopulateDbAsync(UserRoomDatabase db) {
            mDao = db.UserDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //mDao.deleteAll();


            User u = new User("test@t.com", "t");
            Profile p = new Profile();
            p.setProfileName("s");
            p.setProfileBiography("obsessed with fantasy");
            p.setProfilePicture("");
            u.setProfile(p);
            u.addOwnedList(0);
            u.addOwnedList(1);
            u.addOwnedList(2);
            u.addOwnedList(3);
            u.addOwnedList(4);
            u.addOwnedList(5);
            u.addOwnedList(6);
            mDao.insert(u);

            /*
            User = new User("c@c.c", "cccc");
            mDao.insert(User);
            User = new User("d@d.d", "abcd");
            mDao.insert(User);
            */
            return null;
        }
    }
}