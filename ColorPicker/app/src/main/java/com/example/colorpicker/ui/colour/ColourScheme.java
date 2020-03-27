package com.example.colorpicker.ui.colour;

import com.example.colorpicker.ui.common.Method;
import java.util.ArrayList;

/**
 * @author Alex Valente
 */
public class ColourScheme {
    private static ArrayList<Colour> palette;
    private int size;
    private Method type;

    /**
     * Constructor for the ColourScheme class
     * @param _colour Object of type Colour that represents the initial colour to be added.
     * @see Colour
     * @param _size Integer between 2 and 4 inclusive, determining the size of the Colour Palette to be generated
     * @param _type Enumerator of type Method
     * @see Method
     */
    public ColourScheme(Colour _colour, int _size, Method _type){
        palette = new ArrayList<Colour>();
        this.size = _size;
        palette.add(_colour);
        this.type = _type;
    }

    /**
     * Receives a Colour to be added to the palette list.
     * @param toAdd Object of type Colour, represents the colour to be added.
     * @see Colour
     */
    public void Add(Colour toAdd){
        palette.add(toAdd);
    }

    /**
     * Receives a Colour and an index
     * @param _newColour Object of type Colour used to replace another colour.
     * @param idx Integer, determines the position of the colour to be replaced
     * @see Colour
     */
    public void Change(Colour _newColour, int idx){
        palette.set(idx, _newColour);
    }

    /**
     * Get the colour from the list by the index
     * @param idx Integer representing the index of the Colour in the list.
     * @return Object of type Colour
     * @see Colour
     */
    public Colour Get(int idx){
        return palette.get(idx);
    }

    /**
     * Getter method for the Size of the list (amount of colours in the Scheme)
     * @return Integer, Size of the colour list/palette
     */
    public int Size() { return this.size; }

    /**
     * Getter method for the Type of the list (amount of colours in the Scheme)
     * @return Enumerator of type Method, identifies the type of algorithm has been used to generate this ColourScheme
     * @see Method
     * @see ColourScheme
     */
    public Method Type() { return this.type; }
}