package Visitor;

//This class uses Visitor Pattern to get the total number of user groups on mini twitter

import application.User;
import application.UserGroup;

public class UserGroupTotal implements Visitor{	
	private int userGroupCounter;
	
	public UserGroupTotal() {
		userGroupCounter = 0;
	}
	
	public int getCounter() {
		return userGroupCounter;
	}
	
	@Override 
	public void visit(User user) {
		//not needed for this class
	}
	
	@Override
	public void visit(UserGroup group) {
		userGroupCounter++; //increment to show total of user groups
	}

}