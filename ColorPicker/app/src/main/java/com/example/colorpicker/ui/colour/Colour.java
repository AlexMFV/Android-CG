package com.example.colorpicker.ui.colour;

import java.text.DecimalFormat;

/**
 * @author Alex Valente
 */
public class Colour {

    /**
     * Variables for the various types of Colour representation
     */
    private String hex;
    private double[] cmyk;
    private int[] rgba;

    /*DEPRECATED VARIABLES*/
        //String colour_name;
        //int[] hsl; /* Mode: º-%-% (degree, percentage, percentage)*/
        //double luminance;


    /**
     * Constructor for the Colour class that receives 3 integers corresponding to the respective RGB values.
     * @param r Corresponds to the color Red in the Spectrum
     * @param g Corresponds to the color Green in the Spectrum
     * @param b Corresponds to the color Blue in the Spectrum
     */
    public Colour(int r, int g, int b){
        this.hex = ConvertToHex(r, g, b);
        this.cmyk = ConvertToCMYK(r, g, b);
        this.rgba = new int[]{r, g, b, 255};
        //this.hsl = ConvertToHSL(r, g, b); /* DEPRECATED */
    }


    /**
     * Converts a set of RGB values into the corresponding Hex values
     * @param r Corresponds to the color Red in the Spectrum
     * @param g Corresponds to the color Green in the Spectrum
     * @param b Corresponds to the color Blue in the Spectrum
     * @return Hexadecimal value that corresponds to a certain colour
     */
    private String ConvertToHex(int r, int g, int b){
        return "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
    }

    /**
     * Converts a set of RGB values into the corresponding CMYK values
     * @param r Corresponds to the color Red in the Spectrum
     * @param g Corresponds to the color Green in the Spectrum
     * @param b Corresponds to the color Blue in the Spectrum
     * @return Returns an array with values corresponding to CMYK (Cyan, Magenta, Yellow, Key) Colour
     */
    private double[] ConvertToCMYK(int r, int g, int b){
        double _r = r/255.0;
        double _g = g/255.0;
        double _b = b/255.0;
        double[] values = new double[4];

        double maxN = Math.max(_r, _g);
        maxN = Math.max(maxN, _b);
        double k = 1-maxN;

        values[0] = (1-_r-k) / (1-k); //c
        values[1] = (1-_g-k) / (1-k); //m
        values[2] = (1-_b-k) / (1-k); //y
        values[3] = k; //k

        return values;
    }

    /**
     * Get RGBA value of the colour.
     * @return An integer array with RGBA values respectively.
     */
    public int[] RGBA(){ return this.rgba; }

    /**
     * Get RGBA value of the colour in String format.
     * @return A String formed with the object RGBA values.
     */
    public String StringRGBA(){ return "(" + this.rgba[0] + "," + this.rgba[1] + "," + this.rgba[2] + ")"; }

    /**
     * Get CMYK value of the colour in String format.
     * @return A String formed with the object CMYK values.
     */
    public String StringCMYK(){
        DecimalFormat df = new DecimalFormat("#.##");
        return "(" + df.format(this.cmyk[0]) + "," + df.format(this.cmyk[1]) + "," + df.format(this.cmyk[2]) + "," + df.format(this.cmyk[3]) + ")";
    }

    /**
     * Get Hexadecimal value of the colour in String format.
     * @return A String formed with the object Hexadecimal value.
     */
    public String StringHex(){ return this.hex; }

    /* DEPRECATED METHOD */
        /*public int[] ConvertToHSL(int r, int g, int b){
            int[] values = new int[3];

            values[0] = r >= 180;
            values[1] = ;
            values[2] = ;

            return values;
        }
    */
}
