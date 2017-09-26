package com.harry.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetToListUtil {

    private SetToListUtil() {

    }


    public static <T> List<T> setToList(Set<T> set) {

        if (set == null || set.isEmpty()) {

            return null;
        }


        List<T> list = new ArrayList<T>();

        Iterator<T> iterator = set.iterator();

        while (iterator.hasNext()) {

            list.add(iterator.next());

        }

        return list;

    }


}