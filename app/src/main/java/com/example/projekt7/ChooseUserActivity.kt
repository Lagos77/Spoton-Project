package com.example.projekt7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.projekt7.DataManager.TAG
import com.google.android.gms.tasks.OnSuccessListener

class ChooseUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        supportActionBar?.hide()

        val businessOwner = findViewById<Button>(R.id.buttonBusinessOwner)
        val regularUser = findViewById<Button>(R.id.buttonRegularUser)

        businessOwner.setOnClickListener {
            val intent = Intent(this,CreateBOActivity::class.java)
            startActivity(intent)
        }

        regularUser.setOnClickListener {
            val ruCreate = Intent(this,CreateRUActivity::class.java)
            startActivity(ruCreate)
        }  
    }
}