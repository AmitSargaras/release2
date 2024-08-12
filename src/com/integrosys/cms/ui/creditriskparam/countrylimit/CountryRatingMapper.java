/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

import com.integrosys.cms.ui.common.ConvertFloatToString;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryRatingMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
            {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},			
		});
	}

	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
			throws MapperException {
		try
		{
        	CountryLimitUIHelper helper = new CountryLimitUIHelper();
			ICountryRating[] item = (ICountryRating[])obj;
			CountryRatingForm itemForm = (CountryRatingForm)commonForm;				

			if (item.length != 0) {
				String countryRatingCode[] = new String[item.length];
				String bankCapFundPercentage[] = new String[item.length];
				String presetCtryLimitPercentage[] = new String[item.length];
				
				for (int i = 0; i < item.length; i++) {
					Double perValue = item[i].getBankCapFundPercentage();
					if(perValue!=null){
						bankCapFundPercentage[i] = perValue.toString();
					}
					else {
						bankCapFundPercentage[i] = "";
					}
					
					perValue = item[i].getPresetCtryLimitPercentage();
					if(perValue!=null){
						presetCtryLimitPercentage[i] = perValue.toString();
					}
					else {
						presetCtryLimitPercentage[i] = "";
					}
					countryRatingCode[i] = item[i].getCountryRatingCode();
				}
				itemForm.setCountryRating( countryRatingCode );
				itemForm.setBankCapFundPercent( bankCapFundPercentage );
				itemForm.setPresetCountryLimitPercent( presetCtryLimitPercentage );
			}
							
			return itemForm;
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
		
		try
		{
			CountryRatingForm itemForm = (CountryRatingForm)commonForm;
						
			ICountryLimitTrxValue trxValue = (ICountryLimitTrxValue)(inputs.get("countryLimitTrxObj"));				
			ICountryLimitParam countryLimitObj = trxValue.getStagingCountryLimitParam();				
		
			ICountryRating[] actualCtryRating = countryLimitObj.getCountryRatingList();
			String[] ratingList = itemForm.getCountryRating();			
			String[] bankCapFundPercentList = itemForm.getBankCapFundPercent();			
			String[] presetCountryLimitPercentList = itemForm.getPresetCountryLimitPercent();			

			for (int i = 0; i < actualCtryRating.length; i++) {
				boolean found = false;				
				
				if( ratingList != null ) {
					for (int j = 0; j < ratingList.length; j++) {

						if( actualCtryRating[i].getCountryRatingCode().equals( ratingList[j] ) 
						) {
							if( !bankCapFundPercentList[j].trim().equals("") ) {								
								actualCtryRating[i].setBankCapFundPercentage ( new Double( bankCapFundPercentList[j] ) );		
							}			
							if( !presetCountryLimitPercentList[j].trim().equals("") ) {							
								actualCtryRating[i].setPresetCtryLimitPercentage ( new Double( presetCountryLimitPercentList[j] ) );							
							}
							found = true;
							DefaultLogger.debug (this, "found, updating rating code: " + actualCtryRating[i].getCountryRatingCode() );
							break;
						}
					}
				}				
				
			}							
			return actualCtryRating;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new MapperException();
		}
	}	

}
