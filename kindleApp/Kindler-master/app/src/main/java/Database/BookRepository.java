package Database;

import android.app.Application;
import android.os.AsyncTask;

public class BookRepository {
    private BookDao mBookDao;

    BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        mBookDao = db.BookDao();
    }

    Book getBook(Integer bi) {
        return mBookDao.getBook(bi);
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Book book) {
        new insertAsyncTask(mBookDao).execute(book);
    }

    private static class insertAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDao mAsyncTaskDao;

        insertAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
