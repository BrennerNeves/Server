package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brenner
 */
public class Message {
	
	private int id;
	private User user;
	private List<String> message = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
	
	
	@Override
	public String toString() {
		String result = "";
		for(String me : message) { 
			result = result + me + "\n";
		};
		return result;
	}
	
	
	

}
