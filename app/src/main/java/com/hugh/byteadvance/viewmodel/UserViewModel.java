package com.hugh.byteadvance.viewmodel;

import android.util.Log;

import com.hugh.byteadvance.databing.UserBean;
import com.hugh.byteadvance.viewmodel.schedulers.BaseSchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenyw on 2020/9/16.
 */
public class UserViewModel extends ViewModel {
    private MutableLiveData<List<UserBean>> users;
    private Disposable mTimeLimitDisposable;
    protected BaseSchedulerProvider mSchedulerProvider;
    private MutableLiveData<String> currentName; //定义String变量

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

    public UserViewModel() {
        mSchedulerProvider = new BaseSchedulerProvider() {
            @NonNull
            @Override
            public Scheduler computation() {
                return Schedulers.trampoline();
            }

            @NonNull
            @Override
            public Scheduler io() {
                return Schedulers.trampoline();
            }

            @NonNull
            @Override
            public Scheduler ui() {
                return Schedulers.trampoline();
            }
        };
    }

    public LiveData<List<UserBean>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<UserBean>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        //通过延时操作模仿网络请求
        if (mTimeLimitDisposable != null) {
            mTimeLimitDisposable.dispose();
        }
        mTimeLimitDisposable = Flowable.intervalRange(0, 3000, 0, 1, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.computation())
                .doOnError(throwable -> {

                })
                .doOnComplete(this::getUserDataDone)
                .subscribe();
    }

    private void getUserDataDone(){
        Log.e("aaa","getUserDataDone");
        UserBean userBean = new UserBean("第一个名字","第二个名字");

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mTimeLimitDisposable != null) {
            mTimeLimitDisposable.dispose();
        }
    }
}
