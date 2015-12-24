package in.org.kurukshetra.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import in.org.kurukshetra.app.R;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static KenBurnsView mHeaderPicture;
    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.notif_icon
    };

    public void funEvents1(View view) {
        //Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
        //ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image);
            Intent intent = new Intent(this,Events.class);
            startActivity(intent);
    }

    public void funEvents2(View view) {
        //Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
        //ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image2);
        Intent intent = new Intent(this,XceedActivity.class);
        startActivity(intent);
    }

    public void funEvents3(View view) {
        //Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
        //ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image);
        Intent intent = new Intent(this,GlActivity.class);
        startActivity(intent);
    }

    public void funEvents4(View view) {
        //Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
      //  ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image);
       // Intent intent = new Intent(this,Events.class);
        //startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final SharedPreferences s;
        final String MyPREFERENCES = "MyPrefs" ;

        s=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        if(s.getInt("status",0)==0)
        {
            new MaterialShowcaseView.Builder(this)
                    .setTarget(fab)
                    .setDismissText("GOT IT")
                    .setContentText("This is some amazing feature you should know about")
                    .show();
            SharedPreferences.Editor e = s.edit();
            e.putInt("status", 1);
            e.commit();
        }

        Pushbots.sharedInstance().init(this);
        mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
        mHeaderPicture.setResourceIds(R.drawable.image1, R.drawable.image2, R.drawable.image3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar2);
        setTitle("");
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.home_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(HomeActivity.this, OverlayActivity.class);
                    startActivity(intent);

            }
        });



    }





    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                HomeActivity.this);


        alertDialog.setMessage("Are you sure you want to Exit?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "HOME");
        adapter.addFrag(new UpdatesFragment(), "UPDATES");
        viewPager.setAdapter(adapter);
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           // return mFragmentTitleList.get(position);
            return null;
        }
    }
}
