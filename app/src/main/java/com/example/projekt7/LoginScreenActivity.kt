package com.example.projekt7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        if (DataManager.auth.currentUser != null){
            startActivity(Intent(this,ProfileScreenActivity::class.java))
        }

        textEmail = findViewById(R.id.editTextEmail)
        textPassword = findViewById(R.id.editTextPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        buttonLogin.setOnClickListener {
            var email = textEmail.text.toString()
            var password = textPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(applicationContext,"Enter an email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            DataManager.auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        startActivity(Intent(this,ProfileScreenActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this, "Email or password is invalid.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }

        buttonCreate.setOnClickListener {
            startActivity(Intent(this,CreateRUActivity::class.java))
        }

    }
}