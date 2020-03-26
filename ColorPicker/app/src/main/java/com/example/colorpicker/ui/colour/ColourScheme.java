package com.example.colorpicker.ui.colour;

import com.example.colorpicker.ui.common.Method;
import java.util.ArrayList;

public class ColourScheme {
    public static ArrayList<Colour> palette;
    int size;
    Method type;

    public ColourScheme(Colour _colour, int _size, Method _type){
        palette = new ArrayList<Colour>();
        this.size = _size;
        this.palette.add(_colour);
        this.type = _type;
    }

    public void Add(Colour toAdd){
        this.palette.add(toAdd);
    }

    public void Change(Colour _newColour, int idx){
        this.palette.set(idx, _newColour);
    }

    public Colour Get(int idx){
        return this.palette.get(idx);
    }

    public int Size() { return this.size; }
    public Method Type() { return this.type; }
}