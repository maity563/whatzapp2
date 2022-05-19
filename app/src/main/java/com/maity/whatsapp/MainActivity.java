package com.maity.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.maity.whatsapp.Adapter.Fragmentadapter;
import com.maity.whatsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();

        binding.viewpager.setAdapter(new Fragmentadapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.settings:

                Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent1);
                  break;
            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,signactivity.class);
                startActivity(intent);
                break;

           

        }
        return true;
    }

}