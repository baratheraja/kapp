package in.org.kurukshetra.app16;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pushbots.push.Pushbots;

import in.org.kurukshetra.app16.app.MyApplication;

public class ProjectsActivity extends AppCompatActivity {
 private SectionsPagerAdapter mSectionsPagerAdapter;

    public static String[] abouts = {"Looking for a land / place for your sweet home or for your business?\n" +
            "Going to be fooled by the people advertising in TV channels or want to select a place as you select products while online shopping?\n" +
            "Here is our land selection web application to help you to select a place of your choice.\n" +
            "Our web app allows you to select a land of your choice from a given area of your choice and makes sure you have the best user experience.\n" +
            "If you have already selected a land, you can weigh in your options here.\n" +
            "Our app is embedded with different layers of maps that provide information about the nearby features such as schools, bus stops, railways stations, as well as the soil maps, ground water prospects and other such maps that provide you the data about any place.",

            "The main objective of this project is to make the auto rickshaws “safe as well as customer friendly”. In an age when development in technology is tremendous, the usage of autos which are very well popular in India has decreased enormously. A\b major reason for the decline in preference of autos is the hassle on has to go through for the fare. The fare that drivers demand is both unreasonable and illegal. Even though there are few measures, it turned out to be ineffective while the situation worsens.\n" +
            "\n" +
            "In the present scenario, SMART AUTOs will be a welcomed change for the people. It shall feature:\n" +
            "\n" +
            "1) An automated electronic meter interfaced with a microcontroller and an ultrasonic sensor (to sense the presence of passenger). A message containing the details of the auto will be sent to the nearby police station if the meter is in “off” condition and when there is a passenger inside the auto. This helps the passengers to pay the right fare for the distance travelled.\n" +
            "2) An “emergency  button“ along with a keypad, fixed on one side of the passenger’s seat, which can be used in case the auto is driven off in suspicious path. The emergency button is enclosed within a glass case which when broken by the passenger under emergency, a continuous call and a message is sent to the police station which will give them the tracking details. The keypad can also be used to dial any number, in case the person wants to inform his/her family about the emergency. This keypad will be activated only when the glass case is broken.\n" +
            "3) A mobile app which can be downloaded by the passengers from Play Store for tracking down the route that they are heading to and this app has an interesting feature that becomes very useful for the passengers who are new to the place. It indicates the nearby monuments and tourist spots around the city. \n" +
            "4) A solar charger which works with the use of solar panels over the roof of the auto and a battery that stores the energy. This in turn can be used to power up the passengers mobile phones when they are out of charge.\n",


            "Anti-lock braking systems are used to prevent sudden locking of the wheels of a car when braking at high speeds. They work by producing a pulsating effect, quickly engaging and disengaging the brakes around 15 times a second at 100 kmph. This allows the driver to control the steering even under adverse braking conditions.\n" +
                    "\n" +
                    "Mechanical ABS overcomes the two main difficulties of conventional anti-lock brakes, namely the high cost and need for any electronic systems. This is achieved by using rollers to provide the pulsating effect required.\n" +
                    "                     \n" +
                    "The brake discs have slots machined in them, which hold the chromium steel rollers. As the brakes are applied at high speeds, the brake callipers slide quickly over the balls, due to less friction, and slower over the rest of the brake. This provides the required variation in braking force, viz. the pulsating effect.\n" +
                    "\n" +
                    "As the image shows, the rollers slide along the slots when the brake callipers move over them and return to their original positions afterwards due to centrifugal forces.\n",


            "PURPOSE: To alert people in case of any emergency (i.e accidents, assaults) and provide immediate help\n" +
                    "PRINCIPLE: Static GPS tracking\n" +
                    "METHOD OF USE: People using this app can create a group of their friends (say five to ten people) who they would like to contact in case of a problem. \n" +
                    "Emergencies can range from something as simple as a car breakdown to a full-fledged attack. To distinguish between these the level of the problem can be specified. The GPS works two ways - providing the friends with the location of the user and well as the user with the location of his friends. Hereby, he/she can select the nearest person for help, thereby providing immediate relief. \n",

            "Simply speaking, our project brings Dexter alive! Ergo, the project is a humanoid robot capable of motion, also has hands which are functional. The purpose of the bot is to welcome people. The application of similar bots can be used in fields of photography, cinematography.\n",
            "An accident won’t arrive with a bell on its neck or will it?\n" +
                    "Wireless Accident Detection System or WADS aims at providing that “critical hour” help required to rescue victims of road accidents. WADS is an attempt to bridge the gap between the time of accident and the time of rescue. \n" +
                    "In the unfortunate event of an accident, an SOS message with co-ordinates of the impact site, is sent to trusted contacts, within seconds of the crash. Also, an alarm is raised, to draw the attention of those nearby, and thus, speed up rescue. This project will be a humble effort in minimising road casualties.\n",
            "A farm from Coimbatore had approached CEG Tech Forum in need of a machine that would keep track of coconuts and give an accurate count of the same. The necessity arose because of the chance of miscount with labourers. The owner of the farm right now has no means to know the actual count of coconuts being sold and relies only on his labourers. They had been experiencing a problem where the labourers fake the count while a much larger number has been sold. The labourers would benefit by seeking a commission from the buyer for faking the count. This product is designed to be as robust as possible and at the same time cost-effective.",

            "This is a machine that crushes empty water bottles and gives you a 5 Rupee coin. The machine senses the bottle and allows it to enter into the machine. It uses slider mechanism which forces a weight to crush the empty bottle. The machine pushes a 5 rupee coin as output, giving everyone a winning situation, with plastics recycled and cash-returns for the recycler.",
            "The basic principle of this project is derived from a popular industrial concept called 3-D printing. 3-D printing is used to make a solid prototype from a CAD file in the .stl format. \n" +
                    "This machine will adapt the 3-D printing techniques to the 2-D form. Main components required are:\n" +
                    "Arduino microcontroller\n" +
                    "Stepper motors\n" +
                    "Guideways\n" +
                    "Input is fed as a .svg file. Text is converted from .pdf and images are directly converted to the format. Image processing is done and data as a series of points are then fed into the Arduino microcontroller. \n" +
                    "Stepper motors are programmed accordingly and the required output is obtained.\n" +
                    "\n" +
                    "PURPOSE OF THE PROJECT:\n" +
                    "Reduces writing work of students, with most of the redundant records are written from online documents.\n" +
                    "With a little modification, the machine can act as a laser cutting machine (by replacing the writing instrument with a laser beam source)\n" +
                    "Can reproduce complicated images and profiles systematically.\n",
            "Asherah is a fully autonomous unmanned bot which can clean the water surface by navigating through it.\n" +
                    "The bot is equipped with camera to detect the pollutants and garbage particles of definite size. It moves on the water surface, collects the garbage from water surface and deposits them on a tray present on top of the bot. \n" +
                    " More than 70 percent of population depends on water bodies like lake and ponds for their daily living. However, these water bodies are polluted to a large extent. This bot is being built mainly to clean the water surface for access to cleaner water for use.",
            "This project aims at preventing accidents due to people travelling on the 3rd tier of the intercity buses. This system stops the bus from moving if people are still on the 3rd tier while commencing the journey from the bus stop. The bus will slow down automatically if people move to the third tier of the bus while it is in motion. This system also integrates within itself an information system which gives a list of buses to a destination chosen by the user and the number of empty seats in those busses. This prevents overcrowding on the same bus. The system also allows users to be queued in a particular order, which prevents conflict of who should alight a bus at a particular point of time. \n",
            "During the time of disaster it is of paramount importance to identify the people trapped in different places and also make an evacuation plan for them. It is highly unlikely that humans can access the disaster affected areas and find out if the people trapped there. A robot has been designed by us for exactly this application.",
            "Narendra Modi’s Swachh Bharat campaign has gained momentum and why should only humans be involved in this initiative? We have designed a Swachh Robot that will help us in cleanliness. This robot will collect the waste in an area and dispose it off. This robot is controlled manually. ",
            "Today most of the infant deaths are mainly due to the respiratory blockage that occurs in the respiratory tract while sleeping, named as APNEA. To prevent their death and to alert the parents and responsible people, we have developed a simple monitor. The purpose of this project is to saves the live of infants. Inhalation and Exhalation waveforms can be monitored along with the respiratory rate. This system will send out messages and alert the people around and can be easily installed at home.\n",
            "Mobile phones have taken an innovative step towards security with their pattern locks. These are highly secure and also fun to use. The lock that we have made will give you the protection of a conventional lock combined with the security provided by the patterns. You will have to draw the patterns to unlock not on any surface but in Air. This lock uses infrared to sense the movements of your fingers."
    };

    public static String[] projects={

    };

    TabLayout tabLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Pushbots.sharedInstance().tag("Projects");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.about_us_tabs);
        tabLayout.setupWithViewPager(mViewPager);


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
        private static final String ARG_SECTION_CONTENT = "section_content";
        private static final String ARG_SECTION_NUMBER = "section_number";
        int position;

        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_CONTENT, abouts[sectionNumber]);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_projects, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.projectDetails);
            textView.setText(getArguments().getString(ARG_SECTION_CONTENT));
            ImageView imageView = (ImageView) rootView.findViewById(R.id.project_image);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    imageView.setImageResource(R.drawable.pro_1);break;
                case 1:
                    imageView.setImageResource(R.drawable.pro_2);break;

                case 2:
                    imageView.setImageResource(R.drawable.pro_3); break;
                case 3:
                    imageView.setImageResource(R.drawable.pro_4); break;
                case 4:
                    imageView.setImageResource(R.drawable.pro_5);break;
                case 5:
                    imageView.setImageResource(R.drawable.pro_6);break;
                case 6:
                    imageView.setImageResource(R.drawable.pro_7);break;
                case 7:
                    imageView.setImageResource(R.drawable.pro_8);break;

                case 8:
                    imageView.setImageResource(R.drawable.pro_9); break;
                case 9:
                    imageView.setImageResource(R.drawable.pro_10); break;
                case 10:
                    imageView.setImageResource(R.drawable.pro_11);break;
                case 11:
                    imageView.setImageResource(R.drawable.pro_12);break;
                case 12:
                    imageView.setImageResource(R.drawable.pro_13); break;
                case 13:
                    imageView.setImageResource(R.drawable.pro_14);break;
                case 14:
                    imageView.setImageResource(R.drawable.pro_15);break;

            }
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Land property";
                case 1:
                    return "Smart auto";
                case 2:
                    return "ABS";
                case 3:
                    return "Emergency app";
                case 4:
                    return "Dexter bot";
                case 5:
                    return "WADS";
                case 6:
                    return "NIU Counter";
                case 7:
                    return "Can crusher";
                case 8:
                    return "2D-writer";
                case 9:
                    return "Asherah";
                case 10:
                    return "Foot Board Accident prevention system";
                case 11:
                    return "Disaster Surveillance Bot";
                case 12:
                    return "Swachh Bot";
                case 13:
                    return "Apnea Monitor";
                case 14:
                    return "Air Lock";
            }
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Projects");
    }
}
