/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/08/07 06:32:23 $ Tag: $Name: $
 */

public class OwnerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public OwnerMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");

		SecurityReceiptForm aForm = (SecurityReceiptForm) cForm;
		String tLimitProfileID = aForm.getLimitProfileID();
		String tCollateralID = aForm.getCollateralID();

		long collateralID = Long.parseLong(tCollateralID);
		long limitProfileID = Long.parseLong(tLimitProfileID);
		long legalID=0;
		if( aForm.getLegalID()!=null&& !(aForm.getLegalID().trim().equals(""))){
			String tLegalID = aForm.getLegalID();
			legalID = Long.parseLong(tLegalID);
			}

		String custCategory = aForm.getCustCategory();

		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, legalID,
				custCategory);

		return owner;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		SecurityReceiptForm aForm = (SecurityReceiptForm) cForm;
		if (obj != null) {
			ICollateralCheckListOwner owner = (ICollateralCheckListOwner) obj;
			long collateralID = owner.getCollateralID();
			ICollateralProxy cProxy = CollateralProxyFactory.getProxy();
			ICollateral iCol;
			try {
				iCol = cProxy.getCollateral(collateralID, false);
			}
			catch (CollateralException ex) {
				throw new MapperException("failed to retrive collateral using collateral id [" + collateralID + "]", ex);
			}
			ICollateralNewMasterJdbc newICollateralNewMasterJdbc= (ICollateralNewMasterJdbc)BeanHouse.get("collateralNewMasterJdbc");
			SearchResult searchResult = newICollateralNewMasterJdbc.getAllCollateralNewMaster();
			ArrayList list= (ArrayList)searchResult.getResultList();
			HashMap collateralHashMap = new HashMap();
			
			if(list!=null){
				for(int l=0;l<list.size();l++){
					OBCollateralNewMaster master=(OBCollateralNewMaster)list.get(l);
					collateralHashMap.put(master.getNewCollateralCode(), master.getNewCollateralDescription());
				}
			}
			aForm.setCollateralID(String.valueOf(collateralID));
			aForm.setCollateralRef(String.valueOf(owner.getCollateralRef()));
			aForm.setSecType(iCol.getCollateralType().getTypeCode());
			aForm.setSecSubType(iCol.getCollateralSubType().getSubTypeCode());
			aForm.setSecName((String)collateralHashMap.get(iCol.getCollateralCode()));
			aForm.setLimitProfileID(String.valueOf(owner.getLimitProfileID()));
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
