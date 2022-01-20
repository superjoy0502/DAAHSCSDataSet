/*
 * Copyright (c) Dongwoo Kim (https://github.com/superjoy0502) 2021.
 *
 * This program uses kotlin-csv library by doyaaaaaken (https://github.com/doyaaaaaken/kotlin-csv).
 * This program is licensed under Apache-2.0 because of the "License and copyright notice" condition of the license of the library above.
 */

package io.github.superjoy0502.daahscsdataset;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static GUI gui;

    public static void main(String[] args) {

        gui = new GUI();

        /*final JFileChooser chooser = new JFileChooser();
        Scanner scanner = new Scanner(System.in);

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");

        String userName = myObj.nextLine();  // Read user input
        System.out.println("Username is: " + userName);  // Output user input

        while (true) {

            clear();
            System.out.println("""
                ##DAAHS CS 20 Big Data Project - Dongwoo Kim##
                ==============================================
                Please choose a COVID-19 dataset file to analyze.
                <Enter 'F' to choose a file>
            """);

            String cmd = scanner.next();

            if (cmd.equalsIgnoreCase("F")) {

//                scanner.close();
                break;

            }

        }

        int val = chooser.showOpenDialog(null);
        if (val == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
        }*/

    }

    public static void clear() {

        for (int i = 0; i < 100; i++) System.out.println("\n");

    }

}
