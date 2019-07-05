package com.app.searchplaces.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.app.searchplaces.R;
import com.app.searchplaces.util.AppConstant;
import com.app.searchplaces.util.AppPreferences;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.app.searchplaces.util.AppConstant.favMap;

/**
 * Responsible to host app fragment and navigation fragment
 * Currently there is not much to do with activity hence no base class declaration.
 * This extends directly {@link DaggerAppCompatActivity} for auto injection
 *
 */
public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(favMap != null){
            appPreferences.putStringSet(AppConstant.KEY_FAV_IDS, favMap);
        }
    }
}
