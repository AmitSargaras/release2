/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/ReadDepositCommand.java,v 1.9 2004/06/04 05:19:57 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CollateralStpValidateUtils;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

import org.apache.struts.action.ActionErrors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/06/04 05:19:57 $ Tag: $Name: $
 */
public class ViewReadDepositCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"indexID", "java.lang.String", REQUEST_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"ref_id", "java.lang.String", REQUEST_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
        
               // { "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"ref_id.session", "java.lang.String", SERVICE_SCOPE},
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lcnNo", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				 { "custname", "java.lang.String", SERVICE_SCOPE },
				 { "serialNo", "java.lang.String", REQUEST_SCOPE },
					{ "remark", "java.lang.String", REQUEST_SCOPE },
					{ "cashId", "java.lang.String", REQUEST_SCOPE },
					{ "facilityId", "java.lang.String", REQUEST_SCOPE },
					{ "facilityName", "java.lang.String", REQUEST_SCOPE },
					
        });
        
        
    }

    /**
     * Defines an two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{{"form.depositObject", "java.lang.Object", FORM_SCOPE},
                {"actualDeposit", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", REQUEST_SCOPE},
                {"stageDeposit", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", REQUEST_SCOPE},
                {"depositObj", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},               
                {"ref_id.session", "java.lang.String", SERVICE_SCOPE},
				
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lcnNo", "java.lang.String", REQUEST_SCOPE },				
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				 { "custname", "java.lang.String", SERVICE_SCOPE },
				 { "serialNo", "java.lang.String", REQUEST_SCOPE },
					{ "remark", "java.lang.String", REQUEST_SCOPE },
					{ "cashId", "java.lang.String", REQUEST_SCOPE },
					{ "facilityId", "java.lang.String", REQUEST_SCOPE },
					{ "facilityName", "java.lang.String", REQUEST_SCOPE },
        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ICashCollateral iCash;
        ICashCollateral iCashCol = null;

        long index = Long.parseLong((String) map.get("indexID"));
        long cashId = Long.parseLong((String) map.get("cashId"));
        String refId=(String)map.get("ref_id");
        if(null==refId || "".equals(refId)){
        	refId=(String)map.get("ref_id.session");
        }

        // ICashDeposit iCashDep = (ICashDeposit) map.get("form.depositObject");
        String from_event = (String) map.get("from_event");
        OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
        try {
			itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(ctx, itrxValue.getTransactionID());
		} catch (CollateralException e) {
			e.printStackTrace();
		}
        ICashDeposit dep = null;
        
        if ((from_event != null) && from_event.equals("read")) {
            iCash = (ICashCollateral) itrxValue.getCollateral();
        } else {
            iCash = (ICashCollateral) itrxValue.getStagingCollateral();
            iCashCol = (ICashCollateral) itrxValue.getCollateral();
            /*if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())) {
                Map context = new HashMap();
                //Andy Wong: set CMV to staging if actual got value but staging blank, used for pre Stp valuation validation
                if(itrxValue.getCollateral()!=null && itrxValue.getCollateral().getCMV()!=null
                        && (itrxValue.getStagingCollateral().getCMV()==null || itrxValue.getStagingCollateral().getCMV().getAmount() <= 0)){
                    itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
                }
                context.put(CollateralStpValidator.COL_OB, iCash);
                context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
                context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
                ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
                if (!errors.isEmpty()) {
                    temp.put(MESSAGE_LIST, errors);
                }
            }*/

            /*if ((from_event != null) && from_event.equals("process")) {
                ICashDeposit actualDep = null;
                if (itrxValue.getCollateral() != null) {
                    actualDep = getItem(((ICashCollateral) itrxValue.getCollateral()).getDepositInfo(), index);
                }
                ICashDeposit stageDep = getItem(iCash.getDepositInfo(), index);
                result.put("actualDeposit", actualDep);
                result.put("stageDeposit", stageDep);
            }
        }*/
       /* ICashDeposit dep;
        if (from_event != null) {
            dep = getItem(iCash.getDepositInfo(), index);
            if ((dep == null) && from_event.equals("process")) {
                if (itrxValue.getCollateral() != null) {
                    dep = getItem(((ICashCollateral) itrxValue.getCollateral()).getDepositInfo(), index);
                }
            }
        } else {
            dep = iCash.getDepositInfo()[(int) index];
        }*/
        /**
         * add by sachin
         * */
            
            for(int i = 0; i< iCashCol.getDepositInfo().length ;i++)
            {
            	if(cashId == iCashCol.getDepositInfo()[i].getCashDepositID())
            	{
            		 dep = iCashCol.getDepositInfo()[i];
            	}
            } 
        
        }
        
        for(int i = 0; i< iCash.getDepositInfo().length ;i++)
        {
        	if(cashId == iCash.getDepositInfo()[i].getCashDepositID())
        	{
        		 dep = iCash.getDepositInfo()[i];
        	}
        }
        ICashDeposit actualDep = null;
        if (itrxValue.getCollateral() != null) {
            actualDep = getItem(((ICashCollateral) itrxValue.getCollateral()).getDepositInfo(), refId);
        }
        ICashDeposit stageDep = null;
        stageDep=getItem(((ICashCollateral) itrxValue.getStagingCollateral()).getDepositInfo(), refId);
        //ICashDeposit stageDep = getItem(iCash.getDepositInfo(), index);
        result.put("actualDeposit", actualDep);
        result.put("stageDeposit", stageDep);
       
        String event = (String) map.get("event");
       // List list = (List)map.get("lienList");
		//if(list==null)
		//{
        List list=null;
		list = new ArrayList();
		ILienMethod[] ilean = dep.getLien();
		if(ilean != null)
        {
        for(int i=0;i<ilean.length;i++)
        {
        	OBLien lien = new OBLien();
        	lien.setCashDepositID(ilean[i].getCashDepositID());
        	lien.setLienAmount(ilean[i].getLienAmount());
        	lien.setLienNumber(ilean[i].getLienNumber());
        	lien.setLienID(ilean[i].getLienID());
        	lien.setSerialNo(ilean[i].getSerialNo());
        	lien.setRemark(ilean[i].getRemark());
        	lien.setBaselSerial(ilean[i].getBaselSerial());
        	lien.setActualLienId(ilean[i].getActualLienId());
        	lien.setFacilityName(ilean[i].getFacilityName());
        	lien.setFacilityId(ilean[i].getFacilityId());
        	lien.setLcnNo(ilean[i].getLcnNo());
        	list.add(lien);
        
        }
       // }
		}
		
		
       /* if (from_event != null) {
        	if(dep.getLien()!=null){
        	ilean = getLienItem(dep.getLien(), index);
        	//list = getLienItemList(dep.getLien(), index);
            if ((ilean == null) && from_event.equals("process")) {
                if (itrxValue.getCollateral() != null) {
                	ilean = getLienItem(((ICashDeposit) itrxValue.getCollateral()).getLien(), index);
                	if(ilean != null)
                    {
                    for(int i=0;i<ilean.length;i++)
                    {
                    	OBLien lien = new OBLien();
                    	lien.setCashDepositID(ilean[i].getCashDepositID());
                    	lien.setLienAmount(ilean[i].getLienAmount());
                    	lien.setLienNumber(ilean[i].getLienNumber());
                    	lien.setLienID(ilean[i].getLienID());
                    	list.add(lien);
                    
                    }
                    }
                	//	list = getLienItemList(((ICashDeposit) itrxValue.getCollateral()).getLien(), index);
                }
            }
        }
        } else {
        	ilean = dep.getLien();
        	//list = dep.getLien();
        }*/
        
       // 
		/*if( lienNumber != null && ! lienNumber.equals("") )
		lien.setLienNumber(Long.parseLong(lienNumber));
		if( lienAmount != null && ! lienAmount.equals("") )
			lien.setLienAmount(Long.parseLong(lienAmount));*/
       // lien.setLienNumber(ilean.getLienNumber());
       // lien.setLienAmount(ilean.getLienAmount());
        /*if(ilean != null)
        {
        for(int i=0;i<ilean.length;i++)
        {
        	OBLien lien = new OBLien();
        	lien.setCashDepositID(ilean[i].getCashDepositID());
        	lien.setLienAmount(ilean[i].getLienAmount());
        	lien.setLienNumber(ilean[i].getLienNumber());
        	lien.setLienID(ilean[i].getLienID());
        	list.add(lien);
        
        }
        }*/
		/*ICashDeposit dep1;
		dep1 = (iCash.getDepositInfo()[0]).getLien()[0];
		OBLien lien = new OBLien();
		if( lienNumber != null && ! lienNumber.equals("") )
		lien.setLienNumber(Long.parseLong(lienNumber));
		if( lienAmount != null && ! lienAmount.equals("") )
			lien.setLienAmount(Long.parseLong(lienAmount));
		
		
		list.add(lien);
		*/
	//	ILienMethod obLien = (OBLien)map.get("OBLien");
		String lienNumber = (String) map.get("lienNo");
		String lienAmount = (String) map.get("lienAmount");
		String lcnNo  = (String)map.get("lcnNo");
		String serialNo = (String) map.get("serialNo");
		String remark = (String) map.get("remark");
		String facilityId = (String) map.get("facilityId");
		String facilityName = (String) map.get("facilityName");
		
		result.put("lienNo", lienNumber);
		result.put("lienAmount", lienAmount);
		result.put("ref_id.session", refId);
		result.put("lcnNo", lcnNo);		
		result.put("serialNo", serialNo);
		result.put("remark", remark);
		result.put("facilityId", facilityId);
		result.put("facilityName", facilityName);
		
		
		//result.put("OBLien", ilean);
		
		result.put("lienList", list);
		 /**
         * add by sachin
         * */
		
		if(itrxValue != null)
		{
			
			long partyid =   itrxValue.getCollateral().getCollateralID() ;
			  String  partyName = getPartyNamebyCode(partyid);
			  result.put("custname", partyName);
		}
        result.put("form.depositObject", dep);
        result.put("depositObj", dep);

        result.put("indexID", map.get("indexID"));
        result.put("cashId", (String) map.get("cashId"));
        result.put("subtype", map.get("subtype"));
        result.put("from_event", from_event);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
    private String getPartyNamebyCode(long partyCode) {
		String partyName = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				partyName = CollateralDAOFactory.getDAO().getPartyNamebyCode(partyCode);
		}
		catch (Exception ex) {
		}
		return partyName;
	}

    private ICashDeposit getItem(ICashDeposit temp[], long itemRef) {
        ICashDeposit item = null;
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
            
                item = temp[i];
           
		}
		return item;
	}
    
    private ICashDeposit getItem(ICashDeposit temp[], String refId) {
        ICashDeposit item = null;
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
            
                item = temp[i];
                if(item.getDepositRefNo().equals(refId)){
                	 return item;
                }
           
		}
		return item;
	}
    
    private ILienMethod[] getLienItem(ILienMethod temp[], long itemRef) {
    	ILienMethod[] item = new ILienMethod[temp.length];    	
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
          //  if (temp[i].getCashDepositID() == itemRef) {
            	item[i] = temp[i];
         //   } else {
			//	continue;
		//	}
		}
		return item;
	}
   /* private List getLienItemList(ILienMethod temp[], long itemRef) {
    	List itemList = null;
    	
        if (temp == null) {
            return itemList;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].getCashDepositID() == itemRef) {
            	itemList.add(temp[i]) ;
            	
            } else {
				continue;
			}
		}
		return itemList;
	}*/

}
