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
class DataManager(val gui: GUI) {

    var data: ArrayList<Array<Any>> = ArrayList()

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

    var isConfirmedFilterOn = false
    var isDeathsFilterOn = false
    var isRecoveredFilterOn = false

    fun getDataFromResource(fileName: String): InputStream {

        /* https://mkyong.com/java/java-read-a-file-from-resources-folder/ */
        fileInputStream = javaClass.classLoader.getResourceAsStream("csv/$fileName")

        return fileInputStream
            ?: throw FileNotFoundException("$fileName is not found!")

    }

    fun printData(input: Any) {

        var inputStream: InputStream = InputStream.nullInputStream()

        if (input !is File && input !is InputStream) return
        if (input is File) inputStream = FileInputStream(input)
        else if (input is InputStream) inputStream = input

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

    fun loadDataSet(input: Any) {

        var inputStream: InputStream = InputStream.nullInputStream()

        if (input !is File && input !is InputStream) throw TypeCastException("loadDataSet(input: Any) only accepts InputStream and File as input!")
        if (input is File) inputStream = FileInputStream(input)
        else if (input is InputStream) inputStream = input

        csvReader().open(inputStream) {

            data = ArrayList()

            for (row in readAllAsSequence()) {

                data += row.toTypedArray()

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

    fun getSamplefromIntArrayList(arrayList: ArrayList<Int?>, n: Int = 5, bottom: Boolean = false): ArrayList<Int?> {

        val tempList: ArrayList<Int?> = sortIntArrayList(arrayList)
        if (!bottom) tempList.reverse()
        val topFiveList: ArrayList<Int?> = ArrayList()
        for (i in 0 until n) topFiveList.add(tempList[i])

        return topFiveList

    }

    fun sortIntArrayList(arrayList: ArrayList<Int?>): ArrayList<Int?> {

        val finalList: ArrayList<Int?> = arrayList
        println(finalList)

        for (j in finalList.size - 1 downTo 1) {

            for (i in 0 until j) {

                if (finalList[i]!! > finalList[i + 1]!!) {

                    val t = finalList[i]
                    finalList[i] = finalList[i + 1]
                    finalList[i + 1] = t

                }

            }

        }

        println(finalList)
        return finalList

    }

    fun getAverageIntArrayList(arrayList: ArrayList<Int?>): Double {

        var total = 0.0
        for (i in arrayList) {

            if (i != null) total += i

        }

        return (total / arrayList.size)

    }

    fun getSamplefromDoubleArrayList(arrayList: ArrayList<Double?>, n: Int = 5, bottom: Boolean = false): ArrayList<Double?> {

        val tempList: ArrayList<Double?> = sortDoubleArrayList(arrayList)
        if (!bottom) tempList.reverse()
        val topFiveList: ArrayList<Double?> = ArrayList()
        for (i in 0 until n) topFiveList.add(tempList[i])

        return topFiveList

    }

    fun sortDoubleArrayList(arrayList: ArrayList<Double?>): ArrayList<Double?> {

        val finalList: ArrayList<Double?> = arrayList
        println(finalList)

        for (j in finalList.size - 1 downTo 1) {

            for (i in 0 until j) {

                if (finalList[i] == null || finalList[i + 1] == null) continue

                if (finalList[i]!! > finalList[i + 1]!!) {

                    val t = finalList[i]
                    finalList[i] = finalList[i + 1]
                    finalList[i + 1] = t

                }

            }

        }

        println(finalList)
        return finalList

    }

    fun getAverageDoubleArrayList(arrayList: ArrayList<Double?>): Double {

        var total = 0.0
        for (i in arrayList) {

            if (i != null) total += i

        }

        return (total / arrayList.size)

    }

    fun getCountryIndex(country: String): Int {

        var index = -1

        for (i in 0 until countryRegion.size) {

            if (country.equals(countryRegion[i], true)){

                index = i
                break

            }

        }

        return index

    }

    fun <E> getDataHigherThan(arrayList: ArrayList<E?>, number: Double): ArrayList<E> {

        val list = ArrayList<E>()

        for (e in arrayList) {

            val ee = e.toString().toDoubleOrNull() ?: continue

            if (ee > number) {

                list += e!!

            }

        }

        return list

    }

}