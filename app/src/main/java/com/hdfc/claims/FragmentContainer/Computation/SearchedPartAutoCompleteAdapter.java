package com.hdfc.claims.FragmentContainer.Computation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.util.ArrayList;

/**
 * Created by bhattzen on 12/24/2015.
 */
public class SearchedPartAutoCompleteAdapter extends BaseAdapter implements Filterable {


    private ArrayList<String> fullList;
    private ArrayList<String> mOriginalValues;

    private ArrayList<String> addedList;
    private ArrayFilter mFilter;
    private Context context;
    private PartsSelectionFragment.AddBtnClickListener addClickListener = null;

    public SearchedPartAutoCompleteAdapter(Context context, ArrayList<String> fullList,
                                           ArrayList<String> addedList, PartsSelectionFragment.AddBtnClickListener addClickListener) {
        this.context = context;
        this.addClickListener = addClickListener;
        this.fullList = fullList;

        this.mOriginalValues = new ArrayList<String>(fullList);

        this.addedList = addedList;

    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public String getItem(int position) {
        return fullList.get(position);
    }

    private class ViewHolder {
        TextView searchedPartName;
        ImageView imgRemove;
        ImageView imgAdd;
        RelativeLayout rl1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_searched_part_autocompletetextview, null);
            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup)convertView);

            holder.searchedPartName = (TextView) convertView.findViewById(R.id.searchedPartName);
            holder.imgRemove = (ImageView) convertView
                    .findViewById(R.id.imgRemove);
            holder.imgAdd = (ImageView) convertView.findViewById(R.id.imgAdd);
            holder.rl1 = (RelativeLayout) convertView.findViewById(R.id.rl);

            // Model query = (new
            // ArrayList<Model>(arrayList.size())).get(position);
            if (addedList.size() != 0) {
                for (int i = 0; i < addedList.size(); i++) {
                    if (fullList.get(position).equals(addedList.get(i))) {

                        holder.imgAdd.setVisibility(View.GONE);
                        holder.imgRemove.setVisibility(View.VISIBLE);
                        holder.rl1.setBackgroundColor(Color
                                .parseColor("#f8f8f8"));
                    }
                }
            }

            holder.searchedPartName.setText(fullList.get(position));
            holder.imgAdd.setTag(holder.searchedPartName.getText());
            holder.imgRemove.setTag(holder.searchedPartName.getText());
            holder.imgAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (addClickListener != null)
                        addClickListener.onAddBtnClick(v.getTag().toString());
                    /*Toast.makeText(context, "" + position + "",
                            Toast.LENGTH_SHORT).show();*/
                    holder.imgAdd.setVisibility(View.GONE);
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
                    /*Toast.makeText(context, "" + position + "",
                            Toast.LENGTH_SHORT).show();*/
                    holder.imgAdd.setVisibility(View.VISIBLE);
                    holder.imgRemove.setVisibility(View.GONE);
                    holder.rl1.setBackgroundColor(Color.TRANSPARENT);
                }
            });


        } else {

        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList<String>(fullList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<String> list = new ArrayList<String>(
                            mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<String> values = mOriginalValues;
                int count = values.size();

                ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    String item = values.get(i);
                    if (item.toLowerCase().contains(prefixString)) {
                        newValues.add(item);
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results.values != null) {
                fullList = (ArrayList<String>) results.values;
            } else {
                fullList = new ArrayList<String>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
