package Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

@Database(entities = {Book.class}, version = 1)
@TypeConverters({ListTypeConverter.class})
public abstract class BookRoomDatabase extends RoomDatabase {

    public abstract BookDao BookDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile BookRoomDatabase INSTANCE;

    static BookRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookRoomDatabase.class, "Book_database")
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

        private final BookDao mDao;

        PopulateDbAsync(BookRoomDatabase db) {
            mDao = db.BookDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //mDao.deleteAll();

            Book book = new Book("The Way of the Kings", "https://upload.wikimedia.org/wikipedia/en/8/8b/TheWayOfKings.png");
            mDao.insert(book);
            Log.d("BookRoomDatabaseLog", "Book Id" + Integer.toString(book.getBookId()));
            book = new Book("The Lord of The Rings", "https://vignette.wikia.nocookie.net/lotr/images/d/db/51eq24cRtRL._SX331_BO1%2C204%2C203%2C200_.jpg/revision/latest/scale-to-width-down/220?cb=20160701231234");
            mDao.insert(book);
            book = new Book("The Martian", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7onMDKEa8nLOVMnI1iCSDIooR7ZGhEJ4tTfQyD4v99TnDwsK9");
            mDao.insert(book);
            book = new Book("The Chronicles of Narnia", "https://images.gr-assets.com/books/1357470998l/6753727.jpg");
            mDao.insert(book);
            book = new Book("Harry Potter and the Socerer's Stone", "https://am23.akamaized.net/tms/cnt/uploads/2017/06/harry-potter-and-the-sorcerers-stone-222x300.jpg");
            mDao.insert(book);
            book = new Book("The Fountainhead", "https://images-na.ssl-images-amazon.com/images/I/510VwbbHZkL._SX303_BO1,204,203,200_.jpg");
            mDao.insert(book);
            book = new Book("Ender's Game", "https://prodimage.images-bn.com/pimages/9780765342294_p0_v1_s550x406.jpg");
            mDao.insert(book);
            book = new Book("Artemis", "http://www.mymbuzz.com/wp-content/uploads/sites/2/2017/06/artemis-jacket-fullsize.jpg");
            mDao.insert(book);
            book = new Book("Mistborn", "https://images-na.ssl-images-amazon.com/images/I/91VZMbqbuZL.jpg");
            mDao.insert(book);

            return null;
        }
    }
}