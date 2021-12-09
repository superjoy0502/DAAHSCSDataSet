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
                        println("Selected file: ${selectedFile.absolutePath}")

                        gui.dataManager.displayData(selectedFile)

                    }

                }
                "exit" -> exitProcess(0)

            }

        }
    }

    override fun itemStateChanged(e: ItemEvent?) {

        when (e?.itemSelectable) {

            gui.confirmedBox -> {

                gui.confirmedSpinnerMin.isEnabled = !gui.confirmedSpinnerMin.isEnabled
                gui.confirmedSpinnerMax.isEnabled = !gui.confirmedSpinnerMax.isEnabled
                gui.dataManager.isConfirmedFilterOn = !gui.dataManager.isConfirmedFilterOn

            }

            gui.deathsBox -> {

                gui.deathsSpinnerMin.isEnabled = !gui.deathsSpinnerMin.isEnabled
                gui.deathsSpinnerMax.isEnabled = !gui.deathsSpinnerMax.isEnabled
                gui.dataManager.isDeathsFilterOn = !gui.dataManager.isDeathsFilterOn

            }

            gui.recoveredBox -> {

                gui.recoveredSpinnerMin.isEnabled = !gui.recoveredSpinnerMin.isEnabled
                gui.recoveredSpinnerMax.isEnabled = !gui.recoveredSpinnerMax.isEnabled
                gui.dataManager.isRecoveredFilterOn = !gui.dataManager.isRecoveredFilterOn

            }

        }

    }

}