package sngular.com.atmsmap.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import sngular.com.atmsmap.R;
import sngular.com.atmsmap.presentation.view.fragment.SearchFragment;

/**
 * Created by alberto.hernandez on 13/04/2016.
 */
public class SearchActivity extends AppCompatActivity {
    private static final String FRAGMENT_SEARCH = "searchFragment";
    private static AutoCompleteTextView mAutocompletePlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_search_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_search_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAutocompletePlaces((AutoCompleteTextView) findViewById(R.id.search_autocomplete_box));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search, new SearchFragment(), FRAGMENT_SEARCH)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static AutoCompleteTextView getAutocompletePlacesView() {
        return mAutocompletePlaces;
    }

    public static void setAutocompletePlaces(AutoCompleteTextView mAutocompletePlaces) {
        SearchActivity.mAutocompletePlaces = mAutocompletePlaces;
    }
}
