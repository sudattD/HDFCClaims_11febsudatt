package com.hdfc.claims.FragmentContainer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hdfc.claims.FragmentContainer.DocumentUpload.DocumentsUploadFragment;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkshopSelectionFragment extends DialogFragment implements View.OnClickListener{

    private View view;
    Context context;

    int mYear, mMonth, mDay;
    private int hour, minute;

    ImageView previousImg, nextImg;

    ImageView imgReportDate;
    ImageView imgInvoiceDate;

    EditText txtReportDate;
    EditText txtInvoiceDate;
    EditText txtReportTime;

    EditText edtWorkshopAddress, edtInvoiceNo, edtInvoiceAmount, edtRepair;
    Button btnSet;

    RadioButton btnWorkshopOne, btnWorkshopTwo;

    RelativeLayout rlTimePicker;

    TimePicker timePicker;

    RadioButton ws2;

    private String[] workshopName = {"Data5", "Data6", "Data7", "Data8"};

    ArrayAdapter<String> adapter;

    public WorkshopSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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

        view = inflater.inflate(R.layout.fragment_workshop_selection, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        return view;
    }

    private void initializeControls() {
        context = view.getContext();

        rlTimePicker = (RelativeLayout) view.findViewById(R.id.layout_time_picker);

        txtReportDate = (EditText) view.findViewById(R.id.edtReportDate);
        txtInvoiceDate = (EditText) view.findViewById(R.id.edtInvoiceDate);
        txtReportTime = (EditText) view.findViewById(R.id.edtReportTime);

        txtReportDate.setOnClickListener(this);
        txtInvoiceDate.setOnClickListener(this);
        txtReportTime.setOnClickListener(this);

        edtInvoiceAmount = (EditText) view.findViewById(R.id.edtInvoiceAmount);
        edtInvoiceNo = (EditText) view.findViewById(R.id.edtInvoiceNumber);
        edtWorkshopAddress = (EditText) view.findViewById(R.id.edtWorkshopAddress);
        edtRepair = (EditText) view.findViewById(R.id.edtRepairDetails);

        timePicker = (TimePicker) view.findViewById(R.id.timePicker1);

        btnSet = (Button) view.findViewById(R.id.btnSetTime);
        btnSet.setOnClickListener(this);

        ws2 = (RadioButton) view.findViewById(R.id.btnWorkshopTwo);
        ws2.setEnabled(false);

//        imgReportDate.setOnClickListener(this);
//        imgInvoiceDate.setOnClickListener(this);

        MaterialBetterSpinner workshopNameSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_workshop_name);
       // MaterialBetterSpinner workshopSelectionSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_workshop_selection);

        // Spinner Drop down elements
        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,workshopName);
        ArrayList<String> aOptions = new ArrayList<String>();
        aOptions.add("Vehicle Not Reported");
        aOptions.add("Create New Workshop");
        aOptions.add("Survey at Residence");
        aOptions.add("Select Workshop");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, workshopName);
        workshopNameSpinner.setAdapter(adapter);


        btnWorkshopOne = (RadioButton) view.findViewById(R.id.btnWorkshopOne);
        btnWorkshopTwo = (RadioButton) view.findViewById(R.id.btnWorkshopTwo);

        btnWorkshopOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopOne.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#C43635"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        btnWorkshopTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopTwo.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#000000"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#C43635"));
                }
            }
        });

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Workshop Details", "C230015034232");
    }

    public static WorkshopSelectionFragment newInstance() {
        WorkshopSelectionFragment f1 = new WorkshopSelectionFragment();
        f1.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtReportDate:
                datePicker(txtReportDate);
                break;
            case R.id.edtInvoiceDate:
                datePicker(txtInvoiceDate);
                break;
            case R.id.edtReportTime:
                rlTimePicker.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSetTime:
                txtReportTime.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                rlTimePicker.setVisibility(View.GONE);
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
                txt.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
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
                dlNrcDetailsFragment fragment2 = new dlNrcDetailsFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                FragmentPointOfImpact fragment1 = new FragmentPointOfImpact();
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
