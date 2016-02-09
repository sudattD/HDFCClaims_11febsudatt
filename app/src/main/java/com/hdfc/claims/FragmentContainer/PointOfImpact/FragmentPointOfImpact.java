package com.hdfc.claims.FragmentContainer.PointOfImpact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdfc.claims.FragmentContainer.Computation.ComputationDataEntryFragment;
import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.SurveyDetailsFragment;
import com.hdfc.claims.FragmentContainer.WorkshopSelectionFragment;
import com.hdfc.claims.FragmentContainer.dlNrcDetailsFragment;
import com.hdfc.claims.R;

/**
 * Created by patelmih on 12/28/2015.
 */
public class FragmentPointOfImpact extends Fragment {

    private View view;
    Context context;

    Fragment fragment;
    ImageView openEdit;

    ImageView openbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Inflate the layout for this fragment

//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_point_of_impact, container, false);

        openbtn = (ImageView) view.findViewById(R.id.openbtn);
        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PointOfImpactActivity.class);
                //Log.e("Check", "Value is Not Null");
                startActivity(intent);
            }
        });

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Point Of Impact", "C230015034232");

        return view;
    }

    android.support.v4.app.FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.arrow_forward:
                //dismiss();
                WorkshopSelectionFragment fragment2 = new WorkshopSelectionFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                SurveyDetailsFragment fragment1 = new SurveyDetailsFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.frame_container, fragment1);
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
