package com.example.projekt7

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

private const val PICK_PHOTO_CODE = 1234

class SaveSpotActivity : AppCompatActivity() {
    private lateinit var imageSelected : ImageView
    private var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_spot)

        val btnSelect = findViewById<Button>(R.id.btnImageSelect)

         imageSelected = findViewById(R.id.imageSelected)

        btnSelect.setOnClickListener {
            Log.i("Select","Image opened")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if (imagePickerIntent.resolveActivity(packageManager) != null){
                startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                Log.i("Select","photoUri $photoUri")
                imageSelected.setImageURI(photoUri)
            } else {
                Toast.makeText(this,"Action canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}