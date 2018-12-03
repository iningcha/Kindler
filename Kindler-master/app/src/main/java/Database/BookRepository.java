package Database;

import android.app.Application;
import android.arch.persistence.room.ColumnInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
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
    //Retrieve list of all books
    public List<Book> getAllBook() {
        getAllBookAsyncTask guat = new getAllBookAsyncTask(mBookDao);
        guat.execute();
        List<Book> res = new ArrayList<>();
        try {
            res = guat.get();
        } catch (Exception e) {

        }
        return res;
    }

    private static class getAllBookAsyncTask extends AsyncTask<Void, Void, List<Book>> {
        private BookDao mAsyncTaskDao;

        getAllBookAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Book> doInBackground(final Void... sth) {
            return mAsyncTaskDao.getAllBook();
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

    List<Match> getMatchById(User u){
        BookRepository.getMatchByIdAsyncTask guat = new BookRepository.getMatchByIdAsyncTask(mBookDao);
        guat.execute(u);
        List<Match> res = new ArrayList<>();
        try{
            res = guat.get();

        } catch(Exception e) {

        }
        return res;
    }

    public static  class tempMatch {
        @ColumnInfo(name = "owneduser")
        public int owneduser;

        @ColumnInfo(name = "wishuser")
        public int wishuser;

        @ColumnInfo(name = "bookid")
        public int bookid;

        public int getOwneduser() { return owneduser; }
        public int getWishuser() { return wishuser; }
        public int getBookid() { return bookid; }
    }


    private static class getMatchByIdAsyncTask extends AsyncTask<User, Void, List<Match>> {
        private BookDao mAsyncTaskDao;

        getMatchByIdAsyncTask(BookDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected List<Match> doInBackground(final User... params) {
            List<Match> res = new ArrayList<>();
            List<tempMatch> list = mAsyncTaskDao.getMatchByUserId(params[0].getUserId());
            Log.d(" this tempMatch shit", Integer.toString(list.size()));
            for(int i = 0; i < list.size(); i++){
                Match m = new Match();
                m.setMatchWisher(list.get(i).getWishuser());
                m.setMatchOwner(list.get(i).getOwneduser());
                m.setMatchBookId(list.get(i).getBookid());
                res.add(m);
            }
            return res;

        }
    }
}
