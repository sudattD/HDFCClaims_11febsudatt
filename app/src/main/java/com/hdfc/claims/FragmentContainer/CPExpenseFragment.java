package com.hdfc.claims.FragmentContainer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CPExpenseFragment extends DialogFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private View view;
    Context context;

    int mYear, mMonth, mDay;



    private String[] payeeType = {"Data1","Data2"};
    private String[] paymentModeType = {"Data3","Data4"};

    EditText edtPayeeName,edtPayeeAddress,edtProfFees,edtExpense,edtServiceTax,edtTotalBill,edtTDS,edtChequeAmount,edtExpenseBillDate;



    ArrayAdapter<String> adapter;

    public CPExpenseFragment() {
        // Required empty public constructor
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
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.arrow_forward).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cp_expense, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        return view;
    }

    private void initializeControls() {
        context = view.getContext();

        edtChequeAmount = (EditText)view.findViewById(R.id.edtChequeAmount);
        edtExpense = (EditText)view.findViewById(R.id.edtExpense);
        edtExpenseBillDate = (EditText)view.findViewById(R.id.edtExpenseBillDate);
        edtPayeeAddress = (EditText)view.findViewById(R.id.edtPayeeAddressCPExpense);
        edtPayeeName = (EditText)view.findViewById(R.id.edtPayeeNameCPExpense);
        edtProfFees = (EditText)view.findViewById(R.id.edtProfFees);
        edtServiceTax = (EditText)view.findViewById(R.id.edtServiceTaxCPExpense);
        edtTDS = (EditText)view.findViewById(R.id.edtTDSCPExpense);
        edtTotalBill = (EditText)view.findViewById(R.id.edtTotalBill);

        // Spinner element
        MaterialBetterSpinner payeeTypeSpinner = (MaterialBetterSpinner)view.findViewById(R.id.spinner_payee_type_cp_expense);
        MaterialBetterSpinner paymentModeTypeSpinner = (MaterialBetterSpinner)view.findViewById(R.id.spinner_payment_mode_type);

        // Spinner click listener
        //payeeTypeSpinner.setOnItemSelectedListener(this);
        //paymentModeTypeSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        adapter = new ArrayAdapter<String>(getActivity(),  R.layout.spinner_layout, payeeType);
        payeeTypeSpinner.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(),  R.layout.spinner_layout, paymentModeType);
        paymentModeTypeSpinner.setAdapter(adapter);

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("CP Expense", "C230015034232");
    }


    public static CPExpenseFragment newInstance() {
        CPExpenseFragment f1 = new CPExpenseFragment();
        f1.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {



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
            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                CPLossFragment fragment1 = new CPLossFragment();
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
