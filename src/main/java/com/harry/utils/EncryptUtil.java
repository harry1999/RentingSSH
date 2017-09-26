package com.harry.utils;


import org.apache.commons.codec.binary.Base64;

public class EncryptUtil {


    private static final String TOKEN = "x1y2z3";
    private static int time = 5;


    private EncryptUtil() {

    }


    public static String encode(String info) {

        byte[] result = Base64.encodeBase64((info + TOKEN).getBytes());

        if (time-- != 0) {

            encode(new String(result));

        }

        time = 5;

        return new String(result);

    }


    public static String decode(String code) {

        byte[] result = null;

        for (int i = 0; i < time; i++) {

            result = Base64.decodeBase64(code);

            result = new String(result).substring(0, new String(result).indexOf(TOKEN)).getBytes();

        }

        return new String(result);

    }


    public static void main(String[] args) {

        System.out.println(encode("11111"));

        System.out.println(decode("MTExMTF4MXkyejM="));

    }


}
