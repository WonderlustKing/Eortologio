package ateith.eortologio;

/**
 * Created by Γιώργος on 13/11/2014.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.db4o.ObjectSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NamesOnDate extends Activity {
    String monthName[] = new String[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names_on_date);

        monthName[0] = "January";
        monthName[1] = "February";
        monthName[2] = "March";
        monthName[3] = "April";
        monthName[4] = "May";
        monthName[5] = "June";
        monthName[6] = "July";
        monthName[7] = "August";
        monthName[8] = "September";
        monthName[9] = "October";
        monthName[10] = "November";
        monthName[11] = "December";
        int recievedDate;
        int month;
        month = getIntent().getExtras().getInt("month");
         recievedDate = getIntent().getExtras().getInt("dayOfMonth");

        Log.v("Date : ",""+recievedDate);
        Log.v("Month : ",""+month);
        TextView textView = (TextView) findViewById(R.id.text_1);

//ObjectSet monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));
        //(new Month(""));
        //textView.setText(MainActivity.myDB.getDB().queryByExample(new Month("January")).toString());
        ObjectSet monthList;
        monthList = MainActivity.myDB.getDB().queryByExample(new Month(monthName[month]));
        textView.setText(((Month)monthList.get(0)).getDaysList().get(recievedDate-1).toString());
        textView.setGravity(Gravity.CENTER);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_names_on_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
