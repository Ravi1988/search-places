package com.app.searchplaces.ui.venue.adapter;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.navigation.Navigation;

import com.app.searchplaces.R;
import com.app.searchplaces.data.models.venuemodels.Category;
import com.app.searchplaces.data.models.venuemodels.Venue;
import com.app.searchplaces.events.EventConstant;
import com.app.searchplaces.events.MessageEvent;
import com.app.searchplaces.util.CommonUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.app.searchplaces.util.AppConstant.favMap;

/**
 * Binding adapter for Venue search and detail.
 * Put all custom view binding here.
 */
public  class VenueItemBinding {
    /**
     * Sets the venue category name to text view.
     * Priority given to first category item in list. Can be changed to support
     * multiple category text with | (pipe) separator.
     * @param textView inflated view
     * @param category venue category list of type {@link List<Category>}
     */
    @BindingAdapter("venueCat")
    public static void setCategory(TextView textView, List<Category> category){
        if(category != null && category.size() > 0){
            textView.setVisibility(View.VISIBLE);
            textView.setText(category.get(0).getName());
        }else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * Sets the venue category list first item icon to image view.
     *
     * @param imageView inflated view
     * @param category venue category list of type {@link List<Category>}
     */
    @BindingAdapter("venueIcon")
    public static void setIcon(ImageView imageView,List<Category> category){
        if(category != null && category.size() > 0 && category.get(0).getIcon() != null){
            Glide.with(imageView.getContext()).load(category.get(0).getIcon().getIconUrl()).placeholder(R.drawable.ic_placeholder).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.ic_placeholder);
        }
    }

    /**
     * Sets the item click listener for venue Recyclerview items
     * @param rootView inflated view
     * @param venue venue of type {@link Venue}
     */
     @BindingAdapter("itemClick")
    public static void setItemClick(ConstraintLayout rootView, Venue venue){
         rootView.setOnClickListener(v -> {
             //Navigate to detail fragment using Navigation controller
             //with action id searchToDetail
             Navigation.findNavController(v).navigate(R.id.searchToDetail);
             //pass venue to detail fragment using event bus
             // posting it as sticky since fragment is not attached to window yet
             EventBus.getDefault().postSticky(new MessageEvent(EventConstant.VENUE_ITEM,venue));
         });
    }

    /**
     * set click for favourite icon
     * @param button inflated view
     * @param venue venue of type {@link Venue}
     */
    @BindingAdapter("favClick")
    public static void setFavClick(FloatingActionButton button, Venue venue){
        button.setOnClickListener(v -> {
            //update venue object
            venue.setMarkedFav(!venue.isMarkedFav());
            //update preference map
            if(!venue.isMarkedFav()){
                favMap.remove(venue.getId());
            }else {
                favMap.add(venue.getId());
            }
            //change icon
            setFavState(button,venue);

            CommonUtil.showToast(v.getContext(),venue.isMarkedFav() ? v.getResources().getString(R.string.favourited)
                            : v.getResources().getString(R.string.un_favourited));
        });
    }

    /**
     * Change favourite icon image of floating button as per selection
     * @param button inflated view
     * @param venue venue of type {@link Venue}
     */
    @BindingAdapter("favState")
    public static void setFavState(FloatingActionButton button, Venue venue){
        if( venue != null && venue.isMarkedFav()){
            button.setImageResource(R.drawable.ic_sharp_favorite_24px);
        }else {
            button.setImageResource(R.drawable.ic_sharp_favorite_border_24px);
        }
    }


    /**
     *Change favourite icon image of list item image as per selection
     * @param imageView inflated view
     * @param isMarked true/false -> selected/unselected
     */
    @BindingAdapter("favState")
    public static void setFavState(ImageView imageView, boolean isMarked){
        if(isMarked){
            imageView.setImageResource(R.drawable.ic_sharp_favorite_24px);
        }else {
            imageView.setImageResource(R.drawable.ic_sharp_favorite_border_24px);
        }
    }

    /**
     * Sets the height of map to 50% of screen height
     * @param layout inflated view
     * @param barHeight required for data binding
     */
    @BindingAdapter("mapHeight")
    public static void setMapHeight(View layout, Integer barHeight){
        DisplayMetrics displayMetrics = layout.getContext().getResources().getDisplayMetrics();
        barHeight = displayMetrics.heightPixels / 2;
        layout.getLayoutParams().height = barHeight;
    }

}
