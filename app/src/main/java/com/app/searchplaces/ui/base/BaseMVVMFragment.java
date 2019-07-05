package com.app.searchplaces.ui.base;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.app.searchplaces.BR;
import com.app.searchplaces.R;
import com.app.searchplaces.events.EventConstant;
import com.app.searchplaces.events.MessageEvent;
import com.app.searchplaces.util.CommonUtil;
import com.google.android.material.snackbar.Snackbar;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import dagger.android.support.DaggerFragment;


/**
 * Base class for all View Model Fragments. Responsible for data biding and view model binding.
 * <p>This extends {@link DaggerFragment} class for auto injection.
 * @param <T> , View Model class which extends BaseViewModel.class.
 */
public abstract class BaseMVVMFragment<T extends BaseViewModel> extends DaggerFragment {

    protected T viewModel;
    private ViewDataBinding dataBinding;
    private SearchView search;
    private boolean enableSearch = false;
    private Toolbar toolbar;
    private static ProgressDialog progressDialog;

    public BaseMVVMFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);// register event bus
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);// unregister event bus
        super.onStop();
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getContentView(inflater, container, savedInstanceState);
    }

    /**
     * Super class method, called when fragment is being created
     * No child can override this method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    private View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater,
                getFragmentLayout(), container, false);
        viewModel = createViewModel();
        dataBinding.setLifecycleOwner(this);
        if(viewModel != null){
            dataBinding.setVariable(BR.vm,viewModel);
        }
        return dataBinding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(getActivity() == null){
            return;
        }
        inflater.inflate(R.menu.menu, menu);
        if(enableSearch){
            inflateSearch(menu);
        }

        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
    }

    /**
     * Super class method, called when view is created
     * Load your view model here.
     *
     * @param view : inflated {@link View}
     */
    protected final void initLayout(View view) {
        onViewModelCreated(view, viewModel);
        initProgressDialog();
    }

    /**
     * Initialize progress dialog
     */
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
    }


    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    //sticky true, to receive sticky events
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        onBusEvent(event);
    }

    /**
     * Super class method, called when any event is received
     * @param event event received from event bus {@link EventBus}
     */
    protected void onBusEvent(MessageEvent event) {
        switch (event.eventID){
            case EventConstant.INFO_SNACK_BAR :
                updateViewForNoData((String)event.message);
                break;
            case EventConstant.SHOW_HIDE_LOADER:
                if((boolean)event.message){
                    showProgressbar();
                }else {
                    hideProgressbar();
                }
                break;
            case EventConstant.SNACKBAR_WITH_RETRY:
                showSnackBarWithRetry((String) event.message);
                break;
        }
    }

    /**
     * Override tp get text changes
     * @param newText text of input field as user types in
     */
    protected void onQueryChange(String newText) { }

    /**
     * Override this method to get searched query string
     * @param query Query to be submitted
     */
    protected void onQuerySubmit(String query) {
        if(getActivity() != null ){
            CommonUtil.hideKeyboard(getActivity());
        }
    }

    /**
     * @return viewModel, instance of view model for fragment
     */
    public T getViewModel() {
        return viewModel;
    }

    /**
     * @return dataBinding, data binding object for this view
     */
    public ViewDataBinding getDataBinding(){
        return dataBinding;
    }

    /**
     * Override this method to create view model specific to fragment
     *
     * @return ViewModel
     */
    protected abstract T createViewModel();

    /**
     * Override this method to provide fragment xml view
     *
     * @return layoutId, returns the fragment layout id
     */
    public abstract int getFragmentLayout();

    /**
     * Called after view model is created
     * @param view , View attached to this fragment
     * @param viewModel , view model attached to this fragment
     */
    public abstract void onViewModelCreated(View view, T viewModel);

    /**
     * Super class method, called when event received with INFO_SNACK_BAR
     * Updates the UI for user information
     * @param data message received from event
     */
    protected void updateViewForNoData(String data) {
        showSnackBar((String) data);
    }

    /**
     * Hide progress dialog
     */
    private void hideProgressbar() {
        progressDialog.hide();
    }

    /**
     * Show progress dialog
     */
    private void showProgressbar() {
        progressDialog.show();
    }

    /**
     * Show snackbar with msg
     * @param data msg to display
     */
    private void showSnackBar(String data) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                data, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Show snackbar with retry button to retry API call
     * @param data msg to display
     */
    private void showSnackBarWithRetry(String data) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                data, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), view -> {
                    // Retry the failed request
                    if(getViewModel() != null){
                        getViewModel().retryNow();
                    }
                }).show();
    }

    /**
     * inflates search view
     * @param menu
     */
    private void inflateSearch(Menu menu) {
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (manager != null && search != null) {
            //create and set search adapter for suggestion
            search.setSuggestionsAdapter(new SimpleCursorAdapter(
                    getActivity(), android.R.layout.simple_list_item_1, null,
                    new String[] { SearchManager.SUGGEST_COLUMN_TEXT_1 },
                    new int[] { android.R.id.text1 },0));

            search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
            search.setQueryRefinementEnabled(true);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    onQuerySubmit(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    onQueryChange(newText);
                    return true;
                }
            });
            search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int position) {
                    return false;
                }

                @Override
                public boolean onSuggestionClick(int position) {
                    Cursor cursor = (Cursor) search.getSuggestionsAdapter().getItem(position);
                    String data = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                    if(!search.getQuery().equals(data)){
                        //Sets the selected query from suggestion list to search box
                        search.setQuery(data,true);
                    }
                    cursor.close();
                    return true;
                }
            });
        }
    }

    /**
     * Provide toolbar instance to super class
     * @param toolbar inflated {@link Toolbar}
     */
    protected void setToolbarView(Toolbar toolbar){
        this.toolbar = toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
    /**
     * Set title and show back button
     * @param title, Title for tool bar
     * @param showBackButton, show back button on tool bar
     */
    protected void setTitle(String title, boolean showBackButton) {
        if(toolbar == null){
            return;
        }
        toolbar.setTitle(title);
        if(showBackButton){
            toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
            toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(toolbar).popBackStack());
        }
    }

    /**
     * Set to true to enable search in action bar
     * @param enableSearch  true/false
     */
    protected void setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    /**
     * @return Inflated {@link SearchView}
     */
    protected SearchView getSearchView(){
        return search;
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToScreen(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment,
                                            int frameId,
                                            Bundle args) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(args != null){
            fragment.setArguments(args);
        }
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}
