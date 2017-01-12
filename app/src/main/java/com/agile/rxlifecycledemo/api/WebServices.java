package com.agile.rxlifecycledemo.api;

import com.agile.rxlifecycledemo.posts.PostModel;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by agile-01 on 8/17/2016.
 */
public interface WebServices {

    @GET("posts")
    Observable<List<PostModel>> getPosts();

    @FormUrlEncoded
    @POST("posts")
    Observable<PostModel> addPost(
            @Field("userId") long userId,
            @Field("title") String title,
            @Field("body") String body
    );

    @DELETE("posts/{id}")
    Observable<PostModel> deletePost(
            @Path("id") long postId
    );

}
