package aarnav100.developer.dcbankapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import aarnav100.developer.dcbankapp.Adapters.LogAdapter;
import aarnav100.developer.dcbankapp.Classes.TLog;



public class LogBook extends AppCompatActivity {
    public static final String TAG = "BankApp error";
    private ListView list;
    private LogAdapter adapter;
    private ArrayList<TLog> logs=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.headColor));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.headColor)));
        list=(ListView)findViewById(R.id.log_list);
        adapter=new LogAdapter(logs,this);
        list.setAdapter(adapter);
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logs.clear();
                ((ProgressBar)findViewById(R.id.pro1)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.tv_bal)).setText(dataSnapshot.child("Balance").getValue().toString());
                for(DataSnapshot data:dataSnapshot.child("Transaction").getChildren()){
                    TLog log=new TLog();
                    log.setDate(data.getKey());
                    log.setAmount(data.child("Amount").getValue().toString());
                    log.setTo(data.child("Sent_To").getValue().toString());
                    logs.add(log);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,databaseError.getMessage());
            }
        });
    }
}
