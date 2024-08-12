package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 9, 2008
 * Time: 5:56:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoldFeedInfoModel {

    private String goldGrade;
    private String goldUOM;
    private Amount price;


    public String getGoldGrade() {
        return goldGrade;
    }

    public void setGoldGrade(String goldGrade) {
        this.goldGrade = goldGrade;
    }

    public String getGoldUOM() {
        return goldUOM;
    }

    public void setGoldUOM(String goldUOM) {
        this.goldUOM = goldUOM;
    }

    public Amount getPrice() {
        return price;
    }

    public void setPrice(Amount price) {
        this.price = price;
    }

    
}
