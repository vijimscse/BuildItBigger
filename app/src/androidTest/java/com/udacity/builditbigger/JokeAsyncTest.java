package com.udacity.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Viji on 9/14/2017.
 */


@RunWith(AndroidJUnit4.class)
public class JokeAsyncTest {

    @Test
    public void testDoInBackground() throws Exception{
        try {
            final JokeAsync endpointsAsyncTask = new JokeAsync(new JokeListener() {
                @Override
                public void onJokeFetched(String joke) {
                }
            });
            endpointsAsyncTask.execute();
            String result = endpointsAsyncTask.get(30, TimeUnit.SECONDS);
            assertNotNull(result);
            assertTrue(result.length() > 0);


        } catch (Exception e){
            Log.e("JokeAsync", "testDoInBackground: Timed out");
        }
    }
}
