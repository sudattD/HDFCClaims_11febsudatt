package com.hdfc.claims.MasterFiles;

/**
 * Created by bhattzen on 2/5/2016.
 */
public class WorkshopMasterModel {

    private String WorkshopId;
    private String WorkshopName;
    private String WorkshopMobile;

    public String getWorkshopAddress() {
        return WorkshopAddress;
    }

    public void setWorkshopAddress(String workshopAddress) {
        WorkshopAddress = workshopAddress;
    }

    public String getWorkshopId() {
        return WorkshopId;
    }

    public void setWorkshopId(String workshopId) {
        WorkshopId = workshopId;
    }

    public String getWorkshopName() {
        return WorkshopName;
    }

    public void setWorkshopName(String workshopName) {
        WorkshopName = workshopName;
    }

    public String getWorkshopMobile() {
        return WorkshopMobile;
    }

    public void setWorkshopMobile(String workshopMobile) {
        WorkshopMobile = workshopMobile;
    }

    private String WorkshopAddress;
}
