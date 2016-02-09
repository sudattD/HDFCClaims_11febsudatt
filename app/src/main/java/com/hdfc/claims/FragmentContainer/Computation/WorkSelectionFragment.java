package com.hdfc.claims.FragmentContainer.Computation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.LinkedHashMap;
import java.util.Map;

import ademar.phasedseekbar.PhasedSeekBar;


public class WorkSelectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private Context context;
    private ListView partTypeListView;
    private WorkSelectionListAdapter adapter;
    private LinkedHashMap<String, WorkSelectionListModel> partsTypeList;

   /* private TextView part_selection_badge;
    private TextView computation_data_entry_badge;
    private TextView assesment_badge;
    private TextView computation_summary_badge;
*/

    private RadioButton btnWorkshopOne, btnWorkshopTwo;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    SharedPreferences sharedPreferences;


    public WorkSelectionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
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

        PhasedSeekBar psbStar = FragmentContainerActivity.psbStar;
        psbStar.setPosition(2);

        FragmentContainerActivity.hideKeyboard(getActivity());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_selection, container, false);

        sharedPreferences=view.getContext().getSharedPreferences("MySharedPref", 1);

        context = view.getContext();
        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Work Selection", "C230015034232");
        partTypeListView = (ListView)view.findViewById(R.id.listViewPartsType);
        partsTypeList = new LinkedHashMap<String,WorkSelectionListModel>();

       /* part_selection_badge = (TextView) view.findViewById(R.id.part_selection_badge);
        computation_data_entry_badge = (TextView) view.findViewById(R.id.computation_data_entry_badge);
        assesment_badge = (TextView) view.findViewById(R.id.assesment_badge);
        computation_summary_badge = (TextView) view.findViewById(R.id.computation_summary_badge);*/

        btnWorkshopOne = (RadioButton) view.findViewById(R.id.btnWorkshopOne);
        btnWorkshopTwo = (RadioButton) view.findViewById(R.id.btnWorkshopTwo);

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


       /* part_selection_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PartsSelectionFragment fragment2 = new PartsSelectionFragment();


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();


            }
        });

        computation_data_entry_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComputationDataEntryFragment fragment2 = new ComputationDataEntryFragment();




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
        });*/

        generateQueryList();





        return view;
    }

    private void generateQueryList() {

        for(int i=0;i<11;i++)
        {
            WorkSelectionListModel model = new WorkSelectionListModel();
            model.setPartName(getResources().getStringArray(R.array.partNameArray)[i]);

            partsTypeList.put(model.getPartName(),model);
        }

        adapter = new WorkSelectionListAdapter(getActivity(),partsTypeList);
        partTypeListView.setAdapter(adapter);
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
                ComputationAssesmentFragment fragment2 = new ComputationAssesmentFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                //checkForPreferences();

                PartsSelectionFragment fragment1 = new PartsSelectionFragment();
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

}
