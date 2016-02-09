package com.hdfc.claims.FragmentContainer.PolicyInformation;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Widgets.AnimatedExpandableListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by patelmih on 10/9/2015.
 */
public class ExpandablePolicyInfoListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<String>> listDataChildValue;

    public ExpandablePolicyInfoListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listDataChild, HashMap<String, List<String>> listDataChildValue) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
        this.listDataChildValue = listDataChildValue;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        final String childTexValue = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_claim_info, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblClaimInfoListItem);

        TextView txtListChildValue = (TextView) convertView
                .findViewById(R.id.lblClaimInfoListItemValue);

        txtListChild.setText(childTexValue);;
        txtListChildValue.setText(childTexValue);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

   /* @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_claim_info, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblClaimInfoListItem);

        txtListChild.setText(childText);
        return convertView;
    }*/

    /*@Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }*/

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_expandable_claim_info, null);
            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup)convertView);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
