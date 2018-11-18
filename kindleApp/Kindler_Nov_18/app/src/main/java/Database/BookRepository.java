package Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class BookRepository {
    private BookDao mBookDao;

    BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        mBookDao = db.BookDao();
    }

    Book getBook(Book b) {
        BookRepository.getBookAsyncTask guat = new BookRepository.getBookAsyncTask(mBookDao);
        //Log.d("UserRepo", u.getUsername());
        guat.execute(b);
        Book res = new Book("fake", "pic");
        try {
            res = guat.get();
        } catch (Exception e) {

        }
        return res;
    }

    private static class getBookAsyncTask extends AsyncTask<Book, Void, Book> {
        private BookDao mAsyncTaskDao;

        getBookAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Book doInBackground(final Book... params) {
            List<Book> list = mAsyncTaskDao.getBook(params[0].getBookId());
            Log.d("BookRepoLog", Integer.toString(params[0].getBookId()));
            if (list.size() == 0) {
                Log.d("BookRepoLog", "bad query");
                Book b = new Book("no", "pic");
                return b;
            }
            return list.get(0);
        }
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
