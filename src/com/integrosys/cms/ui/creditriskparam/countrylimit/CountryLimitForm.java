/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitForm extends TrxContextForm implements Serializable{		
   
	private String[] deletedItemList;
    private List countryLimitItemList;	
    private List countryRatingList;		 
	
     /**
       * Description : set the list of item deleted
       *
       * @param deletedItemList is the list of item deleted
       */
    public void setDeletedItemList(String[] deletedItemList) {
        this.deletedItemList = deletedItemList;
    }
	
   /**
       * Description : get method for form to get the list of item deleted
       *
       * @return deletedItemList
       */
    public String[] getDeletedItemList() {
        return this.deletedItemList;
    }

    /**
       * Description : set the list of country limit item added
       *
       * @param countryLimitItemList is the list of country limit item added
       */
    public void setCountryLimitItemList(List countryLimitItemList) {
        this.countryLimitItemList = countryLimitItemList;
    }
	
	/**
       * Description : get method for form to get the list of country limit item added
       *
       * @return countryLimitItemList
       */
	public List getCountryLimitItemList() {
        return this.countryLimitItemList;
    }   
	
	/**
       * Description : set the list of country rating added
       *
       * @param countryRatingList is the list of country rating added
       */
    public void setCountryRatingList(List countryRatingList) {
        this.countryRatingList = countryRatingList;
    }
	
	/**
       * Description : get method for form to get the list of country rating added
       *
       * @return countryRatingList
       */
	public List getCountryRatingList() {
        return this.countryRatingList;
    }   
	   
	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.CommonForm#getMapper()
	 */
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = 
		{
			{"CountryLimitForm", "com.integrosys.cms.ui.creditriskparam.countrylimit.CountryLimitMapper"},
            {"deleteCountryLimit", "com.integrosys.cms.ui.creditriskparam.countrylimit.DeleteCountryLimitMapper"},
			{"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
		};
		return input;
	}
		
	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}
}
