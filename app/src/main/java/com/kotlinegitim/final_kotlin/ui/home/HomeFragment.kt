package com.kotlinegitim.final_kotlin.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.kotlinegitim.final_kotlin.*
import com.kotlinegitim.final_kotlin.R
import com.kotlinegitim.final_kotlin.customadaptors.DrawsListCustomAdaptor
import com.kotlinegitim.final_kotlin.databinding.FragmentHomeBinding
import com.kotlinegitim.final_kotlin.services.DrawDetaiLoader
import com.kotlinegitim.final_kotlin.services.DrawLoader
import com.kotlinegitim.final_kotlin.services.FirebaseService
import com.kotlinegitim.final_kotlin.ui.Favorites.Favorites
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    var listDraw= mutableListOf<DrawObj>()
    var listDraw2= mutableListOf<DrawObj>()
    var details= mutableListOf<DrawDetail>()
    lateinit var drawListview : ListView
    private lateinit var database: DatabaseReference
    lateinit var sharedPreferences : SharedPreferences

    var currentDate : String = ""

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val root = inflater.inflate(R.layout.fragment_home,null,true)
        drawListview = root.findViewById(R.id.listDraw)



        database = FirebaseDatabase.getInstance("https://draw-app-60d8e-default-rtdb.firebaseio.com/").reference


        /*FirebaseService.listDraw2.clear()
        FirebaseService().GetValues("Draws")

        Handler().postDelayed({
            println("bu mu:" + FirebaseService.listDraw2)

            val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout,FirebaseService.listDraw2 )
            drawListview.adapter = adapter
            adapter.notifyDataSetChanged()
        }, 1500)*/


        //Bu şekilde daha iyi performans yakaladım, diğer türlü 1.5 sn bekliyorum
        val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout, listDraw2 )

        database.child("Draws").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (Snapshot in dataSnapshot.children) {

                    listDraw2.add(Snapshot.getValue(DrawObj::class.java)!!)
                    drawListview.adapter = adapter
                    adapter.notifyDataSetChanged()

                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })





        return root
    }


    override fun onStart() {


        val seconds: Long = 1000
        val minutes = seconds * 60
        val hours = minutes * 60



        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        sharedPreferences = requireActivity().getSharedPreferences("LoginTime", MODE_PRIVATE)

        val timer = sharedPreferences.getString("time","")

        val date1: Date = sdf.parse(currentDate)

        println(timer)



        if (timer.isNullOrEmpty() == true){

            updateLogin()

            WriteAllValues()
        }


        else {


            val date2: Date = sdf.parse(timer)

            val different: Long = date1.getTime()-date2.getTime()

            val elapsedHours = different / hours


            if (abs(elapsedHours) >= 3){

                WriteAllValues()

                updateLogin()

                println("Güncelleneli 3 saat oldu ya da geçti")
            }


        }






        super.onStart()
    }

    fun updateLogin(){

        val myEdit = sharedPreferences.edit()
        myEdit.putString("time",currentDate)
        myEdit.apply()

    }

    fun WriteAllValues(){

        writeValues("https://www.kimkazandi.com/cekilisler","Draws")
        writeValues("https://www.kimkazandi.com/cekilisler/yeni-baslayanlar","Draws2")
        writeValues("https://www.kimkazandi.com/cekilisler/bedava-katilim","Draws3")
        writeValues("https://www.kimkazandi.com/cekilisler/araba-kazan","Draws4")
        writeValues("https://www.kimkazandi.com/cekilisler/telefon-tablet-kazan","Draws5")
        writeValues("https://www.kimkazandi.com/cekilisler/tatil-kazan","Draws6")

    }

    fun writeValues(url:String, key:String){

        val obj = DrawLoader()
        val obj2 = DrawDetaiLoader()

        Thread {
            listDraw.clear()
            listDraw = obj.Draws(url) as MutableList<DrawObj>
            database.child(key).setValue(listDraw)


             Thread {
                for (item in listDraw) {


                    details = obj2.DrawDetail(item.url.toString()) as MutableList<DrawDetail>
                    database.child("DetailDraws").child(item.title.toString()).setValue(details)

                    println(item.url)
                    details.clear()

                }
             }.start()



        }.start()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}



