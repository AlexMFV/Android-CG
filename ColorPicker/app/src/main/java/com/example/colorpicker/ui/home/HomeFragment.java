package com.example.colorpicker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colorpicker.R;
import com.example.colorpicker.ui.common.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private RecyclerView postList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, postsRef;
    String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.loadColourOfTheDay(root);

        //Start FireBase Instances
        mAuth = FirebaseAuth.getInstance();

        //Get the current user ID
        userId = mAuth.getCurrentUser().getUid();

        //References for the Database and Storage
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postsRef = FirebaseDatabase.getInstance().getReference().child("Photos");

        //Setting the RecyclerView as a Layout Manager Template
        postList = root.findViewById(R.id.lstPosts);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        postList.setLayoutManager(linearLayout);

        DisplayImagePosts();

        return root;
    }

    /**
     * This method is responsible for Querying the Firebase Storage and returning all the images associated with the user
     * (Every user can see every image from every account)
     * @see FirebaseStorage
     * @see FirebaseDatabase
     * @see FirebaseRecyclerAdapter
     * @see FirebaseRecyclerOptions
     */
    private void DisplayImagePosts(){
        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(postsRef, Posts.class)
                .build();

        FirebaseRecyclerAdapter<Posts, PostsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Posts, PostsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Posts model) {
                        //Through the ViewModel set the values from the database to the elements
                        holder.username.setText(model.getUsername());
                        holder.date.setText(model.getDate());
                        holder.time.setText(model.getTime());
                        Picasso.get().load(model.getImageUrl()).into(holder.image);
                    }

                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //Set the template fot the ViewHolder
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_display_layout, parent, false);
                        PostsViewHolder viewHolder = new PostsViewHolder(view);
                        return viewHolder;
                    }
                };

        //Telling the Recycler List which Template to use
        postList.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * Static class responsible for the static elements in the ViewHolder
     */
    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView username, date, time;
        ImageView image;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.post_user_name);
            date = itemView.findViewById(R.id.post_date);
            time = itemView.findViewById(R.id.post_time);
            image = itemView.findViewById(R.id.post_image);
        }
    }
}
