package com.example.simple_weather.util;

import static com.example.simple_weather.util.Constant.API_KEY_OPENMAPWEARGER;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.simple_weather.model.searchview_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class city_finder {

    MutableLiveData<List<searchview_model>> liveData = new MutableLiveData<>();

    public city_finder() {
    }

    public LiveData<List<searchview_model>> call_request(String text) {


        List<searchview_model> list = new ArrayList<>();

        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + text + "&limit=20&appid=" + API_KEY_OPENMAPWEARGER;

        Request request = new Request.Builder().url(url).build();
        Log.d("TAG", "call_request: " + url);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("TAG", "onResponse: " + "ok");
                try {
                    JSONArray jsonArray1 = new JSONArray(response.body().string());
                    for (int i = 0; jsonArray1.length() > i; i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        list.add(new searchview_model(jsonObject.getString("name"), change(jsonObject.getString("country"))));
                    }

                    liveData.postValue(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return liveData;
    }

    public String change(String code) {
        Locale locale = new Locale("en", code);
        return locale.getDisplayCountry(Locale.US);
    }

}
