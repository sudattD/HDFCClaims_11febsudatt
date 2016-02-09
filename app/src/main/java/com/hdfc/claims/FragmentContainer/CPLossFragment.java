package com.hdfc.claims.FragmentContainer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.hdfc.claims.FragmentContainer.Computation.ComputationSummaryLessFragment;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CPLossFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener  {

    private View view;
    Context context;

    int mYear, mMonth, mDay;

    ImageView previousImg, nextImg;

    EditText edtPayeeName,edtPayeeAddress,edtServiceTax,edtTDS,edtNetAmount;

    Button btnClear;

    private String[] payeeType = {"Data1","Data2", "Data3","Data4"};
    private String[] payType = {"Data5","Data6", "Data7","Data8"};
    private String[] paymentMode = {"NEFT","Cheque", "DD","RTGS"};

    ArrayAdapter<String> adapter;

    public CPLossFragment() {
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

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cp_loss, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    private void initializeControls() {
        context = view.getContext();

        // Spinner element
        MaterialBetterSpinner payeeTypeSpinner = (MaterialBetterSpinner)view.findViewById(R.id.spinner_payee_type_cp_loss);
        MaterialBetterSpinner payTypeSpinner = (MaterialBetterSpinner)view.findViewById(R.id.spinner_pay_type);
        MaterialBetterSpinner paymentModeSpinner = (MaterialBetterSpinner)view.findViewById(R.id.spinner_payment_mode);


        edtPayeeAddress = (EditText)view.findViewById(R.id.edtPayeeAddressCPLoss);
        edtPayeeName = (EditText)view.findViewById(R.id.edtPayeeNameCPLoss);
        edtServiceTax = (EditText)view.findViewById(R.id.edtServiceTaxCPLoss);
        edtTDS = (EditText)view.findViewById(R.id.edtTDSCPLoss);
        edtNetAmount = (EditText)view.findViewById(R.id.edtNetAmount);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(),  R.layout.spinner_layout, payeeType);
        payeeTypeSpinner.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(getActivity(),  R.layout.spinner_layout, payType);
        payTypeSpinner.setAdapter(adapter);

        payTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentContainerActivity.hideKeyboard(getActivity());
                NEFTEntryFragment fragment2 = new NEFTEntryFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
            }
        });

        adapter = new ArrayAdapter<String>(getActivity(),  R.layout.spinner_layout, paymentMode);
        paymentModeSpinner.setAdapter(adapter);

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("CP Loss", "C230015034232");
    }

    @Override
    public void onClick(View view) {
        {
            switch (view.getId()) {
            }
        }
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
                FragmentContainerActivity.hideKeyboard(getActivity());
                CPExpenseFragment fragment2 = new CPExpenseFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                ComputationSummaryLessFragment fragment1 = new ComputationSummaryLessFragment();
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
