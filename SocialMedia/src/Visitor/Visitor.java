package Visitor;

//This interface uses Visitor Pattern

import application.User;
import application.UserGroup;

public interface Visitor {	

	public void visit(User user);		
	public void visit(UserGroup group); 
}