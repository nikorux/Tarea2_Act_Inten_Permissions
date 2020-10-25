package com.pucmm.tarea2_act_inten_permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import java.util.stream.Collectors;
import androidx.core.app.ActivityCompat;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;



import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_CAMARA_CODE = 202;
    private static final int PERMISSION_REQUEST_CONTACTS_CODE = 204;
    private static final int PERMISSION_REQUEST_STORAGE_CODE = 205;

    private Button btn_continue, btn_cancel;


    private Switch swStorage,
            swCamera,
            swPhone,
            swLocation,
            swContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_continue = findViewById(R.id.btnContinue);
        btn_cancel = findViewById(R.id.btnCancel);
        swStorage = findViewById(R.id.swStorage);
        swCamera = findViewById(R.id.swCamera);
        swPhone = findViewById(R.id.swPhone);
        swLocation = findViewById(R.id.swLocation);
        swContacts = findViewById(R.id.swContacts);
        swStorage.setOnCheckedChangeListener(this);
        swCamera.setOnCheckedChangeListener(this);
        swPhone.setOnCheckedChangeListener(this);
        swLocation.setOnCheckedChangeListener(this);
        swContacts.setOnCheckedChangeListener(this);

        btn_continue.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final String [] access = getAccess();
                ActivityCompat.requestPermissions(MainActivity.this, access, PERMISSION_REQUEST_CODE);
            }
        });


        btn_cancel.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        checkSwitch();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String[] getAccess(){
        ArrayList<String> list = new ArrayList<>();
        list.add(swStorage.isChecked() ? READ_EXTERNAL_STORAGE : "");
        list.add(swLocation.isChecked() ? ACCESS_FINE_LOCATION : "");
        list.add(swCamera.isChecked() ? CAMERA : "");
        list.add(swPhone.isChecked() ? CALL_PHONE : "");
        list.add(swContacts.isChecked() ? READ_CONTACTS : "");

        list.stream().filter(f -> !f.isEmpty()).collect(Collectors.toList());

        return list.toArray(new String[list.size()]);
    }


    private void checkSwitch(){
        if (!swStorage.isChecked() && ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            swStorage.setChecked(true); }
        if (!swLocation.isChecked() && ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            swLocation.setChecked(true); }
        if (!swCamera.isChecked() && ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED){
            swCamera.setChecked(true); }
        if (!swPhone.isChecked() && ActivityCompat.checkSelfPermission(this, CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            swPhone.setChecked(true); }
        if (!swContacts.isChecked() && ActivityCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            swContacts.setChecked(true); }
    }


    @Override
    public void onCheckedChanged(CompoundButton v, boolean b) {
        Switch view;
        String access;

        switch(v.getId()){
            case R.id.swStorage:
                view = findViewById(R.id.swStorage);
                access = READ_EXTERNAL_STORAGE;
                break;
            case R.id.swLocation:
                view = findViewById(R.id.swLocation);
                access = ACCESS_FINE_LOCATION;
                break;
            case R.id.swCamera:
                view = findViewById(R.id.swCamera);
                access = CAMERA;
                break;
            case R.id.swPhone:
                view = findViewById(R.id.swPhone);
                access = CALL_PHONE;
                break;
            case R.id.swContacts:
                view = findViewById(R.id.swContacts);
                access = READ_CONTACTS;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        if (!view.isChecked() && ActivityCompat.checkSelfPermission(this, access) == PackageManager.PERMISSION_GRANTED){
            view.setChecked(true);
            Snackbar.make(view, "Permiso concedido.", Snackbar.LENGTH_LONG).show();
        }

    }

}