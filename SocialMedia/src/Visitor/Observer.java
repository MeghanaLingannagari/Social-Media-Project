package Visitor;

//This interface uses the Observer Pattern.
//Observer implements only one method, update so that this method will be used in Subject class.

public interface Observer {	
	
	public void update(Subject subject);

}