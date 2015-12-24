package in.org.kurukshetra.app;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import in.org.kurukshetra.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class HospiActivity extends AppCompatActivity {

    public void map(View view) {
        double latitude
                = 13.0096996;
        double longitude = 80.2353672;
        String label = "College of Engineering,Guindy";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void call(View view){
        String phno = view.getContentDescription().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phno));
        startActivity(intent);
    }
    public void mail(View view)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + view.getContentDescription().toString()));
        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.hospi_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_CONTENT = "contents";
        private static final String POSITION = "position";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String content,int position) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_CONTENT, content);
            args.putInt(POSITION,position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int position = getArguments().getInt(POSITION);

            View rootView = inflater.inflate(R.layout.fragment_hospi, container, false);
            FloatingActionButton fab= (FloatingActionButton) rootView.findViewById(R.id.fab_hospi);
            if(position!=3) {
                fab.hide();
            }
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Html.fromHtml(getArguments().getString(ARG_SECTION_CONTENT)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            initContent();
        }
        JSONObject obj;
        JSONArray tabs;
        private void initContent() {
            String json = loadJSONFromAsset();
            try {
                obj = new JSONObject(json);
                tabs = obj.getJSONArray("hospitalities");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String loadJSONFromAsset() {
            String json;

            try {
                InputStream is = getAssets().open("hospi/hospitalities.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }

        @Override
        public Fragment getItem(int position) {

            {
                try {
                    if(position < 5)
                        return PlaceholderFragment.newInstance(tabs.getJSONObject(position).getString("desc"),position);
                    else
                        return new hospi();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return  tabs.getJSONObject(position).getString("title");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
