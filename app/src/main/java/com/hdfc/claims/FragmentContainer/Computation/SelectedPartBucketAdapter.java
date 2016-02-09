package com.hdfc.claims.FragmentContainer.Computation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

public class SelectedPartBucketAdapter extends BaseAdapter {

    ArrayList<String> arrayList;
    Context context;

    SharedPreferences sharedPartsSelection;


    public SelectedPartBucketAdapter(Activity activity, ArrayList<String> arrayList) {
        // TODO Auto-generated constructor stub
        this.arrayList = arrayList;
        context = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder {
        TextView partNameSelected;
        ImageView imgRemove;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_selected_part_bucket, null);

            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup) convertView);

            holder = new ViewHolder();
            sharedPartsSelection=context.getSharedPreferences("MyPartsSelection", 1);

            holder.partNameSelected = (TextView) convertView.findViewById(R.id.partNameSelected);

            //Model query = (new ArrayList<Model>(arrayList.size())).get(position);

            holder.partNameSelected.setText(arrayList.get(position));
            holder.imgRemove = (ImageView) convertView.findViewById(R.id.imgRemove);

            holder.imgRemove.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String temp=arrayList.get(position);


                    Log.e("Array List",temp);


                    if(sharedPartsSelection.contains(temp)) {
                        Log.e("Pref Deleted ", temp);
                        SharedPreferences.Editor keyValuesEditor = sharedPartsSelection.edit();
                        keyValuesEditor.remove(temp).commit();

                        arrayList.remove(position);


                        notifyDataSetChanged();
                    }

                    else {
                        arrayList.remove(position);


                        notifyDataSetChanged();
                    }
                }
            });

        } else {

        }


        return convertView;
    }

}
