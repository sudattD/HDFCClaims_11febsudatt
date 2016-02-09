package com.hdfc.claims.FragmentContainer.DocumentUpload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;
import com.hdfc.claims.FragmentContainer.Computation.ComputationDataEntryFragment;
import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.FragmentContainer.PolicyInformation.PolicyInformationFragment;
import com.hdfc.claims.FragmentContainer.dlNrcDetailsFragment;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.FontChangeCrawler;
import com.hdfc.claims.Utilities.ImageLoadingUtils;
import com.hdfc.claims.FragmentContainer.WorkshopSelectionFragment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DocumentsUploadFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;
    Context context;

    private String[] otherData = {"New Category"};

    ArrayAdapter<String> adapter_state;

    ImageView imgClaimFormCamera, imgClaimFormGallery, imgRegistrationFormCamera, imgRegistrationFormGallery, imgDrivingLicenseCamera, imgDrivingLicenseGallery, imgEstimateCamera, imgEstimateGallery, imgCrashedPhotosCamera, imgCrashedPhotosGallery, imgRepairBillCamera, imgRepairBillGallery, imgSuveyFeesBillCamera, imgSurveyFeesBillGallery, imgCancelledChequeCamera, imgCancelledChequeGallery, imgKYCDocCamera, imgKYCDocGallery, imgNewCategoryCamera, imgNewCategoryGallery;

    ImageView backClaimForm, backRegistrationcopy, backDrivingLicense, backEstimate, backCrashPhotos, backRepairBill, backSurveyFeesBill, backCancelledCheque, backKYCDocuments, backNewCategory;
    ImageView forwardClaimForm, forwardRegistrationcopy, forwardDrivingLicense, forwardEstimate, forwardCrashPhotos, forwardRepairBill, forwardSurveyFeesBill, forwardCancelledCheque, forwardKYCDocuments, forwardNewCategory;

    RelativeLayout layoutClaimForm, layoutRegistrationCopy, layoutDrivingLicense, layoutEstimate, layoutCrashPhotos, layoutRepairBill, layoutSurveyFeesBill, layoutCancelledCheque, layoutKYCDocuments, layoutNewCategory;

    LinearLayout newCategoryLayout;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_IMAGE_REQUEST_CODE = 200;

    private String docType = "";

    private int claimFormCount, registrationCopyCount, drivingLicenseCount, estimateCount, crashedPhotosCount, repairBillCount, surveyFeesBillCount, cancelledChequeCount, kycDocsCount, newCategoryCount = 0;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_GALLERY_IMAGE = 2;
    private static final String IMAGE_DIRECTORY_NAME = "HDFC Images";

    private Uri fileUri;

    private String imgDecodableString;

    private TextView txtClaimForm, txtRegistrationCopy, txtDrivingLicense, txtEstimate, txtCrashPhotos, txtRepairBill, txtSurveyFesBill, txtCancelledCheque, txtKYCDocs, txtNewCategory;

    private ArrayList<String> claimFormListImagesUri, registrationCopyListImagesUri, drivingLicenseListImagesUri, estimateListImagesUri, crashedPhotosListImagesUri, repairBillListImagesUri, surveyFeesBillListImagesUri, cancelledChequeListImagesUri, kycDocsListImagesUri, newCategoryListImagesUri;
    private HorizontalListView claimFormListViewImages, registrationCopyListViewImages, drivingLicenseListViewImages, estimateListViewImages, crashedPhotosListViewImages, repairBillListViewImages, surveyFeesBillListViewImages, cancelledChequeListViewImages, kycDocsListViewImages, newCategoryListViewImages;
    private DocumentUploadClaimFormAdapter claimFormAdapter;
    private DocumentUploadRegistrationCopyAdapter registrationCopyAdapter;
    private DocumentUploadDrivingLicenseAdapter drivingLicenseAdapter;
    private DocumentUploadEstimateAdapter estimateAdapter;
    private DocumentUploadCrashedPhotosAdapter crashedPhotosAdapter;
    private DocumentUploadRepairBillAdapter repairBillAdapter;
    private DocumentUploadSurveyFeesBillAdapter surveyFeesBillAdapter;
    private DocumentUploadCancelledChequeAdapter cancelledChequeAdapter;
    private DocumentUploadKYCDocAdapter kycDocAdapter;
    private DocumentUploadNewCategoryAdapter newCategoryAdapter;

    private ImageLoadingUtils utils;

    RelativeLayout rlCategoryContainer;

    public DocumentsUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        DrawerLayout drawerLayout = FragmentContainerActivity.drawerLayout;
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        view = inflater.inflate(R.layout.fragment_documents_upload, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initializeControls();

        if (savedInstanceState != null) {
            claimFormListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            claimFormAdapter = new DocumentUploadClaimFormAdapter(getActivity(), claimFormListImagesUri);
            claimFormListViewImages.setAdapter(claimFormAdapter);

            registrationCopyListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            registrationCopyAdapter = new DocumentUploadRegistrationCopyAdapter(getActivity(), registrationCopyListImagesUri);
            registrationCopyListViewImages.setAdapter(registrationCopyAdapter);

            drivingLicenseListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            drivingLicenseAdapter = new DocumentUploadDrivingLicenseAdapter(getActivity(), drivingLicenseListImagesUri);
            drivingLicenseListViewImages.setAdapter(drivingLicenseAdapter);

            estimateListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            estimateAdapter = new DocumentUploadEstimateAdapter(getActivity(), estimateListImagesUri);
            estimateListViewImages.setAdapter(estimateAdapter);

            crashedPhotosListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            crashedPhotosAdapter = new DocumentUploadCrashedPhotosAdapter(getActivity(), crashedPhotosListImagesUri);
            crashedPhotosListViewImages.setAdapter(crashedPhotosAdapter);

            repairBillListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            repairBillAdapter = new DocumentUploadRepairBillAdapter(getActivity(), repairBillListImagesUri);
            repairBillListViewImages.setAdapter(repairBillAdapter);

            surveyFeesBillListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            surveyFeesBillAdapter = new DocumentUploadSurveyFeesBillAdapter(getActivity(), surveyFeesBillListImagesUri);
            surveyFeesBillListViewImages.setAdapter(surveyFeesBillAdapter);

            cancelledChequeListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            cancelledChequeAdapter = new DocumentUploadCancelledChequeAdapter(getActivity(), cancelledChequeListImagesUri);
            cancelledChequeListViewImages.setAdapter(cancelledChequeAdapter);

            kycDocsListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            kycDocAdapter = new DocumentUploadKYCDocAdapter(getActivity(), kycDocsListImagesUri);
            kycDocsListViewImages.setAdapter(kycDocAdapter);

            newCategoryListImagesUri = (ArrayList<String>) savedInstanceState.get("URI");
            newCategoryAdapter = new DocumentUploadNewCategoryAdapter(getActivity(), newCategoryListImagesUri);
            newCategoryListViewImages.setAdapter(newCategoryAdapter);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("URI", claimFormListImagesUri);
        outState.putStringArrayList("URI", registrationCopyListImagesUri);
        outState.putStringArrayList("URI", drivingLicenseListImagesUri);
        outState.putStringArrayList("URI", estimateListImagesUri);
        outState.putStringArrayList("URI", crashedPhotosListImagesUri);
        outState.putStringArrayList("URI", repairBillListImagesUri);
        outState.putStringArrayList("URI", surveyFeesBillListImagesUri);
        outState.putStringArrayList("URI", cancelledChequeListImagesUri);
        outState.putStringArrayList("URI", kycDocsListImagesUri);
        outState.putStringArrayList("URI", newCategoryListImagesUri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.setCancelable(true);
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "HelveticaNeueLTStd-Roman.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage(data);
                if (docType == "Claim Form") {
                    claimFormCount++;
                    txtClaimForm.setText("Claim Form Bond" + " (" + claimFormCount + ")");
                    layoutClaimForm.setVisibility(View.VISIBLE);
                    if (claimFormCount >= 4) {
                        backClaimForm.setVisibility(View.VISIBLE);
                        forwardClaimForm.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Registration Copy") {
                    registrationCopyCount++;
                    txtRegistrationCopy.setText("Registration Copy" + " (" + registrationCopyCount + ")");
                    layoutRegistrationCopy.setVisibility(View.VISIBLE);
                    if (registrationCopyCount >= 4) {
                        backRegistrationcopy.setVisibility(View.VISIBLE);
                        forwardRegistrationcopy.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Driving License") {
                    drivingLicenseCount++;
                    txtDrivingLicense.setText("Driving License" + " (" + drivingLicenseCount + ")");
                    layoutDrivingLicense.setVisibility(View.VISIBLE);
                    if (drivingLicenseCount >= 4) {
                        backDrivingLicense.setVisibility(View.VISIBLE);
                        forwardRegistrationcopy.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Estimate") {
                    estimateCount++;
                    txtEstimate.setText("Estimate" + " (" + estimateCount + ")");
                    layoutEstimate.setVisibility(View.VISIBLE);
                    if (estimateCount >= 4) {
                        backEstimate.setVisibility(View.VISIBLE);
                        forwardEstimate.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Crash Photos") {
                    crashedPhotosCount++;
                    txtCrashPhotos.setText("Crash Photos" + " (" + crashedPhotosCount + ")");
                    layoutCrashPhotos.setVisibility(View.VISIBLE);
                    if (crashedPhotosCount >= 4) {
                        backCrashPhotos.setVisibility(View.VISIBLE);
                        forwardCrashPhotos.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Repair Bill") {
                    repairBillCount++;
                    txtRepairBill.setText("Repair Bill" + " (" + repairBillCount + ")");
                    layoutRepairBill.setVisibility(View.VISIBLE);
                    if (repairBillCount >= 4) {
                        backRepairBill.setVisibility(View.VISIBLE);
                        forwardRepairBill.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Survey Fees Bill") {
                    surveyFeesBillCount++;
                    txtSurveyFesBill.setText("Survey Fees Bill" + " (" + surveyFeesBillCount + ")");
                    layoutSurveyFeesBill.setVisibility(View.VISIBLE);
                    if (surveyFeesBillCount >= 4) {
                        backSurveyFeesBill.setVisibility(View.VISIBLE);
                        forwardSurveyFeesBill.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Cancelled Cheque") {
                    cancelledChequeCount++;
                    txtCancelledCheque.setText("Cancelled Cheque" + " (" + cancelledChequeCount + ")");
                    layoutCancelledCheque.setVisibility(View.VISIBLE);
                    if (cancelledChequeCount >= 4) {
                        backCancelledCheque.setVisibility(View.VISIBLE);
                        forwardCancelledCheque.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "KYC Documents") {
                    kycDocsCount++;
                    txtKYCDocs.setText("KYC Documents" + " (" + kycDocsCount + ")");
                    layoutKYCDocuments.setVisibility(View.VISIBLE);
                    if (kycDocsCount >= 4) {
                        backKYCDocuments.setVisibility(View.VISIBLE);
                        forwardKYCDocuments.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "New Category") {
                    newCategoryCount++;
                    txtNewCategory.setText("New Category" + " (" + newCategoryCount + ")");
                    layoutNewCategory.setVisibility(View.VISIBLE);
                    if (newCategoryCount >= 4) {
                        backNewCategory.setVisibility(View.VISIBLE);
                        forwardNewCategory.setVisibility(View.VISIBLE);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewGalleryImage(data);
                if (docType == "Claim Form") {
                    claimFormCount++;
                    txtClaimForm.setText("Claim Form Bond" + " (" + claimFormCount + ")");
                    layoutClaimForm.setVisibility(View.VISIBLE);
                    if (claimFormCount >= 4) {
                        backClaimForm.setVisibility(View.VISIBLE);
                        forwardClaimForm.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Registration Copy") {
                    registrationCopyCount++;
                    txtRegistrationCopy.setText("Registration Copy" + " (" + registrationCopyCount + ")");
                    layoutRegistrationCopy.setVisibility(View.VISIBLE);
                    if (registrationCopyCount >= 4) {
                        backRegistrationcopy.setVisibility(View.VISIBLE);
                        forwardRegistrationcopy.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Driving License") {
                    drivingLicenseCount++;
                    txtDrivingLicense.setText("Driving License" + " (" + drivingLicenseCount + ")");
                    layoutDrivingLicense.setVisibility(View.VISIBLE);
                    if (drivingLicenseCount >= 4) {
                        backDrivingLicense.setVisibility(View.VISIBLE);
                        forwardRegistrationcopy.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Estimate") {
                    estimateCount++;
                    txtEstimate.setText("Estimate" + " (" + estimateCount + ")");
                    layoutEstimate.setVisibility(View.VISIBLE);
                    if (estimateCount >= 4) {
                        backEstimate.setVisibility(View.VISIBLE);
                        forwardEstimate.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Crash Photos") {
                    crashedPhotosCount++;
                    txtCrashPhotos.setText("Crash Photos" + " (" + crashedPhotosCount + ")");
                    layoutCrashPhotos.setVisibility(View.VISIBLE);
                    if (crashedPhotosCount >= 4) {
                        backCrashPhotos.setVisibility(View.VISIBLE);
                        forwardCrashPhotos.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Repair Bill") {
                    repairBillCount++;
                    txtRepairBill.setText("Repair Bill" + " (" + repairBillCount + ")");
                    layoutRepairBill.setVisibility(View.VISIBLE);
                    if (repairBillCount >= 4) {
                        backRepairBill.setVisibility(View.VISIBLE);
                        forwardRepairBill.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Survey Fees Bill") {
                    surveyFeesBillCount++;
                    txtSurveyFesBill.setText("Survey Fees Bill" + " (" + surveyFeesBillCount + ")");
                    layoutSurveyFeesBill.setVisibility(View.VISIBLE);
                    if (surveyFeesBillCount >= 4) {
                        backSurveyFeesBill.setVisibility(View.VISIBLE);
                        forwardSurveyFeesBill.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "Cancelled Cheque") {
                    cancelledChequeCount++;
                    txtCancelledCheque.setText("Cancelled Cheque" + " (" + cancelledChequeCount + ")");
                    layoutCancelledCheque.setVisibility(View.VISIBLE);
                    if (cancelledChequeCount >= 4) {
                        backCancelledCheque.setVisibility(View.VISIBLE);
                        forwardCancelledCheque.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "KYC Documents") {
                    kycDocsCount++;
                    txtKYCDocs.setText("KYC Documents" + " (" + kycDocsCount + ")");
                    layoutKYCDocuments.setVisibility(View.VISIBLE);
                    if (kycDocsCount >= 4) {
                        backKYCDocuments.setVisibility(View.VISIBLE);
                        forwardKYCDocuments.setVisibility(View.VISIBLE);
                    }
                }
                if (docType == "New Category") {
                    newCategoryCount++;
                    txtNewCategory.setText("New Category" + " (" + newCategoryCount + ")");
                    layoutNewCategory.setVisibility(View.VISIBLE);
                    if (newCategoryCount >= 4) {
                        backNewCategory.setVisibility(View.VISIBLE);
                        forwardNewCategory.setVisibility(View.VISIBLE);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "User didn't choose image from gallery", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to choose image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String stredittext = data.getStringExtra("deleteImageURI");
                int position = claimFormListImagesUri.indexOf(stredittext);
                claimFormListImagesUri.remove(position);
                claimFormAdapter.notifyDataSetChanged();
                /*claimFormAdapter = new DocumentUploadClaimFormAdapter(getActivity(), claimFormListImagesUri);
                claimFormListViewImages.setAdapter(claimFormAdapter);*/
            }
        }
    }

    private void initializeControls() {
        context = view.getContext();

        rlCategoryContainer = (RelativeLayout) view.findViewById(R.id.layout_category_container);

        newCategoryLayout = (LinearLayout) view.findViewById(R.id.layout_new_category);

        layoutClaimForm = (RelativeLayout) view.findViewById(R.id.layoutClaimForm);
        layoutRegistrationCopy = (RelativeLayout) view.findViewById(R.id.layoutRegistrationCopy);
        layoutDrivingLicense = (RelativeLayout) view.findViewById(R.id.layoutDrivingLicense);
        layoutEstimate = (RelativeLayout) view.findViewById(R.id.layoutEstimate);
        layoutCrashPhotos = (RelativeLayout) view.findViewById(R.id.layoutCrashPhotos);
        layoutRepairBill = (RelativeLayout) view.findViewById(R.id.layoutRepairBill);
        layoutSurveyFeesBill = (RelativeLayout) view.findViewById(R.id.layoutSurveyBill);
        layoutCancelledCheque = (RelativeLayout) view.findViewById(R.id.layoutCancelledCheque);
        layoutKYCDocuments = (RelativeLayout) view.findViewById(R.id.layoutKYCDoc);
        layoutNewCategory = (RelativeLayout) view.findViewById(R.id.layoutNewCategory);

        backClaimForm = (ImageView) view.findViewById(R.id.backClaimForm);
        backRegistrationcopy = (ImageView) view.findViewById(R.id.backRegistrationCopy);
        backDrivingLicense = (ImageView) view.findViewById(R.id.backDrivingLicence);
        backEstimate = (ImageView) view.findViewById(R.id.backEstimate);
        backCrashPhotos = (ImageView) view.findViewById(R.id.backCrashPhotos);
        backRepairBill = (ImageView) view.findViewById(R.id.backRepairBill);
        backSurveyFeesBill = (ImageView) view.findViewById(R.id.backSurveyBill);
        backCancelledCheque = (ImageView) view.findViewById(R.id.backCancelledCheque);
        backKYCDocuments = (ImageView) view.findViewById(R.id.backKYCDoc);
        backNewCategory = (ImageView) view.findViewById(R.id.backNewCategory);

        forwardClaimForm = (ImageView) view.findViewById(R.id.forwardClaimForm);
        forwardRegistrationcopy = (ImageView) view.findViewById(R.id.forwardRegistrationCopy);
        forwardDrivingLicense = (ImageView) view.findViewById(R.id.forwardDrivingLicense);
        forwardEstimate = (ImageView) view.findViewById(R.id.forwardEstimate);
        forwardCrashPhotos = (ImageView) view.findViewById(R.id.forwardCrashPhotos);
        forwardRepairBill = (ImageView) view.findViewById(R.id.forwardRepairBill);
        forwardSurveyFeesBill = (ImageView) view.findViewById(R.id.forwardSurveyBill);
        forwardCancelledCheque = (ImageView) view.findViewById(R.id.forwardCancelledCheque);
        forwardKYCDocuments = (ImageView) view.findViewById(R.id.forwardKYCDoc);
        forwardNewCategory = (ImageView) view.findViewById(R.id.forwardNewCategory);

        txtClaimForm = (TextView) view.findViewById(R.id.txtClaimForm);
        txtRegistrationCopy = (TextView) view.findViewById(R.id.txtRegistrationCopy);
        txtDrivingLicense = (TextView) view.findViewById(R.id.txtDrivingLicense);
        txtEstimate = (TextView) view.findViewById(R.id.txtEstimate);
        txtCrashPhotos = (TextView) view.findViewById(R.id.txtCrashPhotos);
        txtRepairBill = (TextView) view.findViewById(R.id.txtRepairBill);
        txtSurveyFesBill = (TextView) view.findViewById(R.id.txtSurveyFeesBill);
        txtCancelledCheque = (TextView) view.findViewById(R.id.txtCancelledCheque);
        txtKYCDocs = (TextView) view.findViewById(R.id.txtKYCDoc);
        txtNewCategory = (TextView) view.findViewById(R.id.txtNewCategory);

        claimFormListViewImages = (HorizontalListView) view.findViewById(R.id.listViewClaimForm);
        registrationCopyListViewImages = (HorizontalListView) view.findViewById(R.id.listViewRegistrationCopyImages);
        drivingLicenseListViewImages = (HorizontalListView) view.findViewById(R.id.listViewDrivingLicenseImages);
        estimateListViewImages = (HorizontalListView) view.findViewById(R.id.listViewEstimateImages);
        crashedPhotosListViewImages = (HorizontalListView) view.findViewById(R.id.listViewCrashPhotosImages);
        repairBillListViewImages = (HorizontalListView) view.findViewById(R.id.listViewRepairBillImages);
        surveyFeesBillListViewImages = (HorizontalListView) view.findViewById(R.id.listViewSurveyImages);
        cancelledChequeListViewImages = (HorizontalListView) view.findViewById(R.id.listViewCancelledChequeImages);
        kycDocsListViewImages = (HorizontalListView) view.findViewById(R.id.listViewKYCDocImages);
        newCategoryListViewImages = (HorizontalListView) view.findViewById(R.id.listViewNewCategoryImages);

        imgClaimFormCamera = (ImageView) view.findViewById(R.id.ImgClaimFormCamera);
        imgClaimFormGallery = (ImageView) view.findViewById(R.id.ImgClaimFormGallery);

        imgRegistrationFormCamera = (ImageView) view.findViewById(R.id.ImgRegistrationCopyCamera);
        imgRegistrationFormGallery = (ImageView) view.findViewById(R.id.ImgRegistrationCopyGallery);

        imgDrivingLicenseCamera = (ImageView) view.findViewById(R.id.ImgDrivingLicenseCamera);
        imgDrivingLicenseGallery = (ImageView) view.findViewById(R.id.ImgDrivingLicenseGallery);

        imgEstimateCamera = (ImageView) view.findViewById(R.id.ImgEstimateCamera);
        imgEstimateGallery = (ImageView) view.findViewById(R.id.ImgEstimateGallery);

        imgCrashedPhotosCamera = (ImageView) view.findViewById(R.id.ImgCrashPhotosCamera);
        imgCrashedPhotosGallery = (ImageView) view.findViewById(R.id.ImgCrashPhotosGallery);

        imgRepairBillCamera = (ImageView) view.findViewById(R.id.ImgRepairBillCamera);
        imgRepairBillGallery = (ImageView) view.findViewById(R.id.ImgRepairBillGallery);

        imgSuveyFeesBillCamera = (ImageView) view.findViewById(R.id.ImgSurveyFeesBillCamera);
        imgSurveyFeesBillGallery = (ImageView) view.findViewById(R.id.ImgSurveyFeesBillGallery);

        imgCancelledChequeCamera = (ImageView) view.findViewById(R.id.ImgCancelledChequeCamera);
        imgCancelledChequeGallery = (ImageView) view.findViewById(R.id.ImgCancelledChequeGallery);

        imgKYCDocCamera = (ImageView) view.findViewById(R.id.ImgKYCDocCamera);
        imgKYCDocGallery = (ImageView) view.findViewById(R.id.ImgKYCDocGallery);

        imgNewCategoryCamera = (ImageView) view.findViewById(R.id.ImgNewCategoryCamera);
        imgNewCategoryGallery = (ImageView) view.findViewById(R.id.ImgNewCategoryGallery);

        claimFormListImagesUri = new ArrayList<String>();
        registrationCopyListImagesUri = new ArrayList<String>();
        drivingLicenseListImagesUri = new ArrayList<String>();
        estimateListImagesUri = new ArrayList<String>();
        crashedPhotosListImagesUri = new ArrayList<String>();
        repairBillListImagesUri = new ArrayList<String>();
        surveyFeesBillListImagesUri = new ArrayList<String>();
        cancelledChequeListImagesUri = new ArrayList<String>();
        kycDocsListImagesUri = new ArrayList<String>();
        newCategoryListImagesUri = new ArrayList<String>();

        utils = new ImageLoadingUtils(getActivity());

        imgClaimFormCamera.setOnClickListener(this);
        imgClaimFormGallery.setOnClickListener(this);

        imgRegistrationFormCamera.setOnClickListener(this);
        imgRegistrationFormGallery.setOnClickListener(this);

        imgDrivingLicenseCamera.setOnClickListener(this);
        imgDrivingLicenseGallery.setOnClickListener(this);

        imgEstimateCamera.setOnClickListener(this);
        imgEstimateGallery.setOnClickListener(this);

        imgCrashedPhotosCamera.setOnClickListener(this);
        imgCrashedPhotosGallery.setOnClickListener(this);

        imgRepairBillCamera.setOnClickListener(this);
        imgRepairBillGallery.setOnClickListener(this);

        imgSuveyFeesBillCamera.setOnClickListener(this);
        imgSurveyFeesBillGallery.setOnClickListener(this);

        imgCancelledChequeCamera.setOnClickListener(this);
        imgCancelledChequeGallery.setOnClickListener(this);

        imgKYCDocCamera.setOnClickListener(this);
        imgKYCDocGallery.setOnClickListener(this);

        imgNewCategoryCamera.setOnClickListener(this);
        imgNewCategoryGallery.setOnClickListener(this);

        // Spinner element
        MaterialBetterSpinner otherSpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner_others);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, otherData);

        // Spinner Drop down elements
        adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, otherData);

        otherSpinner.setAdapter(adapter);
        otherSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), " " + position + " " + id + "Click ", Toast.LENGTH_LONG).show();
                newCategoryLayout.setVisibility(View.VISIBLE);
            }
        });

        ((FragmentContainerActivity) getActivity()).setActionBarTitle("Upload Documents", "C230015034232");
    }

/*
    private void createImageLayout() {
        LinearLayout parent = new LinearLayout(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(10, 10, 10, 10);

        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setWeightSum(2);

        TextView imageType = new TextView(context);
        imageType.setText("New Image Category");

        //parent.addView(imageType);

        RelativeLayout childLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams relativeLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.weight = 1.0f;
        childLayout.setLayoutParams(relativeLayoutParams);
        childLayout.setGravity(Gravity.END);

        ImageView imgCamera = new ImageView(context);
        RelativeLayout.LayoutParams imgCameraLayoutParams = new RelativeLayout.LayoutParams(25, 25);
        imgCamera.setImageResource(R.drawable.camera);
        imgCamera.setLayoutParams(imgCameraLayoutParams);

        ImageView imgGallery = new ImageView(context);
        RelativeLayout.LayoutParams imgGalleryLayoutParams = new RelativeLayout.LayoutParams(25, 25);
        imgGalleryLayoutParams.setMargins(15, 0, 15, 0);
        imgCamera.setImageResource(R.drawable.gallery);
        imgCamera.setLayoutParams(imgGalleryLayoutParams);

        childLayout.addView(imgCamera);
        childLayout.addView(imgGallery);

        parent.addView(childLayout);

        rlCategoryContainer.addView(parent);
    }
*/

    public static DocumentsUploadFragment newInstance() {
        DocumentsUploadFragment f1 = new DocumentsUploadFragment();

        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        return f1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.ImgClaimFormCamera):
                docType = "Claim Form";
                captureImage();
                break;
            case (R.id.ImgClaimFormGallery):
                docType = "Claim Form";
                chooseFromGallery();
                break;
            case (R.id.ImgRegistrationCopyCamera):
                docType = "Registration Copy";
                captureImage();
                break;
            case (R.id.ImgRegistrationCopyGallery):
                docType = "Registration Copy";
                chooseFromGallery();
                break;
            case (R.id.ImgDrivingLicenseCamera):
                docType = "Driving License";
                captureImage();
                break;
            case (R.id.ImgDrivingLicenseGallery):
                docType = "Driving License";
                chooseFromGallery();
                break;
            case (R.id.ImgEstimateCamera):
                docType = "Estimate";
                captureImage();
                break;
            case (R.id.ImgEstimateGallery):
                docType = "Estimate";
                chooseFromGallery();
                break;
            case (R.id.ImgCrashPhotosCamera):
                docType = "Crash Photos";
                captureImage();
                break;
            case (R.id.ImgCrashPhotosGallery):
                docType = "Crash Photos";
                chooseFromGallery();
                break;
            case (R.id.ImgRepairBillCamera):
                docType = "Repair Bill";
                captureImage();
                break;
            case (R.id.ImgRepairBillGallery):
                docType = "Repair Bill";
                chooseFromGallery();
                break;
            case (R.id.ImgSurveyFeesBillCamera):
                docType = "Survey Fees Bill";
                captureImage();
                break;
            case (R.id.ImgSurveyFeesBillGallery):
                docType = "Survey Fees Bill";
                chooseFromGallery();
                break;
            case (R.id.ImgCancelledChequeCamera):
                docType = "Cancelled Cheque";
                captureImage();
                break;
            case (R.id.ImgCancelledChequeGallery):
                docType = "Cancelled Cheque";
                chooseFromGallery();
                break;
            case (R.id.ImgKYCDocCamera):
                docType = "KYC Documents";
                captureImage();
                break;
            case (R.id.ImgKYCDocGallery):
                docType = "KYC Documents";
                chooseFromGallery();
                break;
            case (R.id.ImgNewCategoryCamera):
                docType = "New Category";
                captureImage();
                break;
            case (R.id.ImgNewCategoryGallery):
                docType = "New Category";
                chooseFromGallery();
                break;
        }
    }

    private void captureImage() {
        // TODO Auto-generated method stub

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    private void chooseFromGallery() {
        // TODO Auto-generated method stub

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        // start the image capture Intent
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);

    }

    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        // TODO Auto-generated method stub
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    private File getOutputMediaFile(int mediaTypeImage) {
        // TODO Auto-generated method stub


        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {

            return null;
        }

        return mediaFile;

    }

    private void previewCapturedImage(Intent data) {
        // TODO Auto-generated method stub

        try {
            // hide video preview

            //getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,fileUri));

            new ImageCompressionAsyncTask(false).execute(fileUri.getPath());


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewGalleryImage(Intent data) {
        // TODO Auto-generated method stub
        try {


            //new ImageCompressionAsyncTask(true).execute(fileUri.getPath());
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            new ImageCompressionAsyncTask(true).execute(imgDecodableString);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    FragmentManager fragmentManager;
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
                ComputationDataEntryFragment fragment2 = new ComputationDataEntryFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.commit();
                return true;

            case android.R.id.home:
                dlNrcDetailsFragment fragment1 = new dlNrcDetailsFragment();
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

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {
        private boolean fromGallery;

        public ImageCompressionAsyncTask(boolean fromGallery) {
            this.fromGallery = fromGallery;
        }

        @Override
        protected String doInBackground(String... params) {
            String filePath = compressImage(params[0]);
            return filePath;
        }

        public String compressImage(String imageUri) {

            String filePath = getRealPathFromURI(imageUri);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 816.0f;
            float maxWidth = 816.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out = null;
            String filename = getFilename();
            try {
                out = new FileOutputStream(filename);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return filename;
        }

        private String getRealPathFromURI(String contentURI) {
            Uri contentUri = Uri.parse(contentURI);
            Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        }

        public String getFilename() {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "HDFC Images");
            if (!file.exists()) {
                file.mkdirs();
            }
            String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
            return uriSting;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (fromGallery) {

                if (docType == "Claim Form") {
                    //database.insertUri(result);
                    claimFormListImagesUri.add(result);
                    //cursor = database.getallUri();
                    claimFormAdapter = new DocumentUploadClaimFormAdapter(getActivity(), claimFormListImagesUri);
                    claimFormListViewImages.setAdapter(claimFormAdapter);
                }
                if (docType == "Registration Copy") {
                    registrationCopyListImagesUri.add(result);
                    //cursor = database.getallUri();
                    registrationCopyAdapter = new DocumentUploadRegistrationCopyAdapter(getActivity(), registrationCopyListImagesUri);
                    registrationCopyListViewImages.setAdapter(registrationCopyAdapter);
                }
                if (docType == "Driving License") {
                    drivingLicenseListImagesUri.add(result);
                    //cursor = database.getallUri();
                    drivingLicenseAdapter = new DocumentUploadDrivingLicenseAdapter(getActivity(), drivingLicenseListImagesUri);
                    drivingLicenseListViewImages.setAdapter(drivingLicenseAdapter);
                }
                if (docType == "Estimate") {
                    estimateListImagesUri.add(result);
                    //cursor = database.getallUri();
                    estimateAdapter = new DocumentUploadEstimateAdapter(getActivity(), estimateListImagesUri);
                    estimateListViewImages.setAdapter(estimateAdapter);
                }
                if (docType == "Crash Photos") {
                    crashedPhotosListImagesUri.add(result);
                    //cursor = database.getallUri();
                    crashedPhotosAdapter = new DocumentUploadCrashedPhotosAdapter(getActivity(), crashedPhotosListImagesUri);
                    crashedPhotosListViewImages.setAdapter(crashedPhotosAdapter);
                }
                if (docType == "Repair Bill") {
                    repairBillListImagesUri.add(result);
                    //cursor = database.getallUri();
                    repairBillAdapter = new DocumentUploadRepairBillAdapter(getActivity(), repairBillListImagesUri);
                    repairBillListViewImages.setAdapter(repairBillAdapter);
                }
                if (docType == "Survey Fees Bill") {
                    surveyFeesBillListImagesUri.add(result);
                    //cursor = database.getallUri();
                    surveyFeesBillAdapter = new DocumentUploadSurveyFeesBillAdapter(getActivity(), surveyFeesBillListImagesUri);
                    surveyFeesBillListViewImages.setAdapter(surveyFeesBillAdapter);
                }
                if (docType == "Cancelled Cheque") {
                    cancelledChequeListImagesUri.add(result);
                    //cursor = database.getallUri();
                    cancelledChequeAdapter = new DocumentUploadCancelledChequeAdapter(getActivity(), cancelledChequeListImagesUri);
                    cancelledChequeListViewImages.setAdapter(cancelledChequeAdapter);
                }
                if (docType == "KYC Documents") {
                    kycDocsListImagesUri.add(result);
                    //cursor = database.getallUri();
                    kycDocAdapter = new DocumentUploadKYCDocAdapter(getActivity(), kycDocsListImagesUri);
                    kycDocsListViewImages.setAdapter(kycDocAdapter);
                }
                if (docType == "New Category") {
                    newCategoryListImagesUri.add(result);
                    //cursor = database.getallUri();
                    newCategoryAdapter = new DocumentUploadNewCategoryAdapter(getActivity(), newCategoryListImagesUri);
                    newCategoryListViewImages.setAdapter(newCategoryAdapter);
                }
            } else {

                if (docType == "Claim Form") {
                    //database.insertUri(result);
                    claimFormListImagesUri.add(result);
                    //cursor = database.getallUri();
                    claimFormAdapter = new DocumentUploadClaimFormAdapter(getActivity(), claimFormListImagesUri);
                    claimFormListViewImages.setAdapter(claimFormAdapter);
                }
                if (docType == "Registration Copy") {
                    registrationCopyListImagesUri.add(result);
                    //cursor = database.getallUri();
                    registrationCopyAdapter = new DocumentUploadRegistrationCopyAdapter(getActivity(), registrationCopyListImagesUri);
                    registrationCopyListViewImages.setAdapter(registrationCopyAdapter);
                }
                if (docType == "Driving License") {
                    drivingLicenseListImagesUri.add(result);
                    //cursor = database.getallUri();
                    drivingLicenseAdapter = new DocumentUploadDrivingLicenseAdapter(getActivity(), drivingLicenseListImagesUri);
                    drivingLicenseListViewImages.setAdapter(drivingLicenseAdapter);
                }
                if (docType == "Estimate") {
                    estimateListImagesUri.add(result);
                    //cursor = database.getallUri();
                    estimateAdapter = new DocumentUploadEstimateAdapter(getActivity(), estimateListImagesUri);
                    estimateListViewImages.setAdapter(estimateAdapter);
                }
                if (docType == "Crash Photos") {
                    crashedPhotosListImagesUri.add(result);
                    //cursor = database.getallUri();
                    crashedPhotosAdapter = new DocumentUploadCrashedPhotosAdapter(getActivity(), crashedPhotosListImagesUri);
                    crashedPhotosListViewImages.setAdapter(crashedPhotosAdapter);
                }
                if (docType == "Repair Bill") {
                    repairBillListImagesUri.add(result);
                    //cursor = database.getallUri();
                    repairBillAdapter = new DocumentUploadRepairBillAdapter(getActivity(), repairBillListImagesUri);
                    repairBillListViewImages.setAdapter(repairBillAdapter);
                }
                if (docType == "Survey Fees Bill") {
                    surveyFeesBillListImagesUri.add(result);
                    //cursor = database.getallUri();
                    surveyFeesBillAdapter = new DocumentUploadSurveyFeesBillAdapter(getActivity(), surveyFeesBillListImagesUri);
                    surveyFeesBillListViewImages.setAdapter(surveyFeesBillAdapter);
                }
                if (docType == "Cancelled Cheque") {
                    cancelledChequeListImagesUri.add(result);
                    //cursor = database.getallUri();
                    cancelledChequeAdapter = new DocumentUploadCancelledChequeAdapter(getActivity(), cancelledChequeListImagesUri);
                    cancelledChequeListViewImages.setAdapter(cancelledChequeAdapter);
                }
                if (docType == "KYC Documents") {
                    kycDocsListImagesUri.add(result);
                    //cursor = database.getallUri();
                    kycDocAdapter = new DocumentUploadKYCDocAdapter(getActivity(), kycDocsListImagesUri);
                    kycDocsListViewImages.setAdapter(kycDocAdapter);
                }
                if (docType == "New Category") {
                    newCategoryListImagesUri.add(result);
                    //cursor = database.getallUri();
                    newCategoryAdapter = new DocumentUploadNewCategoryAdapter(getActivity(), newCategoryListImagesUri);
                    newCategoryListViewImages.setAdapter(newCategoryAdapter);
                }
            }

        }

    }
}
