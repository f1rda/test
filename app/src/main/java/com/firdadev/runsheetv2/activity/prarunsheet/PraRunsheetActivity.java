package com.firdadev.runsheetv2.activity.prarunsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firdadev.runsheetv2.BuildConfig;
import com.firdadev.runsheetv2.MainActivity;
import com.firdadev.runsheetv2.R;
import com.firdadev.runsheetv2.activity.LoginActivity;
import com.firdadev.runsheetv2.adapter.AlertDialogManager;
import com.firdadev.runsheetv2.api.Api;
import com.firdadev.runsheetv2.model.DefaultResponse;
import com.firdadev.runsheetv2.model.LoginResponse;
import com.firdadev.runsheetv2.model.courier.CourierResponse;
import com.firdadev.runsheetv2.model.courier.DetailItem;
import com.firdadev.runsheetv2.model.prarunsheet.GeneratePraResponse;
import com.firdadev.runsheetv2.network.ServiceClient;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kinda.alert.KAlertDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraRunsheetActivity extends AppCompatActivity {
    final String usernameApi = BuildConfig.Username;
    final String api_key = BuildConfig.ApiKey;

    private Button generateNumber, Approve, viewDetail, scanBarcode, save, ok;
    private Api api;
    private Context mContext;
    private SearchableSpinner a;
    private LinearLayout linearLayout;
    private EditText textGenerateNumber, dateView, remarks, connote;
    TextView user, branch, zone, zoneName, namaZona, cabang, zoneCourier, userID, courieri;
    private String username, kodeCbg, zoneC, zoneN, connoteNo, generateNumberPra, remark, courierId, list, statusId;
    private String date, courier;

    private String KEY_NAME = "USERNAME";
    private String KEY_BRANCH = "BRANCH";
    private String KEY_ZONE_CODE = "ZONECODE";
    private String KEY_ZONE_NAME = "ZONENAME";

    private List<DetailItem> detailItems = new ArrayList<>();
    private ImageView imageView;
    private LoginResponse loginResponse;

    private LinearLayout courierIDspin;

    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TableLayout tableLayout;
    TableRow newRow;
    TextView cNnoteNo;

    List<String> items;

    AlertDialogManager alert = new AlertDialogManager();
    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    private ListView listView;
    private ArrayAdapter adapter;
    //ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> names = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prarunsheet);

        textGenerateNumber = findViewById(R.id.pra_runsheet);
        dateView = findViewById(R.id.date);
        remarks = findViewById(R.id.remarks);
        connote = findViewById(R.id.scanConnote);
        branch = findViewById(R.id.txtKdcabang);
        zoneName = findViewById(R.id.zoneCdName);
        namaZona = findViewById(R.id.nameZona);
        user = findViewById(R.id.txtUser);
        generateNumber = findViewById(R.id.new_number);
        generateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(dateView);
                getNumber(usernameApi, api_key, kodeCbg);
            }
        });

        a = findViewById(R.id.courierId);
        listView = findViewById(R.id.listView1);
        imageView = findViewById(R.id.save);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                names);
        a.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connoteNo = connote.getText().toString().trim();
                if(connoteNo.isEmpty()){
                    connote.setError("CNOTE is required");
                    connote.requestFocus();
                    return;
                }
                names.add(connote.getText().toString());
                connote.setText("");
                adapter.notifyDataSetChanged();
            }

        });

        user = findViewById(R.id.txtUser);
        username = user.getText().toString();
        branch = findViewById(R.id.txtKdcabang);
        kodeCbg = branch.getText().toString();
        zone = findViewById(R.id.txtKdCabangUser);
        zoneC = zone.getText().toString();
        //zoneCourier
        zoneName = findViewById(R.id.zoneCdName);
        zoneN = zoneName.getText().toString();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString(KEY_NAME);
            user.setText(username);
            kodeCbg = extras.getString(KEY_BRANCH);
            branch.setText(kodeCbg);
            zoneC = extras.getString(KEY_ZONE_CODE);
            zone.setText(zoneC);
            zoneN = extras.getString(KEY_ZONE_CODE);
            zoneName.setText(zoneN);
        }

        getList(usernameApi, api_key, zoneC);
        scanBarcode = findViewById(R.id.btnScanBarcode);
        setupToolbar();
        viewDetail = findViewById(R.id.btnViewDetail);
        viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailConnote();
            }
        });
        //btnViewDetail
        Approve = findViewById(R.id.approve);

        Approve.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                generateNumberPra = textGenerateNumber.getText().toString().trim();

                courierId = a.getSelectedItem().toString();
                if(generateNumberPra.isEmpty()){
                    textGenerateNumber.setError("Number Pra is required");
                    textGenerateNumber.requestFocus();
                    return;
                }
                else if(courierId.isEmpty()){
                    TextView errorText = (TextView) courieri.getText();
                    errorText.setError("Courier ID is required");
                    errorText.requestFocus();
                    return;
                }
                else {
                    Approve();
                }
            }
        });

     // loadData();


    }

    private void loadData(){
        Intent intent = getIntent();
        loginResponse = (LoginResponse) intent.getSerializableExtra("zone_name");
        namaZona.setText(loginResponse.getZoneName());
    }

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

    public void clear(){
        textGenerateNumber.setText("");
        dateView.setText("");
        remarks.setText("");
        connote.setText("");
    }

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
                connote.setText(scanContent);
            }


        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

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

                            ArrayAdapter arrayAdapter = new ArrayAdapter(PraRunsheetActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            searchableSpinner.setAdapter(arrayAdapter);
                            searchableSpinner.setTitle("Select Item");
                            searchableSpinner.setPositiveButton("OK");

                        } else {
                            alert.showAlertDialog(PraRunsheetActivity.this, "Gagal mengambil data.. ", "Silahkan coba kembali", false);
                        }
                    }

                    @Override
                    public void onFailure(Call<CourierResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal ... ", "Koneksi Internet Bermasalah ", false);
                    }

                });

    }

    public void setDate(EditText view) {

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }

    //menampilkan form dialog detail cNote
    private void detailConnote() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PraRunsheetActivity.this);
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

    private void getNumber(String usernameApi, String apiKey, final String kodeCabang) {

        ServiceClient
                .buildServiceClient()
                .generatePra(usernameApi, apiKey, kodeCabang)
                .enqueue(new Callback<GeneratePraResponse>() {
                    @Override
                    public void onResponse(Call<GeneratePraResponse> call, Response<GeneratePraResponse> response) {
                        EditText textGenerateNumber = (EditText) findViewById(R.id.pra_runsheet);
                        if (response.isSuccessful()) {

                            GeneratePraResponse generatePraResponse = response.body();
                            if (generatePraResponse.isStatus() == true) {

                                String a = generatePraResponse.getNoPraposting();
                                textGenerateNumber.setText(a);
                            }

                        } else {
                            alert.showAlertDialog(PraRunsheetActivity.this, "Gagal mengambil data.. ", "Silahkan coba kembali", false);
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneratePraResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal ... ", "Koneksi Internet Bermasalah ", false);
                    }

                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Approve() {

        generateNumberPra = textGenerateNumber.getText().toString().trim();
        date = dateView.getText().toString().trim();
        remark = remarks.getText().toString().trim();
        //connoteNo = connote.getText().toString().trim();
        kodeCbg = branch.getText().toString().trim();
        zoneN = zoneName.getText().toString().trim();
        username = user.getText().toString().trim();
        courierId = a.getSelectedItem().toString();

        // Start Add Delimiter scan barcode into database
        String[] array = new String[names.size()];
        names.toArray(array);
        String joinedString = String.join(", ", names);
        // End Delimiter

        ServiceClient
                .buildServiceClient()
                .prarunsheet(usernameApi, api_key, generateNumberPra, kodeCbg, date, zoneN, remark, username, joinedString, courierId)
                .enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (response.isSuccessful()) {
                                    showCustomDialog();
                                    clear();

                        }else
                        {
                            showCustomDialog2();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        alert.showAlertDialog(mContext, "Gagal ... ", "Koneksi Internet Bermasalah ", false);
                    }
                });
    }
}
