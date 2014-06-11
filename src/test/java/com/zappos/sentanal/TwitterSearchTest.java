package com.zappos.sentanal;

import twitter4j.Status;

import java.util.List;

/**
 * Created by maxkeene on 6/11/14.
 */
public class TwitterSearchTest {
    public void testSearchTweets() {
        TwitterSearch twitterSearch = new TwitterSearch();
        List<Status> results = twitterSearch.searchTweets("zappos", 10);
        for (Status result : results) {
            System.out.println(result.getText());
        }
    }
}
