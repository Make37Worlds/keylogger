package org.example;

public class split {
    public static void main(String[] args) {
//        String str = "[CLIPBOARD]:TAJZH8\n" +
//                "TAJJHMCS4321[CLIPBOARD]:ArrayList<Integer> list = new ArrayList<Integer>();\n" +
//                "\n" +
//                "\n" +
//                "HELLOW␣\n" +
//                "WOR\n" +
//                "⏎\n" +
//                "HELLO␣I␣AM␣JIN␣ZHIH\n" +
//                "ONG\n" +
//                "⏎\n" +
//                "TAJZH\n" +
//                "8⇧2\n" +
//                "GMAIL.CIN\n" +
//                "⏎\n" +
//                "TAJJHMCS51\n" +
//                "⏎\n" +
//                "⏎";
//        String paragraph[] = str.split("(\n|⏎|\\[CLIPBOARD\\]:)+");
//        for(int i = 0; i < paragraph.length ; i++){
//            System.out.println(i+": "+paragraph[i]);
//        }


        String str = "This is a sentence,with a comma. And another sentence;with a semicolon.";
        str = str.replaceAll("\\s*([,;:.?!])\\s*", "$1 ");
        System.out.println(str);
    }
}
