package com.todoapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.todoapp.Helper.RetroClient;
import com.todoapp.Models.UserCreateModel;
import com.todoapp.Models.UserResponseModel;
import com.todoapp.R;
import com.todoapp.Services.ApiServices;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In_Activity extends AppCompatActivity {

    private EditText nameTextArea;
    private EditText surnameTextArea;
    private EditText emailTextArea;
    private EditText passwordTextArea;
    private Button signInButton;
    private String name="";
    private String surname="";
    private String email="";
    private String password="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in_);
        initViews();
        setClickListener();
    }

    private void initViews() {
        nameTextArea = findViewById(R.id.name_text_area);
        surnameTextArea = findViewById(R.id.surname_text_area);
        emailTextArea = findViewById(R.id.email_text_area);
        passwordTextArea = findViewById(R.id.password_text_area);
        signInButton = findViewById(R.id.signButton);
    }

    private void setClickListener(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEdits();
                if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    handleSignRequest();
                }
            }
        });
    }


    private void checkEdits() {
        name = nameTextArea.getText().toString();
        surname = surnameTextArea.getText().toString();
        email = emailTextArea.getText().toString();
        password = passwordTextArea.getText().toString();

        if (name.isEmpty()){
            nameTextArea.setHint("*Name is required");
            nameTextArea.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
        if (surname.isEmpty()){
            surnameTextArea.setHint("*Surname is required");
            surnameTextArea.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
        if (email.isEmpty()){
            emailTextArea.setHint("*Email is required");
            emailTextArea.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
        if (password.isEmpty()){
            passwordTextArea.setHint("*Password is required");
            passwordTextArea.setHintTextColor(getResources().getColor(R.color.dark_red));
        }
    }

    private void handleSignRequest() {

        String fullName=name+" "+surname;

        ApiServices myApi = RetroClient.getApiServices();
        UserCreateModel userCreateModel = new UserCreateModel();
        userCreateModel.setUserFullName(fullName);
        userCreateModel.setUserPassword(password);
        userCreateModel.setUserEmail(email);
        retrofit2.Call<UserResponseModel> call = myApi.createNewUser(userCreateModel);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<UserResponseModel> call, Response<UserResponseModel> response) {

                if (response.isSuccessful()){
                    if (response.body().getUserId() == 0){
                        Toast.makeText(getApplicationContext(),"Invalid sign in!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent loginIntent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(loginIntent);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid request!",Toast.LENGTH_LONG).show();
                    emailTextArea.setText("");
                    passwordTextArea.setText("");
                    nameTextArea.setText("");
                    surnameTextArea.setText("");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Request error!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
