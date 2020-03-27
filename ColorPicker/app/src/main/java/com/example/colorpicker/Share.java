package com.example.colorpicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.example.colorpicker.ui.colour.Colour;
import com.example.colorpicker.ui.colour.ColourScheme;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import static androidx.constraintlayout.widget.Constraints.*;

public class Share extends Activity {

    Button btnShare;
    Intent shareIntent;
    String shareBody;
    ColourScheme scheme;
    Button btnSelected;
    TextView txtHex;
    TextView txtRGBA;
    TextView txtCMYK;
    //Button changeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);

        //Convert the passed JSON object back to the original object
        String jsonObj = getIntent().getStringExtra("Generated_Scheme");
        scheme = new Gson().fromJson(jsonObj, ColourScheme.class);

        BuildColourScheme();
        ShareMessageBody();

        btnSelected = findViewById(R.id.btnSelected);
        txtHex = findViewById(R.id.txtHex);
        txtRGBA = findViewById(R.id.txtRGBA);
        txtCMYK = findViewById(R.id.txtCMYK);
        btnShare = findViewById(R.id.Bshare);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Colour Scheme");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
            }
        });

        UpdateValues(0);
    }

    private void ShareMessageBody() {
        String url = "https://colors.muz.li/palette/<0>/<1>/<2>/<3>/ffffff";
        for(int i = 0; i < scheme.Size(); i++){
            String toReplace = "<" + i + ">";
            url = url.replace(toReplace, scheme.Get(i).StringHex().substring(1));
        }

        if(scheme.Size() == 3)
            url = url.replace("<3>", "ffffff");
        else
            if(scheme.Size() == 2) {
                url = url.replace("<3>", "ffffff");
                url = url.replace("<2>", "ffffff");
            }

        shareBody = url;
    }

    //NOT YET IMPLEMENTED
    /*public void UpdateColorOnScheme(Colour newColour){
        Button btnSelected = findViewById(R.id.btnSelected);
        int colourIdx = Integer.valueOf(btnSelected.getTag().toString());
        scheme.Change(newColour, colourIdx);
        UpdateValues(0);
    }*/

    //Creates the Grid for the colours to be displayed
    public void BuildColourScheme(){
        for(int idx = 0; idx < scheme.Size(); idx++){
            Button btn = new Button(this);

            LinearLayout layout = findViewById(R.id.coloursLayout);
            layout.setWeightSum(1f);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1f / scheme.Size();
            btn.setLayoutParams(params);
            btn.setTag(idx);
            btn.setId(idx);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(10);
            drawable.setStroke(5, Color.BLACK);
            drawable.setColor(Color.rgb(scheme.Get(idx).RGBA()[0], scheme.Get(idx).RGBA()[1], scheme.Get(idx).RGBA()[2]));
            btn.setBackground(drawable);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateValues(GetButtonID(v.getId()));
                }
            });
            layout.addView(btn);
        }
    }

    public void UpdateValues(int idx){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(10);
        drawable.setStroke(5, Color.BLACK);
        drawable.setColor(Color.rgb(scheme.Get(idx).RGBA()[0], scheme.Get(idx).RGBA()[1], scheme.Get(idx).RGBA()[2]));
        btnSelected.setBackground(drawable);
        btnSelected.setTag(idx);

        txtHex.setText(String.format("%s %s", getString(R.string.text_hexValue), scheme.Get(idx).StringHex()));
        txtCMYK.setText(String.format("%s %s", getString(R.string.text_CmykValue), scheme.Get(idx).StringCMYK()));
        txtRGBA.setText(String.format("%s %s", getString(R.string.text_RgbValue), scheme.Get(idx).StringRGBA()));
    }

    public int GetButtonID(int id){
        Button btn = findViewById(id);
        return Integer.valueOf(btn.getTag().toString());
    }
}
