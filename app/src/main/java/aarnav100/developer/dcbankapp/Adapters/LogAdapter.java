package aarnav100.developer.dcbankapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import aarnav100.developer.dcbankapp.Classes.TLog;
import aarnav100.developer.dcbankapp.R;

/**
 * Created by aarnavjindal on 09/09/17.
 */

public class LogAdapter extends BaseAdapter {
    private ArrayList<TLog> list;
    private Context context;

    public LogAdapter(ArrayList<TLog> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TLog getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TLog log=getItem(position);
        Holder holder;
        if(convertView==null){
            convertView=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_view,null);
            holder=new Holder();
            holder.t_money=(TextView) convertView.findViewById(R.id.t_money);
            holder.t_date=(TextView) convertView.findViewById(R.id.t_date);
            holder.t_to=(TextView)convertView.findViewById(R.id.t_name);
            convertView.setTag(holder);
        }
        else
            holder=(Holder)convertView.getTag();

        holder.t_money.setText(log.getAmount());
        holder.t_to.setText(log.getTo());
        holder.t_date.setText(log.getDate());

        return convertView;
    }
    private class Holder{
        TextView t_money,t_date,t_to;
    }
}