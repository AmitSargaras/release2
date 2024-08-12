/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author Administrator <p/> TODO To change the template for this generated
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakerCreateSecDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "secDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "returnURL", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",REQUEST_SCOPE },
				{ "collateralMap", "java.util.HashMap", SERVICE_SCOPE },
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICMSCustomer customer=(ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if(ctx!=null && ctx.getCustomer()==null){
				ctx.setCustomer(customer);
			}
			ICollateralTrxValue secTrxObj = (ICollateralTrxValue) map.get("secTrxObj");
			ICollateral col = (ICollateral) (map.get("secDetailForm"));
			String returnURL = (String) (map.get("returnURL"));
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			String limitProfileID = (String) (map.get("limitProfileID"));

//			if ((col.getPledgors() == null) || (col.getPledgors().length == 0)) {
//				exceptionMap.put("pledgorMandatory", new ActionMessage("error.no.entries"));
//			}
//			else {
//				for (int i = 0; i < col.getPledgors().length; i++) {
//					for (int j = 0; j < col.getPledgors().length; j++) {
//						if ((i != j) && col.getPledgors()[i].getLegalID().equals(col.getPledgors()[j].getLegalID())) {
//							exceptionMap.put("pledgorMandatory", new ActionMessage("error.entries.duplicate"));
//						}
//					}
//				}
//			}

			MISecurityUIHelper helper = new MISecurityUIHelper();
			ICollateral stagingCol = secTrxObj.getStagingCollateral();
			helper.setTrxLocation(ctx, stagingCol);
			helper.setPledgorLocation(stagingCol);
			secTrxObj.setStagingCollateral(helper.getCollateralBySubtype(stagingCol));

			SBMISecProxy proxy = helper.getSBMISecProxy();
			if (exceptionMap.size() == 0) {

				// ICMSTrxResult res = proxy.createCollateralTrx(ctx,secTrxObj.getStagingCollateral() secTrxObj,
				// false);
				// result.put("request.ITrxResult", res);
				
				if(null!=secTrxObj && null!=secTrxObj.getStagingCollateral() && null!=secTrxObj.getStagingCollateral().getCollateralSubType() ) {
				if("ND".equals(secTrxObj.getFromState()) && "PT701".equals(secTrxObj.getStagingCollateral().getCollateralSubType().getSubTypeCode())) {
					if(secTrxObj.getStagingCollateral() instanceof  OBPropertyCollateral) {
						OBPropertyCollateral stagingCollateral = (OBPropertyCollateral) secTrxObj.getStagingCollateral();
						stagingCollateral.setVersion1("0");
						stagingCollateral.setVersion2("0");
						stagingCollateral.setVersion3("0");
						secTrxObj.setStagingCollateral(stagingCollateral);
					}
				}
				}
				
				ICMSTrxResult res = proxy.makerDirectCreateCollateralTrx(ctx, secTrxObj);
				ICollateralTrxValue colVal = (ICollateralTrxValue) res.getTrxValue();
				ICollateral newcol = colVal.getCollateral();

				ILimit curLimit = lmtTrxObj.getStagingLimit();
				ICollateralAllocation[] alloc = curLimit.getCollateralAllocations();

				List tempList = new ArrayList();
				tempList.add(newcol);

				if (alloc == null) {
					ICollateralAllocation[] newAlloc = new ICollateralAllocation[tempList.size()];
					for (int j = 0; j < tempList.size(); j++) {
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setCollateral((OBCollateral) (tempList.get(j)));
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(limitProfileID));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
						newAlloc[j] = nextAlloc;
					}
					curLimit.setCollateralAllocations(newAlloc);
				}
				else {
					ICollateralAllocation[] newAlloc = new ICollateralAllocation[alloc.length + tempList.size()];
					for (int j = 0; j < alloc.length; j++) {
						newAlloc[j] = alloc[j];
					}
					for (int j = 0; j < tempList.size(); j++) {
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setCollateral((OBCollateral) (tempList.get(j)));
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(limitProfileID));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);

						newAlloc[j + alloc.length] = nextAlloc;
					}
					curLimit.setCollateralAllocations(newAlloc);
				}

			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		result.put("collateralMap",  getCollateralInfo());
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	public HashMap getCollateralInfo() {
		HashMap map= new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc)BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result= collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list=(ArrayList)result.getResultList();
		for(int ab=0;ab<list.size();ab++){
			ICollateralNewMaster newMaster=(ICollateralNewMaster)list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());
		}
		return map;
	}
	
}
