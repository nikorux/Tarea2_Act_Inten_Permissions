package com.pucmm.tarea2_act_inten_permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btn_storage, btn_location, btn_camera, btn_phone, btn_contacts;

        btn_storage = findViewById(R.id.btnStorage);
        btn_location = findViewById(R.id.btnLocation);
        btn_camera = findViewById(R.id.btnCamera);
        btn_phone = findViewById(R.id.btnPhone);
        btn_contacts = findViewById(R.id.btnContacts);

        btn_storage.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_contacts.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String access;

        switch (view.getId()){
            case R.id.btnStorage:
                access = READ_EXTERNAL_STORAGE;
                break;
            case R.id.btnLocation:
                access = ACCESS_FINE_LOCATION;
                break;
            case R.id.btnCamera:
                access = CAMERA;
                break;
            case R.id.btnPhone:
                access = CALL_PHONE;
                break;
            case R.id.btnContacts:
                access = READ_CONTACTS;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        if(ActivityCompat.checkSelfPermission(this, access) == PackageManager.PERMISSION_GRANTED){
            Snackbar.make(view, "Permiso ya otorgado.", Snackbar.LENGTH_LONG)
                    .setAction("Open", (v) -> {
                        Intent intent;
                        switch (access){
                            case READ_EXTERNAL_STORAGE:
                                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("application/video");
                                startActivity(intent);
                                break;
                            case CAMERA:
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent);
                                break;
                            case CALL_PHONE:
                                String uri = "tel:8296857124";
                                intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                                break;
                            case READ_CONTACTS:
                                intent = new Intent(Intent.ACTION_VIEW);
                                Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, "");
                                intent.setData(contactUri);
                                startActivity(intent);
                                break;
                            case ACCESS_FINE_LOCATION:
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=santiago");
                                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                intent.setPackage("com.google.android.apps.maps");
                                startActivity(intent);
                                break;
                        }
                    }).show();

        } else {
            Snackbar.make(view, "Por favor solicite permiso.", Snackbar.LENGTH_LONG).show();
        }
    }
}
