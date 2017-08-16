package com.etc.spring.service;


import org.apache.commons.lang.StringUtils;

/**
 * Created by none.none on 2017/2/13.
 */
public enum PageState {
    /**
     * 返回0-5
     */
    SETPAGE, FIRST, PREVIOUS, NEXT, LAST, GOPAGE;

    public static int getOrdinal(String value) {
        int index = -1;
        if (StringUtils.isEmpty(value)) {
            return index;
        }
        String newValue = StringUtils.trim(value).toUpperCase();
        try {
            index = valueOf(newValue).ordinal();
        } catch (IllegalArgumentException e) {
        }
        return index;
    }
}


