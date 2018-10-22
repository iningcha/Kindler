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

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public LiveData<User> getCurrUser() {
        return mCurrUser;
    }

    public boolean login(String username, String password) {
        if (mUserRepository.auth(username, password)) {
            mCurrUser = mUserRepository.getCurrUser();
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {
        if (mUserRepository.auth(username, password)) {
            //already registered
            return false;
        }
        User user = new User(username, password);
        insertUser(user);
        mCurrUser = mUserRepository.getCurrUser();
        return true;
    }

    public void logout () {
        mCurrUser = null;
    }

    public List<Integer> getWishlist() {
        return mCurrUser.getValue().getWishList();
    }
    public List<Integer> getOwnedlist() {
        return mCurrUser.getValue().getOwnedList();
    }
    public Profile getProfile() {
        return mCurrUser.getValue().getProfile();
    }
    public List<Integer> getMatches() {
        return mCurrUser.getValue().getMatches();
    }

    //Can be used to update user
    public void insertUser(User user) {
        mUserRepository.insert(user);
    }

    public void addWishList(Integer i) {
        User u = mCurrUser.getValue();
        u.addWishList(i);
        mUserRepository.insert(u);
    }

    public void addOwnedList(Integer i) {
        User u = mCurrUser.getValue();
        u.addOwnedList(i);
        mUserRepository.insert(u);
    }

    public void removeWishList(Integer i) {
        User u = mCurrUser.getValue();
        u.removeWishList(i);
        mUserRepository.insert(u);
    }

    public void removeOwnedList(Integer i) {
        User u = mCurrUser.getValue();
        u.removeOwnedList(i);
        mUserRepository.insert(u);
    }

    public void setMatches(Integer i) {
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
    public Book getBook(Integer i) {
        return mBookRepository.getBook(i);
    }

    public void addWishUser(Integer bid, Integer uid) {
        mBookRepository.getBook(bid).addWishUser(uid);
    }

    public void addOwnedUser(Integer bid, Integer uid) {
        mBookRepository.getBook(bid).addOwnedUser(uid);
    }

    //Match
    public LiveData<List<Match>> getMatchByOwner(int uid) {
        return mMatchRepository.getMatchByOwner(uid);
    }

    public LiveData<List<Match>> getMatchByWisher(int uid) {
        return mMatchRepository.getMatchByWisher(uid);
    }

    public void updateMatchStatus(int mid) {
        mMatchRepository.updateStatus(mid);
    }
}
