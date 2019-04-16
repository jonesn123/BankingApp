package com.hyunyong.myapplication.viewModel;

import com.hyunyong.myapplication.repository.NetworkWorker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainViewModel extends ViewModel {

    private WorkManager workManager = WorkManager.getInstance();
    private OneTimeWorkRequest getRecipe = new OneTimeWorkRequest.Builder(NetworkWorker.class).build();
    public LiveData<WorkInfo> getMutableLiveData() {
        return workManager.getWorkInfoByIdLiveData(getRecipe.getId());
    }

    public void fetchFromNetwork() {
        WorkManager.getInstance().enqueue(getRecipe);
    }
}
