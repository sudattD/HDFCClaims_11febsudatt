package com.hdfc.claims.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.hdfc.claims.R;
import com.hdfc.claims.Widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationActivity extends AppCompatActivity {

    private View view;
    Context context;

    ListView listView;

    List<String> listNotification;
    HashMap<String, List<String>> listNotificationTitle;
    HashMap<String, List<String>> listNotificationDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        context = this;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Notifications");

        listView = (ListView) findViewById(R.id.listNotification);

        prepareListData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    private void prepareListData() {

        String[] titles = new String[] { "Notification 1",
                "Notification 2",
                "Notification 3",
                "Notification 4",
                "Notification 5",
                "Notification 6",
                "Notification 7",
                "Notification 8"
        };

        String[] desc = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item_notification, R.id.txtNotificationTitle, titles);

        listView.setAdapter(adapter);

/*        listNotification = new ArrayList<String>();
        listNotificationTitle = new HashMap<String, List<String>>();
        listNotificationDesc = new HashMap<String,List<String>>();

        // Adding child data
        List<String> basicInfo = new ArrayList<String>();
        basicInfo.add("Notification 1");
        basicInfo.add("Notification 2");
        basicInfo.add("Notification 3");
        basicInfo.add("Notification 4");

        //Adding Child Values
        List<String> basicInfoValue = new ArrayList<String>();
        basicInfo.add("Inner Panel" + "");
        basicInfo.add("Lights");
        basicInfo.add("Rim & Tyre");
        basicInfo.add("Badging & Stickers");
        basicInfo.add("W / S Cleaning");

        listNotificationTitle.put(listNotification.get(0), basicInfo); // Header, Child data

        listNotificationDesc.put(listNotification.get(0), basicInfoValue); // Header, Child data

        adatpter = new NotificationAdapter(this, listNotification, listNotificationTitle, listNotificationDesc);

        listView.setAdapter(adatpter);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
