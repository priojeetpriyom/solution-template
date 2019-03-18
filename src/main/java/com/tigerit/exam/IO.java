package com.tigerit.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
/**
 * @author Faisal Ahmed
 * This is a helper class for input/output in java.
 * Your don't need to use it if you want. This is
 * just for your convenience. Don't use Scanner or
 * System.console() stuff for input.
 */
public class IO {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static Scanner in; 

    private IO() {}

    static long timer;
    public static void init(){
        FileInputStream instream = null;  
        PrintStream outstream = null;  

        String INPUT  = "/home/pritom/Documents/tigerit/solution-template/src/main/java/com/tigerit/exam/input.txt";
        String OUTPUT  = "/home/pritom/Documents/tigerit/solution-template/src/main/java/com/tigerit/exam/output.txt";

        
        try {  
            instream = new FileInputStream(INPUT);  
            outstream = new PrintStream(new FileOutputStream(OUTPUT));  
            System.setIn(instream);  
            System.setOut(outstream);  
        } catch (Exception e) {  
            System.err.println("Error Occurred.: ");  
            e.printStackTrace();
        }
        in = new Scanner(System.in);
        timer = System.currentTimeMillis();
        // printLine("firstLineFromFile:"+in.nextLine()); 
    }

    public static String readLine() {
        String value;
        try {
            value = reader.readLine();
        } catch (IOException ex) {
            value = null;
        }
        return value;
    }

    public static Integer readLineAsInteger() {
        return Integer.parseInt(readLine());
    }

    
    // public static String readLine() {
    //     // printLine("readLine called");
    //     String value;
        
    //     value = in.nextLine();
        
    //     // printLine("scanned value:"+value);
    //     return value;
    // }
    // public static Integer readLineAsInteger() {
    //     return Integer.parseInt(readLine());
    // }

    public static void printLine(Object value) {
        System.out.println(value);
    }


    static void printTimeConsumed() {
        printLine("time consumed:"+ (System.currentTimeMillis() - timer) );
    }
}
