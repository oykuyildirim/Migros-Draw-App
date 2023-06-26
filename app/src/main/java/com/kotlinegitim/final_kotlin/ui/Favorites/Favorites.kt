package com.kotlinegitim.final_kotlin.ui.Favorites

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.kotlinegitim.final_kotlin.DrawObj
import com.kotlinegitim.final_kotlin.MainActivity
import com.kotlinegitim.final_kotlin.R
import com.kotlinegitim.final_kotlin.customadaptors.DrawsListCustomAdaptor
import com.kotlinegitim.final_kotlin.databinding.FragmentTelephoneDrawBinding


class Favorites : Fragment() {
    private var _binding: FragmentTelephoneDrawBinding? = null

    lateinit var favoriteListview : ListView

    var listDraw= mutableListOf<DrawObj>()

    private lateinit var database: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    // private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        val root = inflater.inflate(R.layout.fragment_favorites_layout,null,true)

        favoriteListview = root.findViewById(R.id.favorites)



        val adapter = DrawsListCustomAdaptor(requireActivity(),R.layout.draw_custom_layout,listDraw)
        favoriteListview.adapter = adapter
        adapter.notifyDataSetChanged()



        return root
    }

    override fun onStart() {
        val sh = requireActivity().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        val sh2 = requireActivity().getSharedPreferences("FavoritesImages", MODE_PRIVATE)
        val sh3= requireActivity().getSharedPreferences("FavoritesSuprise", MODE_PRIVATE)
        val sh32= requireActivity().getSharedPreferences("FavoritesSuprise2", MODE_PRIVATE)
        val sh33= requireActivity().getSharedPreferences("FavoritesSuprise3", MODE_PRIVATE)
        val sh4= requireActivity().getSharedPreferences("FavoritesURL", MODE_PRIVATE)


        val sharedFavorites = sh.all.map { it.key }



        if (sharedFavorites.count() > 0) {
            for (i in 0..sharedFavorites.count()-1) {

                println(i)
                var obj = DrawObj(
                    sharedFavorites[i],
                    sh2.getString(sharedFavorites[i]+"Image", "") as String?,
                    sh4.getString(sharedFavorites[i]+"Draw", "").toString(),
                    sh3.getString(sharedFavorites[i]+"Draw", "").toString(),
                    sh32.getString(sharedFavorites[i]+"Draw", "").toString(),
                    sh33.getString(sharedFavorites[i]+"Draw", "").toString()
                )
                listDraw.add(obj)

            }
        }
        super.onStart()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}