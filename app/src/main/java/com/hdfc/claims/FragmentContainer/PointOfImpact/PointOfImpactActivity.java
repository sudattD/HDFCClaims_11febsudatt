package com.hdfc.claims.FragmentContainer.PointOfImpact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfc.claims.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointOfImpactActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //For Left CAR
    ImageView imgTopLeftTyre, imgLeftFender, imgTopLeftRim, imgPillarLeft, imgLeftDoorFrnt, imgLeftDoorRear, imgLeftRunningBoard, imgQuarterLeft, imgBottomLeftTyre, imgBottomLeftRim,
    //For Middle CAR
    imgBumperFrnt, imgLeftHeadLight, imgRightHeadLight, imgGrille, imgBonet, imgFrntWindSheild, imgRoof, imgRearWindSheild, imgDicky, imgTailLeft, imgTailRight, getImgBumperRear,
    //For Right CAR
    imgTopRightTyre, imgRightFender, imgTopRightRim, imgPillarRight, imgRightDoorFrnt, imgRightDoorRear, imgRightRunningBoard, imgQuarterRight, imgBottomRightTyre, imgBottomRightRim;


    public static boolean isAddPart = false;

    Button btnAddToPartsSelection;


    ArrayList carList;

    String key = "Nawaz";
    static final String STATE_SCORE = "playerScore";


    static int staticTopLeftTyre = 0, staticTopLeftRim = 0, staticFenderLeft = 0, staticPillarLeft = 0, staticLeftDoorFrnt = 0, staticLeftDoorRear = 0, staticLeftRunningBoard = 0, staticQuarterLeft = 0, staticBottomLeftTyre = 0, staticBottomLeftRim = 0,
            staticBumperFrnt = 0, staticLeftHeadLight = 0, staticRightHeadLight = 0, staticGrille = 0, staticBonet = 0, staticWindShieldFrnt = 0, staticRoof = 0, staticRearWindShield = 0, staticDicky = 0, staticTailLeft = 0, staticTailRight = 0, staticBumperRear = 0,
            staticTopRightTyre = 0, staticRightFender = 0, staticTopRightRim = 0, staticPillarRight = 0, staticRightDoorFrnt = 0, staticRightDoorRear = 0, staticRightRunningBoard = 0, staticQuarterRight = 0, staticBottomRightTyre = 0, staticBottomRightRim = 0;


    Map<String, String> map;

    Context context;
    SharedPreferences sharedPreferences, sharedPartsSelection;

    NavigationView navigationView;
    Menu menu;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_impact_main);


        bundle = getIntent().getExtras();


        if (savedInstanceState != null) {

            //Log.d(MainActivity.class.getSimpleName(),"onCreate State"+savedInstanceState.getInt(STATE_SCORE));
        } else {
            //Log.d(MainActivity.class.getSimpleName(),"onCreate State else"+savedInstanceState.getInt(STATE_SCORE));
        }


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.hideOverflowMenu();

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      /*  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialize();
        checkForPreferences();

       /*  ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.carpartslist_layout, carPartsList);
        ListView carListView=(ListView)findViewById(R.id.carPartslist);
        carListView.setAdapter(adapter);
*/
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        //Log.d(MainActivity.class.getSimpleName(), "onSavedInstance called");
        outState.putInt(STATE_SCORE, 99);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bundle myBundle = new Bundle();
        myBundle.putInt(key, 34);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            // Log.d(MainActivity.class.getSimpleName(),"BUNDLE INFO ON RESTORE"+savedInstanceState.getInt(key));
        }

    }

    public void initialize() {

        //FOR LEFT CAR

        context = this;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", 1);
        sharedPartsSelection = getApplicationContext().getSharedPreferences("MyPartsSelection", 1);


        carList = new ArrayList<String>();


        menu = navigationView.getMenu();

        navigationView.setItemIconTintList(null);

        map = new HashMap<String, String>();


        btnAddToPartsSelection = (Button) findViewById(R.id.btnAddtoPartsSelection);


        btnAddToPartsSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PointOfImpactActivity.this);
                alertDialogBuilder.setMessage("Are you sure you want to add part(s) ?");

                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences.Editor keyValuesEditor = sharedPartsSelection.edit();

                        Log.e("Pref", "Detected");
                        if (map.isEmpty()) {
                            Log.e("In Map", "Yes");
                            keyValuesEditor.remove("Contains");
                            Log.e("Parts Button", "No");
                        } else {
                            for (String s : map.keySet()) {
                                keyValuesEditor.putString(s, map.get(s));
                            }

                            keyValuesEditor.commit();

                            keyValuesEditor.putString("Contains", "Yes");
                            Toast.makeText(getApplicationContext(),"Part(s) successfully added",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                if(map.isEmpty()) {

                 Toast.makeText(getApplicationContext(),"No Part Selected",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


            }
        });


        imgTopLeftTyre = (ImageView) findViewById(R.id.leftTopTyre);
        imgLeftFender = (ImageView) findViewById(R.id.fenderLeft);
        imgTopLeftRim = (ImageView) findViewById(R.id.leftTopRim);
        imgPillarLeft = (ImageView) findViewById(R.id.pillarLeft);
        imgLeftDoorFrnt = (ImageView) findViewById(R.id.doorFrontLeft);
        imgLeftDoorRear = (ImageView) findViewById(R.id.doorRearLeft);
        imgLeftRunningBoard = (ImageView) findViewById(R.id.runningBoardLeft);
        imgQuarterLeft = (ImageView) findViewById(R.id.quarterPanelLeft);
        imgBottomLeftTyre = (ImageView) findViewById(R.id.leftBottomTyre);
        imgBottomLeftRim = (ImageView) findViewById(R.id.leftBottomRim);

        imgTopLeftTyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLeftTopTyre(staticTopLeftTyre);

            }
        });
        imgLeftFender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgLeftFender(staticFenderLeft);

            }
        });
        imgTopLeftRim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgTopLeftRim(staticTopLeftRim);
            }
        });

        imgPillarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgPillarLeft(staticPillarLeft);
            }
        });
        imgLeftDoorFrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgLeftDoorFrnt(staticLeftDoorFrnt);
            }
        });
        imgLeftDoorRear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgLeftDoorRear(staticLeftDoorRear);
            }
        });
        imgLeftRunningBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgLeftRunningBoard(staticLeftRunningBoard);
            }
        });
        imgQuarterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgQuarterLeft(staticQuarterLeft);
            }
        });
        imgBottomLeftTyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBottomLeftTyre(staticBottomLeftTyre);
            }
        });
        imgBottomLeftRim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBottomLeftRim(staticBottomLeftRim);
            }
        });


        //FOR MIDDLE CAR

        imgBumperFrnt = (ImageView) findViewById(R.id.bumperFront);
        imgLeftHeadLight = (ImageView) findViewById(R.id.headLightLeft);
        imgRightHeadLight = (ImageView) findViewById(R.id.headLightRight);
        imgGrille = (ImageView) findViewById(R.id.grille);
        imgBonet = (ImageView) findViewById(R.id.bonet);
        imgFrntWindSheild = (ImageView) findViewById(R.id.windSheildFront);
        imgRoof = (ImageView) findViewById(R.id.roof);
        imgRearWindSheild = (ImageView) findViewById(R.id.windSheilRear);
        imgDicky = (ImageView) findViewById(R.id.dicky);
        imgTailLeft = (ImageView) findViewById(R.id.tailLightLeft);
        imgTailRight = (ImageView) findViewById(R.id.tailLightRight);
        getImgBumperRear = (ImageView) findViewById(R.id.bumperRearl);

        imgBumperFrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBumperFrnt(staticBumperFrnt);
            }
        });
        imgLeftHeadLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgLeftHeadLight(staticLeftHeadLight);
            }
        });
        imgRightHeadLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRightHeadLight(staticRightHeadLight);

            }
        });
        imgGrille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgGrille(staticGrille);
            }
        });
        imgBonet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBonet(staticBonet);
            }
        });
        imgFrntWindSheild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgFrntWindSheild(staticWindShieldFrnt);
            }
        });
        imgRoof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRoof(staticRoof);
            }
        });
        imgRearWindSheild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRearWindShield(staticRearWindShield);
            }
        });

        imgDicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgDicky(staticDicky);
            }
        });
        imgTailLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgTailLeft(staticTailLeft);
            }
        });
        imgTailRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgTailRight(staticTailRight);
            }
        });
        getImgBumperRear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBumperRear(staticBumperRear);
            }
        });


        //FOR RIGHT CAR

        imgTopRightTyre = (ImageView) findViewById(R.id.rightTopTyre);
        imgRightFender = (ImageView) findViewById(R.id.fenderRight);
        imgTopRightRim = (ImageView) findViewById(R.id.rightTopRim);
        imgPillarRight = (ImageView) findViewById(R.id.pillarRight);
        imgRightDoorFrnt = (ImageView) findViewById(R.id.doorFrontRight);
        imgRightDoorRear = (ImageView) findViewById(R.id.doorRearRight);
        imgRightRunningBoard = (ImageView) findViewById(R.id.runningBoardRight);
        imgQuarterRight = (ImageView) findViewById(R.id.quarterPanelRight);
        imgBottomRightTyre = (ImageView) findViewById(R.id.rightBottomTyre);
        imgBottomRightRim = (ImageView) findViewById(R.id.rightBottomRim);

        imgTopRightTyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgTopRightTyre(staticTopRightTyre);
            }
        });
        imgRightFender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRightFender(staticRightFender);
            }
        });
        imgTopRightRim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgTopRightRim(staticTopRightRim);
            }
        });
        imgPillarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgPillarRight(staticPillarRight);
            }
        });
        imgRightDoorFrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRightDoorFrnt(staticRightDoorFrnt);
            }
        });
        imgRightDoorRear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRightDoorRear(staticRightDoorRear);
            }
        });
        imgRightRunningBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgRightRunningBoard(staticRightRunningBoard);
            }
        });
        imgQuarterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgQuarterRight(staticQuarterRight);
            }
        });
        imgBottomRightTyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBottomRightTyre(staticBottomRightTyre);
            }
        });
        imgBottomRightRim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImgBottomRightRim(staticBottomRightRim);
            }
        });
    }

    public void checkForPreferences() {


        Map<String, ?> value = sharedPreferences.getAll();
        if (value.isEmpty()) {
            //Log.e("Point Of Impact Activity", "No Data found");
        } else {
            for (Map.Entry<String, ?> entry : value.entrySet()) {

                setFromPreferences(entry.getKey(), entry.getValue().toString());
                map.put(entry.getKey(), entry.getValue().toString());
            }
        }


    }

    public void setFromPreferences(String name, String color) {

        Log.e("Value is ", name + "" + color + "");
        if (name.equals("Tyre Front Left")) {
            if (color.equals("Yellow")) {
                changeLeftTopTyre(0);
            } else {
                changeLeftTopTyre(1);
            }

        }
        if (name.equals("Fender Left")) {
            if (color.equals("Yellow")) {
                changeImgLeftFender(0);
            } else {
                changeImgLeftFender(1);
            }

        }
        if (name.equals("Rim Front Left")) {
            if (color.equals("Yellow")) {
                changeImgTopLeftRim(0);
            } else {
                changeImgTopLeftRim(1);
            }
        }
        if (name.equals("Left Pillar")) {
            if (color.equals("Yellow")) {
                changeImgPillarLeft(0);
            } else {
                changeImgPillarLeft(1);
            }
        }
        if (name.equals("Door Front Left")) {
            if (color.equals("Yellow")) {
                changeImgLeftDoorFrnt(0);
            } else {
                changeImgLeftDoorFrnt(1);
            }
        }
        if (name.equals("Door Rear Left")) {
            if (color.equals("Yellow")) {
                changeImgLeftDoorRear(0);
            } else {
                changeImgLeftDoorRear(1);
            }
        }


        if (name.equals("Running Board Left")) {
            if (color.equals("Yellow")) {
                changeImgLeftRunningBoard(0);
            } else {
                changeImgLeftRunningBoard(1);
            }
        }

        if (name.equals("Quarter Left")) {
            if (color.equals("Yellow")) {
                changeImgQuarterLeft(0);
            } else {
                changeImgQuarterLeft(1);
            }
        }

        if (name.equals("Tyre Rear Left")) {
            if (color.equals("Yellow")) {
                changeImgBottomLeftTyre(0);
            } else {
                changeImgBottomLeftTyre(1);
            }
        }

        if (name.equals("Rim Rear Left")) {
            if (color.equals("Yellow")) {
                changeImgBottomLeftRim(0);
            } else {
                changeImgBottomLeftRim(1);
            }
        }
        if (name.equals("Bumper Front")) {
            if (color.equals("Yellow")) {
                changeImgBumperFrnt(0);
            } else {
                changeImgBumperFrnt(1);
            }
        }
        if (name.equals("Head Light Left")) {
            if (color.equals("Yellow")) {
                changeImgLeftHeadLight(0);
            } else {
                changeImgLeftHeadLight(1);
            }
        }
        if (name.equals("Head Light Right")) {
            if (color.equals("Yellow")) {
                changeImgRightHeadLight(0);
            } else {
                changeImgRightHeadLight(1);
            }
        }
        if (name.equals("Grille")) {
            if (color.equals("Yellow")) {
                changeImgGrille(0);
            } else {
                changeImgGrille(1);
            }
        }
        if (name.equals("Bonnet")) {
            if (color.equals("Yellow")) {
                changeBonet(0);
            } else {
                changeBonet(1);
            }
        }
        if (name.equals("Windshield Front")) {
            if (color.equals("Yellow")) {
                changeImgFrntWindSheild(0);
            } else {
                changeImgFrntWindSheild(1);
            }
        }
        if (name.equals("Roof")) {
            if (color.equals("Yellow")) {
                changeRoof(0);
            } else {
                changeRoof(1);
            }
        }
        if (name.equals("Windshield Rear")) {
            if (color.equals("Yellow")) {
                changeImgRearWindShield(0);
            } else {
                changeImgRearWindShield(1);
            }
        }
        if (name.equals("Dicky")) {
            if (color.equals("Yellow")) {
                changeImgDicky(0);
            } else {
                changeImgDicky(1);
            }
        }
        if (name.equals("Tail Light Left")) {
            if (color.equals("Yellow")) {
                changeImgTailLeft(0);
            } else {
                changeImgTailLeft(1);
            }
        }
        if (name.equals("Tail Light Right")) {
            if (color.equals("Yellow")) {
                changeImgTailRight(0);
            } else {
                changeImgTailRight(1);
            }
        }
        if (name.equals("Bumper Rear")) {
            if (color.equals("Yellow")) {
                changeImgBumperRear(0);
            } else {
                changeImgBumperRear(1);
            }
        }
        if (name.equals("Tyre Front Right")) {
            if (color.equals("Yellow")) {
                changeImgTopRightTyre(0);
            } else {
                changeImgTopRightTyre(1);
            }
        }
        if (name.equals("Fender Right")) {
            if (color.equals("Yellow")) {
                changeImgRightFender(0);
            } else {
                changeImgRightFender(1);
            }
        }
        if (name.equals("Rim Front Right")) {
            if (color.equals("Yellow")) {
                changeImgTopRightRim(0);
            } else {
                changeImgTopRightRim(1);
            }
        }
        if (name.equals("Right Pillar")) {
            if (color.equals("Yellow")) {
                changeImgPillarRight(0);
            } else {
                changeImgPillarRight(1);
            }
        }
        if (name.equals("Door Front Right")) {
            if (color.equals("Yellow")) {
                changeImgRightDoorFrnt(0);
            } else {
                changeImgRightDoorFrnt(1);
            }
        }
        if (name.equals("Door Rear Right")) {
            if (color.equals("Yellow")) {
                changeImgRightDoorRear(0);
            } else {
                changeImgRightDoorRear(1);
            }
        }
        if (name.equals("Running Board Right")) {
            if (color.equals("Yellow")) {
                changeImgRightRunningBoard(0);
            } else {
                changeImgRightRunningBoard(1);
            }
        }
        if (name.equals("Quarter Right")) {
            if (color.equals("Yellow")) {
                changeImgQuarterRight(0);
            } else {
                changeImgQuarterRight(1);
            }
        }
        if (name.equals("Tyre Rear Right")) {
            if (color.equals("Yellow")) {
                changeImgBottomRightTyre(0);
            } else {
                changeImgBottomRightTyre(1);
            }
        }
        if (name.equals("Rim Rear Right")) {
            if (color.equals("Yellow")) {
                changeImgBottomRightRim(0);
            } else {
                changeImgBottomRightRim(1);
            }
        }


    }


    public void changeLeftTopTyre(int topLeftTyre) {

        //  int i=0;
        int a = carList.size();
        staticTopLeftTyre = topLeftTyre;
        if (staticTopLeftTyre == 0) {
            imgTopLeftTyre.setImageResource(R.drawable.tyre_lightdamage);
            staticTopLeftTyre++;
            Log.e("Top LEft Tyre is ", staticTopLeftTyre + "");


            if (carList.contains("Tyre Front Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tyre Front Left");
                    int id = carList.indexOf("Tyre Front Left");

                    menu.add(1, id, 1, carList.get(0).toString());
                    map.put("Tyre Front Left", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                } else {
                    carList.add(a, "Tyre Front Left");
                    int id = carList.indexOf("Tyre Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Front Left", "Yellow");
                }
            }


        } else if (staticTopLeftTyre == 1) {
            imgTopLeftTyre.setImageResource(R.drawable.tyre_heavydamage);
            staticTopLeftTyre++;

            map.put("Tyre Front Left", "Red");
            if (carList.contains("Tyre Front Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Left"));
                menuItem.setIcon(R.drawable.icon_red);

            } else {

                if (a == 0) {
                    carList.add(a, "Tyre Front Left");
                    int id = carList.indexOf("Tyre Front Left");

                    menu.add(1, id, 1, carList.get(0).toString());


                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                } else {
                    carList.add(a, "Tyre Front Left");
                    int id = carList.indexOf("Tyre Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }

        } else if (staticTopLeftTyre == 2) {
            imgTopLeftTyre.setImageResource(R.drawable.tyre);
            staticTopLeftTyre = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tyre Front Left").commit();

            map.remove("Tyre Front Left");
            if (carList.contains("Tyre Front Left")) {

                int tm = carList.indexOf("Tyre Front Left");
                int id = menu.getItem(tm).getItemId();

                Log.e("ID Top Left Tyre", tm + "");
                menu.removeItem(id);
                carList.remove("Tyre Front Left");
                Log.e("Size ", map.size() + "");
            }

        }
    }

    public void changeImgLeftFender(int fenderLeft) {


        staticFenderLeft = fenderLeft;
        int a = carList.size();
        if (staticFenderLeft == 0) {
            imgLeftFender.setImageResource(R.drawable.fender_left_lightdamage);
            //setIcon(i,"Yellow");
            staticFenderLeft++;
            if (carList.contains("Fender Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Fender Left");
                    int id = carList.indexOf("Fender Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    map.put("Fender Left", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);

                } else {
                    carList.add(a, "Fender Left");
                    int id = carList.indexOf("Fender Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    map.put("Fender Left", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                }
            }


        } else if (staticFenderLeft == 1) {
            imgLeftFender.setImageResource(R.drawable.fender_left_heavydamage);
            staticFenderLeft++;

            if (carList.contains("Fender Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Fender Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Fender Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Fender Left");
                    int id = carList.indexOf("Fender Left");
                    menu.add(1, id, 1, carList.get(0).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Fender Left");
                    int id = carList.indexOf("Fender Left");
                    menu.add(1, id, 1, carList.get(a++).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                }
            }


            //setIcon(i,"Red");

        } else if (staticFenderLeft == 2) {
            imgLeftFender.setImageResource(R.drawable.fender_left);
            staticFenderLeft = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Fender Left").commit();

            map.remove("Fender Left");

            //setIcon(i, "Grey");
            if (carList.contains("Fender Left")) {


                int tm = carList.indexOf("Fender Left");
                int id = menu.getItem(tm).getItemId();

                Log.e("Id Fender left ", id + "");
                menu.removeItem(id);
                carList.remove("Fender Left");
            }
        }


    }

    public void changeImgTopLeftRim(int leftTopRim) {


        staticTopLeftRim = leftTopRim;
        int a = carList.size();
        if (staticTopLeftRim == 0) {
            imgTopLeftRim.setImageResource(R.drawable.rim_lightdamage);

            staticTopLeftRim++;
            if (carList.contains("Rim Front Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Rim Front Left");
                    int id = carList.indexOf("Rim Front Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    map.put("Top Left Rim", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);

                } else {
                    carList.add(a, "Rim Front Left");
                    int id = carList.indexOf("Rim Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    map.put("Top Left Rim", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                }
            }


        } else if (staticTopLeftRim == 1) {

            imgTopLeftRim.setImageResource(R.drawable.rim_heavydamage);
            staticTopLeftRim++;
            if (carList.contains("Rim Front Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Left"));
                menuItem.setIcon(R.drawable.icon_red);

                map.put("Rim Front Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Rim Front Left");
                    int id = carList.indexOf("Rim Front Left");
                    menu.add(1, id, 1, carList.get(0).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Rim Front Left");
                    int id = carList.indexOf("Rim Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                }
            }


        } else if (staticTopLeftRim == 2) {
            imgTopLeftRim.setImageResource(R.drawable.rim);
            staticTopLeftRim = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Rim Front Left").commit();
            map.remove("Rim Front Left");
            if (carList.contains("Rim Front Left")) {


                Log.e("Remove", carList.indexOf("Rim Front Left") + "");
                int tm = carList.indexOf("Rim Front Left");
                int id = menu.getItem(tm).getItemId();
                menu.removeItem(id);

                carList.remove("Rim Front Left");
            }

        }
    }

    public void changeImgPillarLeft(int pillarLeft) {


        staticPillarLeft = pillarLeft;
        int a = carList.size();
        if (staticPillarLeft == 0) {
            imgPillarLeft.setImageResource(R.drawable.a_pillar_left_lightdamage);

            staticPillarLeft++;
            if (carList.contains("Left Pillar")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Left Pillar");
                    int id = carList.indexOf("Left Pillar");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    map.put("Left Pillar", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Left Pillar"));
                    menuItem.setIcon(R.drawable.icon_yellow);

                } else {
                    carList.add(a, "Left Pillar");
                    int id = carList.indexOf("Left Pillar");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    map.put("Left Pillar", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Left Pillar"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                }
            }


        } else if (staticPillarLeft == 1) {
            imgPillarLeft.setImageResource(R.drawable.a_pillar_left_heavydamage);
            staticPillarLeft++;
            if (carList.contains("Left Pillar")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Left Pillar"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Left Pillar", "Red");

            } else {
                if (a == 0) {
                    carList.add(a, "Left Pillar");
                    int id = carList.indexOf("Left Pillar");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Left Pillar"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Left Pillar");
                    int id = carList.indexOf("Left Pillar");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Left Pillar"));
                    menuItem.setIcon(R.drawable.icon_red);
                }
            }


        } else if (staticPillarLeft == 2) {
            imgPillarLeft.setImageResource(R.drawable.a_pillar_left);
            staticPillarLeft = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Left Pillar").commit();
            map.remove("Left Pillar");

            int tm = carList.indexOf("Left Pillar");

            int id = menu.getItem(tm).getItemId();
            Log.e("ID Left Pillar ", id + "");
            menu.removeItem(id);
            carList.remove("Left Pillar");
        }
    }

    public void changeImgLeftDoorFrnt(int leftDoorFront) {


        staticLeftDoorFrnt = leftDoorFront;
        int a = carList.size();
        if (staticLeftDoorFrnt == 0) {

            imgLeftDoorFrnt.setImageResource(R.drawable.door_front_left_lightdamage);

            staticLeftDoorFrnt++;
            if (carList.contains("Door Front Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Door Front Left");
                    int id = carList.indexOf("Door Front Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    map.put("Door Front Left", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);

                } else {
                    carList.add(a, "Door Front Left");
                    int id = carList.indexOf("Door Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    map.put("Door Front Left", "Yellow");
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                }
            }


        } else if (staticLeftDoorFrnt == 1) {
            imgLeftDoorFrnt.setImageResource(R.drawable.door_front_left_heavydamage);
            staticLeftDoorFrnt++;
            if (carList.contains("Door Front Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Door Front Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Door Front Left");
                    int id = carList.indexOf("Door Front Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Door Front Left");
                    int id = carList.indexOf("Door Front Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());

                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                }

            }


        } else if (staticLeftDoorFrnt == 2) {
            imgLeftDoorFrnt.setImageResource(R.drawable.door_front_left);
            staticLeftDoorFrnt = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Door Front Left").commit();
            map.remove("Door Front Left");
            int tm = carList.indexOf("Door Front Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Door Front Left ", id + "");
            menu.removeItem(id);
            carList.remove("Door Front Left");
        }
    }

    public void changeImgLeftDoorRear(int leftDoorRear) {


        staticLeftDoorRear = leftDoorRear;
        int a = carList.size();
        if (staticLeftDoorRear == 0) {
            imgLeftDoorRear.setImageResource(R.drawable.door_rear_left_lightdamage);
            staticLeftDoorRear++;

            if (carList.contains("Door Rear Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Door Rear Left");
                    int id = carList.indexOf("Door Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Rear Left", "Yellow");

                } else {
                    carList.add(a, "Door Rear Left");
                    int id = carList.indexOf("Door Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Rear Left", "Yellow");
                }
            }


        } else if (staticLeftDoorRear == 1) {
            imgLeftDoorRear.setImageResource(R.drawable.door_rear_left_heavydamage);
            staticLeftDoorRear++;
            if (carList.contains("Door Rear Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Door Rear Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Door Rear Left");
                    int id = carList.indexOf("Door Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Door Rear Left");
                    int id = carList.indexOf("Door Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                }

            }


        } else if (staticLeftDoorRear == 2) {
            imgLeftDoorRear.setImageResource(R.drawable.door_rear_left);
            staticLeftDoorRear = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Door Rear Left").commit();
            map.remove("Door Rear Left");
            int tm = carList.indexOf("Door Rear Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Door Rear Left ", id + "");
            menu.removeItem(id);
            carList.remove("Door Rear Left");

        }

    }

    public void changeImgLeftRunningBoard(int leftRunningBoard) {


        staticLeftRunningBoard = leftRunningBoard;

        int a = carList.size();
        if (staticLeftRunningBoard == 0) {
            imgLeftRunningBoard.setImageResource(R.drawable.running_board_left_lightdamage);

            staticLeftRunningBoard++;
            if (carList.contains("Running Board Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Running Board Left");
                    int id = carList.indexOf("Running Board Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Running Board Left", "Yellow");

                } else {
                    carList.add(a, "Running Board Left");
                    int id = carList.indexOf("Running Board Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Running Board Left", "Yellow");
                }
            }


        } else if (staticLeftRunningBoard == 1) {
            imgLeftRunningBoard.setImageResource(R.drawable.running_board_left_heavydamage);
            staticLeftRunningBoard++;

            if (carList.contains("Running Board Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Running Board Left", "Red");

            } else {
                if (a == 0) {
                    carList.add(a, "Running Board Left");
                    int id = carList.indexOf("Running Board Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Running Board Left");
                    int id = carList.indexOf("Running Board Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticLeftRunningBoard == 2) {
            imgLeftRunningBoard.setImageResource(R.drawable.running_board_left);
            staticLeftRunningBoard = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Running Board Left").commit();
            map.remove("Running Board Left");

            int tm = carList.indexOf("Running Board Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Running Board Left ", id + "");
            menu.removeItem(id);
            carList.remove("Running Board Left");
        }
    }

    public void changeImgQuarterLeft(int quarterLeft) {


        staticQuarterLeft = quarterLeft;
        int a = carList.size();
        if (staticQuarterLeft == 0) {
            imgQuarterLeft.setImageResource(R.drawable.quarter_panel_left_lightdamage);
            staticQuarterLeft++;

            if (carList.contains("Quarter Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Quarter Left");
                    int id = carList.indexOf("Quarter Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Quarter Left", "Yellow");

                } else {
                    carList.add(a, "Quarter Left");
                    int id = carList.indexOf("Quarter Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Quarter Left", "Yellow");
                }
            }


        } else if (staticQuarterLeft == 1) {
            imgQuarterLeft.setImageResource(R.drawable.quarter_panel_left_heavydamage);
            staticQuarterLeft++;
            if (carList.contains("Quarter Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Quarter Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Quarter Left");
                    int id = carList.indexOf("Quarter Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Quarter Left");
                    int id = carList.indexOf("Quarter Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }

            }


        } else if (staticQuarterLeft == 2) {
            imgQuarterLeft.setImageResource(R.drawable.quarter_panel_left);
            staticQuarterLeft = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Quarter Left").commit();
            map.remove("Quarter Left");

            int tm = carList.indexOf("Quarter Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Quarter Left ", id + "");
            menu.removeItem(id);
            carList.remove("Quarter Left");
        }

    }

    public void changeImgBottomLeftTyre(int bottomLeftTyre) {


        staticBottomLeftTyre = bottomLeftTyre;
        int a = carList.size();
        if (staticBottomLeftTyre == 0) {
            imgBottomLeftTyre.setImageResource(R.drawable.tyre_lightdamage);
            staticBottomLeftTyre++;
            if (carList.contains("Tyre Rear Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tyre Rear Left");
                    int id = carList.indexOf("Tyre Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Rear Left", "Yellow");

                } else {
                    carList.add(a, "Tyre Rear Left");
                    int id = carList.indexOf("Tyre Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Rear Left", "Yellow");
                }
            }
        } else if (staticBottomLeftTyre == 1) {
            imgBottomLeftTyre.setImageResource(R.drawable.tyre_heavydamage);
            staticBottomLeftTyre++;
            if (carList.contains("Tyre Rear Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Tyre Rear Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Tyre Rear Left");
                    int id = carList.indexOf("Tyre Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Tyre Rear Left");
                    int id = carList.indexOf("Tyre Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);
                }

            }


        } else if (staticBottomLeftTyre == 2) {
            imgBottomLeftTyre.setImageResource(R.drawable.tyre);
            staticBottomLeftTyre = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tyre Rear Left").commit();
            map.remove("Tyre Rear Left");

            int tm = carList.indexOf("Tyre Rear Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Tyre Rear Left ", id + "");
            menu.removeItem(id);
            carList.remove("Tyre Rear Left");
        }

    }

    public void changeImgBottomLeftRim(int bottomLeftRim) {


        staticBottomLeftRim = bottomLeftRim;
        int a = carList.size();

        if (staticBottomLeftRim == 0) {
            imgBottomLeftRim.setImageResource(R.drawable.rim_lightdamage);
            staticBottomLeftRim++;

            if (carList.contains("Rim Rear Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Rim Rear Left");
                    int id = carList.indexOf("Rim Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Rear Left", "Yellow");

                } else {
                    carList.add(a, "Rim Rear Left");
                    int id = carList.indexOf("Rim Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Rear Left", "Yellow");
                }
            }


        } else if (staticBottomLeftRim == 1) {
            imgBottomLeftRim.setImageResource(R.drawable.rim_heavydamage);
            staticBottomLeftRim++;
            if (carList.contains("Rim Rear Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Rim Rear Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Rim Rear Left");
                    int id = carList.indexOf("Rim Rear Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Rim Rear Left");
                    int id = carList.indexOf("Rim Rear Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }

            }


        } else if (staticBottomLeftRim == 2) {
            imgBottomLeftRim.setImageResource(R.drawable.rim);
            staticBottomLeftRim = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Rim Rear Left").commit();
            map.remove("Rim Rear Left");

            int tm = carList.indexOf("Rim Rear Left");

            int id = menu.getItem(tm).getItemId();
            Log.e("Rim Rear Left ", id + "");
            menu.removeItem(id);
            carList.remove("Rim Rear Left");
        }
    }

    public void changeImgBumperFrnt(int bumperFront) {

        staticBumperFrnt = bumperFront;
        int a = carList.size();
        if (staticBumperFrnt == 0) {
            imgBumperFrnt.setImageResource(R.drawable.bumper_frnt_lightdamage);

            staticBumperFrnt++;

            if (carList.contains("Bumper Front")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Bumper Front");
                    int id = carList.indexOf("Bumper Front");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Front"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bumper Front", "Yellow");

                } else {
                    carList.add(a, "Bumper Front");
                    int id = carList.indexOf("Bumper Front");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Front"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bumper Front", "Yellow");
                }
            }

        } else if (staticBumperFrnt == 1) {
            imgBumperFrnt.setImageResource(R.drawable.bumper_frnt_heavydamage);
            staticBumperFrnt++;
            if (carList.contains("Bumper Front")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Front"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Bumper Front", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Bumper Front");
                    int id = carList.indexOf("Bumper Front");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Front"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Bumper Front");
                    int id = carList.indexOf("Bumper Front");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Front"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }

        } else if (staticBumperFrnt == 2) {
            imgBumperFrnt.setImageResource(R.drawable.bumper_frnt);
            staticBumperFrnt = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Bumper Front").commit();
            map.remove("Bumper Front");

            int tm = carList.indexOf("Bumper Front");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Bumper Front");


        }
    }

    public void changeImgLeftHeadLight(int leftHeadLight) {


        staticLeftHeadLight = leftHeadLight;
        int a = carList.size();
        if (staticLeftHeadLight == 0) {
            imgLeftHeadLight.setImageResource(R.drawable.head_light_left_lightdamage);

            staticLeftHeadLight++;

            if (carList.contains("Head Light Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Head Light Left");
                    int id = carList.indexOf("Head Light Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Head Light Left", "Yellow");

                } else {
                    carList.add(a, "Head Light Left");
                    int id = carList.indexOf("Head Light Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Head Light Left", "Yellow");
                }
            }


        } else if (staticLeftHeadLight == 1) {
            imgLeftHeadLight.setImageResource(R.drawable.head_light_left_heavydamage);
            staticLeftHeadLight++;
            if (carList.contains("Head Light Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Head Light Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Head Light Left");
                    int id = carList.indexOf("Head Light Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Head Light Left");
                    int id = carList.indexOf("Head Light Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticLeftHeadLight == 2) {
            imgLeftHeadLight.setImageResource(R.drawable.head_light_left);
            staticLeftHeadLight = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Head Light Left").commit();
            map.remove("Head Light Left");

            int tm = carList.indexOf("Head Light Left");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Head Light Left");

        }

    }

    public void changeImgRightHeadLight(int rightHeadLight) {


        staticRightHeadLight = rightHeadLight;
        int a = carList.size();
        if (staticRightHeadLight == 0) {
            imgRightHeadLight.setImageResource(R.drawable.head_light_right_lightdamage);

            staticRightHeadLight++;

            if (carList.contains("Head Light Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Head Light Right");
                    int id = carList.indexOf("Head Light Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Head Light Right", "Yellow");


                } else {
                    carList.add(a, "Head Light Right");
                    int id = carList.indexOf("Head Light Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Head Light Right", "Yellow");
                }
            }


        } else if (staticRightHeadLight == 1) {
            imgRightHeadLight.setImageResource(R.drawable.head_light_right_heavydamage);
            staticRightHeadLight++;
            if (carList.contains("Head Light Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Head Light Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Head Light Right");
                    int id = carList.indexOf("Head Light Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Head Light Right");
                    int id = carList.indexOf("Head Light Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Head Light Right"));
                    menuItem.setIcon(R.drawable.icon_red);
                }
            }


        } else if (staticRightHeadLight == 2) {
            imgRightHeadLight.setImageResource(R.drawable.head_light_right);
            staticRightHeadLight = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Head Light Right").commit();
            map.remove("Head Light Right");


            int tm = carList.indexOf("Head Light Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Head Light Right");
        }

    }

    public void changeImgGrille(int grille) {

        staticGrille = grille;
        int a = carList.size();
        if (staticGrille == 0) {
            imgGrille.setImageResource(R.drawable.grille_lightdamage);

            staticGrille++;
            if (carList.contains("Grille")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Grille");
                    int id = carList.indexOf("Grille");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Grille"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Grille", "Yellow");

                } else {
                    carList.add(a, "Grille");
                    int id = carList.indexOf("Grille");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Grille"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Grille", "Yellow");
                }
            }


        } else if (staticGrille == 1) {
            imgGrille.setImageResource(R.drawable.grille_heavydamage);
            staticGrille++;
            if (carList.contains("Grille")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Grille"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Grille", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Grille");
                    int id = carList.indexOf("Grille");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Grille"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Grille");
                    int id = carList.indexOf("Grille");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Grille"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticGrille == 2) {
            imgGrille.setImageResource(R.drawable.grille);
            staticGrille = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Grille").commit();
            map.remove("Grille");


            int tm = carList.indexOf("Grille");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Grille");
        }
    }

    public void changeBonet(int bonet) {

        staticBonet = bonet;
        int a = carList.size();
        if (staticBonet == 0) {
            imgBonet.setImageResource(R.drawable.bonnet_lightdamage);

            staticBonet++;

            if (carList.contains("Bonnet")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Bonnet");
                    int id = carList.indexOf("Bonnet");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bonnet"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bonnet", "Yellow");

                } else {
                    carList.add(a, "Bonnet");
                    int id = carList.indexOf("Bonnet");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bonnet"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bonnet", "Yellow");
                }
            }


        } else if (staticBonet == 1) {
            imgBonet.setImageResource(R.drawable.bonnet_heavydamage);
            staticBonet++;
            if (carList.contains("Bonnet")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Bonnet"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Bonnet", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Bonnet");
                    int id = carList.indexOf("Bonnet");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bonnet"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Bonnet");
                    int id = carList.indexOf("Bonnet");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bonnet"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticBonet == 2) {
            imgBonet.setImageResource(R.drawable.bonnet);
            staticBonet = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Bonnet").commit();
            map.remove("Bonnet");

            int tm = carList.indexOf("Bonnet");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Bonnet");
        }
    }

    public void changeImgFrntWindSheild(int frontWindShield) {

        staticWindShieldFrnt = frontWindShield;
        int a = carList.size();
        if (staticWindShieldFrnt == 0) {
            imgFrntWindSheild.setImageResource(R.drawable.windshield_front_lightdamage);

            staticWindShieldFrnt++;

            if (carList.contains("Windshield Front")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Windshield Front");
                    int id = carList.indexOf("Windshield Front");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Front"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Windshield Front", "Yellow");

                } else {
                    carList.add(a, "Windshield Front");
                    int id = carList.indexOf("Windshield Front");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Front"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Windshield Front", "Yellow");
                }
            }


        } else if (staticWindShieldFrnt == 1) {
            imgFrntWindSheild.setImageResource(R.drawable.windshield_front_heavydamage);
            staticWindShieldFrnt++;

            if (carList.contains("Windshield Front")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Front"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Windshield Front", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Windshield Front");
                    int id = carList.indexOf("Windshield Front");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Front"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Windshield Front");
                    int id = carList.indexOf("Windshield Front");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Front"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticWindShieldFrnt == 2) {
            imgFrntWindSheild.setImageResource(R.drawable.windshield_front);
            staticWindShieldFrnt = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Windshield Front").commit();
            map.remove("Windshield Front");

            int tm = carList.indexOf("Windshield Front");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Windshield Front");
        }

    }

    public void changeRoof(int roof) {

        staticRoof = roof;
        int a = carList.size();
        if (staticRoof == 0) {
            imgRoof.setImageResource(R.drawable.roof_lightdamage);

            staticRoof++;

            if (carList.contains("Roof")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Roof");
                    int id = carList.indexOf("Roof");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Roof"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Roof", "Yellow");

                } else {
                    carList.add(a, "Roof");
                    int id = carList.indexOf("Roof");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Roof"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Roof", "Yellow");
                }
            }


        } else if (staticRoof == 1) {
            imgRoof.setImageResource(R.drawable.roof_heavydamage);
            staticRoof++;
            if (carList.contains("Roof")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Roof"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Roof", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Roof");
                    int id = carList.indexOf("Roof");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Roof"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Roof");
                    int id = carList.indexOf("Roof");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Roof"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRoof == 2) {
            imgRoof.setImageResource(R.drawable.roof);
            staticRoof = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Roof").commit();
            map.remove("Roof");
            int tm = carList.indexOf("Roof");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Roof");
        }

    }

    public void changeImgRearWindShield(int rearWindshield) {


        staticRearWindShield = rearWindshield;
        int a = carList.size();
        if (staticRearWindShield == 0) {
            imgRearWindSheild.setImageResource(R.drawable.windshield_rear_lightdamage);

            staticRearWindShield++;

            if (carList.contains("Windshield Rear")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Windshield Rear");
                    int id = carList.indexOf("Windshield Rear");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Rear"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Windshield Rear", "Yellow");

                } else {
                    carList.add(a, "Windshield Rear");
                    int id = carList.indexOf("Windshield Rear");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Rear"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Windshield Rear", "Yellow");
                }
            }

        } else if (staticRearWindShield == 1) {
            imgRearWindSheild.setImageResource(R.drawable.windshield_rear_heavydamage);
            staticRearWindShield++;
            if (carList.contains("Windshield Rear")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Rear"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Windshield Rear", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Windshield Rear");
                    int id = carList.indexOf("Windshield Rear");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Rear"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Windshield Rear");
                    int id = carList.indexOf("Windshield Rear");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Windshield Rear"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRearWindShield == 2) {
            imgRearWindSheild.setImageResource(R.drawable.windshield_rear);
            staticRearWindShield = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Windshield Rear").commit();
            map.remove("Windshield Rear");

            int tm = carList.indexOf("Windshield Rear");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Windshield Rear");
        }

    }

    public void changeImgDicky(int dicky) {

        staticDicky = dicky;
        int a = carList.size();
        if (staticDicky == 0) {
            imgDicky.setImageResource(R.drawable.dicky_lightdamage);

            staticDicky++;

            if (carList.contains("Dicky")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Dicky");
                    int id = carList.indexOf("Dicky");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Dicky"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Dicky", "Yellow");

                } else {
                    carList.add(a, "Dicky");
                    int id = carList.indexOf("Dicky");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Dicky"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Dicky", "Yellow");
                }
            }


        } else if (staticDicky == 1) {
            imgDicky.setImageResource(R.drawable.dicky_heavydamage);
            staticDicky++;

            if (carList.contains("Dicky")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Dicky"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Dicky", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Dicky");
                    int id = carList.indexOf("Dicky");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Dicky"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Dicky");
                    int id = carList.indexOf("Dicky");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Dicky"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticDicky == 2) {
            imgDicky.setImageResource(R.drawable.dicky);
            staticDicky = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Dicky").commit();
            map.remove("Dicky");

            int tm = carList.indexOf("Dicky");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Dicky");
        }
    }

    public void changeImgTailLeft(int tailLeft) {

        staticTailLeft = tailLeft;
        int a = carList.size();
        if (staticTailLeft == 0) {
            imgTailLeft.setImageResource(R.drawable.tail_light_left_lightdamage);

            staticTailLeft++;

            if (carList.contains("Tail Light Left")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tail Light Left");
                    int id = carList.indexOf("Tail Light Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tail Light Left", "Yellow");

                } else {
                    carList.add(a, "Tail Light Left");
                    int id = carList.indexOf("Tail Light Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Left"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tail Light Left", "Yellow");
                }
            }

        } else if (staticTailLeft == 1) {
            imgTailLeft.setImageResource(R.drawable.tail_light_left_heavydamage);
            staticTailLeft++;
            if (carList.contains("Tail Light Left")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Left"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Tail Light Left", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Tail Light Left");
                    int id = carList.indexOf("Tail Light Left");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Left"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Tail Light Left");
                    int id = carList.indexOf("Tail Light Left");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Left"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticTailLeft == 2) {
            imgTailLeft.setImageResource(R.drawable.tail_light_left);
            staticTailLeft = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tail Light Left").commit();
            map.remove("Tail Light Left");

            int tm = carList.indexOf("Tail Light Left");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Tail Light Left");
        }
    }

    public void changeImgTailRight(int tailRight) {

        staticTailRight = tailRight;
        int a = carList.size();
        if (staticTailRight == 0) {
            imgTailRight.setImageResource(R.drawable.tail_light_right_lightdamage);

            staticTailRight++;

            if (carList.contains("Tail Light Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tail Light Right");
                    int id = carList.indexOf("Tail Light Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tail Light Right", "Yellow");


                } else {
                    carList.add(a, "Tail Light Right");
                    int id = carList.indexOf("Tail Light Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tail Light Right", "Yellow");
                }
            }

        } else if (staticTailRight == 1) {
            imgTailRight.setImageResource(R.drawable.tail_light_right_heavydamage);
            staticTailRight++;
            if (carList.contains("Tail Light Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Tail Light Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Tail Light Right");
                    int id = carList.indexOf("Tail Light Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Tail Light Right");
                    int id = carList.indexOf("Tail Light Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tail Light Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }

        } else if (staticTailRight == 2) {
            imgTailRight.setImageResource(R.drawable.tail_light_right);
            staticTailRight = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tail Light Right").commit();
            map.remove("Tail Light Right");

            int tm = carList.indexOf("Tail Light Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Tail Light Right");
        }
    }

    public void changeImgBumperRear(int bumperRear) {

        staticBumperRear = bumperRear;

        int a = carList.size();
        if (staticBumperRear == 0) {
            getImgBumperRear.setImageResource(R.drawable.bumper_rear_lightdamage);

            staticBumperRear++;

            if (carList.contains("Bumper Rear")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Bumper Rear");
                    int id = carList.indexOf("Bumper Rear");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Rear"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bumper Rear", "Yellow");

                } else {
                    carList.add(a, "Bumper Rear");
                    int id = carList.indexOf("Bumper Rear");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Rear"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Bumper Rear", "Yellow");
                }
            }


        } else if (staticBumperRear == 1) {
            getImgBumperRear.setImageResource(R.drawable.bumper_rear_heavydamage);
            staticBumperRear++;
            if (carList.contains("Bumper Rear")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Rear"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Bumper Rear", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Bumper Rear");
                    int id = carList.indexOf("Bumper Rear");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Rear"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Bumper Rear");
                    int id = carList.indexOf("Bumper Rear");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Bumper Rear"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticBumperRear == 2) {
            getImgBumperRear.setImageResource(R.drawable.bumper_rear);
            staticBumperRear = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Bumper Rear").commit();
            map.remove("Bumper Rear");

            int tm = carList.indexOf("Bumper Rear");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Bumper Rear");
        }

    }


    public void changeImgTopRightTyre(int topRightTyre) {


        staticTopRightTyre = topRightTyre;
        int a = carList.size();
        if (staticTopRightTyre == 0) {
            imgTopRightTyre.setImageResource(R.drawable.tyre_lightdamage);

            staticTopRightTyre++;

            if (carList.contains("Tyre Front Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tyre Front Right");
                    int id = carList.indexOf("Tyre Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Front Right", "Yellow");

                } else {
                    carList.add(a, "Tyre Front Right");
                    int id = carList.indexOf("Tyre Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Front Right", "Yellow");
                }
            }

        } else if (staticTopRightTyre == 1) {
            imgTopRightTyre.setImageResource(R.drawable.tyre_heavydamage);
            staticTopRightTyre++;
            if (carList.contains("Tyre Front Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Tyre Front Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Tyre Front Right");
                    int id = carList.indexOf("Tyre Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Tyre Front Right");
                    int id = carList.indexOf("Tyre Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticTopRightTyre == 2) {
            imgTopRightTyre.setImageResource(R.drawable.tyre);
            staticTopRightTyre = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tyre Front Right").commit();
            map.remove("Tyre Front Right");

            int tm = carList.indexOf("Tyre Front Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Tyre Front Right");

        }

    }

    public void changeImgRightFender(int rightFender) {

        staticRightFender = rightFender;
        int a = carList.size();
        if (staticRightFender == 0) {
            imgRightFender.setImageResource(R.drawable.fender_right_lightdamage);

            staticRightFender++;

            if (carList.contains("Fender Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Fender Right");
                    int id = carList.indexOf("Fender Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Fender Right", "Yellow");

                } else {
                    carList.add(a, "Fender Right");
                    int id = carList.indexOf("Fender Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Fender Right", "Yellow");
                }
            }

        } else if (staticRightFender == 1) {
            imgRightFender.setImageResource(R.drawable.fender_right_heavydamage);
            staticRightFender++;
            if (carList.contains("Fender Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Fender Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Fender Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Fender Right");
                    int id = carList.indexOf("Fender Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Fender Right");
                    int id = carList.indexOf("Fender Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Fender Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRightFender == 2) {
            imgRightFender.setImageResource(R.drawable.fender_right);
            staticRightFender = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Fender Right").commit();
            map.remove("Fender Right");

            int tm = carList.indexOf("Fender Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Fender Right");

        }

    }

    public void changeImgTopRightRim(int topRightRim) {

        staticTopRightRim = topRightRim;
        int a = carList.size();
        if (staticTopRightRim == 0) {
            imgTopRightRim.setImageResource(R.drawable.rim_lightdamage);

            staticTopRightRim++;

            if (carList.contains("Rim Front Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Rim Front Right");
                    int id = carList.indexOf("Rim Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Front Right", "Yellow");

                } else {
                    carList.add(a, "Rim Front Right");
                    int id = carList.indexOf("Rim Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Front Right", "Yellow");
                }
            }

        } else if (staticTopRightRim == 1) {
            imgTopRightRim.setImageResource(R.drawable.rim_heavydamage);
            staticTopRightRim++;
            if (carList.contains("Rim Front Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Rim Front Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Rim Front Right");
                    int id = carList.indexOf("Rim Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);
                    map.put("Rim Front Right", "Yellow");

                } else {
                    carList.add(a, "Rim Front Right");
                    int id = carList.indexOf("Rim Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticTopRightRim == 2) {
            imgTopRightRim.setImageResource(R.drawable.rim);
            staticTopRightRim = 0;
            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Rim Front Right").commit();
            map.remove("Rim Front Right");


            int tm = carList.indexOf("Rim Front Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Rim Front Right");
        }

    }

    public void changeImgPillarRight(int pillarRight) {

        staticPillarRight = pillarRight;
        int a = carList.size();
        if (staticPillarRight == 0) {
            imgPillarRight.setImageResource(R.drawable.a_pillar_right_lightdamage);

            staticPillarRight++;

            if (carList.contains("Right Pillar")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Right Pillar");
                    int id = carList.indexOf("Right Pillar");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Right Pillar"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Right Pillar", "Yellow");

                } else {
                    carList.add(a, "Right Pillar");
                    int id = carList.indexOf("Right Pillar");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Right Pillar"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Right Pillar", "Yellow");
                }
            }


        } else if (staticPillarRight == 1) {
            imgPillarRight.setImageResource(R.drawable.a_pillar_right_heavydamage);
            staticPillarRight++;
            if (carList.contains("Right Pillar")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Right Pillar"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Right Pillar", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Right Pillar");
                    int id = carList.indexOf("Right Pillar");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Right Pillar"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Right Pillar");
                    int id = carList.indexOf("Right Pillar");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Right Pillar"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }

        } else if (staticPillarRight == 2) {
            imgPillarRight.setImageResource(R.drawable.a_pillar_right);
            staticPillarRight = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Right Pillar").commit();
            map.remove("Right Pillar");


            int tm = carList.indexOf("Right Pillar");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Right Pillar");
        }

    }

    public void changeImgRightDoorFrnt(int rightDoorFrnt) {

        staticRightDoorFrnt = rightDoorFrnt;
        int a = carList.size();
        if (staticRightDoorFrnt == 0) {
            imgRightDoorFrnt.setImageResource(R.drawable.door_front_right_lightdamage);

            staticRightDoorFrnt++;


            if (carList.contains("Door Front Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Door Front Right");
                    int id = carList.indexOf("Door Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Front Right", "Yellow");

                } else {
                    carList.add(a, "Door Front Right");
                    int id = carList.indexOf("Door Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Front Right", "Yellow");
                }
            }

        } else if (staticRightDoorFrnt == 1) {
            imgRightDoorFrnt.setImageResource(R.drawable.door_front_right_heavydamage);
            staticRightDoorFrnt++;
            if (carList.contains("Door Front Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Door Front Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Door Front Right");
                    int id = carList.indexOf("Door Front Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Door Front Right");
                    int id = carList.indexOf("Door Front Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Front Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRightDoorFrnt == 2) {
            imgRightDoorFrnt.setImageResource(R.drawable.door_front_right);
            staticRightDoorFrnt = 0;

            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Door Front Right").commit();
            map.remove("Door Front Right");


            int tm = carList.indexOf("Door Front Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Door Front Right");
        }

    }

    public void changeImgRightDoorRear(int rightDoorRear) {

        staticRightDoorRear = rightDoorRear;
        int a = carList.size();
        if (staticRightDoorRear == 0) {
            imgRightDoorRear.setImageResource(R.drawable.door_rear_right_lightdamage);

            staticRightDoorRear++;

            if (carList.contains("Door Rear Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Door Rear Right");
                    int id = carList.indexOf("Door Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Rear Right", "Yellow");

                } else {
                    carList.add(a, "Door Rear Right");
                    int id = carList.indexOf("Door Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Door Rear Right", "Yellow");
                }
            }
        } else if (staticRightDoorRear == 1) {
            imgRightDoorRear.setImageResource(R.drawable.door_rear_right_heavydamage);
            staticRightDoorRear++;
            if (carList.contains("Door Rear Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Door Rear Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Door Rear Right");
                    int id = carList.indexOf("Door Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                } else {
                    carList.add(a, "Door Rear Right");
                    int id = carList.indexOf("Door Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Door Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRightDoorRear == 2) {
            imgRightDoorRear.setImageResource(R.drawable.door_rear_right);
            staticRightDoorRear = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Door Rear Right").commit();
            map.remove("Door Rear Right");

            int tm = carList.indexOf("Door Rear Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Door Rear Right");

        }

    }

    public void changeImgRightRunningBoard(int runningBoardRight) {

        staticRightRunningBoard = runningBoardRight;
        int a = carList.size();

        if (staticRightRunningBoard == 0) {
            imgRightRunningBoard.setImageResource(R.drawable.running_board_right_lightdamage);

            staticRightRunningBoard++;

            if (carList.contains("Running Board Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Running Board Right");
                    int id = carList.indexOf("Running Board Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Running Board Right", "Yellow");

                } else {
                    carList.add(a, "Running Board Right");
                    int id = carList.indexOf("Running Board Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Running Board Right", "Yellow");
                }
            }

        } else if (staticRightRunningBoard == 1) {
            imgRightRunningBoard.setImageResource(R.drawable.running_board_right_heavydamage);
            staticRightRunningBoard++;
            if (carList.contains("Running Board Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Running Board Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Running Board Right");
                    int id = carList.indexOf("Running Board Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Running Board Right");
                    int id = carList.indexOf("Running Board Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Running Board Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticRightRunningBoard == 2) {
            imgRightRunningBoard.setImageResource(R.drawable.running_board_right);
            staticRightRunningBoard = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Running Board Right").commit();
            map.remove("Running Board Right");


            int tm = carList.indexOf("Running Board Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Running Board Right");
        }

    }

    public void changeImgQuarterRight(int quarterRight) {


        staticQuarterRight = quarterRight;
        int a = carList.size();
        if (staticQuarterRight == 0) {
            imgQuarterRight.setImageResource(R.drawable.quarter_panel_right_lightdamage);

            staticQuarterRight++;

            if (carList.contains("Quarter Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Quarter Right");
                    int id = carList.indexOf("Quarter Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Quarter Right", "Yellow");

                } else {
                    carList.add(a, "Quarter Right");
                    int id = carList.indexOf("Quarter Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Quarter Right", "Yellow");
                }
            }

        } else if (staticQuarterRight == 1) {
            imgQuarterRight.setImageResource(R.drawable.quarter_panel_right_heavydamage);
            staticQuarterRight++;
            if (carList.contains("Quarter Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Quarter Right", "Red");
            } else {

                if (a == 0) {
                    carList.add(a, "Quarter Right");
                    int id = carList.indexOf("Quarter Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Quarter Right");
                    int id = carList.indexOf("Quarter Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Quarter Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticQuarterRight == 2) {
            imgQuarterRight.setImageResource(R.drawable.quarter_panel_right);
            staticQuarterRight = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Quarter Right").commit();
            map.remove("Quarter Right");


            int tm = carList.indexOf("Quarter Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Quarter Right");

        }

    }

    public void changeImgBottomRightTyre(int bottomRightTyre) {

        staticBottomRightTyre = bottomRightTyre;
        int a = carList.size();
        if (staticBottomRightTyre == 0) {
            imgBottomRightTyre.setImageResource(R.drawable.tyre_lightdamage);

            staticBottomRightTyre++;

            if (carList.contains("Tyre Rear Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Tyre Rear Right");
                    int id = carList.indexOf("Tyre Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Rear Right", "Yellow");

                } else {
                    carList.add(a, "Tyre Rear Right");
                    int id = carList.indexOf("Tyre Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Tyre Rear Right", "Yellow");
                }
            }

        } else if (staticBottomRightTyre == 1) {
            imgBottomRightTyre.setImageResource(R.drawable.tyre_heavydamage);
            staticBottomRightTyre++;
            if (carList.contains("Tyre Rear Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Tyre Rear Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Tyre Rear Right");
                    int id = carList.indexOf("Tyre Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Tyre Rear Right");
                    int id = carList.indexOf("Tyre Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Tyre Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticBottomRightTyre == 2) {
            imgBottomRightTyre.setImageResource(R.drawable.tyre);
            staticBottomRightTyre = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Tyre Rear Right").commit();
            map.remove("Tyre Rear Right");


            int tm = carList.indexOf("Tyre Rear Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Tyre Rear Right");
        }

    }

    public void changeImgBottomRightRim(int bottomRightRim) {


        staticBottomRightRim = bottomRightRim;
        int a = carList.size();
        if (staticBottomRightRim == 0) {
            imgBottomRightRim.setImageResource(R.drawable.rim_lightdamage);

            staticBottomRightRim++;


            if (carList.contains("Rim Rear Right")) {

            } else {

                Log.e("First Size", a + "");

                if (a == 0) {
                    carList.add(a, "Rim Rear Right");
                    int id = carList.indexOf("Rim Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Rear Right", "Yellow");

                } else {
                    carList.add(a, "Rim Rear Right");
                    int id = carList.indexOf("Rim Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Right"));
                    menuItem.setIcon(R.drawable.icon_yellow);
                    map.put("Rim Rear Right", "Yellow");
                }
            }


        } else if (staticBottomRightRim == 1) {
            imgBottomRightRim.setImageResource(R.drawable.rim_heavydamage);
            staticBottomRightRim++;
            if (carList.contains("Rim Rear Right")) {
                MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Right"));
                menuItem.setIcon(R.drawable.icon_red);
                map.put("Rim Rear Right", "Red");
            } else {
                if (a == 0) {
                    carList.add(a, "Rim Rear Right");
                    int id = carList.indexOf("Rim Rear Right");
                    menu.add(1, id, 1, carList.get(0).toString());
                    //menu.add(carList.get(0).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);


                } else {
                    carList.add(a, "Rim Rear Right");
                    int id = carList.indexOf("Rim Rear Right");
                    menu.add(1, id, 1, carList.get(a++).toString());
                    //  menu.add(carList.get(a++).toString());
                    MenuItem menuItem = menu.getItem(carList.indexOf("Rim Rear Right"));
                    menuItem.setIcon(R.drawable.icon_red);

                }
            }


        } else if (staticBottomRightRim == 2) {
            imgBottomRightRim.setImageResource(R.drawable.rim);
            staticBottomRightRim = 0;


            SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();
            keyValuesEditor.remove("Rim Rear Right").commit();
            map.remove("Rim Rear Right");

            int tm = carList.indexOf("Rim Rear Right");
            int id = menu.getItem(tm).getItemId();
            menu.removeItem(id);
            carList.remove("Rim Rear Right");
        }

    }


    @Override
    public void onBackPressed() {


        SharedPreferences.Editor keyValuesEditor = sharedPreferences.edit();

        Log.e("Pref", "Detected");
        if (map.isEmpty()) {
            Log.e("In Map", "Yes");
            keyValuesEditor.remove("Contains");


        } else {
            for (String s : map.keySet()) {
                keyValuesEditor.putString(s, map.get(s));
            }

            keyValuesEditor.commit();
            keyValuesEditor.putString("Contains", "Yes");

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main, menu);

       /* Drawable drawable=menu.findItem(R.id.menuLeftTopTyre).getIcon();


        if(drawable!=null)
        {
            drawable.mutate();
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setIcon(int i, String color) {

        MenuItem menuItem = navigationView.getMenu().getItem(i);
        if (color == "Yellow") {
            menuItem.setIcon(R.drawable.icon_yellow);
        } else if (color == "Red") {
            menuItem.setIcon(R.drawable.icon_red);
        } else if (color == "Grey") {
            menuItem.setIcon(R.drawable.icon_grey);
        }
    }
}
