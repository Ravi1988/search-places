package com.app.searchplaces.ui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.app.searchplaces.events.EventConstant;
import com.app.searchplaces.events.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Base class for all view models.
 * Responsible for error handling, retry and UI events.
 * <p>This extends {@link ViewModel} class
 */
public abstract class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();//Holds reference of all disposable
    private PublishSubject<Object> retrySubject = PublishSubject.create();
    /**
     * @param msg To be displayed on UI
     */
    protected final void showError(@NonNull String msg) {
        EventBus.getDefault().post(new MessageEvent(EventConstant.SNACKBAR_WITH_RETRY,msg));
    }

    /**
     * @param msg To be displayed on UI
     */
    protected final void showInfoMsg(@NonNull String msg) {
        EventBus.getDefault().post(new MessageEvent(EventConstant.INFO_SNACK_BAR,msg));
    }

    /**
     * Shows progress bar
     */
    protected final void showLoader() {
        EventBus.getDefault().post(new MessageEvent(EventConstant.SHOW_HIDE_LOADER,true));
    }

    /**
     * Hides progress bar
     */
    protected final void hideLoader() {
        EventBus.getDefault().post(new MessageEvent(EventConstant.SHOW_HIDE_LOADER,false));
    }

    /**
     * Handle error from API
     */
    protected void dispatchOnFailure(@NonNull  Throwable throwable) {
        hideLoader();
        showError(throwable.getMessage());
    }

    /**
     * Add disposable to composite disposable
     * @param disposable disposable returned from subscription
     */
    public void addDisposable(@NonNull Disposable disposable){
        compositeDisposable.add(disposable);
    }

    /**
     * @param observable {@link Observable} of type  {@link Throwable}
     * @return retry subject
     */
    protected ObservableSource<?> getRetrySubject(@NonNull Observable<Throwable> observable) {
        return observable.zipWith(retrySubject, (o, o2) -> o);
    }

    /**
     * Call to retry API
     */
    public void retryNow(){
        showLoader();
        //initiate retry
        retrySubject.onNext(new Object());
    }

    /**
     * Called when view model is being destroyed
     *
     * Clear memory here
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        retrySubject = null;
        compositeDisposable.dispose();
    }
}
