package com.bincontrol.binstore.util;

import java.io.IOException;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Base64Utils {

    // 压缩和解压的次数
    private final static int DECODE_TIMES = 5;

    public static String decryptBASE64(String key) {

        key = key.trim().replace(" ", "");
        byte[] bytes;

        try {
            int decodeTimes = DECODE_TIMES;
            while (decodeTimes > 0) {
                bytes = (new BASE64Decoder()).decodeBuffer(key);
                key = new String(bytes, "utf-8");
                decodeTimes--;
            }
            return key;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String encryptBASE64(String key) {

        key = key.trim().replace(" ", "");
        byte[] bytes;

        try {
            int decodeTimes = 5;
            while (decodeTimes > 0) {
                bytes = key.getBytes();
                key = (new BASE64Encoder()).encodeBuffer(bytes);
                decodeTimes--;
            }
            return key;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
         }
    }

}
