/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset

import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableModel


class GUI {

    var frame: JFrame
    var menuBar: JMenuBar = JMenuBar()
    var menu: JMenu = JMenu()
    var menuItem: JMenuItem = JMenuItem()
    var fileChooser = JFileChooser()
    var label: JLabel = JLabel()
    var confirmedBox: JCheckBox = JCheckBox()
    var deathsBox: JCheckBox = JCheckBox()
    var recoveredBox: JCheckBox = JCheckBox()
    var confirmedMinModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var deathsMinModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var recoveredMinModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var confirmedMaxModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var deathsMaxModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var recoveredMaxModel: SpinnerModel = SpinnerNumberModel(0, 0, 1000000000, 1)
    var confirmedSpinnerMin = JSpinner()
    var deathsSpinnerMin = JSpinner()
    var recoveredSpinnerMin = JSpinner()
    var confirmedSpinnerMax = JSpinner()
    var deathsSpinnerMax = JSpinner()
    var recoveredSpinnerMax = JSpinner()
    var filterPanel: JPanel = JPanel()
    var tablePanel: JScrollPane = JScrollPane()
    var table: JTable = JTable()
    val columnNames: Array<String> = arrayOf(
        "Province",
        "Country",
        "Last Updated",
        "Confirmed",
        "Deaths",
        "Recovered",
        "Active",
        "Incident Rate",
        "Fatality Ratio"
    )

    val listener: GUIListener = GUIListener(this)

    var fileChooserResult: Int = 0

    val dataManager = DataManager(this)

    init {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame = JFrame("DAAHS COVID-19 Dataset Analyzer")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        setupMenu()
        setupFilterPanel()

        table = JTable()
        tablePanel = JScrollPane(table)
        frame.add(tablePanel)

        frame.setSize(960, 540)
        frame.setLocationRelativeTo(null)

        frame.jMenuBar = menuBar
        frame.isVisible = true

    }

    private fun setupMenu() {

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
        menuItem.addActionListener(listener)
        menu.add(menuItem)
        menu.addSeparator()
        menuItem = JMenuItem("Exit")
        menuItem.mnemonic = KeyEvent.VK_E
        menuItem.actionCommand = "exit"
        menuItem.accessibleContext.accessibleDescription = "Exits Program"
        menuItem.addActionListener(listener)
        menu.add(menuItem)

        fileChooser = JFileChooser()
        fileChooser.currentDirectory = File(System.getProperty("user.home"))
        fileChooser.fileFilter = FileNameExtensionFilter("COVID-19 Dataset (*.csv)", "csv")

    }

    private fun setupFilterPanel() {

        filterPanel = JPanel(GridBagLayout())
        filterPanel.preferredSize = Dimension(192, 540)
        val constraints: GridBagConstraints = GridBagConstraints()
        constraints.anchor = GridBagConstraints.WEST
        constraints.insets = Insets(10, 10, 10, 10)
        //        p.layout = BoxLayout(p, BoxLayout.PAGE_AXIS)

        confirmedBox = JCheckBox("Confirmed")
        confirmedBox.mnemonic = KeyEvent.VK_C
        confirmedBox.isSelected = false
        confirmedBox.addItemListener(listener)
        constraints.gridx = 0
        constraints.gridy = 0
        filterPanel.add(confirmedBox, constraints)

        constraints.gridx = 1
        constraints.gridy = 0
        confirmedSpinnerMin = JSpinner(confirmedMinModel)
        confirmedSpinnerMin.isEnabled = false
        filterPanel.add(confirmedSpinnerMin, constraints)

        constraints.gridx = 2
        constraints.gridy = 0
        label = JLabel("~")
        filterPanel.add(label, constraints)

        constraints.gridx = 3
        constraints.gridy = 0
        confirmedSpinnerMax = JSpinner(confirmedMaxModel)
        confirmedSpinnerMax.isEnabled = false
        filterPanel.add(confirmedSpinnerMax, constraints)

        deathsBox = JCheckBox("Deaths")
        deathsBox.mnemonic = KeyEvent.VK_D
        deathsBox.isSelected = false
        deathsBox.addItemListener(listener)
        constraints.gridx = 0
        constraints.gridy = 1
        filterPanel.add(deathsBox, constraints)

        constraints.gridx = 1
        constraints.gridy = 1
        deathsSpinnerMin = JSpinner(deathsMinModel)
        deathsSpinnerMin.isEnabled = false
        filterPanel.add(deathsSpinnerMin, constraints)

        constraints.gridx = 2
        constraints.gridy = 1
        label = JLabel("~")
        filterPanel.add(label, constraints)

        constraints.gridx = 3
        constraints.gridy = 1
        deathsSpinnerMax = JSpinner(deathsMaxModel)
        deathsSpinnerMax.isEnabled = false
        filterPanel.add(deathsSpinnerMax, constraints)

        recoveredBox = JCheckBox("Recovered")
        recoveredBox.mnemonic = KeyEvent.VK_R
        recoveredBox.isSelected = false
        recoveredBox.addItemListener(listener)
        constraints.gridx = 0
        constraints.gridy = 2
        filterPanel.add(recoveredBox, constraints)

        constraints.gridx = 1
        constraints.gridy = 2
        recoveredSpinnerMin = JSpinner(recoveredMinModel)
        recoveredSpinnerMin.isEnabled = false
        filterPanel.add(recoveredSpinnerMin, constraints)

        constraints.gridx = 2
        constraints.gridy = 2
        label = JLabel("~")
        filterPanel.add(label, constraints)

        constraints.gridx = 3
        constraints.gridy = 2
        recoveredSpinnerMax = JSpinner(recoveredMaxModel)
        recoveredSpinnerMax.isEnabled = false
        filterPanel.add(recoveredSpinnerMax, constraints)

        filterPanel.border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Filters")

        frame.add(filterPanel)

    }

}