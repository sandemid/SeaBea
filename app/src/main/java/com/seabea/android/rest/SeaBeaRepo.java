package com.seabea.android.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeaBeaRepo {
    private static SeaBeaRepo singleton = null;
    private IOSeaBea API;

    private SeaBeaRepo() {
        API = createAdapter();
    }

    public static SeaBeaRepo getSingleton() {
        if(singleton == null) {
            singleton = new SeaBeaRepo();
        }

        return singleton;
    }

    public IOSeaBea getAPI() {
        return API;
    }

    private IOSeaBea createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(IOSeaBea.class);
    }
}
