package com.integrosys.cms.ui.customer.viewdetails;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.util.List;

public class CustomerViewForm extends TrxContextForm implements java.io.Serializable {

    private String subProfileID = "";

    private String userID = "";
    private String legalName = "";
    private String legalIdSub = "";
    private String customerName = "";
    private String legalID = "";
    private String leIDType = "";
    private String idNO = "";

    private String gobutton = "";
    private String all = "";



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSubProfileID() {
        return subProfileID;
    }

    public void setSubProfileID(String subProfileID) {
        this.subProfileID = subProfileID;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalIdSub() {
        return legalIdSub;
    }

    public void setLegalIdSub(String legalIdSub) {
        this.legalIdSub = legalIdSub;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLegalID() {
        return legalID;
    }

    public void setLegalID(String legalID) {
        this.legalID = legalID;
    }

    public String getLeIDType() {
        return leIDType;
    }

    public void setLeIDType(String leIDType) {
        this.leIDType = leIDType;
    }

    public String getIdNO() {
        return idNO;
    }

    public void setIdNO(String idNO) {
        this.idNO = idNO;
    }

    public String getGobutton() {
        return gobutton;
    }

    public void setGobutton(String gobutton) {
        this.gobutton = gobutton;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }


    public String[][] getMapper() {

        String[][] input = {
                { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
        };

        return input;
    }
}
