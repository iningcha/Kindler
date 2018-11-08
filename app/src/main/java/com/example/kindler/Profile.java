package com.example.kindler;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import Database.UserViewModel;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    private TextView name;
    private ImageView profilePicture;
    private TextView biography;
    Bitmap bitmap;
    EditText edtBio,edtName;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        init();
        listeners();
    }

    public void init(){

        profilePicture = findViewById(R.id.profilePicture);
        name = findViewById(R.id.name);
        biography = findViewById(R.id.biography);
        edtBio= findViewById(R.id.edtBiography);
        edtName=findViewById(R.id.edtName);
        Database.Profile p = mUserViewModel.getCurrProfile();
        edtName.setHint(p.getProfileName());
        edtBio.setHint(p.getProfileBiography());
        profilePicture.setImageResource(R.drawable.test);
        name.setText("Samantha Chang");
        biography.setText("Student studying CS at USC");

    }

    public void listeners(){
        profilePicture.setOnClickListener(this);
    }

    public void onClickSave(View view) {
        Database.Profile p = mUserViewModel.getCurrProfile();
        p.setProfileName(edtName.getText().toString());
        p.setProfileBiography(edtBio.getText().toString());
        mUserViewModel.setProfile(p);
        Toast.makeText(this, "Saved Profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.profilePicture:
                CameraDialog cameraDialog=new CameraDialog(Profile.this);
                cameraDialog.show();
                break;
            default:
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

                profilePicture.setImageBitmap(bitmap);

             //   strProfile=encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

            } else if (requestCode == 2) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getApplicationContext().getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    bitmap = (BitmapFactory.decodeFile(picturePath));
                    bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                    profilePicture.setImageBitmap(bitmap);

              //      strProfile=encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

                } catch (Exception e) {
                    Log.e("Exception",e.toString());
                }
            }
//            else if (requestCode==3){
//                bitmap = (Bitmap) data.getExtras().get("data");
//                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
//
//                bgImageView.setImageBitmap(bitmap);
//
//               // strBg=encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//            }
//            else if (requestCode==4){
//                try {
//                    Uri selectedImage = data.getData();
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//                    Cursor c = context.getContentResolver().query(selectedImage, filePath, null, null, null);
//                    c.moveToFirst();
//                    int columnIndex = c.getColumnIndex(filePath[0]);
//                    String picturePath = c.getString(columnIndex);
//                    c.close();
//                    bitmap = (BitmapFactory.decodeFile(picturePath));
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
//                    bgImageView.setImageBitmap(bitmap);
//
//                    strBg=encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//                } catch (Exception e) {
//                    Log.e("Exception",e.toString());
//                }
//            }
        }
    }

    public class CameraDialog extends Dialog{

        Context context;
        TextView camera,gallery,cancel;

        public CameraDialog(@NonNull Context context) {
            super(context);
            this.context=context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.dialog);

            camera=(TextView)findViewById(R.id.camera);
            gallery=(TextView)findViewById(R.id.gallery);
            cancel=(TextView)findViewById(R.id.cancel);

            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                }
            });

            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }
    }
}