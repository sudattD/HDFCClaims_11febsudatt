package com.hdfc.claims.FragmentContainer.Computation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by bhattzen on 10/26/2015.
 */
public class WorkSelectionListAdapter extends BaseAdapter {

    private LinkedHashMap<String, WorkSelectionListModel> partsTypeList = new LinkedHashMap<String, WorkSelectionListModel>();
    private Context context;
    private Activity activity;
    static int isRepairReplace = 0,isPaintType = 0;

    public WorkSelectionListAdapter(Activity activity, LinkedHashMap<String, WorkSelectionListModel> partsTypeList) {
        context = activity;
        this.activity = activity;
        this.partsTypeList = partsTypeList;
    }

    private class ViewHolder {

        TextView txtPartName;
        TextView txtPartType;
        ImageView imgReplaceRepair;

        ImageView imgFullPartialPaint;

        LinearLayout ll;

    }


    @Override
    public int getCount() {
        return partsTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_work_selection, null);

            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup) convertView);

            holder = new ViewHolder();
            holder.txtPartName = (TextView) convertView.findViewById(R.id.txtPartName);
            holder.txtPartType = (TextView) convertView.findViewById(R.id.txtPartType);
            holder.imgReplaceRepair = (ImageView) convertView.findViewById(R.id.imgReplaceRepair);
            holder.imgFullPartialPaint = (ImageView) convertView.findViewById(R.id.imgFullPartialPaint);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

            /*if( (position % 2) == 0 ){

                holder.ll.setBackgroundColor(Color.TRANSPARENT);
            }
            else
            {
                holder.ll.setBackgroundColor(context.getResources().getColor(R.color.grey_light));
            }*/


            WorkSelectionListModel partType = (new ArrayList<WorkSelectionListModel>(partsTypeList.values())).get(position);

            holder.txtPartName.setText(partType.getPartName());
            final ViewHolder finalHolder1 = holder;
            holder.txtPartType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                            context);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            context,
                            android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapter.add("Rubber");
                    arrayAdapter.add("Fiber");
                    arrayAdapter.add("Glass");
                    arrayAdapter.add("Metal");
                    arrayAdapter.add("Consumable");

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String selected = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    context);
                            if (selected.equals("Rubber")) {
                                finalHolder1.txtPartType.setText("R");
                            }
                            if (selected.equals("Fiber")) {
                                finalHolder1.txtPartType.setText("F");
                            }
                            if (selected.equals("Glass")) {
                                finalHolder1.txtPartType.setText("G");
                            }
                            if (selected.equals("Metal")) {
                                finalHolder1.txtPartType.setText("M");
                            }
                            if (selected.equals("Consumable")) {
                                finalHolder1.txtPartType.setText("C");
                            }
                        }


                    });

                    builderSingle.setTitle("Select Part Type");
                    builderSingle.show();
                }
            });

            final ViewHolder finalHolder = holder;
            holder.imgReplaceRepair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isRepairReplace==0)
                    {
                        finalHolder.imgReplaceRepair.setImageResource(R.drawable.replace);
                        isRepairReplace++;
                    }
                    else {
                        finalHolder.imgReplaceRepair.setImageResource(R.drawable.repair);
                        isRepairReplace=0;
                    }


                }
            });

            holder.imgFullPartialPaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isPaintType==0)
                    {
                        finalHolder.imgFullPartialPaint.setImageResource(R.drawable.hp);
                        isPaintType++;
                    }
                    else if(isPaintType==1) {
                        finalHolder.imgFullPartialPaint.setImageResource(R.drawable.np);
                        isPaintType++;
                    }
                    else{
                        finalHolder.imgFullPartialPaint.setImageResource(R.drawable.fp);
                        isPaintType=0;
                    }


                }
            });




        } else {

        }


        return convertView;
    }
}
