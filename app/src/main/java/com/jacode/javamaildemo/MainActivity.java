package com.jacode.javamaildemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jacode.javamaildemo.databinding.ActivityMainBinding;
import com.jacode.javamaildemo.model.ConfigurationModel;
import com.jacode.javamaildemo.provider.ConfigurationProvider;
import com.jacode.javamaildemo.provider.GenerateMailProvider;
import com.jacode.javamaildemo.service.NetworkService;
import com.jacode.javamaildemo.ui.ConfigurationHost;

import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;



public class MainActivity extends AppCompatActivity {

    /**
     * link view with ViewBinding
     */
    private ActivityMainBinding binding;

    // Model Host Config
    private ConfigurationModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model= ConfigurationProvider.ReadConfiguration(this);

        if (model.getPort()==0){
            Toast.makeText(this, " Puto Configura bien  tu host ahi arriba ⚙️ ", Toast.LENGTH_SHORT).show();
        }else{
            binding.emailFrom.setText(model.getUsername());
        }

        // app Tool bar
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_send :{
                        SendMail();
                        break;
                    }
                    case R.id.action_config:{
                        Intent intent = new Intent(MainActivity.this, ConfigurationHost.class);
                        startActivity(intent);
                        break;
                    }

                    default:
                        Toast.makeText(MainActivity.this, "No Válido", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }


    @SuppressLint("CheckResult")
    private void SendMail(){

        // Capture the data
        String to= Objects.requireNonNull(binding.emailTo.getText()).toString();
        String subject= Objects.requireNonNull(binding.subjectEmail.getText()).toString();
        String body=binding.emailBody.getText().toString();


        String[] params={to,subject,body};

        // Send Mail using Async Task - No Recommended  Async Task is deprecated
       if (NetworkService.isNetworkConnected(this)){
           SendMailAsync async=new SendMailAsync();
           async.execute(params);
       }else{
           Toast.makeText(this, "No tiene conexión a Internet", Toast.LENGTH_SHORT).show();
       }


    }



    @SuppressLint("StaticFieldLeak")
    private class SendMailAsync extends AsyncTask<String,Void,Boolean>{


        @Override
        protected Boolean doInBackground(String...params) {

            String to = params[0];
            String subject= params[1];
            String body=params[2];


            // CAPTURE THE DATA
            try {
                MimeMessage message=GenerateMailProvider.PlainMail(to,subject,body,MainActivity.this);
                if (message!=null){
                    Transport.send(message); // SEND MAIL
                    return true;
                }else return  false;

            } catch (MessagingException e) {
                Log.e("Error",e.toString());
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            // IT IS VERIFIED THAT THE MAIL WAS SEND
            if (aBoolean){
                // HIDE YOUR ALERT DIALOG HERE
                Toast.makeText(MainActivity.this, "Correo Enviado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Correo no Enviado", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Here you call your  dialog loading
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}