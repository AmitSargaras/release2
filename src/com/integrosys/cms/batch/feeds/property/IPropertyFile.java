/**
 *
 */
package com.integrosys.cms.batch.feeds.property;

import java.util.Date;

/**
 * @author sai heng
 * @date 09 dec 08
 *
 */
public interface IPropertyFile {
    public void setCompositeID(String concatID);
	public String getCompositeID();

    public String getCifID();
    public void setCifID(String cifID);

    public String getSourceSecurityId();
    public void setSourceSecurityId(String sourceSecurityId) ;

    public String getValuerName();
    public void setValuerName(String valuerName);

//    public Date getValuationDate();
//    public void setValuationDate(Date valuationDate);

//    public int getValuationFrequency();
//    public void setValuationFrequency(int valuationFrequency);

//    public Date getReValuationDate();
//    public void setReValuationDate(Date reValuationDate);

    public String getValuerCode();
    public void setValuerCode(String valuerCode);

    public double getOmv();
    public void setOmv(double omv);

    public double getFsv();
    public void setFsv(double fsv);

    public double getReservedPrice();
    public void setReservedPrice(double reservedPrice);

    public String getValuationCurrency();
    public void setValuationCurrency(String valuationCurrency);

    public long getCollateralID();
    public void setCollateralID(long collateralID);

}