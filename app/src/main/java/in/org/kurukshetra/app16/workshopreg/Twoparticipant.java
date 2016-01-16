package in.org.kurukshetra.app16.workshopreg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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

import in.org.kurukshetra.app16.R;
import in.org.kurukshetra.app16.SessionManager;
import in.org.kurukshetra.app16.app.MyApplication;

import static in.org.kurukshetra.app16.workshopreg.Network2.*;

public class Twoparticipant extends AppCompatActivity implements RegisterCallback {

    private String wid;
    private Toolbar toolbar;
    private EditText inputName1, inputEmail1, inputPassword1;
    private EditText inputName2, inputEmail2, inputPassword2;
    private EditText Student_am, other_detail;
    private RadioGroup rg1,rg2;
    private Spinner clsp1,clsp2,crsp1,crsp2;
    private CheckBox c1,c2,c3,c4,c5;
    private TextInputLayout inputLayoutName2, inputLayoutEmail2, inputLayoutPassword2;
    private TextInputLayout inputLayoutName1, inputLayoutEmail1, inputLayoutPassword1;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_participant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wid = getIntent().getStringExtra("wid");

        Pushbots.sharedInstance().tag("Register form "+ wid);
        inputLayoutName1 = (TextInputLayout) findViewById(R.id.input_layout_name1);
        inputLayoutEmail1 = (TextInputLayout) findViewById(R.id.input_layout_email1);
        inputLayoutPassword1 = (TextInputLayout) findViewById(R.id.input_layout_password1);
        inputName1 = (EditText) findViewById(R.id.input_name1);
        inputEmail1 = (EditText) findViewById(R.id.input_email1);
        inputPassword1= (EditText) findViewById(R.id.input_phone1);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputLayoutName2 = (TextInputLayout) findViewById(R.id.input_layout_name2);
        inputLayoutEmail2 = (TextInputLayout) findViewById(R.id.input_layout_email2);
        inputLayoutPassword2 = (TextInputLayout) findViewById(R.id.input_layout_password2);
        inputName2 = (EditText) findViewById(R.id.input_name2);
        inputEmail2 = (EditText) findViewById(R.id.input_email2);
        inputPassword2 = (EditText) findViewById(R.id.input_phone2);
        btnSignUp = (Button) findViewById(R.id.btn_signup);


        Student_am=(EditText)findViewById(R.id.std_name);
        other_detail=(EditText)findViewById(R.id.other_detail);

        Student_am.addTextChangedListener(new MyTextWatcher(Student_am));
        other_detail.addTextChangedListener(new MyTextWatcher(other_detail));

        inputName1.addTextChangedListener(new MyTextWatcher(inputName1));
        inputEmail1.addTextChangedListener(new MyTextWatcher(inputEmail1));
        inputPassword1.addTextChangedListener(new MyTextWatcher(inputPassword1));

        inputName2.addTextChangedListener(new MyTextWatcher(inputName2));
        inputEmail2.addTextChangedListener(new MyTextWatcher(inputEmail2));
        inputPassword2.addTextChangedListener(new MyTextWatcher(inputPassword2));


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




        Spinner c_spinner1 = (Spinner) findViewById(R.id.college_spinner1);
        Spinner cr_spinner1 = (Spinner) findViewById(R.id.course_spinner1);

        Spinner c_spinner2 = (Spinner) findViewById(R.id.college_spinner2);
        Spinner cr_spinner2 = (Spinner) findViewById(R.id.course_spinner2);

        ArrayAdapter<String> c_adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,c_spinner);
        ArrayAdapter<String> cr_adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cr_spinner);

        c_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        c_spinner1.setAdapter(c_adapter1);
        cr_spinner1.setAdapter(cr_adapter1);

        ArrayAdapter<String> c_adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,c_spinner);
        ArrayAdapter<String> cr_adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cr_spinner);

        c_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        c_spinner2.setAdapter(c_adapter2);
        cr_spinner2.setAdapter(cr_adapter2);


    }



    private void submitForm() {
        if (!validateName1()) {
            return;
        }
        if (!validateName2()) {
            return;
        }

        if (!validateEmail1()) {
            return;
        }

        if (!validateEmail2()) {
            return;
        }

        if (!validatePassword1()) {
            return;
        }

        if (!validatePassword2()) {
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

        rg2=(RadioGroup)findViewById(R.id.radio2);
        if(rg2.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_2 year !", Toast.LENGTH_SHORT).show();
            return;
        }



        clsp1=(Spinner)findViewById(R.id.college_spinner1);
        if(clsp1.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_1 college !", Toast.LENGTH_SHORT).show();
            return;
        }

        clsp2=(Spinner)findViewById(R.id.college_spinner2);
        if(clsp2.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_2 college !", Toast.LENGTH_SHORT).show();
            return;
        }





        crsp1=(Spinner)findViewById(R.id.course_spinner1);
        if(crsp1.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_1 course !", Toast.LENGTH_SHORT).show();
            return;
        }

        crsp2=(Spinner)findViewById(R.id.course_spinner2);
        if(crsp2.getSelectedItemPosition()==0)
        {
            Toast.makeText(getApplicationContext(), "Error! Choose Participant_2 course !", Toast.LENGTH_SHORT).show();
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


        WorkshopRegistration workshopRegistrationObj =  assign_values();
        Network2 network2 = new Network2(this);
        network2.registerWorkshop(workshopRegistrationObj);

        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName1() {
        if (inputName1.getText().toString().trim().isEmpty()) {
            inputLayoutName1.setError(getString(R.string.error));
            requestFocus(inputName1);
            return false;
        } else {
            inputLayoutName1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName2() {
        if (inputName2.getText().toString().trim().isEmpty()) {
            inputLayoutName2.setError(getString(R.string.error));
            requestFocus(inputName2);
            return false;
        } else {
            inputLayoutName2.setErrorEnabled(false);
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

    private boolean validateEmail1() {
        String email = inputEmail1.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail1.setError(getString(R.string.error));
            requestFocus(inputEmail1);
            return false;
        } else {
            inputLayoutEmail1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail2() {
        String email = inputEmail2.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail2.setError(getString(R.string.error));
            requestFocus(inputEmail2);
            return false;
        } else {
            inputLayoutEmail2.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword1() {
        if (inputPassword1.getText().toString().trim().isEmpty()) {
            inputLayoutPassword1.setError(getString(R.string.error));
            requestFocus(inputPassword1);
            return false;
        } else {
            inputLayoutPassword1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword2() {
        if (inputPassword2.getText().toString().trim().isEmpty()) {
            inputLayoutPassword2.setError(getString(R.string.error));
            requestFocus(inputPassword2);
            return false;
        } else {
            inputLayoutPassword2.setErrorEnabled(false);
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
            //intent.putExtra("wid",wid);
            //intent.putExtra("tid",tid);
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
                case R.id.input_name1:
                    validateName1();
                    break;
                case R.id.input_email1:
                    validateEmail1();
                    break;
                case R.id.input_phone1:
                    validatePassword1();
                    break;

                case R.id.input_name2:
                    validateName2();
                    break;
                case R.id.input_email2:
                    validateEmail2();
                    break;
                case R.id.input_phone2:
                    validatePassword2();
                    break;
            }
        }
    }



    public WorkshopRegistration assign_values(){

        EditText ed_name1 = (EditText) findViewById(R.id.input_name1);
        String name1 = ed_name1.getText().toString();

        EditText ed_phone1 = (EditText) findViewById(R.id.input_phone1);
        String phone1 = ed_phone1.getText().toString();

        EditText ed_email1 = (EditText) findViewById(R.id.input_email1);
        String email1 = ed_email1.getText().toString();


        EditText ed_name2 = (EditText) findViewById(R.id.input_name2);
        String name2 = ed_name2.getText().toString();

        EditText ed_phone2 = (EditText) findViewById(R.id.input_phone2);
        String phone2 = ed_phone2.getText().toString();

        EditText ed_email2 = (EditText) findViewById(R.id.input_email2);
        String email2 = ed_email2.getText().toString();



        RadioGroup rg1= (RadioGroup)findViewById(R.id.radio1);
        String year1 = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

        RadioGroup rg2= (RadioGroup)findViewById(R.id.radio2);
        String year2 = ((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString();




        Spinner sp_college1=(Spinner)findViewById(R.id.college_spinner1);
        String college1 = sp_college1.getSelectedItem().toString();

        Spinner sp_course1=(Spinner)findViewById(R.id.course_spinner1);
        String course1 = sp_course1.getSelectedItem().toString();

        Spinner sp_college2=(Spinner)findViewById(R.id.college_spinner2);
        String college2 = sp_college2.getSelectedItem().toString();

        Spinner sp_course2=(Spinner)findViewById(R.id.course_spinner2);
        String course2 = sp_course2.getSelectedItem().toString();




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


        WorkshopUser workshopUser_obj1 =new WorkshopUser(name1,email1,college1,course1,year1,phone1);
        WorkshopUser workshopUser_obj2 =new WorkshopUser(name2,email2,college2,course2,year2,phone2);

        List<WorkshopUser> workshopUsers = new ArrayList<>();
        workshopUsers.add(workshopUser_obj1);
        workshopUsers.add(workshopUser_obj2);

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
