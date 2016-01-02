package in.org.kurukshetra.app16;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.org.kurukshetra.app16.app.MyApplication;

public class WorkshopDetails extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    String eventName,eventKey,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        eventName=getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("cat");

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
        Drawable d = loadImageFromAsset(eventKey);
        imageView.setImageDrawable(d);

        String json = loadJSONFromAsset(eventKey);
        ContactDetails store = new ContactDetails();
        store.init();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray tabs = obj.getJSONObject("workshop").getJSONArray("tabs");
            int n = tabs.length();

            for(int i=0;i<n;i++) {
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
      //  adapter.addFragment(oneFragment3, "Contact");
        viewPager.setAdapter(adapter);

    }
    public Drawable loadImageFromAsset(String file) {
        Drawable d=null;
        try {
            InputStream ims = getAssets().open("images/"+file+".jpeg");
            d = Drawable.createFromStream(ims, null);
        }
        catch(IOException ex) {
        }
        return d;
    }

    public String loadJSONFromAsset(String name) {
        String json;
        try {

            File inputFile = new File(Environment.getExternalStorageDirectory()+"/k16/workshops",name+".json");
            InputStream is = new FileInputStream(inputFile);
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hospi, menu);
        return true;

    }
*/
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

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Workshop "+eventName);
    }
}