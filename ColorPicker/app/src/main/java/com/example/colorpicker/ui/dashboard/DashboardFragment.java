package com.example.colorpicker.ui.dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.colorpicker.MainActivity;
import com.example.colorpicker.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * @author Alex Valente
 * @author Rosie Murphy
 * @Author Furqan Khan
 */
public class DashboardFragment extends Fragment {

    private ImageView viewImage;
    private Button button;
    private Button btnUpload;
    private String userId, imageUrl, currDate, currTime, finalDate;

    /**
     * FireBase Instances to allow connection to the Database and Authentication
     */
    FirebaseAuth mAuth;
    StorageReference imageRef;
    DatabaseReference usersRef;
    DatabaseReference photosRef;
    Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Firebase Instances
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //References to the database to allow write and read
        imageRef = FirebaseStorage.getInstance().getReference();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        photosRef = FirebaseDatabase.getInstance().getReference().child("Photos");

        //Instantiating the elements
        btnUpload = root.findViewById(R.id.btn_UploadPhoto);
        button = root.findViewById(R.id.btn_SelectPhoto);
        viewImage = root.findViewById(R.id.selectedImage);

        //OnClick Event Listeners
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFileToFireBase(viewImage.getBackground());
            }
        });

        return root;
    }


    /**
     * This method executes the AlertDialog for the user to select which method to upload a photo
     * Camera or Gallery
     * @see AlertDialog
     */
    private void selectImage(){
        final CharSequence[] options = { "Camera", "Gallery", "Exit"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Choose Method");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (options[choice].equals("Camera"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageState(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[choice].equals("Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[choice].equals("Exit"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * This event is executed once the user selects an option from the AlertDialog opened on the selectImage() method. This method
     * creates a temporary file in the phone internal storage and compresses the image so that it can be sent to the FireBase Storage
     * without taking much space.
     * @param requestCode Integer received as a response from the AlertDialog (option selected)
     * @param resultCode Integer received as a response from the Alert Dialog (Valid selected option)
     * @param data Intent that contains the image data, ready to be parsed to a Uri
     * @see AlertDialog
     * @see Intent
     * @see Uri
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* Camera Result */
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageState());
                for (File tempF : f.listFiles()) {
                    if (tempF.getName().equals("temp.jpg")) {
                        f = tempF;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    btnUpload.setEnabled(true);
                    String path = Environment.getExternalStorageState()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /* Gallery Result */
            } else if (requestCode == 2) {
                imageUri = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = this.getActivity().getContentResolver().query(imageUri, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, bmOptions));
                Log.w("path of image", picturePath + "");
                viewImage.setImageBitmap(thumbnail);
                btnUpload.setEnabled(true);
            }
        }
    }


    /**
     * This method is responsible for creating random IDs and uploading the Photos to the FireBase Storage.
     * The method waits to receive the imageUri, which has a reference to the image on the Database, which
     * will be used to assign to the respective user.
     * @param photo Receives a Drawable that is later added as a styling to the buttons.
     * @see FirebaseStorage
     * @see FirebaseDatabase
     * @see Uri
     * @see Drawable
     */
    public void UploadFileToFireBase(Drawable photo){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currDate = dateFormat.format(cal.getTime());
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        currTime = dateFormat.format(cal.getTime());

        finalDate = currDate + currTime;
        final StorageReference filePath = imageRef.child("Images").child(imageUri.getLastPathSegment() + finalDate + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        //On Failure
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
            //On Successful Execution
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Image Uploaded Successfully!", Toast.LENGTH_SHORT).show();

                //Waits for the URL of the uploaded Image
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        //Image Reference URL
                        imageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            imageUrl = task.getResult().toString();
                            LinkPhotoToUserFireBase();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    }
                });
            }
        });
    }

    /**
     * This method is responsible for assigning the current user to the recently uploaded image, through URL
     * references.
     */
    private void LinkPhotoToUserFireBase() {
        usersRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Gathers the username of the current user
                    String username = dataSnapshot.child("username").getValue().toString();

                    //Hash map, mapped to the Table structure of the database
                    HashMap map = new HashMap();
                    map.put("uid", userId);
                    map.put("date", currDate);
                    map.put("time", currTime);
                    map.put("username", username);
                    map.put("imageUrl", imageUrl);

                    //Creates a field in the Database with a Random ID provided by the userId and a mixture of Date and Time for
                    //added security and randomness
                    photosRef.child(userId + finalDate).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Photo has been published!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Error while publishing the photo!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            /**
             * @deprecated
             * @param databaseError /Error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}