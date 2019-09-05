package com.eleganzit.workmanagerexample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyInterface {


    @FormUrlEncoded
    @POST("Instapure/instapure-api/imageFeedlist")
    Call<ImageFeedlistResponse> imageFeedlist(
            @Field("from_limit") String     from_limit,
            @Field("image_type") String image_type


    ); @FormUrlEncoded
    @POST("Instapure/instapure-api/videoFeedlist")
    Call<VideoFeedlistResponse> videoFeedlist(
            @Field("from_limit") String     from_limit,
            @Field("video_type") String video_type,
            @Field("to_limit") String to_limit


    );
}
