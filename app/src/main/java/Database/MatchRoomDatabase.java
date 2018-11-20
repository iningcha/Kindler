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

@Database(entities = {Match.class}, version = 2)
@TypeConverters({ListTypeConverter.class})
public abstract class MatchRoomDatabase extends RoomDatabase {

    public abstract MatchDao MatchDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MatchRoomDatabase INSTANCE;

    static MatchRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MatchRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MatchRoomDatabase.class, "match_database")
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

        private final MatchDao mDao;

        PopulateDbAsync(MatchRoomDatabase db) {
            mDao = db.MatchDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //mDao.deleteAll();
            ArrayList<Integer> a = new ArrayList<>();

            Match Match = new Match(1, 1, 2);
            Match match2 = new Match(7, 1, 2);
            Match match3 = new Match(9, 1, 2);
            Match match4 = new Match(3, 2, 1);
            Match match5 = new Match(2, 2, 1);
            Match match6 = new Match(6, 2, 1);

            mDao.insert(Match);
            mDao.insert(match2);
            mDao.insert(match3);
            mDao.insert(match4);
            mDao.insert(match5);
            mDao.insert(match6);


            return null;
        }
    }
}