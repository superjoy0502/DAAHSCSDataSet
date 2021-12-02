/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.swing.JFileChooser
import kotlin.system.exitProcess


class GUIListener(private val gui: GUI): ActionListener {

    override fun actionPerformed(e: ActionEvent?) {

        if (e != null) {

            when (e.actionCommand) {

                "open" -> {

                    gui.fileChooserResult = gui.fileChooser.showOpenDialog(gui.f)
                    if (gui.fileChooserResult == JFileChooser.APPROVE_OPTION) {

                        val selectedFile: File = gui.fileChooser.selectedFile
                        println("Selected file: ${selectedFile.absolutePath}")

                        gui.dataManager.printData(selectedFile)

                    }

                }
                "exit" -> exitProcess(0)

            }

        }
    }

}