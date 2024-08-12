/*
 * Created on Apr 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationFrequency;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface IValuationModel {
	public long getCollateralId();

	public void setCollateralId(long colId);

    public String getSecId();

    public void setSecId(String secId);
    
    public String getSecType();

	public void setSecType(String secType);

	public String getSecSubtype();

	public void setSecSubtype(String secSubtype);

	public String getSecurityCountry();

	public void setSecurityCountry(String country);

	public String getSecCurrency();

	public void setSecCurrency(String secCurrency);

	public Date getPrevValuationDate();

	public void setPrevValuationDate(Date prevValuationDate);

	public Date getValuationDate();

	public void setValuationDate(Date valuationDate);

	public ValuationFrequency getValuationFrequency();

	public void setValuationFrequency(ValuationFrequency valuationFrequency);

	public Amount getValOMV();

	public void setValOMV(Amount valOMV);

	public double getValuationMargin();

	public void setValuationMargin(double valuationMargin);

	public Amount getValFSV();

    public void setValFSV(Amount valFSV);
    
    public void setDetailFromCollateral(ICollateral col);
	
	public Date getNextValuationDate();

	public void setNextValuationDate(Date nextValuationDate);

    /**
     * Valuation Type in IValuationModel refers to ICMSConstant.VALUATION_SOURCE_TYPE_M,
     * ICMSConstant.VALUATION_SOURCE_TYPE_S, ICMSConstant.VALUATION_SOURCE_TYPE_A.
     * This valuation type is not the same as IValuation.valuationType; it actually corresponds to
     * IValuation.sourceType instead.
     * @return valuation type
     */
    public String getValuationType();

    public void setValuationType(String valuationType);

    public String getValuer();

    public void setValuer(String valuer);

}
