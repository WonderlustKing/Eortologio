package ateith.eortologio;

import java.util.ArrayList;
import java.util.List;


public class Day {
	private int myDay;
	private List<Name_Day> myNameDays  = new ArrayList<Name_Day>();
	
	public Day(){}
	
	public Day(int day,String nameDay){
		myDay=day;
		myNameDays.add(new Name_Day(nameDay));
	}
	
	public List<Name_Day> getNameList(){
		return  myNameDays;
	}
	
	public void setName(String nameDay){
		myNameDays.add(new Name_Day(nameDay));
	}
	
	public int get_Day()
	{
		return myDay;
	}
	
	public void set_Day(int tmpDay){
		myDay = tmpDay;
	}
	public String toString(){
        String tmp_str="";
        for (Name_Day tmp : myNameDays){
            tmp_str+=tmp.toString()+"\n";
        }
        return tmp_str.trim();
    }
	
	
	
}
