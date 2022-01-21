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
    var dataLoaded = false
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

        dataLoaded = true

    }

    fun getSamplefromIntArrayList(arrayList: ArrayList<Int?>, n: Int = 5, bottom: Boolean = false): ArrayList<Int> {

        var counter = n

        val tempList: ArrayList<Int?> = sortIntArrayList(arrayList)
//        println("size: ${tempList.size}")
        if (bottom) tempList.reverse()
        val topFiveList: ArrayList<Int> = ArrayList()
        for (i in 0 until counter) {
            if (counter == tempList.size) {
                return arrayListOf(-1)
            }
//            println("count: $i")
            if (tempList[i] != null) {
                topFiveList.add(tempList[i]!!)
            }
            else counter++
        }

//        println(topFiveList)
        return topFiveList

    }

    fun getCountriesOfIntSamples(arrayList: ArrayList<Int?>): ArrayList<String> {

//        println("cc $arrayList")

        val countriesList = ArrayList<String>()
        val sampleList = getSamplefromIntArrayList(arrayList)

        for (s in sampleList) {

            val i = getIntegerIndex(arrayList, s)
            if (i == -1) continue
//            println(i)
            if (i < countryRegion.size) countryRegion[i]?.let { countriesList.add(it) }

        }

        if (countriesList == ArrayList<String>()) {
            countriesList.add("A problem has occurred in the Dataset")
        }

        return countriesList

    }

    fun sortIntArrayList(arrayList: ArrayList<Int?>): ArrayList<Int?> {

        val finalList: ArrayList<Int?> = ArrayList()
        finalList.addAll(arrayList)
//        println("$finalList")

        for (j in finalList.size - 1 downTo 1) {

            for (i in 0 until j) {

                if (finalList[i] != null && finalList[i + 1] != null){

                    if (finalList[i]!! > finalList[i + 1]!!) {

                        val t = finalList[i]
                        finalList[i] = finalList[i + 1]
                        finalList[i + 1] = t

                    }

                }

            }

        }

//        println(finalList)
        finalList.reverse()
        return finalList

    }

    fun getAverageIntArrayList(arrayList: ArrayList<Int?>): Double {

        var total = 0.0
        var size = arrayList.size
        for (i in arrayList) {

            if (i != null) {
                total += i
            } else {
                size--
            }

        }

        return (total / size)

    }

    fun getSamplefromDoubleArrayList(arrayList: ArrayList<Double?>, n: Int = 5, bottom: Boolean = false): ArrayList<Double> {

        var counter = n

        val tempList: ArrayList<Double?> = sortDoubleArrayList(arrayList)
//        println("size: ${tempList.size}")
        if (bottom) tempList.reverse()
        val topFiveList: ArrayList<Double> = ArrayList()
        for (i in 0 until counter) {
            if (counter == tempList.size) {
                return arrayListOf(-1.0)
            }
//            println("count: $i")
            if (tempList[i] != null) {
                topFiveList.add(tempList[i]!!)
            }
            else counter++
        }

//        println(topFiveList)
        return topFiveList

    }

    fun getCountriesOfDoubleSamples(arrayList: ArrayList<Double?>): ArrayList<String> {

//        println("cc $arrayList")

        val countriesList = ArrayList<String>()
        val sampleList = getSamplefromDoubleArrayList(arrayList)

        for (s in sampleList) {

            val i = getDoubleIndex(arrayList, s)
            if (i == -1) continue
//            println(i)
            if (i < countryRegion.size) countryRegion[i]?.let { countriesList.add(it) }

        }

        if (countriesList == ArrayList<String>()) {
            countriesList.add("A problem has occurred in the Dataset")
        }

        return countriesList

    }

    fun sortDoubleArrayList(arrayList: ArrayList<Double?>): ArrayList<Double?> {

        val finalList: ArrayList<Double?> = ArrayList()
        finalList.addAll(arrayList)
//        println("$finalList")

        for (j in finalList.size - 1 downTo 1) {

            for (i in 0 until j) {

                if (finalList[i] != null && finalList[i + 1] != null){

                    if (finalList[i]!! > finalList[i + 1]!!) {

                        val t = finalList[i]
                        finalList[i] = finalList[i + 1]
                        finalList[i + 1] = t

                    }

                }

            }

        }

//        println(finalList)
        finalList.reverse()
        return finalList

    }

    fun getAverageDoubleArrayList(arrayList: ArrayList<Double?>): Double {

        var total = 0.0
        var size = arrayList.size
        for (i in arrayList) {

            if (i != null) {
                total += i
            } else {
                size--
            }

        }

        return (total / size)

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

    fun getIntegerIndex(arrayList: ArrayList<Int?>, number: Int): Int {

        var index = -1

        for (i in 0 until arrayList.size) {

            if (number == arrayList[i]){

                index = i
                break

            }

        }
//        println(number)
//        println(arrayList)

        return index

    }

    fun getDoubleIndex(arrayList: ArrayList<Double?>, number: Double): Int {

        var index = -1

        for (i in 0 until arrayList.size) {

            if (number == arrayList[i]){

                index = i
                break

            }

        }

        return index

    }

    fun switchStringArrayListToString(arrayList: ArrayList<String>): String {

        var str = ""

        for (e in arrayList) {

            str += if (e == arrayList[arrayList.size - 1]) "$e"
            else "${e}, "

        }

        return str

    }

}