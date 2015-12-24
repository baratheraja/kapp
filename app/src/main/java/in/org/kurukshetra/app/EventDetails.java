package in.org.kurukshetra.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.org.kurukshetra.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    String eventName,eventKey,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        eventName=getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("cat");
        if(eventName.equals("BIM")){
            eventName = "Building Information Modelling";
        }
        else if(eventName.equals("OSPC")){
            eventName = "Onsite Programming Contest";
        }
        else if(eventName.equals("OLPC")){
            eventName = "Online Programming Contest";
        }
        setTitle(eventName);
        eventKey = getIntent().getStringExtra("key");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar2);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(toolbar.getTitle());
        }

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        imageView = (ImageView) findViewById(R.id.backdrop);
        Drawable d = loadImageFromAsset("riddles-of-the-sphinx");
        imageView.setImageDrawable(d);

        String json = loadJSONFromAsset(eventKey);
        ContactDetails store = new ContactDetails();
        store.init();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray tabs = obj.getJSONObject("event").getJSONArray("tabs");
            int n = tabs.length();

            for(int i=0;i<n-1;i++) {
                JSONObject tab = tabs.getJSONObject(i);
                String title = tab.getString("title");
                String content = tab.getString("content");
                Bundle args = new Bundle();
                OneFragment oneFragment = new OneFragment();
                String[] dataArray = new String []{content};
                args.putStringArray("value",dataArray);
                oneFragment.setArguments(args);
                adapter.addFragment(oneFragment,title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] dataArray3 =  store.names.get(eventKey);
        String[] numArray = store.ph_no.get(eventKey);
        String mail_id = store.mail_id.get(eventKey);

        Bundle args3 = new Bundle();
        OneFragment oneFragment3 = new OneFragment();

        args3.putStringArray("value", dataArray3);
        args3.putStringArray("number", numArray);
        args3.putString("mail_id", mail_id);

        oneFragment3.setArguments(args3);
        adapter.addFragment(oneFragment3, "Contact");
        viewPager.setAdapter(adapter);

    }
    public Drawable loadImageFromAsset(String file) {
        Drawable d=null;
        try {
            InputStream ims = getAssets().open("images/"+file+".jpg");
            d = Drawable.createFromStream(ims, null);
        }
        catch(IOException ex) {
        }
        return d;
    }

    public String loadJSONFromAsset(String name) {
        String json;
        try {
            InputStream is = getAssets().open("events/"+name+".json");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hospi, menu);
        return true;

    }

    Intent intent2=new Intent(Intent.ACTION_SEND);
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.share){
            String text="http://m.kurukshetra.org.in/#/"+category.toString();
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT,"Check out the  event "+eventName+" at 'Kurukshetra'16' "+text);
            startActivity(Intent.createChooser(intent2, "Share via . . ."));

        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}