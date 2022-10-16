
package com.example.projekt7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projekt7.Model.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.projekt7.databinding.ActivityDisplayMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class DisplayMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayMapsBinding
    private lateinit var userMap: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as Place

        super.onCreate(savedInstanceState)
        binding = ActivityDisplayMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val bounceBuilder = LatLngBounds.Builder()
        if (userMap != null) {
            val latLng = LatLng(userMap.latitude , userMap.longitude)
            bounceBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(userMap.title).snippet(userMap.description))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounceBuilder.build(), 9999,9999,2))
    }
}