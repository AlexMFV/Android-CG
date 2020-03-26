package com.example.colorpicker.ui.common;

public final class Commons {
    /*Digital Luminance*/
    public static final float LUM_R = 0.2126f;
    public static final float LUM_G = 0.7152f;
    public static final float LUM_B = 0.0722f;

    /*Perceived Luminance*/
    public static final float PLUM_R = 0.299f;
    public static final float PLUM_G = 0.587f;
    public static final float PLUM_B = 0.114f;

    /* MAX-MIN Values */
    static final int RGB_MAX = 255;
    static final int RGB_MIN = 0;

    /* Luminance Ratio (L1 + 0.05) / (L2 + 0.05) ,
    goes from 1 (no contrast) all the way to21
    (highest possible contrast)*/
}
