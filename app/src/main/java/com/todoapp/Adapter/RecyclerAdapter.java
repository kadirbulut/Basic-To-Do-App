package com.todoapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.todoapp.Activity.ToDosActivity;
import com.todoapp.Helper.RetroClient;
import com.todoapp.Models.ProjectObjectModel;
import com.todoapp.Models.ProjectResponseModel;
import com.todoapp.R;
import com.todoapp.Services.ApiServices;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<ProjectResponseModel> projects;
    LayoutInflater inflater;
    Context context;
    private String encodedString="";

    public RecyclerAdapter(Context context, ArrayList<ProjectResponseModel> projects,String encodedString) {
        inflater = LayoutInflater.from(context);
        this.projects = projects;
        this.context=context;
        this.encodedString=encodedString;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProjectResponseModel selectedProduct = projects.get(position);
        holder.setData(selectedProduct, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Do you want to remove this to-do?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        updateToDo(selectedProduct.getId(),selectedProduct);
                    }
                });
                dialog.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeToDo(selectedProduct.getId(),selectedProduct);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertdialog = dialog.create();
                alertdialog.show();
            }
        });

    }

    private void updateToDo(final int id, final ProjectResponseModel selectedProduct) {

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("UPDATE THE TO-DO");
        dialog.setContentView(R.layout.adding_new_todo_dialog);
        dialog.setTitle("Update To-Do");
        Button updating = dialog.findViewById(R.id.accept_button);
        Button canceling = dialog.findViewById(R.id.cancel_button);
        final EditText content_area = dialog.findViewById(R.id.content_area);
        updating.setText("UPDATE");
        content_area.setText(selectedProduct.getContent());
        updating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!content_area.getText().toString().equals(selectedProduct.getContent())){
                    sendUpdateRequest(id,content_area.getText().toString());
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

    private void sendUpdateRequest(int id,String newContent) {
        ApiServices myApi = RetroClient.getApiServices();
        ProjectObjectModel model = new ProjectObjectModel();
        model.setContent(newContent);
        Call<ProjectResponseModel> call = myApi.updateTheProject("Basic "+encodedString,id,model);
        call.enqueue(new Callback<ProjectResponseModel>() {
            @Override
            public void onResponse(Call<ProjectResponseModel> call, Response<ProjectResponseModel> response) {
                if (response.isSuccessful()){
                    ((ToDosActivity)context).getProjectsList();
                }
                else{
                    Toast.makeText(context,"Invalid updating!",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ProjectResponseModel> call, Throwable t) {
                Toast.makeText(context,"Request error!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeToDo(int projectId, final ProjectResponseModel model) {
        ApiServices myApi = RetroClient.getApiServices();
        Call<ProjectResponseModel> call = myApi.removeAProject("Basic "+encodedString,projectId);
        call.enqueue(new Callback<ProjectResponseModel>() {
            @Override
            public void onResponse(Call<ProjectResponseModel> call, Response<ProjectResponseModel> response) {
                if (response.isSuccessful()){
                    ((ToDosActivity)context).getProjectsList();
                }
                else {
                    Toast.makeText(context,"Invalid removing!",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ProjectResponseModel> call, Throwable t) {
                Toast.makeText(context,"Request error!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    private TextView itemContent;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(View itemView) {
            super(itemView);
            itemContent = (TextView) itemView.findViewById(R.id.item_content);
        }

        public void setData(ProjectResponseModel selectedProduct, int position) {
            itemContent.setText(selectedProduct.getContent());
        }
        @Override
        public void onClick(View v) {
        }

    }

}