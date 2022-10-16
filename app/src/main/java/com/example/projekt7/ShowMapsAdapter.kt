package com.example.projekt7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.projekt7.Model.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class ShowMapsAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)

    override fun getInfoWindow(marker: Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.snippet_dialog, null)

        val titleView = infoWindow.findViewById<TextView>(R.id.snippTitle)
        val descriptView = infoWindow.findViewById<TextView>(R.id.snippDescription)

        val place = marker.tag as Place

        titleView.text = place.title
        descriptView.text = place.description

        return infoWindow
    }

    override fun getInfoContents(p0: Marker): View? {
        return null
    }


}