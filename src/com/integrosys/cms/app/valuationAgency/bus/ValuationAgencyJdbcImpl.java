package com.integrosys.cms.app.valuationAgency.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.bus.HolidayJdbcImpl.HolidayRowMapper;

public class ValuationAgencyJdbcImpl extends JdbcDaoSupport implements
		IValuationAgencyJdbc {
	private static final String SELECT_VALUATION_AGENCY_TRX = "select ID,VERSION_TIME,CREATE_BY,CREATION_DATE,LAST_UPDATE_BY,LAST_UPDATE_DATE,DEPRECATED,STATUS,VALUATION_AGENCY_CODE,VALUATION_AGENCY_NAME,ADDRESS,STATE,REGION,COUNTRY,CITY_TOWN from CMS_VALUATION_AGENCY where master_id is not null";
	private static final String SELECT_INSERT_VALUATION_AGENCY_TRX = "SELECT ID,VERSION_TIME,CREATE_BY,CREATION_DATE,LAST_UPDATE_BY,LAST_UPDATE_DATE,DEPRECATED,STATUS,VALUATION_AGENCY_CODE,VALUATION_AGENCY_NAME,ADDRESS,STATE,REGION,COUNTRY,CITY_TOWN from CMS_STAGE_VALUATION_AGENCY where deprecated='N' AND ID ";
	private static final String GET_COUNTRY = "select ID,COUNTRY_NAME from CMS_COUNTRY where DEPRECATED='N' AND ID=";
	private static final String SELECT_REGION_TRX = "SELECT id,region_name from CMS_REGION where deprecated='N' AND id=";
	private static final String SELECT_STATE_TRX = "SELECT id,state_name from CMS_STATE where deprecated='N' AND id=";
	private static final String SELECT_CITY_TRX = "SELECT id,city_name from CMS_CITY where deprecated='N' AND id=";
	
	/**
	 * @return List of all authorized Valuation Agency
	 */

	
	
/**	
	public boolean isPrevFileUploadPending() {

		String rowCount = "SELECT TRANSACTION_TYPE FROM TRANSACTION WHERE TRANSACTION_TYPE='INSERT_VALUATION_AGENCY'";
		List rowList = getJdbcTemplate().queryForList(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "SELECT TRANSACTION_TYPE FROM TRANSACTION WHERE TRANSACTION_TYPE='INSERT_VALUATION_AGENCY' AND STATUS != 'ACTIVE'";
			List valuationAgencyList = getJdbcTemplate().queryForList(sqlQuery);
			if(valuationAgencyList.size()>0){
				return true;
			}
		}
		return false;
	}
	**/
	public List getAllStageValuationAgency(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		String SearchQuery ="SELECT SYS_ID from CMS_FILE_MAPPER where TRANS_ID='"+searchBy +"'";
		List listId = getJdbcTemplate().queryForList(SearchQuery);

		for (int i = 0; i < listId.size(); i++) {
			HashMap map = (HashMap) listId.get(i);
//				System.out.println("val = " + map.get("SYS_ID"));
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.get("SYS_ID").toString();
		}
		
		if(!strId.equals("")){
			String GET_BRANCH_QUERY_STRING = SELECT_INSERT_VALUATION_AGENCY_TRX
			+ "in ( " + strId +  " )";
			try {
				if (searchByValue.trim() != "") {
					resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
							new ValuationAgencyRowMapper());
				}
	
			} catch (Exception e) {
				throw new ValuationAgencyException("ERROR-- While retriving ValuationAgency");
			}
		}
		return resultList;
	}
	
	/**
	 * @return list of files uploaded in staging table of ValuationAgency.
	 */
	public List getFileMasterList(String searchBy) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		String SearchQuery ="SELECT SYS_ID from CMS_FILE_MAPPER where TRANS_ID='"+searchBy +"' ";
		List listId = getJdbcTemplate().queryForList(SearchQuery);
       return listId;
	}
	
	public class ValuationAgencyRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBValuationAgency result = new OBValuationAgency();
			result.setValuationAgencyCode(rs.getString("VALUATION_AGENCY_CODE"));
			result.setValuationAgencyName(rs.getString("VALUATION_AGENCY_NAME"));
			
			ICountry country = getCountry(rs.getString("COUNTRY"));
			result.getCountry().setIdCountry(country.getIdCountry());
			
			IRegion region = getRegion(rs.getString("REGION"));
			result.getRegion().setIdRegion(region.getIdRegion());
			
			IState state = getState(rs.getString("STATE"));
			result.getState().setIdState(state.getIdState());
			
			ICity city = getCity(rs.getString("CITY_TOWN"));
			result.getCityTown().setIdCity(city.getIdCity());
			
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setLastUpdateBy(rs.getString("last_update_by"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	public ICountry getCountry(String countryId) {
		ICountry country = new OBCountry();
		try {
			String select_region = GET_COUNTRY + countryId;
			List resultList = getJdbcTemplate().query(select_region,
					new CountryRowMapper());
			Iterator itr = resultList.iterator();
			while (itr.hasNext()) {
				OBCountry countryResult = (OBCountry) itr.next();
				country.setIdCountry(countryResult
						.getIdCountry());
				country.setCountryName(countryResult
						.getCountryName());
			}

		} catch (Exception e) {
			throw new ValuationAgencyException("ERROR-- While retriving RelationshipMgr");
		}
		return country;
	}
	
	public class CountryRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBRegion result = new OBRegion();
			result.setRegionName(rs.getString("region_name"));
			result.setIdRegion(rs.getLong("id"));
			return result;
		}
	}
	

	public IRegion getRegion(String regionId) {
		IRegion region = new OBRegion();
		try {
			String select_region = SELECT_REGION_TRX + regionId;
			List resultList = getJdbcTemplate().query(select_region,
					new RegionRowMapper());
			Iterator itr = resultList.iterator();
			while (itr.hasNext()) {
				OBRegion regionResult = (OBRegion) itr.next();
				region.setIdRegion(regionResult
						.getIdRegion());
				region.setRegionName(regionResult
						.getRegionName());
			}

		} catch (Exception e) {
			throw new ValuationAgencyException("ERROR-- While retriving RelationshipMgr");
		}
		return region;
	}
	
	public class RegionRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBRegion result = new OBRegion();
			result.setRegionName(rs.getString("region_name"));
			result.setIdRegion(rs.getLong("id"));
			return result;
		}
	}
	
	public IState getState(String stateId) {
		IState state = new OBState();
		try {
			String select_state = SELECT_STATE_TRX + stateId;
			List resultList = getJdbcTemplate().query(select_state,
					new StateRowMapper());
			Iterator itr = resultList.iterator();
			while (itr.hasNext()) {
				OBState stateResult = (OBState) itr.next();
				state.setIdState(stateResult
						.getIdState());
				state.setStateName(stateResult
						.getStateName());
			}

		} catch (Exception e) {
			throw new ValuationAgencyException("ERROR-- While retriving RelationshipMgr");
		}
		return state;
	}
	
	public class StateRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBState result = new OBState();
			result.setStateName(rs.getString("state_name"));
			result.setIdState(rs.getLong("id"));
			return result;
		}
	}
	
	public ICity getCity(String cityId) {
		ICity city = new OBCity();
		try {
			String select_city = SELECT_CITY_TRX + cityId;
			List cityList = getJdbcTemplate().query(select_city,
					new CityRowMapper());
			Iterator itr = cityList.iterator();
			while (itr.hasNext()) {
				OBCity stateResult = (OBCity) itr.next();
				city.setIdCity(stateResult
						.getIdCity());
				city.setCityName(stateResult
						.getCityName());
			}

		} catch (Exception e) {
			throw new ValuationAgencyException("ERROR-- While retriving RelationshipMgr");
		}
		return city;
	}
	
	public class CityRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCity result = new OBCity();
			result.setCityName(rs.getString("city_name"));
			result.setIdCity(rs.getLong("id"));
			return result;
		}
	}


	
}
