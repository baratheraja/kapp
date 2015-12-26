package in.org.kurukshetra.app16;

import java.util.HashMap;

/**
 * Created by N.Yuvanesh on 22-12-2015.
 */
public class XceedContactDetails {

    public   HashMap<String,String[]> names = new HashMap<>();
    public   HashMap<String,String[]> ph_no = new HashMap<>();
    public  HashMap<String,String> mail_id = new HashMap<>();

    public   void init()
    {
        names.put("how-stuff-works",new String[]{"Sabarish.V.N","Rahul Vignesh"});
        names.put("chaos-theory",new String[]{"Adharsh Gukan","Rahul Vignesh"});
        names.put("chaos-theory-edb72509-27cc-46a7-b84b-744cec0c24e4",new String[]{"Vibha Sridhar","Rahul Vignesh"});
        names.put("biz-quiz",new String[]{"Tharunya","Rahul Vignesh"});


        ph_no.put("how-stuff-works",new String[]{"+91 87544 55024","+919551357967"});
        ph_no.put("chaos-theory",new String[]{"+919487539200","+919551357967"});
        ph_no.put("chaos-theory-edb72509-27cc-46a7-b84b-744cec0c24e4",new String[]{"+91 95517 32289","+919551357967"});
        ph_no.put("biz-quiz",new String[]{"+91 99425 82259","+919551357967"});


        mail_id.put("how-stuff-works","xceed@kurukshetra.org.in");
        mail_id.put("chaos-theory","xceed@kurukshetra.org.in");
        mail_id.put("chaos-theory-edb72509-27cc-46a7-b84b-744cec0c24e4","xceed@kurukshetra.org.in");
        mail_id.put("biz-quiz","xceed@kurukshetra.org.in" );

    }
}