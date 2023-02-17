package com.example.poke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.poke.model.Pokemon;
import com.example.poke.model.PokemonM2;
import com.example.poke.pokeapi.PokemonSin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button button;

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    TextView namePoke;
    ImageView imgPoke;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);;

        namePoke=findViewById(R.id.namePoke);
        imgPoke=findViewById(R.id.imgPoke);
        button=findViewById(R.id.encontrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cadena = String.valueOf(nuemroAzar());
                buscarPoke(cadena);

            }

        });


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pokedex:
                        startActivities(new Intent[]{new Intent(getApplicationContext(), MainActivity.class)});
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:

                        return true;

                }
                return false;
            }
        });


    }

    private void buscarPoke(String numero){
        Retrofit retrofit1=new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PokemonSin pokemonSin=retrofit1.create(PokemonSin.class);
        Call<PokemonM2> call=pokemonSin.find(numero);
        call.enqueue(new Callback<PokemonM2>() {
            @Override
            public void onResponse(Call<PokemonM2> call, Response<PokemonM2> response) {
                if (response.isSuccessful()){
                    PokemonM2 p=response.body();
                    String URL_IMG="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+numero+".png";
                    namePoke.setText(p.getName());
                    Glide.with(getApplication()).load(URL_IMG).into(imgPoke);
                    Log.e(TAG, "Nombreee"+p.getName());
                    Log.e(TAG, "Numeroooo"+numero);


                }else {
                    Log.e(TAG, "vacio"+response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<PokemonM2> call, Throwable t) {

                Log.e(TAG,"nose: "+t.getMessage());

            }
        });
    }


    private int nuemroAzar(){
        Random random = new Random();

        int numeroRandom = random.nextInt(1007+1);
        return numeroRandom;
    }
}