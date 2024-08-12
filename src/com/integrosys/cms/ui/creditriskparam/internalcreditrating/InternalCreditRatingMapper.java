package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternalCreditRatingMapper extends AbstractCommonMapper {

        public String[][] getParameterDescriptor() {
        	return (new String[][]{
				{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},			
        	});
    	}

	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
			throws MapperException {		
		try
	{
			String event = (String)(inputs.get("event"));
			IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)(inputs.get("internalCreditRatingTrxObj"));
			IInternalCreditRatingParam creditRatingParam = (IInternalCreditRatingParam)obj;			
			InternalCreditRatingForm internalCreditRatingForm = (InternalCreditRatingForm)commonForm;				
							
			
				if(creditRatingParam.getInternalCreditRatingList()!= null)
				{
			    internalCreditRatingForm.setInternalCreditRatingItemList(creditRatingParam.getInternalCreditRatingList());
		    }
		    else
		    {
			     internalCreditRatingForm.setInternalCreditRatingItemList(new ArrayList());
		    }
		    
		  if("checker_prepare_process".equals(event))
			{
			  renderCompareItem(internalCreditRatingTrxObj.getActualICRList(), internalCreditRatingTrxObj.getStagingICRList(), internalCreditRatingForm);
		  }
		  
      internalCreditRatingForm.setDeletedItemList(new String[0]);
				
					
	    return internalCreditRatingForm;
	
	}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new MapperException();
		}
	}	
	
	
	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs)
			throws MapperException {
		// TODO Auto-generated method stub
		    String event = (String)(inputs.get("event"));
		
        IInternalCreditRatingParam paramList = null;
        InternalCreditRatingForm form = (InternalCreditRatingForm) commonForm;

        IInternalCreditRatingTrxValue trxValue = (IInternalCreditRatingTrxValue) inputs.get("internalCreditRatingTrxObj");

        if (paramList == null) {
            paramList = new OBInternalCreditRatingParam();
        }
        paramList.setInternalCreditRatingList(trxValue.getStagingICRList());
        if (event.equals("maker_delete_item")) {
            deleteItem(paramList, form);
        }

       
        return paramList;

	}
	
	private void deleteItem(IInternalCreditRatingParam paramList, InternalCreditRatingForm aForm) {
        String[] deleteInd = aForm.getDeletedItemList();
        ArrayList obCol = new ArrayList(paramList.getInternalCreditRatingList());

        if (deleteInd != null && deleteInd.length > 0) {
           List temp = new ArrayList();
            for (int i = 0; i < deleteInd.length; i++) {
                int nextDelInd = Integer.parseInt(deleteInd[i]);
                temp.add(obCol.get(nextDelInd));
            }

            obCol.removeAll(temp);
            temp.clear();
        }

        paramList.setInternalCreditRatingList(obCol);
        DefaultLogger.debug(this, "getInternalCreditRatingList,mapper::::" + paramList.getInternalCreditRatingList());
    }
    
    private static void renderCompareItem(List actualItem, List stageItem, InternalCreditRatingForm aForm) throws Exception {
       IInternalCreditRating[] oldLmtRef = null;
		   IInternalCreditRating[] newLmtRef = null;

        if (actualItem != null) {
            ArrayList compareitemList = new ArrayList(actualItem);
            oldLmtRef = (IInternalCreditRating[]) compareitemList.toArray(new IInternalCreditRating[compareitemList.size()]);
        }
        if (stageItem != null) {
            ArrayList comparestagingitemList = new ArrayList(stageItem);
            newLmtRef = (IInternalCreditRating[]) comparestagingitemList.toArray(new IInternalCreditRating[comparestagingitemList.size()]);
        }

        if (oldLmtRef == null) {
            oldLmtRef = new IInternalCreditRating[0];
        }
        if (newLmtRef == null) {
            newLmtRef = new IInternalCreditRating[0];
        }

        
        //List compareRatingList = CompareOBUtil.compOBArray(newRatingRef, oldRatingRef);
				
		    // ctryLimitForm.setCountryRatingList( compareRatingList );
        aForm.setInternalCreditRatingItemList(CompareOBUtil.compOBArray(newLmtRef, oldLmtRef));
    }
	
	
}
