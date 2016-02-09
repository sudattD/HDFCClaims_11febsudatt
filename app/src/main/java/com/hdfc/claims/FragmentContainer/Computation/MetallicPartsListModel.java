package com.hdfc.claims.FragmentContainer.Computation;

/**
 * Created by bhattzen on 10/29/2015.
 */
public class MetallicPartsListModel {

    private String PartName;

    public String getAssessedName() {
        return AssessedName;
    }

    public void setAssessedName(String assessedName) {
        AssessedName = assessedName;
    }

    public String getPartName() {
        return PartName;
    }

    public void setPartName(String partName) {
        PartName = partName;
    }

    public String getBilledAmount() {
        return BilledAmount;
    }

    public void setBilledAmount(String billedAmount) {
        BilledAmount = billedAmount;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    private String AssessedName;
    private String BilledAmount;
    private String NetAmount;
}
