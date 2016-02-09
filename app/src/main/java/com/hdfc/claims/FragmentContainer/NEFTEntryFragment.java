package com.hdfc.claims.FragmentContainer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

public class NEFTEntryFragment extends Fragment implements View.OnClickListener {

    private View view;
    Context context;

    int mYear, mMonth, mDay;

    EditText edtbenAddress, edtbenName, edtPhoneNumber, edtMobileNumber, edtMicrCode, edtAccountName, edtAccountNumber, edtBankName, edtBranchName, edtEmailID, edtIFSC;

    RadioButton btnNew, btnExisting, btnRejected;

    public NEFTEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.arrow_forward).setVisible(false);
        super.onPrepareOptionsMenu(menu);
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
        view = inflater.inflate(R.layout.fragment_neft_entry, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        return view;
    }

    private void initializeControls() {
        context = view.getContext();

        edtbenAddress = (EditText) view.findViewById(R.id.edtBeneficiaryAddress);
        edtbenName = (EditText) view.findViewById(R.id.edtBeneficiaryName);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtMobileNumber = (EditText) view.findViewById(R.id.edtMobileNumber);
        edtMicrCode = (EditText) view.findViewById(R.id.edtMICRCode);
        edtAccountName = (EditText) view.findViewById(R.id.edtAccountName);
        edtAccountNumber = (EditText) view.findViewById(R.id.edtAccountNumber);
        edtBankName = (EditText) view.findViewById(R.id.edtBankName);
        edtBranchName = (EditText) view.findViewById(R.id.edtBranchName);
        edtEmailID = (EditText) view.findViewById(R.id.edtEmailID);
        edtIFSC = (EditText) view.findViewById(R.id.edtIFSCCode);

        btnNew = (RadioButton) view.findViewById(R.id.btnNew);
        btnExisting = (RadioButton) view.findViewById(R.id.btnExisting);
        btnRejected = (RadioButton) view.findViewById(R.id.btnRejected);

        btnNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnNew.isChecked()) {
                    btnNew.setTextColor(Color.parseColor("#C43635"));
                    
                    btnExisting.setTextColor(Color.parseColor("#989898"));
                    btnRejected.setTextColor(Color.parseColor("#989898"));
                }
            }
        });

        btnExisting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnExisting.isChecked()) {
                    btnRejected.setTextColor(Color.parseColor("#989898"));
                    btnNew.setTextColor(Color.parseColor("#989898"));
                    btnExisting.setTextColor(Color.parseColor("#C43635"));
                }
            }
        });

        btnRejected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnRejected.isChecked()) {
                    btnExisting.setTextColor(Color.parseColor("#989898"));
                    btnNew.setTextColor(Color.parseColor("#989898"));
                    btnRejected.setTextColor(Color.parseColor("#C43635"));
                }
            }
        });

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("NEFT Entry", "C230015034232");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
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
