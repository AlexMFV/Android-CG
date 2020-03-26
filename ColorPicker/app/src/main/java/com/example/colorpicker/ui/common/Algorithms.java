package com.example.colorpicker.ui.common;

import com.example.colorpicker.ui.colour.Colour;
import com.example.colorpicker.ui.colour.ColourScheme;

import static com.example.colorpicker.ui.common.Commons.*;
import java.util.Random;

public class Algorithms {

    public static void Analogous(){

    }

    public static void Monochromatic(){

    }

    public static void Triadic(){

    }

    public static void Tetradic(){

    }

    public static void Complementary(){

    }

    public static void SplitComplementary(){

    }

    /* Generates a random assortment of colours */
    public static ColourScheme Random(Colour colour, int size){
        ColourScheme scheme = new ColourScheme(colour, size, Method.RANDOM);

        while(size-1 != 0){
            scheme.Add(GenerateRandom());
            size--;
        }
        return scheme;
    }

    /* Generates a random colour */
    static Colour GenerateRandom(){
        Random rand = new Random();
        int r = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;
        int g = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;
        int b = rand.nextInt((RGB_MAX - RGB_MIN) + 1) + RGB_MIN;

        return new Colour(r, g, b);
    }
}
