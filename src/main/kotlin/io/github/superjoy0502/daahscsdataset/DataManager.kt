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
import javax.swing.JTable
import javax.swing.table.DefaultTableColumnModel
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableColumnModel
import kotlin.reflect.typeOf

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

    fun displayData(input: Any) {

        var inputStream: InputStream = InputStream.nullInputStream()

        if (input !is File && input !is InputStream) return
        if (input is File) inputStream = FileInputStream(input)
        else if (input is InputStream) inputStream = input

        loadDataSet(inputStream)

        val array: Array<Array<Any>> = data.toTypedArray()
        gui.table = JTable(array, gui.columnNames)

    }

    fun loadDataSet(inputStream: InputStream) {

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

}