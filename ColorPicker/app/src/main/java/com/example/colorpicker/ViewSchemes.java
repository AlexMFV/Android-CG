package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.colorpicker.ui.common.Schemes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewSchemes extends AppCompatActivity {

    private RecyclerView schemesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, schemesRef;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schemes);

        //Set the template for the RecycleListView
        schemesList = findViewById(R.id.lstSchemes);
        schemesList.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getBaseContext());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        schemesList.setLayoutManager(linearLayout);

        //Firebase Instances
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        schemesRef = FirebaseDatabase.getInstance().getReference().child("Schemes");

        DisplayImagePosts();
    }

    /**
     * This method is responsible for updating the RecyclerListView with the images from the database
     * this method loads the images in the background, while it only show a limited amount otf them to the user.
     */
    private void DisplayImagePosts(){
        FirebaseRecyclerOptions<Schemes> options = new FirebaseRecyclerOptions.Builder<Schemes>()
                .setQuery(schemesRef, Schemes.class)
                .build();

        FirebaseRecyclerAdapter<Schemes, ViewSchemes.SchemesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Schemes, ViewSchemes.SchemesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewSchemes.SchemesViewHolder holder, int position, @NonNull Schemes model) {

                        holder.col1.setBackground(ToDrawable(model.getColor1()));
                        holder.col2.setBackground(ToDrawable(model.getColor2()));

                        String size = model.getSize();

                        if(Integer.valueOf(size) == 3) {
                            holder.col3.setBackground(ToDrawable(model.getColor3()));
                            holder.col4.setVisibility(View.INVISIBLE);
                        }
                        else {
                            if (Integer.valueOf(size) == 2) {
                                holder.col3.setVisibility(View.INVISIBLE);
                                holder.col4.setVisibility(View.INVISIBLE);
                            } else {
                                holder.col3.setBackground(ToDrawable(model.getColor3()));
                                holder.col4.setBackground(ToDrawable(model.getColor4()));
                            }
                        }
                    }

                    @NonNull
                    @Override
                    public ViewSchemes.SchemesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_display_layout, parent, false);
                        ViewSchemes.SchemesViewHolder viewHolder = new ViewSchemes.SchemesViewHolder(view);
                        return viewHolder;
                    }
                };

        schemesList.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * Class responsible for the static members of the template
     */
    public static class SchemesViewHolder extends RecyclerView.ViewHolder{
        Button col1, col2, col3, col4;

        public SchemesViewHolder(@NonNull View itemView) {
            super(itemView);

            col1 = itemView.findViewById(R.id.scheme_colour1);
            col2 = itemView.findViewById(R.id.scheme_colour2);
            col3 = itemView.findViewById(R.id.scheme_colour3);
            col4 = itemView.findViewById(R.id.scheme_colour4);
        }
    }

    /**
     * This method is responsible for converting the RGB String values into a Drawable that later will be added to the
     * palette buttons
     * @param rgb String containing RGB values
     * @return Object of type Drawable
     */
    public Drawable ToDrawable(String rgb){
        rgb = rgb.substring(1, rgb.length()-1);
        String[] values = rgb.split(",");

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(10);
        drawable.setStroke(5, Color.BLACK);
        drawable.setColor(Color.rgb(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2])));

        return drawable;
    }
}