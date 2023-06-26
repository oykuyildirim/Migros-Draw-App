package com.kotlinegitim.final_kotlin.ui.freeparticipation

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.kotlinegitim.final_kotlin.DrawObj
import com.kotlinegitim.final_kotlin.customadaptors.DrawsListCustomAdaptor
import com.kotlinegitim.final_kotlin.R
import com.kotlinegitim.final_kotlin.databinding.FragmentFreeParticipationDrawBinding
import com.kotlinegitim.final_kotlin.databinding.FragmentTelephoneDrawBinding
import com.kotlinegitim.final_kotlin.services.FirebaseService

class FreeParticipationFragment : Fragment() {
    private var _binding: FragmentFreeParticipationDrawBinding? = null

    lateinit var drawListview : ListView

    var listDraw= mutableListOf<DrawObj>()
    var listDraw2= mutableListOf<DrawObj>()
    private lateinit var database: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    // private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        val root = inflater.inflate(R.layout.fragment_free_participation_draw,null,true)

        drawListview = root.findViewById(R.id.listDraw6)


        database = FirebaseDatabase.getInstance("https://draw-app-60d8e-default-rtdb.firebaseio.com/").reference



        /*FirebaseService.listDraw2.clear()
        FirebaseService().GetValues("Draws3")

        Handler().postDelayed({
            println("bu mu:" + FirebaseService.listDraw2)

            val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout,
                FirebaseService.listDraw2 )
            drawListview.adapter = adapter
            adapter.notifyDataSetChanged()
        }, 1500)*/

        val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout, listDraw2 )

        database.child("Draws3").addValueEventListener(object : ValueEventListener {
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}