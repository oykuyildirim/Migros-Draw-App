package com.kotlinegitim.final_kotlin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class DetailPage : AppCompatActivity() {

    var listDetail= mutableListOf<DrawDetail>()
    private lateinit var database: DatabaseReference

    lateinit var scrollTxt: TextView
    lateinit var scrollImportant: TextView
    lateinit var scrollImportant1: TextView
    lateinit var scrollImportant2: TextView
    lateinit var scrollImportant3: TextView
    lateinit var scrollImportant4: TextView
    lateinit var scrollImportant5: TextView
    lateinit var scrollImportant6: TextView
    lateinit var scrollImportant7: TextView
    lateinit var scrollImage: ImageView

    lateinit var abstract : TextView
    lateinit var abstract2 : TextView
    lateinit var abstract3 :TextView


    lateinit var favoriteBtn : Button


    lateinit var drawDetail: DrawDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_page)


        scrollTxt = findViewById(R.id.fullText)
        scrollImage = findViewById(R.id.imageDraw)
        scrollImportant = findViewById(R.id.titleImp)
        scrollImportant1 = findViewById(R.id.beginTime)
        scrollImportant2 = findViewById(R.id.lastTime)
        scrollImportant3 = findViewById(R.id.drawTime)
        scrollImportant4 = findViewById(R.id.announce)
        scrollImportant5 = findViewById(R.id.min)
        scrollImportant6 = findViewById(R.id.total)
        scrollImportant7 = findViewById(R.id.totalsuprise)

        abstract = findViewById(R.id.abstractText)
        abstract2 = findViewById(R.id.abstractText2)
        abstract3 = findViewById(R.id.abstactText3)

        favoriteBtn = findViewById(R.id.favorite)


        val title = intent.getStringExtra("title")

        checkFavorite(title.toString())

        database = FirebaseDatabase.getInstance("https://draw-app-60d8e-default-rtdb.firebaseio.com/").reference

        database.child("DetailDraws").child(title.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (Snapshot in dataSnapshot.children) {

                    drawDetail = Snapshot.getValue(DrawDetail::class.java)!!


                    putDetails(drawDetail!!)
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        favoriteBtn.setOnClickListener{

            FavoriteControl(title.toString())

        }


    }

    fun putDetails (drawDetail : DrawDetail){


        Glide.with(this@DetailPage).load(drawDetail.imageLink).into(scrollImage)

        scrollTxt.text = drawDetail.fulldrawdetail!!.get(0)

        var drawImportant = drawDetail!!.details!!
        scrollImportant.text = drawImportant.get(0)
        scrollImportant1.text = drawImportant.get(1)
        scrollImportant2.text = drawImportant.get(2)
        scrollImportant3.text = drawImportant.get(3)
        scrollImportant4.text = drawImportant.get(4)
        scrollImportant5.text = drawImportant.get(5)
        scrollImportant6.text = drawImportant.get(6)
        scrollImportant7.text = drawImportant.get(7)


        var drawAbstract = drawDetail.detailmore

        abstract.text = drawAbstract!!.get(0)
        abstract2.text = drawAbstract!!.get(1)
        abstract3.text = drawAbstract!!.get(2)


    }

    fun FavoriteControl(title : String){

        val sh = getSharedPreferences("Favorites", MODE_PRIVATE)
        val sh2 = getSharedPreferences("FavoritesImages", MODE_PRIVATE)
        val sh3= getSharedPreferences("FavoritesSuprise", MODE_PRIVATE)
        val sh32= getSharedPreferences("FavoritesSuprise2", MODE_PRIVATE)
        val sh33= getSharedPreferences("FavoritesSuprise3", MODE_PRIVATE)
        val sh4= getSharedPreferences("FavoritesURL", MODE_PRIVATE)
        val IsFavorite = sh.getString(title, "false")



        if (IsFavorite == "false"){
            val myEdit = sh.edit()
            myEdit.putString(title,"true")
            myEdit.commit()
            myEdit.apply()

            val myEdit2 = sh2.edit()
            myEdit2.putString(title+"Image",drawDetail.imageLink)
            myEdit2.apply()


            var suprise = intent.getStringExtra("drawMoney")
            var suprise1 = intent.getStringExtra("drawMoney2")
            var suprise2 = intent.getStringExtra("drawMoney3")

            val myEdit3 = sh3.edit()
            myEdit3.putString(title+"Draw", suprise)
            myEdit3.commit()
            myEdit3.apply()

            val myEdit32 = sh32.edit()
            myEdit32.putString(title+"Draw", suprise1)
            myEdit32.commit()
            myEdit32.apply()

            val myEdit33 = sh33.edit()
            myEdit33.putString(title+"Draw", suprise2)
            myEdit33.commit()
            myEdit33.apply()

            var url = intent.getStringExtra("url")
            val myEdit4 = sh4.edit()
            myEdit4.putString(title+"Draw", url )
            myEdit4.commit()
            myEdit4.apply()


            favoriteBtn.setBackgroundResource(R.drawable.yes)

        }

        else{

            val myEdit = sh.edit()
            myEdit.remove(title)
            myEdit.commit()
            myEdit.apply()

            val myEdit2 = sh2.edit()
            myEdit2.remove(title+"Images")
            myEdit2.commit()
            myEdit2.apply()

            val myEdit3 = sh3.edit()
            myEdit3.remove(title+"Draw")
            myEdit3.commit()
            myEdit3.apply()

            val myEdit32 = sh32.edit()
            myEdit32.remove(title+"Draw")
            myEdit32.commit()
            myEdit32.apply()

            val myEdit33 = sh33.edit()
            myEdit33.remove(title+"Draw")
            myEdit33.commit()
            myEdit33.apply()

            val myEdit4 = sh4.edit()
            myEdit4.remove(title+"Draw")
            myEdit4.commit()
            myEdit4.apply()

            favoriteBtn.setBackgroundResource(R.drawable.no)



        }


    }

    fun checkFavorite(title : String){

        val sh = getSharedPreferences("Favorites", MODE_PRIVATE)
        val IsFavorite = sh.getString(title, "false")

        if (IsFavorite == "true"){

            favoriteBtn.setBackgroundResource(R.drawable.yes)

        }

        else{

            favoriteBtn.setBackgroundResource(R.drawable.no)

        }


    }


    override fun onBackPressed() {

        var intent = Intent (this , MainActivity::class.java )

        startActivity(intent)
        super.onBackPressed()
    }
}