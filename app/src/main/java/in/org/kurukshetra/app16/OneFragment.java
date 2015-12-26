package in.org.kurukshetra.app16;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class  OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        RecyclerView recyclerView = new RecyclerView(getActivity());

        if(args.getString("mail_id") != null)
            recyclerView.setAdapter(new YourRecyclerAdapter(getActivity(),args.getStringArray("value"),args.getStringArray("number"), args.getString("mail_id")));
        else if ( args.getStringArray("number")!= null)
            recyclerView.setAdapter(new YourRecyclerAdapter(getActivity(),args.getStringArray("value"),args.getStringArray("number")));
        else
            recyclerView.setAdapter(new YourRecyclerAdapter(getActivity(),args.getStringArray("value")));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

}