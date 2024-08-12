/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IValuationDetails.java,v 1.1 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents valuation details of the Asset of type General
 * Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public interface IValuationDetails extends Serializable {

	public Date getValuationDate();

	public Date getRevaluationDate();

	public int getRevalFreq();

	public String getRevalFreqUnit();

	public void setValuationDate(Date valuationDate);

	public void setRevaluationDate(Date revaluationDate);

	public void setRevalFreq(int revalFreq);

	public void setRevalFreqUnit(String revalFreqUnit);
}
