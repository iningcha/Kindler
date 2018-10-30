package com.example.kindler;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.arch.lifecycle.ViewModelProviders;
import static org.junit.Assert.assertEquals;
import Database.User;
import Database.MatchDao;
import Database.UserViewModel;
import Database.UserRepository;

/**
 * Created by samanthachang on 10/29/18.
 */

public class MatchListUnitTest {

    private Match myMatch = new Match();
    private UserViewModel mUserViewModel = ViewModelProviders.of(myMatch).get(UserViewModel.class);


    @Test
    public void testMatchListSize() throws Exception {
        assertEquals(myMatch.getMatchListSize(), mUserViewModel.getMatches().size());
    }


}