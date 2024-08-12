package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static com.integrosys.cms.ui.collateral.CollateralConstant.PARENT_PAGE;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import static com.integrosys.cms.ui.collateral.CollateralAction.EVENT_PREPARE_CREATE;
import static com.integrosys.cms.ui.collateral.CollateralAction.EVENT_PREPARE_UPDATE;
import static com.integrosys.base.uiinfra.common.ICommonEventConstant.EVENT_READ;
import static com.integrosys.cms.ui.collateral.CollateralAction.EVENT_PROCESS;
import static com.integrosys.cms.ui.collateral.CollateralAction.EVENT_PROCESS_UPDATE;
import static com.integrosys.cms.ui.collateral.CollateralAction.EVENT_CLOSE;
public class AssetGenChargeHelper extends JdbcTemplateAdapter {

	public static List getLocationList() {
		ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
		List cityList=(ArrayList)proxy.getCityByCountryCode(PropertyManager.getValue("clims.application.country","INDIA"));
	    List locationList= new ArrayList();
		LabelValueBean lvBeanAll = new LabelValueBean(ICMSConstant.CITY_LABEL_ALL,ICMSConstant.CITY_VALUE_ALL);
		locationList.add(lvBeanAll);
	    if(cityList!=null && cityList.size()>0){
	    	String label;
			String value;
	    for (Iterator iter = cityList.iterator(); iter.hasNext();) {
			ICity city = (OBCity) iter.next();
	    	label=city.getCityName();
			value= Long.toString(city.getIdCity());
			LabelValueBean lvBean = new LabelValueBean(label,value);
			locationList.add(lvBean);
	    }
	    }
		return locationList;
	}
	public static List getDisplayDueDateList(List retrivedDueDateList) {
		List dueDateList= new ArrayList();
		 if(retrivedDueDateList!=null && retrivedDueDateList.size()>0){
		    	String label;
				String value;
				String dateWithDocCode;
		    for (Iterator iter = retrivedDueDateList.iterator(); iter.hasNext();) {
		    	Object obj = iter.next();
		    	if(obj!=null){
		    		dateWithDocCode=(String)obj;
//		    		Date dateObj = (Date)obj;
//					if (dateObj.before(DateUtil.getDate())) {
						label=dateWithDocCode.split(",")[0];
						value=dateWithDocCode;
						LabelValueBean lvBean = new LabelValueBean(label,value);
						dueDateList.add(lvBean);
//					}
		    	}
		    }
		    }
			return dueDateList;
	}
	
	public static String getActionNameForRouting(String event, HashMap<String,Object> map){
		String parentPageFrom = (String) map.get(PARENT_PAGE);
		if(StringUtils.isNotBlank(parentPageFrom))
			return parentPageFrom;
		else if(EVENT_PREPARE_CREATE.equals(event)
			||EVENT_PREPARE_UPDATE.equals(event))
			return "ASSET_UPDATE";
		else if(EVENT_READ.equals(event))
			return "ASSET_READ";
		else if(EVENT_PROCESS.equals(event)
				||EVENT_PROCESS_UPDATE.equals(event))
			return "ASSET_PROCESS";
		else if(EVENT_CLOSE.equals(event))
			return "ASSET_CLOSE";
		else
			return "";
	}
	
	public static String getLocationNameById(long locationID) {
		
		String cityName = null;
		
		ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
		
		if(ICMSConstant.CITY_VALUE_ALL.equals(String.valueOf(locationID))){
			cityName=ICMSConstant.CITY_LABEL_ALL;
		}else{
			ICityTrxValue cityById;
			try {
				cityById = proxy.getCityById(locationID);
				cityName = cityById.getActualCity().getCityName();
			} catch (Exception e) {
				DefaultLogger.error(DueDateAndStockHelper.class.getName(), "Exception caught in getLocationNameById : "+e.getMessage(), e);
			}
			
		}
		
		return cityName;
	}
	
	public List getCompoList(String category) throws SearchDAOException {
		
		List compoList = new ArrayList();
		try {
		String sql = "select COMPONENT_CODE,COMPONENT_NAME from CMS_COMPONENT where COMPONENT_CATEGORY = '"+category+"' and DEPRECATED ='N' ";
		List queryForList = getJdbcTemplate().queryForList(sql);
		if(null != queryForList) {
			for (int i = 0; i < queryForList.size(); i++) {
				Map  map = (Map) queryForList.get(i);
//			
				String id= map.get("COMPONENT_CODE").toString();
				String val =  map.get("COMPONENT_NAME").toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				compoList.add(lvBean);
			}
		}
		}catch(Exception e) {
			System.out.println("Exception in getCompoList()"+e.getMessage());
			e.printStackTrace();
		}
		return compoList;
	}
	
}
