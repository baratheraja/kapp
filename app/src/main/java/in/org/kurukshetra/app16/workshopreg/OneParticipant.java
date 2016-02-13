package in.org.kurukshetra.app16.workshopreg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import  in.org.kurukshetra.app16.R;
import  in.org.kurukshetra.app16.SessionManager;
import in.org.kurukshetra.app16.app.MyApplication;
import  in.org.kurukshetra.app16.workshopreg.Network2.RegisterCallback;

public class OneParticipant extends AppCompatActivity implements RegisterCallback{

    private String wid;
    private EditText inputName, inputEmail, inputPassword, Student_am, other_detail;
    private RadioGroup rg1;
    private Spinner clsp1,crsp1;
    private CheckBox c1,c2,c3,c4,c5;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_participant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wid = getIntent().getStringExtra("wid");
        Pushbots.sharedInstance().tag("Register form "+ wid);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_phone);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        Student_am=(EditText)findViewById(R.id.std_name);
        other_detail=(EditText)findViewById(R.id.other_detail);

        Student_am.addTextChangedListener(new MyTextWatcher(Student_am));
        other_detail.addTextChangedListener(new MyTextWatcher(other_detail));


        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });



        List<String> c_spinner=new ArrayList<String>();
        List<String> cr_spinner = new ArrayList<String>();

        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        BufferedReader br = null;
        BufferedReader br1 = null;
        String myjsonstring;
        String myjsonstring1;

        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "colleges.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        myjsonstring = sb.toString();
        try {
            JSONArray jsonArray = new JSONArray(myjsonstring);
            for (int i=0;i< jsonArray.length();i++){
                String college = jsonArray.getString(i);
                c_spinner.add(college);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            br1 = new BufferedReader(new InputStreamReader(getAssets().open(
                    "courses.json")));
            String temp1;
            while ((temp1 = br1.readLine()) != null)
                sb1.append(temp1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br1.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        myjsonstring1 = sb1.toString();
        try {
            JSONArray jsonArray1 = new JSONArray(myjsonstring1);
            for (int i=0;i< jsonArray1.length();i++){
                String course = jsonArray1.getString(i);
                cr_spinner.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        Spinner c_spinner1 = (Spinner) findViewById(R.id.college_spinner);
        Spinner cr_spinner1 = (Spinner) findViewById(R.id.course_spinner);

        ArrayAdapter<String> c_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,c_spinner);
        ArrayAdapter<String> cr_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cr_spinner);

        c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        c_spinner1.setAdapter(c_adapter);
        cr_spinner1.setAdapter(cr_adapter);
    }
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if(!validateStudent()){
            return;
        }

        if (!validateother()){
            return;
        }

        rg1=(RadioGroup)findViewById(R.id.radio1);
        if(rg1.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_1  year !", Toast.LENGTH_SHORT).show();
            return;
        }


        clsp1=(Spinner)findViewById(R.id.college_spinner);
        if(clsp1.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_1 college !", Toast.LENGTH_SHORT).show();
            return;
        }


        crsp1=(Spinner)findViewById(R.id.course_spinner);
        if(crsp1.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_1 course !", Toast.LENGTH_SHORT).show();
            return;
        }



        c1=(CheckBox)findViewById(R.id.checkbox_facebook);
        c2=(CheckBox)findViewById(R.id.checkbox_whatsapp);
        c3=(CheckBox)findViewById(R.id.checkbox_student);
        c4=(CheckBox)findViewById(R.id.checkbox_website);
        c5=(CheckBox)findViewById(R.id.checkbox_other);

        if(!c1.isChecked()&&!c2.isChecked()&&!c3.isChecked()&&!c4.isChecked()&&!c5.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Error! Choose How do you came to know ", Toast.LENGTH_SHORT).show();
            return;
        }

        WorkshopRegistration workshopRegistrationObj = assign_values();
        Network2 network2 = new Network2(this);
        network2.registerWorkshop(workshopRegistrationObj);
        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.error));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateStudent() {

        CheckBox st_check =(CheckBox)findViewById(R.id.checkbox_student);

        if(st_check.isChecked()) {

            if (Student_am.getText().toString().trim().isEmpty()) {
                Student_am.setError(getString(R.string.error));
                requestFocus(Student_am);
                return false;
            } else {
                Student_am.setError(null);
            }
        }
        Student_am.setError(null);

        return true;
    }

    private boolean validateother() {

        CheckBox othr_check =(CheckBox)findViewById(R.id.checkbox_student);

        if(othr_check.isChecked()) {

            if (other_detail.getText().toString().trim().isEmpty()) {
                other_detail.setError(getString(R.string.error));
                requestFocus(other_detail);
                return false;
            } else {
                other_detail.setError(null);
            }
        }

        other_detail.setError(null);

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.error));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.error));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void call(String tid) {
        if(tid!=null){
            Intent intent = new Intent(this,TransactionActivity.class);

            SessionManager session= new SessionManager(this);
            session.setTkid(tid);
            Toast.makeText(this,tid,Toast.LENGTH_LONG).show();
            intent.putExtra("wid",wid);
            intent.putExtra("tid",tid);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"no Tid",Toast.LENGTH_LONG).show();
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_phone:
                    validatePassword();
                    break;
            }
        }
    }

    public WorkshopRegistration assign_values(){

        EditText ed_name = (EditText) findViewById(R.id.input_name);
        String name = ed_name.getText().toString();

        EditText ed_phone = (EditText) findViewById(R.id.input_phone);
        String phone = ed_phone.getText().toString();

        EditText ed_email = (EditText) findViewById(R.id.input_email);
        String email = ed_email.getText().toString();



        RadioGroup rg= (RadioGroup)findViewById(R.id.radio1);
        String year = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();




        Spinner sp_college=(Spinner)findViewById(R.id.college_spinner);
        String college = sp_college.getSelectedItem().toString();

        Spinner sp_course=(Spinner)findViewById(R.id.course_spinner);
        String course = sp_course.getSelectedItem().toString();




        CheckBox cb_facebook =(CheckBox)findViewById(R.id.checkbox_facebook);
        boolean facebook=cb_facebook.isChecked();

        CheckBox cb_whatsapp = (CheckBox)findViewById(R.id.checkbox_whatsapp);
        boolean whatsapp=cb_whatsapp.isChecked();

        CheckBox cb_website = (CheckBox)findViewById(R.id.checkbox_website);
        boolean website=cb_website.isChecked();

        CheckBox cb_student = (CheckBox)findViewById(R.id.checkbox_student);
        boolean student=cb_student.isChecked();

        CheckBox cb_other=(CheckBox)findViewById(R.id.checkbox_other);
        boolean other=cb_other.isChecked();


        WorkshopUser workshopUser_obj =new WorkshopUser(name,email,college,course,year,phone);

        List<WorkshopUser> workshopUsers = new ArrayList<>();
        workshopUsers.add(workshopUser_obj);
        WorkshopRegistration workshopRegistration_obj=new WorkshopRegistration(workshopUsers,wid,facebook,whatsapp,student,website,other,"Hi","Hello");
        return workshopRegistration_obj;
    }

    public void expand_student(View view)
    {
        EditText sa_visible=(EditText)findViewById(R.id.std_name);
        CheckBox sa =(CheckBox) findViewById(R.id.checkbox_student);

        if(sa.isChecked())
        {
            sa_visible.setVisibility(View.VISIBLE);
        }
        else
        {
            sa_visible.setVisibility(View.GONE);
        }

    }

    public void expand_other(View view){

        EditText othr_visible=(EditText)findViewById(R.id.other_detail);
        CheckBox othr =(CheckBox) findViewById(R.id.checkbox_other);

        if(othr.isChecked())
        {
            othr_visible.setVisibility(View.VISIBLE);
        }
        else
        {
            othr_visible.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Registering " + wid);
    }
}
