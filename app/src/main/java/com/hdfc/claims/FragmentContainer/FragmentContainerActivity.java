package com.hdfc.claims.FragmentContainer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hdfc.claims.FragmentContainer.Computation.ComputationAssesmentFragment;
import com.hdfc.claims.FragmentContainer.Computation.ComputationDataEntryFragment;
import com.hdfc.claims.FragmentContainer.Computation.ComputationSummaryLessFragment;
import com.hdfc.claims.FragmentContainer.Computation.WorkSelectionFragment;
import com.hdfc.claims.FragmentContainer.DocumentUpload.DocumentsUploadFragment;
import com.hdfc.claims.FragmentContainer.PolicyInformation.PolicyInformationFragment;
import com.hdfc.claims.FragmentContainer.PointOfImpact.FragmentPointOfImpact;
import com.hdfc.claims.FragmentContainer.Computation.NavigationDrawerListAdapter;
import com.hdfc.claims.FragmentContainer.Computation.PartsSelectionFragment;
import com.hdfc.claims.FragmentContainer.PointOfImpact.PointOfImpactActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.FragmentContainer.Computation.SelectedPartBucketAdapter;
import com.hdfc.claims.Utilities.DatabaseUtils;
import com.hdfc.claims.Widgets.ExpandedListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ademar.phasedseekbar.PhasedInteractionListener;
import ademar.phasedseekbar.PhasedListener;
import ademar.phasedseekbar.PhasedSeekBar;
import ademar.phasedseekbar.SimplePhasedAdapter;

public class FragmentContainerActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle mbundle;

    private DatabaseUtils dbHelper;

    public static DrawerLayout drawerLayout;
    public static PhasedSeekBar psbStar;
    private ExpandedListView lvPanelOuterParts;
    private ExpandedListView lvGlassParts;
    private ExpandedListView lvPlasticRubberParts;
    private String[] lvMenuItems;
    private ImageView imgOpenDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout frame_container;
    private RelativeLayout rlRight;
    private ImageView imgCloseDrawer, imgHome;

    HorizontalScrollView sv;

    public static boolean isHomeClick = false;

    LinearLayout layoutInsuredDetails, layoutPolicyInfo, layoutDocumentUpload, layoutWorkshopSelection, layoutSurveyDetails, layoutDLnRCDetails, layoutPointOfImpact, layoutComputationEntry, layoutComputationSummary, layoutClaimHistory, layoutCPLoss, layoutCPExpense;


    SharedPreferences sharedPreferences;

    public static List<String> listPanelOuterParts;
    public static List<String> listGlassParts;
    public static List<String> listPlasticRubberParts;

    private CheckBox checkboxPanelOuterParts, checkboxGlassParts, checkboxPlasticRubberParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", 1);
        dbHelper = new DatabaseUtils(this);

        sv = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        ImageView right = (ImageView) findViewById(R.id.forward);

        imgHome = (ImageView) findViewById(R.id.home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHomeClick = true;
                finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down);
            }
        });

        layoutInsuredDetails = (LinearLayout) findViewById(R.id.layoutInsuredDetails);
        layoutPolicyInfo = (LinearLayout) findViewById(R.id.layoutPolicyInfo);
        layoutDocumentUpload = (LinearLayout) findViewById(R.id.layoutDocumentsUpload);
        layoutWorkshopSelection = (LinearLayout) findViewById(R.id.layoutWorkshopSelection);
        layoutSurveyDetails = (LinearLayout) findViewById(R.id.layoutSurveyDetails);
        layoutDLnRCDetails = (LinearLayout) findViewById(R.id.layoutDLnRCDetails);
        layoutPointOfImpact = (LinearLayout) findViewById(R.id.layoutPointOfImpact);
        layoutComputationEntry = (LinearLayout) findViewById(R.id.layoutComputationEntry);
        layoutComputationSummary = (LinearLayout) findViewById(R.id.layoutComputationSummary);
        layoutClaimHistory = (LinearLayout) findViewById(R.id.layoutClaimHistory);
        layoutCPLoss = (LinearLayout) findViewById(R.id.layoutCPLoss);
        layoutCPExpense = (LinearLayout) findViewById(R.id.layoutCPExpense);


        layoutInsuredDetails.setOnClickListener(this);
        layoutPolicyInfo.setOnClickListener(this);
        layoutDocumentUpload.setOnClickListener(this);
        layoutWorkshopSelection.setOnClickListener(this);
        layoutSurveyDetails.setOnClickListener(this);
        layoutDLnRCDetails.setOnClickListener(this);
        layoutPointOfImpact.setOnClickListener(this);
        layoutComputationEntry.setOnClickListener(this);
        layoutComputationSummary.setOnClickListener(this);
        layoutClaimHistory.setOnClickListener(this);
        layoutCPLoss.setOnClickListener(this);
        layoutCPExpense.setOnClickListener(this);

        mbundle = getIntent().getExtras();

        drawerLayout = (DrawerLayout) findViewById(R.id.allPartsDrawer);
        frame_container = (FrameLayout) findViewById(R.id.frame_container);
        rlRight = (RelativeLayout) findViewById(R.id.llRight);

        imgCloseDrawer = (ImageView) findViewById(R.id.imgCloseDrawer);

        imgCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        listPanelOuterParts = new ArrayList<String>();
        listGlassParts = new ArrayList<String>();
        listPlasticRubberParts = new ArrayList<String>();

        listPanelOuterParts = Arrays.asList(getResources().getStringArray(R.array.panelOuterParts));
        listGlassParts = Arrays.asList(getResources().getStringArray(R.array.glassParts));
        listPlasticRubberParts = Arrays.asList(getResources().getStringArray(R.array.plasticRubberParts));

        checkboxPanelOuterParts = (CheckBox) findViewById(R.id.checkboxPanelOuterParts);
        checkboxGlassParts = (CheckBox) findViewById(R.id.checkboxGlassParts);
        checkboxPlasticRubberParts = (CheckBox) findViewById(R.id.checkboxPlasticRubberParts);


        lvPanelOuterParts = (ExpandedListView) findViewById(R.id.lvPanelOuterParts);
        lvPanelOuterParts.setAdapter(new NavigationDrawerListAdapter(this, listPanelOuterParts, PartsSelectionFragment.allPartList, new PartsSelectionFragment.AddBtnClickListener() {
            @Override
            public void onAddBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.add(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);
            }

            @Override
            public void onRemoveBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.remove(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);
            }
        }));

        lvGlassParts = (ExpandedListView) findViewById(R.id.lvGlassParts);
        lvGlassParts.setAdapter(new NavigationDrawerListAdapter(this, listGlassParts, PartsSelectionFragment.allPartList
                , new PartsSelectionFragment.AddBtnClickListener() {
            @Override
            public void onAddBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.add(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);

            }

            @Override
            public void onRemoveBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.remove(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);

            }
        }));

        lvPlasticRubberParts = (ExpandedListView) findViewById(R.id.lvPlasticRubberParts);
        lvPlasticRubberParts.setAdapter(new NavigationDrawerListAdapter(this
                , listPlasticRubberParts, PartsSelectionFragment.allPartList, new PartsSelectionFragment.AddBtnClickListener() {
            @Override
            public void onAddBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.add(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);

            }

            @Override
            public void onRemoveBtnClick(String position) {
                int i = PartsSelectionFragment.searchedPartList.indexOf(position);
                PartsSelectionFragment.allPartList.remove(PartsSelectionFragment.searchedPartList.get(i));
                SelectedPartBucketAdapter adapter = new SelectedPartBucketAdapter(FragmentContainerActivity.this, PartsSelectionFragment.allPartList);
                PartsSelectionFragment.lvSelectedPartsBucket.setAdapter(adapter);

            }
        }));


        checkboxPanelOuterParts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxPanelOuterParts.isChecked())
                    lvPanelOuterParts.setVisibility(View.VISIBLE);
                else
                    lvPanelOuterParts.setVisibility(View.GONE);
            }
        });

        checkboxGlassParts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxGlassParts.isChecked())
                    lvGlassParts.setVisibility(View.VISIBLE);
                else
                    lvGlassParts.setVisibility(View.GONE);
            }
        });

        checkboxPlasticRubberParts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxPlasticRubberParts.isChecked())
                    lvPlasticRubberParts.setVisibility(View.VISIBLE);
                else
                    lvPlasticRubberParts.setVisibility(View.GONE);
            }
        });


        int width = (getResources().getDisplayMetrics().widthPixels) * 80 / 100;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) rlRight.getLayoutParams();
        params.width = width;
        rlRight.setLayoutParams(params);


        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_arrow_forward_white_24dp, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {

                ImageView imgOpenDrawer = PartsSelectionFragment.imgOpenDrawer;
                imgOpenDrawer.setVisibility(View.VISIBLE);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {

                ImageView imgOpenDrawer = PartsSelectionFragment.imgOpenDrawer;
                imgOpenDrawer.setVisibility(View.GONE);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                frame_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();


            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputMethodManager = (InputMethodManager) FragmentContainerActivity.this
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        FragmentContainerActivity.this.getCurrentFocus().getWindowToken(),
                        0
                );
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);


        Fragment fragment = null;

        switch (mbundle.getString("fragmentName")) {
            case "insuredDetails":
                Bundle bundle = new Bundle();
                bundle.putString("MasterClaimNumber", mbundle.getString("MasterClaimNumber"));
                fragment = new InsuredDetailsFragment();
                fragment.setArguments(bundle);
                break;
            case "policyInfo":
                fragment = new PolicyInformationFragment();
                break;
            case "documentsUpload":
                fragment = new DocumentsUploadFragment();
                break;
            case "workshopSelection":
                fragment = new WorkshopSelectionFragment();
                break;
            case "surveyDetails":
                fragment = new SurveyDetailsFragment();
                break;
            case "computationEntry":
                fragment = new ComputationDataEntryFragment();
                break;
            case "computationSummary":
                fragment = new ComputationSummaryLessFragment();
                break;
            case "claimHistory":
                fragment = new ClaimHistoryFragment();
                break;
            case "pointofimpact":
                fragment = new FragmentPointOfImpact();
                break;
            case "partSelection":
                fragment = new PartsSelectionFragment();
                break;
            case "cpLoss":
                fragment = new CPLossFragment();
                break;
            case "cpExpense":
                fragment = new CPExpenseFragment();
                break;
            case "dlNrc":
                fragment = new dlNrcDetailsFragment();
                break;
        }

        FragmentManager fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();


        psbStar = (PhasedSeekBar) findViewById(R.id.psb_star);


        final Resources resources = getResources();

        psbStar.setAdapter(new SimplePhasedAdapter(resources, new int[]{
                R.drawable.btn_star1_selector,
                R.drawable.btn_star2_selector,
                R.drawable.btn_star3_selector,
                R.drawable.btn_star4_selector,
                R.drawable.btn_star5_selector}));


        psbStar.setListener(new PhasedListener() {


            @Override
            public void onPositionSelected(int position) {
                if (position == 0) {
                    FragmentContainerActivity.hideKeyboard(FragmentContainerActivity.this);
                    Fragment fragment = null;
                    FragmentManager fragmentManager;
                    fragment = new ComputationDataEntryFragment();
                    fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                }
                if (position == 1) {
                    FragmentContainerActivity.hideKeyboard(FragmentContainerActivity.this);
                    Fragment fragment = null;
                    FragmentManager fragmentManager;
                    fragment = new PartsSelectionFragment();
                    fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

                }
                if (position == 2) {
                    FragmentContainerActivity.hideKeyboard(FragmentContainerActivity.this);
                    Fragment fragment = null;
                    FragmentManager fragmentManager;
                    fragment = new WorkSelectionFragment();
                    fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                }
                if (position == 3) {
                    FragmentContainerActivity.hideKeyboard(FragmentContainerActivity.this);
                    Fragment fragment = null;
                    FragmentManager fragmentManager;
                    fragment = new ComputationAssesmentFragment();
                    fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                }
                if (position == 4) {
                    FragmentContainerActivity.hideKeyboard(FragmentContainerActivity.this);
                    Fragment fragment = null;
                    FragmentManager fragmentManager;
                    fragment = new ComputationSummaryLessFragment();
                    fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                }
            }
        });


        psbStar.setInteractionListener(new PhasedInteractionListener() {
            @Override
            public void onInteracted(int x, int y, int position, MotionEvent motionEvent) {
                Log.d("PSB", String.format("onInteracted %d %d %d %d", x, y, position, motionEvent.getAction()));
            }
        });


        psbStar.setPosition(0);


    }

    public void setActionBarTitle(String title, String subtitle) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + title + " </font>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>" + subtitle + " </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(196, 54, 53)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_arrow_back_black);
    }

    public void setActionBarTitle_first(String title, String subtitle) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + title + " </font>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>" + subtitle + " </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(196, 54, 53)));
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.ic_arrow_back_black);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_container, menu);
        return true;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }/* else if (isCustomKeyboardVisible()) {
            hideCustomKeyboard();
        }*/ else {
            super.onBackPressed();
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

        }
    }

   /* public boolean isCustomKeyboardVisible() {
        return ComputationAssesmentFragment.mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideCustomKeyboard() {
        ComputationAssesmentFragment.mKeyboardView.setVisibility(View.GONE);
        ComputationAssesmentFragment.mKeyboardView.setEnabled(false);
    }*/

    Fragment fragment = null;
    FragmentManager fragmentManager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutInsuredDetails:


                fragment = new InsuredDetailsFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutPolicyInfo:


                fragment = new PolicyInformationFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutDocumentsUpload:
                fragment = new DocumentsUploadFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutWorkshopSelection:
                fragment = new WorkshopSelectionFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutSurveyDetails:
                fragment = new SurveyDetailsFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutDLnRCDetails:
                fragment = new dlNrcDetailsFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutPointOfImpact:
/*                fragment = new FragmentPointOfImpact();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();*/
                checkForPreferences();

              /*  Intent intent = new Intent(FragmentContainerActivity.this, PointOfImpactActivity.class);
                startActivity(intent);*/
                break;
            case R.id.layoutComputationEntry:
                fragment = new ComputationDataEntryFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutComputationSummary:
                fragment = new ComputationSummaryLessFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutClaimHistory:
                fragment = new ClaimHistoryFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutCPLoss:
                fragment = new CPLossFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.layoutCPExpense:
                fragment = new CPExpenseFragment();
                fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
                break;
        }
    }

    public void checkForPreferences() {

        Log.e("Check", "In Check of Preferences");
        Map<String, ?> value = sharedPreferences.getAll();
        if (value.isEmpty()) {
            Log.e("Value ", value + "");
            Intent intent = new Intent(FragmentContainerActivity.this, PointOfImpactActivity.class);
            Log.e("Check", "Value is Not Null");
            startActivity(intent);
            this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            fragment = new FragmentPointOfImpact();
            fragmentManager = FragmentContainerActivity.this.getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.frame_container, fragment).commit();
        }
    }


}
