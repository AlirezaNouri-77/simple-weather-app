package com.example.weathertest.util;

import android.content.Context;

import com.example.weathertest.model.searchview_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class local_json_city {

    Context context;

    public local_json_city(Context context) {
        this.context = context;
    }

    public String loadJSONFromAsset() {

        String json;

        try {

            InputStream is = context.getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

}
