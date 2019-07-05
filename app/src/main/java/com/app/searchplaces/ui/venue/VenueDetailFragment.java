package com.app.searchplaces.ui.venue;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.app.searchplaces.BuildConfig;
import com.app.searchplaces.R;
import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.databinding.VenueDetailViewBinding;
import com.app.searchplaces.events.EventConstant;
import com.app.searchplaces.events.MessageEvent;
import com.app.searchplaces.ui.map.MapViewFragment;
import com.app.searchplaces.ui.base.BaseMVVMFragment;
import com.app.searchplaces.ui.venue.vm.VenueDetailViewModel;
import com.app.searchplaces.util.AppConstant;
import com.app.searchplaces.util.CommonUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;


/**
 * Responsible to show venue detail and venue location on map
 */
public class VenueDetailFragment extends BaseMVVMFragment<VenueDetailViewModel> {

    @Inject
    VenueDetailViewModel venueDetailViewModel;

    /**
     * @return view model object for this fragment
     */
    @Override
    protected VenueDetailViewModel createViewModel() {
        return venueDetailViewModel;
    }

    /**
     * @return layout id
     */
    @Override
    public int getFragmentLayout() {
        return R.layout.venue_detail_view;
    }

    /**
     * @param view      , View attached to this fragment
     * @param viewModel , view model attached to this fragment
     */
    @Override
    public void onViewModelCreated(View view, VenueDetailViewModel viewModel) {
        VenueDetailViewBinding binding  = (VenueDetailViewBinding) getDataBinding();
        setupToolbar(binding);
    }

    /**
     * Sets the toolbar view to base fragment
     * @param binding data binding object
     */
    private void setupToolbar(VenueDetailViewBinding binding) {
        setToolbarView(binding.appbarLay.toolbarLay.toolbar);
        setTitle(getString(R.string.venue_detail),true);
    }

    /**
     * Handle events that are not being handled by super class
     * @param event event from EventBus
     */
    public void onBusEvent(MessageEvent event) {
        super.onBusEvent(event);
        if (event.eventID == EventConstant.VENUE_ITEM) {
            if(event.message == null){
                handleNoData();
            }else{
                viewModel.setLiveDataVenue((Venue) event.message);
                initMapView((Venue) event.message);
            }

            EventBus.getDefault().removeStickyEvent(event);//Removed so that will not get trigger again
        }
    }

    /**
     * Go back if there is no venue detail is available
     */
    private void handleNoData() {
        CommonUtil.showToast(getActivity(),getResources().getString(R.string.no_details));
        if(getView() != null){
            Navigation.findNavController(getView()).popBackStack();
        }
    }

    /**
     * adds the map fragment to map container
     * @param venue venue object of type {@link Venue}
     */
    private void initMapView(Venue venue) {
        Bundle bundle = new Bundle();
        bundle.putDouble(AppConstant.KEY_SRC_LAT, BuildConfig.DEFAULT_LAT);
        bundle.putDouble(AppConstant.KEY_SRC_LNG, BuildConfig.DEFAULT_LONG);
        bundle.putString(AppConstant.KEY_SRC_TITLE, getString(R.string.seattle));
        if(venue != null && venue.getLocation() != null) {
            bundle.putDouble(AppConstant.KEY_DES_LAT, venue.getLocation().getLat());
            bundle.putDouble(AppConstant.KEY_DES_LNG, venue.getLocation().getLng());
            bundle.putString(AppConstant.KEY_DES_TITLE, venue.getName());
        }
        addFragmentToScreen(getChildFragmentManager(), new MapViewFragment() ,R.id.mapContainer, bundle);

    }

}
