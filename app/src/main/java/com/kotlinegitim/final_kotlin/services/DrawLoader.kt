package com.kotlinegitim.final_kotlin.services

import com.kotlinegitim.final_kotlin.DrawObj
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DrawLoader {



    fun Draws (url : String) : List<DrawObj>{


        val arr = mutableListOf<DrawObj>()

        val drawInfo = mutableListOf<String>()

        val doc: Document = Jsoup.connect(url).timeout(15000).get()

        var elements: Elements = doc.getElementsByClass("col-sm-3 col-lg-3 item")

        for (item in elements) {


            val image = item.getElementsByTag("img")
            val title = image.attr("alt")
            val image_link = "https://www.kimkazandi.com/"+image.attr("src")
            val url = "https://www.kimkazandi.com/"+item.getElementsByTag("a").attr("href")
            val details = item.getElementsByTag("span").text()

            val details2 = item.getElementsByClass("date d-flex")

            for ( item in details2){

                drawInfo.add(item.text())

            }

            val details3 = item.getElementsByClass("date kosul_fiyat d-flex").text()



            if (image_link != "" && url != "" && details.toString() != "" && title !=""){
                val draw = DrawObj(title, image_link, url, drawInfo.get(0), drawInfo.get(1), details3)
                arr.add(draw)
            }

            drawInfo.clear()


        }



        return arr
    }


}