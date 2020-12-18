package application;

//MessageTotal uses Visitor Pattern
//This class supports the Total Message button, and will return the total number of messages of users

import application.User;
import application.UserGroup;
import Visitor.Visitor;

public class MessageTotal implements Visitor{
	private int counter;
	
	
	public MessageTotal() {
		counter = 0;
	}
	
	@Override
	public void visit(User user) {
		counter = counter + user.getTweets().size();
	}
	
	public int getCounter() {
		return counter;
	}
	
	@Override
	public void visit(UserGroup group) {
	}
	
}