package com.hdfc.claims.Dashboard;

/**
 * Created by patelmih on 9/29/2015.
 */
public class DashboardListModel {


    private String Master_Claim_Number;


    private String Vehicle_Registration_No;
    private String Vehicle_Model;
    private String Work_Shop;
    private String Insured_Name;
    private String Tat;

    private String Claim_Stage;
    private String Serial_No;
    private String Start_Time;
    private String Claim_Type;
    private String Insured_City;
    private String Email;
    private String Mobile;
    private String Inbox_Status;
    private String Claim_Number;
    private String Claim_Status;


    public String getClaim_Status() {
        return Claim_Status;
    }

    public void setClaim_Status(String claim_Status) {
        Claim_Status = claim_Status;
    }


    public String getClaim_Stage() {
        return Claim_Stage;
    }

    public void setClaim_Stage(String claim_Stage) {
        Claim_Stage = claim_Stage;
    }

    public String getSerial_No() {
        return Serial_No;
    }

    public void setSerial_No(String serial_No) {
        Serial_No = serial_No;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getClaim_Type() {
        return Claim_Type;
    }

    public void setClaim_Type(String claim_Type) {
        Claim_Type = claim_Type;
    }

    public String getInsured_City() {
        return Insured_City;
    }

    public void setInsured_City(String insured_City) {
        Insured_City = insured_City;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getInbox_Status() {
        return Inbox_Status;
    }

    public void setInbox_Status(String inbox_Status) {
        Inbox_Status = inbox_Status;
    }

    public String getClaim_Number() {
        return Claim_Number;
    }

    public void setClaim_Number(String claim_Number) {
        Claim_Number = claim_Number;
    }


    public String getMaster_Claim_Number() {
        return Master_Claim_Number;
    }

    public String getVehicle_Registration_No() {
        return Vehicle_Registration_No;
    }

    public String getVehicle_Model() {
        return Vehicle_Model;
    }

    public String getWork_Shop() {
        return Work_Shop;
    }

    public String getInsured_Name() {
        return Insured_Name;
    }

    public String getTat() {
        return Tat;
    }

    public void setMaster_Claim_Number(String master_Claim_Number) {
        Master_Claim_Number = master_Claim_Number;
    }

    public void setVehicle_Registration_No(String vehicle_Registration_No) {
        this.Vehicle_Registration_No = vehicle_Registration_No;
    }

    public void setVehicle_Model(String vehicle_Model) {
        this.Vehicle_Model = vehicle_Model;
    }

    public void setWork_Shop(String work_Shop) {
        this.Work_Shop = work_Shop;
    }

    public void setInsured_Name(String insured_Name) {
        this.Insured_Name = insured_Name;
    }

    public void setTat(String tat) {
        this.Tat = tat;
    }
}
