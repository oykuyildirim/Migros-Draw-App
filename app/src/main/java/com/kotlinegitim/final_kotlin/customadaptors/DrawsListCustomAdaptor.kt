package com.kotlinegitim.final_kotlin.customadaptors

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kotlinegitim.final_kotlin.DetailPage
import com.kotlinegitim.final_kotlin.DrawObj
import com.kotlinegitim.final_kotlin.R

class DrawsListCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: MutableList<DrawObj>) :
    ArrayAdapter<DrawObj>(context, resource, objects) {

    lateinit var title_txt : TextView
    lateinit var detail_txt : TextView
    lateinit var detail_txt2 : TextView
    lateinit var detail_txt3 : TextView
    lateinit var image : ImageView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val root = context.layoutInflater.inflate(resource,null,false)

        title_txt = root.findViewById(R.id.title_custom)
        detail_txt = root.findViewById(R.id.detail_custom)
        detail_txt2 = root.findViewById(R.id.detail_custom2)
        detail_txt3 = root.findViewById(R.id.detail_custom3)
        image = root.findViewById(R.id.draw_image)

        val draws = objects.get(position)


        title_txt.text = draws.title
        detail_txt.text = draws.details1
        detail_txt2.text = draws.details2
        detail_txt3.text = draws.details3

        Glide.with(context).load(draws.img).into(image)

        root.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, DetailPage::class.java)


                intent.putExtra("url", draws.url)
                intent.putExtra("title", draws.title)
                intent.putExtra("drawMoney", draws.details1)
                intent.putExtra("drawMoney2", draws.details2)
                intent.putExtra("drawMoney3", draws.details3)
                intent.putExtra("img", draws.img)

                context.startActivity(intent)

            }
        })


        return root
    }


}