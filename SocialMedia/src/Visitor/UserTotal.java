package Visitor;

//This class users Visitor Pattern 
//UserTotal finds the total number of users on mini twitter

import application.User;
import application.UserGroup;

public class UserTotal implements Visitor{	
	private int userCount;
	
	public UserTotal() {
		userCount = 0;
	}
	
	public int getUserCount() {
		return userCount;
	}
	
	@Override
	public void visit(User user) {
		userCount++;
	}
	
	@Override
	public void visit(UserGroup group) {
		//not needed for this class
	}

}