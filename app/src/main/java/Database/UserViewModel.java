package Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends AndroidViewModel{
    private UserRepository mUserRepository;
    private BookRepository mBookRepository;
    private MatchRepository mMatchRepository;

    // Using LiveData and caching what getAlphabetizedUsers returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private static String mCurrUsername;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mBookRepository = new BookRepository(application);
        mMatchRepository = new MatchRepository(application);
    }

    public List<User> getAllUser() { return mUserRepository.getAllUser(); }

    public boolean login(String username, String password) {
        User u = new User(username, password);
        if (mUserRepository.login(u)) {
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
        mCurrUsername = username;
        return true;
    }

    //Getters
    public List<Integer> getWishlist() {
        User u = new User(mCurrUsername, "pw");
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

    //Get User by username
    public User getUser(String un) {
        User u = new User(un, "u");
        return mUserRepository.getUser(u);
    }

    //Get User by userid
    public User getUserById(Integer i) {
        User u = new User("u", "u");
        u.setUserId(i);
        return mUserRepository.getUserById(u);
    }

    public int getCurrUserId() {
        User u = new User(mCurrUsername, "pw");
        return mUserRepository.getUser(u).getUserId();
    }

    //Can be used to update user
    public void insertUser(User user) {
        mUserRepository.insert(user);
    }

    public void setProfile(Profile p) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        u.setProfile(p);
        mUserRepository.insert(u);
        Log.d("UserViewModelLog", "set profile");
    }

    public void addWishList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        for (int j=0; j<u.getWishList().size(); j++) {
            if (u.getWishList().get(j) == i) {
                return;
            }
        }
        u.addWishList(i);
        mUserRepository.insert(u);
        Log.d("UserViewModelLog", "add wish list");
    }

    public void addOwnedList(Integer i) {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        for (int j=0; j<u.getOwnedList().size(); j++) {
            if (u.getWishList().get(j) == i) {
                return;
            }
        }
        u.addOwnedList(i);
        mUserRepository.insert(u);
    }

    //Remove book from wishlist for current user
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
    public void insertBook(Book b) {
        mBookRepository.insert(b);
    }

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

    public void generateMatch() {
        mMatchRepository.deleteTable();
        List<User> users = mUserRepository.getAllUser();
        Log.d("UserViewModel", "generateMatch");
        for (User u : users) {
            for (User v : users) {
                if (u != v) {
                    for (Integer i : u.getOwnedList()) {
                        for (Integer j : v.getWishList()) {
                            Log.d("UserViewModel", "compare");
                            if (i == j) {
                                Log.d("UserViewModel", "create Match ");
                                Match m = new Match(i, u.getUserId(), v.getUserId());
                                mMatchRepository.insert(m);
                            }
                        }
                    }
                }
            }
        }

        /*
        List<Book> list = mBookRepository.getAllBook();
        for (Book b : list) {
            if (b.getOwnedUser().size() > 0 && b.getWishUser().size() > 0) {
                for (Integer i : b.getOwnedUser()) {
                    for (Integer j : b.getWishUser()) {
                        if (mMatchRepository.checkMatch(i, j).size() == 0) {
                            Match m = new Match(b.getBookId(), i, j);
                            mMatchRepository.insert(m);
                        }
                    }
                }
            }
        }
        */
    }

    //Match
    public List<Match> getMatchByOwner(int uid) {
        return mMatchRepository.getMatchByOwner(uid);
    }

    public List<Match> getMatchByWisher(int uid) {
        return mMatchRepository.getMatchByWisher(uid);
    }


    public List<Match> getMatchByUserId() {
        User temp = new User(mCurrUsername, "pw");
        User u = mUserRepository.getUser(temp);
        return mBookRepository.getMatchById(u);
    }

//    public void updateMatchStatus(int mid) {
//        mMatchRepository.updateStatus(mid);
//    }
}