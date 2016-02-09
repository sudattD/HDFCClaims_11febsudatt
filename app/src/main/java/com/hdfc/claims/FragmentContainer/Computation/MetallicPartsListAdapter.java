package com.hdfc.claims.FragmentContainer.Computation;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by bhattzen on 10/29/2015.
 */
public class MetallicPartsListAdapter extends BaseAdapter {

    private LinkedHashMap<String, MetallicPartsListModel> metallicPartsList = new LinkedHashMap<String, MetallicPartsListModel>();
    private Context context;
    private Activity activity;

    String edtFlag = "";

    public MetallicPartsListAdapter(Activity activity, LinkedHashMap<String, MetallicPartsListModel> metallicPartsList) {
        context = activity;
        this.activity = activity;
        this.metallicPartsList = metallicPartsList;
    }

    private class ViewHolder {
        TextView tvPartName;
        EditText edtAssessedName;
        EditText edtBilledAmount;
        EditText edtNetAmount;

        EditText edtTemp;
    }

    @Override
    public int getCount() {
        return metallicPartsList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    ViewHolder holder = null;
    View finalView2 = null;

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item_metallic_parts, null);

            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
            fontChanger.replaceFonts((ViewGroup) view);

            holder = new ViewHolder();
            holder.tvPartName = (TextView) view.findViewById(R.id.tvPartName);
            holder.edtAssessedName = (EditText) view.findViewById(R.id.edtAssessedName);
            holder.edtBilledAmount = (EditText) view.findViewById(R.id.edtBilledAmount);
            holder.edtNetAmount = (EditText) view.findViewById(R.id.edtNetAmount);
            holder.edtTemp = (EditText) view.findViewById(R.id.edtNetAmount);

            final View finalView = view;

            holder.edtAssessedName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtFlag = "a";
                    ComputationAssesmentFragment.keyboardLayout.setVisibility(View.VISIBLE);
                    holder.edtAssessedName = (EditText) finalView.findViewById(R.id.edtAssessedName);
                    ComputationAssesmentFragment.edtCustomEdit.setText(holder.edtAssessedName.getText());
                    ComputationAssesmentFragment.customString = holder.edtAssessedName.getText().toString();
                    finalView2 = finalView;
                }
            });

            holder.edtBilledAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtFlag = "b";
                    ComputationAssesmentFragment.keyboardLayout.setVisibility(View.VISIBLE);
                    holder.edtBilledAmount = (EditText) finalView.findViewById(R.id.edtBilledAmount);
                    ComputationAssesmentFragment.edtCustomEdit.setText(holder.edtBilledAmount.getText());
                    ComputationAssesmentFragment.customString = holder.edtBilledAmount.getText().toString();
                    finalView2 = finalView;
                }
            });

            holder.edtNetAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtFlag = "n";
                    ComputationAssesmentFragment.keyboardLayout.setVisibility(View.VISIBLE);
                    holder.edtNetAmount = (EditText) finalView.findViewById(R.id.edtNetAmount);
                    ComputationAssesmentFragment.edtCustomEdit.setText(holder.edtNetAmount.getText());
                    ComputationAssesmentFragment.customString = holder.edtNetAmount.getText().toString();
                    finalView2 = finalView;
                }
            });

            ComputationAssesmentFragment.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtFlag.equals("n")) {
                        holder.edtNetAmount = (EditText) finalView2.findViewById(R.id.edtNetAmount);
                        DecimalFormat formatter = new DecimalFormat("#,##,###.00");
                        holder.edtNetAmount.setText(formatter.format(Integer.parseInt(ComputationAssesmentFragment.edtCustomEdit.getText().toString())));
                    }
                    if (edtFlag.equals("a")) {
                        holder.edtAssessedName = (EditText) finalView2.findViewById(R.id.edtAssessedName);
                        DecimalFormat formatter = new DecimalFormat("#,##,###.00");
                        holder.edtAssessedName.setText(formatter.format(ComputationAssesmentFragment.edtCustomEdit.getText().toString()));
                    }
                    if (edtFlag.equals("b")) {
                        holder.edtBilledAmount = (EditText) finalView2.findViewById(R.id.edtBilledAmount);
                        DecimalFormat formatter = new DecimalFormat("#,##,###.00");
                        holder.edtBilledAmount.setText(formatter.format(ComputationAssesmentFragment.edtCustomEdit.getText().toString()));
                    }
                }
            });

            MetallicPartsListModel claim = (new ArrayList<MetallicPartsListModel>(metallicPartsList.values())).get(position);

            holder.tvPartName.setText(claim.getPartName());
            holder.edtAssessedName.setText(claim.getAssessedName());
            holder.edtBilledAmount.setText(claim.getBilledAmount());
            holder.edtNetAmount.setText(claim.getNetAmount());

        } else {

        }

        return view;

    }
}