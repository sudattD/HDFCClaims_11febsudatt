package com.hdfc.claims.FragmentContainer.Computation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Widgets.ExpandedListView;

import java.util.LinkedHashMap;
import java.util.Map;

import ademar.phasedseekbar.PhasedSeekBar;


public class ComputationAssesmentFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private Context context;

   /* private TextView part_selection_badge;
    private TextView work_selection_badge;
    private TextView computation_data_entry_badge;
    private TextView computation_summary_badge;*/

    private RadioButton btnWorkshopOne, btnWorkshopTwo;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    SharedPreferences sharedPreferences;

    private ExpandedListView lvMetallicParts, lvRemoveRefit, lvRubberPlasticParts, lvGlassParts, lvRepair, lvTowing, lvPaintParts;

    private MetallicPartsListAdapter adapter;
    private LabourChargesListAdapter labourAdapter;
    private LinkedHashMap<String, MetallicPartsListModel> metallicPartsList;

    private LinkedHashMap<String, LabourChargesListModel> labourChargesList;

    public static RelativeLayout keyboardLayout;
    public static Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnPoint, btnBack, btnOK;
    public static EditText edtCustomEdit;
    public static String customString = "";

    private SearchViewCompat sv;

    LinearLayout ll;

    public ComputationAssesmentFragment() {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.show_assessment);
        item.setVisible(true);
        getActivity().invalidateOptionsMenu();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        PhasedSeekBar psbStar = FragmentContainerActivity.psbStar;
        psbStar.setPosition(3);

        FragmentContainerActivity.hideKeyboard(getActivity());

        View view = inflater.inflate(R.layout.fragment_computation_assesment, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", 1);

        context = view.getContext();

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Assessment", "C230015034232");

        metallicPartsList = new LinkedHashMap<String, MetallicPartsListModel>();
        labourChargesList = new LinkedHashMap<String, LabourChargesListModel>();

        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        btn7 = (Button) view.findViewById(R.id.btn7);
        btn8 = (Button) view.findViewById(R.id.btn8);
        btn9 = (Button) view.findViewById(R.id.btn9);
        btn0 = (Button) view.findViewById(R.id.btn0);
        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnPoint = (Button) view.findViewById(R.id.btnPoint);
        btnBack = (Button) view.findViewById(R.id.btnBack);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        edtCustomEdit = (EditText) view.findViewById(R.id.edtCustomEdit);
        keyboardLayout = (RelativeLayout) view.findViewById(R.id.custKeyboard);


        lvMetallicParts = (ExpandedListView) view.findViewById(R.id.lvMetallicParts);
        lvRubberPlasticParts = (ExpandedListView) view.findViewById(R.id.lvRubberPlasticParts);
        lvGlassParts = (ExpandedListView) view.findViewById(R.id.lvGlassParts);
        lvRemoveRefit = (ExpandedListView) view.findViewById(R.id.lvRemoveRefit);
        lvRepair = (ExpandedListView) view.findViewById(R.id.lvRepair);
        lvTowing = (ExpandedListView) view.findViewById(R.id.lvTowing);
        lvPaintParts = (ExpandedListView) view.findViewById(R.id.lvPaintParts);

        generateQueryList();
        generatePaintList();

      /*  part_selection_badge = (TextView) view.findViewById(R.id.part_selection_badge);
        work_selection_badge = (TextView) view.findViewById(R.id.work_selection_badge);
        computation_data_entry_badge = (TextView) view.findViewById(R.id.computation_data_entry_badge);
        computation_summary_badge = (TextView) view.findViewById(R.id.computation_summary_badge);
*/
        btnWorkshopOne = (RadioButton) view.findViewById(R.id.btnWorkshopOne);
        btnWorkshopTwo = (RadioButton) view.findViewById(R.id.btnWorkshopTwo);

        ll = (LinearLayout) view.findViewById(R.id.ll_hide_show);

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


        /*part_selection_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PartsSelectionFragment fragment2 = new PartsSelectionFragment();


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

        return view;
    }

    private void generatePaintList() {

        for (int i = 0; i < 2; i++) {
            LabourChargesListModel model = new LabourChargesListModel();


            model.setPartName(getResources().getStringArray(R.array.metallicPartName)[i]);
            model.setBilledAmount(getResources().getStringArray(R.array.metallicBilledAmount)[i]);
            model.setPaintLabour(getResources().getStringArray(R.array.metallicBilledAmount)[i]);
            model.setPaintMaterial(getResources().getStringArray(R.array.metallicBilledAmount)[i]);
            model.setAssesmentSum(getResources().getStringArray(R.array.metallicBilledAmount)[i]);
            model.setNetAmount(getResources().getStringArray(R.array.metallicNetAmount)[i]);
            labourChargesList.put(model.getPartName(), model);
        }

        labourAdapter = new LabourChargesListAdapter(getActivity(), labourChargesList);
        lvPaintParts.setAdapter(labourAdapter);
    }

    private void generateQueryList() {

        for (int i = 0; i < 4; i++) {
            MetallicPartsListModel model = new MetallicPartsListModel();
            model.setPartName(getResources().getStringArray(R.array.metallicPartName)[i]);
            model.setAssessedName(getResources().getStringArray(R.array.metallicAssessedName)[i]);
            model.setBilledAmount(getResources().getStringArray(R.array.metallicBilledAmount)[i]);
            model.setNetAmount(getResources().getStringArray(R.array.metallicNetAmount)[i]);
            metallicPartsList.put(model.getPartName(), model);
        }

        adapter = new MetallicPartsListAdapter(getActivity(), metallicPartsList);
        lvMetallicParts.setAdapter(adapter);
        lvRemoveRefit.setAdapter(adapter);
        lvRubberPlasticParts.setAdapter(adapter);
        lvGlassParts.setAdapter(adapter);
        lvRepair.setAdapter(adapter);
        lvTowing.setAdapter(adapter);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
                ComputationSummaryLessFragment fragment2 = new ComputationSummaryLessFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                //checkForPreferences();
                WorkSelectionFragment fragment1 = new WorkSelectionFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.frame_container, fragment1);
                fragmentTransaction.commit();
                return true;

            case R.id.show_assessment:
                //checkForPreferences();
                if (ll.getVisibility() == View.VISIBLE) {
                    ll.setVisibility(View.GONE);
                } else {
                    ll.setVisibility(View.VISIBLE);
                }
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Toast.makeText(getContext(), "1", Toast.LENGTH_LONG).show();
                customString += "1";
                edtCustomEdit.setText(customString);
                break;
            case R.id.btn2:
                customString += "2";
                edtCustomEdit.setText(customString);
                break;
            case R.id.btn3:
                customString += "3";
                edtCustomEdit.setText(customString);
                break;
            case R.id.btnBack:
                if (customString.length() > 0) {
                    customString = customString.substring(0, customString.length() - 1);
                }
                edtCustomEdit.setText(customString);
                break;
        }
    }


}
