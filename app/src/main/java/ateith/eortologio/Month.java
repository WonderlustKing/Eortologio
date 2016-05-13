package ateith.eortologio;

import java.util.ArrayList;
import java.util.List;

public class Month {
	
	private String month_Name;
	private List<Day> myDays = new ArrayList<Day>();

    public String toString(){
        return month_Name;
    }

	public Month(){}
	public Month(String tmp_month_Name){
		month_Name=tmp_month_Name;
	}
	
	public void add_Day(int day,String DayName)
	{
		//Day tmp = new Day(day,DayName);
		List<Day> tmpList = myDays;
		boolean flag = false;
		for (Day tmpDayFromList : tmpList){
			if(tmpDayFromList.get_Day()==day){
				myDays.get(day-1).setName(DayName);//day-1 or day+1 or day
				flag=true;
			}
				
			
		}
		if(flag==false)
			myDays.add(new Day(day,DayName));
	
	}
	
	public List<Day> getDaysList(){
		return myDays;
	}
	
	
	public String getName(){
		return month_Name;
	}
	public void setName(String DayName){
		month_Name=DayName;
	}
	
	
	
	
	
}
