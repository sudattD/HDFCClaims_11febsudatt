package com.hdfc.claims.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.hdfc.claims.Dashboard.DashboardListModel;
import com.hdfc.claims.FragmentContainer.InsuredDetailsModel;
import com.hdfc.claims.FragmentContainer.dlNrcDetailsModel;
import com.hdfc.claims.MasterFiles.WorkshopMasterModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;


/**
 * Created by bhattzen on 1/28/2016.
 */
public class DatabaseUtils {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //Tables
    private static final String DATABASE_NAME = "HDFC_E2E_DATABASE";
    private static final String DASHBOARD_CLAIM_TABLE = "DASHBOARD_CLAIM_TABLE";
    private static final String INSURED_DETAIL_TABLE = "INSURED_DETAIL_TABLE";
    private static final String DL_N_RC_DETAIL_TABLE = "DL_N_RC_DETAIL_TABLE";
    private static final String WORSKHOP_MASTER_TABLE = "WORSKHOP_MASTER_TABLE";


    //database version
    private static final int DATABASE_VERSION = 1;

    //dashboard
    public static final String MASTER_CLAIM_NUMBER = "master_claim_number";
    public static final String VEHICLE_REGISTRATION_NO = "vehicle_registration_no";
    public static final String VEHICLE_MODEL = "vehicle_model";
    public static final String INSURED_NAME = "insured_name";
    public static final String TAT = "tat";
    public static final String CLAIM_STAGE = "claim_stage";
    public static final String SERIAL_NO = "serial_no";
    public static final String START_TIME = "start_time";
    public static final String CLAIM_TYPE = "claim_type";
    public static final String INSURED_CITY = "insured_city";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String INBOX_STATUS = "inbox_status";
    public static final String CLAIM_NUMBER = "claim_number";
    public static final String CLAIM_STATUS = "claim_status";

    //insured
    public static final String INSURED_ADDRESS = "insured_address";
    public static final String INSURED_PIN_CODE = "insured_pin_code";
    public static final String INSURED_MOBILE_NUMBER = "insured_mobile_number";
    public static final String INSURED_EMAIL = "insured_email";


    /*DL N RC DETAILS*/
    //DL
    public static final String DRIVER_NAME = "driver_name";
    public static final String DRIVER_DOB = "driver_dob";
    public static final String DRIVER_GENDER = "driver_gender";
    public static final String ISSUANCE_DATE = "issuance_date";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String LICENSE_NUMBER = "license_number";
    public static final String ISSUANCE_RTO = "issuance_rto";
    //RC
    public static final String TRANSFER_DATE = "transfer_date";
    public static final String VEHICLE_MAKE = "vehicle_make";
    public static final String ENGINE_NUMBER = "engine_number";
    public static final String CHASSIS_NUMBER = "chassis_number";
    public static final String RTO_NAME = "rto_name";
    //FIR
    public static final String FIR_DATE = "fir_date";
    public static final String POLICE_STATION = "police_station";
    public static final String FIR_UNDER_SECTION = "fir_under_section";


    //Workshop Master
    public static final String WORKSHOP_ID = "workshop_id";
    public static final String WORKSHOP_NAME = "workshop_name";
    public static final String WORKSHOP_MOBILE = "workshop_mobile";
    public static final String WORKSHOP_ADDRESS = "workshop_address";


    private static final String CREATE_DASHBOARD_CLAIM_TABLE = "create table " + DASHBOARD_CLAIM_TABLE + " (" + MASTER_CLAIM_NUMBER + " text primary key ,"

            + VEHICLE_REGISTRATION_NO + " text,"
            + VEHICLE_MODEL + " text,"
            + INSURED_NAME + " text,"
            + TAT + " text,"
            + CLAIM_STAGE + " text,"
            + SERIAL_NO + " text,"
            + START_TIME + " text,"
            + CLAIM_TYPE + " text,"
            + INSURED_CITY + " text,"
            + EMAIL + " text,"
            + MOBILE + " text,"
            + INBOX_STATUS + " text,"
            + CLAIM_STATUS + " text,"
            + CLAIM_NUMBER + " text)";


    private static final String CREATE_INSURED_DETAIL_TABLE = "create table " + INSURED_DETAIL_TABLE + " ("
            + MASTER_CLAIM_NUMBER + " text primary key,"
            + INSURED_NAME + " text,"
            + INSURED_ADDRESS + " text,"
            + INSURED_PIN_CODE + " text,"
            + INSURED_MOBILE_NUMBER + " text,"
            + INSURED_EMAIL + " text)";

    private static final String CREATE_DL_N_RC_DETAIL_TABLE = "create table " + DL_N_RC_DETAIL_TABLE + " ("
            + MASTER_CLAIM_NUMBER + " text primary key,"
            + DRIVER_NAME + " text,"
            + DRIVER_DOB + " text,"
            + DRIVER_GENDER + " text,"
            + ISSUANCE_DATE + " text,"
            + EXPIRY_DATE + " text,"
            + LICENSE_NUMBER + " text,"
            + ISSUANCE_RTO + " text,"
            + INSURED_NAME + " text,"
            + VEHICLE_REGISTRATION_NO + " text,"
            + TRANSFER_DATE + " text,"
            + VEHICLE_MAKE + " text,"
            + VEHICLE_MODEL + " text,"
            + ENGINE_NUMBER + " text,"
            + CHASSIS_NUMBER + " text,"
            + RTO_NAME + " text,"
            + FIR_DATE + " text,"
            + POLICE_STATION + " text,"
            + FIR_UNDER_SECTION + " text)";


    private static final String CREATE_WORKSHOP_MASTER_TABLE = "create table " + WORSKHOP_MASTER_TABLE + " ("
            + WORKSHOP_ID + " text primary key,"
            + WORKSHOP_NAME + " text,"
            + WORKSHOP_MOBILE + " text,"
            + WORKSHOP_ADDRESS + " text)";


    public DatabaseUtils(Context context) {

        DBHelper = new DatabaseHelper(context);

    }

    private void open() {
        if (db != null) {
            if (!db.isOpen()) {
                db = DBHelper.getWritableDatabase();
            }
        } else {
            db = DBHelper.getWritableDatabase();
        }
    }

    public void insertDashboardClaimData(DashboardListModel dbmodel) {
        open();
        ContentValues values = new ContentValues();

        values.put(MASTER_CLAIM_NUMBER, dbmodel.getMaster_Claim_Number());
        values.put(VEHICLE_REGISTRATION_NO, dbmodel.getVehicle_Registration_No());
        values.put(VEHICLE_MODEL, dbmodel.getVehicle_Model());
        values.put(INSURED_NAME, dbmodel.getInsured_Name());
        values.put(TAT, dbmodel.getTat());
        values.put(CLAIM_STAGE, dbmodel.getClaim_Stage());
        values.put(SERIAL_NO, dbmodel.getSerial_No());
        values.put(START_TIME, dbmodel.getStart_Time());
        values.put(CLAIM_TYPE, dbmodel.getClaim_Type());
        values.put(INSURED_CITY, dbmodel.getInsured_City());
        values.put(EMAIL, dbmodel.getEmail());
        values.put(MOBILE, dbmodel.getMobile());
        values.put(INBOX_STATUS, dbmodel.getInbox_Status());
        values.put(CLAIM_NUMBER, dbmodel.getClaim_Number());
        values.put(CLAIM_STATUS, dbmodel.getClaim_Status());
        db.insert(DASHBOARD_CLAIM_TABLE, null, values);
    }

    public void insertInsuredDetailsData(InsuredDetailsModel dbmodel) {
        open();
        ContentValues values = new ContentValues();

        values.put(MASTER_CLAIM_NUMBER, dbmodel.getMaster_Claim_Number());
        values.put(INSURED_NAME, dbmodel.getInsured_Name());
        values.put(INSURED_ADDRESS, dbmodel.getInsured_Address());
        values.put(INSURED_PIN_CODE, dbmodel.getInsured_Pin_Code());
        values.put(INSURED_MOBILE_NUMBER, dbmodel.getInsured_Mobile_Number());
        values.put(INSURED_EMAIL, dbmodel.getInsured_Email());
        db.insert(INSURED_DETAIL_TABLE, null, values);
    }


    public void insertDLNRCDetailsData(dlNrcDetailsModel dlNrcmodel) {
        open();
        ContentValues dlNrcvalues = new ContentValues();

        dlNrcvalues.put(MASTER_CLAIM_NUMBER, dlNrcmodel.getMaster_Claim_Number());
        dlNrcvalues.put(DRIVER_NAME, dlNrcmodel.getDriverName());
        dlNrcvalues.put(DRIVER_DOB, dlNrcmodel.getDriverDOB());
        dlNrcvalues.put(DRIVER_GENDER, dlNrcmodel.getGender());
        dlNrcvalues.put(ISSUANCE_DATE, dlNrcmodel.getIssuanceDate());
        dlNrcvalues.put(EXPIRY_DATE, dlNrcmodel.getExpiryDate());
        dlNrcvalues.put(LICENSE_NUMBER, dlNrcmodel.getLicenseNumber());
        dlNrcvalues.put(ISSUANCE_RTO, dlNrcmodel.getIssuanceRTO());
        dlNrcvalues.put(INSURED_NAME, dlNrcmodel.getVehicleOwner());
        dlNrcvalues.put(VEHICLE_REGISTRATION_NO, dlNrcmodel.getVehicleRegNumber());
        dlNrcvalues.put(TRANSFER_DATE, dlNrcmodel.getTransferDate());
        dlNrcvalues.put(VEHICLE_MAKE, dlNrcmodel.getVehicleMake());
        dlNrcvalues.put(VEHICLE_MODEL, dlNrcmodel.getVehicleModel());
        dlNrcvalues.put(ENGINE_NUMBER, dlNrcmodel.getEngineNumber());
        dlNrcvalues.put(CHASSIS_NUMBER, dlNrcmodel.getChassisNumber());
        dlNrcvalues.put(RTO_NAME, dlNrcmodel.getRtoName());
        dlNrcvalues.put(FIR_DATE, dlNrcmodel.getFirDate());
        dlNrcvalues.put(POLICE_STATION, dlNrcmodel.getPoliceStation());
        dlNrcvalues.put(FIR_UNDER_SECTION, dlNrcmodel.getFirUnderSection());
        db.insert(DL_N_RC_DETAIL_TABLE, null, dlNrcvalues);
    }


    public void insertWorkshopMasterRecord(WorkshopMasterModel dbmodel) {
        open();
        ContentValues values = new ContentValues();

        values.put(WORKSHOP_ID, dbmodel.getWorkshopId());
        values.put(WORKSHOP_NAME, dbmodel.getWorkshopName());
        values.put(WORKSHOP_MOBILE, dbmodel.getWorkshopMobile());
        values.put(WORKSHOP_ADDRESS, dbmodel.getWorkshopAddress());

        db.insert(WORSKHOP_MASTER_TABLE, null, values);
    }


    public LinkedHashMap<String, DashboardListModel> getAllDashboardClaimsList() {
        LinkedHashMap<String, DashboardListModel> claimHashmap = new LinkedHashMap<String, DashboardListModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DASHBOARD_CLAIM_TABLE;

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DashboardListModel details = new DashboardListModel();

                // cursor.getString(cursor.getColumnIndex(COMMENT))


                details.setMaster_Claim_Number(cursor.getString(cursor
                        .getColumnIndex(MASTER_CLAIM_NUMBER)));
                details.setVehicle_Registration_No(cursor.getString(cursor
                        .getColumnIndex(VEHICLE_REGISTRATION_NO)));
                details.setVehicle_Model(cursor.getString(cursor
                        .getColumnIndex(VEHICLE_MODEL)));
                details.setInsured_Name(cursor.getString(cursor
                        .getColumnIndex(INSURED_NAME)));
                details.setTat(cursor.getString(cursor
                        .getColumnIndex(TAT)));
                details.setClaim_Stage(cursor.getString(cursor
                        .getColumnIndex(CLAIM_STAGE)));
                details.setSerial_No(cursor.getString(cursor
                        .getColumnIndex(SERIAL_NO)));
                details.setStart_Time(cursor.getString(cursor
                        .getColumnIndex(START_TIME)));
                details.setClaim_Type(cursor.getString(cursor
                        .getColumnIndex(CLAIM_TYPE)));
                details.setInsured_City(cursor.getString(cursor
                        .getColumnIndex(INSURED_CITY)));
                details.setEmail(cursor.getString(cursor
                        .getColumnIndex(EMAIL)));
                details.setMobile(cursor.getString(cursor
                        .getColumnIndex(MOBILE)));
                details.setInbox_Status(cursor.getString(cursor
                        .getColumnIndex(INBOX_STATUS)));
                details.setClaim_Number(cursor.getString(cursor
                        .getColumnIndex(CLAIM_NUMBER)));
                details.setClaim_Status(cursor.getString(cursor
                        .getColumnIndex(CLAIM_STATUS)));

                claimHashmap.put(
                        cursor.getString(cursor.getColumnIndex(MASTER_CLAIM_NUMBER)),
                        details);

                // Adding contact to list

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return claimHashmap;
    }

    public LinkedHashMap<String, DashboardListModel> getFilteredDashboardClaimsList(String filterQuery) {
        LinkedHashMap<String, DashboardListModel> claimHashmap = new LinkedHashMap<String, DashboardListModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DASHBOARD_CLAIM_TABLE + " WHERE " + CLAIM_STATUS + "='" + filterQuery + "'";

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DashboardListModel details = new DashboardListModel();

                // cursor.getString(cursor.getColumnIndex(COMMENT))


                details.setMaster_Claim_Number(cursor.getString(cursor
                        .getColumnIndex(MASTER_CLAIM_NUMBER)));
                details.setVehicle_Registration_No(cursor.getString(cursor
                        .getColumnIndex(VEHICLE_REGISTRATION_NO)));
                details.setVehicle_Model(cursor.getString(cursor
                        .getColumnIndex(VEHICLE_MODEL)));
                details.setInsured_Name(cursor.getString(cursor
                        .getColumnIndex(INSURED_NAME)));
                details.setTat(cursor.getString(cursor
                        .getColumnIndex(TAT)));
                details.setClaim_Stage(cursor.getString(cursor
                        .getColumnIndex(CLAIM_STAGE)));
                details.setSerial_No(cursor.getString(cursor
                        .getColumnIndex(SERIAL_NO)));
                details.setStart_Time(cursor.getString(cursor
                        .getColumnIndex(START_TIME)));
                details.setClaim_Type(cursor.getString(cursor
                        .getColumnIndex(CLAIM_TYPE)));
                details.setInsured_City(cursor.getString(cursor
                        .getColumnIndex(INSURED_CITY)));
                details.setEmail(cursor.getString(cursor
                        .getColumnIndex(EMAIL)));
                details.setMobile(cursor.getString(cursor
                        .getColumnIndex(MOBILE)));
                details.setInbox_Status(cursor.getString(cursor
                        .getColumnIndex(INBOX_STATUS)));
                details.setClaim_Number(cursor.getString(cursor
                        .getColumnIndex(CLAIM_NUMBER)));
                details.setClaim_Status(cursor.getString(cursor
                        .getColumnIndex(CLAIM_STATUS)));

                claimHashmap.put(
                        cursor.getString(cursor.getColumnIndex(MASTER_CLAIM_NUMBER)),
                        details);

                // Adding contact to list

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return claimHashmap;
    }


    public InsuredDetailsModel getInsuredDetailsByMasterClaimNumber(String master_claim_number) {
        InsuredDetailsModel insuredModel = new InsuredDetailsModel();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + INSURED_DETAIL_TABLE + " WHERE " + MASTER_CLAIM_NUMBER + "='" + master_claim_number + "'";

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                insuredModel.setMaster_Claim_Number(cursor.getString(cursor
                        .getColumnIndex(MASTER_CLAIM_NUMBER)));

                insuredModel.setInsured_Name(cursor.getString(cursor.getColumnIndex(INSURED_NAME)));
                insuredModel.setInsured_Address(cursor.getString(cursor.getColumnIndex(INSURED_ADDRESS)));
                insuredModel.setInsured_Pin_Code(cursor.getString(cursor.getColumnIndex(INSURED_PIN_CODE)));
                insuredModel.setInsured_Email(cursor.getString(cursor.getColumnIndex(INSURED_EMAIL)));
                insuredModel.setInsured_Mobile_Number(cursor.getString(cursor.getColumnIndex(INSURED_MOBILE_NUMBER)));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return insuredModel;
    }


    public dlNrcDetailsModel getDLNRCDetailsByMasterClaimNumber(String master_claim_number) {
        dlNrcDetailsModel dlNrcModel = new dlNrcDetailsModel();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DL_N_RC_DETAIL_TABLE + " WHERE " + MASTER_CLAIM_NUMBER + "='" + master_claim_number + "'";

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor dlNrccursor = db.rawQuery(selectQuery, null);

        if (dlNrccursor.moveToFirst()) {
            do {
                dlNrcModel.setMaster_Claim_Number(dlNrccursor.getString(dlNrccursor.getColumnIndex(MASTER_CLAIM_NUMBER)));

                dlNrcModel.setDriverName(dlNrccursor.getString(dlNrccursor.getColumnIndex(DRIVER_NAME)));
                dlNrcModel.setDriverDOB(dlNrccursor.getString(dlNrccursor.getColumnIndex(DRIVER_DOB)));
                dlNrcModel.setGender(dlNrccursor.getString(dlNrccursor.getColumnIndex(DRIVER_GENDER)));
                dlNrcModel.setIssuanceDate(dlNrccursor.getString(dlNrccursor.getColumnIndex(ISSUANCE_DATE)));
                dlNrcModel.setExpiryDate(dlNrccursor.getString(dlNrccursor.getColumnIndex(EXPIRY_DATE)));
                dlNrcModel.setLicenseNumber(dlNrccursor.getString(dlNrccursor.getColumnIndex(LICENSE_NUMBER)));
                dlNrcModel.setIssuanceRTO(dlNrccursor.getString(dlNrccursor.getColumnIndex(ISSUANCE_RTO)));
                dlNrcModel.setVehicleOwner(dlNrccursor.getString(dlNrccursor.getColumnIndex(INSURED_NAME)));
                dlNrcModel.setVehicleRegNumber(dlNrccursor.getString(dlNrccursor.getColumnIndex(VEHICLE_REGISTRATION_NO)));
                dlNrcModel.setTransferDate(dlNrccursor.getString(dlNrccursor.getColumnIndex(TRANSFER_DATE)));
                dlNrcModel.setVehicleMake(dlNrccursor.getString(dlNrccursor.getColumnIndex(VEHICLE_MAKE)));
                dlNrcModel.setVehicleModel(dlNrccursor.getString(dlNrccursor.getColumnIndex(VEHICLE_MODEL)));
                dlNrcModel.setEngineNumber(dlNrccursor.getString(dlNrccursor.getColumnIndex(ENGINE_NUMBER)));
                dlNrcModel.setChassisNumber(dlNrccursor.getString(dlNrccursor.getColumnIndex(CHASSIS_NUMBER)));
                dlNrcModel.setRtoName(dlNrccursor.getString(dlNrccursor.getColumnIndex(RTO_NAME)));
                dlNrcModel.setFirDate(dlNrccursor.getString(dlNrccursor.getColumnIndex(FIR_DATE)));
                dlNrcModel.setPoliceStation(dlNrccursor.getString(dlNrccursor.getColumnIndex(POLICE_STATION)));
                dlNrcModel.setFirUnderSection(dlNrccursor.getString(dlNrccursor.getColumnIndex(FIR_UNDER_SECTION)));
            }
            while (dlNrccursor.moveToNext());
        }
        dlNrccursor.close();
        db.close();

        return dlNrcModel;
    }


    public long UpdateInsuredDetailsByMasterClaimNumber(String master_claim_number, InsuredDetailsModel model) {

        long id = 0;


        ContentValues values = new ContentValues();
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        values.put(INSURED_MOBILE_NUMBER, model.getInsured_Mobile_Number());
        values.put(INSURED_EMAIL, model.getInsured_Email());


        String countQuery = "SELECT * FROM " + INSURED_DETAIL_TABLE + " WHERE "
                + MASTER_CLAIM_NUMBER + "='" + master_claim_number + "'";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            //id = db.update(TABLE_NOTIFICATION_DETAILS, values, PUSH_IS_UPDATED + "=?",null);

            id = db.update(INSURED_DETAIL_TABLE,
                    values,
                    MASTER_CLAIM_NUMBER + " = ?",
                    new String[]{master_claim_number});
        } else {
            id = db.insertOrThrow(INSURED_DETAIL_TABLE, null, values);
        }

        // id =db.update(TABLE_NOTIFICATION_DETAILS, values, PUSH_IS_UPDATED + "="+pnm.getIsUpdated(), null);
        db.close();
        return id;

    }


    public long UpdatedlNrcDetailsByMasterClaimNumber(String master_claim_number, dlNrcDetailsModel model) {

        long id = 0;

        ContentValues values = new ContentValues();
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        values.put(DRIVER_NAME, model.getDriverName());
        values.put(DRIVER_DOB, model.getDriverDOB());
        values.put(ISSUANCE_DATE, model.getIssuanceDate());
        values.put(EXPIRY_DATE, model.getExpiryDate());
        values.put(LICENSE_NUMBER, model.getLicenseNumber());
        values.put(ISSUANCE_RTO, model.getIssuanceRTO());
        values.put(VEHICLE_REGISTRATION_NO, model.getVehicleRegNumber());
        values.put(ENGINE_NUMBER, model.getEngineNumber());
        values.put(CHASSIS_NUMBER, model.getChassisNumber());
        values.put(FIR_DATE, model.getFirDate());
        values.put(POLICE_STATION, model.getPoliceStation());
        values.put(FIR_UNDER_SECTION, model.getFirUnderSection());

        String countQuery = "SELECT * FROM " + DL_N_RC_DETAIL_TABLE + " WHERE "
                + MASTER_CLAIM_NUMBER + "='" + master_claim_number + "'";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {

            id = db.update(DL_N_RC_DETAIL_TABLE,
                    values,
                    MASTER_CLAIM_NUMBER + " = ?",
                    new String[]{master_claim_number});
        } else {
            id = db.insertOrThrow(DL_N_RC_DETAIL_TABLE, null, values);
        }

        db.close();
        return id;

    }


    public static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DASHBOARD_CLAIM_TABLE);
            db.execSQL(CREATE_INSURED_DETAIL_TABLE);
            db.execSQL(CREATE_DL_N_RC_DETAIL_TABLE);
            db.execSQL(CREATE_WORKSHOP_MASTER_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }
}
