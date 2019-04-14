package com.hyunyong.myapplication.viewModel;

import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.repository.NetworkWorker;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> mutableLiveData;
    public LiveData<List<Recipe>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getRecipeFromNetwork() {
        OneTimeWorkRequest getRecipe = new OneTimeWorkRequest.Builder(NetworkWorker.class).build();
        WorkManager.getInstance().enqueue(getRecipe);
    }
}
