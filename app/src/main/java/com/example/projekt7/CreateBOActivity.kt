package com.example.projekt7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CreateBOActivity : AppCompatActivity() {
    lateinit var nameEditText: EditText
    lateinit var businessEditText: EditText
    lateinit var streetEditText: EditText
    lateinit var zipEditText: EditText
    lateinit var phoneEditText: EditText
    lateinit var mailEditText: EditText
    lateinit var keyEditText: EditText
    lateinit var passwordEditText: EditText
    val TAG = "!!!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_boactivity)

        val createUserButton = findViewById<Button>(R.id.buttonCreateUser)
        createUserButton.setOnClickListener() {
            nameEditText = findViewById(R.id.editTextName)
            businessEditText = findViewById(R.id.editTextBusiness)
            streetEditText = findViewById(R.id.editTextStreet)
            zipEditText = findViewById(R.id.editTextZip)
            phoneEditText = findViewById(R.id.editTextPhone)
            mailEditText = findViewById(R.id.editTextMail)
            passwordEditText = findViewById(R.id.editTextPassword)
            keyEditText = findViewById(R.id.editTextIdentityKey)

            var nameString = nameEditText.getText().toString()
            var streetString = streetEditText.getText().toString()
            var businessString = businessEditText.getText().toString()
            var passwordString = passwordEditText.getText().toString()
            var zipString = zipEditText.getText().toString()
            var phoneString = phoneEditText.getText().toString().toInt()
            var mailString = mailEditText.getText().toString()


            val newBusinessOwner = BusinessOwner(
                nameString, businessString, streetString, zipString, phoneString, mailString,
                passwordString
            )
            DataManager.businessOwnerList.add(newBusinessOwner)

            //Nedan är lite check i LogCat på att alla uppgifter sparats
            var printNewUser =
                DataManager.businessOwnerList[DataManager.businessOwnerList.lastIndex]
            Log.d(TAG, "${DataManager.businessOwnerList.lastIndex}")
            Log.d(TAG, "New user name: ${printNewUser.name}")
            Log.d(TAG, "New user name: ${printNewUser.business}")
            Log.d(TAG, "New user name: ${printNewUser.adressStreet}")
            Log.d(TAG, "New user name: ${printNewUser.townZipCode}")
            Log.d(TAG, "New user name: ${printNewUser.phone}")
            Log.d(TAG, "New user mail: ${printNewUser.mail}")
            Log.d(TAG, "New user password: ${printNewUser.password}")
        }
    }
}