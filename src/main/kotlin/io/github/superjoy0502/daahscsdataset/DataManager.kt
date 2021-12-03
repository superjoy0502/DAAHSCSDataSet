/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

/* https://github.com/CSSEGISandData/COVID-19 */
class DataManager {

    val provinceState: ArrayList<String?> = ArrayList()
    val countryRegion: ArrayList<String?> = ArrayList()
    val lastUpdate: ArrayList<String?> = ArrayList()
    val confirmed: ArrayList<Int?> = ArrayList()
    val deaths: ArrayList<Int?> = ArrayList()
    val recovered: ArrayList<Int?> = ArrayList()
    val active: ArrayList<Int?> = ArrayList()
    val incidentRate: ArrayList<Double?> = ArrayList()
    val caseFatalityRatio: ArrayList<Double?> = ArrayList()

    var fileInputStream: InputStream? = null

    /* https://mkyong.com/java/java-read-a-file-from-resources-folder/ */
    fun getDataFromResource(fileName: String): InputStream {

        fileInputStream = javaClass.classLoader.getResourceAsStream("csv/$fileName")

        return fileInputStream
            ?: throw FileNotFoundException("$fileName is not found!")

    }

    /* https://stackoverflow.com/questions/44061143/read-csv-line-by-line-in-kotlin */
    fun printData(inputStream: InputStream) {

        loadDataSet(inputStream)

        println(provinceState)
        println(countryRegion)
        println(lastUpdate)
        println(confirmed)
        println(deaths)
        println(recovered)
        println(active)
        println(incidentRate)
        println(caseFatalityRatio)

    }

    fun printData(file: File) {

        val inputStream: InputStream = FileInputStream(file)

        loadDataSet(inputStream)

        println(provinceState)
        println(countryRegion)
        println(lastUpdate)
        println(confirmed)
        println(deaths)
        println(recovered)
        println(active)
        println(incidentRate)
        println(caseFatalityRatio)

    }

    fun loadDataSet(inputStream: InputStream) {

        csvReader().open(inputStream) {

            for (row in readAllAsSequence()) {

                provinceState += row[2]
                countryRegion += row[3]
                lastUpdate += row[4]
                confirmed += row[7].toIntOrNull()
                deaths += row[8].toIntOrNull()
                recovered += row[9].toIntOrNull()
                active += row[10].toIntOrNull()
                incidentRate += row[12].toDoubleOrNull()
                caseFatalityRatio += row[13].toDoubleOrNull()

            }

        }

        provinceState.removeFirstOrNull()
        countryRegion.removeFirstOrNull()
        lastUpdate.removeFirstOrNull()
        confirmed.removeFirstOrNull()
        deaths.removeFirstOrNull()
        recovered.removeFirstOrNull()
        active.removeFirstOrNull()
        incidentRate.removeFirstOrNull()
        caseFatalityRatio.removeFirstOrNull()

    }

}