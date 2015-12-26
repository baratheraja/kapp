package in.org.kurukshetra.app16;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by baratheraja on 23/11/15.
 */

class YourRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] dataSource, numberSource;
    private String mail_id;
    private LayoutInflater inflater;
    private Context context;
    public boolean isContact;
    private boolean isMail;
    private int position;

    public YourRecyclerAdapter(Context context, String[] dataArgs) {
        inflater = LayoutInflater.from(context);
        dataSource = dataArgs;
        this.context = context;
    }

    public YourRecyclerAdapter(Context context, String[] dataArgs, String[] numArgs,String mail_id) {
        inflater = LayoutInflater.from(context);
        dataSource = dataArgs;
        numberSource = numArgs;
        this.mail_id=mail_id;
        this.context = context;
        isContact = true;
    }
    public YourRecyclerAdapter(Context context, String[] dataArgs, String[] numArgs) {
        inflater = LayoutInflater.from(context);
        dataSource = dataArgs;
        numberSource = numArgs;
        this.context = context;
        isContact = true;
    }

    @Override
    public int getItemViewType(int position) {

        if(position<dataSource.length)
            return 0;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View root;

        if(i==1)
            root = inflater.inflate(R.layout.item2, viewGroup, false);
        else if (!isContact)
            root = inflater.inflate(R.layout.item, viewGroup, false);
        else
            root = inflater.inflate(R.layout.item1, viewGroup, false);


        YourRecyclerViewHolder holder = new YourRecyclerViewHolder(root);
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {

        final YourRecyclerViewHolder yourRecyclerViewHolder= (YourRecyclerViewHolder)viewHolder;
      //  URLImageParser p = new URLImageParser(yourRecyclerViewHolder.textView, context);

        if(i<dataSource.length)
            yourRecyclerViewHolder.textView.setText(Html.fromHtml(dataSource[i]));

        if (isContact) {




            try {
                if(i<dataSource.length) {
                    yourRecyclerViewHolder.num.setText(numberSource[i]);
                    yourRecyclerViewHolder.fab_dial.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            String phno = yourRecyclerViewHolder.num.getText().toString();
                            callIntent.setData(Uri.parse("tel:" + phno));
                            v.getContext().startActivity(callIntent);
                        }
                    });
                }
                else {
                    yourRecyclerViewHolder.email.setText(mail_id);


                    yourRecyclerViewHolder.fab_mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mail_id));
                            v.getContext().startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public int getItemCount() {

        if(isContact)
            return dataSource.length+1;

        return dataSource.length;
    }

    static class  YourRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView num;
        TextView email;
        FloatingActionButton fab_dial,fab_mail;


        public YourRecyclerViewHolder(View itemView) {
            super(itemView);


            textView = (TextView) itemView.findViewById(R.id.list_item);


            try {
                num = (TextView) itemView.findViewById(R.id.number);
                email = (TextView) itemView.findViewById(R.id.mail);
                fab_dial = (FloatingActionButton) itemView.findViewById(R.id.myFAB);
                fab_mail = (FloatingActionButton) itemView.findViewById(R.id.myFAB1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}