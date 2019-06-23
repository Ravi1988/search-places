package com.app.searchplaces.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.app.searchplaces.R;
import com.app.searchplaces.ui.base.BaseFragment;

public class SearchPlaceFragment extends BaseFragment {
    private Menu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(getActivity() == null){
            return;
        }
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        this.menu = menu;

        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (manager != null) {
            search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPlaces(query);
                return true;
                }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void searchPlaces(String query) {

    }
}
