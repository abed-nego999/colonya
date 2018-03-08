package sngular.com.atmsmap.presentation.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnClick;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.presentation.model.SearchResult;
import sngular.com.atmsmap.presentation.view.activity.SearchActivity;
import sngular.com.awesomemap.component.AddressSearcher;
import sngular.com.awesomemap.listener.PlaceClickListener;

/**
 * Created by alberto.hernandez on 13/04/2016.
 */
public class SearchFragment extends Fragment implements PlaceClickListener {
    private static final String TAG = SearchFragment.class.getSimpleName();
    @Bind(B.id.search_radius_text)
    TextView mRadiusText;
    @Bind(B.id.search_radius_bar)
    SeekBar mRadiusBar;
    @Bind(B.id.btn_search)
    TextView mSearchButton;

    private static final String SEARCH_RESULT = "searchResult";
    private static final int MIN_VALUE_RADIUS = 1;
    private static final int MAX_VALUE_RADIUS = 100;

    private AutoCompleteTextView mAutocompletePlaces;
    private int mProgress = MIN_VALUE_RADIUS;
    private PlaceBuffer mPlaces;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterFork.bind(this, view);
        mAutocompletePlaces = SearchActivity.getAutocompletePlacesView();
        AddressSearcher adressSearcher = new AddressSearcher(getActivity(), mAutocompletePlaces, R.layout.item_autocomplete_search, this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        seekBarController();
    }

    private void seekBarController() {
        mRadiusBar.setMax(MAX_VALUE_RADIUS - MIN_VALUE_RADIUS);
        mRadiusBar.setProgress(MIN_VALUE_RADIUS);
        mRadiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                mProgress = progresValue + MIN_VALUE_RADIUS;
                mRadiusText.setText(mProgress + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Empty Constructor
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Empty Constructor
            }
        });

        mRadiusText.setText(mRadiusBar.getProgress() + " Km");
    }


    @Override
    public void placeClick(PlaceBuffer places) {
        if (mAutocompletePlaces != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mAutocompletePlaces.getWindowToken(), 0);
        }
        mPlaces = places;
        mSearchButton.setEnabled(true);
        mSearchButton.setTextColor(getResources().getColor(R.color.app_black));
    }

    @OnClick(B.id.btn_search)
    public void searchWithPlaceRadius() {
        if (mPlaces != null) {
            final Place place = mPlaces.get(0);
            if (place.isDataValid()) {
                SearchResult searchResult = new SearchResult(place.getId(), place.getName().toString(),
                        place.getAddress().toString(), mProgress * 1000, (float) place.getLatLng().longitude,
                        (float) place.getLatLng().latitude);

                mPlaces.release();
                Log.e(TAG, "Buscar: " + searchResult.getName() + " en radio: " +
                        searchResult.getRadius().toString());
                Intent data = new Intent();
                data.putExtra(SEARCH_RESULT, searchResult);
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            } else {
                mPlaces.release();
            }
        }
    }
}
