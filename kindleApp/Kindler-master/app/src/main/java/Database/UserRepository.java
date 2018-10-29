package Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;
    private LiveData<User> mCurrUser;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.UserDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    LiveData<User> getCurrUser() {
        return mCurrUser;
    }

    boolean auth(String username, String password) {
        List<User> list = mUserDao.auth(username, password);
        if (list.size() == 0) {
            return false;
        }
        mCurrUser = mUserDao.getCurrUser(username);
        return true;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(User User) {
        new insertAsyncTask(mUserDao).execute(User);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
