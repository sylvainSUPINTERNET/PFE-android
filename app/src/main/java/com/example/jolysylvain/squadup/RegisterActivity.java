package com.example.jolysylvain.squadup;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/*
 *
 * http://loopj.com/android-async-http/
 *
 */
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;


public class RegisterActivity extends AppCompatActivity {

    EditText lastname;
    EditText email;
    EditText password;
    EditText passwordConfirmed;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.context = this;

        setContentView(R.layout.activity_register);


    }

    public void register(View v) {
        // do something when the button is clicked
        lastname = findViewById(R.id.register_lastname);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        passwordConfirmed = findViewById(R.id.register_passwordConfirmed);


        //TODO move this call API is just a test
        AsyncHttpClient client = new AsyncHttpClient();

        final RequestParams body = new RequestParams();
        body.put("name", lastname.getText().toString());
        body.put("email", email.getText().toString());
        body.put("password", password.getText().toString());
        body.put("passwordConfirmed", passwordConfirmed.getText().toString());


            //10.0.2.2 for emulator
            //for real device => ipconfig and copy IPv4
            client.post("http://192.168.1.17:8080/api/user/register", body, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    JSONObject json = new JSONObject(
                            new String(responseBody));
                    System.out.println(json);

                    DialogManager dialogManager = new DialogManager(json.toString(), "Erreur lors de l'enregistrement", context);
                    dialogManager.generateDialog().show();


                } catch (JSONException e) {
                    System.out.println("ERROR");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
