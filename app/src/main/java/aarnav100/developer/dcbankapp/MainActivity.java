package aarnav100.developer.dcbankapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import aarnav100.developer.dcbankapp.Adapters.ChatAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    int flag=0;
    private Button sendToBot;
    private ChatAdapter adapter;
    private ArrayList<Pair<String,Boolean>> chats;
    public static String strsub;
    Set<String> names = new HashSet<>();
    Set<String> prod = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.headColor));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.headColor)));
        names.add("deepanshu");
        names.add("daizy");
        names.add("arnav");
        names.add("9899150985");
        names.add("barbeque nation");
        prod.add("iphone");
        prod.add("watch");
        prod.add("book");
        prod.add("shirt");
        prod.add("kindle");
        list=(ListView)findViewById(R.id.chatList);
        chats=new ArrayList<>();
        chats.add(new Pair<String, Boolean>("How may I help you ?",Boolean.FALSE));
        adapter=new ChatAdapter(this,chats);
        list.setAdapter(adapter);
        sendToBot=(Button)findViewById(R.id.button_send);
        sendToBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=((EditText)findViewById(R.id.edit_text_out)).getText().toString();
                chats.add(new Pair<String, Boolean>(str, Boolean.TRUE));
                adapter.notifyDataSetChanged();
                //speechPrompt();
                chats.add(new Pair<String, Boolean>(input(str),Boolean.FALSE));
            }
        });
    }
    void speechPrompt(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Start speech recognition");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK&&requestCode==100){
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String ans=input(result.get(0));
            Log.d("TAG",ans);
            chats.add(new Pair<String, Boolean>(ans,Boolean.FALSE));
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String input(String str) {

        if (checkend(str)) {
//            if (num == 1) {
//                return ("Your task is completed.");
//            }
//
//            if (num == 2) {
//                return ("Okay.Remember me when you have trouble");
//            }
//
//            if (num == 0) {
                return ("Want to do something else?");
            //}


        } else if (checkpay(str)) {
            flag = 2;
            return ("Whom shall i make the payment to ?");


        } else if (checkbuy(str, prod) > 0) {
            if (checkbuy(str, prod) == 2) {
                return ("open product link for " + strsub);
            } else if (checkbuy(str, prod) == 1) {

                flag = 6;
                return ("What do you want to buy my friend?");

            }
        } else if (checkPassbook(str)) {
            return ("Display passbook"); //display the passbook instead
        }


        if (flag == 2) {
            str = str.toLowerCase();
            String amount;
            if (checkperson(str, names)) {
                flag = 3;
                return ("Enter amount to pay");

            } else {
                flag = 4;

                return ("The person is not in your contacts.Enter his or her mobile number");
            }
        }

        if (flag == 3) {
            // open payment gateway
            startActivity(new Intent(MainActivity.this,PaymentPage.class));
            return ("authorize your payment //opens camera or figerprint for authorization");
        }
        if (flag == 4) {
            flag = 5;
            names.add(str);
            return ("Give me the amount you want to send");

        }
        if (flag == 5) {

            //open payment gateway
            startActivity(new Intent(MainActivity.this,PaymentPage.class));
            return ("authorize your payment //opens camera or figerprint for authorization");


        }

        if (flag == 6) {
            if (checkprod(str, prod)) {


                if(true) {
                    return ("Your account balance is low.");
                } else {

                    //open product page
                    return ("Opens product link");
                }
            } else {

                return ("Product unavailaible");
            }
        }
        return "";
    }







    public static int checkbuy(String str,Set<String> prod) {
        // TODO Auto-generated method stub
        str = str.toLowerCase();
        if(str.contains("buy")||str.contains("browse")||str.contains("directory")||str.contains("list")||str.contains("product"))
        {
            for(int i=0;i<str.length()-1;i++)
            {
                for(int j=i+1;j<=str.length();j++)
                {
                    if(prod.contains(str.substring(i,j)))
                    {
                        strsub=str.substring(i,j);
                        return 2;
                    }
                }
            }
            return 1;
        }

        return 0;
    }


    public static boolean checkprod(String buy, Set<String> prod) {
        // TODO Auto-generated method stub
        buy =buy.toLowerCase();
        for(int i=0;i<buy.length()-1;i++)
        {
            for(int j=i+1;j<=buy.length();j++)
            {
                if(prod.contains(buy.substring(i,j)))
                    return true;
            }
        }


        return false;


    }




    public static boolean checkperson(String str,Set<String> names) {
        // TODO Auto-generated method stub
        for(int i=0;i<str.length()-1;i++)
        {
            for(int j=i+1;j<=str.length();j++)
            {
                if(names.contains(str.substring(i,j)))
                    return true;
            }
        }

        return false;
    }


    public static boolean checkpay(String str) {
        // TODO Auto-generated method stub
        str = str.toLowerCase();
        if(str.contains("pay")||str.contains("give"))
            return true;

        return false;
    }


    public static boolean checkend(String str)
    {
        str = str.toLowerCase();
        for(int i=0;i<str.length()-1;i++)
        {
            for(int j=i+1;j<=str.length();j++)
            {
                if(str.substring(i,j).equals("bye"))
                    return true;
                if(str.substring(i,j).equals("thank"))
                    return true;
                if(str.substring(i,j).equals("done"))
                    return true;
            }
        }

        return false;
    }

    public static boolean checkPassbook(String str) {

        if(str.toLowerCase().contains("passbook"))
            return true;

        return false;

    }

}
