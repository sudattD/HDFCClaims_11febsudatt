package com.hdfc.claims.FragmentContainer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfc.claims.FragmentContainer.DocumentUpload.DocumentsUploadFragment;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.DatabaseUtils;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Globals;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.Map;

public class dlNrcDetailsFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;
    Context context;

    int mYear, mMonth, mDay;

    ImageView previousImg, nextImg;

    EditText imgDriverDOB;
    EditText imgIssuanceDate;
    EditText imgExpiryDate;

    EditText txtDriverDOB;
    EditText txtIssuanceDate;
    EditText txtExpiryDate;

    EditText edtLicence;
    EditText edtIssuance;
    EditText edtFIRDate;

    EditText edtDriverName, edtLicenseNumber, edtIssuanceRTO, edtVehicleOwner, edtVehicleRegNumber, edtTransferDate, edtVehicleMake, edtVehicleModel, edtEngineNumber, edtChassisNumber, edtRTOName, edtFIRName, edtPoliceStation, edtFIRUnderSection;

    RelativeLayout rl, rlFIR;

    SharedPreferences sharedPreferences;

    private DatabaseUtils dbHelper;

    CheckBox chkDriving, chkFIR;

    private String[] driverType = {"Data1", "Data2", "Data3", "Data4"};
    private String[] licenceType = {"Data5", "Data6", "Data7", "Data8"};
    private String[] vehiceColor = {"Data1", "Data2", "Data3", "Data4"};
    private String[] vehiceColorType = {"Data5", "Data6", "Data7", "Data8"};

    ArrayAdapter<String> adapter;

    public dlNrcDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        view = inflater.inflate(R.layout.fragment_dl_n_rc_details, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        getdlNrcDetails();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    private void initializeControls() {
        context = view.getContext();

        dbHelper = new DatabaseUtils(context);

        sharedPreferences = context.getSharedPreferences("MySharedPref", 1);

        imgDriverDOB = (EditText) view.findViewById(R.id.edtDriverDOB);
        imgIssuanceDate = (EditText) view.findViewById(R.id.edtIssuanceDate);
        imgExpiryDate = (EditText) view.findViewById(R.id.edtExpiryDate);

        txtDriverDOB = (EditText) view.findViewById(R.id.edtDriverDOB);
        txtIssuanceDate = (EditText) view.findViewById(R.id.edtIssuanceDate);
        txtExpiryDate = (EditText) view.findViewById(R.id.edtExpiryDate);

        edtLicence = (EditText) view.findViewById(R.id.edtLicenceNumber);
        edtIssuance = (EditText) view.findViewById(R.id.edtIssuanceRTO);
        edtFIRDate = (EditText) view.findViewById(R.id.edtFIRDate);

        chkDriving = (CheckBox) view.findViewById(R.id.chkDriver);
        chkFIR = (CheckBox) view.findViewById(R.id.chkFIR);

        edtDriverName = (EditText) view.findViewById(R.id.edtDriverName);
        edtLicenseNumber = (EditText) view.findViewById(R.id.edtLicenceNumber);
        edtVehicleOwner = (EditText) view.findViewById(R.id.edtVehicleOwner);
        edtVehicleRegNumber = (EditText) view.findViewById(R.id.edtVehicleRegNoValue);
        edtTransferDate = (EditText) view.findViewById(R.id.edtTransferDateValue);
        edtVehicleMake = (EditText) view.findViewById(R.id.edtVehicleMakeValue);
        edtVehicleModel = (EditText) view.findViewById(R.id.edtVehicleModelValue);
        edtEngineNumber = (EditText) view.findViewById(R.id.edtEngineNumber);
        edtIssuanceRTO = (EditText) view.findViewById(R.id.edtIssuanceRTO);
        edtChassisNumber = (EditText) view.findViewById(R.id.edtChasisNumber);
        edtRTOName = (EditText) view.findViewById(R.id.edtRTOName);
        edtFIRName = (EditText) view.findViewById(R.id.edtFIRDate);
        edtPoliceStation = (EditText) view.findViewById(R.id.edtPoliceStation);
        edtFIRUnderSection = (EditText) view.findViewById(R.id.edtFIRSection);

        rl = (RelativeLayout) view.findViewById(R.id.hide_show_rl);
        rlFIR = (RelativeLayout) view.findViewById(R.id.layout_fir);

        imgDriverDOB.setOnClickListener(this);
        imgIssuanceDate.setOnClickListener(this);
        imgExpiryDate.setOnClickListener(this);
        edtFIRDate.setOnClickListener(this);

        chkDriving.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chkDriving.isChecked()) {
                    rl.setVisibility(View.VISIBLE);
                } else {
                    rl.setVisibility(View.GONE);
                }
            }
        });

        chkFIR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chkFIR.isChecked()) {
                    rlFIR.setVisibility(View.VISIBLE);
                } else {
                    rlFIR.setVisibility(View.GONE);
                }
            }
        });

        // Spinner element
        MaterialBetterSpinner driverSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_driver_type);
        MaterialBetterSpinner licenceSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_licence_type);
        MaterialBetterSpinner vehicleColorSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_vehicle_color);
        MaterialBetterSpinner vehicleColorTypeSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_vehicle_color_type);

        // Spinner click listener
        driverSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, driverType);
        driverSpinner.setAdapter(adapter);

        // Spinner click listener
        licenceSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, licenceType);
        licenceSpinner.setAdapter(adapter);

        // Spinner click listener
        vehicleColorSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, vehiceColor);

        vehicleColorSpinner.setAdapter(adapter);

        // Spinner click listener
        vehicleColorTypeSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, vehiceColorType);
        vehicleColorTypeSpinner.setAdapter(adapter);

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("DL & RC Details", "C230015034232");
    }

    public static dlNrcDetailsFragment newInstance() {
        dlNrcDetailsFragment f1 = new dlNrcDetailsFragment();
        f1.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtDriverDOB:
                datePicker(txtDriverDOB);
                break;
            case R.id.edtIssuanceDate:
                datePicker(txtIssuanceDate);
                break;
            case R.id.edtExpiryDate:
                datePicker(txtExpiryDate);
                break;
            case R.id.edtFIRDate:
                datePicker(edtFIRDate);
                break;
        }
    }

    public void datePicker(final TextView txt) {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                txt.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        //mDatePicker.setTitle("Select date");
        Window window = mDatePicker.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        window.setAttributes(wlp);
        mDatePicker.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        dlNrcDetailsModel model = new dlNrcDetailsModel();
        model.setDriverName(edtDriverName.getText().toString());
        model.setDriverDOB(txtDriverDOB.getText().toString());
        model.setIssuanceDate(txtIssuanceDate.getText().toString());
        model.setExpiryDate(txtExpiryDate.getText().toString());
        model.setLicenseNumber(edtLicenseNumber.getText().toString());
        model.setIssuanceRTO(edtIssuanceRTO.getText().toString());
        model.setVehicleRegNumber(edtVehicleRegNumber.getText().toString());
        model.setEngineNumber(edtEngineNumber.getText().toString());
        model.setChassisNumber(edtChassisNumber.getText().toString());
        model.setFirDate(edtFIRDate.getText().toString());
        model.setPoliceStation(edtPoliceStation.getText().toString());
        model.setFirUnderSection(edtFIRUnderSection.getText().toString());

        dbHelper.UpdatedlNrcDetailsByMasterClaimNumber(Globals.MASTER_CLAIM_NUMBER, model);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getdlNrcDetails() {

        //InsuredDetailsModel claim = dbHelper.getInsuredDetailsByMasterClaimNumber(getArguments().getString("MasterClaimNumber"));
        dlNrcDetailsModel claim = dbHelper.getDLNRCDetailsByMasterClaimNumber(Globals.MASTER_CLAIM_NUMBER);

        edtDriverName.setText(claim.getDriverName());
        txtDriverDOB.setText(claim.getDriverDOB());
        txtIssuanceDate.setText(claim.getIssuanceDate());
        txtExpiryDate.setText(claim.getExpiryDate());
        edtLicenseNumber.setText(claim.getLicenseNumber());
        edtIssuanceRTO.setText(claim.getIssuanceRTO());
        edtVehicleOwner.setText(claim.getVehicleOwner());
        edtVehicleRegNumber.setText(claim.getVehicleRegNumber());
        edtTransferDate.setText(claim.getTransferDate());
        edtVehicleMake.setText(claim.getVehicleMake());
        edtVehicleModel.setText(claim.getVehicleModel());
        edtEngineNumber.setText(claim.getEngineNumber());
        edtChassisNumber.setText(claim.getChassisNumber());
        edtRTOName.setText(claim.getRtoName());
        edtFIRDate.setText(claim.getFirDate());
        edtPoliceStation.setText(claim.getPoliceStation());
        edtFIRUnderSection.setText(claim.getFirUnderSection());
    }

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.arrow_forward:

                FragmentContainerActivity.hideKeyboard(getActivity());
                DocumentsUploadFragment fragment2 = new DocumentsUploadFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                WorkshopSelectionFragment fragment1 = new WorkshopSelectionFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.frame_container, fragment1);
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
