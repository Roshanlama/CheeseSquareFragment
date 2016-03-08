package com.support.android.designlibdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CheeseDetailFragment extends Fragment {

    private static final String CHEESE_NAME = "cheese-name";

    private String cheeseName;
    private OnCheeseDetailFragmentListener mListener;

    public CheeseDetailFragment() {
    }

    public static CheeseDetailFragment newInstance(String cheese) {
        Bundle args = new Bundle();
        args.putString(CHEESE_NAME, cheese);
        CheeseDetailFragment fragment = new CheeseDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cheeseName = getArguments().getString(CHEESE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cheese_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.enableCollapse();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCheeseDetailFragmentListener) {
            mListener = (OnCheeseDetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCheeseDetailFragmentListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getCollapsingToolbar().setTitle("Detail");
    }

    public interface OnCheeseDetailFragmentListener {
        void enableCollapse();
    }

}
