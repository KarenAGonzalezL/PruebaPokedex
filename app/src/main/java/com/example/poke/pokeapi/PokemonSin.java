package com.example.poke.pokeapi;


import com.example.poke.model.PokemonM2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonSin {
    @GET("pokemon/{id}")
    public Call<PokemonM2> find(@Path("id") String id);
}
