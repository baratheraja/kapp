package in.org.kurukshetra.app16;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class
        UpdatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    HandleJSON obj;
    private static ArrayList<String> list = new ArrayList();
    public static final String My_Pref = "Updatelist";
    SharedPreferences pref;
    SwipeRefreshLayout swipeLayout;
    private static int updatecount = 0;
    public UpdatesFragment() {
        // Required empty public constructor
    }
UpdatesAdapter updatesAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    list.clear();
    list.add("Please connect to the internet");
    pref = getActivity().getSharedPreferences(My_Pref, 0);
    updatecount = pref.getInt("size", 0);
    if (updatecount > 0)
        list.clear();

    for (int i = 0; i < updatecount; i++) {

        list.add(pref.getString("update" + i, ""));

    }
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);
        updatesAdapter = new UpdatesAdapter(getActivity(),list);
        recyclerView.setAdapter(updatesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUpdte();
    }

    public void setdata()
    {
        try {
            pref = getActivity().getSharedPreferences(My_Pref, 0);
            SharedPreferences.Editor ed = pref.edit();
            if (obj.count > 0)
                ed.putInt("size", obj.count);
            for (int i = 0; i < obj.count; i++) {
                ed.putString("update" + i, obj.title[i]);
            }
            ed.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final View viewi = view;
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeLayout.setOnRefreshListener(this);;
    }



    @Override
    public void onRefresh() {
        loadUpdte();
    }

    public void loadUpdte(){

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    obj = new HandleJSON();
                    setdata();
                    pref = getActivity().getSharedPreferences(My_Pref,0);
                    updatecount = pref.getInt("size", 0);
                    if (updatecount > 0)
                        list.clear();

                    for (int i = 0; i < updatecount; i++) {
                        list.add(pref.getString("update" + i , ""));
                    }
                    updatesAdapter.updateItem(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                swipeLayout.setRefreshing(false);
                super.onPostExecute(result);
                updatesAdapter.notifyDataSetChanged();

            }
        }.execute();
    }
}
