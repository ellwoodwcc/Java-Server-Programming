/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MyOutput {
// If a file is not passed in, we will open up a PrintStream
// created from System.out

    PrintStream out = System.out;

// output goes to screen 
    public MyOutput() {
    }//basically just doing sysout.println

// output goes to a file
    public MyOutput(String file) {
        this(file, false);//can either overwrite file or append
    }//this calls the other constructor MyOutput below with false added as boolean

// If append = true, then the output file will be appended as
// opposed to overwriting from the beginning. 
    public MyOutput(String file, boolean append) {//
        try {
            if (file.length() > 0) {
                FileOutputStream fw;
                if (append) {
                    fw = new FileOutputStream(file, true);
                } else {
                    fw = new FileOutputStream(file);
                }
                out = new PrintStream(fw);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Exception in get"
                    + e.getMessage());
        }
    }

    public void println(String str) {
        out.println(str);
    }

    public void print(String str) {
        out.print(str);
    }

    public void close() {
        out.close();
    }

    protected void finalize() {
        close();
    }
}