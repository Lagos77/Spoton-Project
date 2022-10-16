package com.example.projekt7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt7.Model.Place
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*

 val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
private const val REQUEST_CODE = 1234

class ProfileScreenActivity : AppCompatActivity() {

    /*    private lateinit var mapAdapter: MapsAdapter
    private lateinit var rvMaps: RecyclerView
*/
    private lateinit var recyclerView: RecyclerView
    private lateinit var mapsList: ArrayList<Place>
    private lateinit var db: FirebaseFirestore
    private lateinit var profileAdapter: ProfileAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val fabCreateMap = findViewById<FloatingActionButton>(R.id.fabCreateButton)
        val fabSignOut = findViewById<FloatingActionButton>(R.id.fabSignOut)
        recyclerView = findViewById(R.id.rvProfileScreen)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mapsList = arrayListOf()

        profileAdapter = ProfileAdapter(mapsList, object : ProfileAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ProfileScreenActivity, DisplayMapsActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, mapsList[position])
                startActivity(intent)
            }
        })
        recyclerView.adapter = profileAdapter

        spotsListener()



/*               mapsAdapter = MapsAdapter(this, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ProfileScreenActivity, DisplayMapsActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, mapsList[position])
                startActivity(intent)
            }
        })
*/



        fabCreateMap.setOnClickListener {
            startActivity(Intent(this, CreateMapActivity::class.java))
        }

        fabSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginScreenActivity::class.java))
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun spotsListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("places")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            mapsList.add(dc.document.toObject(Place::class.java))
                        }
                    }
                    profileAdapter.notifyDataSetChanged()
                }
            })
    }
}
