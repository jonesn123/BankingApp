package com.hyunyong.myapplication.repository;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.dao.RecipeDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkWorker extends Worker {

    private Context mContext;

    public NetworkWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        mContext = context;
    }

    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode json = mapper.readTree(result);

            AppDataBase dataBase = AppDataBase.getDatabase(mContext);
            RecipeDao dao = dataBase.recipeDao();
            if (json.isArray()) {
                for (final Iterator<JsonNode> i = json.elements(); i.hasNext(); ) {
                    final JsonNode jsonNode = i.next();

                    // insert DAO
                    Recipe recipe = new Gson().fromJson(jsonNode.toString(), Recipe.class);
                    dao.insert(recipe);

                }
            }

        } catch (IOException e) {
            e.toString();
        }

        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }
}