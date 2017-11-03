package com.lib.mylib.util;

/**
 * ndk开测试类
 * Created by Administrator on 2017/11/2.
 */

public class JniUtil {

    private static JniUtil obj = new JniUtil();
    private JniUtil(){}

    public static JniUtil instance(){
        return obj;
    }
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("ndk-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String stringFromJNI1(int position);

    public static native String stringFromJNI2();
}
