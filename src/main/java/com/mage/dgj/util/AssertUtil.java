package com.mage.dgj.util;

import com.mage.dgj.exception.ParamsException;

public class AssertUtil {

    public static void isTrue(Boolean flag,String msg){
        if (flag){
            throw new ParamsException(msg);
        }
    }
}
