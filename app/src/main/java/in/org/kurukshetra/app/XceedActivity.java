package in.org.kurukshetra.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.fourmob.panningview.library.PanningView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XceedActivity extends AppCompatActivity {
    private PieChart pieChart, eventList;
    private ScrollView scrollView;
    private Map<String, String> eventKeys = new HashMap<>();
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> eventlist = new ArrayList<>();

    String[] cats = {"Ahmedabad", "Bangalore"};
    String[] general = {"Alcatraz","Gambling Math","k! Spell Bee","Mock G20"};
    String[] engineering = {"Building Information Modelling","Circuit Craze","Contraptions","Fully Doped","Godspeed","How Stuff Works","Innovate"};
    String[] online = {"Bank Robbery","Dalal Bull","Online Programming Contest","Riddles Of the Sphinx","Sherlock"};
    String[] coding = {"Heptathlon","Ninja Coding","Onsite Programming Contest","Tame the Code","The Imitation Game"};
    String[] robotics = {"k!ardinal Quest","Robowars","Tanker Bot","The Gem Quest"};
    String[] quiz = {"k! Biz Quiz","k! Open Quiz","k! School Quiz","SciTech Quiz"};
    String[] management = {"Chaos Theory","Enigma","k! Wallet","Marketing Madness"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        initKeys();

        setContentView(R.layout.activity_events);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_icon);
        //upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        */

        setTitle("Events");
        for (int i = 0; i < 7; i++) {
            entries.add(new Entry(1, i));
            categories.add(cats[i]);
        }

        //setting the layout height

        //moving background
        PanningView panningView = (PanningView) findViewById(R.id.panningView);
        panningView.setImageResource(R.drawable.blue);
        panningView.startPanning();


        //setting categories
        pieChart = (PieChart) findViewById(R.id.platinum);
        pieChart.setCenterText("Events");
        pieChart.setDescription(null);
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        PieDataSet dataset1 = new PieDataSet(entries, "Events");
        dataset1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataset1.setDrawValues(false);
        dataset1.setValueTextColor(R.color.white);
        PieData pieData1 = new PieData(categories, dataset1);
        pieData1.setValueTextColor(R.color.white);
        pieChart.setDescriptionTextSize(100);
        pieChart.setDescriptionColor(R.color.white);
        pieChart.setData(pieData1);
        pieChart.setCenterTextColor(R.color.white);
        pieChart.setHoleRadius(40);
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
        eventKeys.put("Sherlock","sherlock");
        eventKeys.put("Riddles Of the Sphinx","riddles-of-the-sphinx");
        eventKeys.put("Dalal Bull","dalal-bull");
        eventKeys.put("Bank Robbery","bank-robbery");
        eventKeys.put("Innovate","innovate");
        eventKeys.put("Godspeed","godspeed");
        eventKeys.put("How Stuff Works","how-stuff-works");
        eventKeys.put("Building Information Modelling","building-information-modelling");
        eventKeys.put("Circuit Craze","circuit-craze");
        eventKeys.put("Fully Doped","fully-doped");
        eventKeys.put("Chaos Theory","chaos-theory");
        eventKeys.put("Marketing Madness","marketing-madness");
        eventKeys.put("Enigma","enigma");
        eventKeys.put("k! Wallet","k-wallet");
        eventKeys.put("SciTech Quiz","scitech-quiz");
        eventKeys.put("k! Biz Quiz","k-biz-quiz");
        eventKeys.put("k! Open Quiz","k-open-quiz");
        eventKeys.put("k! School Quiz","k-school-quiz");
        eventKeys.put("Mock G20","mock-g20");
        eventKeys.put("k! Spell Bee","k-spell-bee");
        eventKeys.put("Gambling Math","gambling-math");
        eventKeys.put("Alcatraz","alcatraz");
        eventKeys.put("Robowars","robowars");
        eventKeys.put("The Gem Quest","the-gem-quest");
        eventKeys.put("Tanker Bot","tanker-bot");
        eventKeys.put("Tame the Code","tame-the-code");
        eventKeys.put("Heptathlon","heptathlon");
        eventKeys.put("Onsite Programming Contest","onsite-programming-contest");
        eventKeys.put("Online Programming Contest","online-programming-contest");
        eventKeys.put("Contraptions","contraptions");
        eventKeys.put("Ninja Coding","ninja-coding");
        eventKeys.put("k!ardinal Quest","k-ardinal-quest");
        eventKeys.put("The Imitation Game","the-imitation-game");

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
        if(cat.equals("General")){
         events = general;
        }
        else if(cat.equals("Engineering")){
            events = engineering;
        }
        else if(cat.equals("Online")){
            events = online;
        }
        else if(cat.equals("Coding")){
            events = coding;
        }
        else if(cat.equals("Robotics")){
           events = robotics;
        }
        else if(cat.equals("Quiz")){
            events = quiz;
        }
        else{
            events = management;
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
        dataset2.setColors(ColorTemplate.JOYFUL_COLORS);
        dataset2.setDrawValues(false);
        dataset2.setValueTextColor(R.color.white);
        PieData pieData2 = new PieData(eventlist, dataset2);
        pieData2.setValueTextColor(R.color.white);
        eventList.setDescriptionTextSize(100);
        eventList.setDescriptionColor(R.color.white);
        eventList.setData(pieData2);
        eventList.setCenterTextColor(R.color.white);
        eventList.setHoleRadius(40);
        eventList.setTransparentCircleRadius(50);
        eventList.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(XceedActivity.this,EventDetails.class);
                intent.putExtra("name",eventlist.get(e.getXIndex()));
                intent.putExtra("key",eventKeys.get(eventlist.get(e.getXIndex())));
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

}