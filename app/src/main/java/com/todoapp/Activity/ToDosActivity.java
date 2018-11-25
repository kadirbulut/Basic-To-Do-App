package com.todoapp.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.todoapp.Adapter.RecyclerAdapter;
import com.todoapp.Helper.RetroClient;
import com.todoapp.Models.ProjectObjectModel;
import com.todoapp.Models.ProjectResponseModel;
import com.todoapp.R;
import com.todoapp.Services.ApiServices;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ToDosActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerAdapter itemAdapter;
    private Button addAToDoButton;
    private String encodedString="";
    private ApiServices myApi = RetroClient.getApiServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_dos);
        Bundle extras = getIntent().getExtras();
        encodedString = extras.getString("encodeduserinfo");
        initViews();
        setClickListeners();
        getProjectsList();
    }

    private void initViews() {
        addAToDoButton = findViewById(R.id.add_todo_button);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void updateRecyclerView(ArrayList<ProjectResponseModel> list){
        for (int i=0; i<5;i++)
            list.remove(0);
        itemAdapter = new RecyclerAdapter(this,list,encodedString);
        recyclerView.setAdapter(itemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setClickListeners(){
        addAToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddingDialog();
            }
        });
    }



    private void showAddingDialog() {
        final Dialog dialog = new Dialog(ToDosActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.adding_new_todo_dialog);
        dialog.setTitle("New To-Do");
        Button adding = dialog.findViewById(R.id.accept_button);
        Button canceling = dialog.findViewById(R.id.cancel_button);
        adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText content_area = dialog.findViewById(R.id.content_area);
                String content = content_area.getText().toString();
                if (content.length() <=3 ){
                    content_area.setHint("Too short to-do name!");
                    content_area.setHintTextColor(getResources().getColor(R.color.dark_red));
                }
                else{
                    addANewToDo(content);
                    getProjectsList();
                    dialog.dismiss();
                }
            }
        });
        canceling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addANewToDo(String content) {
        ProjectObjectModel model = new ProjectObjectModel();
        model.setContent(content);
        model.setIcon(1);

        Call<ProjectResponseModel> call = myApi.createANewToDo("Basic "+encodedString,model);
        call.enqueue(new Callback<ProjectResponseModel>() {
            @Override
            public void onResponse(Call<ProjectResponseModel> call, Response<ProjectResponseModel> response) {
                if (response.isSuccessful()){
                    getProjectsList();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid adding!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Request error!",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getProjectsList(){
        Call<List<ProjectResponseModel>> call = myApi.getAllProjects("Basic "+encodedString);
        call.enqueue(new Callback<List<ProjectResponseModel>>() {
            @Override
            public void onResponse(Call<List<ProjectResponseModel>> call, Response<List<ProjectResponseModel>> response) {
                if (response.isSuccessful()){
                    ArrayList<ProjectResponseModel> list = new ArrayList<>();
                    for (ProjectResponseModel model : response.body()){
                        System.out.println("----->"+model.getContent());
                        list.add(model);
                    }
                    updateRecyclerView(list);
                }
            }

            @Override
            public void onFailure(Call<List<ProjectResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Request error!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ToDosActivity.this);
        dialog.setTitle("LOGOUT");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(ToDosActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertdialog = dialog.create();
        alertdialog.show();

    }
}
