package application;

//This class uses the Singleton Pattern and implements methods to getUser, and findUser

import application.Tree;
import application.User;
import application.UserGroup;

public class AdminPanel {
	
	private static AdminPanel instance = null;
	private UserGroup ugRootGroup;
	
	
	private AdminPanel() {
		//create the root group
		ugRootGroup = new UserGroup("Root");
	}
	
	public static AdminPanel getInstance() { //Singleton Pattern
		if (instance == null) {
			instance = new AdminPanel();
		}
		return instance;
	}
	
	//for findUser to call using tree item and ID
	//if the user's treeview item and ID are equal to the searched ID return user
    private User searchUser(Tree item, String id) { 
		if (item instanceof User && item.getId() == id) {
			return (User) item;
		}

		if (item instanceof UserGroup) {
			for (Tree t : ((UserGroup) item).getList()) {
				User user = searchUser(t, id);
				if (user != null) {
					return user;
				}
			}
		}
		return null;
	}
    
  //finding a particular user by searching its ID for User type
  	public User findUser(String ID) { 
          return searchUser(ugRootGroup, ID);
      }
    
}