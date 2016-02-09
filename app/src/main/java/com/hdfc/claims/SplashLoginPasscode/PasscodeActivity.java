package com.hdfc.claims.SplashLoginPasscode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hdfc.claims.Dashboard.DashboardContainerActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.APIURLs;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasscodeActivity extends AppCompatActivity implements View.OnClickListener {


    /////Zengdfgdfgvds vdscv    gdkkhjkhjkhjkhjkhjkfgdfgdfgdfgdfgdfgdfggg

    private Context context;

    private SharedPreferences pref;
    private EditText passcodeEdt;
    private TextView passcodeBtn;

   private CheckBox chkEye;

    RelativeLayout rl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        getSupportActionBar().hide();

        initializeViews();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    private void initializeViews() {

        context = PasscodeActivity.this;
        pref = context.getSharedPreferences("Session Data", MODE_PRIVATE);
        passcodeEdt = (EditText) findViewById(R.id.passcodeEdt);
        passcodeBtn = (TextView) findViewById(R.id.passcodeBtn);

        chkEye = (CheckBox) findViewById(R.id.chkEye);

        rl = (RelativeLayout) findViewById(R.id.rl);

        passcodeBtn.setOnClickListener(this);

        passcodeEdt.setText("12345");

        chkEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    passcodeEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passcodeEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.passcodeBtn:

                getValidationCheck();


                break;

            default:
                break;
        }
    }

    private void getValidationCheck() {

        if (Utility.isTextEmpty(passcodeEdt)) {
            Snackbar snackbar = Snackbar.make(rl, R.string.toast_passcode_required, Snackbar.LENGTH_LONG);
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
            snackbar.show();
            passcodeEdt.requestFocus();
            return;
        }


        if (Utility.checkInternetConnection(this)) {
            if (pref.getString("OfflinePassCode", "").equals("")) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("OfflinePassCode", passcodeEdt.getText().toString());
                edit.commit();

                Intent intent = new Intent(context, DashboardContainerActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            } else {


                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);
                pDialog.show();

                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("userId", pref.getString("Username", ""));
                jsonParams.put("passcode", pref.getString("OnlinePassCode", ""));
                jsonParams.put("deviceId", "APA91bFxhRv7GWB9Xu4ryNf3MyWrYoNBT861ToPcmUgMLslhoYhWr_T5iLMj8Jw8CpqItGKWd6g568LEVSgHsDwlvCZAbp3Ocssp8hMabjep4zSVLWRPST2_1QKbAPl2MCw3dqAFEoK5");
                jsonParams.put("devicePlatform", "android");


                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.POST, APIURLs.VERIFY_PASSCODE_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                pDialog.hide();
                                try {
                                    response = response.getJSONObject("VerifyPasscodeResult");
                                    String message = response.getString("Message");

                                    if (message.equals("True") && passcodeEdt.getText().toString().equals(pref.getString("OfflinePassCode", ""))) {
                                        Intent intent = new Intent(context, DashboardContainerActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                                    } else {
                                        Snackbar snackbar = Snackbar
                                                .make(rl, R.string.toast_passcode_invalid, Snackbar.LENGTH_LONG)
                                                .setAction("Forgot Passcode?", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        // get prompts.xml view
                                                        LayoutInflater layoutInflater = LayoutInflater.from(context);

                                                        View promptView = layoutInflater.inflate(R.layout.popup_forgot_passcode, null);

                                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                                        // set prompts.xml to be the layout file of the alertdialog builder
                                                        alertDialogBuilder.setView(promptView);

                                                        final EditText forgotEmail = (EditText) promptView.findViewById(R.id.edtForgotUserName);

                                                        // setup a dialog window
                                                        alertDialogBuilder
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        // get user input and set it to result


                                                                    }
                                                                })
                                                                .setNegativeButton("Cancel",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                dialog.dismiss();
                                                                            }
                                                                        });

                                                        // create an alert dialog
                                                        final AlertDialog alertD = alertDialogBuilder.create();

                                                        alertD.show();

                                                        alertD.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (Utility.isTextEmpty(forgotEmail)) {
                                                                    Snackbar snackbar = Snackbar.make(rl, "Please enter Username", Snackbar.LENGTH_LONG);
                                                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                                                    group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
                                                                    snackbar.show();
                                                                    passcodeEdt.requestFocus();
                                                                    return;
                                                                }

                                                                if (forgotEmail.getText().toString().equals(pref.getString("Username", ""))) {

                                                                    alertD.dismiss();

                                                                    Snackbar snackbar = Snackbar.make(rl, "Passcode is " + pref.getString("OfflinePassCode", ""), Snackbar.LENGTH_LONG);
                                                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                                                    group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
                                                                    snackbar.show();
                                                                } else {
                                                                    Snackbar snackbar = Snackbar.make(rl, "Invalid Username", Snackbar.LENGTH_LONG);
                                                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                                                    group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
                                                                    snackbar.show();
                                                                }

                                                            }
                                                        });
                                                    }
                                                });
                                        ViewGroup group = (ViewGroup) snackbar.getView();
                                        group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
                                        snackbar.show();


                                    }


                                } catch (JSONException e) {
                                    pDialog.hide();
                                    Snackbar snackbar = Snackbar
                                            .make(rl, "Invalid Passcode", Snackbar.LENGTH_LONG);
                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                    group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));
                                    snackbar.show();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.hide();
                                error.printStackTrace();
                            }
                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("oAuthkey", pref.getString("oAuthkey", ""));

                        return headers;
                    }
                };

                Volley.newRequestQueue(this).add(jsonRequest);


                //Animation animationShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                //passcodeEdt.startAnimation(animationShake);


            }


        } else {
           /* Snackbar snackbar = Snackbar
                    .make(rl, "No internet connection", Snackbar.LENGTH_LONG);
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

            snackbar.show();
            return;*/
            if (pref.getString("OfflinePassCode", "").equals("")) {
                Snackbar snackbar = Snackbar
                        .make(rl, "No internet connection", Snackbar.LENGTH_LONG);
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

                snackbar.show();
                return;
            } else {

                if (passcodeEdt.getText().toString().equals(pref.getString("OfflinePassCode", ""))) {
                    Intent intent = new Intent(context, DashboardContainerActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(rl, R.string.toast_passcode_invalid, Snackbar.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) snackbar.getView();
                    group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

                    snackbar.show();
                    return;
                }

            }

        }

    }

}
