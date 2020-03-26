package com.example.colorpicker.ui.colour;

import java.text.DecimalFormat;

public class Colour {
    //String colour_name;
    private String hex;
    private double[] cmyk;
    private int[] rgba;
    //int[] hsl; /* Mode: º-%-% (degree, percentage, percentage)*/ /* DEPRECATED */
    //double luminance;

    public Colour(int r, int g, int b){
        this.hex = ConvertToHex(r, g, b);
        this.cmyk = ConvertToCMYK(r, g, b);
        this.rgba = new int[]{r, g, b, 255};
        //this.hsl = ConvertToHSL(r, g, b); /* DEPRECATED */
    }

    //public void Luminance(){ }

    private String ConvertToHex(int r, int g, int b){
        return "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
    }

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

    public int[] RGBA(){ return this.rgba; }

    public String StringRGBA(){ return "(" + this.rgba[0] + "," + this.rgba[1] + "," + this.rgba[2] + ")"; }

    public String StringCMYK(){
        DecimalFormat df = new DecimalFormat("#.##");
        return "(" + df.format(this.cmyk[0]) + "," + df.format(this.cmyk[1]) + "," + df.format(this.cmyk[2]) + "," + df.format(this.cmyk[3]) + ")";
    }

    public String StringHex(){ return this.hex; }

    /* DEPRECATED */
    /*public int[] ConvertToHSL(int r, int g, int b){
        int[] values = new int[3];

        values[0] = r >= 180;
        values[1] = ;
        values[2] = ;

        return values;
    }*/
}
