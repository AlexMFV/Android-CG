package com.example.colorpicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.example.colorpicker.ui.colour.Colour;
import com.example.colorpicker.ui.colour.ColourScheme;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.*;

/**
 * @author Alex Valente
 * @author Rosie Murphy
 * @author Furqan Khan
 * @author Benedita Laranjeira
 */
public class Share extends Activity {

    Button btnShare;
    Intent shareIntent;
    String shareBody;
    ColourScheme scheme;
    Button btnSelected;
    TextView txtHex;
    TextView txtRGBA;
    TextView txtCMYK;
    Button btnSave;
    String currDate, currTime, finalDate, userId;
    //Button changeSelected;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, schemesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);

        //Convert the passed JSON object back to the original object
        String jsonObj = getIntent().getStringExtra("Generated_Scheme");
        scheme = new Gson().fromJson(jsonObj, ColourScheme.class);

        //FireBase Instances and References
        mAuth = FirebaseAuth.getInstance();
        //Current UserId
        userId = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        schemesRef = FirebaseDatabase.getInstance().getReference().child("Schemes");

        //Logic Methods
        BuildColourScheme();
        ShareMessageBody();

        //Get references for the elements
        btnSelected = findViewById(R.id.btnSelected);
        txtHex = findViewById(R.id.txtHex);
        txtRGBA = findViewById(R.id.txtRGBA);
        txtCMYK = findViewById(R.id.txtCMYK);
        btnShare = findViewById(R.id.Bshare);
        btnSave = findViewById(R.id.Bsave);

        //EventListeners
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

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                UploadColourSchemeToFireBase();
            }
        });

        UpdateValues(0);
    }

    /**
     * This method starts by generating a random ID with the userID, current date and current time, for added security.
     * Then an HashMap is created to map the Database Table and is then sent to Firebase to be added as a field.
     */
    private void UploadColourSchemeToFireBase() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currDate = dateFormat.format(cal.getTime());
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        currTime = dateFormat.format(cal.getTime());

        finalDate = currDate + currTime;

        usersRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    HashMap map = new HashMap();
                    map.put("uid", userId);
                    map.put("size", String.valueOf(scheme.Size()));

                    for(int i = 0; i < scheme.Size(); i++){
                        map.put("color"+(i+1), scheme.Get(i).StringRGBA());
                    }

                    if(scheme.Size() == 3)
                        map.put("color4", "(255,255,255)");
                    else
                    if(scheme.Size() == 2) {
                        map.put("color3", "(255,255,255)");
                        map.put("color4", "(255,255,255)");
                    }

                    schemesRef.child(userId + finalDate).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getBaseContext(), "Scheme has been saved!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "Error while saving the scheme!", Toast.LENGTH_LONG).show();
                                btnSave.setEnabled(true);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method manipulates the string message body while sharing, so that the user can share a link
     * that will show the generated palette in a website automatically
     */
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

    /**
     * Dynamically creates the Grid for the colours to be displayed
     */
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

    /**
     * Updates all the values from the text boxes to the buttons resembling the selected colours.
     * @param idx Receives an Integer that defines the index of the colour to be updated to.
     */
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

    /**
     * This method transforms the Id of the button into an index, so that it can be determined which colour it resembles.
     * @param id Receives an Integer that represents an element ID
     * @return Integer with the index it's colour
     */
    public int GetButtonID(int id){
        Button btn = findViewById(id);
        return Integer.valueOf(btn.getTag().toString());
    }
}
