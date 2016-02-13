package in.org.kurukshetra.app16.workshopreg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pushbots.push.Pushbots;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import  in.org.kurukshetra.app16.R;
import in.org.kurukshetra.app16.app.MyApplication;

public class TransactionActivity extends AppCompatActivity {

    private Map<String, String> eventKeys = new HashMap<>();
    String wid;
    Button pay;
    private void initKeys() {
        eventKeys.put("IBM's Cognitive Computing","IC");
        eventKeys.put("Creative Coding","CC");
        eventKeys.put("Growth Hacking","GH");
        eventKeys.put("Krithi","KR");
        eventKeys.put("Eye Controlled Robots","EC");
        eventKeys.put("Distance Relays and Substation Automation","DR");
        eventKeys.put("Build Your 3D Printer","3D");
        eventKeys.put("ESRI's Connective Convergence","GI");
        eventKeys.put("Samsung's Virtual Reality","VR");
    }


    public static String  getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        wid = getIntent().getStringExtra("wid");

        Pushbots.sharedInstance().tag("Register success "+ wid);
        pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.onlinesbi.com/prelogin/icollecthome.htm?corpID=60746");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        String tid = getIntent().getStringExtra("tid");
        initKeys();
        String wname = getKeyByValue(eventKeys,wid);
        TextView tv1 = (TextView) findViewById(R.id.tid);
        TextView tv2 = (TextView) findViewById(R.id.dd);
        String t = "<div class=\"container\" style=\"margin-top:50px; color:#c8fffa;\">\n" +
                "<h1>Your transaction id is: <b>"+ tid +"</b></h1>\n" +
                "<p>\n" +
                "<h2>Online Payment Instructions</h2>\n" +
                "</p>\n" +
                "</br>\n" +
                "<div style=\"   ;\">\n" +
                "<li>\n" +
                "Note down the Transaction id.\n" +
                "</li><br/>\n" +
                "<li>\n" +
                "In the payment page, Accept the terms and conditions and click “PROCEED”. </li>\n" +
                "<br/>\n" +
                "<li>In the next page, Select <b>"+ wname +" Workshop </b> category, fill Transaction id and other details correctly and click “SUBMIT” button.</li>\n" +
                "<br/>\n" +
                "<li>Once finished with entering all the required details, click “CONFIRM” button to proceed. </li><br/>\n" +
                "<li>Make payment as per your convenience. (Options available are payment of fees through SBI Net Banking, State bank ATM cum Debit Cards / Other Bank Debit / Credit Cards and through SBI Branches).\n" +
                "</li><br/>\n" +
                "<li> SAVE and Keep copy of receipt for future reference. </li><br/>\n" +
                "</div>\n" +
                "<div style=\"padding-left:50px;\">\n" +
                "\n";
        tv1.setText(Html.fromHtml(t));
        t= "<p>\n" +
                "<h2>DD Payment Instructions</h2>\n" +
                "<div style=\"   ;\">\n" +
                "<li>Demand Draft can be drawn in any Nationalized bank in favor of <b>\"CEG\tTECH FORUM”</b> and it should be sent to the following address:</li><br/>\n" +
                "<div style=\"padding-left:25%;\"><P><B>SHARUKH MOHAMED M,</B></P>\n" +
                "<P ><B>STUDENT DIRECTOR-FINANCE,</B></P>\n" +
                "<P ><B>ROOM-15, BLOCK-6,</B></P>\n" +
                "<P ><B>ENGINEERING COLLEGE HOSTELS,</B></P>\n" +
                "<p><b>ANNA UNIVERSITY,</b></p>\n" +
                "<p><b>CHENNAI,</b></p>\n" +
                "<p><b>TAMIL NADU -600025.</b></p></div><br/>\n" +
                "<li><b>Note:</b></li></br>\n" +
                "<div style=\"padding-left:50px;\">\n" +
                "<P><I>1.The DD has to be taken only in favor of “CEG TECH FORUM”. DDs favoring any other names will not be accepted.</I></P>\n" +
                "<P><I>2. Write your <b> Transaction ID, Team Leader Name, Team Leader K ID, Team Leader Phone Number, Mail ID, College, Workshop Name, Student Ambassador Referral ID (if you are referred by a student Ambassador) </b> at\tthe back of the DD.</I></P>\n" +
                "Last date for DD payment is\t\n" +
                "</div><br/>\n" +
                "<p>We have sent a mail to your registered mail address with all the above instructions, incase you wish to pay at a later stage. Kindly pay as soon as possible to confirm your registration.</p>\n" +
                "\n" +
                "</div>\n" +
                "</p>\n" +
                "<div>\n" +
                "<input style=\"height:100px; visibility:hidden;\"></input>\n" +
                "</div>\n" +
                "</div>\n";
        tv2.setText(Html.fromHtml(t));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Registering " + wid);
    }

}
