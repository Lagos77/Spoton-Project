package com.example.projekt7

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.projekt7.databinding.ActivityRumapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RUMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityRumapsBinding

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRumapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Home Location if I dont want to add user current location

        val latitude = 59.3293
        val longitude = 18.0686
        val zoomLevel = 15f

        val homeLatLng = LatLng(latitude, longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        mMap.addMarker(MarkerOptions().position(homeLatLng))


        // functions
        setMapLongClick(mMap)
        setPoiClick(mMap)
        enableMyLocation()


    }

    private fun setMapLongClick(map:GoogleMap) {
        map.setOnMapLongClickListener {
            val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long:%2$.5f",
                    it.latitude,
                    it.longitude
            )
            map.addMarker(
                    MarkerOptions()
                            .position(it)
                            .title("Insert this place")
                            .snippet("Click here")

            )

            val database = Firebase.database("https://spoton-8bebd-default-rtdb.europe-west1.firebasedatabase.app/")
            val reference = database.reference
            val data = reference.push().child("location").setValue(it)

        }



    }

    private fun setPoiClick(map:GoogleMap) {
        map.setOnPoiClickListener {
            val poiMarker = map.addMarker(
                    MarkerOptions()
                            .position(it.latLng)
                            .title(it.name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
            poiMarker.showInfoWindow()

            val database = Firebase.database("https://spoton-8bebd-default-rtdb.europe-west1.firebasedatabase.app/")
            val reference = database.reference
            val data = reference.push().child("location").setValue(it)
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
            if (requestCode == REQUEST_LOCATION_PERMISSION) {
                if (grantResults.contains(PackageManager.PERMISSION_GRANTED))
                    enableMyLocation()
            }

    }

}