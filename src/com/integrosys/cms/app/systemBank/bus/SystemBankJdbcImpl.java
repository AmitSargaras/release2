package com.integrosys.cms.app.systemBank.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;

/**
 * @author abhijit.rudrakshawar
 * 
 * Jdbc implication of Interface System Bank Jdbc
 */

public class SystemBankJdbcImpl extends JdbcDaoSupport implements
		ISystemBankJdbc {

	private static final String SELECT_SYSTEM_BANK_TRX = "SELECT id,system_bank_code,system_bank_name,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from CMS_SYSTEM_BANK where master_id is not null ";

	/**
	 * @return List of all  System Bank according to the query passed.
     */
	
	public List listSystemBank(long bankCode) throws SearchDAOException {

		String SELECT_SYSTEM_BANK_ID = "SELECT id,system_bank_code,system_bank_name,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from cms_system_bank  where id="
				+ bankCode;

		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_SYSTEM_BANK_ID,
					new SystemBankRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemBankException(
					"ERROR-- Unable to get System Bank List");
		}

		return resultList;

	}
	/**
	 * @return List of all authorized System Bank
	 */
	
	public List getAllSystemBank() {
		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_SYSTEM_BANK_TRX,
					new SystemBankRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemBankException(
					"ERROR-- Unable to get System Bank List");
		}

		return resultList;
	}

	public class SystemBankRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBSystemBank result = new OBSystemBank();
			result.setCityTown(new OBCity());
			result.setState(new OBState());
			result.setRegion(new OBRegion());
			result.setCountry(new OBCountry());
			result.setSystemBankCode(rs.getString("system_bank_code"));
			result.setSystemBankName(rs.getString("system_bank_name"));
			result.setAddress(rs.getString("address"));
			
			result.getCityTown().setIdCity(Long.parseLong(rs.getString("city_town")));
			result.getCountry().setIdCountry(Long.parseLong(rs.getString("country")));
			result.getRegion().setIdRegion(Long.parseLong(rs.getString("region")));
			result.getState().setIdState(Long.parseLong(rs.getString("state")));
			result.setContactNumber(rs.getLong("contact_number"));
			result.setContactMail(rs.getString("contact_mail"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}


}
