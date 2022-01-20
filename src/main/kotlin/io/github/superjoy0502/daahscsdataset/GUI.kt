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
import java.security.Key
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableModel


class GUI {

    var frame: JFrame
    lateinit var menuBar: JMenuBar
    lateinit var menu: JMenu
    lateinit var menuItem: JMenuItem
    lateinit var fileChooser: JFileChooser
    lateinit var label: JLabel
    lateinit var avConfirmed: JLabel
    lateinit var avDeaths: JLabel
    lateinit var avRecovered: JLabel
    lateinit var avActive: JLabel
    lateinit var avIncidentRate: JLabel
    lateinit var avCaseFatality: JLabel
    lateinit var smConfirmed: JLabel
    lateinit var smDeaths: JLabel
    lateinit var smRecovered: JLabel
    lateinit var smActive: JLabel
    lateinit var smIncidentRate: JLabel
    lateinit var smCaseFatality: JLabel
    lateinit var textBox: JTextField
    lateinit var button: JButton
    lateinit var filterPanel: JPanel
    lateinit var mainPanel: JPanel
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
        mainPanel = JPanel()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        setupMenu()
        setupFilterPanel()

        frame.add(mainPanel)

        frame.pack()

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
        filterPanel.preferredSize = Dimension(192, 270)
        val constraints: GridBagConstraints = GridBagConstraints()
        constraints.anchor = GridBagConstraints.WEST
        constraints.insets = Insets(10, 10, 10, 10)
        //        p.layout = BoxLayout(p, BoxLayout.PAGE_AXIS)

        button = JButton("Analyze Data")
        button.actionCommand = "analyze"
        button.mnemonic = KeyEvent.VK_A
        button.addActionListener(listener)
        constraints.gridx = 0
        constraints.gridy = 0
        filterPanel.add(button, constraints)

        label = JLabel("Average of: ")
        constraints.gridx = 1
        constraints.gridy = 0
        filterPanel.add(label, constraints)
        label = JLabel("5 Countries with the most:")
        constraints.gridx = 2
        constraints.gridy = 0
        filterPanel.add(label, constraints)
        label = JLabel("Confirmed Cases")
        constraints.gridx = 0
        constraints.gridy = 1
        filterPanel.add(label, constraints)
        label = JLabel("Deaths")
        constraints.gridx = 0
        constraints.gridy = 2
        filterPanel.add(label, constraints)
        label = JLabel("Recovered")
        constraints.gridx = 0
        constraints.gridy = 3
        filterPanel.add(label, constraints)
        label = JLabel("Active Cases")
        constraints.gridx = 0
        constraints.gridy = 4
        filterPanel.add(label, constraints)
        label = JLabel("Incident Rate")
        constraints.gridx = 0
        constraints.gridy = 5
        filterPanel.add(label, constraints)
        label = JLabel("Fatality Ratio")
        constraints.gridx = 0
        constraints.gridy = 6
        filterPanel.add(label, constraints)
        avConfirmed = JLabel()
        constraints.gridx = 1
        constraints.gridy = 1
        filterPanel.add(avConfirmed, constraints)
        avDeaths = JLabel()
        constraints.gridx = 1
        constraints.gridy = 2
        filterPanel.add(avDeaths, constraints)
        avRecovered = JLabel()
        constraints.gridx = 1
        constraints.gridy = 3
        filterPanel.add(avRecovered, constraints)
        avActive = JLabel()
        constraints.gridx = 1
        constraints.gridy = 4
        filterPanel.add(avActive, constraints)
        avIncidentRate = JLabel()
        constraints.gridx = 1
        constraints.gridy = 5
        filterPanel.add(avIncidentRate, constraints)
        avCaseFatality = JLabel()
        constraints.gridx = 1
        constraints.gridy = 6
        filterPanel.add(avCaseFatality, constraints)

        smConfirmed = JLabel()
        constraints.gridx = 2
        constraints.gridy = 1
        filterPanel.add(smConfirmed, constraints)
        smDeaths = JLabel()
        constraints.gridx = 2
        constraints.gridy = 2
        filterPanel.add(smDeaths, constraints)
        smRecovered = JLabel()
        constraints.gridx = 2
        constraints.gridy = 3
        filterPanel.add(smRecovered, constraints)
        smActive = JLabel()
        constraints.gridx = 2
        constraints.gridy = 4
        filterPanel.add(smActive, constraints)
        smIncidentRate = JLabel()
        constraints.gridx = 2
        constraints.gridy = 5
        filterPanel.add(smIncidentRate, constraints)
        smCaseFatality = JLabel()
        constraints.gridx = 2
        constraints.gridy = 6
        filterPanel.add(smCaseFatality, constraints)

        filterPanel.border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "General")

        mainPanel.add(filterPanel)

        filterPanel = JPanel(GridBagLayout())
        filterPanel.preferredSize = Dimension(192, 270)

        label = JLabel("Country:")
        constraints.gridx = 0
        constraints.gridy = 0
        filterPanel.add(label, constraints)

        textBox = JTextField()
        textBox.minimumSize = textBox.preferredSize
        constraints.gridx = 1
        constraints.gridy = 0
        constraints.weightx = 1.0
        constraints.fill = GridBagConstraints.HORIZONTAL
        filterPanel.add(textBox, constraints)
        constraints.weightx = 0.0

        button = JButton("Analyze Country")
        button.actionCommand = "analyzeCountry"
        button.mnemonic = KeyEvent.VK_C
        button.addActionListener(listener)
        constraints.gridx = 2
        constraints.gridy = 0
        filterPanel.add(button, constraints)

        filterPanel.border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Country")

        mainPanel.add(filterPanel)

    }

}