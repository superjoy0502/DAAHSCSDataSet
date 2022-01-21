/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.io.File
import java.io.InputStream
import javax.swing.JFileChooser
import kotlin.system.exitProcess


class GUIListener(private val gui: GUI): ActionListener, ItemListener {

    override fun actionPerformed(e: ActionEvent?) {

        if (e != null) {

            when (e.actionCommand) {

                "open" -> {

                    gui.fileChooserResult = gui.fileChooser.showOpenDialog(gui.frame)
                    if (gui.fileChooserResult == JFileChooser.APPROVE_OPTION) {

                        val selectedFile: File = gui.fileChooser.selectedFile
//                        println("Selected file: ${selectedFile.absolutePath}")
                        gui.dataManager.loadDataSet(selectedFile)
//                        println(gui.dataManager.getSamplefromIntArrayList(gui.dataManager.confirmed))
//                        println(gui.dataManager.getSamplefromDoubleArrayList(gui.dataManager.caseFatalityRatio))
//                        gui.dataManager.displayData(selectedFile)

                    }

                }
                "exit" -> exitProcess(0)
                "analyze" -> {

                    println("Analyze")

                    if (!gui.dataManager.dataLoaded) {

                        // Update Average Text
                        gui.avConfirmed.text = "Load a Dataset!"
                        gui.avDeaths.text = "Load a Dataset!"
                        gui.avRecovered.text = "Load a Dataset!"
                        gui.avActive.text = "Load a Dataset!"
                        gui.avIncidentRate.text = "Load a Dataset!"
                        gui.avCaseFatality.text = "Load a Dataset!"

                        // Update Sample Text
                        gui.smConfirmed.text = "Load a Dataset!"
                        gui.smDeaths.text = "Load a Dataset!"
                        gui.smRecovered.text = "Load a Dataset!"
                        gui.smActive.text = "Load a Dataset!"
                        gui.smIncidentRate.text = "Load a Dataset!"
                        gui.smCaseFatality.text = "Load a Dataset!"

                    } else {

                        // Update Average Text
                        gui.avConfirmed.text =
                            gui.dataManager.getAverageIntArrayList(gui.dataManager.confirmed).toString()
                        gui.avDeaths.text =
                            gui.dataManager.getAverageIntArrayList(gui.dataManager.deaths).toString()
                        gui.avRecovered.text =
                            gui.dataManager.getAverageIntArrayList(gui.dataManager.recovered).toString()
                        gui.avActive.text =
                            gui.dataManager.getAverageIntArrayList(gui.dataManager.active).toString()
                        gui.avIncidentRate.text =
                            gui.dataManager.getAverageDoubleArrayList(gui.dataManager.incidentRate).toString() + " cases per 100,000"
                        gui.avCaseFatality.text =
                            gui.dataManager.getAverageDoubleArrayList(gui.dataManager.caseFatalityRatio).toString() + "%"

                        // Update Sample Text
                        gui.smConfirmed.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfIntSamples(gui.dataManager.confirmed)
                            )
                        gui.smDeaths.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfIntSamples(gui.dataManager.deaths)
                            )
                        gui.smRecovered.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfIntSamples(gui.dataManager.recovered)
                            )
                        gui.smActive.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfIntSamples(gui.dataManager.active)
                            )
                        gui.smIncidentRate.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfDoubleSamples(gui.dataManager.incidentRate)
                            )
                        gui.smCaseFatality.text =
                            gui.dataManager.switchStringArrayListToString(
                                gui.dataManager.getCountriesOfDoubleSamples(gui.dataManager.caseFatalityRatio)
                            )

                    }

                }
                "analyzeCountry" -> {

                    val i = gui.dataManager.getCountryIndex(gui.countrySearchBox.text)
                    if (i == -1) {

                        gui.cnConfirmed.text = "That country does not exist!"
                        gui.cnDeaths.text = ""
                        gui.cnRecovered.text = ""
                        gui.cnActive.text = ""
                        gui.cnIncidentRate.text = ""
                        gui.cnCaseFatality.text = ""

                    }
                    else {

                        gui.cnConfirmed.text = "Confirmed: ${gui.dataManager.confirmed[i]}"
                        gui.cnDeaths.text = "Deaths: ${gui.dataManager.deaths[i]}"
                        gui.cnRecovered.text = "Recovered: ${gui.dataManager.recovered[i]}"
                        gui.cnActive.text = "Active: ${gui.dataManager.active[i]}"
                        gui.cnIncidentRate.text = "Incident Rate: ${gui.dataManager.incidentRate[i]} cases per 100,000"
                        gui.cnCaseFatality.text = "Fatality Ratio: ${gui.dataManager.caseFatalityRatio[i]}%"

                    }

                }

            }

        }
    }

    override fun itemStateChanged(e: ItemEvent?) {
        TODO("Not yet implemented")
    }

}