package com.zappos.sentanal;

import com.sun.deploy.util.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import twitter4j.Status;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maxkeene on 6/11/14.
 */
public class TweetClassifier {
    private static JFrame jframe;

    public static void main(String[] args) throws IOException {
        jframe = new JFrame("Tweet Classifier");
        String limitString = JOptionPane.showInputDialog(jframe, "How many tweets would you like to classify?");
        int limit = Integer.parseInt(limitString);
        TwitterSearch twitterSearch = new TwitterSearch();
        List<Status> results = twitterSearch.searchTweets("zappos", limit);
        FileWriter writer = new FileWriter("tweets.csv", true);
        for (Status result : results) {
            Object[] options = {"skip",
                    "negative",
                    "neutral",
                    "positive"};
            int choice = JOptionPane.showOptionDialog(jframe, result.getText(),
                    "tweet from " + result.getUser().getScreenName(),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            System.out.println(options[choice]);

            if (choice != 0) {
                List<String> rowArray = new ArrayList<String>();
                rowArray.add("\"" + (String) options[choice] + "\"");
                String msg = StringEscapeUtils.escapeJava(result.getText());
                rowArray.add("\"" + msg + "\"");
                rowArray.add("\"" + String.valueOf(result.getFavoriteCount()) + "\"");
                rowArray.add("\"" + String.valueOf(result.getRetweetCount()) + "\"");
                rowArray.add("\"" + result.getUser().getScreenName() + "\"");
                Calendar cal = Calendar.getInstance();
                cal.setTime(result.getCreatedAt());
                rowArray.add("\"" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + "\"");
                rowArray.add("\"" + String.valueOf(result.getText().split(" ").length) + "\"");
                writer.append(StringUtils.join(rowArray, ", "));
                writer.append("\n");
            }
        }
        writer.close();
        System.out.println("done");
        return;
    }
}
