package com.maity.whatsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.maity.whatsapp.Models.Users;
import com.maity.whatsapp.databinding.ActivitySettingsBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_settings);

        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage= FirebaseStorage.getInstance();
        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


         binding.back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent= new Intent(SettingsActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         });

         binding.savebutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String username=binding.etUsername.getText().toString();
                 String status=binding.etstatus.getText().toString();

                 HashMap<String,Object> obj =new HashMap<>();
                 obj.put("userName",username);
                 obj.put("Status",status);

                 database.getReference().child("Users")
                         .child(FirebaseAuth.getInstance().getUid())
                         .updateChildren(obj);
             }
         });
         database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull  DataSnapshot snapshot) {

                 Users users=snapshot.getValue(Users.class);
                 Picasso.get()
                         .load(users.getProfiledp())
                         .placeholder(R.drawable.profile_chat_dp)
                         .into(binding.dp);


             }

             @Override
             public void onCancelled(@NonNull  DatabaseError error) {

             }
         });

         binding.plus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 intent.setType("image/*");
                 startActivityForResult(intent,33);

             }
         });

    }

    @Override

    protected  void onActivityResult(int rqcode,int rscode,Intent data)
    {
     super.onActivityResult(rqcode,rscode,data);
     if(data.getData() != null)
     {
         Uri sfile=data.getData();
         binding.dp.setImageURI(sfile);

         final StorageReference reference=storage.getReference().child("Profile Picture").child(FirebaseAuth.getInstance().getUid());

         reference.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

              //   Toast.makeText(SettingsActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
               reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {

                       database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                               .child("Profile Picture").setValue(uri.toString());
                       Toast.makeText(SettingsActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                   }
               });

             }
         });


     }

    }

}