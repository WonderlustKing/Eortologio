package ateith.eortologio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.AbstractThreadedSyncAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

//db4o.ObjectContainer

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import com.db4o.config.AndroidSupport;
import com.db4o.config.BigMathSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ObjectSet;
import ateith.eortologio.AsyncResponse;
import ateith.eortologio.R;



public class MainActivity extends Activity implements AsyncResponse
{
 //   private ObjectContainer myDB;
    private ObjectSet result;
    String str;
    int i=0;
    static db4o myDB;
    Month proto = new Month(null);
    String monthName[] = new String[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    requestWindowFeature(Window.FEATURE_PROGRESS);

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


        if(new File(getApplicationInfo().dataDir+"/eortologio.db4o").exists())
        myDB=  new db4o(  getApplicationInfo().dataDir,true);
        else
            myDB=  new db4o(  getApplicationInfo().dataDir,false);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //     setProgressBarIndeterminate(true);
        setContentView(R.layout.activity_main);
        //     setProgressBarIndeterminate(false);
        Button cmd,show;

        myDB.delegate = this;
        myDB.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

           SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
         searchView.setSearchableInfo(  searchManager.getSearchableInfo(getComponentName()));
         searchView.setQueryHint("Δώσε όνομα.");
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
          //  myDB=  new db4o(  getApplicationInfo().dataDir);


//
       //     MainActivity tmp;

            myDB.getDB().close();
        //    myDB =null;
            myDB=  new db4o(  getApplicationInfo().dataDir,false);
            myDB.delegate = this;
           myDB.execute();
            return true;
        }
        if(id==R.id.action_settings){
            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void message(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    int count = 0;
    @Override
    public void processUpdate(int number_of_current_month) {
        count++;

        if(count!=12)
            message("Please wait " + (number_of_current_month+1) + "/12");
        if(count==12){
            setProgressBarIndeterminateVisibility(false);
            message("Completed !");
            count=0;
        }

        //       setProgress((int)(((double)count/(12)*10000)));
    }



    public void processBefore() {

        setProgressBarIndeterminateVisibility(true);

        setProgressBarIndeterminate(true);
        //     setProgressBarVisibility(true);
    }

    public void onDestroy() {
        if(new File(getApplicationInfo().dataDir+"/eortologio.db4o").exists())
        myDB.getDB().close();
        super.onDestroy();

    }


    public void processFinish(String result) {

      Calendar c = Calendar.getInstance();
      ObjectSet monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));

        setProgressBarIndeterminate(false);
        setProgressBarIndeterminateVisibility(false);
        int flag=0;
        switch(c.get(Calendar.MONTH)){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                switch(c.get(Calendar.DAY_OF_MONTH)){
                    case 31:
                        flag=31;
                        break;
                    default :
                        flag=1;
                        break;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                switch(c.get(Calendar.DAY_OF_MONTH)){
                case 30:
                    flag=30;
                    break;
              default :
                        flag=1;
                        break;
            }
                break;
            case 2:
                switch(c.get(Calendar.DAY_OF_MONTH)){
                    case 29:
                        flag=29;
                        break;
                    case 28:
                        flag=28;
                        break;
                    default :
                        flag=1;
                        break;
                }
                break;

        }

//mhnes 31 - 1 , 3 , 5 ,  7, 8, 10 , 12
        //   4 , 6 , 9 ,11
        // 2 28 i 23

        TextView txt = (TextView) findViewById(R.id.info_text1);


            txt.setText(((Month)monthList.get(0)).getDaysList().get((c.get(Calendar.DAY_OF_MONTH)-1)).toString());
            txt.setGravity(Gravity.CENTER);
            txt = (TextView) findViewById(R.id.info_text2);
        if (flag==31){
            monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));
            txt.setText(((Month)monthList.get(0)).getDaysList().get(0).toString());
            txt.setGravity(Gravity.CENTER);
        }

        if(flag==29){
            monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));
            txt.setText(((Month)monthList.get(0)).getDaysList().get(0).toString());
            txt.setGravity(Gravity.CENTER);
        }

        if(flag==28){
            monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));
            txt.setText(((Month)monthList.get(0)).getDaysList().get(0).toString());
            txt.setGravity(Gravity.CENTER);
        }

        if(flag==1)
        {
            monthList = myDB.getDB().queryByExample(new Month(monthName[c.get(Calendar.MONTH)]));
            txt.setText(((Month)monthList.get(0)).getDaysList().get(c.get(Calendar.DAY_OF_MONTH)).toString());
            txt.setGravity(Gravity.CENTER);
        }


    }
}
