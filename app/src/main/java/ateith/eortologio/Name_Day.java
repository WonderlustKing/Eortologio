package ateith.eortologio;

public class Name_Day {
	
	private String Name;
	public Name_Day(){}
	public Name_Day(String tmp){
		Name=tmp;
	}
	
	public String getName(){
		return Name;
	}
	
	public void setName(String tmp){
		Name=tmp;
	}
	public String toString(){
        return Name;
    }
}
