package com.app.searchplaces.data.api.Rx;

import io.reactivex.Scheduler;


/**
 * Interface to provide Rx schedulers
 */
public interface RxSchedulers {

  Scheduler androidUI();

  Scheduler io();

  Scheduler computation();

  Scheduler immediate();
}