package in.org.kurukshetra.app;

import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {
 private SectionsPagerAdapter mSectionsPagerAdapter;
    public static String[] abouts = {"Be it the battles in the mythology or of history, they have always been fought with a cause. Greed of land, resources and supremacy were always the intention. In an age devoid of the need for those, the next battle, it is said, would be for water. Against all odds, we believe that the battle of the brains is a more indispensable battle for our generation. As Student Directors of CTF, we welcome you to this battle, where wits need to be as sharp as swords and the mind as ready as ever to engage in the battle. We at CTF believe that Kurukshetra is the platform for events that will put to test the versatile ability of logic and smart thinking, hands-on workshops and enthralling experiences through guest lecture series. Make your way to CEG during Kurukshetra and leave a bit smarter, a bit more accomplished and proud of yourselves.\n",
    "From being a Survey school in 1794 to a Civil Engineering college in 1858 and finally College of Engineering, Guindy in 1859, the college always strives for excellence in academia and in enriching the students with experience. With the pride of being one of the oldest engineering colleges in India, CEG continues in the path of inspiring engineers to excel at any endeavour.",
    "CEG Tech Forum, the student run organisation, established in the year 2006, has become the technical hub of our college. The Student Directors of CTF work towards uniting the technical activities of CEG under this forum, to nurture and give direction to any student. Through collaborations with industries and academia, we aim to bring out the technological and research curiosity in our students. CTFâ€™s activities also include its flagship event, Kurukshetra.",
    "The Cyclotron symbolizes the celebration of the indomitable spirit of engineering and innovation. It represents the ever expanding pursuit of knowledge. Just as a cyclotron accelerates a charged particle using high frequency, Kurukshetra provides that extra impetus for the engineer to excel.",
    "The UNESCO patronage is the highest form of support granted by the organization, as a moral endorsement of exceptional activity which has a real impact on education, science, cultural or communication. Kurukshetra is the first event of its kind to receive this recognition. This recognition puts Kurukshetra in league with some of the most prestigious endeavors in the world.",
    "Kurukshetra, an International Techno-Management Festival organized by CEG Tech Forum, has embarked on its journey of tenth edition. From its inception in 2007, it aimed at bringing together talents from varied engineering and management domains. Kurukshetra has evolved to act as an effective medium of interface between the academia and the industry. The fest with a vision for future to motivate, to provide opportunity and to identify and analyze societal problems, thereby provides tools to the current generation to solve it."};
    TabLayout tabLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.about_us_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, abouts[sectionNumber]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.about_content);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.about_image);
            textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                case 1:

                case 2:

                case 3:

                case 4:

                case 5:

            }

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INTRODUCTION";
                case 1:
                    return "CEG";
                case 2:
                    return "CTF";
                case 3:
                    return "CYCLOTRON";
                case 4:
                    return "UNESCO";
                case 5:
                    return "KURUKSHETRA";
            }
            return null;
        }
    }
}
