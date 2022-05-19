package com.example.retrofitexample.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    //calling the api service without the need to instantiate the object

    /**
     https://rickandmortyapi.com/api/character/?page=1
     The retrofit builder will need a base url so we extract that
     from our link and create the base url variable of type String
     */

    private val BASE_URL = "https://rickandmortyapi.com/api/"

    //creating a variable for the moshi builder, adding a converter to it

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    //create an instance of retrofit by lazy so it is only initialized when it is needed.
    //we will pass the base URL and the moshi variables created above

    private val retrofit: Retrofit by lazy{
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }


        //this will allow us to use the ApiService interface to get the characters.
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}

/**
 Below the object class I create an interface to define how Retrofit talks to the service using the @GET method.
 */

//An interface called ApiService
interface ApiService {
    /**
     I then create a fetchCharacters method annotated with @GET passing the character path just like in our api link above
     to tell the server send us those characters.
     */
    @GET("character")
    fun fetchCharacters(@Query("page") page:String): Call<CharacterResponse>

}