package in.org.kurukshetra.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priya.kurukshetra.R;

/**
 * Created by baratheraja on 23/11/15.
 */

class YourRecyclerAdapter extends RecyclerView.Adapter<YourRecyclerAdapter.YourRecyclerViewHolder> {
    private String[] dataSource,numberSource;
    private LayoutInflater inflater;
    private  Context context;
    public  boolean isContact;
    public YourRecyclerAdapter(Context context,String[] dataArgs) {
        inflater = LayoutInflater.from(context);
        dataSource = dataArgs;
        this.context=context;
    }
    public YourRecyclerAdapter(Context context,String[] dataArgs,String[] numArgs) {
        inflater = LayoutInflater.from(context);
        dataSource = dataArgs;
        numberSource=numArgs;
        this.context=context;
        isContact=true;
    }

    @Override
    public YourRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


             View root;

            if(!isContact)
            root = inflater.inflate(R.layout.item, viewGroup, false);
            else
            root = inflater.inflate(R.layout.item1, viewGroup, false);


        YourRecyclerViewHolder holder = new YourRecyclerViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(YourRecyclerViewHolder yourRecyclerViewHolder, int i) {
        yourRecyclerViewHolder.textView.setText(Html.fromHtml(dataSource[i]));

        if(isContact)
            yourRecyclerViewHolder.num.setText(numberSource[i]);


    }


    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    static class YourRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView num;

        public YourRecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_item);

            try {
                num= (TextView) itemView.findViewById(R.id.number);
            }
            catch (Exception e) {

                 }

        }
    }
}