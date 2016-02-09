package com.hdfc.claims.FragmentContainer.Computation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.*;


import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ademar.phasedseekbar.PhasedSeekBar;

public class PartsSelectionFragment extends Fragment {

    List<String> listPartType;
    HashMap<String, List<String>> listDataChild;

   /* private TextView computation_data_entry_badge;
    private TextView work_selection_badge;
    private TextView assesment_badge;
    private TextView computation_summary_badge;*/

    private RadioButton btnWorkshopOne, btnWorkshopTwo;

    SharedPreferences sharedPreferences, sharedPartsSelection;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static ListView lvSelectedPartsBucket;
    public static ArrayList<String> searchedPartList = new ArrayList<String>();
    public static ArrayList<String> allPartList = new ArrayList<String>();
    Button btnClose;
    public static ImageView imgOpenDrawer;

    private FrameLayout empty_cart;

    DrawerLayout drawerLayout;


    // TODO: Rename and change types and number of parameters


    public PartsSelectionFragment() {
        // Required empty public constructor
    }

    public void toggleMenu() {
        drawerLayout.openDrawer(GravityCompat.START);

    }

    public interface AddBtnClickListener {

        public abstract void onAddBtnClick(String position);

        public abstract void onRemoveBtnClick(String position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        PhasedSeekBar psbStar = FragmentContainerActivity.psbStar;
        psbStar.setPosition(1);

        FragmentContainerActivity.hideKeyboard(getActivity());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_parts_selection, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", 1);

        sharedPartsSelection = view.getContext().getSharedPreferences("MyPartsSelection", 1);


        //drawerLayout = new CustomDrawerLayout(getActivity(),rlAbove.getHeight());


        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Part Selection", "C230015034232");

      /*  computation_data_entry_badge = (TextView) view.findViewById(R.id.computation_data_entry_badge);
        work_selection_badge = (TextView) view.findViewById(R.id.work_selection_badge);
        assesment_badge = (TextView) view.findViewById(R.id.assesment_badge);
        computation_summary_badge = (TextView) view.findViewById(R.id.computation_summary_badge);*/
        btnWorkshopOne = (RadioButton) view.findViewById(R.id.btnWorkshopOne);
        btnWorkshopTwo = (RadioButton) view.findViewById(R.id.btnWorkshopTwo);

        lvSelectedPartsBucket = (ListView) view.findViewById(R.id.lvSelectedPartsBucket);
        btnClose = (Button) view.findViewById(R.id.btnClose);

        imgOpenDrawer = (ImageView) view.findViewById(R.id.imgOpenDrawer);

        empty_cart = (FrameLayout) view.findViewById(R.id.empty_cart);

        imgOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        searchedPartList.addAll(FragmentContainerActivity.listGlassParts);
        searchedPartList.addAll(FragmentContainerActivity.listPanelOuterParts);
        searchedPartList.addAll(FragmentContainerActivity.listPlasticRubberParts);

        Map<String, ?> value = sharedPartsSelection.getAll();
        if (value.isEmpty()) {
            Log.e("Parts Selection is", "No Data Found");
        } else {
            for (Map.Entry<String, ?> entry : value.entrySet()) {

                Log.e("Parts Selection is ", entry.getKey() + entry.getValue().toString());


                if (!allPartList.contains(entry.getKey())) {
                    allPartList.add(entry.getKey());
                }
            }

            if (allPartList.size() == 0) {
                empty_cart.setVisibility(View.VISIBLE);
            } else {
                empty_cart.setVisibility(View.GONE);
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(getActivity(), allPartList);
                lvSelectedPartsBucket.setAdapter(adapter);
            }


        }


        btnWorkshopOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopOne.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#C43635"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#989898"));
                }
            }
        });

        btnWorkshopTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnWorkshopTwo.isChecked()) {
                    btnWorkshopOne.setTextColor(Color.parseColor("#989898"));
                    btnWorkshopTwo.setTextColor(Color.parseColor("#C43635"));
                }
            }
        });


      /*  computation_data_entry_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComputationDataEntryFragment fragment2 = new ComputationDataEntryFragment();


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
            }
        });

        work_selection_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkSelectionFragment fragment2 = new WorkSelectionFragment();


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();


            }
        });

        assesment_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComputationAssesmentFragment fragment2 = new ComputationAssesmentFragment();


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();


            }
        });

        computation_summary_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComputationSummaryLessFragment fragment2 = new ComputationSummaryLessFragment();


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();


            }
        });
*/

        // preparing list data
        //prepareListData();


        SearchedPartAutoCompleteAdapter adapter = new SearchedPartAutoCompleteAdapter(getActivity(),
                searchedPartList, allPartList, new AddBtnClickListener() {

            @Override
            public void onAddBtnClick(String position) {
                // TODO Auto-generated method stub

                empty_cart.setVisibility(View.GONE);
                int i = searchedPartList.indexOf(position);
                allPartList.add(searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(getActivity(), allPartList);
                lvSelectedPartsBucket.setAdapter(adapter);

            }

            @Override
            public void onRemoveBtnClick(String position) {
                // TODO Auto-generated method stub
                int i = searchedPartList.indexOf(position);
                allPartList.remove(searchedPartList.get(i));
                if (allPartList.size() == 0) {
                    empty_cart.setVisibility(View.VISIBLE);
                    SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(getActivity(), allPartList);
                    lvSelectedPartsBucket.setAdapter(adapter);

                } else {
                    empty_cart.setVisibility(View.GONE);
                    SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(getActivity(), allPartList);
                    lvSelectedPartsBucket.setAdapter(adapter);
                }
            }
        });


        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.searchPartsSelectionAutoComplete);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                autoCompleteTextView.setText("");
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                autoCompleteTextView.dismissDropDown();
            }
        });


        class task extends TimerTask {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        autoCompleteTextView.showDropDown();
                    }
                });
            }
        }
        ;


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {


                new Timer().schedule(new task(), 0, 50);
            }

        });


        return view;
    }


    private void prepareListData() {
        listPartType = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listPartType.add("Mechanical Parts");
        listPartType.add("Glass Parts");
        listPartType.add("Interior");
        listPartType.add("Electronics");
        listPartType.add("Engine Gearbox");
        listPartType.add("Steering | Suspension");

        // Adding child data
        List<String> mechanicalParts = new ArrayList<String>();
        mechanicalParts.add("Inner Panel");
        mechanicalParts.add("Lights");
        mechanicalParts.add("Rim & Tyre");
        mechanicalParts.add("Badging & Stickers");
        mechanicalParts.add("W / S Cleaning");

        List<String> glassParts = new ArrayList<String>();
        glassParts.add("Windshield");
        glassParts.add("Back Glass");
        glassParts.add("Left Mirror");
        glassParts.add("Right Mirror");
        glassParts.add("Front Lights");
        glassParts.add("Front Left Light");
        glassParts.add("Front Right Light");
        glassParts.add("Back Lights");
        glassParts.add("Back Left Light");
        glassParts.add("Back Right Light");

        List<String> interior = new ArrayList<String>();
        interior.add("Dashboard");
        interior.add("Air Conditioning");
        interior.add("Instrument Panel");
        interior.add("Air Bags");

        List<String> electronics = new ArrayList<String>();
        electronics.add("Stereo");
        electronics.add("GPS");
        electronics.add("Parking Sensors");

        List<String> engineGearBox = new ArrayList<String>();
        engineGearBox.add("Gear Box");
        engineGearBox.add("Transmission");
        engineGearBox.add("Shaft");
        engineGearBox.add("Piston/cylinder");

        List<String> steeringSuspension = new ArrayList<String>();
        steeringSuspension.add("Steering");
        steeringSuspension.add("Front Suspension");
        steeringSuspension.add("Rear Suspension");

        listDataChild.put(listPartType.get(0), mechanicalParts); // Header, Child data
        listDataChild.put(listPartType.get(1), glassParts);
        listDataChild.put(listPartType.get(2), interior);
        listDataChild.put(listPartType.get(3), electronics);
        listDataChild.put(listPartType.get(4), engineGearBox);
        listDataChild.put(listPartType.get(5), steeringSuspension);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.arrow_forward:
                //dismiss();
                WorkSelectionFragment fragment2 = new WorkSelectionFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                //checkForPreferences();
                ComputationDataEntryFragment fragment1 = new ComputationDataEntryFragment();
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

    public void checkForPreferences() {

        Log.e("Check", "In Check of Preferences");
        Map<String, ?> value = sharedPreferences.getAll();
        if (value.isEmpty()) {
            Log.e("Value ", value + "");
            Intent intent = new Intent(getActivity(), PointOfImpactActivity.class);
            Log.e("Check", "Value is Not Null");

            startActivity(intent);
        } else {
            fragment = new FragmentPointOfImpact();
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
