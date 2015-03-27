package com.project.team16.nfclock;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.ToggleButton;

import java.util.List;


public class homePage extends ListActivity {

   private Adapter adapter_;
   private DBManager dbManager = new DBManager(this);
   private Context context_;
   // private ListView listView = (ListView) findViewById(R.id.list);

    public void sendMessage(View view) {
        openAlarm(-1);
    }


    public void openAlarm(long id){
        Intent intent = new Intent(homePage.this, CreateAlarm.class);
        intent.putExtra("id", id);
       // Log.d("HEELO","TEMP");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context_ = this;
        setContentView(R.layout.activity_home_page);

        adapter_ = new Adapter(this, dbManager.getAlarms());

        setListAdapter(adapter_);


       // listView = (ListView) findViewById(android.R.id.list);
       // listView.setEmptyView(findViewById(android.R.id.empty));
        //listView.setAdapter(adapter_);
        //populateListView();

    }

    public void setAlarmEnabled(long id, boolean isOn){
        AlarmTemplate template = dbManager.getAlarm(id);
        template.isOn = isOn;
        dbManager.updateAlarm(template);

        adapter_.setAlarms(dbManager.getAlarms());
        adapter_.notifyDataSetChanged();
    }

/*    public void openAlarmDetails(long id){
        Intent intent = new Intent(this, CreateAlarm.class);
        intent.putExtra("id", id);
        startActivityForResult(intent,0);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        //Log.d("HitHomepage",data.toString());

        if (data == null){
            Log.d("Data", "Is NuLL");

        }

        if (resultCode == RESULT_OK) {
            Log.d("ResultCode", "Okay");
            //Log.d
            adapter_.setAlarms(dbManager.getAlarms());
            adapter_.notifyDataSetChanged();
        }

    }



}
