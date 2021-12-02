/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset

import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


class GUI {

    var f: JFrame
    var menuBar: JMenuBar
    var menu: JMenu
    var menuItem: JMenuItem
    val listener: GUIListener = GUIListener(this)
    val fileChooser = JFileChooser()
    var fileChooserResult: Int = 0

    val dataManager = DataManager()

    init {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        f = JFrame("DAAHS COVID-19 Dataset Analyzer")
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.setSize(960, 540)
        f.setLocationRelativeTo(null)

        /* https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html */
        menuBar = JMenuBar()

        menu = JMenu("File")
        menu.mnemonic = KeyEvent.VK_F
        menu.accessibleContext.accessibleDescription = "File Menu"
        menuBar.add(menu)
        menuItem = JMenuItem("Open")
        menuItem.mnemonic = KeyEvent.VK_O
        menuItem.actionCommand = "open"
        menuItem.accessibleContext.accessibleDescription = "Opens a CSV File"
        menuItem.addActionListener(listener);
        menu.add(menuItem)
        menuItem = JMenuItem("Exit")
        menuItem.mnemonic = KeyEvent.VK_E
        menuItem.actionCommand = "exit"
        menuItem.accessibleContext.accessibleDescription = "Exits Program"
        menuItem.addActionListener(listener);
        menu.add(menuItem)

        fileChooser.currentDirectory = File(System.getProperty("user.home"))
        fileChooser.fileFilter = FileNameExtensionFilter("COVID-19 Dataset (*.csv)", "csv")

        f.jMenuBar = menuBar
        f.isVisible = true

    }

}