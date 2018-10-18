package Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel{
    private UserRepository mUserRepository;
    private BookRepository mBookRepository;
    private MatchRepository mMatchRepository;

    // Using LiveData and caching what getAlphabetizedUsers returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<User>> mAllUsers;
    private LiveData<User> mCurrUser;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mBookRepository = new BookRepository(application);
        mMatchRepository = new MatchRepository(application);
        mAllUsers = mUserRepository.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() { return mAllUsers; }

    LiveData<User> getCurrUser() {
        return mCurrUser;
    }

    boolean login(String username, String password) {
        if (mUserRepository.auth(username, password)) {
            mCurrUser = mUserRepository.getCurrUser();
            return true;
        }
        return false;
    }

    boolean register(String username, String password) {
        if (mUserRepository.auth(username, password)) {
            //already registered
            return false;
        }
        User user = new User(username, password);
        insertUser(user);
        mCurrUser = mUserRepository.getCurrUser();
        return true;
    }

    void logout () {
        mCurrUser = null;
    }

    List<Integer> getWishlist() {
        return mCurrUser.getValue().getWishList();
    }
    List<Integer> getOwnedlist() {
        return mCurrUser.getValue().getOwnedList();
    }
    Profile getProfile() {
        return mCurrUser.getValue().getProfile();
    }
    List<Integer> getMatches() {
        return mCurrUser.getValue().getMatches();
    }

    //Can be used to update user
    void insertUser(User user) {
        mUserRepository.insert(user);
    }

    void addWishList(Integer i) {
        User u = mCurrUser.getValue();
        u.addWishList(i);
        mUserRepository.insert(u);
    }

    void addOwnedList(Integer i) {
        User u = mCurrUser.getValue();
        u.addOwnedList(i);
        mUserRepository.insert(u);
    }

    void removeWishList(Integer i) {
        User u = mCurrUser.getValue();
        u.removeWishList(i);
        mUserRepository.insert(u);
    }

    void removeOwnedList(Integer i) {
        User u = mCurrUser.getValue();
        u.removeOwnedList(i);
        mUserRepository.insert(u);
    }

    void setMatches(Integer i) {
        User u = mCurrUser.getValue();
        u.addMatches(i);
        mUserRepository.insert(u);
    }

    void setProfile(Profile p) {
        User u = mCurrUser.getValue();
        u.setProfile(p);
        mUserRepository.insert(u);
    }

    //Book
    Book getBook(Integer i) {
        return mBookRepository.getBook(i);
    }

    void addWishUser(Integer bid, Integer uid) {
        mBookRepository.getBook(bid).addWishUser(uid);
    }

    void addOwnedUser(Integer bid, Integer uid) {
        mBookRepository.getBook(bid).addOwnedUser(uid);
    }

    //Match
    LiveData<List<Match>> getMatchByOwner(int uid) {
        return mMatchRepository.getMatchByOwner(uid);
    }

    LiveData<List<Match>> getMatchByWisher(int uid) {
        return mMatchRepository.getMatchByWisher(uid);
    }

    void updateMatchStatus(int mid) {
        mMatchRepository.updateStatus(mid);
    }
}
