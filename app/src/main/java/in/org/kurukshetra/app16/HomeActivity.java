package in.org.kurukshetra.app16;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pushbots.push.Pushbots;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import in.org.kurukshetra.app16.app.MyApplication;
import in.org.kurukshetra.app16.sync.K16SyncAdapter;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class HomeActivity extends AppCompatActivity {

    public static final String[] ASSET_SUB_DIR = { "hospi" , "workshops", "xceed" , "events" };

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static KenBurnsView mHeaderPicture;
    private int[] tabIcons = {
            R.drawable.home_ico,
            R.drawable.notif_ico
    };

    public void funEvents1(View view) {
        Intent intent = new Intent(this,Events.class);
        startActivity(intent);
    }

    public void funEvents2(View view) {
        Intent intent = new Intent(this,XceedActivity.class);
        startActivity(intent);
    }

    public void funEvents3(View view) {
        Intent intent = new Intent(this,GlActivity.class);
        startActivity(intent);
    }

    public void funEvents4(View view) {
	    Intent intent = new Intent(this,Workshops.class);
        startActivity(intent);
    }

	@Override
	protected void onPostResume () {
		super.onPostResume ();
	//	stopService (new Intent (HomeActivity.this, ChatHead.class));
	}

	@Override
	protected void onPause () {
		super.onPause ();
	//	startService (new Intent (HomeActivity.this, ChatHead.class));
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final SharedPreferences s;
        final String MyPREFERENCES = "MyPrefs" ;

        s = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        if(s.getInt("status",0)==0)
        {
            new MaterialShowcaseView.Builder(this)
                    .setTarget(fab)
                    .setDismissText("GOT IT")
                    .setContentText("Click this button to open the quick menu")
                    .show();

            copyAssetJsonToStorage();
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

        K16SyncAdapter.initializeSyncAdapter(this);
        K16SyncAdapter.syncImmediately(this);

    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();

    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder (HomeActivity.this);

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
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Home Page");
    }

    private void copyAssetJsonToStorage(){

        File myDir = new File(Environment.getExternalStorageDirectory(),"k16");
        if(!myDir.exists()){
            myDir.mkdirs();
        }
	    for (String aASSET_SUB_DIR : ASSET_SUB_DIR) {
		    createNewSubDir (aASSET_SUB_DIR);
		    copyAssets (aASSET_SUB_DIR);
	    }
    }


    private void copyAssets(String assetFolder) {
        AssetManager assetManager = getAssets();
        String[] files;
        try {
            files = assetManager.list(assetFolder);

            Log.e("tag", "Got Assets List");
            copyDirContents(assetFolder,files);
            Log.e("tag","contents copied");
        }
        catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
    }

    public void copyDirContents(String assetFolder,String [] files){

        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = getAssets().open(assetFolder+"/"+filename);
                Log.e("tag", "Opened - " + filename);
                //File outFile = new File(getExternalFilesDir(null), filename);
                File outFile = new File(Environment.getExternalStorageDirectory()+"/k16/"+assetFolder, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                Log.e("tag", "Done copying - "+filename);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }


    private void createNewSubDir(String dirName){

        File myDir = new File(Environment.getExternalStorageDirectory()+"/k16",dirName);
        if(!myDir.exists()) {
            myDir.mkdirs();
        }
    }
}
