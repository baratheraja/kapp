package in.org.kurukshetra.app16.workshopreg;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import in.org.kurukshetra.app16.R;

/**
 * Created by baratheraja on 11/1/16.
 */
public class CountChooserFragment extends DialogFragment{

    public static Integer minCount=1,maxCount=1;
    public static String wid;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.count_chooser, container);
        spinner = (Spinner) view.findViewById(R.id.count_spinner);
        ArrayList<String> count = new ArrayList<>();
        for(Integer i = minCount ; i<= maxCount ; i++){
            count.add(i.toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,count);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getDialog().setTitle("No of team mates");
        spinner.setAdapter(arrayAdapter);
        spinner.getSelectedItemPosition();
        Button button = (Button) view.findViewById(R.id.proceed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spinner.getSelectedItemPosition();
                String count = spinner.getItemAtPosition(i).toString();
                if(count.equals("1")){

                    Intent intent = new Intent(getActivity(),OneParticipant.class);
                    intent.putExtra("wid",wid);
                    startActivity(intent);

                }else if(count.equals("2")){

                    Intent intent = new Intent(getActivity(),Twoparticipant.class);
                    intent.putExtra("wid",wid);
                    startActivity(intent);

                }else if(count.equals("3")){

                    Intent intent = new Intent(getActivity(),ThreeParticipant.class);
                    intent.putExtra("wid",wid);
                    startActivity(intent);

                }else if(count.equals("4")){

                    Intent intent = new Intent(getActivity(),FourParticipant.class);
                    intent.putExtra("wid",wid);
                    startActivity(intent);

                }
                dismiss();

            }
        });
        return view;
    }

    public CountChooserFragment() {
    }

    public static CountChooserFragment newInstance(int minCoun,int maxCoun,String id){
        minCount = minCoun;
        maxCount = maxCoun;
        wid = id;
        Bundle args = new Bundle();
        args.putString("DUMMY", "dummy");
        CountChooserFragment f = new CountChooserFragment();
        f.setArguments(args);
        return f;
    }

/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String count = parent.getItemAtPosition(position).toString();
        if(count.equals("1")){

            Intent intent = new Intent(getActivity(),OneParticipant.class);
            intent.putExtra("wid",wid);
            startActivity(intent);

        }else if(count.equals("2")){

            Intent intent = new Intent(getActivity(),Twoparticipant.class);
            intent.putExtra("wid",wid);
            startActivity(intent);

        }else if(count.equals("3")){

            Intent intent = new Intent(getActivity(),ThreeParticipant.class);
            intent.putExtra("wid",wid);
            startActivity(intent);

        }else if(count.equals("4")){

            Intent intent = new Intent(getActivity(),FourParticipant.class);
            intent.putExtra("wid",wid);
            startActivity(intent);

        }
        dismiss();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }*/
}
