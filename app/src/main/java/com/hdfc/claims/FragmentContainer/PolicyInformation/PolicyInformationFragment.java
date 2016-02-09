package com.hdfc.claims.FragmentContainer.PolicyInformation;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hdfc.claims.FragmentContainer.ClaimHistoryFragment;
import com.hdfc.claims.FragmentContainer.DocumentUpload.DocumentsUploadFragment;
import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.InsuredDetailsFragment;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolicyInformationFragment extends DialogFragment {

    private View view;
    Context context;

    CheckBox chkPolicyInfo,chkEndorsement,chkClaimInfo;
    LinearLayout policyInfo,endorsement,claimInfo;


    ExpandablePolicyInfoListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listInfoType;
    HashMap<String, List<String>> listInfoDataChild;
    HashMap<String, List<String>> listInfoDataChildValue;

    public PolicyInformationFragment() {
        // Required empty public constructor
    }

    public static PolicyInformationFragment newInstance() {
        PolicyInformationFragment f1 = new PolicyInformationFragment();
        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static PolicyInformationFragment newInstance(int sectionNumber) {
        PolicyInformationFragment fragment = new PolicyInformationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        view = inflater.inflate(R.layout.fragment_policy_information, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

//        prepareListData();

        Display display =  ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return view;
    }

    //Convert pixel to dip
    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void prepareListData() {
        {
            listInfoType = new ArrayList<String>();
            listInfoDataChild = new HashMap<String, List<String>>();
            listInfoDataChildValue = new HashMap<String,List<String>>();

            // Adding child data
            listInfoType.add("Basic Info");
            listInfoType.add("Policy & Claim Details");
            listInfoType.add("Past Claim Details");

            // Adding child data
            List<String> basicInfo = new ArrayList<String>();
            basicInfo.add("Inner Panel" + "");
            basicInfo.add("Lights");
            basicInfo.add("Rim & Tyre");
            basicInfo.add("Badging & Stickers");
            basicInfo.add("W / S Cleaning");
            //Adding Child Values
            List<String> basicInfoValue = new ArrayList<String>();
            basicInfo.add("Inner Panel" + "");
            basicInfo.add("Lights");
            basicInfo.add("Rim & Tyre");
            basicInfo.add("Badging & Stickers");
            basicInfo.add("W / S Cleaning");

            List<String> policy_ClaimDetails = new ArrayList<String>();
            policy_ClaimDetails.add("Windshield");
            policy_ClaimDetails.add("Back Glass");
            policy_ClaimDetails.add("Left Mirror");
            policy_ClaimDetails.add("Right Mirror");
            policy_ClaimDetails.add("Front Lights");
            policy_ClaimDetails.add("Front Left Light");
            policy_ClaimDetails.add("Front Right Light");
            policy_ClaimDetails.add("Back Lights");
            policy_ClaimDetails.add("Back Left Light");
            policy_ClaimDetails.add("Back Right Light");

            List<String> policy_ClaimDetailsValue = new ArrayList<String>();
            policy_ClaimDetails.add("Windshield");
            policy_ClaimDetails.add("Back Glass");
            policy_ClaimDetails.add("Left Mirror");
            policy_ClaimDetails.add("Right Mirror");
            policy_ClaimDetails.add("Front Lights");
            policy_ClaimDetails.add("Front Left Light");
            policy_ClaimDetails.add("Front Right Light");
            policy_ClaimDetails.add("Back Lights");
            policy_ClaimDetails.add("Back Left Light");
            policy_ClaimDetails.add("Back Right Light");

            List<String> pastClaimDetails = new ArrayList<String>();
            pastClaimDetails.add("Dashboard");
            pastClaimDetails.add("Air Conditioning");
            pastClaimDetails.add("Instrument Panel");
            pastClaimDetails.add("Air Bags");

            List<String> pastClaimDetailsValue = new ArrayList<String>();
            pastClaimDetails.add("Dashboard");
            pastClaimDetails.add("Air Conditioning");
            pastClaimDetails.add("Instrument Panel");
            pastClaimDetails.add("Air Bags");

            List<String> cimaRemarks = new ArrayList<String>();
            cimaRemarks.add("Stereo");
            cimaRemarks.add("GPS");
            cimaRemarks.add("Parking Sensors");

            List<String> cimaRemarksValue = new ArrayList<String>();
            cimaRemarks.add("Stereo");
            cimaRemarks.add("GPS");
            cimaRemarks.add("Parking Sensors");

            List<String> breakinRemarks = new ArrayList<String>();
            breakinRemarks.add("Gear Box");
            breakinRemarks.add("Transmission");
            breakinRemarks.add("Shaft");
            breakinRemarks.add("Piston/cylinder");

            List<String> breakinRemarksValue = new ArrayList<String>();
            breakinRemarks.add("Gear Box");
            breakinRemarks.add("Transmission");
            breakinRemarks.add("Shaft");
            breakinRemarks.add("Piston/cylinder");

            listInfoDataChild.put(listInfoType.get(0), basicInfo); // Header, Child data
            listInfoDataChild.put(listInfoType.get(1), policy_ClaimDetails);
            listInfoDataChild.put(listInfoType.get(2), pastClaimDetails);

            listInfoDataChildValue.put(listInfoType.get(0), basicInfoValue); // Header, Child data
            listInfoDataChildValue.put(listInfoType.get(1), policy_ClaimDetailsValue);
            listInfoDataChildValue.put(listInfoType.get(2), pastClaimDetailsValue);

            listAdapter = new ExpandablePolicyInfoListAdapter(getActivity(), listInfoType, listInfoDataChild, listInfoDataChildValue);

//            expListView.setAdapter(listAdapter);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    public void initializeControls() {
        context = view.getContext();

        // get the listview
       // expListView = (ExpandableListView) view.findViewById(R.id.ExpandableClaimInfo);

        chkPolicyInfo=(CheckBox)view.findViewById(R.id.chkPolicyInfo);
        chkEndorsement=(CheckBox)view.findViewById(R.id.chkEndorsement);
        chkClaimInfo=(CheckBox)view.findViewById(R.id.chkClaimInfo);


        endorsement=(LinearLayout)view.findViewById(R.id.endorsement);
        policyInfo=(LinearLayout)view.findViewById(R.id.policyInfo);
        claimInfo=(LinearLayout)view.findViewById(R.id.claimInfo);



        claimInfo.setVisibility(View.VISIBLE);



        chkPolicyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chkPolicyInfo.isChecked()) {
                    policyInfo.setVisibility(View.VISIBLE);
                    endorsement.setVisibility(View.GONE);
                    claimInfo.setVisibility(View.GONE);
                    chkEndorsement.setChecked(false);
                    chkClaimInfo.setChecked(false);
                }
                else
                {
                    policyInfo.setVisibility(View.GONE);
                }
            }
        });
        chkEndorsement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chkEndorsement.isChecked()){
                    endorsement.setVisibility(View.VISIBLE);
                    policyInfo.setVisibility(View.GONE);
                    claimInfo.setVisibility(View.GONE);
                    chkPolicyInfo.setChecked(false);
                    chkClaimInfo.setChecked(false);

                }
                else
                {
                    endorsement.setVisibility(View.GONE);
                }
            }
        });

        chkClaimInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkClaimInfo.isChecked()){
                    claimInfo.setVisibility(View.VISIBLE);
                    policyInfo.setVisibility(View.GONE);
                    endorsement.setVisibility(View.GONE);
                    chkPolicyInfo.setChecked(false);
                    chkEndorsement.setChecked(false);

                }
                else
                {
                    claimInfo.setVisibility(View.GONE);
                }
            }
        });



        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Policy & Claims Details", "C230015034232");
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
                //dismiss();
                ClaimHistoryFragment fragment2 = new ClaimHistoryFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                InsuredDetailsFragment fragment1 = new InsuredDetailsFragment();
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
