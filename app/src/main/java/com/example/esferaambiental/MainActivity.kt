package com.example.esferaambiental

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startDataCollectionButton: MaterialButton = findViewById(R.id.startDataCollectionButton)

        startDataCollectionButton.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(
                this@MainActivity,
                FormActivity::class.java
            )
            ContextCompat.startActivity(intent)
        })
    }
}