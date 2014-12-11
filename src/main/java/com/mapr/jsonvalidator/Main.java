package com.mapr.jsonvalidator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.base.Joiner;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> jsonFiles = new ArrayList<String>();

//        Support System.in as a method for inputting files
        while (System.in.available() > 0) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String stdInFile = null;
            while ((stdInFile = input.readLine()) != null) {
                jsonFiles.add(stdInFile);
            }
        }

        if (args.length > 0) {
            Collections.addAll(jsonFiles, args);
        }

        if(jsonFiles.isEmpty()) {
            System.err.println("Error: No files specified.");
            System.exit(1);
        }

//        Loop through the files and check that they contain valid records
        for (String filename : jsonFiles) {
            try {
                Stats stats = new Stats();
                final InputStream in = new FileInputStream(filename);
                final JsonParser jp = new JsonFactory().createParser(in);

                while (true) {
                    try {
                        if (jp.nextToken() == null) {
                            break;
                        }
                    } catch (JsonParseException e) {
                        stats.errors.add(e.getMessage());
                        stats.parseErrors += 1;
                    }

                }

                System.out.println(filename);
                System.out.print(getStatusString(stats));
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }

        System.exit(0);
    }

    private static String getStatusString(Stats stats) {
        String returnString = String.format("Parse Errors: %d%n",
                stats.parseErrors
        );

        if ( stats.parseErrors > 0) {
            returnString += String.format("Error Info: %n%s%n%n",
                    Joiner.on("\n").join(stats.errors) );
        }

        return returnString;
    }

    //    Stats object contains statistical information
    private static class Stats {
        Integer parseErrors = 0;
        List<String> errors = new ArrayList<String>();
    }
}
