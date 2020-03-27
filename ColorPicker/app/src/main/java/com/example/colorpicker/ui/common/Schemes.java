package com.example.colorpicker.ui.common;

import com.example.colorpicker.ui.colour.Colour;

/**
 * @author Alex Valente
 */
public class Schemes {
    String uid;
    String size;
    String color1;
    String color2;
    String color3;
    String color4;

    public Schemes(){ }

    /**
     * Constructor for the Schemes class
     * @param uid String with and ID corresponding to the respective user
     * @param size Integer representing the size of the Colour list
     * @param color1 Object of type Colour, represents the first colour in the list.
     * @param color2 Object of type Colour, represents the second colour in the list.
     * @param color3 Object of type Colour, represents the third colour in the list.
     * @param color4 Object of type Colour, represents the forth colour in the list.
     * @see Colour
     */
    public Schemes(String uid, String size, String color1, String color2, String color3, String color4) {
        this.uid = uid;
        this.size = size;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getColor4() {
        return color4;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }
}
