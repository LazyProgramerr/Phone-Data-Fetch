package com.sai.phonedatafetch.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMobiles(mobiles: List<PhoneModel>)

    @Query("select * from Mobiles where lower(mBrand) like lower(:name) order by mYear desc")
    suspend fun getMobilesByBrand(name:String) : List<PhoneModel>

    @Query("select * from Mobiles where lower(mName) like lower(:name) order by mYear DESC")
    suspend fun getMobilesByName(name:String) : List<PhoneModel>

    @Query("select distinct lower(mBrand) from Mobiles order by mBrand asc")
    suspend fun getMobileBrands() : List<String>

    @Query("select distinct lower(mBrand) from Mobiles order by mBrand asc")
    fun getMobileBrandsLD() : LiveData<List<String>>     ///returns the list of unique mobile brands

    @Query("select distinct lower(mBrand) from Mobiles where lower(mBrand) like lower(:name)")
    suspend fun idBrand(name:String):List<String>
}