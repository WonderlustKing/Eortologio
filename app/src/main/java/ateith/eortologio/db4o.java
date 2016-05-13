package ateith.eortologio;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.db4o.config.AndroidSupport;
import com.db4o.config.BigMathSupport;
import com.db4o.config.EmbeddedConfiguration;

public class db4o extends AsyncTask <Void, Integer, String>
{
    public AsyncResponse delegate=null;
	private ObjectContainer db;
    ContentResolver mContentResolver;

    String path;
boolean exists;

    public db4o(String s,boolean b){
        path=s;
        exists=b;
    }

    protected String doInBackground(Void... params) {
        try {
       if(exists)
       db = Db4oEmbedded.openFile(db4oDBFullPath());
    else
    getData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {

        delegate.processUpdate(progress[0]);
    }


    protected void onPostExecute(String s) {

       delegate.processFinish("");

    }

    protected void onPreExecute() {
                delegate.processBefore();

    }


    public ObjectContainer getDB(){
        return db;
    }



	private void InitializeDB4O() throws Exception{
		//delete file
            if(exists)
            new File(db4oDBFullPath()).delete();
            db = Db4oEmbedded.openFile(dbConfig(), db4oDBFullPath());
	}

    private  String db4oDBFullPath() {

   return// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
           //     +

path+"/eortologio.db4o";
          //  "/eortologio.db4o";
    }

   private EmbeddedConfiguration dbConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().add(new AndroidSupport());
        configuration.common().add(new BigMathSupport());
        configuration.file().lockDatabaseFile(false);
        return configuration;
    }
	
private void getData() throws Exception {
    String month[] = new String[12];
    String monthName[] = new String[12];
    String host = "http://www.eortologio.gr/data/eortes/eortes_";

    InitializeDB4O();

    month[0] = "january.php";
    monthName[0] = "January";
    month[1] = "february.php";
    monthName[1] = "February";
    month[2] = "march.php";
    monthName[2] = "March";
    month[3] = "april.php";
    monthName[3] = "April";
    month[4] = "may.php";
    monthName[4] = "May";
    month[5] = "june.php";
    monthName[5] = "June";
    month[6] = "july.php";
    monthName[6] = "July";
    month[7] = "august.php";
    monthName[7] = "August";
    month[8] = "september.php";
    monthName[8] = "September";
    month[9] = "october.php";
    monthName[9] = "October";
    month[10] = "november.php";
    monthName[10] = "November";
    month[11] = "december.php";
    monthName[11] = "December";


    for (int i = 0; i < month.length; i++) {

        Month objMonth = new Month(monthName[i]);
        Document document = Jsoup.connect(host + month[i].toString()).get();

        Element table = document.select("table").get(5);
        Elements rows = table.getElementsByTag("tr");



        String tmp_Name = "";
        String myTable[];
        int tmp_Day = 0;

        for (Element row : rows) {
            if (row != rows.get(0) && row != rows.get(1) && row != rows.get(2)) //0 kai 2
            {

      //          tmp_Name = row.getElementsByTag("td").get(0).text().split(" ").toString();
                tmp_Name = row.getElementsByTag("td").get(0).text();

                //myTable = row.getElementsByTag("td").get(0).text().split("\\(");
                myTable = row.getElementsByTag("td").get(0).text().split("\\(");
        //        Log.v(" Name : ",myTable[0]);

                if (row.getElementsByTag("td").size() > 1) {
                    tmp_Day = Integer.parseInt(row.getElementsByTag("td").get(1).text());
                    Log.i("Table ",myTable[0]);
                 //   if(myTable[0].toString().trim().length()==0)
                    if(myTable[0].equals(""))
                        objMonth.add_Day(tmp_Day, "Δεν υπάρχει κάποια γνωστή γιορτή");
                        else
                     objMonth.add_Day(tmp_Day, myTable[0].trim());
                }
            }
        }
        db.store(objMonth);
        db.commit();
        publishProgress(i);
    }
   // db.close();


}


}
