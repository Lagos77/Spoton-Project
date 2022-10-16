package com.example.projekt7

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt7.DataManager.db
import com.example.projekt7.Model.Place

data class ProfileAdapter(var spotMaps: ArrayList<Place>,val c: Context, val onClickListener: OnClickListener) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.usermaps_saved,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val maps : Place = spotMaps[position]
        holder.titles.text = maps.title
        holder.descriptions.text = maps.description

        holder.itemView.setOnClickListener{
            onClickListener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return spotMaps.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var titles : TextView = itemView.findViewById(R.id.umTitleLocation)
        var descriptions : TextView = itemView.findViewById(R.id.umDescription)
        //var mMenus : ImageView
       // var itemPosition = 0
//       val longitude : TextView = itemView.findViewById(R.id.umLongitude)
//        val latitude : TextView = itemView.findViewById(R.id.umLatitude)

        init {
            titles = itemView.findViewById(R.id.umTitleLocation)
            descriptions = itemView.findViewById(R.id.umDescription)
           //mMenus = itemView.findViewById(R.id.mMenus)
           // mMenus.setOnClickListener { popupMenus(it) }
        }
    }
/*
    private fun popupMenus(itemView: View) {
        //val itemViewHolder = itemView as ViewHolder
        // Log.d("!!!", "${itemViewHolder.itemPosition} " )
        val position = spotMaps[0]        // VER BEM ISTO nao é zero
        val popupMenus = PopupMenu (c,itemView)   // arranjar esta funcao e ver o que é
        popupMenus.inflate(R.menu.show_menu)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.delete -> {
                    AlertDialog.Builder(c)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_warning)
                        .setMessage("Are you sure delete this place?")
                        .setPositiveButton("Yes") {
                                dialog,_->
                            spotMaps.removeAt(0) // O Erro está aqui
                            notifyDataSetChanged()
                            Toast.makeText(c,"Delete this place", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            deletePlace()  // funcao para apagar no firebase
                        }
                        .setNegativeButton("No") {
                                dialog,_->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                    true
                }

                else -> true
            }

        }

        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu,true)
    }

 */

   private fun deletePlace() {
        db.collection("places").document("Spot1")
            .delete()
            .addOnSuccessListener { Log.d(TAG, "PLace successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting place", e) }
    }


}