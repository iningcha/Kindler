package com.example.kindler;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import Database.User;
import Database.UserViewModel;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView name;
    private ImageView profilePicture;
    private TextView biography;
    Bitmap bitmap=null;
    EditText edtBio,edtName;
    private UserViewModel mUserViewModel;
    String imageStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        init();
        listeners();
    }

    public void init() {

        profilePicture = findViewById(R.id.profilePicture);
        edtBio = findViewById(R.id.edtBiography);
        edtName = findViewById(R.id.edtName);
        Database.Profile p = mUserViewModel.getCurrProfile();

        edtName.setHint(p.getProfileName());
        edtBio.setHint(p.getProfileBiography());

        imageStr=p.getProfilePicture();

        if (imageStr.length()>0) {
            profilePicture.setImageBitmap(decodeBase64(imageStr));
        }else {
            profilePicture.setImageResource(R.drawable.test);
        }

    }

    public void listeners(){
        profilePicture.setOnClickListener(this);
    }

    public void onClickSave(View view) {
        Database.Profile p = mUserViewModel.getCurrProfile();
        if (edtName.getText().toString().length() > 0) {
            p.setProfileName(edtName.getText().toString());
        }
        if (edtBio.getText().toString().length() > 0) {
            p.setProfileBiography(edtBio.getText().toString());
        }

        if (bitmap != null) {
            imageStr=encodeTobase64(bitmap);
            p.setProfilePicture(imageStr);
        }
        mUserViewModel.setProfile(p);
        Toast.makeText(this, "Saved Profile", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditProfileActivity.this, Profile.class);
        startActivity(intent);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.profilePicture:
                CameraDialog cameraDialog=new CameraDialog(EditProfileActivity.this);
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


                } catch (Exception e) {
                    Log.e("Exception",e.toString());
                }
            }

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
                    dismiss();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                }
            });

            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
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
