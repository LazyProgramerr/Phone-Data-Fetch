package com.sai.phonedatafetch.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mobiles")
data class PhoneModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mBrand: String,
    val mName:String,
    val mWeight:String,
    val mRam:String,
    val fCamera :String,
    val bCamera: String,
    val mProcessor: String,
    val bCapacity : String,
    val sSize :String,
    val mPrice : String,
    val mYear: Int,
    )
