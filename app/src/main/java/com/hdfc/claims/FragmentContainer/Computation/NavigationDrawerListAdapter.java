package com.hdfc.claims.FragmentContainer.Computation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhattzen on 12/31/2015.
 */
public class NavigationDrawerListAdapter extends BaseAdapter {

    private List<String> fullPartList;


    private ArrayList<String> addedList;

    private Context context;
    private PartsSelectionFragment.AddBtnClickListener addClickListener = null;

    public NavigationDrawerListAdapter(Context context, List<String> fullPartList,
                                       ArrayList<String> addedList, PartsSelectionFragment.AddBtnClickListener addClickListener) {

        this.fullPartList = fullPartList;
        this.context = context;

        this.addClickListener = addClickListener;
        this.addedList = addedList;

    }

    @Override
    public int getCount() {
        return fullPartList.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public String getItem(int position) {
        return fullPartList.get(position);
    }

    private class ViewHolder {
        TextView txtPartName;
        ImageView imgRemove;

        RelativeLayout rl1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_nav_drawer_parts, null);
            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup) convertView);

            holder.txtPartName = (TextView) convertView.findViewById(R.id.txtPartName);
            holder.imgRemove = (ImageView) convertView
                    .findViewById(R.id.imgRemove);

            holder.rl1 = (RelativeLayout) convertView.findViewById(R.id.rl);

            // Model query = (new
            // ArrayList<Model>(arrayList.size())).get(position);
            if (addedList.size() != 0) {
                for (int i = 0; i < addedList.size(); i++) {
                    if (fullPartList.get(position).equals(addedList.get(i))) {


                        holder.imgRemove.setVisibility(View.VISIBLE);
                        holder.rl1.setBackgroundColor(Color
                                .parseColor("#f8f8f8"));
                    }
                }
            }

            holder.txtPartName.setText(fullPartList.get(position));
            holder.txtPartName.setTag(holder.txtPartName.getText());
            holder.imgRemove.setTag(holder.txtPartName.getText());
            holder.txtPartName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (addClickListener != null)
                        addClickListener.onAddBtnClick(v.getTag().toString());
                    /*Toast.makeText(context, "" + position + "",
                            Toast.LENGTH_SHORT).show();*/
                    holder.txtPartName.setEnabled(false);
                    holder.imgRemove.setVisibility(View.VISIBLE);
                    holder.rl1.setBackgroundColor(Color.parseColor("#f8f8f8"));
                }
            });

            holder.imgRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (addClickListener != null)
                        addClickListener
                                .onRemoveBtnClick(v.getTag().toString());
                   /* Toast.makeText(context, "" + position + "",
                            Toast.LENGTH_SHORT).show();*/
                    holder.txtPartName.setEnabled(true);
                    holder.imgRemove.setVisibility(View.GONE);
                    holder.rl1.setBackgroundColor(Color.TRANSPARENT);
                }
            });


        } else {

        }
        return convertView;
    }

}
