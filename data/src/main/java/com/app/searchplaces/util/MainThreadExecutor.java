package com.app.searchplaces.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

// Executes Runnable tasks on the UI thread
public class MainThreadExecutor implements Executor {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        mHandler.post(command);
    }

}