package com.example.projekt7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginScreenActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var textEmail : EditText
    lateinit var textPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        textEmail = findViewById(R.id.editTextEmail)
        textPassword = findViewById(R.id.editTextPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        buttonLogin.setOnClickListener {
            loginUser()

            }

        buttonCreate.setOnClickListener {
            val intent = Intent(this,ChooseUserActivity::class.java)
            startActivity(intent)
        }

    }

    fun loginUser() {
        var email = textEmail.text.toString()
        var password = textPassword.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(applicationContext,"Enter an email and password", Toast.LENGTH_SHORT).show()
            return
        }
        DataManager.auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    gotoActivity()
                } else {
                    Toast.makeText(
                        this, "Email or password is invalid. Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun gotoActivity() {
            val userProfile = Intent(this, ProfileScreenActivity::class.java)
            startActivity(userProfile)
        }
}