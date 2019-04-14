package model;


/**
 * @author Brenner
 */
public class User {
	
	private int id;
	private String name;
	private String ipAddres;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddres() {
		return ipAddres;
	}
	public void setIpAddres(String ipAddres) {
		this.ipAddres = ipAddres;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	
	

}
