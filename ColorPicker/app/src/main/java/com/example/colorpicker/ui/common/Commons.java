package com.example.colorpicker.ui.common;

/**
 * @author Alex Valente
 */
public final class Commons {
    /**
     * Digital Luminance Constants
     */
    public static final float LUM_R = 0.2126f;
    public static final float LUM_G = 0.7152f;
    public static final float LUM_B = 0.0722f;

    /**
     * Perceived Luminance Constants
     */
    public static final float PLUM_R = 0.299f;
    public static final float PLUM_G = 0.587f;
    public static final float PLUM_B = 0.114f;

    /**
     * MIN-MAX Constants
     */
    static final int RGB_MAX = 255;
    static final int RGB_MIN = 0;

    /* Luminance Ratio (L1 + 0.05) / (L2 + 0.05) ,
    goes from 1 (no contrast) all the way to21
    (highest possible contrast)*/
}
