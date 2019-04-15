package com.hyunyong.myapplication.repository;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkWorker extends Worker {

    public NetworkWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                .build();

        Data.Builder builder = new Data.Builder();
        try {
            Response response = client.newCall(request).execute();

            String res = response.body().string();
            Log.e("tag", res);

            builder.putString("result", "abc");

        } catch (IOException e) {

        }

        // Indicate whether the task finished successfully with the Result
        return Result.success(builder.build());
    }
}