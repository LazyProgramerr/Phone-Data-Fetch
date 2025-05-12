package com.sai.phonedatafetch.database

import androidx.room.*

@Dao
interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMobiles(mobiles: List<PhoneModel>)

    @Query("select * from Mobiles where lower(mBrand) = lower(:name) and mYear between (:year-4) and :year  order by mYear DESC")
    suspend fun getMobilesByBrand(name:String,year: Int) : List<PhoneModel>     //// returns the list of mobiles based on brands

    @Query("select * from Mobiles where lower(mName) like lower(:name) and mYear between (:year-4) and :year  order by mYear DESC")
    suspend fun getMobilesByName(name:String,year: Int) : List<PhoneModel>      //// returns the list of mobiles in 4 years range

    @Query("select distinct lower(mBrand) from Mobiles order by mBrand asc")
    suspend fun getMobileBrands() : List<String>     ///returns the list of unique mobile brands

}