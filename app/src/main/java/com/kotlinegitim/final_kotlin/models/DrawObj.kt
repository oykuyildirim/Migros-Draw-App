package com.kotlinegitim.final_kotlin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class DrawObjList(val drawObjects: List<DrawObj>?= null)

data class DrawObj(val title:String? = "",
                   val img:String? ="",
                   val url : String? ="",
                   val details1 : String?="",
                   val details2 : String?="",
                   val details3 : String?="")

data class DrawDetailid(val drawTitle: List<String>?= null,
                       val drawDetail: List<String>?= null)

data class DrawDetail(val detailmore : List<String>? = null,
                      val details: List<String>? = null,
                      val fulldrawdetail: List<String>? = null,
                      val imageLink : String? = null

)
