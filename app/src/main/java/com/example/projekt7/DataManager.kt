package com.example.projekt7

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DataManager {
    val TAG = "!!!"
    var auth = Firebase.auth
    val db = Firebase.firestore

    var regularUserList = mutableListOf<RegularUser>()
    var businessOwnerList = mutableListOf<BusinessOwner>()

    fun createRU(mailString:String, passwordString:String){

        if (mailString.isEmpty() || passwordString.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(mailString, passwordString)
            .addOnCompleteListener  { task ->
                if ( task.isSuccessful) {
                    Log.d(TAG, "createUser: Success")
                    auth.signInWithEmailAndPassword(mailString,passwordString)
                    addRegularData(regularUserList[regularUserList.lastIndex])
                    auth.signOut()
                } else {
                    Log.d(TAG, "createUser: user not created ${task.exception}")
                }
            }
    }

    fun createBO(mailString:String, passwordString:String){

        if (mailString.isEmpty() || passwordString.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(mailString, passwordString)
            .addOnCompleteListener  { task ->
                if ( task.isSuccessful) {
                    Log.d(TAG, "createUser: Success")
                    auth.signInWithEmailAndPassword(mailString,passwordString)
                    addBusinessData(businessOwnerList[businessOwnerList.lastIndex])
                    auth.signOut()
                } else {
                    Log.d(TAG, "createUser: user not created ${task.exception}")
                }
            }
    }

    fun addBusinessData(businessOwner: BusinessOwner){
        val user = auth.currentUser
        if (user == null)
            return

        db.collection("users").document(user.uid)
            .collection("data").add(businessOwner)
            .addOnCompleteListener {
                Log.d("!!!", "saveItem: add: ${it.exception}")
            }

    }
    fun addRegularData(regularUser:RegularUser){
        val user = auth.currentUser
        if (user == null)
            return

        db.collection("users").document(user.uid)
            .collection("data").add(regularUser)
            .addOnCompleteListener {
                Log.d("!!!", "saveItem: add: ${it.exception}")
            }

    }
}
