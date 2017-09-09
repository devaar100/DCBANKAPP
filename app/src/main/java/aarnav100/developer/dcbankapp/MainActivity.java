package aarnav100.developer.dcbankapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import java.util.Locale;

import aarnav100.developer.dcbankapp.Adapters.ChatAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private Button sendToBot;
    private ChatAdapter adapter;
    private ArrayList<Pair<String,Boolean>> chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.chatList);
        chats=new ArrayList<>();
        chats.add(new Pair<String, Boolean>("How may I help you ?",Boolean.FALSE));
        adapter=new ChatAdapter(this,chats);
        list.setAdapter(adapter);
        sendToBot=(Button)findViewById(R.id.button_send);
        sendToBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chats.add(new Pair<String, Boolean>(((EditText)findViewById(R.id.edit_text_out)).getText().toString(),
                        Boolean.TRUE));
                adapter.notifyDataSetChanged();
                speechPrompt();
            }
        });
        speechPrompt();
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
            chats.add(new Pair<String, Boolean>(result.get(0),Boolean.FALSE));
            adapter.notifyDataSetChanged();
            Log.d("TAG","Got response here");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
