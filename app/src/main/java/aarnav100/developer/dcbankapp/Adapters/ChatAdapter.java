package aarnav100.developer.dcbankapp.Adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import aarnav100.developer.dcbankapp.R;

/**
 * Created by aarnavjindal on 09/09/17.
 */

public class ChatAdapter extends BaseAdapter {
    Context context;
    ArrayList<Pair<String,Boolean>> strings;

    public ChatAdapter(Context context, ArrayList<Pair<String, Boolean>> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Pair<String,Boolean> getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Pair<String,Boolean> pair=getItem(i);
        View v;
        LayoutInflater li=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        if(pair.second.equals(false)){
            v=li.inflate(R.layout.bot_chat,null);
            ((TextView)v.findViewById(R.id.chat)).setText(pair.first);
        }
        else{
            v=li.inflate(R.layout.user_chat,null);
            ((TextView)v.findViewById(R.id.chat)).setText(pair.first);
        }
        return v;
    }
}
