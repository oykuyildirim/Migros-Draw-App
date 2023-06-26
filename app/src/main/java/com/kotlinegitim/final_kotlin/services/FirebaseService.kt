package com.kotlinegitim.final_kotlin.services

import android.widget.ListView
import com.google.firebase.database.*
import com.kotlinegitim.final_kotlin.DrawDetail
import com.kotlinegitim.final_kotlin.DrawObj
import com.kotlinegitim.final_kotlin.R
import com.kotlinegitim.final_kotlin.customadaptors.DrawsListCustomAdaptor

class FirebaseService {

    private lateinit var database: DatabaseReference

    companion object {
        var listDraw2 = mutableListOf<DrawObj>()
    }

    fun GetValues (keyName:String){


        var listDraw = mutableListOf<DrawObj>()

        database = FirebaseDatabase.getInstance("https://draw-app-60d8e-default-rtdb.firebaseio.com/").reference


        database.child(keyName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (Snapshot in dataSnapshot.children) {

                    listDraw2.add(Snapshot.getValue(DrawObj::class.java)!!)


                }

               println( listDraw2)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }
}