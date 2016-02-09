package com.hdfc.claims.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfc.claims.Dashboard.ApprovalQueryListModel;
import com.hdfc.claims.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by patelmih on 9/28/2015.
 */
public class ApprovalQueryListAdapter extends BaseAdapter {
    private LinkedHashMap<String, ApprovalQueryListModel> queryList = new LinkedHashMap<String, ApprovalQueryListModel>();
    private Context context;
    private Activity activity;

    public ApprovalQueryListAdapter(Activity activity, LinkedHashMap<String, ApprovalQueryListModel> queryList) {
        context = activity;
        this.activity = activity;
        this.queryList = queryList;
    }

    private class ViewHolder {

        TextView msgOwnerUser,msgOwnerService;
        TextView queryUser,queryService;

        CardView cvUser,cvService;

    }

    @Override
    public int getCount() {
        return queryList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item_approval_query, null);
            holder = new ViewHolder();
            holder.msgOwnerUser = (TextView) view.findViewById(R.id.msgOwnerUser);
            holder.msgOwnerService = (TextView) view.findViewById(R.id.msgOwnerService);
            holder.queryUser = (TextView) view.findViewById(R.id.queryUser);
            holder.queryService = (TextView) view.findViewById(R.id.queryService);

            holder.cvUser = (CardView) view.findViewById(R.id.cvUser);
            holder.cvService = (CardView) view.findViewById(R.id.cvService);


            ApprovalQueryListModel query = (new ArrayList<ApprovalQueryListModel>(queryList.values())).get(position);

            if(query.getQueryQue().equals("U")){
                holder.cvUser.setVisibility(View.VISIBLE);
                holder.msgOwnerUser.setText(query.getQueryQue());
                holder.queryUser.setText(query.getQueryAns());
            }else if(query.getQueryQue().equals("S")){
                holder.cvService.setVisibility(View.VISIBLE);
                holder.msgOwnerService.setText(query.getQueryQue());
                holder.queryService.setText(query.getQueryAns());
            }

        } else {

        }

        return view;
    }
}
