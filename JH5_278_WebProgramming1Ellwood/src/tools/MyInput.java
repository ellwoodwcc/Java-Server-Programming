/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MyInput {
// If a file is not passed in, we will open up a Scanner
// created from System.in

    Scanner in = new Scanner(System.in);

// read from keyboard
    public MyInput() {
    }

//read from file fileName
    public MyInput(String fileName) {
        try {
            if (fileName.length() > 0) {
                File f = new File(fileName);
                in = new Scanner(f);
            }
        } catch (FileNotFoundException e) {
            System.out.println("MyInput open Error:" + fileName + " ... defaulting to keyboard");
        }
    }

    public void close() {
        in.close();
    }

    protected void finalize() {
        close();
    }

    public String get() {
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return null;
        }
    }

    public int getInt() {
        String str = get();
        if (str != null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) { /* Do Nothing */

            }
        }
        System.out.println("Bad int input");
        return -1;
    }
}
