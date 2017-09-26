package com.harry.utils;

public class FileUtil {

    private FileUtil() {

    }


    public static String getFileType(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."), fileName.length());

    }


}
