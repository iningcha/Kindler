package com.example.kindler;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kindler.adapters.BookCardAdapter;
import com.example.kindler.models.Book;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Database.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserViewModel mUserViewModel;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        setContentView(R.layout.activity_main);
        setup();
        reload();
        Toolbar mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_search:
                Intent intentsearch = new Intent(this, Search.class);
                this.startActivity(intentsearch);
                break;
            case R.id.menu_profile:
                Intent intentprofile = new Intent(this, Profile.class);
                this.startActivity(intentprofile);
                break;
            case R.id.menu_booklist:
                Intent intentbooklist = new Intent(this, BookListActivity.class);
                this.startActivity(intentbooklist);
                break;
            case R.id.menu_wishlist:
                Intent intentwishlist = new Intent(this,WishListActivity.class);
                this.startActivity(intentwishlist);
                break;
            case R.id.menu_matches:
                Intent intentmatches = new Intent(this, Match.class);
                this.startActivity(intentmatches);
                break;
            case R.id.menu_logout:
                Toast.makeText(getApplicationContext(), "Logged Out!" , Toast.LENGTH_SHORT ).show();
                Intent intentlogout = new Intent(this, LoginActivity.class);
                this.startActivity(intentlogout);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private BookCardAdapter adapter;

    private Book createBook() {
        return new Book("Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800");
    }

    private List<Book> createBooks() {
        List<Book> spots = new ArrayList<>();
        spots.add(new Book("The Way of The Kings", "Brandon Sanderson", "https://upload.wikimedia.org/wikipedia/en/8/8b/TheWayOfKings.png"));
        spots.add(new Book("The Lord of The Rings", "J.R.R. Tolkien", "https://vignette.wikia.nocookie.net/lotr/images/d/db/51eq24cRtRL._SX331_BO1%2C204%2C203%2C200_.jpg/revision/latest/scale-to-width-down/220?cb=20160701231234"));
        spots.add(new Book("The Martian", "Andy Weir", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7onMDKEa8nLOVMnI1iCSDIooR7ZGhEJ4tTfQyD4v99TnDwsK9"));
        spots.add(new Book("The Chronicles of Narnia", "C.S. Lewis", "https://images.gr-assets.com/books/1357470998l/6753727.jpg"));
        spots.add(new Book("Harry Potter and the Socerer's Stone", "J. K. Rowling", "https://am23.akamaized.net/tms/cnt/uploads/2017/06/harry-potter-and-the-sorcerers-stone-222x300.jpg"));
        spots.add(new Book("The Fountainhead", "Ayn Rand", "https://images-na.ssl-images-amazon.com/images/I/510VwbbHZkL._SX303_BO1,204,203,200_.jpg"));
        spots.add(new Book("Ender's Game", "Orson Scott Card", "https://prodimage.images-bn.com/pimages/9780765342294_p0_v1_s550x406.jpg"));
        spots.add(new Book("Artemis", "Andy Weir", "http://www.mymbuzz.com/wp-content/uploads/sites/2/2017/06/artemis-jacket-fullsize.jpg"));
        spots.add(new Book("Mistborn", "Brandon Sanderson", "https://images-na.ssl-images-amazon.com/images/I/91VZMbqbuZL.jpg"));

        return spots;
    }

    private BookCardAdapter createBookCardAdapter() {
        final BookCardAdapter adapter = new BookCardAdapter(getApplicationContext());
        adapter.addAll(createBooks());
        return adapter;
    }

    private void setup() {
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        cardStackView = (CardStackView) findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (direction.toString() == "Right") {
                    int uid = mUserViewModel.getCurrUserId();
                    mUserViewModel.addWishList(cardStackView.getTopIndex());
                    mUserViewModel.addWishUser(cardStackView.getTopIndex(), uid);
                    Toast.makeText(getApplicationContext(), "Added book to Wishlist!" , Toast.LENGTH_SHORT ).show();
                    Log.d("MainActivityLog", "Add book to WishList " + Integer.toString(cardStackView.getTopIndex()));
                }

                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });
    }

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createBookCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private LinkedList<Book> extractRemainingBooks() {
        LinkedList<Book> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }

    private void addFirst() {
        LinkedList<Book> spots = extractRemainingBooks();
        spots.addFirst(createBook());
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void addLast() {
        LinkedList<Book> spots = extractRemainingBooks();
        spots.addLast(createBook());
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void removeFirst() {
        LinkedList<Book> spots = extractRemainingBooks();
        if (spots.isEmpty()) {
            return;
        }

        spots.removeFirst();
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void removeLast() {
        LinkedList<Book> spots = extractRemainingBooks();
        if (spots.isEmpty()) {
            return;
        }

        spots.removeLast();
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createBooks());
        adapter.notifyDataSetChanged();
    }

    public void swipeLeft() {
        List<Book> spots = extractRemainingBooks();
        if (spots.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);
        count++;
        if (count == spots.size()) {
            count = 0;
        }
        cardStackView.swipe(SwipeDirection.Left, cardAnimationSet, overlayAnimationSet);
    }

    public void swipeRight() {
        List<Book> spots = extractRemainingBooks();
        if (spots.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        cardStackView.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet);
    }

}
