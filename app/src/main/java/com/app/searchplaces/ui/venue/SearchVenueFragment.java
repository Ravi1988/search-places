package com.app.searchplaces.ui.venue;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.searchplaces.R;
import com.app.searchplaces.databinding.SearchListViewBinding;
import com.app.searchplaces.ui.venue.adapter.VenueAdapter;
import com.app.searchplaces.ui.base.BaseMVVMFragment;
import com.app.searchplaces.ui.venue.vm.VenueViewModel;
import com.app.searchplaces.util.APIParams;
import com.app.searchplaces.util.AppConstant;
import com.app.searchplaces.util.AppPreferences;
import java.util.HashSet;
import javax.inject.Inject;

import static com.app.searchplaces.util.AppConstant.favMap;

/**
 * Responsible for providing search interface for venue search.
 * Requests the server via {@link VenueViewModel} class and shows the response in a {@link RecyclerView}
 */
public class SearchVenueFragment extends BaseMVVMFragment<VenueViewModel> {

    @Inject
    VenueAdapter venueAdapter;

    @Inject
    VenueViewModel venueViewModel;

    @Inject
    AppPreferences appPreferences;

    private SearchListViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnableSearch(true); // enable search
        setHasOptionsMenu(true); // enable search option menu
        loadFavourite();//Initialize favourite map if not initialized
    }

    /**
     * @return view model for this fragment
     */
    @Override
    protected VenueViewModel createViewModel() {
        return venueViewModel;
    }

    /**
     * @return fragment layout id
     */
    @Override
    public int getFragmentLayout() {
        return R.layout.search_list_view;
    }

    /**
     * @param view      , View attached to this fragment
     * @param viewModel , view model attached to this fragment
     */
    @Override
    public void onViewModelCreated(View view, VenueViewModel viewModel) {
        binding = (SearchListViewBinding) getDataBinding();
        setToolbarView(binding.toolbarLay.toolbar);
        setupRecyclerView();
        observeSearchSuggetion();
        observeSearchResult();
        getViewModel().configureAutoSearch();
    }

    /**
     * Init RecyclerView with adapter and manager
     */
    private void setupRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(venueAdapter);
    }

    /**
     * @param msg to display
     */
    @Override
    protected void updateViewForNoData(String msg) {
        super.updateViewForNoData(msg);
        showNoDataView();
    }

    /**
     * Disable RecyclerView and show empty view
     */
    private void showNoDataView() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
    }

    /**
     * Enable RecyclerView and hide empty view
     */
    private void showRecyclerView() {
        binding.emptyView.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Adds observer to venue search result live data
     * and submits the list to adapter
     */
    private void observeSearchResult() {
        getViewModel().getVenueList().observe(this, venues -> {
            showRecyclerView();
            venueAdapter.submitList(venues);
        });
    }

    /**
     * @param newText text of input field as user types in
     */
    @Override
    protected void onQueryChange(String newText) {
        super.onQueryChange(newText);
        getViewModel().onQueryChange(newText);
    }

    /**
     * @param query Query to be submitted
     */
    @Override
    protected void onQuerySubmit(String query) {
        super.onQuerySubmit(query);
        searchPlaces(query);
    }

    /**
     * Makes the call to API via view model and prepares the data source for paging adapter
     * @param query query to be searched
     */
    private void searchPlaces(String query) {
        // Initialize Data Source
        VenueDataSource dataSource = new VenueDataSource(APIParams.getPlacesRequestParams(query),getViewModel());
        getViewModel().searchForVenues(dataSource);
    }


    /**
     * Adds observer to search suggetion live data
     */
    private void observeSearchSuggetion(){
        getViewModel().getSearchSuggestion().observe(this,
                venues -> {
            getSearchView().getSuggestionsAdapter().
                        changeCursor(getViewModel().getCursor(venues));
                    getSearchView().getSuggestionsAdapter().notifyDataSetChanged();
                });

    }

    /**
     * Loads the preference data for favourite
     */
    private void loadFavourite() {
        if(favMap == null) {
            favMap = new HashSet<>(appPreferences.
                    getStringSet(AppConstant.KEY_FAV_IDS, new HashSet<>()));
        }
    }

}
