package com.kennywgx.config.mybatisplus;

import com.sun.org.omg.CORBA.portable.ValueHelper;

import java.util.ArrayList;
import java.util.List;

public class IntegerList extends ArrayList<Integer> {

    public static IntegerList toIntegerList(List<Integer> list) {
        if (null == list) {
            return null;
        }
        IntegerList integerList = new IntegerList();
        integerList.addAll(list);
        return integerList;
    }

}
