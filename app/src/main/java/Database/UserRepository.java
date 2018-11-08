package Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;
    private String mCurrUsername;
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

    User getUser(User u) {
        getUserAsyncTask guat = new getUserAsyncTask(mUserDao);
        guat.execute(u);
        User res = new User("no", "pw");
        try {
            res = guat.get();
            Log.d("UserRepo", res.getUsername());
            Log.d("UserRepo", Integer.toString(res.getWishList().size()));
        } catch (Exception e) {

        }
        return res;
    }

    private static class getUserAsyncTask extends AsyncTask<User, Void, User> {
        private UserDao mAsyncTaskDao;

        getUserAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(final User... params) {

            List<User> list = mAsyncTaskDao.auth(params[0].getUsername());
            if (list.size() == 0) {
                Log.d("UserRepo", "bad query");
                User u = new User("no", "pw");
                return u;
            }
            return list.get(0);
        }
    }

    User getUserById(User u) {
        getUserByIdAsyncTask guat = new getUserByIdAsyncTask(mUserDao);
        guat.execute(u);
        User res = new User("no", "pw");
        try {
            res = guat.get();
            Log.d("UserRepo", res.getUsername());
            Log.d("UserRepo", Integer.toString(res.getWishList().size()));
        } catch (Exception e) {

        }
        return res;
    }

    private static class getUserByIdAsyncTask extends AsyncTask<User, Void, User> {
        private UserDao mAsyncTaskDao;

        getUserByIdAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(final User... params) {

            List<User> list = mAsyncTaskDao.getUserById(params[0].getUserId());
            if (list.size() == 0) {
                Log.d("UserRepo", "bad query");
                User u = new User("no", "pw");
                return u;
            }
            return list.get(0);
        }
    }

    String getCurrUsername () {
        return this.mCurrUsername;
    }


    Boolean login(User u) {
        loginAsyncTask aat = new loginAsyncTask(mUserDao);
        aat.execute(u);
        Boolean res = false;
        try{
            res = aat.get();
        }catch(Exception e){

        }

        if (res) {
            mCurrUsername = u.getUsername();
            mCurrUser = mUserDao.getCurrUser(u.getUsername());
            Log.d("userRepo", mCurrUsername);
        }
        return res;
    }

    private static class loginAsyncTask extends AsyncTask<User, Void, Boolean> {
        private UserDao mAsyncTaskDao;

        loginAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            List<User> list = mAsyncTaskDao.auth(params[0].getUsername());
            if (list.size() == 0) {
                return false;
            }
            if (list.get(0).getPassword().compareTo(params[0].getPassword()) == 0){
                return true;
            }
            return false;
        }

    }

    Boolean register(User u) {
        authAsyncTask aat = new authAsyncTask(mUserDao);
        aat.execute(u);
        Boolean res = false;
        try{
            res = aat.get();
        }catch(Exception e){

        }

        if (res) {
            mCurrUsername = u.getUsername();
            mCurrUser = mUserDao.getCurrUser(u.getUsername());
        }

        return res;
    }

    private static class authAsyncTask extends AsyncTask<User, Void, Boolean> {
        private UserDao mAsyncTaskDao;

        authAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            List<User> list = mAsyncTaskDao.auth(params[0].getUsername());
            if (list.size() != 0) {
                return false;
            }
            return true;
        }

    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(User user) {
        insertAsyncTask iat = new insertAsyncTask(mUserDao);
        iat.execute(user);
        try{
            iat.get();
        } catch (Exception e) {

        }
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