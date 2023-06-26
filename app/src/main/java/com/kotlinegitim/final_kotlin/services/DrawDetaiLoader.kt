package com.kotlinegitim.final_kotlin.services

import com.kotlinegitim.final_kotlin.DrawDetail
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DrawDetaiLoader {

    lateinit var  doc: Document



    fun DrawDetail (url : String) : List<DrawDetail> {


        val arr = mutableListOf<DrawDetail>()
        val detailarr = mutableListOf<String>()


        doc = Jsoup.connect(url).timeout(15000).get()

        var elements: Elements = doc.getElementsByClass("kalanSure")

        for (item in elements) {


            val detail = item.getElementsByTag("h4").text()

            detailarr.add(detail)


        }

        val detailmore = CampaignDetail()

        val fulldetail = CampaignMoreDetail()


        val imageLink = ImageUrl()

        arr.add(DrawDetail(detailmore,detailarr,fulldetail,imageLink))

        return arr

    }

    fun CampaignDetail() : List<String>{

        val detailmorearr = mutableListOf<String>()

        var elements2 : Elements = doc.getElementsByClass("scrollbar-dynamic")


        for (item in elements2){

            val detay = item.getElementsByTag("p").text()

            detailmorearr.add(detay)



        }

       return detailmorearr

    }



    fun ImageUrl() : String{

        var image_link =""
        var elements2 : Elements = doc.getElementsByClass("container-fluid")


        for (item in elements2){

            val image = item.getElementsByTag("img")

            image_link = "https://www.kimkazandi.com/"+image.attr("src")


        }

        return image_link
    }


    fun CampaignMoreDetail():List<String>{

        val fulldetailtext = mutableListOf<String>()
        var elements2 : Elements = doc.getElementById("home")!!.getElementsByClass("scrollbar-dynamic")
        fulldetailtext.add(elements2.text())

        return fulldetailtext


    }


}