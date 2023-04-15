package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalyzer {
    public static void analyzeLogFile(String logFilePath) throws IOException {

        String str = "";
        ArrayList<Integer> list = new ArrayList<>();
        FileWriter fw = new FileWriter("password.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

               str +=line+"\n";
            }

            str=str.replace(": ",":");
            str=str.replace("] ","]");
            str=str.replace("; ",";");
            str=str.replace(", ",",");
            String paragraph[] = str.split("(\n|;|\r|⏎|⇥|\\[CLIPBOARD\\]:)+");

            for(int i = 0; i < paragraph.length ; i++) {
                System.out.println(i + ": " + paragraph[i]);
                if (!paragraph[i].contains(" ")&&!paragraph[i].contains("␣")&&!paragraph[i].matches(".*[+\\-*/].*")) {
                    if (paragraph[i].length() > 10 || !paragraph[i].matches("[a-zA-Z]+")) {
                        list.add(i);
                    }
                }
            }
            for(int i = 1; i < list.size(); i++){
                if(list.get(i)-list.get(i-1) == 1){
                    fw.write("("+paragraph[list.get(i-1)]+", "+paragraph[list.get(i)]+")"+"\n");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            // handle the exception appropriately
        } finally {
            fw.close();
        }
    }

}
