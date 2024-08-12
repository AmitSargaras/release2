/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerDAO.java,v 1.62 2006/08/22 05:40:41 jzhai Exp $
 */
package com.integrosys.cms.app.manualinput.aa.bus;

//java

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchResultSetProcessUtils;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.math.BigDecimal;
/**
 * DAO for customer
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.62 $
 * @since $Date: 2006/08/22 05:40:41 $ Tag: $Name: $
 */

public class CAMDAO extends JdbcTemplateAdapter {

    private final Logger logger = LoggerFactory.getLogger(CAMDAO.class);
    /**
     * Default Constructor
     */
    public CAMDAO() {
    }

    /**
     * Get a list of customers info by list if limit ID
     *
     * @param limitIDList - Collection
     * @return Collection - list of customers info based of limit ID given
     * @throws SearchDAOException if errors
     */
    public List getCreditApproverList() throws SearchDAOException {
        	String sql = "SELECT APPROVAL_CODE, APPROVAL_NAME, SENIOR FROM CMS_CREDIT_APPROVAL WHERE STATUS='ACTIVE' ";
        	
    	 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                 String[] stringArray = new String[3];
                 stringArray[0] = rs.getString("APPROVAL_CODE");
                 stringArray[1] = rs.getString("APPROVAL_NAME");
                 stringArray[2] = rs.getString("SENIOR");
                 return stringArray;
             }
         });

         return resultList;
    }
    
    public List getCreditApproverListWithLimit(BigDecimal sanctLmt) throws SearchDAOException {
    	
    	String sql = "SELECT APPROVAL_CODE, APPROVAL_NAME, SENIOR FROM CMS_CREDIT_APPROVAL WHERE STATUS='ACTIVE'  and MAXIMUM_LIMIT >= "+ sanctLmt;
    	
	 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
             String[] stringArray = new String[3];
             stringArray[0] = rs.getString("APPROVAL_CODE");
             stringArray[1] = rs.getString("APPROVAL_NAME");
             stringArray[2] = rs.getString("SENIOR");
             return stringArray;
         }
     });

     return resultList;
}
    
    public List getCheckCreditApproval(String approverCode) throws SearchDAOException {
    	String sql = "SELECT APPROVAL_NAME, SENIOR FROM CMS_CREDIT_APPROVAL WHERE STATUS='ACTIVE' AND APPROVAL_CODE = '" + approverCode+"'";
    	
	 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
             String[] stringArray = new String[2];
             stringArray[0] = rs.getString("APPROVAL_NAME");
             stringArray[1] = rs.getString("SENIOR");
             return stringArray;
         }
     });

     return resultList;
}

}