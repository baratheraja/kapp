package in.org.kurukshetra.app16;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import in.org.kurukshetra.app16.workshopreg.FourParticipant;
import in.org.kurukshetra.app16.workshopreg.OneParticipant;
import in.org.kurukshetra.app16.workshopreg.ThreeParticipant;
import in.org.kurukshetra.app16.workshopreg.Twoparticipant;

/**
 * Created by baratheraja on 11/1/16.
 */
public class CollegeChooserFragment extends DialogFragment{

    public static Integer minCount=1,maxCount=1;
    public static String wid;
    OnCollegeListener mOncollege;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mOncollege = (OnCollegeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLoginListener");
        }
    }

    public interface OnCollegeListener {
        void onCollege(String college);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.college_chooser, container);

        getDialog().setTitle("Enter your college Name");
        final EditText collegeE = (EditText) view.findViewById(R.id.input_college);
        Button button = (Button) view.findViewById(R.id.proceed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String college = collegeE.getText().toString();
                mOncollege.onCollege(college);
                dismiss();
            }
        });
        return view;
    }

    public CollegeChooserFragment() {
    }

    public static CollegeChooserFragment newInstance(){
        Bundle args = new Bundle();
        args.putString("DUMMY", "dummy");
        CollegeChooserFragment f = new CollegeChooserFragment();
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
