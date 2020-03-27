package com.example.colorpicker.ui.common;

import com.example.colorpicker.ui.colour.Colour;
import com.example.colorpicker.ui.colour.ColourScheme;

import static com.example.colorpicker.ui.common.Commons.*;
import java.util.Random;

/**
 * @author Alex Valente
 */
public class Algorithms {

    /**
     * Generates a ColourScheme following the rules of the Analogous Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void Analogous(){

    }

    /**
     * Generates a ColourScheme following the rules of the Monochromatic Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void Monochromatic(){

    }

    /**
     * Generates a ColourScheme following the rules of the Triadic Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void Triadic(){

    }

    /**
     * Generates a ColourScheme following the rules of the Tetradic Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void Tetradic(){

    }

    /**
     * Generates a ColourScheme following the rules of the Complementary Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void Complementary(){

    }

    /**
     * Generates a ColourScheme following the rules of the SplitComplementary Harmonic
     * @deprecated Not Implemented Yet
     * @see ColourScheme
     */
    public static void SplitComplementary(){

    }

    /**
     * Generates a pseudo-random assortment of colours.
     * @return Object of type ColourScheme
     * @see ColourScheme
     */
    public static ColourScheme Random(Colour colour, int size){
        ColourScheme scheme = new ColourScheme(colour, size, Method.RANDOM);

        while(size-1 != 0){
            scheme.Add(GenerateRandom());
            size--;
        }
        return scheme;
    }

    /**
     * Complements the method Algorithms.Random() by generating a pseudo-random colour.
     * @return Object of type Colour to be added to the random assortment of colours
     * @see Colour
     * @see Random
     * @see ColourScheme
     */
    /* Generates a random colour */
    private static Colour GenerateRandom(){
        Random rand = new Random();
        int r = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;
        int g = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;
        int b = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;

        return new Colour(r, g, b);
    }
}
