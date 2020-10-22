package com.firdadev.runsheetv2.activity.runsheet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firdadev.runsheetv2.BuildConfig;
import com.firdadev.runsheetv2.R;
import com.firdadev.runsheetv2.adapter.AlertDialogManager;
import com.firdadev.runsheetv2.model.courier.CourierResponse;
import com.firdadev.runsheetv2.model.courier.DetailItem;
import com.firdadev.runsheetv2.model.runsheet.GenerateRunsheetNoResponse;
import com.firdadev.runsheetv2.network.ServiceClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kinda.alert.KAlertDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunsheetActivity extends AppCompatActivity {

    final String usernameApi = BuildConfig.Username;
    final String api_key = BuildConfig.ApiKey;

    Button btnNoRunsheet, approved;
    TextView userID, branch, zoneUser;
    EditText edtRunsheetNo, edtDate, edtRemarks, edtScanResullt;
    SearchableSpinner ssPrarunsheet, ssGetCourier;

    String kodeCbg, zoneCode;

    Context mContext;
    AlertDialogManager alert = new AlertDialogManager();

    private List<DetailItem> detailItems = new ArrayList<>();
    private ArrayList<String> names = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_runsheet);

        setupToolbar();
        btnNoRunsheet = findViewById(R.id.new_number);
        approved = findViewById(R.id.approve);
        userID = findViewById(R.id.txtUser);
        branch = findViewById(R.id.txtKdcabang);
        zoneUser = findViewById(R.id.txtKdCabangUser);
        edtRunsheetNo = findViewById(R.id.runsheet);
        edtDate = findViewById(R.id.date);
        edtRemarks = findViewById(R.id.remarks);
        edtScanResullt = findViewById(R.id.scanConnote);
        ssPrarunsheet = findViewById(R.id.prarunsheetId);
        ssGetCourier = findViewById(R.id.courierId);

        btnNoRunsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate(edtDate);
                getNumber(usernameApi, api_key,kodeCbg);
            }
        });
        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getList(usernameApi, api_key, zoneCode);
    }

    //Start Setup Toolbar back to home
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_new);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //End


    //Start getDate for Modul Runsheet
    public void setDate(EditText view) {

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }
    //End

    //Start getRunsheetNo
    private void getNumber(String usernameApi, String apiKey, final String kodeCabang) {

        ServiceClient
                .buildServiceClient()
                .generateRunsheet(usernameApi, apiKey, kodeCabang)
                .enqueue(new Callback<GenerateRunsheetNoResponse>() {
                    @Override
                    public void onResponse(Call<GenerateRunsheetNoResponse> call, Response<GenerateRunsheetNoResponse> response) {
                        if (response.isSuccessful()) {

                            GenerateRunsheetNoResponse generateRunsheetNoResponse = response.body();
                            if (generateRunsheetNoResponse.isStatus() == true) {

                                String a = generateRunsheetNoResponse.getNoPraposting();
                                edtRunsheetNo.setText(a);
                            }

                        } else {
                            alert.showAlertDialog(RunsheetActivity.this, "Gagal mengambil data.. ", "Silahkan coba kembali", false);
                        }
                    }

                    @Override
                    public void onFailure(Call<GenerateRunsheetNoResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal ... ", "Koneksi Internet Bermasalah ", false);
                    }

                });

    }
    //End

    //Start getCourier using param zone code
    private void getList(String usernameApi, String apiKey, final String zoneCode) {

        ServiceClient
                .buildServiceClient()
                .getCourier(usernameApi, apiKey, zoneCode)
                .enqueue(new Callback<CourierResponse>() {
                    @Override
                    public void onResponse(Call<CourierResponse> call, Response<CourierResponse> response) {

                        SearchableSpinner searchableSpinner = (SearchableSpinner) findViewById(R.id.courierId);
                        if (response.isSuccessful()) {

                            CourierResponse courierResponse = response.body();
                            detailItems = courierResponse.getDetail();
                            List<String> listSpinner = new ArrayList<String>();
                            listSpinner.add("");
                            for (int i = 0; i < detailItems.size(); i++) {
                                listSpinner.add(detailItems.get(i).getCourierId());// + " - " + detailItems.get(i).getCourierName());
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(RunsheetActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            searchableSpinner.setAdapter(arrayAdapter);
                            searchableSpinner.setTitle("Select Item");
                            searchableSpinner.setPositiveButton("OK");

                        } else {
                            alert.showAlertDialog(RunsheetActivity.this, "Gagal mengambil data.. ", "Silahkan coba kembali", false);
                        }
                    }

                    @Override
                    public void onFailure(Call<CourierResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal ... ", "Koneksi Internet Bermasalah ", false);
                    }

                });

    }
    //End

    //Start Alert Dialog
    private void showCustomDialog() {
        new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Save Successfull !")
                .show();

    }

    private void showCustomDialog2() {
        new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE)
                .setTitleText("Failed!")
                .setContentText("Save Failed !")
                .show();

    }
    //End

    //Start for clear editText
    public void clear(){
        edtRunsheetNo.setText("");
        edtDate.setText("");
        edtRemarks.setText("");
        edtScanResullt.setText("");
    }
    //End

    //Start Coding for Scan Barcode
    public void scanNow(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            if (scanContent.length() < 12||scanContent.length()>16) {
                Toast toast = Toast.makeText(getApplicationContext(), "Connote Not Found!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                edtScanResullt.setText(scanContent);
            }


        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //End

    //menampilkan form dialog detail cNote
    private void detailConnote() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RunsheetActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.table_runsheet, null);
        alertDialog.setView(convertView);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setTitle("List Connote");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);
        alertDialog.show();
    }
}
