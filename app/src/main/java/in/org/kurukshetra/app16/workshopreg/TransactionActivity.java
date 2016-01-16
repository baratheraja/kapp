package in.org.kurukshetra.app16.workshopreg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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
    private void initKeys() {
        eventKeys.put("IBM's Cognitive Computing","ibm-s-cognitive-computing");
        eventKeys.put("Creative Coding","creative-coding");
        eventKeys.put("Growth Hacking","growth-hacking");
        eventKeys.put("Krithi","krithi");
        eventKeys.put("Eye Controlled Robots","eye-controlled-robots");
        eventKeys.put("Distance Relays and Substation Automation","distance-relays-and-substation-automation");
        eventKeys.put("Build Your 3D Printer","build-your-3d-printer");
        eventKeys.put("ESRI's Connective Convergence","esri-s-connective-convergence");
        eventKeys.put("Samsung's Virtual Reality","samsung-s-virtual-reality");
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

        String tid = getIntent().getStringExtra("tid");
        initKeys();
        String wname = getKeyByValue(eventKeys,wid);
        TextView tv1 = (TextView) findViewById(R.id.tid);
        String t = "<div class=\"container\" style=\"margin-top:50px; color:#c8fffa;\">\n" +
                "\t<h1>Your transaction id is: <b>"+ tid +"</b></h1>\n" +
                "\t<p>\n" +
                "\t\t<h2>Online Payment Instructions</h2>\n" +
                "\t</p>\n" +
                "</br>\n" +
                "\t<div style=\"   ;\">\n" +
                "\t<li>\n" +
                "\t\tNote down the Transaction id.\n" +
                "\t</li><br/>\n" +
                "    <li>\n" +
                "   \t\tIn the payment page, Accept the terms and conditions and click “PROCEED”. </li>\n" +
                "<br/>\n" +
                "<li>In the next page, Select <b>"+ wname +" Workshop </b> category, fill Transaction id and other details correctly and click “SUBMIT” button.</li>\n" +
                "<br/>\n" +
                "<li>Once finished with entering all the required details, click “CONFIRM” button to proceed. </li><br/>\n" +
                "<li>Make payment as per your convenience. (Options available are payment of fees through SBI Net Banking, State bank ATM cum Debit Cards / Other Bank Debit / Credit Cards and through SBI Branches).\n" +
                "</li><br/>\n" +
                "<li> SAVE and Keep copy of receipt for future reference. </li><br/>\n" +
                "\t</div>\n" +
                "\t<div style=\"padding-left:50px;\">\n" +
                "\t<a class=\"btn btn-success btn-lg\" href=\"https://www.onlinesbi.com/prelogin/icollecthome.htm?corpID=607468\" target=\"_blank\">Pay Now</a></div><br/>\n" +
                "\t\n" +
                "\t<p>\n" +
                "\t\t<h2>DD Payment Instructions</h2>\n" +
                "\t\t\t<div style=\"   ;\">\n" +
                "\t\t\t<li>The\tworkshop fee is Rs./-.</li><br/>\n" +
                "\t\t\t<li>Demand Draft can be drawn in any Nationalized bank in favor of <b>\"CEG\tTECH FORUM”</b> and it should be sent to the following address:</li><br/>\n" +
                "\t<div style=\"padding-left:25%;\"><P><B>SHARUKH MOHAMED M,</B></P>\n" +
                "\t<P ><B>STUDENT DIRECTOR-FINANCE,</B></P>\n" +
                "\t<P ><B>ROOM-15, BLOCK-6,</B></P>\n" +
                "\t<P ><B>ENGINEERING COLLEGE HOSTELS,</B></P>\n" +
                "\t<p><b>ANNA UNIVERSITY,</b></p>\n" +
                "\t<p><b>CHENNAI,</b></p>\n" +
                "\t<p><b>TAMIL NADU -600025.</b></p></div><br/>\n" +
                "    <li><b>Note:</b></li></br>\n" +
                "    <div style=\"padding-left:50px;\">\n" +
                "\t<P><I>1.The DD has to be taken only in favor of “CEG TECH FORUM”. DDs favoring any other names will not be accepted.</I></P>\n" +
                "\t<P><I>2. Write your <b> Transaction ID, Team Leader Name, Team Leader K ID, Team Leader Phone Number, Mail ID, College, Workshop Name, Student Ambassador Referral ID (if you are referred by a student Ambassador) </b> at\tthe back of the DD.</I></P>\n" +
                "\tLast date for DD payment is\t\n" +
                "   </div><br/>\n" +
                "   <p>We have sent a mail to your registered mail address with all the above instructions, incase you wish to pay at a later stage. Kindly pay as soon as possible to confirm your registration.</p>\n" +
                "\n" +
                "</div>\n" +
                "</p>\n" +
                "<div>\n" +
                "\t<input style=\"height:100px; visibility:hidden;\"></input>\n" +
                "</div>\n" +
                "</div>\n";
                tv1.setText(Html.fromHtml(t));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Registering " + wid);
    }

}
