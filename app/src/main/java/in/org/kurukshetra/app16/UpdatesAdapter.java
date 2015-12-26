package in.org.kurukshetra.app16;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by baratheraja on 23/11/15.
 */

class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdatesViewHolder> {

    private LayoutInflater inflater;
    private  Context context;
    private ArrayList list = new ArrayList();
    public UpdatesAdapter(Context context,ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.context=context;
        this.list = list;
    }

    @Override
    public UpdatesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View root;
        root = inflater.inflate(R.layout.update_card_item, viewGroup, false);
        UpdatesViewHolder holder = new UpdatesViewHolder(root);
        return holder;

    }

    @Override
    public void onBindViewHolder(UpdatesViewHolder homeViewHolder, int i) {

        homeViewHolder.textView.setText(list.get(i).toString());

    }

    public void updateItem(ArrayList list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UpdatesViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public UpdatesViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.update);
        }

    }
}