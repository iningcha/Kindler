package Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

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
    private User mCurrUserOne;
    private static String mCurrUsername;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mBookRepository = new BookRepository(application);
        mMatchRepository = new MatchRepository(application);
        mAllUsers = mUserRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public LiveData<User> getCurrUser() {
        return mUserRepository.getCurrUser();
    }

    public boolean login(String username, String password) {
        User u = new User(username, password);
        if (mUserRepository.login(u)) {
            mCurrUserOne = u;
            mCurrUser = mUserRepository.getCurrUser();
            this.mCurrUsername = username;
            Log.d("UserViewModel", "set " + mCurrUsername);
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {
        User u = new User(username, password);
        if (!mUserRepository.register(u)) {
            // already registered
            return false;
        }
        User user = new User(username, password);
        insertUser(user);
        mCurrUserOne = user;
        mCurrUser = mUserRepository.getCurrUser();
        mCurrUsername = username;
        return true;
    }

    public void logout () {
        mCurrUser = null;
    }

    //Getters
    public List<Integer> getWishlist() {
        User u = new User(mCurrUsername, "pw");
        Log.d("UserViewModel", mCurrUsername);
        return mUserRepository.getUser(u).getWishList();
    }
    public List<Integer> getOwnedlist() {
        User u = new User(mCurrUsername, "pw");
        return mUserRepository.getUser(u).getOwnedList();
    }

    public List<Integer> getMatches() {
        User u = new User(mCurrUsername, "pw");
        return mUserRepository.getUser(u).getMatches();
    }

    public Profile getCurrProfile() {
        User u = new User(mCurrUsername, "pw");
        return mUserRepository.getUser(u).getProfile();
    }

    //Can be used to update user
    public void insertUser(User user) {
        mUserRepository.insert(user);
    }

    public void addWishList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.addWishList(i);
        mUserRepository.insert(u);
    }

    public void addOwnedList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.addOwnedList(i);
        mUserRepository.insert(u);
    }

    public void removeWishList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.removeWishList(i);
        mUserRepository.insert(u);
    }

    public void removeOwnedList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.removeOwnedList(i);
        mUserRepository.insert(u);
    }

    public void setMatches(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.addMatches(i);
        mUserRepository.insert(u);
    }

    //Book
    public Book getBook(Integer i) {
        Book b = new Book("b", "b");
        b.setBookId(i);
        return mBookRepository.getBook(b);
    }

    public void addWishUser(Integer bid, Integer uid) {
        Book b = new Book("b", "b");
        b.setBookId(bid);
        mBookRepository.getBook(b).addWishUser(uid);
    }

    public void addOwnedUser(Integer bid, Integer uid) {
        Book b = new Book("b", "b");
        b.setBookId(bid);
        mBookRepository.getBook(b).addOwnedUser(uid);
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
