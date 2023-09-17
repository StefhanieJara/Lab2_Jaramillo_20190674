package com.example.lab2_jaramillo_20190674.services;

import android.provider.ContactsContract;

import com.example.lab2_jaramillo_20190674.dto.Profile;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUser {
    @GET("/api")
    Call<Profile> getResults();
}
