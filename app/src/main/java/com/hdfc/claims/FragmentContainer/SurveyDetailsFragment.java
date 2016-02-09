package com.hdfc.claims.FragmentContainer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.Map;

public class SurveyDetailsFragment extends DialogFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private View view;
    Context context;

    EditText imgSurveyStartDate;
    EditText imgEstimateDate;
    EditText imgReInspectionDate;
    EditText imgLastDocumentReadDate;

    TextView txtSurveyStartDate;
    TextView txtEstimateDate;
    TextView txtReInspectionDate;
    TextView txtLastDocumentReadDate;

    Button btnClear;

    int mYear, mMonth, mDay;

    ImageView previousImg, nextImg;

    private String[] lossReason = {"Data1", "Data2", "Data3", "Data4"};

    SharedPreferences sharedPreferences;

    public SurveyDetailsFragment() {
        // Required empty public constructor
    }

    public static SurveyDetailsFragment newInstance() {
        SurveyDetailsFragment f1 = new SurveyDetailsFragment();
        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static SurveyDetailsFragment newInstance(int sectionNumber) {
        SurveyDetailsFragment fragment = new SurveyDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        view = inflater.inflate(R.layout.fragment_survey_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initializeControls();

        return view;
    }

    private void initializeControls() {

        context = view.getContext();

        sharedPreferences=context.getSharedPreferences("MySharedPref", 1);

        imgSurveyStartDate = (EditText) view.findViewById(R.id.edtSurveyStartDate);
        imgEstimateDate = (EditText) view.findViewById(R.id.edtEstimateDate);
        imgReInspectionDate = (EditText) view.findViewById(R.id.edtReInspectionDate);
        imgLastDocumentReadDate = (EditText) view.findViewById(R.id.edtLastDocumentReadDate);

        txtSurveyStartDate = (TextView) view.findViewById(R.id.edtSurveyStartDate);
        txtEstimateDate = (TextView) view.findViewById(R.id.edtEstimateDate);
        txtReInspectionDate = (TextView) view.findViewById(R.id.edtReInspectionDate);
        txtLastDocumentReadDate = (TextView) view.findViewById(R.id.edtLastDocumentReadDate);

        view.findViewById(R.id.popup_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        imgSurveyStartDate.setOnClickListener(this);
        imgEstimateDate.setOnClickListener(this);
        imgReInspectionDate.setOnClickListener(this);
        imgLastDocumentReadDate.setOnClickListener(this);

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Survey Details", "C230015034232");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtSurveyStartDate:
                datePicker(txtSurveyStartDate);
                break;
            case R.id.edtEstimateDate:
                datePicker(txtEstimateDate);
                break;
            case R.id.edtReInspectionDate:
                datePicker(txtReInspectionDate);
                break;
            case R.id.edtLastDocumentReadDate:
                datePicker(txtLastDocumentReadDate);
                break;
        }
    }

    public void datePicker(final TextView txt) {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                selectedmonth = selectedmonth + 1 ;
                txt.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.arrow_forward:

                checkForPreferences();

                /*FragmentContainerActivity.hideKeyboard(getActivity());
                FragmentPointOfImpact fragment2 = new FragmentPointOfImpact();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();*/
                return true;

            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                ClaimHistoryFragment fragment1 = new ClaimHistoryFragment();
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

    Fragment fragment = null;

    public void checkForPreferences(){

        Log.e("Check", "In Check of Preferences");
        Map<String, ?> value=sharedPreferences.getAll();
        if(value.isEmpty())
        {
            Log.e("Value ", value+"");
            Intent intent=new Intent(getActivity(),PointOfImpactActivity.class);
            Log.e("Check","Value is Not Null");
            startActivity(intent);
            ((FragmentContainerActivity)context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        else
        {
            fragment = new FragmentPointOfImpact();
            fragmentManager = getActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
        }
    }
}
