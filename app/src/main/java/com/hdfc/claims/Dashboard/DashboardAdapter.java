package com.hdfc.claims.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hdfc.claims.LandingScreen.LandingScreenActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.Globals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by patelmih on 9/28/2015.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashBoardViewHolder> implements Filterable {
    private LinkedHashMap<String, DashboardListModel> filteredClaimsList = new LinkedHashMap<String, DashboardListModel>();
    private LinkedHashMap<String, DashboardListModel> originalClaimsList = new LinkedHashMap<String, DashboardListModel>();
    private Context context;
    private Activity activity;
    Typeface face;

    public DashboardAdapter(Activity activity, LinkedHashMap<String, DashboardListModel> ClaimsList) {
        context = activity;
        this.activity = activity;
        this.filteredClaimsList = ClaimsList;
        this.originalClaimsList = ClaimsList;

        face = Typeface.createFromAsset(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");

    }


    public class DashBoardViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView txtClaimID;
        TextView txtVehicleName;
        TextView txtGarageName;
        TextView txtCustomerName;
        TextView txtVehicleNumber;
        TextView txtTAT;
        TextView txtClaimStatus;

        LinearLayout imgEmail, imgSMS, imgCall, imgPreApproval;

        DashBoardViewHolder(View view) {
            super(view);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtClaimID = (TextView) view.findViewById(R.id.txtClaimID);
            txtVehicleName = (TextView) view.findViewById(R.id.txtVehicleName);
            txtGarageName = (TextView) view.findViewById(R.id.txtGarageName);
            txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
            txtVehicleNumber = (TextView) view.findViewById(R.id.txtVehicleNumber);
            txtTAT = (TextView) view.findViewById(R.id.txtTAT);
            txtClaimStatus = (TextView) view.findViewById(R.id.txtClaimStatus);

            /*txtClaimID.setTypeface(face);
            txtVehicleName.setTypeface(face);
            txtGarageName.setTypeface(face);
            txtCustomerName.setTypeface(face);
            txtVehicleNumber.setTypeface(face);*/

            txtClaimID.setTypeface(face, Typeface.BOLD);
            txtCustomerName.setTypeface(face, Typeface.BOLD);

            imgEmail = (LinearLayout) view.findViewById(R.id.imgEmail);
            imgSMS = (LinearLayout) view.findViewById(R.id.imgSMS);
            imgCall = (LinearLayout) view.findViewById(R.id.imgCall);
            imgPreApproval = (LinearLayout) view.findViewById(R.id.imgPreApproval);

        }
    }


    @Override
    public DashBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dashboard, parent, false);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) v);

        DashBoardViewHolder dvh = new DashBoardViewHolder(v);

        return dvh;
    }


    String[] addresses = {"mihir.patel@synoverge.com"};

    @Override
    public void onBindViewHolder(DashBoardViewHolder holder, int position) {

        final DashboardListModel claim = (new ArrayList<DashboardListModel>(filteredClaimsList.values())).get(position);

        holder.txtClaimID.setText(claim.getMaster_Claim_Number());
        holder.txtVehicleName.setText(claim.getVehicle_Model());
        holder.txtGarageName.setText(claim.getWork_Shop());
        holder.txtCustomerName.setText(claim.getInsured_Name());
        holder.txtVehicleNumber.setText(claim.getVehicle_Registration_No());
        holder.txtTAT.setText(claim.getTat());
        holder.txtClaimStatus.setText(claim.getClaim_Status());

        holder.imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Location", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setType("*/*");
                //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                //intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                //intent.putExtra(Intent.EXTRA_STREAM, "I'm email body.");

                //view.getContext().startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Call", Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + claim.getMobile()));

                view.getContext().startActivity(callIntent);
            }
        });

        holder.imgPreApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Query", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ApprovalQueryActivity.class);
                context.startActivity(intent);
            }
        });

        holder.imgSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "FIR", Toast.LENGTH_LONG).show();
    /*            Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", "");
                context.startActivity(intent);*/
            }
        });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LandingScreenActivity.class);
                intent.putExtra("MasterClaimNumber", claim.getMaster_Claim_Number());
                intent.putExtra("ClaimNumber", claim.getClaim_Number());
                Globals.MASTER_CLAIM_NUMBER = claim.getMaster_Claim_Number();
                context.startActivity(intent);
                ((DashboardContainerActivity) context).overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredClaimsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredClaimsList = (LinkedHashMap<String, DashboardListModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                LinkedHashMap<String, DashboardListModel> FilteredList = new LinkedHashMap<String, DashboardListModel>();

                if (originalClaimsList == null) {
                    originalClaimsList = new LinkedHashMap<String, DashboardListModel>(filteredClaimsList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalClaimsList.size();

                    results.values = originalClaimsList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originalClaimsList.size(); i++) {
                        String master_claim_number = (new ArrayList<DashboardListModel>(originalClaimsList.values())).get(i).getMaster_Claim_Number();
                        String insured_name = (new ArrayList<DashboardListModel>(originalClaimsList.values())).get(i).getInsured_Name();
                        String vehicle_reg_no = (new ArrayList<DashboardListModel>(originalClaimsList.values())).get(i).getVehicle_Registration_No();
                        DashboardListModel model = (new ArrayList<DashboardListModel>(originalClaimsList.values())).get(i);
                        if (master_claim_number.toLowerCase().contains(constraint.toString())
                                || insured_name.toLowerCase().contains(constraint.toString())
                                || vehicle_reg_no.toLowerCase().contains(constraint.toString())) {
                            FilteredList.put(master_claim_number, model);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredList.size();
                    results.values = FilteredList;
                }
                return results;
            }
        };
        return filter;
    }


}
