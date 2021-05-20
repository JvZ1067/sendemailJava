package com.jacode.javamaildemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jacode.javamaildemo.MainActivity;
import com.jacode.javamaildemo.databinding.ActivityConfigurationHostBinding;
import com.jacode.javamaildemo.model.ConfigurationModel;
import com.jacode.javamaildemo.provider.ConfigurationProvider;

import java.util.Objects;

public class ConfigurationHost extends AppCompatActivity {

    /**
     * link view with ViewBiding
     */
    private ActivityConfigurationHostBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConfigurationHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ConfigurationModel host=ConfigurationProvider.ReadConfiguration(this);

        if (host.getPort()!=0){
            binding.hostName.setText(host.getHost());
            binding.hostPort.setText(host.getPort().toString());
            binding.password.setText(host.getPassword());
            binding.username.setText(host.getUsername());
        }

        // LAMBDA EXPRESSION FOR THE CLICK EVENT
        binding.btnSave.setOnClickListener(view -> saveConfigurations());


    }

    /**
     * SAVE CONFIGURATION IN MEMORY
     */
    private void saveConfigurations() {
        // GET DATA FROM THE VIEW
        String host= Objects.requireNonNull(binding.hostName.getText()).toString();
        int port=Integer.parseInt(Objects.requireNonNull(binding.hostPort.getText()).toString());
        String username= Objects.requireNonNull(binding.username.getText()).toString();
        String password= Objects.requireNonNull(binding.password.getText()).toString();

        // SET DATA TO THE  MODEL
        ConfigurationModel config=new ConfigurationModel(host, port, username, password);

        // VERIFY IF THE DATA WAS SAVED IN MEMORY
        boolean status=ConfigurationProvider.WriteConfiguration(this,config);
        if (status) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"No se ha Guardado",Toast.LENGTH_SHORT).show();
        }
    }
}