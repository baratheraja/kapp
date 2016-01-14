package in.org.kurukshetra.app16;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by baratheraja on 23/11/15.
 */

class YourRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] dataSource, numberSource;
    private String m_Text = "";
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
                    final int s =  i;
                    yourRecyclerViewHolder.num.setText(numberSource[i]);
                    yourRecyclerViewHolder.fab_dial.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (numberSource[s].equals("+91 95248 99989" )|| numberSource[s].equals("+91 98945 22683")  || numberSource[s].equals("+91 94890 29308")) {
                                showDialogBox(v);
                            }
                            return false;
                        }
                    });

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


    public void showDialogBox(final View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Ask me as such.");

// Set up the input
        final EditText input = new EditText(v.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(m_Text.equals("Houston,"+" "+"we've had a problem here") || m_Text.equals("Houston,"+" "+"we've had a problem") || m_Text.equals("Houston,"+" "+"we have a problem"))
                {
                    Toast.makeText(v.getContext(), "Thats what i'm expecting", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext(),"Swiggert and Lovell didnt report "+m_Text+"!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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