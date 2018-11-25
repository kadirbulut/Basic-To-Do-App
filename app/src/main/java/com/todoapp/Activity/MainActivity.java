package com.todoapp.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.todoapp.Helper.RetroClient;
import com.todoapp.Models.TokenObject;
import com.todoapp.R;
import com.todoapp.Services.ApiServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.apache.commons.codec.binary.Base64;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signInButton;
    private EditText email_area;
    private EditText password_area;
    private String email="";
    private String password="";
    private String encodedString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setclickListenersForButtons();
    }

    private void setclickListenersForButtons() {
        loginButton = findViewById(R.id.login_button);
        signInButton = findViewById(R.id.sign_in_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditTexts();
                handleAuthentication();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sign_intent = new Intent(getApplicationContext(),Sign_In_Activity.class);
                startActivity(sign_intent);
            }
        });

    }

    private void checkEditTexts(){
        email_area = findViewById(R.id.email_text_area);
        password_area = findViewById(R.id.password_text_area);

        email = email_area.getText().toString();
        password = password_area.getText().toString();

        if (email.isEmpty()){
            email_area.setHint("*E-mail is required");
            email_area.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
        if (password.isEmpty()){
            password_area.setHint("*Password is required");
            password_area.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
    }

    private void handleAuthentication() {

        if (!email.isEmpty() && !password.isEmpty()) {
            ApiServices myApi = RetroClient.getApiServices();
            String info = email+":"+password;
            byte[] bytes = Base64.encodeBase64(info.getBytes());
            encodedString = new String(bytes);

            Call<Boolean> call= myApi.isAUser("Basic "+encodedString);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if ("true".equals(response.body().toString())){
                            handleLogin();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Invalid e-mail or password!",Toast.LENGTH_LONG).show();
                            email_area.setText("");
                            password_area.setText("");
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid user!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Request error!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void handleLogin() {

        if (!email.isEmpty() && !password.isEmpty()){

            ApiServices myApi = RetroClient.getApiServices();
            String info = email+":"+password;
            byte[] bytes = Base64.encodeBase64(info.getBytes());
            final String encodedString = new String(bytes);

            Call<TokenObject> call= myApi.loginIsSuccess("Basic "+encodedString);
            call.enqueue(new Callback<TokenObject>() {
                @Override
                public void onResponse(Call<TokenObject> call, Response<TokenObject> response) {
                    if (response.isSuccessful()){
                        if(response.body().getUserEmail().contains(email)){ //login is success
                            Intent todosIntent = new Intent(getApplicationContext(),ToDosActivity.class);
                            todosIntent.putExtra("encodeduserinfo",encodedString);
                            startActivity(todosIntent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Invalid e-mail or password!",Toast.LENGTH_LONG).show();
                            email_area.setText("");
                            password_area.setText("");
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid request!",Toast.LENGTH_LONG).show();
                        email_area.setText("");
                        password_area.setText("");
                    }
                }

                @Override
                public void onFailure(Call<TokenObject> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Request error!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
