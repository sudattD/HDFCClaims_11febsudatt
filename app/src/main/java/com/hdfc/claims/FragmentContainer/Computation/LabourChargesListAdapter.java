package com.hdfc.claims.FragmentContainer.Computation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hdfc.claims.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by bhattzen on 10/29/2015.
 */
public class LabourChargesListAdapter extends BaseAdapter {


    private LinkedHashMap<String, LabourChargesListModel> labourChargesList = new LinkedHashMap<String, LabourChargesListModel>();
    private Context context;
    private Activity activity;

    public LabourChargesListAdapter(Activity activity, LinkedHashMap<String, LabourChargesListModel> labourChargesList) {
        context = activity;
        this.activity = activity;
        this.labourChargesList = labourChargesList;
    }

    private class ViewHolder {
        EditText edtPaintMaterial;
        TextView tvPartName;
        EditText edtBilledAmount;
        EditText edtAssesmentSum;
        EditText edtPaintLabour;
        EditText edtNetAmount;
    }

    @Override
    public int getCount() {
        return labourChargesList.size();
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
            view = vi.inflate(R.layout.list_item_labour_charges, null);
            holder = new ViewHolder();
            holder.edtPaintLabour = (EditText) view.findViewById(R.id.edtPaintLabour);
            holder.tvPartName = (TextView) view.findViewById(R.id.tvPartName);
            holder.edtPaintMaterial = (EditText) view.findViewById(R.id.edtPaintMaterial);
            holder.edtAssesmentSum = (EditText) view.findViewById(R.id.edtAssesmentSum);
            holder.edtBilledAmount = (EditText) view.findViewById(R.id.edtBilledAmount);
            holder.edtNetAmount = (EditText) view.findViewById(R.id.edtNetAmount);

            final String[] choicesPartName = {"Option 1", "Option 2"};

            LabourChargesListModel claim = (new ArrayList<LabourChargesListModel>(labourChargesList.values())).get(position);


            holder.tvPartName.setText(claim.getPartName());
            holder.edtBilledAmount.setText(claim.getBilledAmount());
            holder.edtAssesmentSum.setText(claim.getAssesmentSum());
            holder.edtNetAmount.setText(claim.getNetAmount());
            holder.edtPaintLabour.setText(claim.getPaintLabour());
            holder.edtPaintMaterial.setText(claim.getPaintMaterial());


        } else {

        }


        return view;
    }


}
