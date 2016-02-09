package com.hdfc.claims.FragmentContainer.Computation;

/**
 * Created by bhattzen on 10/30/2015.
 */
public class LabourChargesListModel {

    private String PartName;

    public String getBilledAmount() {
        return BilledAmount;
    }

    public void setBilledAmount(String billedAmount) {
        BilledAmount = billedAmount;
    }

    public String getPaintLabour() {
        return PaintLabour;
    }

    public void setPaintLabour(String paintLabour) {
        PaintLabour = paintLabour;
    }

    public String getAssesmentSum() {
        return AssesmentSum;
    }

    public void setAssesmentSum(String assesmentSum) {
        AssesmentSum = assesmentSum;
    }

    public String getPaintMaterial() {
        return PaintMaterial;
    }

    public void setPaintMaterial(String paintMaterial) {
        PaintMaterial = paintMaterial;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    private String BilledAmount;
    private String PaintLabour;
    private String PaintMaterial;
    private String AssesmentSum;
    private String NetAmount;


    public String getPartName() {
        return PartName;
    }

    public void setPartName(String partName) {
        PartName = partName;
    }


}
