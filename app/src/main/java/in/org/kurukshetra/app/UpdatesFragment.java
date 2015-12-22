package in.org.kurukshetra.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class UpdatesFragment extends Fragment {

    HandleJSON obj;
    private static ArrayList<String> list = new ArrayList();
    public static final String My_Pref = "Updatelist";
    SharedPreferences pref;
    SwipeRefreshLayout swipeLayout;
    private static int updatecount = 0;
    public UpdatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    obj = new HandleJSON();

                } catch (Exception e) {

                } finally {
                    setdata();
                }
            }
        };
        t.start();

        list.clear();
        list.add("Plaese connect to the net");
        pref = getActivity().getSharedPreferences(My_Pref,0);
        updatecount = pref.getInt("size", 0);
        if (updatecount > 0)
            list.clear();

        for (int i = 0; i < updatecount; i++) {

            list.add(pref.getString("update" + i, ""));

        }

        View view = inflater.inflate(R.layout.fragment_update, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);
        recyclerView.setAdapter(new UpdatesAdapter(getActivity(),list));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void setdata()
    {
        pref = getActivity().getSharedPreferences(My_Pref, 0);
        SharedPreferences.Editor ed = pref.edit();
        if(obj.count>0)
            ed.putInt("size", obj.count);
        for(int i=0;i<obj.count;i++)
        {
            ed.putString("update"+i,obj.title[i]);
        }
        ed.commit();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final View viewi = view;
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            obj = new HandleJSON();

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            setdata();
                        }
                    }
                    
                };
                t.start();

                pref = getActivity().getSharedPreferences(My_Pref,0);
                updatecount = pref.getInt("size", 0);
                if (updatecount > 0)
                    list.clear();

                for (int i = 0; i < updatecount; i++) {

                    list.add(pref.getString("update" + i, ""));

                }

                RecyclerView recyclerView = (RecyclerView) viewi.findViewById(R.id.itemsRecyclerView);
                recyclerView.setAdapter(new UpdatesAdapter(getActivity(),list));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });;
    }

}
