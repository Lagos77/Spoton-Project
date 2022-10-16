package com.example.projekt7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateRUActivity : AppCompatActivity() {
    lateinit var nameEditText: EditText
    lateinit var mailEditText: EditText
    lateinit var userNameEditText: EditText
    lateinit var passwordEditText: EditText
    val TAG =  "!!!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ruactivity)

        supportActionBar?.hide()

        val createUserButton = findViewById<Button>(R.id.buttonCreateUser)
        createUserButton.setOnClickListener(){
            nameEditText = findViewById(R.id.businessName)
            mailEditText = findViewById(R.id.editTextMail)
            userNameEditText = findViewById(R.id.editTextUserName)
            passwordEditText = findViewById(R.id.editTextPassword)

            var nameString = nameEditText.getText().toString()
            var mailString = mailEditText.getText().toString()
            var userNameString = userNameEditText.getText().toString()
            var passwordString = passwordEditText.getText().toString()

        val newRegularUser = RegularUser(
            nameString, mailString,
            userNameString, passwordString)
            DataManager.regularUserList.add(newRegularUser)

            //Nedan är lite check i LogCat på att alla uppgifter sparats
            var printNewUser = DataManager.regularUserList[DataManager.regularUserList.lastIndex]
            Log.d(TAG,"${DataManager.regularUserList.lastIndex}")
            Log.d(TAG,"New user name: ${printNewUser.name}")
            Log.d(TAG,"New user mail: ${printNewUser.mail}")
            Log.d(TAG,"New username: ${printNewUser.userName}")
            Log.d(TAG,"New user password: ${printNewUser.password}")


            DataManager.createRU(mailString,passwordString)

            if(mailString.isEmpty() || passwordString.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all the information to create an account", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
            Toast.makeText(applicationContext,"Account created !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginScreenActivity::class.java)
            startActivity(intent)

        }
    }


    }

