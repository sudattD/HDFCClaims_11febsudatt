package com.hdfc.claims.Dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.APIURLs;
import com.hdfc.claims.Utilities.DatabaseUtils;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class DashboardFragment extends Fragment implements PopupMenu.OnMenuItemClickListener, SearchView.OnQueryTextListener {

    private DatabaseUtils dbHelper;

    private LinkedHashMap<String, DashboardListModel> ClaimsList;
    private SharedPreferences pref;

    private RelativeLayout rl;
    private RelativeLayout hiddenRlDashboard;
    private Button btnRetry;


    private RecyclerView ClaimListView;
    private DashboardAdapter adapter;

    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ((DashboardContainerActivity) getActivity()).setActionBarTitle("Dashboard");

        dbHelper = new DatabaseUtils(context);
        pref = context.getSharedPreferences("Session Data", getActivity().MODE_PRIVATE);
        ClaimListView = (RecyclerView) view.findViewById(R.id.list_view_claims);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        hiddenRlDashboard = (RelativeLayout) view.findViewById(R.id.hiddenRlDashboard);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);


        /*edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClaimsList = dbHelper.getAllDashboardClaimsList();
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);

                ((DashboardContainerActivity) getActivity()).setActionBarTitle("Dashboard(" + ClaimsList.size() + ")");

                hiddenRlDashboard.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.GONE);

            }
        });
        ClaimListView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        ClaimListView.setLayoutManager(llm);


        ClaimsList = new LinkedHashMap<String, DashboardListModel>();

        getValidationCheck();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    /* @Override
     public void onBackPressed() {
         super.onBackPressed();
         overridePendingTransition(R.anim.no_change, R.anim.slide_down);
     }
 */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_claims_list_view, menu);

 *//*        MenuItem actionUser = menu.findItem(R.id.action_user);
        MenuItemCompat.setActionView(actionUser, R.layout.actionbar_badge_user);
        FrameLayout userCount = (FrameLayout) MenuItemCompat.getActionView(actionUser);*//*

        MenuItem actionNotification = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(actionNotification, R.layout.actionbar_badge_notification);
        FrameLayout notificationCount = (FrameLayout) MenuItemCompat.getActionView(actionNotification);

*//*        TextView tvUser = (TextView) userCount.findViewById(R.id.txtActionUser);
        tvUser.setText(ClaimsList.size()+"");*//*

        TextView tvNotification = (TextView) notificationCount.findViewById(R.id.txtActionNotification);
        tvNotification.setText("2");


        return super.onCreateOptionsMenu(menu);
    }
*/
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem actionNotification = menu.findItem(R.id.action_notification);

        MenuItemCompat.setActionView(actionNotification, R.layout.actionbar_badge_notification);
        View action_notification = menu.findItem(R.id.action_notification).getActionView();
        action_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeClickEvent();
            }
        });

        TextView tvNotification = (TextView) action_notification.findViewById(R.id.txtActionNotification);
        tvNotification.setText("2");

        final MenuItem item = menu.findItem(R.id.edtSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (item.getItemId()) {
/*            case R.id.action_map:
                // app icon in action bar clicked; goto parent activity.
                intent = new Intent(context, MapActivity.class);
                context.startActivity(intent);
                return true;*/

            case R.id.action_filter:

                showFilterPopup(getActivity().findViewById(R.id.action_filter));
                return true;
/*            case R.id.action_user:
                Toast.makeText(context, "Tapped", Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void badgeClickEvent() {


        //Toast.makeText(DashboardFragment.this, "you click on notification icon",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_change);

    }

    private void getValidationCheck() {


        if (Utility.checkInternetConnection(context)) {

            getDashboardData();


        } else {
           /* Snackbar snackbar = Snackbar
                    .make(rl, "No internet connection", Snackbar.LENGTH_LONG);

            snackbar.show();
            return;*/

            ClaimsList = dbHelper.getAllDashboardClaimsList();
            adapter = new DashboardAdapter(getActivity(), ClaimsList);
            ClaimListView.setAdapter(adapter);

            ((DashboardContainerActivity) getActivity()).setActionBarTitle("Dashboard(" + ClaimsList.size() + ")");

        }

    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_filter_popup, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(this);


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all_filter:
                ClaimsList = dbHelper.getAllDashboardClaimsList();
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);

                return true;
            case R.id.action_new_filter:
                ClaimsList = dbHelper.getFilteredDashboardClaimsList("NEW");
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);

                return true;
            case R.id.action_incomplete_filter:
                ClaimsList = dbHelper.getFilteredDashboardClaimsList("INCOMPLETE");
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);
                return true;
            case R.id.action_reassigned_filter:
                ClaimsList = dbHelper.getFilteredDashboardClaimsList("RE-ASSIGNED");
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);
                return true;
            case R.id.action_fromapproval_filter:
                ClaimsList = dbHelper.getFilteredDashboardClaimsList("FROM APPROVAL");
                adapter = new DashboardAdapter(getActivity(), ClaimsList);
                ClaimListView.setAdapter(adapter);
                return true;
            default:
                return false;
        }
    }

    private void getDashboardData() {

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("userid", pref.getString("Username", ""));


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, APIURLs.GET_DASHBOARD_DATA_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!

                        try {
                            response = response.getJSONObject("GetDasBoardDataResult");
                            JSONArray jsonArr = response.getJSONArray("DasBoard");


                            for (int i = 0; i < jsonArr.length(); i++) {

                                JSONObject item = jsonArr.getJSONObject(i);

                                DashboardListModel model;
                                model = new DashboardListModel();
                                model.setMaster_Claim_Number(item.getString("Master Claim Number"));
                                model.setVehicle_Registration_No(item.getString("Vehicle Registration No"));
                                model.setVehicle_Model(item.getString("Vehicle Model"));
                                model.setWork_Shop(item.getString("Work Shop"));
                                model.setInsured_Name(item.getString("Insured Name"));
                                model.setTat(item.getString("Tat"));
                                model.setClaim_Status(item.getString("Claim Status"));
                                model.setMobile(item.getString("Mobile"));
                                model.setClaim_Stage(item.getString("Claim Stage"));
                                model.setClaim_Number(item.getString("Claim Number"));
                                // ClaimsList.put(model.getMaster_Claim_Number(), model);

                                dbHelper.insertDashboardClaimData(model);
                            }

                            ClaimsList = dbHelper.getAllDashboardClaimsList();
                            adapter = new DashboardAdapter(getActivity(), ClaimsList);
                            ClaimListView.setAdapter(adapter);
                            ((DashboardContainerActivity) getActivity()).setActionBarTitle("Dashboard(" + ClaimsList.size() + ")");

                            pDialog.hide();


                        } catch (JSONException e) {
                            pDialog.hide();
                            Snackbar snackbar = Snackbar
                                    .make(rl, "Something went wrong!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            e.printStackTrace();
                            hiddenRlDashboard.setVisibility(View.GONE);
                            btnRetry.setVisibility(View.VISIBLE);

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Snackbar snackbar = Snackbar
                                .make(rl, "Slow internet connection!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        error.printStackTrace();
                        hiddenRlDashboard.setVisibility(View.GONE);
                        btnRetry.setVisibility(View.VISIBLE);
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

        Volley.newRequestQueue(context).add(jsonRequest);


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query.toString());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText.toString());
        return true;
    }
}
