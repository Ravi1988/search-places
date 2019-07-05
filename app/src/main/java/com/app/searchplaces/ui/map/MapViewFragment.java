package com.app.searchplaces.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.app.searchplaces.R;
import com.app.searchplaces.databinding.MapViewBinding;
import com.app.searchplaces.ui.base.BaseMVVMFragment;
import com.app.searchplaces.ui.base.BaseViewModel;
import com.app.searchplaces.util.AppConstant;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *Responsible for showing source and destination location on map with markers
 */
public class MapViewFragment extends BaseMVVMFragment implements OnMapReadyCallback {

    private GoogleMap mMap = null;
    private double srcLat,desLat,srcLng,desLng;
    private String srcTitle="",desTitle="";
    private MapViewBinding binding ;
    private LatLngBounds.Builder builder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GET VALUES FROM ARGUMENTS.
        if(getArguments() != null){
            srcLat = getArguments().getDouble(AppConstant.KEY_SRC_LAT);
            srcLng = getArguments().getDouble(AppConstant.KEY_SRC_LNG);
            desLat = getArguments().getDouble(AppConstant.KEY_DES_LAT);
            desLng = getArguments().getDouble(AppConstant.KEY_DES_LNG);
            srcTitle = getArguments().getString(AppConstant.KEY_SRC_TITLE);
            desTitle = getArguments().getString(AppConstant.KEY_DES_TITLE);
        }
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.map_view;
    }

    @Override
    public void onViewModelCreated(View view, BaseViewModel viewModel) { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = (MapViewBinding) getDataBinding();
        // Gets the MapView from the XML layout and creates it
        binding.map.onCreate(savedInstanceState);
        binding.map.getMapAsync(this);//load map

    }



    @Override
    public void onResume() {
        binding.map.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.map.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //map is ready to use
        this.mMap = googleMap;
        LatLng srcPoint = new LatLng(srcLat, srcLng);
        LatLng desPoint = new LatLng(desLat, desLng);
        builder = new LatLngBounds.Builder();// required for zoom
        setupMarker(srcPoint,srcTitle);// add marker to map
        setupMarker(desPoint,desTitle);// add marker to map
        setupMapControlButtons();// setup map button
        zoomToInitialPosition();//zoom camera to show both markers
    }


    /**
     * Sets the correct zoom value and animates
     */
    private void zoomToInitialPosition() {
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20); // offset from edges of the map 20% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    private void setupMarker(@NonNull LatLng markerPoint, String title) {
        Marker mMarker = this.mMap.addMarker(
                new MarkerOptions()
                        .position(markerPoint)
                        .title(title)
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_marker))
        );
        builder.include(mMarker.getPosition());// add marker position to builder
    }

    /**
     * Setup map controllers zoom in, zoom out, initial position
     */
    private void setupMapControlButtons() {
        MapViewBinding binding = (MapViewBinding) getDataBinding();
        binding.location.setOnClickListener(view -> zoomToInitialPosition());
        binding.zoomIn.setOnClickListener(view -> mMap.animateCamera(CameraUpdateFactory.zoomIn()));
        binding.zoomOut.setOnClickListener(view -> mMap.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    /**
     * Converts the vector assets to bitmap for map marker
     * @param context context
     * @param vectorResId vector asset res id
     * @return bitmap descriptor of type {@link BitmapDescriptor}
     */
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
