package com.kotlinegitim.final_kotlin.ui.CarDraw

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kotlinegitim.final_kotlin.DrawObj
import com.kotlinegitim.final_kotlin.customadaptors.DrawsListCustomAdaptor
import com.kotlinegitim.final_kotlin.R
import com.kotlinegitim.final_kotlin.databinding.FragmentCardrawBinding
import com.kotlinegitim.final_kotlin.services.FirebaseService

class CarDrawFragment : Fragment() {

    private var _binding: FragmentCardrawBinding? = null
    private lateinit var database: DatabaseReference


    // This property is only valid between onCreateView and
    // onDestroyView.


    var listDraw= mutableListOf<DrawObj>()
    var listDraw2= mutableListOf<DrawObj>()
    lateinit var drawListview : ListView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root*/

        val root = inflater.inflate(R.layout.fragment_cardraw,null,true)

        drawListview = root.findViewById<ListView>(R.id.listDraw3)
        database = Firebase.database.reference
       /* val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        database = FirebaseDatabase.getInstance("https://draw-app-60d8e-default-rtdb.firebaseio.com/").reference


       /* FirebaseService.listDraw2.clear()
        FirebaseService().GetValues("Draws4")

        Handler().postDelayed({
            println("bu mu:" + FirebaseService.listDraw2)

            val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout,
                FirebaseService.listDraw2 )
            drawListview.adapter = adapter
            adapter.notifyDataSetChanged()
        }, 1500)*/

       val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout, listDraw2 )

        database.child("Draws4").addValueEventListener(object : ValueEventListener {
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