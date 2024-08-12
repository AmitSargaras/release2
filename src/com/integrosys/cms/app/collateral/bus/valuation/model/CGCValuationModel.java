package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 22, 2008
 * Time: 1:59:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CGCValuationModel extends GuaranteeValuationModel {

    private int securedPortion;
    private int unsecuredPortion;

    private Amount securedAmountOrigin;
    private Amount unsecuredAmountOrigin;

    private Amount calcSecuredAmount;
    private Amount calcUnsecuredAmount;
    private Amount calcTotalCgcCoverAmount;



    public int getSecuredPortion() {
        return securedPortion;
    }

    public void setSecuredPortion(int securedPortion) {
        this.securedPortion = securedPortion;
    }

    public int getUnsecuredPortion() {
        return unsecuredPortion;
    }

    public void setUnsecuredPortion(int unsecuredPortion) {
        this.unsecuredPortion = unsecuredPortion;
    }

    public Amount getSecuredAmountOrigin() {
        return securedAmountOrigin;
    }

    public void setSecuredAmountOrigin(Amount securedAmountOrigin) {
        this.securedAmountOrigin = securedAmountOrigin;
    }

    public Amount getUnsecuredAmountOrigin() {
        return unsecuredAmountOrigin;
    }

    public void setUnsecuredAmountOrigin(Amount unsecuredAmountOrigin) {
        this.unsecuredAmountOrigin = unsecuredAmountOrigin;
    }


    public Amount getCalcSecuredAmount() {
        return calcSecuredAmount;
    }

    public void setCalcSecuredAmount(Amount calcSecuredAmount) {
        this.calcSecuredAmount = calcSecuredAmount;
    }

    public Amount getCalcUnsecuredAmount() {
        return calcUnsecuredAmount;
    }

    public void setCalcUnsecuredAmount(Amount calcUnsecuredAmount) {
        this.calcUnsecuredAmount = calcUnsecuredAmount;
    }

    public Amount getCalcTotalCgcCoverAmount() {
        return calcTotalCgcCoverAmount;
    }

    public void setCalcTotalCgcCoverAmount(Amount calcTotalCgcCoverAmount) {
        this.calcTotalCgcCoverAmount = calcTotalCgcCoverAmount;
    }
}
