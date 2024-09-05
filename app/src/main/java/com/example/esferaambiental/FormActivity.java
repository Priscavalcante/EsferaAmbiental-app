package com.example.esferaambiental;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormActivity extends AppCompatActivity {

    private EditText dataInputField;
    private Button saveButton;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        dataInputField = findViewById(R.id.dataInputField);
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        saveButton.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        String data = dataInputField.getText().toString().trim();

        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Get last known location
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Save data to Firebase with location
                    String id = databaseReference.push().getKey();
                    EnvironmentalData environmentalData = new EnvironmentalData(data, latitude, longitude);
                    if (id != null) {
                        databaseReference.child(id).setValue(environmentalData).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(FormActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FormActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(FormActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
