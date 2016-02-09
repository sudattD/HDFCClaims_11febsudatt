package com.hdfc.claims.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.hdfc.claims.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.LinkedHashMap;

public class ApprovalQueryActivity extends AppCompatActivity {

    private Context context;
    private ListView queryListView;
    private ApprovalQueryListAdapter adapter;
    private LinkedHashMap<String, ApprovalQueryListModel> queryList;
    private LinkedHashMap<String, ApprovalQueryListModel> queryListReverse;

    private RadioButton btnWorkshopOne, btnWorkshopTwo;
    private RelativeLayout layoutClaimStage,rlQuery;

    ImageView imgSend;
    EditText edtMsg;

    private String[] claimStage = {"Data1","Data2","Data3","Data4","Data5"};

    ArrayAdapter<String> adapter_state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_pre_approval_query);
        context = ApprovalQueryActivity.this;

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);

        imgSend = (ImageView) findViewById(R.id.imgSend);
        edtMsg = (EditText) findViewById(R.id.edtMessage);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Claim Updation & Query" + " </font>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>" + "C230015034232" + " </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(196, 54, 53)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        queryListView = (ListView) findViewById(R.id.queriestListView);
        queryList = new LinkedHashMap<String, ApprovalQueryListModel>();
        queryListReverse = new LinkedHashMap<String, ApprovalQueryListModel>();

        layoutClaimStage = (RelativeLayout)findViewById(R.id.layoutClaimStage);
        rlQuery = (RelativeLayout)findViewById(R.id.rlQuery);

        btnWorkshopOne = (RadioButton) findViewById(R.id.btnWorkshopOne);
        btnWorkshopTwo = (RadioButton) findViewById(R.id.btnWorkshopTwo);

        btnWorkshopOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopOne.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#C43635"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#989898"));
                    rlQuery.setVisibility(View.GONE);
                    layoutClaimStage.setVisibility(View.VISIBLE);
                }
            }
        });

        btnWorkshopTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopTwo.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#989898"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#C43635"));
                    rlQuery.setVisibility(View.VISIBLE);
                    layoutClaimStage.setVisibility(View.GONE);
                }
            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                ApprovalQueryListModel model = new ApprovalQueryListModel();
                model.setQueryQue("U");
                model.setQueryAns(edtMsg.getText().toString());
                queryList.put(model.getQueryQue(), model);
                for (int i = queryList.size(); i >= 0; i++) {
                    //queryListReverse.put(queryList.get(i));
                }
                adapter = new ApprovalQueryListAdapter(ApprovalQueryActivity.this, queryList);
                queryListView.setAdapter(adapter);*/
            }
        });

        // Spinner element
        MaterialBetterSpinner claimStageSpinner = (MaterialBetterSpinner)findViewById(R.id.spinner_claim_stage);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,claimStage);

        // Spinner Drop down elements
        adapter_state = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, claimStage);

        claimStageSpinner.setAdapter(adapter);

        generateQueryList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    private void generateQueryList() {

        for (int i = 0; i < 4; i++) {
            ApprovalQueryListModel model = new ApprovalQueryListModel();
            model.setQueryQue(getResources().getStringArray(R.array.ownerArray)[i]);
            model.setQueryAns(getResources().getStringArray(R.array.msgArray)[i]);
            queryList.put(model.getQueryQue(), model);
        }

        adapter = new ApprovalQueryListAdapter(this, queryList);
        queryListView.setAdapter(adapter);

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_approval_query, menu);
        return true;
    }*/

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
