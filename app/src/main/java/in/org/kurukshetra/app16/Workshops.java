package in.org.kurukshetra.app16;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pushbots.push.Pushbots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.org.kurukshetra.app16.app.MyApplication;

public class Workshops extends AppCompatActivity {
    private PieChart pieChart, eventList;
    private ScrollView scrollView;
    private Map<String, String> eventKeys = new HashMap<>();
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> eventlist = new ArrayList<>();

    String[] cats = {"Engineering","Robotics", "Management", "School"};
    String[] engineering = {"IBM's Cognitive Computing","Creative Coding","Distance Relays and Substation Automation",
            "Build Your 3D Printer","ESRI's Connective Convergence","Samsung's Virtual Reality"};
    String[] management = {"Growth Hacking"};
    String[] school = {"Krithi"};
    String[] robotics = {"Eye Controlled Robots"};
    int[] colors = { Color.rgb(189, 47, 71), Color.rgb(228, 101, 92), Color.rgb(241, 177, 79),
            Color.rgb(161, 204, 89), Color.rgb(33, 197, 163), Color.rgb(58, 158, 173),Color.rgb(92, 101, 100)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initKeys();

        setContentView(R.layout.activity_workshops);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Workshops");
        for (int i = 0; i < 3; i++) {
            entries.add(new Entry(1, i));
            categories.add(cats[i]);
        }

        pieChart = (PieChart) findViewById(R.id.platinum);
        pieChart.setCenterText("Workshops");
        Pushbots.sharedInstance().tag("Workshops");
        pieChart.setDescription(null);
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutCirc);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        PieDataSet dataset1 = new PieDataSet(entries, "Workshops");
        dataset1.setColors(colors);
        dataset1.setDrawValues(false);
        PieData pieData1 = new PieData(categories, dataset1);
        pieData1.setValueTextColor(Color.rgb(255,255,255));
        pieData1.setValueTextSize(16);
        pieChart.setDescriptionTextSize(100);
        pieChart.setData(pieData1);
        pieChart.setHoleRadius(40);
        pieChart.setCenterTextColor(R.color.black);
        pieChart.setTransparentCircleRadius(50);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                final Entry e1 = e;
                showEvents(e1.getXIndex());
                View view = findViewById(android.R.id.content);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollDown();
                    }
                });

            }

            @Override
            public void onNothingSelected() {
                eventList.setVisibility(View.GONE);
            }
        });


        //events list
        eventList = (PieChart) findViewById(R.id.list);
        eventList.setVisibility(View.GONE);

    }

    private void initKeys() {
            eventKeys.put("IBM's Cognitive Computing","ibm-s-cognitive-computing");
            eventKeys.put("Creative Coding","creative-coding");
            eventKeys.put("Growth Hacking","growth-hacking");
            eventKeys.put("Krithi","krithi");
            eventKeys.put("Eye Controlled Robots","eye-controlled-robots");
            eventKeys.put("Distance Relays and Substation Automation","distance-relays-and-substation-automation");
            eventKeys.put("Build Your 3D Printer","build-your-3d-printer");
            eventKeys.put("ESRI's Connective Convergence","esri-s-connective-convergence");
            eventKeys.put("Samsung's Virtual Reality","samsung-s-virtual-reality");
    }

    //showing events
    public void scrollDown() {
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public void showEvents(final int index) {
        String cat = categories.get(index);
        entries = new ArrayList<>();
        eventlist = new ArrayList<>();
        String [] events;
        if(cat.equals("Engineering")){
            events = engineering;
        }
        else if(cat.equals("Management")){
            events = management;
        }
        else if(cats.equals("School")){
            events = school;
        }
        else {
            events= robotics;
        }
        for (int i = 0; i < events.length; i++) {
            entries.add(new Entry(1, i));
            eventlist.add(events[i]);
        }
        eventList.setVisibility(View.VISIBLE);
        eventList.clear();
        eventList.setCenterText(categories.get(index));
        eventList.setDescription(null);
        eventList.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        PieDataSet dataset2 = new PieDataSet(entries, "Events");
        dataset2.setColors(colors);
        dataset2.setDrawValues(false);
        PieData pieData2 = new PieData(eventlist, dataset2);
        pieData2.setValueTextColor(Color.rgb(255, 255, 255));
        pieData2.setValueTextSize(16);
        eventList.setDescriptionTextSize(100);
        eventList.setData(pieData2);
        eventList.setRotationAngle(180);
        eventList.setHoleRadius(40);
        eventList.setCenterTextColor(R.color.black);
        eventList.setTransparentCircleRadius(50);

        eventList.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                String name = eventlist.get(e.getXIndex());
                Intent intent = new Intent(Workshops.this,WorkshopDetails.class);
                intent.putExtra("name",name);
                intent.putExtra("key",eventKeys.get(name));
                intent.putExtra("cat", categories.get(index));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

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
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Workshops Page");
    }

}