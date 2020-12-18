package Visitor;

//This class uses Observer Pattern

import java.util.ArrayList;
import java.util.List;

public class Subject {	
	
	public List<Observer> observerList = new ArrayList<Observer>();
	
	public void add(Observer obs) {
		this.observerList.add(obs);
	}
	
	public void update() {
		for (Observer observer : this.observerList) {
			observer.update(this);
		} 
	}

}