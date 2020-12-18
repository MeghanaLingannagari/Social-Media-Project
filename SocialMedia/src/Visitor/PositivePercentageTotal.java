package Visitor;

//This class uses Visitor Pattern and it's purpose is to check for "positive" tweets by users.
//In this class, a tweet is considered positive if it has the following words in the tweet: happy, good, and love.

import application.User;
import application.UserGroup;
import java.util.List;

public class PositivePercentageTotal implements Visitor{	//visitor pattern
	private int count;
	
	public PositivePercentageTotal() {
		count = 0;
	}
	
	public int getCount() {
		return count;
	}
	
	public int findPosTweets(List<String> list) {
		int positiveTweet = 0; //keep count when a positive tweet is found
		for(String message : list) {
			String [] splitWordArray = message.split(" "); //using .split() method for breaking a string when there is a space detected, goal is to check for individual words
			for (String splitString : splitWordArray) { //iterate through splitWordArray to check if any of the below words match, if so then the tweet is positive
				if (splitString.equals("happy") || splitString.equals("good") || 
				    splitString.equals("love")) {
					//increment positiveTweet to indicate a positive tweet is found
					positiveTweet++;
					break;
				}
			}
		}
		return positiveTweet; //return the count
	}
	
	@Override
	public void visit(User user) {
		//find the number of positive tweets by calling findPosTweets and user,getTweets() gets a particular user's tweets
		count = count + findPosTweets(user.getTweets());
	}
	
	@Override
	public void visit(UserGroup group) {
	
	}
}