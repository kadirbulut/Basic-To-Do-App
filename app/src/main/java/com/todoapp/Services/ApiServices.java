package com.todoapp.Services;

import com.todoapp.Models.TokenObject;
import com.todoapp.Models.UserCreateModel;
import com.todoapp.Models.ProjectResponseModel;
import com.todoapp.Models.UserResponseModel;
import com.todoapp.Models.ProjectObjectModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {

    //api service for user : is authenticated
    @GET("authentication/isauthenticated.json")
    Call<Boolean> isAUser(@Header("Authorization") String token);

    //api service for basic token authorization
    @GET("authentication/token.json")
    Call<TokenObject> loginIsSuccess(@Header("Authorization") String token);

    //api service for creating a new user
    @POST("user.json")
    Call<UserResponseModel> createNewUser(@Body UserCreateModel userCreateModel);

    //api service for creating a new project
    @POST("projects.json")
    Call<ProjectResponseModel> createANewToDo(@Header("Authorization") String token,@Body ProjectObjectModel projectObjectModel);

    //api service for getting a user info
    @GET("user.json")
    Call<UserResponseModel> getAUser(@Header("Authorization") String token);

    //api service for getting list of all projects
    @GET("projects.json")
    Call<List<ProjectResponseModel>> getAllProjects(@Header("Authorization") String token);

    //delete the project with given id
    @DELETE("projects/{id}.json")
    Call<ProjectResponseModel> removeAProject(@Header("Authorization") String token, @Path("id")int itemId);

    //update the project
    @PUT("projects/{id}.json")
    Call<ProjectResponseModel> updateTheProject(@Header("Authorization") String token,@Path("id")int itemId,@Body ProjectObjectModel projectObjectModel);

}