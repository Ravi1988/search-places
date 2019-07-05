package com.app.searchplaces.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.app.searchplaces.BR;
import com.app.searchplaces.data.models.places.Venue;

/**
 *
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        private BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    public static BaseViewHolder create(ViewGroup parent, int layout) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                layout, parent, false);
        return new BaseViewHolder(binding);
    }

        public void bindTo(Venue venue) {
            //Make sure you define a variable in xml named holder.
            binding.setVariable(BR.holder, venue);
            binding.executePendingBindings();
        }

        public ViewDataBinding getBinding(){
            return binding;
        }

    }
