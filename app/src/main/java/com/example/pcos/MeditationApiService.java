package com.example.pcos;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MeditationApiService {
    @FormUrlEncoded
    @POST("meditation.php")
    Call<MeditationResponse> fetchMeditationAudio(@Field("category") String category);
}
