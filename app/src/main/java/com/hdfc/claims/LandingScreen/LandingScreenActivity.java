package com.hdfc.claims.LandingScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hdfc.claims.Dashboard.DashboardListModel;
import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.InsuredDetailsModel;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.FragmentContainer.dlNrcDetailsModel;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.APIURLs;
import com.hdfc.claims.Utilities.DatabaseUtils;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandingScreenActivity extends AppCompatActivity {

    Bundle bundle;

    LinearLayout dlNrcDetails, insuredDetails, policyInfo, documentsUpload, workshopSelection, surveyDetails, pointOfImpact, computationEntry, computationSummary, neftEntry, cpLoss, cpExpense;

    SharedPreferences sharedPreferences;

    private SharedPreferences pref;

    RelativeLayout rl;

    private DatabaseUtils dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_main_tab);

        // Set up the action bar.
        //final ActionBar actionBar = getSupportActionBar();


        initializeControls();

        getInsuredDetails();

    }

    private void getInsuredDetails() {

        if (Utility.checkInternetConnection(this)) {

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("claimno", bundle.getString("ClaimNumber"));


                        /*INSURED DETAILS API CALL*/
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, APIURLs.GET_INSURED_DETAIL_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            pDialog.hide();
                            try {
                                response = response.getJSONObject("GetCustomerInfoResult");
                                JSONArray jsonArr = response.getJSONArray("Customer List");

                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject item = jsonArr.getJSONObject(i);

                                    InsuredDetailsModel model = new InsuredDetailsModel();
                                    model.setMaster_Claim_Number(bundle.getString("MasterClaimNumber"));
                                    model.setInsured_Name(item.getString("Customer Name"));
                                    model.setInsured_Address(item.getString("Address 1") + "\n" + item.getString("Address 2"));
                                    model.setInsured_Pin_Code(item.getString("Pin Code"));
                                    model.setInsured_Email(item.getString("Email Id"));
                                    model.setInsured_Mobile_Number(item.getString("Mobile No"));
                                    dbHelper.insertInsuredDetailsData(model);
                                }

                            } catch (JSONException e) {
                                Snackbar snackbar = Snackbar
                                        .make(rl, "Oops! Some Error Occured", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.hide();
                            error.printStackTrace();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("oAuthkey", pref.getString("oAuthkey", ""));

                    return headers;
                }
            };


                            /*DL N RC DETAILS API CALL*/
            JsonObjectRequest dlNRCRequest = new JsonObjectRequest
                    (Request.Method.POST, APIURLs.GET_DL_N_RC_DETAIL_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            pDialog.hide();
                            try {
                                response = response.getJSONObject("GetDLDetailsResult");
                                JSONArray jsonArr = response.getJSONArray("DL Details");

                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject item = jsonArr.getJSONObject(i);

                                    dlNrcDetailsModel model = new dlNrcDetailsModel();
                                    model.setMaster_Claim_Number(bundle.getString("MasterClaimNumber"));
                                    model.setVehicleOwner(item.getString("Insured Name"));
                                    model.setVehicleRegNumber(item.getString("Registration Number"));
                                    model.setTransferDate(item.getString("Transfer Date"));
                                    model.setVehicleMake(item.getString("Vehicle Make"));
                                    model.setVehicleModel(item.getString("Vehicle Model"));
                                    model.setEngineNumber(item.getString("Engine Number"));
                                    model.setChassisNumber(item.getString("Chassis Number"));
                                    model.setRtoName(item.getString("RTO Location"));
                                    dbHelper.insertDLNRCDetailsData(model);
                                }

                            } catch (JSONException e) {
                                Snackbar snackbar = Snackbar
                                        .make(rl, "Oops! Some Error Occured", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.hide();
                            error.printStackTrace();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("oAuthkey", pref.getString("oAuthkey", ""));

                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(jsonRequest);
            Volley.newRequestQueue(this).add(dlNRCRequest);

        } else {
            Snackbar snackbar = Snackbar
                    .make(rl, "No internet connection", Snackbar.LENGTH_LONG);

            snackbar.show();
            return;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (FragmentContainerActivity.isHomeClick) {
            FragmentContainerActivity.isHomeClick = false;
            finish();
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    private void initializeControls() {

        bundle = getIntent().getExtras();
        pref = getSharedPreferences("Session Data", Context.MODE_PRIVATE);
        dbHelper = new DatabaseUtils(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", 1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Claim Processing" + " </font>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>" + bundle.getString("MasterClaimNumber") + " </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(196, 54, 53)));

        rl = (RelativeLayout) findViewById(R.id.rl);

        insuredDetails = (LinearLayout) findViewById(R.id.insuredImg);
        policyInfo = (LinearLayout) findViewById(R.id.policyImg);
        documentsUpload = (LinearLayout) findViewById(R.id.documentsImg);
        workshopSelection = (LinearLayout) findViewById(R.id.workshopImg);
        surveyDetails = (LinearLayout) findViewById(R.id.surveyImg);
        pointOfImpact = (LinearLayout) findViewById(R.id.pointOfImpactImg);
        computationEntry = (LinearLayout) findViewById(R.id.computationEntryImg);
        computationSummary = (LinearLayout) findViewById(R.id.computationSummaryImg);
        neftEntry = (LinearLayout) findViewById(R.id.claimHistoryImg);
        cpLoss = (LinearLayout) findViewById(R.id.CPLossImg);
        cpExpense = (LinearLayout) findViewById(R.id.cpExpenseImgImg);
        dlNrcDetails = (LinearLayout) findViewById(R.id.dlNrcImg);


        //Listeners
        insuredDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "insuredDetails");
                intent.putExtra("MasterClaimNumber", bundle.getString("MasterClaimNumber"));

                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        policyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "policyInfo");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        documentsUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "documentsUpload");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        workshopSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "workshopSelection");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        surveyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "surveyDetails");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        pointOfImpact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent = new Intent(LandingScreenActivity.this, PointOfImpactActivity.class);
                startActivity(intent);*/
                checkForPreferences();


            }
        });

        computationEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "computationEntry");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        computationSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "computationSummary");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        neftEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "claimHistory");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        cpLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "cpLoss");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        cpExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "cpExpense");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        dlNrcDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
                intent.putExtra("fragmentName", "dlNrc");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_main_tab, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_save_enabled);
        item.setVisible(false);
        this.invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            /*case R.id.action_save_disabled:
                Snackbar snackbar = Snackbar.make(rl, R.string.forms_not_filled, Snackbar.LENGTH_LONG);
                snackbar.show();*/

            case R.id.action_save_enabled:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Fragment fragment = null;
    FragmentManager fragmentManager;

    public void checkForPreferences() {


        Log.e("Check", "In Check of Preferences");
        Map<String, ?> value = sharedPreferences.getAll();
        if (value.isEmpty()) {
            Log.e("Value ", value + "");
            Intent intent = new Intent(LandingScreenActivity.this, PointOfImpactActivity.class);
            Log.e("Check", "Value is Not Null");

            startActivity(intent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            Intent intent = new Intent(LandingScreenActivity.this, FragmentContainerActivity.class);
            intent.putExtra("fragmentName", "pointofimpact");
            startActivity(intent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

/*            fragment = new FragmentPointOfImpact();
            fragmentManager = LandingScreenActivity.this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();*/
        }
    }
}
