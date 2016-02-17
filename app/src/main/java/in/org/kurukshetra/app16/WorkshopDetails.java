package in.org.kurukshetra.app16;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.pushbots.push.Pushbots;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.org.kurukshetra.app16.app.MyApplication;
import in.org.kurukshetra.app16.workshopreg.CountChooserFragment;

public class WorkshopDetails extends AppCompatActivity implements LoginFragment.OnLoginListener{
    String eventName,eventKey,category;
    HashMap<String, String> idMap;
    HashMap<String, Integer> minMap, maxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_workshop_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        Resources res = getResources();

        final Drawable upArrow = res.getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if (upArrow != null) {
            upArrow.setColorFilter(res.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        initMaps();
        eventName = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("cat");

        setTitle(eventName);
        eventKey = getIntent().getStringExtra("key");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar2);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(toolbar.getTitle());
        }

    }

    private void initMaps() {
        idMap = new HashMap<>();
        minMap = new HashMap<>();
        maxMap = new HashMap<>();
        idMap.put("ibm-s-cognitive-computing","IC");
        idMap.put("creative-coding","CC");
        idMap.put("growth-hacking","GH");
        idMap.put("eye-controlled-robots","EC");
        idMap.put("distance-relays-and-substation-automation","DR");
        idMap.put("build-your-3d-printer","3D");
        idMap.put("esri-s-connective-convergence","GI");
        idMap.put("samsung-s-virtual-reality","VR");

        minMap.put("ibm-s-cognitive-computing",1);
        minMap.put("creative-coding", 1);
        minMap.put("growth-hacking",1);
        minMap.put("eye-controlled-robots",1);
        minMap.put("distance-relays-and-substation-automation", 1);
        minMap.put("build-your-3d-printer", 3);
        minMap.put("esri-s-connective-convergence", 1);
        minMap.put("samsung-s-virtual-reality", 2);

        maxMap.put("ibm-s-cognitive-computing",1);
        maxMap.put("creative-coding",1);
        maxMap.put("growth-hacking",1);
        maxMap.put("eye-controlled-robots",4);
        maxMap.put("distance-relays-and-substation-automation", 1);
        maxMap.put("build-your-3d-printer",4);
        maxMap.put("esri-s-connective-convergence",1);
        maxMap.put("samsung-s-virtual-reality",2);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Drawable d = loadImageFromAsset(eventKey);
        imageView.setImageDrawable(d);

        String json = loadJSONFromAsset(eventKey);
        ContactDetails store = new ContactDetails();
        store.init();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray tabs = obj.getJSONObject("workshop").getJSONArray("tabs");

            for(int i = 0; i < tabs.length(); i++) {

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
        catch(IOException ignored) {
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

    Intent intent2=new Intent(Intent.ACTION_SEND);
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.share){
            String text="http://m.kurukshetra.org.in/#/"+ category;
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT,"Check out the  event "+eventName+" at 'Kurukshetra'16' "+text);
            startActivity(Intent.createChooser(intent2, "Share via . . ."));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogin(int position) {
        if(position == 1){
            Toast.makeText(this,"Logged in",Toast.LENGTH_SHORT).show();
            proceedToRegister();
        }
        else
            Toast.makeText(this,"Logged out",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                proceedToRegister();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Logout Successful",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void proceedToRegister() {
        initMaps();
        CountChooserFragment fragment = CountChooserFragment.newInstance(minMap.get(eventKey),maxMap.get(eventKey),idMap.get(eventKey));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager,"REGISTER");
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
        MyApplication.getInstance().trackScreenView("Workshop " + eventName);

        Pushbots.sharedInstance().tag("Workshop "+ eventName);
    }
}