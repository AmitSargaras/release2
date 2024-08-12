package com.integrosys.cms.ui.geography.country;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;

public class LocationHelper extends HibernateDaoSupport{

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws ValuationAgencyException,TrxParameterException, TransactionException {
		String entityCountryName = "actualCountry";
		String entityRegionName = "actualRegion";
		String entityStateName = "actualState";
		String entityCityName = "actualCity";
		String code = "";
		
		if( countryCode != null &&  ! countryCode.equals("") ){
			String countryQuery = "SELECT FROM " + entityCountryName + " c WHERE UPPER(country_code) like '"+countryCode.toUpperCase()+"' ";
			List countryList = (ArrayList) getHibernateTemplate().find(countryQuery);
			if( countryList.size() > 0 ){
				if( regionCode != null && ! regionCode.equals("") ){ 
					Iterator countryIterator = countryList.iterator();
					if( countryIterator.hasNext() ){
						ICountry country = (ICountry)countryIterator.next();
						long countryId = country.getIdCountry();
						String regionQuery = "SELECT FROM " + entityRegionName + " reg WHERE UPPER(region_code) like '"+regionCode.toUpperCase()+"' AND reg.countryId = "+new Long(countryId);
						List regionList = (ArrayList) getHibernateTemplate().find(regionQuery);
						if( regionList.size() > 0 ){
							if( stateCode != null && ! stateCode.equals("") ){ 
								Iterator regionIterator = regionList.iterator();
								if( regionIterator.hasNext() ){
									IRegion region = (IRegion)regionIterator.next();
									long regionId = region.getIdRegion();
									String stateQuery = "SELECT FROM " + entityStateName + " st WHERE UPPER(state_code) like '"+stateCode.toUpperCase()+"' AND st.regionId = "+new Long(regionId);
									List stateList = (ArrayList) getHibernateTemplate().find(stateQuery);
									if( stateList.size() > 0 ){
										if( cityCode != null && ! cityCode.equals("") ){
											Iterator stateIterator = stateList.iterator();
											if( stateIterator.hasNext() ){
												IState state = (IState)stateIterator.next();
												long stateId = state.getIdState();
												String cityQuery = "SELECT FROM " + entityCityName + " ct WHERE UPPER(city_code) like '"+cityCode.toUpperCase()+"' AND ct.stateId = "+new Long(stateId);
												List cityList = (ArrayList) getHibernateTemplate().find(cityQuery);
												if( cityList.size() == 0 ){
													code = "cityCode";	// City Code Not Exist;
												}
											}											
										}	// End If City Code
									}	// End If stateList
									else{
										code = "stateCode";	// State Code Not Exist;
									}
								}		
							}	// End If State Code
						}	// End If regionList
						else{
							code = "regionCode";	// Region Code Not Exist;
						}							
					}
				}	// End If Region Code
			}	// End If countryList
			else{
				code = "countryCode";	// Country Code Not Exist;
			}			
		}
		return code;
	}
}
