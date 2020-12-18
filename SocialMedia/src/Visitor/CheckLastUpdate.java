package Visitor;

//This class is to get the last updated time attributed to User.
//whenever a new tweet is posted, this button will show who the user that posted the tweet is.

import application.User;
import application.UserGroup;

public class CheckLastUpdate implements Visitor{
	private User user;
	private long lastUpdateTime; 

	public CheckLastUpdate() {
		user = null;
		lastUpdateTime = -1;
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public void visit(User user) {
		if(user.getUpdatedTime()>lastUpdateTime) {
			this.user = user;
			lastUpdateTime = user.getUpdatedTime();
		}
	}

	@Override
	public void visit(UserGroup userGroup) {
		
		}

	

}