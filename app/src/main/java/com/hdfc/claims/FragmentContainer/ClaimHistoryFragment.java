package com.hdfc.claims.FragmentContainer;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfc.claims.FragmentContainer.Computation.ComputationSummaryLessFragment;
import com.hdfc.claims.FragmentContainer.PolicyInformation.PolicyInformationFragment;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimHistoryFragment extends android.support.v4.app.Fragment {

    private View view;
    Context context;

    EditText edtBreakinApprovalDate;

    RadioButton btnNew, btnExisting, btnPastClaims;

    int mYear, mMonth, mDay;

    RelativeLayout rl1, rl2, rl3;

    public ClaimHistoryFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_claim_history, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        return view;
    }

    private void initializeControls() {
        context = view.getContext();

        btnNew = (RadioButton) view.findViewById(R.id.btnNew);
        btnExisting = (RadioButton) view.findViewById(R.id.btnExisting);
        btnPastClaims = (RadioButton) view.findViewById(R.id.btnPastClaim);

        edtBreakinApprovalDate = (EditText) view.findViewById(R.id.edtBreakinApprovalDate);

        rl1 = (RelativeLayout) view.findViewById(R.id.layout_breakin);
        rl2 = (RelativeLayout) view.findViewById(R.id.layout_cima);
        rl3 = (RelativeLayout) view.findViewById(R.id.layout_past_claim_details);

        btnNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnNew.isChecked()) {
                    btnNew.setTextColor(Color.parseColor("#C43635"));
                    btnExisting.setTextColor(Color.parseColor("#989898"));
                    btnPastClaims.setTextColor(Color.parseColor("#989898"));
                    rl1.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                }
            }
        });

        edtBreakinApprovalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(edtBreakinApprovalDate);
            }
        });

        btnExisting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnExisting.isChecked()) {
                    btnNew.setTextColor(Color.parseColor("#989898"));
                    btnPastClaims.setTextColor(Color.parseColor("#989898"));
                    btnExisting.setTextColor(Color.parseColor("#C43635"));
                    rl1.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                }
            }
        });

        btnPastClaims.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnPastClaims.isChecked()) {
                    btnNew.setTextColor(Color.parseColor("#989898"));
                    btnExisting.setTextColor(Color.parseColor("#989898"));
                    btnPastClaims.setTextColor(Color.parseColor("#C43635"));
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                }
            }
        });

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Claim History", "C230015034232");
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
                SurveyDetailsFragment fragment2 = new SurveyDetailsFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                FragmentContainerActivity.hideKeyboard(getActivity());
                PolicyInformationFragment fragment1 = new PolicyInformationFragment();
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
