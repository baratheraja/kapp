package in.org.kurukshetra.app16;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by baratheraja on 23/11/15.
 */

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private LayoutInflater inflater;
    private  Context context;
    public HomeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View root;
        root = inflater.inflate(R.layout.card_item, viewGroup, false);
        HomeViewHolder holder = new HomeViewHolder(root);
        return holder;

    }

    @Override
    public void onBindViewHolder(HomeViewHolder homeViewHolder, int i) {

       /*if(i==0) {
           homeViewHolder.imageView.setImageResource(R.drawable.events_card);
           homeViewHolder.imageView.setContentDescription("Events");
       }
       else if(i==1){
           homeViewHolder.imageView.setImageResource(R.drawable.xceed_card);
           homeViewHolder.imageView.setContentDescription("Xceed");
        }
        else if(i==2){
           homeViewHolder.imageView.setImageResource(R.drawable.gl_card);
           homeViewHolder.imageView.setContentDescription("Gl");
       }
        else {
           homeViewHolder.imageView.setImageResource(R.drawable.workshop_card);
           homeViewHolder.imageView.setContentDescription("Workshops");
       }*/
       }


    @Override
    public int getItemCount() {
        return 4;
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public HomeViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_image);
        }

    }
}