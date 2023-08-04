package com.example.keepoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.keepoapp.R;
import com.example.keepoapp.preferences.SharedPreferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreenActivity extends AppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setCountdownSplashScreen();
    }
    private void setCountdownSplashScreen(){
        disposables.add(waitForNextActivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<String>(){
                @Override
                public void onNext(@NonNull String s) {

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    Log.d("Intent", "Intent Process is here!");
                    SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(SplashScreenActivity.this);
                    if (sharedPreferenceManager.isInitialized()){
                        goToNextActivity(HomeActivity.class);
                    }
                    else{
                        goToNextActivity(LoginActivity.class);
                    }
                    finish();
                }
            }));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
    void goToNextActivity(Class activity){
        Intent intent = new Intent(SplashScreenActivity.this, activity);
        startActivity(intent);
    }
    Observable<String> waitForNextActivity(){
        return io.reactivex.rxjava3.core.Observable.defer((Supplier<ObservableSource<String>>) () -> {
            Thread.sleep(1500);
            return Observable.just("one");
        });
    }
}