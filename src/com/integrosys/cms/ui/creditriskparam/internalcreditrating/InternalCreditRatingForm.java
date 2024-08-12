package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.io.Serializable;
import java.util.List;
import org.apache.struts.action.ActionMapping;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternalCreditRatingForm extends TrxContextForm implements Serializable{		
   
		private String[] deletedItemList;
	  private List internalCreditRatingItemList;	
	   
     
    public void setDeletedItemList(String[] deletedItemList) {
        this.deletedItemList = deletedItemList;
    }
	
    public String[] getDeletedItemList() {
        return deletedItemList;
    }

    public void setInternalCreditRatingItemList(List internalCreditRatingItemList) {
        this.internalCreditRatingItemList = internalCreditRatingItemList;
    }
	
		public List getInternalCreditRatingItemList() {
        return internalCreditRatingItemList;
    }   
	
		public String[][] getMapper() {
		// TODO Auto-generated method stub
			String[][] input = 
			{
				{"InternalCreditRatingForm", "com.integrosys.cms.ui.creditriskparam.internalcreditrating.InternalCreditRatingMapper"},				
				{"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
			};
			return input;
		}
		
}
