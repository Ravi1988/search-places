package com.app.searchplaces.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.MapView;

/**
 * Intercepts the touch events for smooth scroll inside scrollable container
 */
public class CustomMapView extends MapView {

public CustomMapView(Context context, AttributeSet attrs) {
    super(context, attrs);
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
    case MotionEvent.ACTION_UP:
        this.getParent().requestDisallowInterceptTouchEvent(false);
        break;
    case MotionEvent.ACTION_DOWN:
        this.getParent().requestDisallowInterceptTouchEvent(true);
        break;
    }
    return super.dispatchTouchEvent(ev);
}} 