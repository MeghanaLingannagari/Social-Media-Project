package application;

//This class supports the type UserGroup for each usergroup in mini twitter

import Visitor.Visitor;
import java.util.*;

public class UserGroup implements Tree{

	private String id;
	private List<Tree> list;
	
	public UserGroup(String id) {	//constructor 
		this.id = id;
		list = new ArrayList<>();
	}
	
	public void addUser(String id) {
		list.add(new User(id));
	}
	
	public void addGroup(String id) {
		list.add(new UserGroup(id));
	}
	
	public void accept(Visitor visitor) {	//accept a visitor
		//removed counter
		for (Tree tree : list) {
			tree.accept(visitor);
		}
		visitor.visit(this);
	}

	///////// GETTERS AND SETTERS GENERATED /////////
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tree> getList() {
		return list;
	}

	public void setList(List<Tree> list) {
		this.list = list;
	}
	
	
	
}