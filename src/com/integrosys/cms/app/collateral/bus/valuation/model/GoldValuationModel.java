package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 9, 2008
 * Time: 2:36:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoldValuationModel extends GenericValuationModel {

    private String goldGrade;
    private double goldWeight;
    private String goldUOM;
    private Amount goldUnitPrice;


    public String getGoldGrade() {
        return goldGrade;
    }

    public void setGoldGrade(String goldGrade) {
        this.goldGrade = goldGrade;
    }

    public double getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(double goldWeight) {
        this.goldWeight = goldWeight;
    }

    public String getGoldUOM() {
        return goldUOM;
    }

    public void setGoldUOM(String goldUOM) {
        this.goldUOM = goldUOM;
    }


    public Amount getGoldUnitPrice() {
        return goldUnitPrice;
    }

    public void setGoldUnitPrice(Amount goldUnitPrice) {
        this.goldUnitPrice = goldUnitPrice;
    }

    public void setDetailFromCollateral(ICollateral col) {
        super.setDetailFromCollateral(col);
        ISpecificChargeGold gold = (ISpecificChargeGold)col;
        setGoldGrade(gold.getGoldGrade());
        setGoldWeight(gold.getGoldWeight());
        setGoldUOM(gold.getGoldUOM());
    }
    
}
