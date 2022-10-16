package com.example.projekt7

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.projekt7.Model.Place
import com.example.projekt7.databinding.ActivityUploadImageBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "Display"
class UploadImageActivity : AppCompatActivity() {

      lateinit var binding : ActivityUploadImageBinding
      lateinit var mMap: GoogleMap
      lateinit var ImageUri : Uri
      private var markers : MutableList<Marker> = mutableListOf()
      lateinit var latLng : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

       binding.btnImageSelect.setOnClickListener {
            selectImage()

        }
        binding.btnUploadImage.setOnClickListener {
            uploadImage()
            savePlace()
            Log.i(TAG, "Functions work!")

        }

    }

    private fun savePlace() {
        val title = findViewById<EditText>(R.id.textTitle).text.toString()
        val description = findViewById<EditText>(R.id.textDescription).text.toString()
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this,"Place must have non-empty title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val marker = mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description))
            if (marker != null) {
                markers.add(marker)
                // uploadImage()
                val place = Place(title, description, latLng.latitude, latLng.longitude)
                DataManager.db.collection("places").add(place)
            }
        return
    }


    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")

        storageReference.putFile(ImageUri).addOnSuccessListener {
            binding.fbImage.setImageURI(null)
            Toast.makeText(this@UploadImageActivity, "Sucessfuly uploaded", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener{
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@UploadImageActivity, "Failed to upload", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK ) {

            ImageUri = data?.data!!
            binding.fbImage.setImageURI(ImageUri)

        }
    }
}