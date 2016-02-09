package com.hdfc.claims.SplashLoginPasscode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.APIURLs;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private SharedPreferences pref;

    private EditText usernameEdt;
    private EditText passwordEdt;
    private TextView loginBtn;

    private CheckBox chkEye;

    private RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        initializeViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!pref.getString("OnlinePassCode", "").equals("")) {
            finish();
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    private void initializeViews() {

        context = LoginActivity.this;
        usernameEdt = (EditText) findViewById(R.id.usernameEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        chkEye = (CheckBox) findViewById(R.id.chkEye);

        view = (RelativeLayout) findViewById(R.id.rl);

        usernameEdt.setText("MANOJ.GOEL");
        passwordEdt.setText("hdfc123");

        chkEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    passwordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginBtn:

                getValidationCheck();


                break;

            default:
                break;
        }
    }

    private void getValidationCheck() {

        if (Utility.checkInternetConnection(this)) {

            if (Utility.isTextEmpty(usernameEdt)) {
                Snackbar snackbar = Snackbar
                        .make(view, "Please enter Username", Snackbar.LENGTH_LONG);
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

                snackbar.show();
                usernameEdt.requestFocus();
                return;
            }


            if (Utility.isTextEmpty(passwordEdt)) {
                Snackbar snackbar = Snackbar
                        .make(view, "Please enter Password", Snackbar.LENGTH_LONG);
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

                snackbar.show();
                passwordEdt.requestFocus();
                return;
            }

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("userId", usernameEdt.getText().toString());
            jsonParams.put("password", passwordEdt.getText().toString());

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, APIURLs.AUTH_USER_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            pDialog.hide();
                            try {
                                response = response.getJSONObject("AuthenticateUserResult");
                                String oAuthkey = response.getString("oAuthkey");

                                pref = context.getSharedPreferences("Session Data", MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString("Username", usernameEdt.getText().toString());
                                edit.putString("Password", passwordEdt.getText().toString());
                                edit.putString("OnlinePassCode", response.getString("PassCode"));
                                edit.putString("oAuthkey", response.getString("oAuthkey"));
                                edit.commit();

                                /*Snackbar snackbar = Snackbar
                                        .make(rl, oAuthkey, Snackbar.LENGTH_LONG);
                                snackbar.show();*/

                                Intent intent = new Intent(context, PasscodeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up, R.anim.no_change);

                            } catch (JSONException e) {
                                pDialog.hide();
                                Snackbar snackbar = Snackbar
                                        .make(view, "Invalid login!", Snackbar.LENGTH_LONG);
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

                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(jsonRequest);


        } else {
            Snackbar snackbar = Snackbar
                    .make(view, "No internet connection", Snackbar.LENGTH_LONG);
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark));

            snackbar.show();
            return;
        }

    }
}
