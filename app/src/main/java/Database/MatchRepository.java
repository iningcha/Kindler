package Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MatchRepository {
    private MatchDao mMatchDao;

    MatchRepository(Application application) {
        MatchRoomDatabase db = MatchRoomDatabase.getDatabase(application);
        mMatchDao = db.MatchDao();
    }

    List<Match> getMatchByOwner(Integer uid) {
        getMatchByOwnerAsyncTask guat = new getMatchByOwnerAsyncTask(mMatchDao);
        guat.execute(uid);
        List<Match> res = new ArrayList<>();
        try {
            res = guat.get();
        } catch (Exception e) {

        }
        return res;
    }

    private static class getMatchByOwnerAsyncTask extends AsyncTask<Integer, Void, List<Match>> {
        private MatchDao mAsyncTaskDao;

        getMatchByOwnerAsyncTask(MatchDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Match> doInBackground(final Integer... i) {
            return mAsyncTaskDao.getMatchByOwner(i[0]);
        }
    }


    List<Match> getMatchByWisher(Integer uid) {
        getMatchByWisherAsyncTask guat = new getMatchByWisherAsyncTask(mMatchDao);
        guat.execute(uid);
        List<Match> res = new ArrayList<>();
        try {
            res = guat.get();
        } catch (Exception e) {

        }
        return res;
    }

    private static class getMatchByWisherAsyncTask extends AsyncTask<Integer, Void, List<Match>> {
        private MatchDao mAsyncTaskDao;

        getMatchByWisherAsyncTask(MatchDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Match> doInBackground(final Integer... i) {
            return mAsyncTaskDao.getMatchByWisher(i[0]);
        }
    }

//    List<Match> getMatchById(User u){
//        MatchRepository.getMatchByIdAsyncTask guat = new MatchRepository.getMatchByIdAsyncTask(mMatchDao);
//        guat.execute(u);
//        List<Match> res = new ArrayList<>();
//        try{
//            res = guat.get();
//
//        } catch(Exception e) {
//
//        }
//        return res;
//    }

    List<Match> checkMatch(Integer ownedid, Integer wishid) {
        checkMatchAsyncTask guat = new checkMatchAsyncTask(mMatchDao);
        guat.execute(ownedid, wishid);
        List<Match> res = new ArrayList<>();
        try {
            res = guat.get();
        } catch (Exception e) {

        }
        return res;
    }

    private static class checkMatchAsyncTask extends AsyncTask<Integer, Void, List<Match>> {
        private MatchDao mAsyncTaskDao;

        checkMatchAsyncTask(MatchDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Match> doInBackground(final Integer... i) {
            return mAsyncTaskDao.checkMatch(i[0], i[1]);
        }
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Match Match) {
        new insertAsyncTask(mMatchDao).execute(Match);
    }

//    void updateStatus(int mid) {
//        List<Match> m = mMatchDao.getMatchByUserId(mid);
//        mMatchDao.insert(m);
//    }

    private static class insertAsyncTask extends AsyncTask<Match, Void, Void> {

        private MatchDao mAsyncTaskDao;

        insertAsyncTask(MatchDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Match... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

//    private static class getMatchByIdAsyncTask extends AsyncTask<User, Void, List<Match>> {
//        private MatchDao mAsyncTaskDao;
//
//        getMatchByIdAsyncTask(MatchDao dao) { mAsyncTaskDao = dao; }
//
//        @Override
//        protected List<Match> doInBackground(final User... params) {
//
//            List<Match> list = mAsyncTaskDao.getMatchByUserId(params[0].getUserId());
//            return list;
//
//        }
//    }

}
