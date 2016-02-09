package com.hdfc.claims.FragmentContainer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hdfc.claims.Dashboard.DashboardAdapter;
import com.hdfc.claims.Dashboard.DashboardListModel;
import com.hdfc.claims.FragmentContainer.PolicyInformation.PolicyInformationFragment;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.APIURLs;
import com.hdfc.claims.Utilities.DatabaseUtils;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Globals;
import com.hdfc.claims.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsuredDetailsFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private DatabaseUtils dbHelper;

    private LinkedHashMap<String, InsuredDetailsModel> insuredDetail = new LinkedHashMap<String, InsuredDetailsModel>();

    EditText edtInsuredName, edtInsuredAddress, edtInsuredPinCode, edtInsuredMobile, edtInsuredEmail;

    RelativeLayout rl;

    String masterClaimNumber;
  /*  // TODO: Rename and change types and number of parameters
    public static InsuredDetailsFragment newInstance() {
        InsuredDetailsFragment f1 = new InsuredDetailsFragment();
        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }*/

    public static InsuredDetailsFragment newInstance() {
        InsuredDetailsFragment fragment = new InsuredDetailsFragment();
        return fragment;
    }

    public InsuredDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        view = inflater.inflate(R.layout.fragment_insured_details, container, false);

        context = view.getContext();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        initializeControls();

        getInsuredDetails();

        return view;
    }

    private void initializeControls() {

        dbHelper = new DatabaseUtils(context);
        rl = (RelativeLayout) view.findViewById(R.id.rl);

        edtInsuredName = (EditText) view.findViewById(R.id.edtInsuredName);
        edtInsuredAddress = (EditText) view.findViewById(R.id.edtInsuredAddress);
        edtInsuredPinCode = (EditText) view.findViewById(R.id.edtInsuredPinCode);
        edtInsuredMobile = (EditText) view.findViewById(R.id.edtInsuredMobileNumber);
        edtInsuredEmail = (EditText) view.findViewById(R.id.edtInsuredEmailID);

        ((FragmentContainerActivity) getActivity()).setActionBarTitle_first("Insured Details", Globals.MASTER_CLAIM_NUMBER);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.arrow_forward:

                //UpdateInsuredDetails();


                FragmentContainerActivity.hideKeyboard(getActivity());
                PolicyInformationFragment fragment2 = new PolicyInformationFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    private void UpdateInsuredDetails() {
        InsuredDetailsModel model = new InsuredDetailsModel();
        model.setInsured_Mobile_Number(edtInsuredMobile.getText().toString());
        model.setInsured_Email(edtInsuredEmail.getText().toString());

        dbHelper.UpdateInsuredDetailsByMasterClaimNumber(Globals.MASTER_CLAIM_NUMBER, model);

    }

    private void getInsuredDetails() {

        //InsuredDetailsModel claim = dbHelper.getInsuredDetailsByMasterClaimNumber(getArguments().getString("MasterClaimNumber"));
        InsuredDetailsModel claim = dbHelper.getInsuredDetailsByMasterClaimNumber(Globals.MASTER_CLAIM_NUMBER);

        edtInsuredName.setText(claim.getInsured_Name());
        edtInsuredAddress.setText(claim.getInsured_Address());
        edtInsuredPinCode.setText(claim.getInsured_Pin_Code());
        edtInsuredEmail.setText(claim.getInsured_Email());
        edtInsuredMobile.setText(claim.getInsured_Mobile_Number());

    }

    @Override
    public void onPause() {
        super.onPause();

        UpdateInsuredDetails();
    }


}

