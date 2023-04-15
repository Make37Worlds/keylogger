package org.example;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class MouseLogger implements NativeMouseListener {
    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        try {
            // 使用FileWriter和BufferedWriter将换行符追加到keys.txt
            FileWriter fileWriter = new FileWriter("keys.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("无法写入文件: " + e.getMessage());
        }
    }
    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
    }
    @Override
    public void nativeMouseReleased(NativeMouseEvent e){

    }
}
