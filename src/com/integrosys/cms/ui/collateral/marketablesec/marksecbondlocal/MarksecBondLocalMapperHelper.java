//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec.marksecbondlocal;

import java.math.BigDecimal;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/21 13:50:23 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class MarksecBondLocalMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		return obj;

	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		
		MarksecBondLocalForm form = (MarksecBondLocalForm) cForm; 
		
		IBondsLocal cObj = (IBondsLocal) obj;
		
		if(cObj != null && cObj.getEquityList() != null) {
			boolean validationFlag = false;
			outer: 
			for(IMarketableEquity equity : cObj.getEquityList()) {
				IMarketableEquityLineDetail[] lineDetails = equity.getLineDetails();
				if(lineDetails != null) {
					BigDecimal expectedTotal = BigDecimal.valueOf(0);
					BigDecimal actualTotal = BigDecimal.valueOf(0);
					if(equity.getValuationUnitPrice() != null) {
						expectedTotal = BigDecimal.valueOf(equity.getNoOfUnits()).
								multiply(BigDecimal.valueOf(equity.getValuationUnitPrice().getAmount()));
					}
					for(IMarketableEquityLineDetail lineDetail : lineDetails) {
						if(lineDetail.getLineValue() != null) {
							actualTotal = actualTotal.add(lineDetail.getLineValue());
						}
					}
					if(expectedTotal.compareTo(actualTotal) == -1) {
						validationFlag = true;
						break outer;
					}
				}
			}
			
			form.setErrorOnLineValue(validationFlag);
		}

		return form;
	}

	public static Object getObject(HashMap inputs) {
		return ((IBondsLocal) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
