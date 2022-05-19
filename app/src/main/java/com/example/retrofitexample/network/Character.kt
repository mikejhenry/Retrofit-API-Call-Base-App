package com.example.retrofitexample.network

//this data will use JSON data

import com.squareup.moshi.Json

//an object blueprint to hold individual character data
data class Character (
    @Json(name="name")
    val name: String,
    @Json(name="image")
    val image: String,
        )

//an object blueprint to hold a list of results from the API Server response
data class CharacterResponse(@Json(name="results")
    val result: List<Character>
)