package Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class MatchRepository {
    private MatchDao mMatchDao;

    MatchRepository(Application application) {
        MatchRoomDatabase db = MatchRoomDatabase.getDatabase(application);
        mMatchDao = db.MatchDao();
    }

    LiveData<List<Match>> getMatchByOwner(Integer uid) {
        return mMatchDao.getMatchByOwner(uid);
    }

    LiveData<List<Match>> getMatchByWisher(Integer uid) {
        return mMatchDao.getMatchByWisher(uid);
    }

    Match getMatchById(Match m){
        MatchRepository.getMatchByIdAsyncTask guat = new MatchRepository.getMatchByIdAsyncTask(mMatchDao);
        guat.execute(m);
        Match res = new Match();
        try{
            res = guat.get();

        } catch(Exception e) {

        }
        return res;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Match Match) {
        new insertAsyncTask(mMatchDao).execute(Match);
    }

    void updateStatus(int mid) {
        Match m = mMatchDao.getMatchById(mid);
        m.setMatchStatus(true);
        mMatchDao.insert(m);
    }

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

    private static class getMatchByIdAsyncTask extends AsyncTask<Match, Void, Match> {
        private MatchDao mAsyncTaskDao;

        getMatchByIdAsyncTask(MatchDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Match doInBackground(final Match... params) {

            return mAsyncTaskDao.getMatchById(params[0].getMatchId());
        }
    }

}
