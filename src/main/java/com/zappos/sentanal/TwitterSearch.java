package com.zappos.sentanal;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxkeene on 6/11/14.
 */
public class TwitterSearch {
    private Twitter twitter = TwitterFactory.getSingleton();

    public List<Status> searchTweets(String queryString, int limit) {
        Query query = new Query();
        query.setQuery(queryString);
        List<Status> results = new ArrayList<Status>();
        try {
            while (query != null) {
                QueryResult queryResult = twitter.search(query);
                List<Status> statusList = queryResult.getTweets();
                for (Status status : statusList) {
                    if (!status.isRetweet()) {
                        results.add(status);
                    }
                    if (results.size() == limit) {
                        return results;
                    }
                }
                query = queryResult.nextQuery();
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return results;
    }
}
