package com.sai.phonedatafetch.database

import android.content.Context
import com.sai.phonedatafetch.MOBILE_BRAND
import com.sai.phonedatafetch.MOBILE_NAME

class repository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val phoneDao = db.phoneDao()

    suspend fun addMobiles( mobiles:List<PhoneModel>){
        if (mobiles.size > 100){
            mobiles.chunked(100).forEach { chunk ->
                phoneDao.insertMobiles(chunk)
            }
        }else{
            phoneDao.insertMobiles(mobiles)
        }
    }

    suspend fun getMobiles(searchType:String,name: String,year:Int): List<PhoneModel>{
        when (searchType){
            MOBILE_BRAND ->{
                return phoneDao.getMobilesByBrand(name,year)
            }
            MOBILE_NAME ->{
                return phoneDao.getMobilesByName(name,year)
            }
        }
        return emptyList()
    }

    suspend fun getMobileBrands() : List<String>{
        return phoneDao.getMobileBrands()
    }
}