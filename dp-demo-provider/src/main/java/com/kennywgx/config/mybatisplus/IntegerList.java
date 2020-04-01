package com.kennywgx.config.mybatisplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerList extends ArrayList<Integer> {

    @Override
    public String toString() {
        if (this.size() == 0)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        forEach(item -> stringBuilder.append(",").append(item));
        return stringBuilder.substring(1);
    }

    public static IntegerList toIntegerList(List<Integer> list) {
        if (null == list) {
            return null;
        }
        IntegerList integerList = new IntegerList();
        integerList.addAll(list);
        return integerList;
    }

    public static IntegerList toIntegerList(Integer... integers) {
        if (null == integers) {
            return null;
        }
        IntegerList integerList = new IntegerList();
        integerList.addAll(Arrays.asList(integers));
        return integerList;
    }

}
