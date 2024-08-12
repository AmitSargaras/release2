package com.integrosys.cms.app.collateral.bus.valuation.model;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 11, 2008
 * Time: 5:56:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class VehicleValuationModel extends StrtLineValuationModel {

    private String make;
    private String model;
    private String region;    


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
