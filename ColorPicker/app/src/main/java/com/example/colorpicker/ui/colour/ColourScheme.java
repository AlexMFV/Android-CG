package com.example.colorpicker.ui.colour;

import com.example.colorpicker.ui.common.Method;
import java.util.ArrayList;

public class ColourScheme {
    private static ArrayList<Colour> palette;
    private int size;
    private Method type;

    public ColourScheme(Colour _colour, int _size, Method _type){
        palette = new ArrayList<Colour>();
        this.size = _size;
        palette.add(_colour);
        this.type = _type;
    }

    public void Add(Colour toAdd){
        palette.add(toAdd);
    }

    public void Change(Colour _newColour, int idx){
        palette.set(idx, _newColour);
    }

    public Colour Get(int idx){
        return palette.get(idx);
    }

    public int Size() { return this.size; }
    public Method Type() { return this.type; }
}