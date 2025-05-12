package com.sai.phonedatafetch

import android.content.Context
import com.opencsv.CSVReader
import com.sai.phonedatafetch.database.PhoneModel
import java.io.InputStreamReader

fun readCSV(context: Context): List<PhoneModel> {
    val phones = mutableListOf<PhoneModel>()

    // Open the CSV file from assets
    val inputStream = context.assets.open("mobiles_dataset.csv")
    val reader = CSVReader(InputStreamReader(inputStream))

    // Skip the header row
    reader.readNext()

    // Read each line in the CSV
    var line: Array<String>?
    while (reader.readNext().also { line = it } != null) {
        // Assuming the CSV columns are in the following order:
        // Brand, Name, Weight, RAM, Front Camera, Back Camera, Processor, Battery Capacity, Screen Size, Price, Year
        val brand = line!![0]
        val name = line!![1]
        val weight = line!![2]
        val ram = line!![3]
        val frontCamera = line!![4]
        val backCamera = line!![5]
        val processor = line!![6]
        val batteryCapacity = line!![7]
        val screenSize = line!![8]
        val price = line!![9]
        val year = line!![10].toInt()

        phones.add(
            PhoneModel(
                mBrand = brand,
                mName = name,
                mWeight = weight,
                mRam = ram,
                fCamera = frontCamera,
                bCamera = backCamera,
                mProcessor = processor,
                bCapacity = batteryCapacity,
                sSize = screenSize,
                mPrice = price,
                mYear = year,
            )
        )
    }

    reader.close()

    return phones
}
