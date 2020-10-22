package com.firdadev.runsheetv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firdadev.runsheetv2.activity.LoginActivity;
import com.firdadev.runsheetv2.activity.prarunsheet.PraRunsheetActivity;
import com.firdadev.runsheetv2.activity.prarunsheet.praRunsheetNotApprovedActivity;
import com.firdadev.runsheetv2.activity.runsheet.runsheetNotApprovedActivity;
import com.firdadev.runsheetv2.model.LoginResponse;
import com.firdadev.runsheetv2.network.ServiceClient;
import com.firdadev.runsheetv2.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView btnLogout;
    SessionManager session;
    TextView user, branch, zone, zoneName;
    private Context context;
    private String username, kodeCbg, zoneCode, namaZona;
    private String KEY_NAME = "USERNAME";
    private String KEY_BRANCH = "BRANCH";
    private String KEY_ZONE_CODE ="ZONECODE";
    private String KEY_ZONE_NAME ="ZONENAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        user = findViewById(R.id.txtUser);
        //String
        username = user.getText().toString();
        branch = findViewById(R.id.txtKdcabang);
        kodeCbg = branch.getText().toString();
        zone = findViewById(R.id.txtKdCabangUser);
        zoneCode = zone.getText().toString();

        Bundle extras = getIntent().getExtras();
        username = extras.getString(KEY_NAME);
        user.setText(username);
        kodeCbg = extras.getString(KEY_BRANCH);
        branch.setText(kodeCbg);
        zoneCode = extras.getString(KEY_ZONE_CODE);
        zone.setText(zoneCode);

        btnLogout = findViewById(R.id.log_out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MainActivity.this.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
       // getData();
    }
        public void PraRunsheet(View v) {

        finish();
        Intent i = new Intent(getApplicationContext(), PraRunsheetActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_NAME,username);
            bundle.putString(KEY_BRANCH, kodeCbg);
            bundle.putString(KEY_ZONE_CODE, zoneCode);
            //bundle.putString(KEY_ZONE_NAME,namaZona);
            //bundle.putString(KEY_ZONE_NAME,namaZona);
            i.putExtras(bundle);
            //bundle.putString();*/
        startActivity(i);
    }

    public void Runsheet(View v){
        //Intent i = new Intent (this, RunsheetActivity.class);
       // startActivity(i);
    }

    public void UnPraRunsheet(View v){
        Intent i = new Intent (this, praRunsheetNotApprovedActivity.class);
        startActivity(i);
    }

    public void UnRunsheet(View v){
        Intent i = new Intent (this, runsheetNotApprovedActivity.class);
        startActivity(i);
    }

}
