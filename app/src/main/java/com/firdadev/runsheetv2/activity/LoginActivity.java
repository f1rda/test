package com.firdadev.runsheetv2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.firdadev.runsheetv2.BuildConfig;
import com.firdadev.runsheetv2.MainActivity;
import com.firdadev.runsheetv2.R;
import com.firdadev.runsheetv2.activity.prarunsheet.PraRunsheetActivity;
import com.firdadev.runsheetv2.adapter.AlertDialogManager;
import com.firdadev.runsheetv2.model.LoginResponse;
import com.firdadev.runsheetv2.network.ServiceClient;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends Activity {

    private EditText etUser;
    private EditText etPassword;
    private TextView etBranch, etZoneCode;
    private Button btnLogin;
    private Context mContext;

    private String KEY_NAME ="USERNAME";
    private String KEY_BRANCH ="BRANCH";
    private String KEY_ZONE_CODE ="ZONECODE";
    private String KEY_ZONE_NAME ="ZONENAME";

    Activity activity = LoginActivity.this;

    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameApi = BuildConfig.Username;
                String api_key = BuildConfig.ApiKey;
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();

                if (user.isEmpty()) {
                    etUser.setError("User Empty");
                    return;
                }

                if (password.isEmpty()) {
                    etPassword.setError("Password Empty");
                    return;
                }

                doLogin(usernameApi, api_key,user, password);
            }
        });
    }

    private void doLogin(String usernameApi, String api_key, String user, String password) {
        etUser.setEnabled(false);
        etPassword.setEnabled(false);
        btnLogin.setEnabled(false);

        ServiceClient
                .buildServiceClient()
                .auth(usernameApi,api_key,user,password)
                .enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        String user;
                        etUser = findViewById(R.id.username);
                        user = etUser.getText().toString();

                        if (response.isSuccessful()) {
                            LoginResponse auth = response.body();
                            if (auth.isStatus() == true) {

                                //auth.getUserId();
                                //auth.getBranch();
                               // auth.getOrigin();
                               // auth.getZoneCode();
                               // auth.getZoneName();

                                finish();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(KEY_NAME,user);
                                bundle.putString(KEY_BRANCH, auth.getBranch());
                                bundle.putString(KEY_ZONE_CODE, auth.getZoneCode());
                                bundle.putString(KEY_ZONE_NAME,auth.getZoneName());
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } else {
                                alert.showAlertDialog(LoginActivity.this, "Login gagal.. ", "User atau password salah",false);
                                //Toast.makeText(LoginActivity.this, auth.getErrorReason(), Toast.LENGTH_LONG).show();
                                //Toast.makeText(LoginActivity.this, "Username atau password salah, silahkan login kembali", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > "+ t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal.. ", "Koneksi Internet Bermasalah", false);
                    }
                });

}
}
