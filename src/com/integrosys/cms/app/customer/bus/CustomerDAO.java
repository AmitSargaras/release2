/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerDAO.java,v 1.62 2006/08/22 05:40:41 jzhai Exp $
 */
package com.integrosys.cms.app.customer.bus;

//java

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.aurionpro.clims.rest.dto.CoBorrowerDetailsRestRequestDTO;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchResultSetProcessUtils;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.ws.dto.CoBorrowerDetailsRequestDTO;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.UserException;


//import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


//import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * DAO for customer
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.62 $
 * @since $Date: 2006/08/22 05:40:41 $ Tag: $Name: $
 */

public class CustomerDAO extends JdbcTemplateAdapter implements ICustomerDAO {
	private DBUtil dbUtil;
    private final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    protected static String SELECT_DUMMY_PART_SELECT = "SELECT 1 ";

    protected static String SELECT_CUSTOMER_PART_SELECT = "SELECT DISTINCT SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
            + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n      "
            + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n                   "
            + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n              "
            + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n               "
            + "       SCI_LE_MAIN_PROFILE.LMP_ID_TYPE_VALUE, \n           "
            + "       SCI_LE_MAIN_PROFILE.LMP_SECOND_ID_NUMBER, \n        "
            + "       SCI_LE_MAIN_PROFILE.LMP_SECOND_ID_TYPE_VALUE, \n    "
            + "       SCI_LE_MAIN_PROFILE.LMP_THIRD_ID_NUMBER, \n         "
            + "       SCI_LE_MAIN_PROFILE.LMP_THIRD_ID_TYPE_VALUE, \n     "
            + "       SCI_LE_SUB_PROFILE.ULSP_SHORT_NAME, \n               "
            + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n        "
            + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n         "
            + "       SCI_LE_SUB_PROFILE.LSP_ID, \n                       "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n         "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n         "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, \n    "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, \n      "
            + "       SCI_LSP_LMT_PROFILE.CAM_TYPE, \n      "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM, \n             "
            + "       SCI_LSP_LMT_PROFILE.LOS_BCA_REF_NUM, \n             "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_APPR_DATE, \n       "
            + "       SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS, \n              "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, \n            "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, \n       "
            + "       SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE, \n            "
            + "       SCI_LSP_LMT_PROFILE.CMS_CREATE_IND, \n               "
 			+ "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n                 "
            + "       SCI_LE_MAIN_PROFILE.SOURCE_ID \n ";
    
    
    protected static String SELECT_CUSTOMER_PART_SELECT_IMAGE_UPLOAD = "SELECT DISTINCT SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
            + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n      "
            + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n                   "
            + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n              "
            + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n               "
            + "       SCI_LE_MAIN_PROFILE.LMP_ID_TYPE_VALUE, \n           "
            + "       SCI_LE_MAIN_PROFILE.LMP_SECOND_ID_NUMBER, \n        "
            + "       SCI_LE_MAIN_PROFILE.LMP_SECOND_ID_TYPE_VALUE, \n    "
            + "       SCI_LE_MAIN_PROFILE.LMP_THIRD_ID_NUMBER, \n         "
            + "       SCI_LE_MAIN_PROFILE.LMP_THIRD_ID_TYPE_VALUE, \n     "
            + "       SCI_LE_SUB_PROFILE.ULSP_SHORT_NAME, \n               "
            + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n        "
            + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n         "
            + "       SCI_LE_SUB_PROFILE.LSP_ID, \n                       "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n         "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n         "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, \n    "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, \n      "
            + "       SCI_LSP_LMT_PROFILE.CAM_TYPE, \n      "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM, \n             "
            + "       SCI_LSP_LMT_PROFILE.LOS_BCA_REF_NUM, \n             "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_APPR_DATE, \n       "
            + "       SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS, \n              "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, \n            "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, \n       "
            + "       SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE, \n            "
            + "       SCI_LSP_LMT_PROFILE.CMS_CREATE_IND, \n               "
 			+ "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n                 "
            + "       SCI_LE_MAIN_PROFILE.SOURCE_ID \n ";

    // hs: instead of using union for main borrower and join borrower, combine
    // both query into 1 to improve the performance
    protected static String SELECT_CUSTOMER_PART_FROMWHERE = "FROM SCI_LE_MAIN_PROFILE, \n"
            // + "     TRANSACTION, \n"
            + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
            + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
            + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_BANK + "' \n"
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

    // +
    // "      AND TRANSACTION.REFERENCE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
    // + "      AND TRANSACTION.TRANSACTION_TYPE = '" +
    // ICMSConstant.INSTANCE_CUSTOMER + "' ";
    // + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' ";

    protected static String SELECT_ALL_CUSTOMER_PART_FROMWHERE = "FROM SCI_LE_MAIN_PROFILE, \n"
    		
    		/*+ " CMS_UPLOADED_IMAGES, \n"  
    		+ " CMS_IMAGE_TAG_MAP, \n"  
    		+ " CMS_IMAGE_TAG_DETAILS, \n" 
    		+ " CMS_CHECKLIST_ITEM,\n "*/
    		
    		
            // + "     TRANSACTION, \n"
            + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
            + "		SCI_LSP_JOINT_BORROWER ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID \n"
            + "		AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' \n"
            + "     LEFT OUTER JOIN SCI_LSP_LMT_PROFILE ON \n "
            + "		(SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
            + "		OR SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID) \n"
            // + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" +
            // ICMSConstant.AA_TYPE_BANK + "' \n"
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' \n"
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";
           //below line is commented by Anil status is moved to criteria.isActiveCustomer
            //+ "      AND SCI_LE_SUB_PROFILE.STATUS <> 'INACTIVE' \n";  // Shiv 031011

    // +
    // "      AND TRANSACTION.REFERENCE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
    // + "      AND TRANSACTION.TRANSACTION_TYPE = '" +
    // ICMSConstant.INSTANCE_CUSTOMER + "' ";
    // + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' ";
    
    
protected static String SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD = "FROM SCI_LE_MAIN_PROFILE, \n"
    		
    		+ " CMS_UPLOADED_IMAGES, \n"  
    		+ " CMS_IMAGE_TAG_MAP, \n"  
    		+ " CMS_IMAGE_TAG_DETAILS, \n" 
    		+ " CMS_CHECKLIST_ITEM,\n "
    		
    		
            // + "     TRANSACTION, \n"
            + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
            + "		SCI_LSP_JOINT_BORROWER ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID \n"
            + "		AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' \n"
            + "     LEFT OUTER JOIN SCI_LSP_LMT_PROFILE ON \n "
            + "		(SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
            + "		OR SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID) \n"
            // + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" +
            // ICMSConstant.AA_TYPE_BANK + "' \n"
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' \n"
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

	protected static String SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD_CATEGORY = "FROM SCI_LE_MAIN_PROFILE, \n"

    		+ " CMS_UPLOADED_IMAGES, \n"  
    		/*+ " CMS_IMAGE_TAG_MAP, \n"  
    		+ " CMS_IMAGE_TAG_DETAILS, \n" 
    		+ " CMS_CHECKLIST_ITEM,\n "*/
    		
    		
            // + "     TRANSACTION, \n"
            + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
            + "		SCI_LSP_JOINT_BORROWER ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID \n"
            + "		AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' \n"
            + "     LEFT OUTER JOIN SCI_LSP_LMT_PROFILE ON \n "
            + "		(SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
            + "		OR SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID) \n"
            // + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" +
            // ICMSConstant.AA_TYPE_BANK + "' \n"
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' \n"
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

    		
    protected static String SELECT_CUSTOMER_TRADE_PART_SELECT = "SELECT SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, "
            + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, " + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, "
            + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, " + "       SCI_LE_SUB_PROFILE.ULSP_SHORT_NAME, "
            + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, " + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, "
            + "       SCI_LE_SUB_PROFILE.LSP_ID, " + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, " + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM, "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_APPR_DATE, " + "       SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS, "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, " + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, "
            // + "       TRANSACTION.TRANSACTION_ID, "
            + "       SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE, " + "       SCI_LSP_LMT_PROFILE.CMS_CREATE_IND, "
            + "       CMS_TRADING_AGREEMENT.AGREEMENT_ID, " + "       CMS_TRADING_AGREEMENT.AGREEMENT_TYPE ";

    // + "       VW_CF_BL_IND.HAS_CONTRACT_FINANCE, "
    // + "       VW_CF_BL_IND.HAS_BRIDGING_LOAN "
    
    protected static String SELECT_CUSTOMER_TRADE_PART_SELECT_IMAGE_UPLOAD = "SELECT SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, "
            + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, " + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, "
            + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, " + "       SCI_LE_SUB_PROFILE.ULSP_SHORT_NAME, "
            + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, " + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, "
            + "       SCI_LE_SUB_PROFILE.LSP_ID, " + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, "
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, " + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM, "
            + "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_APPR_DATE, " + "       SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS, "
            + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, " + "       SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, "
            // + "       TRANSACTION.TRANSACTION_ID, "
            + "       SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE, " + "       SCI_LSP_LMT_PROFILE.CMS_CREATE_IND, "
            + "       CMS_TRADING_AGREEMENT.AGREEMENT_ID, " + "       CMS_TRADING_AGREEMENT.AGREEMENT_TYPE ";

    protected static String SELECT_CUSTOMER_TRADE_PART_FROMWHERE = "FROM SCI_LE_MAIN_PROFILE, "
            // + "     TRANSACTION, "
            + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  "
            + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
            + "       AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_TRADE + "' "
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' " + "     LEFT OUTER JOIN  "
            + "       CMS_TRADING_AGREEMENT ON "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = CMS_TRADING_AGREEMENT.CMS_LSP_LMT_PROFILE_ID "
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ";

    // +
    // "      AND TRANSACTION.REFERENCE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
    // + "      AND TRANSACTION.TRANSACTION_TYPE = '" +
    // ICMSConstant.INSTANCE_CUSTOMER + "' ";
    // + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' ";

    protected static String SELECT_CUSTOMER_JONIT_PART_FROMWHERE = "FROM SCI_LE_MAIN_PROFILE, \n"
            // + "     TRANSACTION, "
            + "     SCI_LE_SUB_PROFILE, \n"
            + "     SCI_LSP_JOINT_BORROWER LEFT OUTER JOIN  \n"
            + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
            + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '"
            + ICMSConstant.AA_TYPE_BANK
            + "' \n"
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
            + "      AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' \n"
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
            + "      AND SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n";

    // +
    // "      AND TRANSACTION.REFERENCE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
    // + "      AND TRANSACTION.TRANSACTION_TYPE = '" +
    // ICMSConstant.INSTANCE_CUSTOMER + "' ";
    // + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' ";

    protected static String SELECT_CUSTOMER_JONIT_TRADE_PART_FROMWHERE = "FROM SCI_LE_MAIN_PROFILE, "
            // + "     TRANSACTION, "
            + "     SCI_LE_SUB_PROFILE,"
            + "     SCI_LSP_JOINT_BORROWER LEFT OUTER JOIN  "
            + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID "
            + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_TRADE
            + "' "
            + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' "
            + "      AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' "
            + "     LEFT OUTER JOIN  "
            + "       CMS_TRADING_AGREEMENT ON "
            + "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = CMS_TRADING_AGREEMENT.CMS_LSP_LMT_PROFILE_ID "
            // + "     LEFT OUTER JOIN VW_CF_BL_IND "
            // +
            // "      ON VW_CF_BL_IND.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
            + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
            + "      AND SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ";

    // +
    // "      AND TRANSACTION.REFERENCE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
    // + "      AND TRANSACTION.TRANSACTION_TYPE = '" +
    // ICMSConstant.INSTANCE_CUSTOMER + "' ";
    // + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' ";

    protected static String CUSTOMER_SORT_ORDER = " ORDER BY ULSP_SHORT_NAME, LLP_BCA_REF_NUM";

    protected static String SELECT_CUSTOMER_BY_SCI_ID = "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID = ? AND LSP_ID = ?";
    /*
     * Query Commented by Sandeep Shinde on 18-03-2011
     * Reason : Table is Altered by adding new Column named- STATUS to make Customer status 'ACTIVE' 
				where new Customer is created and status 'INACTIVE' where it is deleted
     */
    /*protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE = "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE "
            + "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
            + "WHERE LSP_LE_ID = ? AND SOURCE_ID = ?";*/

    protected static String SELECT_CUSTOMER_BY_IDNO =
            "SELECT CMS_LE_SUB_PROFILE_ID, SOURCE_ID FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE " +
                    "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID " +
                    "WHERE LMP_INC_NUM_TEXT = ? " +
                    "ORDER BY SOURCE_ID, CMS_LE_SUB_PROFILE_ID";
    /*
     * Query Created by Sandeep Shinde on 18-03-2011
     * Reason : Table is Altered by adding new Column named- STATUS to make Customer status 'ACTIVE' 
				where new Customer is created and status 'INACTIVE' where it is deleted
     */
    
    protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE = "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE "
        + "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
        + "WHERE LSP_LE_ID = ?";
    
    protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME = "SELECT LSP_LE_ID,LSP_SHORT_NAME,CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE "
//    	+ "INNER JOIN SCI_LE_MAIN_PROFILE "
//        + "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
        + " WHERE STATUS = 'ACTIVE' ";
    
    protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE = "SELECT LSP_LE_ID,LSP_SHORT_NAME,CMS_LE_SUB_PROFILE_ID,STATUS FROM SCI_LE_SUB_PROFILE " 
//    	  + "INNER JOIN SCI_LE_MAIN_PROFILE "
//        + "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
        ;
      
    
    protected static String SELECT_CUSTOMER_BY_OTHER_SYS_ID = " SELECT SUB.LSP_LE_ID, SUB.LSP_SHORT_NAME, SUB.CMS_LE_SUB_PROFILE_ID,S.CMS_LE_MAIN_PROFILE_ID,SUB.STATUS"
    
   +" FROM SCI_LE_SUB_PROFILE SUB ,SCI_LE_MAIN_PROFILE  MAIN , SCI_LE_OTHER_SYSTEM  S"

     + " WHERE  SUB.CMS_LE_MAIN_PROFILE_ID = MAIN.CMS_LE_MAIN_PROFILE_ID AND "

    + " S.CMS_LE_MAIN_PROFILE_ID = MAIN.CMS_LE_MAIN_PROFILE_ID "
        + " AND SUB.STATUS = 'ACTIVE'";
    
     static String SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = "";
     
     protected static String SELECT_DUPLICATE_CUSTOMER_STAGE_QUERY = " SELECT SUB.LSP_LE_ID, SUB.LSP_SHORT_NAME, SUB.CMS_LE_SUB_PROFILE_ID"
    	    
    	   +" FROM STAGE_SCI_LE_SUB_PROFILE SUB , STAGE_SCI_LE_MAIN_PROFILE  MAIN "

    	     + " WHERE  SUB.CMS_LE_MAIN_PROFILE_ID = MAIN.CMS_LE_MAIN_PROFILE_ID "

    	        + " AND SUB.STATUS = 'ACTIVE'";
     
     protected static String SELECT_DUPLICATE_CUSTOMER_QUERY = " SELECT SUB.LSP_LE_ID, SUB.LSP_SHORT_NAME, SUB.CMS_LE_SUB_PROFILE_ID"
 	    
  	   +" FROM SCI_LE_SUB_PROFILE SUB , SCI_LE_MAIN_PROFILE  MAIN "

  	     + " WHERE  SUB.CMS_LE_MAIN_PROFILE_ID = MAIN.CMS_LE_MAIN_PROFILE_ID "

  	        + " AND SUB.STATUS = 'ACTIVE'";
    
    
    // Table names
    protected static final String LEGAL_ENTITY_TABLE = "SCI_LE_MAIN_PROFILE";

    protected static final String CUSTOMER_TABLE = "SCI_LE_SUB_PROFILE";

    protected static final String LIMIT_PROFILE_TABLE = "SCI_LSP_LMT_PROFILE";

    protected static final String TRANSACTION_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;

    protected static final String TRADE_AGREEMENT_TABLE = "CMS_TRADING_AGREEMENT";

    // Column values for legal entity table
    protected static final String LEGAL_NAME = "LMP_LONG_NAME";

    protected static final String LEGAL_ID = "CMS_LE_MAIN_PROFILE_ID";

    protected static final String LEGAL_REF = "LMP_LE_ID";

    protected static final String CUSTOMER_TYPE = "LMP_TYPE_VALUE";

    // Column values for customer table
    protected static final String CUSTOMER_NAME = "LSP_SHORT_NAME";

    protected static final String UCASE_CUSTOMER_NAME = "ULSP_SHORT_NAME";

    protected static final String CUSTOMER_ID = "CMS_LE_SUB_PROFILE_ID";

    protected static final String CUSTOMER_REF = "LSP_ID";

    protected static final String CUSTOMER_LE_ID = "CMS_LE_MAIN_PROFILE_ID";

    protected static final String CUSTOMER_LE_REF = "LSP_LE_ID";

    protected static final String CUSTOMER_COUNTRY = "CMS_SUB_ORIG_COUNTRY";

    protected static final String CUSTOMER_ORGANISATION = "CMS_SUB_ORIG_ORGANISATION";

    protected static final String SEGMENT_NAME = "LMP_SUB_SGMNT_CODE_VALUE";

    protected static final String COUNTRY_NAME = "LSP_DMCL_CNTRY_ISO_CODE";

    // Column values for limit profile table
    protected static final String BCA_ID = "CMS_LSP_LMT_PROFILE_ID";

    protected static final String BCA_REF = "LLP_BCA_REF_NUM";

    protected static final String BCA_APPROVED_DATE = "LLP_BCA_REF_APPR_DATE";

    protected static final String BCA_CUSTOMER_ID = "CMS_CUSTOMER_ID";

    protected static final String CMS_CUSTOMER_IND = "CMS_NON_BORROWER_IND";

    protected static final String CMS_BCA_STATUS = "CMS_BCA_STATUS";

    protected static final String BCA_ORIG_CNTRY = "CMS_ORIG_COUNTRY";

    protected static final String BCA_ORIG_ORG = "CMS_ORIG_ORGANISATION";

    protected static final String LMT_PROFILE_TYPE = "LMT_PROFILE_TYPE";

    protected static final String CMS_CREATE_IND = "CMS_CREATE_IND";

    protected static final String LIMIT_ID = "LIMIT_ID";

    // Column values for trading agreement table
    protected static final String AGREEMENT_ID = "AGREEMENT_ID";

    protected static final String AGREEMENT_TYPE = "AGREEMENT_TYPE";

    // Column values for trading agreement table
    protected static final String BRIDGING_LOAN_IND = "HAS_BRIDGING_LOAN";

    protected static final String CONTRACT_FINANCE_IND = "HAS_CONTRACT_FINANCE";

    // Column values for transaction table
    protected static final String TRX_ID = ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID;

    protected static final String TRX_TYPE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE;

    protected static final String TRX_REF_ID = ICMSTrxTableConstants.TRXTBL_REFERENCE_ID;

    protected static final String TRX_CUSTOMER_VALUE = ICMSConstant.INSTANCE_CUSTOMER;

	private static final String SELECT_CUST_WITHOUT_TRX = "select mp.LMP_LONG_NAME, mp.CMS_LE_MAIN_PROFILE_ID, mp.LMP_LE_ID, mp.LMP_TYPE_VALUE, sp.LSP_SHORT_NAME, sp.CMS_LE_SUB_PROFILE_ID, "
			+ "sp.LSP_ID, lp.CMS_LSP_LMT_PROFILE_ID, lp.LLP_BCA_REF_NUM, lp.LLP_BCA_REF_APPR_DATE, lp.CMS_ORIG_COUNTRY, sp.CMS_NON_BORROWER_IND, lp.CMS_BCA_STATUS "
			+ "from SCI_LE_MAIN_PROFILE mp, SCI_LE_SUB_PROFILE sp, SCI_LSP_LMT_PROFILE lp "
			+ "where mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID and sp.CMS_LE_SUB_PROFILE_ID = lp.CMS_CUSTOMER_ID and lp.CMS_LSP_LMT_PROFILE_ID = ?";

    private static final String SELECT_CUST_MAIL_DETAILS_STATEMENT = "SELECT lsp.lsp_le_id, lsp.lsp_id, lsp.lsp_short_name, lsp.lsp_id,"
            + "  addr.loa_addr_line_1, addr.loa_addr_line_2, addr.loa_city_text,"
            + "  addr.loa_state, addr.loa_post_code, addr.loa_cntry_iso_code "
            + "FROM sci_le_sub_profile lsp,"
            + "  sci_lsp_off_addr addr, "
            + "(SELECT DISTINCT llp.llp_le_id le_id, llp.llp_lsp_id lsp_id"
            + "   FROM sci_lsp_lmt_profile llp" + "  WHERE (llp.llp_le_id, llp.llp_lsp_id)";

    private static final String SELECT_CUST_MAIL_DETAILS_CONDITION = "    AND (llp.update_status_ind IS NULL OR llp.update_status_ind != 'D' )) req_customer "
            + "WHERE lsp.lsp_le_id = req_customer.le_id "
            + "AND lsp.lsp_id = req_customer.lsp_id "
            + "AND addr.cms_main_profile_id = lsp.cms_le_sub_profile_id ";

    private static final String SELECT_COUNT_CO_BORROWER_LIMIT = "SELECT COUNT(*) FROM SCI_LSP_CO_BORROW_LMT "
            + "WHERE (UPDATE_STATUS_IND <> 'D' OR UPDATE_STATUS_IND IS NULL) " + "AND CMS_CUSTOMER_ID = ?";

    private static final String SELECT_MB_INFO_LIMIT_LIST = "SELECT DISTINCT pf.LLP_LE_ID, pf.CMS_ORIG_COUNTRY, pf.CMS_ORIG_ORGANISATION, sub.LSP_SHORT_NAME "
            + "FROM SCI_LSP_LMT_PROFILE pf, SCI_LSP_APPR_LMTS lmts, SCI_LE_SUB_PROFILE sub "
            + "WHERE lmts.cms_limit_profile_id = pf.cms_lsp_lmt_profile_id "
            + "AND pf.cms_customer_id = sub.CMS_LE_SUB_PROFILE_ID ";

    private static final String SELECT_CB_INFO_LIMIT_LIST = "SELECT DISTINCT lsp.LSP_LE_ID, lsp.CMS_SUB_ORIG_COUNTRY, lsp.CMS_SUB_ORIG_ORGANISATION,lsp.LSP_SHORT_NAME "
            + "FROM SCI_LE_SUB_PROFILE lsp,SCI_LSP_CO_BORROW_LMT clmts "
            + "WHERE clmts.CMS_CUSTOMER_ID = lsp.CMS_LE_SUB_PROFILE_ID " + "AND CMS_LSP_CO_BORROW_LMT_ID ";

    private static final String SELECT_MB_BY_CB_LE_ID = " SELECT  " + " SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
            + " LMP_LONG_NAME, \n" + " CMS_ORIG_COUNTRY,\n" + " CAM_TYPE,\n" + " CMS_ORIG_ORGANISATION, \n" + " LLP_BCA_REF_NUM,\n"
            + " LLP_BCA_REF_APPR_DATE\n" + "  FROM " + " SCI_LSP_LMT_PROFILE,\n" + " SCI_LE_SUB_PROFILE,\n"
            + " SCI_LE_MAIN_PROFILE\n" + " WHERE \n"
            + " SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID=SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID\n"
            + " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID=SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID\n"
            + " AND   SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID\n" + "       IN (\n" + "           SELECT "
            + "            DISTINCT  SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID\n" + "           FROM \n"
            + "            SCI_LSP_CO_BORROW_LMT,\n" + "            SCI_LSP_APPR_LMTS\n" + "           WHERE \n"
            + "            SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID=SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID\n"
            + "     AND    SCI_LSP_CO_BORROW_LMT.CMS_CUSTOMER_ID=? \n" + "          )";

    private static final String getMBByCBSql = " SELECT  " +
            " SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
            " LMP_LONG_NAME, \n" +
            " CMS_ORIG_COUNTRY,\n" +
            " CMS_ORIG_ORGANISATION, \n" +
            " LLP_BCA_REF_NUM,\n" +
            " LLP_BCA_REF_APPR_DATE\n" +
            "  FROM " +
            " SCI_LSP_LMT_PROFILE,\n" +
            " SCI_LE_SUB_PROFILE,\n" +
            " SCI_LE_MAIN_PROFILE\n" +
            " WHERE \n" +
            " SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID=SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID\n" +
            " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID=SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID\n" +
            " AND   SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID\n" +
            "       IN (\n" +
            "           SELECT " +
            "            DISTINCT  SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID\n" +
            "           FROM \n" +
            "            SCI_LSP_CO_BORROW_LMT,\n" +
            "            SCI_LSP_APPR_LMTS\n" +
            "           WHERE \n" +
            "            SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID=SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID\n" +
            "     AND    SCI_LSP_CO_BORROW_LMT.CMS_CUSTOMER_ID=? \n" +
            "          )";

    protected static String SELECT_CUSTOMER_TRADE_SELECT =
            "SELECT SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, "
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, "
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, "
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, "
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, "
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, "
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, "
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, "
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, "
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, "
//    	+ "       SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, "
//   	+ "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM, "
//    	+ "       SCI_LSP_LMT_PROFILE.LLP_BCA_REF_APPR_DATE, "
//    	+ "       SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS, "
//    	+ "       SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, "
//    	+ "       SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, "
//    	+ "       SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE, "
//    	+ "       SCI_LSP_LMT_PROFILE.CMS_CREATE_IND, "
                    + "       CMS_TRADING_AGREEMENT.AGREEMENT_ID, "
                    + "       CMS_TRADING_AGREEMENT.AGREEMENT_TYPE ";

    private static final String KEY_DELIMITER = ";";

    /**
     * Default Constructor
     */
    public CustomerDAO() {
    }

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @param leid         Cif Id
     * @param subProfileID Sub Profile Id
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long searchCustomerID(long leid, long subProfileID) throws SearchDAOException {

        Long customerId = (Long) getJdbcTemplate().query(SELECT_CUSTOMER_BY_SCI_ID,
                new Object[]{new Long(leid), new Long(subProfileID)}, new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Long customerId = new Long(0);
                        if (rs.next()) {
                            customerId = new Long(rs.getLong(1));
                        }

                        return customerId;
                    }

                });

        if (customerId.longValue() == 0) {
            throw new SearchDAOException("Unable to find customer ID given SCI LEID: " + leid
                    + " and SCI SubProfileID: " + subProfileID);
        }

        return customerId.longValue();
    }

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI Source Id
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long searchCustomerByCIFNumber(String cifNumber, String sourceSystemId) throws SearchDAOException {

        Long customerId = (Long) getJdbcTemplate().query(SELECT_CUSTOMER_BY_CIFNO_SOURCE,
                new Object[]{cifNumber}, new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Long customerId = new Long(ICMSConstant.LONG_INVALID_VALUE);
                        if (rs.next()) {
                            customerId = new Long(rs.getLong(1));
                        }

                        return customerId;
                    }

                });

        return customerId.longValue();
    }

    
    public List searchCustomerByCIFNumber(String systemName, String sourceSystemId, String partyName, String partyId) throws SearchDAOException {
         String query="";
    	 if(partyName != null && !partyName.trim().equals("")){
    		// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + " AND UPPER(LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"%' ";
    	 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + " WHERE UPPER(LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"%' ";
    	 }
    	 
    	 
    	 else if((sourceSystemId != null && !sourceSystemId.trim().equals("")) &&((systemName != null && !systemName.trim().equals("")))){
    		// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_OTHER_SYS_CUST_ID = '"+sourceSystemId+"%' ";
    		 query = SELECT_CUSTOMER_BY_OTHER_SYS_ID + "  AND S.CMS_LE_OTHER_SYS_CUST_ID like '"+sourceSystemId+"%'" +"  AND S.CMS_LE_SYSTEM_NAME = '"+ systemName+"'";
    	 }else if((partyId != null && !partyId.trim().equals(""))){
    		// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_OTHER_SYS_CUST_ID = '"+sourceSystemId+"%' ";
    		 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + "  WHERE LSP_LE_ID like '"+partyId.toUpperCase()+"%'";
    	 }
    	 else{
    		 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE;
    		 
    	 }
    	
    	 List resultList = getJdbcTemplate().query(query, new RowMapper() {

             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 
            	 OBCMSCustomer result = new OBCMSCustomer();
     			
     			result.setCifId(rs
     					.getString("LSP_LE_ID"));
     			result.setCustomerName(rs
     					.getString("LSP_SHORT_NAME"));
     			result.setCustomerID(rs
     					.getLong("CMS_LE_SUB_PROFILE_ID"));
     			result.setStatus(rs
     					.getString("STATUS"));
     			
                /* String[] stringArray = new String[3];
                 stringArray[0] = rs.getString("LSP_LE_ID");
                 stringArray[1] = rs.getString("LSP_SHORT_NAME");
                 stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");*/

                 return result;
             }
         }
         );

         return resultList;
         
      
    }
    /*public List searchCustomerByCIFNumber(String cifId) throws SearchDAOException {
        String query="";
   	 if(cifId != null && !cifId.trim().equals("")){
   		// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"%' ";
   	 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_LE_ID) like '"+cifId.toUpperCase()+"%' ";
   	 }
   	 else{
   		 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME;
   		 
   	 }
   	
   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	 
           	 OBCMSCustomer result = new OBCMSCustomer();
    			
    			result.setCifId(rs
    					.getString("LSP_LE_ID"));
    			result.setCustomerName(rs
    					.getString("LSP_SHORT_NAME"));
    			result.setCustomerID(rs
    					.getLong("CMS_LE_SUB_PROFILE_ID"));
    			
                String[] stringArray = new String[3];
                stringArray[0] = rs.getString("LSP_LE_ID");
                stringArray[1] = rs.getString("LSP_SHORT_NAME");
                stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");

                return result;
            }
        }
        );

        return resultList;
        
     
   }*/
    
    /*public List getAllSystemAndSystemId() throws SearchDAOException {
        String query="";
   	
   	 query = "SELECT CMS_LE_SYSTEM_NAME, CMS_LE_OTHER_SYS_CUST_ID  FROM SCI_LE_OTHER_SYSTEM SYS, SCI_LE_SUB_PROFILE SP"
        +" where SYS.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE' ";
   
   	
   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	 
           	 OBSystem result = new OBSystem();
    			
    			result.setSystem(rs
    					.getString("CMS_LE_SYSTEM_NAME"));
    			result.setSystemCustomerId(rs
    					.getString("CMS_LE_OTHER_SYS_CUST_ID"));
    			
    			
                String[] stringArray = new String[3];
                stringArray[0] = rs.getString("LSP_LE_ID");
                stringArray[1] = rs.getString("LSP_SHORT_NAME");
                stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");

                return result;
            }
        }
        );

        return resultList;
        
     
   }*/
    public List getvendorList(String custId) throws SearchDAOException {
        String query="";
   	 query = "SELECT CMS_LE_VENDOR_NAME  FROM SCI_LE_VENDOR_DETAILS "
   	 		+ "where CMS_LE_MAIN_PROFILE_ID = (select  CMS_LE_MAIN_PROFILE_ID "
   	        + " from SCI_LE_SUB_PROFILE WHERE LSP_LE_ID = '"+custId+"' )";
   	 
   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	 OBVendor result = new OBVendor();
    			result.setVendorName(rs
    					.getString("CMS_LE_VENDOR_NAME"));
                return result;
            }
        }
        );
   	 return resultList;
		
	 
   }
    
    
    
    /*public List searchCustomerByCustomerId(String custId) throws SearchDAOException {
        String query="";
   	 if(custId != null && !custId.trim().equals("")){
   		// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"%' ";
   	 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_SUB_PROFILE_ID like '"+custId+"%' ";
   	 }else {
   		query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_SUB_PROFILE_ID like '"+custId+"' ";
   	 }
   	 
   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {

						return customerId;
					}


				});

		if (customerId.longValue() == 0) {
			throw new SearchDAOException(
					"Unable to find customer ID given SCI LEID: " + leid + " and SCI SubProfileID: " + subProfileID);
		}

		return customerId.longValue();
	}*/

	/**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI Source Id
	 *
	 * @return long
	 * @throws SearchDAOException
	 *             if no records found
	 * @throws CustomerException
	 *             on errors
	 */
	/*public long searchCustomerByCIFNumber(String cifNumber, String sourceSystemId) throws SearchDAOException {

		Long customerId = (Long) getJdbcTemplate().query(SELECT_CUSTOMER_BY_CIFNO_SOURCE, new Object[] { cifNumber },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Long customerId = new Long(ICMSConstant.LONG_INVALID_VALUE);
						if (rs.next()) {
							customerId = new Long(rs.getLong(1));
						}

						return customerId;
					}

				});

		return customerId.longValue();
	}*/

	/*public List searchCustomerByCIFNumber(String systemName, String sourceSystemId, String partyName, String partyId)
			throws SearchDAOException {
		String query = "";
		if (partyName != null && !partyName.trim().equals("")) {
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT =
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + " AND UPPER(LSP_SHORT_NAME) like
			// '"+partyName.toUpperCase()+"%' ";
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + " WHERE UPPER(LSP_SHORT_NAME) like '"
					+ partyName.toUpperCase() + "%' ";
		}

		else if ((sourceSystemId != null && !sourceSystemId.trim().equals(""))
				&& ((systemName != null && !systemName.trim().equals("")))) {
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT =
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_OTHER_SYS_CUST_ID =
			// '"+sourceSystemId+"%' ";
			query = SELECT_CUSTOMER_BY_OTHER_SYS_ID + "  AND S.CMS_LE_OTHER_SYS_CUST_ID like '" + sourceSystemId + "%'"
					+ "  AND S.CMS_LE_SYSTEM_NAME = '" + systemName + "'";
		} else if ((partyId != null && !partyId.trim().equals(""))) {
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT =
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_OTHER_SYS_CUST_ID =
			// '"+sourceSystemId+"%' ";
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE + "  WHERE LSP_LE_ID like '" + partyId.toUpperCase() + "%'";
		} else {
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_CLOSE;

		}

		List resultList = getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				OBCMSCustomer result = new OBCMSCustomer();

				result.setCifId(rs.getString("LSP_LE_ID"));
				result.setCustomerName(rs.getString("LSP_SHORT_NAME"));
				result.setCustomerID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));
				result.setStatus(rs.getString("STATUS"));

				
				 * String[] stringArray = new String[3]; stringArray[0] =
				 * rs.getString("LSP_LE_ID"); stringArray[1] = rs.getString("LSP_SHORT_NAME");
				 * stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");
				 

				return result;
			}
		});

		return resultList;

	}*/

	public List searchCustomerByCIFNumber(String cifId) throws SearchDAOException {
		String query = "";
		if (cifId != null && !cifId.trim().equals("")) {
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT =
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_SHORT_NAME) like
			// '"+partyName.toUpperCase()+"%' ";
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_LE_ID) like '" + cifId.toUpperCase()
					+ "%' ";
		} else {
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME;

		}

		List resultList = getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				OBCMSCustomer result = new OBCMSCustomer();

				result.setCifId(rs.getString("LSP_LE_ID"));
				result.setCustomerName(rs.getString("LSP_SHORT_NAME"));
				result.setCustomerID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));

				/*
				 * String[] stringArray = new String[3]; stringArray[0] =
				 * rs.getString("LSP_LE_ID"); stringArray[1] = rs.getString("LSP_SHORT_NAME");
				 * stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");
				 */

				return result;
			}
		});

		return resultList;

	}

	public List getAllSystemAndSystemId() throws SearchDAOException {
		String query = "";

		query = "SELECT CMS_LE_SYSTEM_NAME, CMS_LE_OTHER_SYS_CUST_ID  FROM SCI_LE_OTHER_SYSTEM SYS, SCI_LE_SUB_PROFILE SP"
				+ " where SYS.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE' ";

		List resultList = getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				OBSystem result = new OBSystem();

				result.setSystem(rs.getString("CMS_LE_SYSTEM_NAME"));
				result.setSystemCustomerId(rs.getString("CMS_LE_OTHER_SYS_CUST_ID"));

				/*
				 * String[] stringArray = new String[3]; stringArray[0] =
				 * rs.getString("LSP_LE_ID"); stringArray[1] = rs.getString("LSP_SHORT_NAME");
				 * stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");
				 */

				return result;
			}
		});

		return resultList;

	}

	
	public List searchCustomerByCustomerId(String custId) throws SearchDAOException {
		String query = "";
		if (custId != null && !custId.trim().equals("")) {
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_RESULT =
			// SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND UPPER(LSP_SHORT_NAME) like
			// '"+partyName.toUpperCase()+"%' ";
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_SUB_PROFILE_ID like '" + custId + "%' ";
		} else {
			query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME + " AND CMS_LE_SUB_PROFILE_ID like '" + custId + "' ";
		}

		List resultList = getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				OBCMSCustomer result = new OBCMSCustomer();

				result.setCifId(rs.getString("LSP_LE_ID"));
				result.setCustomerName(rs.getString("LSP_SHORT_NAME"));
				result.setCustomerID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));

				/*
				 * String[] stringArray = new String[3]; stringArray[0] =
				 * rs.getString("LSP_LE_ID"); stringArray[1] = rs.getString("LSP_SHORT_NAME");
				 * stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");
				 */

				return result;
			}
		});

        return resultList;
        
     
   }
    
    
    public boolean isCustomerNameUnique(String partyName){
		String query = SELECT_DUPLICATE_CUSTOMER_STAGE_QUERY +" and UPPER(SUB.LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"' ";
		//ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		
		 List resultList = getJdbcTemplate().query(query, new RowMapper() {

             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 
            	 OBCMSCustomer result = new OBCMSCustomer();
     			
     			result.setCifId(rs
     					.getString("LSP_LE_ID"));
     			result.setCustomerName(rs
     					.getString("LSP_SHORT_NAME"));
     			result.setCustomerID(rs
     					.getLong("CMS_LE_SUB_PROFILE_ID"));

                 return result;
             }
         }
		 );
		 
		if(resultList.size()>0){
			String query1 = SELECT_DUPLICATE_CUSTOMER_QUERY +" and SUB.LSP_SHORT_NAME like '"+partyName+"' ";
			 List resultList1 = getJdbcTemplate().query(query, new RowMapper() {

	             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	 
	            	 OBCMSCustomer result = new OBCMSCustomer();
	     			
	     			result.setCifId(rs
	     					.getString("LSP_LE_ID"));
	     			result.setCustomerName(rs
	     					.getString("LSP_SHORT_NAME"));
	     			result.setCustomerID(rs
	     					.getLong("CMS_LE_SUB_PROFILE_ID"));
	     			
	                 return result;
	             }
	         }
			 );
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;

	}
    
    /**
     * Get list of mailing details for a list of limit profile IDs. One official
     * address per limit profile ID if available.
     *
     * @param securityLimitMapList - List of ICollateralLimitMap
     * @return HashMap - (ICollateralLimitMap, OBCustomerMailingDetails)
     * @throws SearchDAOException if errors
     */
    public Map getCustomerMailingDetails(List securityLimitMapList) throws SearchDAOException {
        logger.info("IN method getCustomerMailingDetails");

		if ((securityLimitMapList == null) || (securityLimitMapList.size() == 0)) {
			return null;
		}

		// construct list of le_id and list of lsp_id
		final Map customerLimitSecLinkMap = new HashMap();
		List leIdList = new ArrayList();
		List lspIdList = new ArrayList();
		populateCifIdAndLspIdList(securityLimitMapList, leIdList, lspIdList, customerLimitSecLinkMap);

		if ((leIdList.size() == 0) || (lspIdList.size() == 0)) {
			return null;
		}

		StringBuffer sqlBuf = new StringBuffer(SELECT_CUST_MAIL_DETAILS_STATEMENT);
		ArrayList params = new ArrayList();
		CommonUtil.buildSQLInList(leIdList, lspIdList, sqlBuf, params);
		sqlBuf.append(SELECT_CUST_MAIL_DETAILS_CONDITION);

		Map resultMap = (Map) getJdbcTemplate().query(sqlBuf.toString(), params.toArray(), new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map result = new HashMap();
				while (rs.next()) {

					String leId = rs.getString("LSP_LE_ID");
					String lspId = rs.getString("LSP_ID");
					String mapKey = getLeLspKey(leId, lspId);

					if (!result.containsKey(mapKey)) {
						OBCustomerMailingDetails mailDetails = new OBCustomerMailingDetails();
						mailDetails.setCustomerLEID(leId);
						mailDetails.setCustomerSubProfileID(lspId);
						mailDetails.setCustomerName(rs.getString("LSP_SHORT_NAME"));
						mailDetails.setAddressLine1(rs.getString("LOA_ADDR_LINE_1"));
						mailDetails.setAddressLine2(rs.getString("LOA_ADDR_LINE_2"));
						mailDetails.setCity(rs.getString("LOA_CITY_TEXT"));
						mailDetails.setState(rs.getString("LOA_STATE"));
						mailDetails.setPostalCode(rs.getString("LOA_POST_CODE"));
						mailDetails.setCountryCode(rs.getString("LOA_CNTRY_ISO_CODE"));
						result.put(customerLimitSecLinkMap.get(mapKey), mailDetails);
					}
				}

				return result;
			}

		});
		return resultMap;

	}

	public Map getFamcodeCustNameByCustomer(List securityLimitMapList) throws SearchDAOException {
		if ((securityLimitMapList == null) || (securityLimitMapList.size() == 0)) {
			return Collections.EMPTY_MAP;
		}

		HashMap customerSecLimitLinkMap = new HashMap();
		ArrayList leIdList = new ArrayList();
		ArrayList lspIdList = new ArrayList();
		populateCifIdAndLspIdList(securityLimitMapList, leIdList, lspIdList, customerSecLimitLinkMap);
		if ((leIdList.size() == 0) || (lspIdList.size() == 0)) {
			return Collections.EMPTY_MAP;
		}

		String SELECT_FAM = "SELECT CUST_LOC_FAM.FAM_CODE, SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, SCI_LE_SUB_PROFILE.LSP_LE_ID, "
				+ "SCI_LE_SUB_PROFILE.LSP_ID FROM SCI_LE_SUB_PROFILE LEFT OUTER JOIN CUST_LOC_FAM "
				+ " ON CUST_LOC_FAM.CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID "
				+ "WHERE (SCI_LE_SUB_PROFILE.LSP_LE_ID, SCI_LE_SUB_PROFILE.LSP_ID)";

		StringBuffer sqlBuf = new StringBuffer(SELECT_FAM);
		List params = new ArrayList();
		CommonUtil.buildSQLInList(leIdList, lspIdList, sqlBuf, params);
		sqlBuf.append(" ORDER BY CUST_LOC_FAM.FAM_CODE");

		Map resultMap = (Map) getJdbcTemplate().query(sqlBuf.toString(), params.toArray(), new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map result = new HashMap();

				while (rs.next()) {
					String leId = rs.getString("LSP_LE_ID");
					String lspId = rs.getString("LSP_ID");
					String mapKey = getLeLspKey(leId, lspId);
					String customerName = rs.getString("LSP_SHORT_NAME");
					String famCode = rs.getString("FAM_CODE");
					if (famCode == null) {
						famCode = "";
					}
					if (!result.containsKey(mapKey)) {
						String[] resultArr = new String[2];
						resultArr[0] = customerName;
						resultArr[1] = famCode;
						result.put(mapKey, resultArr);
					}
				}
				return result;
			}
		});

		return resultMap;

	}

    /**
     * Helper method to populate the le_id_list and the lsp_id_list based on the
     * securityLimitMapList. Put security limit map into
     * customer_secLimitMap_map with le_id & lsp_id as key.
     *
     * @param securityLimitMapList    securityLimitMapList
     * @param leIdList                - List to populate with le_id
     * @param lspIdList               - List to populate with lsp_id
     * @param customerLimitSecLinkMap - (le_id+lsp_id, ICollateralLimitMap)
     */
    private void populateCifIdAndLspIdList(List securityLimitMapList, List leIdList, List lspIdList,
                                           Map customerLimitSecLinkMap) {
        Iterator mapItr = securityLimitMapList.iterator();
        while (mapItr.hasNext()) {
            ICollateralLimitMap map = (ICollateralLimitMap) mapItr.next();
            String leId = map.getSCILegalEntityID();
            long lspId = map.getSCISubProfileID();

			if ((leId != null) && (leId.length() > 0) && (lspId != ICMSConstant.LONG_MIN_VALUE)) {
				leIdList.add(leId);
				lspIdList.add(new Long(lspId));
				customerLimitSecLinkMap.put(getLeLspKey(leId, Long.toString(lspId)), map);
			}
		}
	}

	/**
	 * Helper method to construct the le_id + lsp_id key
	 *
	 * @param leIdString
	 * @param lspIdString
	 * @return String
	 */
	private String getLeLspKey(String leIdString, String lspIdString) {
		return new StringBuffer().append(leIdString).append(KEY_DELIMITER).append(lspIdString).toString();
	}

    /**
     * Helper method to search outer limit customer.
     *
     * @param criteria customer search criteria
     * @return a list of ICustomerSearchResult contained in SearchResult
     * @throws SearchDAOException on error search the customer
     */
    private SearchResult searchOuterLimitCustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
        ILimit[] lmts = criteria.getLimits();
        int size = lmts.length;
        ArrayList list = new ArrayList();
        HashMap map = new HashMap();
        ArrayList allowedCustomers = new ArrayList();

		for (int i = 0; i < size; i++) {
			ICustomerSearchResult cust = new OBCustomerSearchResult();
			list.add(cust);
			cust.setInnerLimitID(lmts[i].getLimitID());
			if (lmts[i].getOuterLimitID() == ICMSConstant.LONG_INVALID_VALUE) {
				continue;
			}
			cust.setOuterLimitID(lmts[i].getOuterLimitID());

			Long innerLmt = new Long(cust.getInnerLimitID());

			if (map.containsKey(innerLmt)) {
				cust = (ICustomerSearchResult) map.get(innerLmt);
			} else {
				map.put(innerLmt, cust);
				if (lmts[i].getIsInnerOuterSameBCA()) {
					continue;
				}
				setCustomerInfo(SELECT_CUST_WITHOUT_TRX, cust, lmts[i].getOuterLimitProfileID(), allowedCustomers);
			}
		}

		return new SearchResult(0, size, size, list);
	}

    /**
     * Helper method to set customer details for the given limit profile id.
     *
     * @param cust           of type ICustomerSearchResult
     * @param limitProfileID cms limit profile id
     * @param allowedList    a list of permitted customer
     * @throws SearchDAOException on error setting the customer info
     */
    private void setCustomerInfo(String sql, final ICustomerSearchResult cust, final long limitProfileID,
                                 final ArrayList allowedList) throws SearchDAOException {
        final int allowedSize = allowedList.size();

		getJdbcTemplate().query(sql, new Object[] { new Long(limitProfileID) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					cust.setLegalReference(rs.getString(LEGAL_REF));
					cust.setInstructionRefNo(rs.getString(BCA_REF));
					cust.setOrigLocCntry(rs.getString(BCA_ORIG_CNTRY));
					cust.setLegalName(rs.getString(LEGAL_NAME));
					cust.setCustomerName(rs.getString(CUSTOMER_NAME));
					long custID = rs.getLong(CUSTOMER_ID);

					boolean custFound = false;
					for (int i = 0; i < allowedSize; i++) {
						long allowedID = ((Long) allowedList.get(i)).longValue();
						if (custID == allowedID) {
							custFound = true;
							ILimitProfile lp = new OBLimitProfile();
							lp.setLimitProfileID(limitProfileID);
							cust.setLimitProfile(lp);
							break;
						}
					}

					if (!custFound) {
						cust.setIsDAPError(true);
					}
				}
				return null;
			}
		});
	}

	public ArrayList getMainBorrowerListByCoBorrowerLeId(long cbLeId) throws SearchDAOException {

		List resultList = (List) getJdbcTemplate().query(SELECT_MB_BY_CB_LE_ID, new Object[] { new Long(cbLeId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						OBCustomerSearchResult customerSearchResult = new OBCustomerSearchResult();
						customerSearchResult.setLegalReference(rs.getString(LEGAL_REF));
						customerSearchResult.setLegalName(rs.getString(LEGAL_NAME));

						OBBookingLocation bkg = new OBBookingLocation();
						bkg.setCountryCode(rs.getString(BCA_ORIG_CNTRY));
						bkg.setOrganisationCode(rs.getString(BCA_ORIG_ORG));
						customerSearchResult.setBcaOrigLocation(bkg);
						customerSearchResult.setCamType(rs.getString("CAM_TYPE")); // A Shiv 190911
						customerSearchResult.setInstructionRefNo(rs.getString(BCA_REF));

						Timestamp ts = rs.getTimestamp(BCA_APPROVED_DATE, DateUtil.getCalendar());
						if (null != ts) {
							customerSearchResult.setInstructionApprovedDate(new java.util.Date(ts.getTime()));
						}

						return customerSearchResult;
					}

				});

		return new ArrayList(resultList);

	}

    /**
     * Search for a list of customer documents based on the criteria
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult - the object that contains a list of
     *         ICustomerSearchResult objects or null if no records are found.
     * @throws SearchDAOException if errors
     */
    public SearchResult searchCustomer(final CustomerSearchCriteria criteria) throws SearchDAOException {
        if (criteria == null) {
            throw new IllegalArgumentException("'criteria' supplied must not be null.");
        }

		if (criteria.getLimits() != null) {
			return searchOuterLimitCustomer(criteria);
		}

		SQLParameter params = SQLParameter.getInstance();

		try {
			long processSearchTime = System.currentTimeMillis();
			String sqlQuery = getSearchCustomerSQL(criteria, params);

			SearchResult searchResult = (SearchResult) getJdbcTemplate().query(sqlQuery, params.asList().toArray(),
					new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							return SearchResultSetProcessUtils.processResultSet(criteria, rs,
									new SearchResultSetProcessUtils.ResultSetProcessAction() {

										public Object doInResultSet(ResultSet rs) throws SQLException {
											return processResultSet(rs);
										}
									});
						}

					});

			logger.debug(">>>>>>>> searchCustomer - execute sql in  : ["
					+ (System.currentTimeMillis() - processSearchTime) + "] ms");
			System.out.println("CustomerDao.java=> SearchResult searchCustomer => sqlQuery=>" + sqlQuery);
			return searchResult;

        }
        catch (DAPFilterException e) {
        	System.out.println("Exception=>"+e);
            logger.warn("The Team in context does not have country and/or organisation defined. "
                    + "No results will be returned.");
            return null;
        }
    }
    
    
    public SearchResult searchCustomerImageUpload(final CustomerSearchCriteria criteria) throws SearchDAOException {
        if (criteria == null) {
            throw new IllegalArgumentException("'criteria' supplied must not be null.");
        }

		if (criteria.getLimits() != null) {
			return searchOuterLimitCustomer(criteria);
		}

		SQLParameter params = SQLParameter.getInstance();

		try {
			long processSearchTime = System.currentTimeMillis();
			String sqlQuery = getSearchCustomerImageUploadSQL(criteria, params);

			SearchResult searchResult = (SearchResult) getJdbcTemplate().query(sqlQuery, params.asList().toArray(),
					new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							return SearchResultSetProcessUtils.processResultSet(criteria, rs,
									new SearchResultSetProcessUtils.ResultSetProcessAction() {

										public Object doInResultSet(ResultSet rs) throws SQLException {
											return processResultSet(rs);
										}
									});
						}

					});

			logger.debug(">>>>>>>> searchCustomerImageUpload - execute sql in  : ["
					+ (System.currentTimeMillis() - processSearchTime) + "] ms");

			return searchResult;

        }
        catch (DAPFilterException e) {
            logger.warn("The Team in context does not have country and/or organisation defined. "
                    + "No results will be returned.");
            return null;
        }
    }

    /**
     * Get a list of customers info by list if limit ID
     *
     * @param limitIDList - Collection
     * @return Collection - list of customers info based of limit ID given
     * @throws SearchDAOException if errors
     */
    public Collection getMBInfoByLimitIDList(Collection limitIDList) throws SearchDAOException {
        StringBuffer sqlBuf = new StringBuffer(SELECT_MB_INFO_LIMIT_LIST);
        sqlBuf.append("AND lmts.CMS_LSP_APPR_LMTS_ID ");

		List params = new ArrayList();
		CommonUtil.buildSQLInList((String[]) limitIDList.toArray(new String[0]), sqlBuf, params);

		List resultList = getJdbcTemplate().query(sqlBuf.toString(), params.toArray(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCustomerSearchResult customerSearchResult = new OBCustomerSearchResult();
				customerSearchResult.setLegalName(rs.getString(CUSTOMER_NAME));
				customerSearchResult.setLegalReference(rs.getString("LLP_LE_ID"));

				OBBookingLocation bkg = new OBBookingLocation();
				bkg.setCountryCode(rs.getString(BCA_ORIG_CNTRY));
				bkg.setOrganisationCode(rs.getString(BCA_ORIG_ORG));
				customerSearchResult.setInstrOrigLocation(bkg);

				return customerSearchResult;
			}
		});

		return resultList;
	}

	public Collection getCBInfoByLimitIDList(Collection limitIDList) throws SearchDAOException {
		StringBuffer sqlBuf = new StringBuffer(SELECT_CB_INFO_LIMIT_LIST);
		List params = new ArrayList();
		CommonUtil.buildSQLInList((String[]) limitIDList.toArray(new String[0]), sqlBuf, params);

		List resultList = getJdbcTemplate().query(sqlBuf.toString(), params.toArray(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCustomerSearchResult customerSearchResult = new OBCustomerSearchResult();
				customerSearchResult.setLegalName(rs.getString(CUSTOMER_NAME));
				customerSearchResult.setLegalReference(rs.getString("LSP_LE_ID"));

				OBBookingLocation bkg = new OBBookingLocation();
				bkg.setCountryCode(rs.getString("CMS_SUB_ORIG_COUNTRY"));
				bkg.setOrganisationCode(rs.getString("CMS_SUB_ORIG_ORGANISATION"));
				customerSearchResult.setInstrOrigLocation(bkg);

				return customerSearchResult;
			}
		});

		return resultList;
	}

	public List getJointBorrowerList(long limitProfileId) throws Exception {

        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select customer.LMT_PROFILE_ID, lsp.LSP_LE_ID, lsp.CMS_LE_SUB_PROFILE_ID, ");
        queryBuffer.append(" lsp.LSP_SHORT_NAME, lmp.LMP_ID_NUMBER ");
        queryBuffer.append(" from");
        queryBuffer.append(" (select CMS_LSP_LMT_PROFILE_ID as LMT_PROFILE_ID, LLP_BCA_REF_NUM as BCA_REF_NUM, ");
        queryBuffer.append(" LLP_LE_ID as LE_ID, to_char(LLP_LSP_ID) as LSP_ID, '1' as CATEGORY from sci_lsp_lmt_profile ");
        queryBuffer.append(" UNION");
        queryBuffer.append(" select CMS_LMP_LIMIT_PROFILE_ID as LMT_PROFILE_ID, LJB_BCA_REF_NUM as BCA_REF_NUM, ");
        //For DB2
        //queryBuffer.append(" LJB_LE_ID as LE_ID, BIGINT(LJB_LSP_ID) as LSP_ID, '2' as CATEGORY from SCI_LSP_JOINT_BORROWER where UPDATE_STATUS_IND <>'D') customer, ");
        //For Oracle
        queryBuffer.append(" LJB_LE_ID as LE_ID, to_char(LJB_LSP_ID) as LSP_ID, '2' as CATEGORY from SCI_LSP_JOINT_BORROWER where UPDATE_STATUS_IND <>'D') customer, ");
        queryBuffer.append(" SCI_LE_SUB_PROFILE lsp, SCI_LE_MAIN_PROFILE lmp ");
        queryBuffer.append(" where customer.LE_ID = lsp.LSP_LE_ID");
        queryBuffer.append(" and customer.LSP_ID = lsp.LSP_ID");
        queryBuffer.append(" and lsp.CMS_LE_MAIN_PROFILE_ID = lmp.CMS_LE_MAIN_PROFILE_ID");
        queryBuffer.append(" and customer.LMT_PROFILE_ID = ? ");

		List resultList = getJdbcTemplate().query(queryBuffer.toString(), new Object[] { new Long(limitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ICMSCustomer obCustomer = new OBCMSCustomer();
						obCustomer.setCustomerID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));
						obCustomer.setCustomerName(rs.getString("LSP_SHORT_NAME"));

						ICMSLegalEntity obLegalEntity = new OBCMSLegalEntity();
						obLegalEntity.setLegalName(rs.getString("LSP_SHORT_NAME"));
						obLegalEntity.setLEReference(rs.getString("LSP_LE_ID"));
						obLegalEntity.setLegalRegNumber(rs.getString("LMP_ID_NUMBER"));
						obCustomer.setCMSLegalEntity(obLegalEntity);

						return obCustomer;
					}
				});

		return resultList;
	}

	public List getOnlyJointBorrowerList(long limitProfileId) throws Exception {

		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("select jb.CMS_LMP_LIMIT_PROFILE_ID, sub.LSP_LE_ID, sub.CMS_LE_SUB_PROFILE_ID, ");
		queryBuffer.append(" sub.LSP_SHORT_NAME, main.LMP_INC_NUM_TEXT, main.LMP_LEGAL_CONST_VALUE ");
		queryBuffer.append(" from SCI_LSP_JOINT_BORROWER jb, SCI_LE_SUB_PROFILE sub, SCI_LE_MAIN_PROFILE main ");
		queryBuffer.append(" where jb.LJB_LE_ID = sub.LSP_LE_ID ");
		queryBuffer.append(" and jb.LJB_LSP_ID = to_char(sub.LSP_ID) ");
		queryBuffer.append(" and sub.CMS_LE_MAIN_PROFILE_ID = main.CMS_LE_MAIN_PROFILE_ID ");
		queryBuffer.append(" and jb.cms_lmp_limit_profile_id = ? ");
		queryBuffer.append(" and jb.UPDATE_STATUS_IND <> 'D' ");

		List resultList = getJdbcTemplate().query(queryBuffer.toString(), new Object[] { new Long(limitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ICMSCustomer obCustomer = new OBCMSCustomer();
						obCustomer.setCustomerID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));
						obCustomer.setCustomerName(rs.getString("LSP_SHORT_NAME"));

						ICMSLegalEntity obLegalEntity = new OBCMSLegalEntity();
						obLegalEntity.setLegalName(rs.getString("LSP_SHORT_NAME"));
						obLegalEntity.setLEReference(rs.getString("LSP_LE_ID"));
						obLegalEntity.setLegalRegNumber(rs.getString("LMP_INC_NUM_TEXT"));
						obLegalEntity.setLegalConstitution(rs.getString("LMP_LEGAL_CONST_VALUE"));
						obCustomer.setCMSLegalEntity(obLegalEntity);
						return obCustomer;
					}
				});

		return resultList;
	}

    /**
     * Method to form the sql condition, given the criteria object
     *
     * @param criteria - customerDocSearchCriteria
     * @return String - the formatted sql condition
     * @throws SearchDAOException if errors
     */
    private String getSearchCustomerSQL(CustomerSearchCriteria criteria, SQLParameter params)
            throws SearchDAOException, DAPFilterException {
        StringBuffer buf = new StringBuffer();

		// search in the following priority...
		String customerName = criteria.getCustomerName();
		String legalIDType = criteria.getLeIDType();
		String legalID = criteria.getLegalID();
		String aaNumber = criteria.getAaNumber();

        String idNO = criteria.getIdNO();
        String aaType = criteria.getLmtProfileType();
        
        String docBarCode = criteria.getDocBarCode();
        
        /*String category =  criteria.getCategoryImage();
        String camDocName = criteria.getCamDocName();
        String statementDocName = criteria.getStatementDocName();
        String othersDocNames = criteria.getOthersDocsName();
        String fromDocAmount = criteria.getFromAmount();
        String toDocAmount = criteria.getToAmount();
        String toDate = criteria.getToDatedoc();
        String fromDate = criteria.getFromDateDoc();
        String docDateType = criteria.getDocumentDateType();
        String filterParty = criteria.getFilterPartyName();
        String docDescription = "";
        
        if(criteria.getFacilityDocName() != null && !"".equals(criteria.getFacilityDocName())) {
        	docDescription = criteria.getFacilityDocName();
        }else if(criteria.getSecurityDocName() != null && !"".equals(criteria.getSecurityDocName())) {
        	docDescription = criteria.getSecurityDocName();
        }else if(criteria.getCamDocName() != null && !"".equals(criteria.getCamDocName())) {
        	docDescription = criteria.getCamDocName();
        }else if(criteria.getStatementDocName() != null && !"".equals(criteria.getStatementDocName())) {
        	docDescription = criteria.getStatementDocName();
        }else if(criteria.getOthersDocsName() != null && !"".equals(criteria.getOthersDocsName())) {
        	docDescription = criteria.getOthersDocsName();
        }*/
        
        boolean isTradeType = false;
        if ((aaType != null) && aaType.equals(ICMSConstant.AA_TYPE_TRADE)) {
            isTradeType = true;
        }
        if(isEmpty(customerName) && isEmpty(legalID)){
        	criteria.setAll("Y");
        }

		if (!isEmpty(customerName) || "Y".equals(criteria.getAll())) {

			// buf.append("UCASE(");
			buf.append(CUSTOMER_TABLE);
			buf.append(".");
			buf.append(UCASE_CUSTOMER_NAME);
			// buf.append(") LIKE ? ");
			buf.append(" LIKE ? ");
			/*
			 * buf.append(" BETWEEN ? "); buf.append("AND ? ");
			 */

			params.addString(customerName.trim().toUpperCase() + "%");
		} else if (!isEmpty(legalID)) {
			buf.append(LEGAL_ENTITY_TABLE).append(".").append(LEGAL_REF);
			buf.append(" = ? ");

			params.addString((legalID.trim()).toUpperCase());

            if(!isEmpty(legalIDType)) {
                buf.append(" AND "); buf.append(LEGAL_ENTITY_TABLE);
                buf.append("."); buf.append("SOURCE_ID"); buf.append(" = ? ");

				params.addString(legalIDType.trim());
			}
		} else if (!isEmpty(idNO)) {

			String idType = criteria.getIDType();
			String issuedCountry = criteria.getIssuedCountry();

			buf.append("( (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

			buf.append(") OR (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

			buf.append(") OR (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

            buf.append(") )\n");
        } 
        else if (!isEmpty(aaNumber)) {
            buf.append(SELECT_CUSTOMER_PART_SELECT);
            buf.append(" FROM SCI_LE_MAIN_PROFILE, SCI_LE_SUB_PROFILE LEFT OUTER JOIN ");
            buf.append("SCI_LSP_LMT_PROFILE ON ");
            buf.append("SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = ");
            buf.append("SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID AND ");
            buf.append("SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' ");
            buf.append("WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
            buf.append("AND SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM = ? ");

			params.addString(aaNumber.trim());

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(" UNION ");

			buf.append(SELECT_CUSTOMER_PART_SELECT);
			buf.append(" FROM SCI_LE_MAIN_PROFILE, SCI_LE_SUB_PROFILE, ");
			buf.append("SCI_LSP_JOINT_BORROWER , SCI_LSP_LMT_PROFILE ");
			buf.append("WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = ");
			buf.append("SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
			buf.append("AND SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM = ? ");

			params.addString(aaNumber.trim());

			buf.append("AND SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ");
			buf.append("AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' ");
			buf.append("AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = ");
			buf.append("SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID ");
			buf.append("AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' ");

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(CUSTOMER_SORT_ORDER);

            return buf.toString();
        } else if (!isEmpty(docBarCode)) {
        	
        	buf.append(SELECT_CUSTOMER_PART_SELECT);  	
        	buf.append("from CMS_CUST_DOC_ITEM ");
        	buf.append("join CMS_CUST_DOC on CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ID = CMS_CUST_DOC.CUSTODIAN_DOC_ID ");
        	buf.append("join CMS_CHECKLIST_ITEM on CMS_CHECKLIST_ITEM.CHECKLIST_ID = CMS_CUST_DOC.CHECKLIST_ID ");
        	buf.append("join CMS_CHECKLIST on CMS_CHECKLIST.CHECKLIST_ID = CMS_CHECKLIST_ITEM.CHECKLIST_ID ");  
        	buf.append("join SCI_LE_SUB_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID ");
        	buf.append("join SCI_LE_MAIN_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
        	buf.append("join SCI_LSP_LMT_PROFILE on CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "); 
        	buf.append("join SCI_LSP_APPR_LMTS on SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ");
        	buf.append("where CMS_CUST_DOC_ITEM.STATUS= 'LODGED' "); 
        	buf.append("AND CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ITEM_BARCODE = ? ");
        	
        	params.addString(docBarCode.trim());
        	
        	appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);
        	
        	
        	buf.append("UNION ");
        	buf.append(SELECT_CUSTOMER_PART_SELECT);
        	buf.append("from CMS_CUST_DOC_ITEM ");
        	buf.append("join CMS_CUST_DOC on CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ID = CMS_CUST_DOC.CUSTODIAN_DOC_ID ");
        	buf.append("join CMS_CHECKLIST_ITEM on CMS_CHECKLIST_ITEM.CHECKLIST_ID = CMS_CUST_DOC.CHECKLIST_ID ");
        	buf.append("join CMS_CHECKLIST on CMS_CHECKLIST.CHECKLIST_ID = CMS_CHECKLIST_ITEM.CHECKLIST_ID ");  
        	buf.append("join SCI_LE_SUB_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID ");
        	buf.append("join SCI_LE_MAIN_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
        	buf.append("join SCI_LSP_LMT_PROFILE on CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "); 
        	buf.append("join SCI_LSP_APPR_LMTS on SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ");
        	buf.append("join CMS_LIMIT_SECURITY_MAP on SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID "); 
        	buf.append("join CMS_SECURITY on CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID "); 
        	buf.append("join CMS_SEC_ENVELOPE on SEC_LSP_LMT_PROFILE_ID = CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID ");
        	buf.append("join CMS_SEC_ENVELOPE_ITEM on CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ID = CMS_SEC_ENVELOPE.SEC_ENVELOPE_ID ");
        	buf.append("where (CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND <> 'D' or CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND is null) ");
        	buf.append("and CMS_CUST_DOC_ITEM.STATUS = 'LODGED' and SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' and SCI_LSP_APPR_LMTS.CMS_LIMIT_STATUS <> 'DELETED' ");
        	buf.append("AND CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ITEM_BARCODE = ? ");

			params.addString(docBarCode.trim());

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(CUSTOMER_SORT_ORDER);

            return buf.toString();
        } else {
            throw new IllegalArgumentException("No criteria found to search the customer!!!");
        }
      
        /*if(fromDocAmount != null && toDocAmount != null) {
        	buf.append(" AND CMS_CHECKLIST_ITEM.DOC_AMT BETWEEN ?  AND ?  "); 
        	params.addString(fromDocAmount.trim());
        	params.addString(toDocAmount.trim());
        }
        if(fromDate != null && toDate != null) {
        	
        	if("DOC_RECEIVED_DATE".equals(docDateType)) {
        		//DOC_RECEIVED_DATE   DOC_DATE   DOC_SCAN_DATE
        		buf.append(" AND CMS_CHECKLIST_ITEM.RECEIVED_DATE BETWEEN ? AND ? "); 
        	}else if("DOC_DATE".equals(docDateType)) {
        		buf.append(" AND CMS_CHECKLIST_ITEM.DOC_DATE BETWEEN ? AND ? "); 
        	}else if("DOC_SCAN_DATE".equals(docDateType)) {
        		
        	}
        	
        	params.addString(fromDate.trim());
        	params.addString(toDate.trim());
        }
        if(category != null && !"".equals(category)) {
        	buf.append(" AND CMS_UPLOADED_IMAGES.IMG_ID = CMS_IMAGE_TAG_MAP.IMAGE_ID "); 
        	buf.append(" AND CMS_IMAGE_TAG_MAP.TAG_ID = CMS_IMAGE_TAG_DETAILS.ID "); 
        	buf.append(" AND CMS_IMAGE_TAG_DETAILS.DOC_DESC =  TO_CHAR(CMS_CHECKLIST_ITEM.DOC_ITEM_ID) "); 
        	buf.append(" AND CMS_UPLOADED_IMAGES.CATEGORY = ? "); 
        	
        	params.addString(category.trim());
        }
        if(docDescription != null && !"".equals(docDescription)) {
        	buf.append(" AND CMS_CHECKLIST_ITEM.DOC_DESCRIPTION = ? "); 
        	
        	params.addString(docDescription.trim());
        }
        buf.append(" AND CMS_IMAGE_TAG_DETAILS.CUST_ID = SCI_LE_MAIN_PROFILE.LMP_LE_ID "); */
        
        
        if (criteria.getByLimit()) {
            buf.append(" AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID IS NOT NULL \n");
        }
		
        if(ICMSConstant.CUSTOMER_STATUS_ALL.equals(criteria.getCustomerStatus())){
        	//append nothing to get all cutomer
        }else if(ICMSConstant.CUSTOMER_STATUS_INACTIVE.equals(criteria.getCustomerStatus())){
        	//to get inactive customer
        	buf.append("      AND SCI_LE_SUB_PROFILE.STATUS = 'INACTIVE'\n");
        }else{
        	//to get active customer by default
			buf.append("      AND SCI_LE_SUB_PROFILE.STATUS <> 'INACTIVE'\n");
		}

		// appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

		StringBuffer querySQL = new StringBuffer();
		String condition = buf.toString();

		querySQL.append(isTradeType ? SELECT_CUSTOMER_TRADE_PART_SELECT : SELECT_CUSTOMER_PART_SELECT);
		querySQL.append(SELECT_ALL_CUSTOMER_PART_FROMWHERE); // SHiv

		if (!isEmpty(condition)) {
			querySQL.append(" AND ");
			querySQL.append(condition);
		}

		querySQL.append(CUSTOMER_SORT_ORDER);
		System.out.println("CustomerDAO.java =>querySQL = " + querySQL);
		return querySQL.toString();
	}

	private String getSearchCustomerImageUploadSQL(CustomerSearchCriteria criteria, SQLParameter params)
			throws SearchDAOException, DAPFilterException {
		StringBuffer buf = new StringBuffer();

		// search in the following priority...
		String customerName = criteria.getCustomerName();
		String legalIDType = criteria.getLeIDType();
		String legalID = criteria.getLegalID();
		String aaNumber = criteria.getAaNumber();

        String idNO = criteria.getIdNO();
        String aaType = criteria.getLmtProfileType();
        
        String docBarCode = criteria.getDocBarCode();
        
        String category =  criteria.getCategoryImage();
        String fromDocAmount = criteria.getFromAmount();
        String toDocAmount = criteria.getToAmount();
        String toDate = criteria.getToDatedoc();
        String fromDate = criteria.getFromDateDoc();
        String docDateType = criteria.getDocumentDateType();
        String filterParty = criteria.getFilterPartyName();
        String docDescription = "";
        boolean flag = true;
        boolean flag1 = true;
        
        if((fromDocAmount != null && !"".equals(fromDocAmount)) || (toDocAmount != null && !"".equals(toDocAmount) )
        		|| (toDate != null && !"".equals(toDate)) ||
        				(fromDate != null && !"".equals(fromDate)) || (docDateType != null && !"".equals(docDateType)) ){
        	
        	flag = true;
        }else {
        	flag = false;
        }
        
        if((category != null && !"".equals(category)) && (fromDocAmount == null || "".equals(fromDocAmount)) && (toDocAmount == null || "".equals(toDocAmount) )
        		&& (toDate == null || "".equals(toDate)) &&
        				(fromDate == null || "".equals(fromDate)) && (docDateType == null || "".equals(docDateType)) ){
        	
        	flag1 = true;
        }else {
        	flag1 = false;
        }
        
        String docNameColumanName = "";
        //SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD_CATEGORY
        //FACILITY_DOC_NAME  SECURITY_DOC_NAME  STATEMENT_DOC_NAME  CAM_DOC_NAME   OTHER_DOC_NAME
        
        if(criteria.getFacilityDocName() != null && !"".equals(criteria.getFacilityDocName())) {
        	docDescription = criteria.getFacilityDocName();
        	docNameColumanName = "FACILITY_DOC_NAME";
        }else if(criteria.getSecurityDocName() != null && !"".equals(criteria.getSecurityDocName())) {
        	docDescription = criteria.getSecurityDocName();
        	docNameColumanName = "SECURITY_DOC_NAME";
        }else if(criteria.getCamDocName() != null && !"".equals(criteria.getCamDocName())) {
        	docDescription = criteria.getCamDocName();
        	docNameColumanName = "CAM_DOC_NAME";
        }else if(criteria.getStatementDocName() != null && !"".equals(criteria.getStatementDocName())) {
        	docDescription = criteria.getStatementDocName();
        	docNameColumanName = "STATEMENT_DOC_NAME";
        }else if(criteria.getOthersDocsName() != null && !"".equals(criteria.getOthersDocsName())) {
        	docDescription = criteria.getOthersDocsName();
        	docNameColumanName = "OTHER_DOC_NAME";
        }
        
        boolean isTradeType = false;
        if ((aaType != null) && aaType.equals(ICMSConstant.AA_TYPE_TRADE)) {
            isTradeType = true;
        }
        if(isEmpty(customerName) && isEmpty(legalID)){
        	criteria.setAll("Y");
        }

		if (!isEmpty(customerName) || "Y".equals(criteria.getAll())) {

			// buf.append("UCASE(");
			buf.append(CUSTOMER_TABLE);
			buf.append(".");
			buf.append(UCASE_CUSTOMER_NAME);
			// buf.append(") LIKE ? ");
			buf.append(" LIKE ? ");
			/*
			 * buf.append(" BETWEEN ? "); buf.append("AND ? ");
			 */

			params.addString(customerName.trim().toUpperCase() + "%");
		} else if (!isEmpty(legalID)) {
			buf.append(LEGAL_ENTITY_TABLE).append(".").append(LEGAL_REF);
			buf.append(" = ? ");

			params.addString((legalID.trim()).toUpperCase());

            if(!isEmpty(legalIDType)) {
                buf.append(" AND "); buf.append(LEGAL_ENTITY_TABLE);
                buf.append("."); buf.append("SOURCE_ID"); buf.append(" = ? ");

				params.addString(legalIDType.trim());
			}
		} else if (!isEmpty(idNO)) {

			String idType = criteria.getIDType();
			String issuedCountry = criteria.getIssuedCountry();

			buf.append("( (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

			buf.append(") OR (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_SECOND_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

			buf.append(") OR (");

			buf.append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_NUMBER");
			buf.append(" = ? ");
			params.addString(idNO.trim().toUpperCase());

			if (StringUtils.isNotBlank(idType)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_TYPE_VALUE");
				buf.append(" = ? ");
				params.addString(idType);
			}

			if (StringUtils.isNotBlank(issuedCountry)) {
				buf.append(" AND ").append(LEGAL_ENTITY_TABLE).append(".").append("LMP_THIRD_ID_COUNTRY_ISSUED");
				buf.append(" = ? ");
				params.addString(issuedCountry);
			}

            buf.append(") )\n");
        } 
        else if (!isEmpty(aaNumber)) {
            buf.append(SELECT_CUSTOMER_PART_SELECT);
            buf.append(" FROM SCI_LE_MAIN_PROFILE, SCI_LE_SUB_PROFILE LEFT OUTER JOIN ");
            buf.append("SCI_LSP_LMT_PROFILE ON ");
            buf.append("SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = ");
            buf.append("SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID AND ");
            buf.append("SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' ");
            buf.append("WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
            buf.append("AND SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM = ? ");

			params.addString(aaNumber.trim());

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(" UNION ");

			buf.append(SELECT_CUSTOMER_PART_SELECT);
			buf.append(" FROM SCI_LE_MAIN_PROFILE, SCI_LE_SUB_PROFILE, ");
			buf.append("SCI_LSP_JOINT_BORROWER , SCI_LSP_LMT_PROFILE ");
			buf.append("WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = ");
			buf.append("SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
			buf.append("AND SCI_LSP_LMT_PROFILE.LLP_BCA_REF_NUM = ? ");

			params.addString(aaNumber.trim());

			buf.append("AND SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ");
			buf.append("AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' ");
			buf.append("AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = ");
			buf.append("SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID ");
			buf.append("AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'REJECTED' ");

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(CUSTOMER_SORT_ORDER);

            return buf.toString();
        } else if (!isEmpty(docBarCode)) {
        	
        	buf.append(SELECT_CUSTOMER_PART_SELECT);  	
        	buf.append("from CMS_CUST_DOC_ITEM ");
        	buf.append("join CMS_CUST_DOC on CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ID = CMS_CUST_DOC.CUSTODIAN_DOC_ID ");
        	buf.append("join CMS_CHECKLIST_ITEM on CMS_CHECKLIST_ITEM.CHECKLIST_ID = CMS_CUST_DOC.CHECKLIST_ID ");
        	buf.append("join CMS_CHECKLIST on CMS_CHECKLIST.CHECKLIST_ID = CMS_CHECKLIST_ITEM.CHECKLIST_ID ");  
        	buf.append("join SCI_LE_SUB_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID ");
        	buf.append("join SCI_LE_MAIN_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
        	buf.append("join SCI_LSP_LMT_PROFILE on CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "); 
        	buf.append("join SCI_LSP_APPR_LMTS on SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ");
        	buf.append("where CMS_CUST_DOC_ITEM.STATUS= 'LODGED' "); 
        	buf.append("AND CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ITEM_BARCODE = ? ");
        	
        	params.addString(docBarCode.trim());
        	
        	appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);
        	
        	
        	buf.append("UNION ");
        	buf.append(SELECT_CUSTOMER_PART_SELECT);
        	buf.append("from CMS_CUST_DOC_ITEM ");
        	buf.append("join CMS_CUST_DOC on CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ID = CMS_CUST_DOC.CUSTODIAN_DOC_ID ");
        	buf.append("join CMS_CHECKLIST_ITEM on CMS_CHECKLIST_ITEM.CHECKLIST_ID = CMS_CUST_DOC.CHECKLIST_ID ");
        	buf.append("join CMS_CHECKLIST on CMS_CHECKLIST.CHECKLIST_ID = CMS_CHECKLIST_ITEM.CHECKLIST_ID ");  
        	buf.append("join SCI_LE_SUB_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID ");
        	buf.append("join SCI_LE_MAIN_PROFILE on SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
        	buf.append("join SCI_LSP_LMT_PROFILE on CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "); 
        	buf.append("join SCI_LSP_APPR_LMTS on SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ");
        	buf.append("join CMS_LIMIT_SECURITY_MAP on SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID "); 
        	buf.append("join CMS_SECURITY on CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID "); 
        	buf.append("join CMS_SEC_ENVELOPE on SEC_LSP_LMT_PROFILE_ID = CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID ");
        	buf.append("join CMS_SEC_ENVELOPE_ITEM on CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ID = CMS_SEC_ENVELOPE.SEC_ENVELOPE_ID ");
        	buf.append("where (CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND <> 'D' or CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND is null) ");
        	buf.append("and CMS_CUST_DOC_ITEM.STATUS = 'LODGED' and SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' and SCI_LSP_APPR_LMTS.CMS_LIMIT_STATUS <> 'DELETED' ");
        	buf.append("AND CMS_CUST_DOC_ITEM.CUSTODIAN_DOC_ITEM_BARCODE = ? ");

			params.addString(docBarCode.trim());

			appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

			buf.append(CUSTOMER_SORT_ORDER);

            return buf.toString();
        } else {
            throw new IllegalArgumentException("No criteria found to search the customer!!!");
        }
      
        if(fromDocAmount != null && !"".equals(fromDocAmount) && toDocAmount != null && !"".equals(toDocAmount)) {
        	buf.append(" AND CMS_CHECKLIST_ITEM.DOC_AMT BETWEEN ?  AND ?  "); 
        	params.addString(fromDocAmount.trim());
        	params.addString(toDocAmount.trim());
        }
        if(fromDate != null && toDate != null && !"".equals(fromDate) && !"".equals(toDate)) {
        	
        	if("DOC_RECEIVED_DATE".equals(docDateType)) {
        		//DOC_RECEIVED_DATE   DOC_DATE   DOC_SCAN_DATE
        		buf.append(" AND CMS_CHECKLIST_ITEM.RECEIVED_DATE BETWEEN ? AND ? "); 
        	}else if("DOC_DATE".equals(docDateType)) {
        		buf.append(" AND CMS_CHECKLIST_ITEM.DOC_DATE BETWEEN ? AND ? "); 
        	}else if("DOC_SCAN_DATE".equals(docDateType)) {
        		buf.append(" AND CMS_UPLOADED_IMAGES.CREATION_DATE BETWEEN ? AND  ?  "); 
        	}
        	
        	params.addString(fromDate.trim());
        	params.addString(toDate.trim());
        }
        if(category != null && !"".equals(category)) {
        	/*buf.append(" AND CMS_UPLOADED_IMAGES.IMG_ID = CMS_IMAGE_TAG_MAP.IMAGE_ID "); 
        	buf.append(" AND CMS_IMAGE_TAG_MAP.TAG_ID = CMS_IMAGE_TAG_DETAILS.ID "); 
        	buf.append(" AND CMS_IMAGE_TAG_DETAILS.DOC_DESC =  TO_CHAR(CMS_CHECKLIST_ITEM.DOC_ITEM_ID) ");*/
        	if(!"CATEGORY_ALL".equals(category) && !"ALL".equals(category)) {
        	buf.append(" AND CMS_UPLOADED_IMAGES.CATEGORY = ? "); 
        	buf.append(" AND SCI_LE_SUB_PROFILE.ULSP_SHORT_NAME = CMS_UPLOADED_IMAGES.CUST_NAME ");
        	params.addString(category.trim());
        	}
        }
        if(docDescription != null && !"".equals(docDescription)) {
//        	buf.append(" AND CMS_CHECKLIST_ITEM.DOC_DESCRIPTION = ? "); 
        	buf.append(" AND CMS_UPLOADED_IMAGES."+docNameColumanName+" = ? "); 
        	
        	params.addString(docDescription.trim());
        }
        
        if(flag == true) {
        
        buf.append(" AND CMS_UPLOADED_IMAGES.IMG_ID = CMS_IMAGE_TAG_MAP.IMAGE_ID "); 
    	buf.append(" AND CMS_IMAGE_TAG_MAP.TAG_ID = CMS_IMAGE_TAG_DETAILS.ID "); 
    	buf.append(" AND CMS_IMAGE_TAG_DETAILS.DOC_DESC =  TO_CHAR(CMS_CHECKLIST_ITEM.DOC_ITEM_ID) ");
        buf.append(" AND CMS_IMAGE_TAG_DETAILS.CUST_ID = SCI_LE_MAIN_PROFILE.LMP_LE_ID "); 
        }
        
        if (criteria.getByLimit()) {
            buf.append(" AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID IS NOT NULL \n");
        }
		
        if(ICMSConstant.CUSTOMER_STATUS_ALL.equals(criteria.getCustomerStatus())){
        	//append nothing to get all cutomer
        }else if(ICMSConstant.CUSTOMER_STATUS_INACTIVE.equals(criteria.getCustomerStatus())){
        	//to get inactive customer
        	buf.append("      AND SCI_LE_SUB_PROFILE.STATUS = 'INACTIVE'\n");
        }else{
        	//to get active customer by default
			buf.append("      AND SCI_LE_SUB_PROFILE.STATUS <> 'INACTIVE'\n");
		}

		// appendOriginatingLocationConditionQuery(buf, criteria.getCtx(), params);

		StringBuffer querySQL = new StringBuffer();
		String condition = buf.toString();

        querySQL.append(isTradeType ? SELECT_CUSTOMER_TRADE_PART_SELECT_IMAGE_UPLOAD : SELECT_CUSTOMER_PART_SELECT_IMAGE_UPLOAD);
        if(flag == true) {
        querySQL.append(SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD);  //SHiv SELECT_ALL_CUSTOMER_PART_FROMWHERE
        }
        else if(flag1 == true) {
        	querySQL.append(SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD_CATEGORY);  //SHiv SELECT_ALL_CUSTOMER_PART_FROMWHERE
        	//SELECT_ALL_CUSTOMER_PART_FROMWHERE_IMAGE_UPLOAD_CATEGORY
        }
        else {
        	querySQL.append(SELECT_ALL_CUSTOMER_PART_FROMWHERE);  //SHiv SELECT_ALL_CUSTOMER_PART_FROMWHERE
        }
        /*querySQL.append(isTradeType ? SELECT_CUSTOMER_TRADE_PART_SELECT : SELECT_CUSTOMER_PART_SELECT);
        querySQL.append(SELECT_ALL_CUSTOMER_PART_FROMWHERE);  //SHiv
*/        
        if (!isEmpty(condition)) {
            querySQL.append(" AND ");
            querySQL.append(condition);
        }

		querySQL.append(CUSTOMER_SORT_ORDER);
		System.out.println("CustomerDAO.java =>querySQL = " + querySQL);

		return querySQL.toString();
	}

	private void appendOriginatingLocationConditionQuery(StringBuffer buf, ITrxContext ctx, SQLParameter params)
			throws DAPFilterException {
		boolean isFilteredByUptoApplicationOnly = PropertyManager.getBoolean("customer.search.dap.aa.only");

		if (ctx != null) {
			ITeam team = ctx.getTeam();

			if (isFilteredByUptoApplicationOnly) {
				buf.append(CustomerDAOFilterHelper.getLimitProfileDAPFiltersQueryByTeam(team, params));
			} else {
				if (buf.length() == 0) {
					buf.append(" 1 = 1 ");
				}

				String[] country = team.getCountryCodes();
				String[] org = team.getOrganisationCodes();

				if (!CommonUtil.isEmptyArray(country) && !CommonUtil.isEmptyArray(org)) {
					String limitAndSecurityDaoFiltersQueryByTeam = CustomerDAOFilterHelper
							.getLimitAndSecurityDAPFiltersQueryByTeam(team, params, true);

					buf.append(limitAndSecurityDaoFiltersQueryByTeam);
				} else {
					throw new DAPFilterException("Country or Organisation List in Team is empty.");
				}
			}
		}
	}

    /**
     * Utiloty method to check if a string value is null or empty
     *
     * @param aValue - String
     * @return boolean - true if empty and false otherwise
     */
    private boolean isEmpty(String aValue) {
        if ((aValue != null) && (aValue.trim().length() > 0)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Process the customer doc search result
     *
     * @param rs - ResultSet
     * @return OBCustomerSearchResult - the customer from the resultset
     * @throws SQLException if errors
     */
    private OBCustomerSearchResult processResultSet(ResultSet rs) throws SQLException {

		OBCustomerSearchResult result = new OBCustomerSearchResult();
		result.setLegalName(rs.getString(LEGAL_NAME));
		result.setLegalID(rs.getLong(LEGAL_ID));
		result.setLegalReference(rs.getString(LEGAL_REF));
		result.setCustomerName(rs.getString(UCASE_CUSTOMER_NAME));
		result.setLmpLeID(rs.getString(LEGAL_REF));

		result.setDob(rs.getTimestamp("LMP_INC_DATE"));
		result.setSourceID(rs.getString("SOURCE_ID"));

		String firstIdNumber = rs.getString("LMP_ID_NUMBER");
		String firstIdType = rs.getString("LMP_ID_TYPE_VALUE");
		String secondIdNumber = rs.getString("LMP_SECOND_ID_NUMBER");
		String secondtIdType = rs.getString("LMP_SECOND_ID_TYPE_VALUE");
		String thirdIdNumber = rs.getString("LMP_THIRD_ID_NUMBER");
		String thirdIdType = rs.getString("LMP_THIRD_ID_TYPE_VALUE");

		if (firstIdNumber != null) {
			result.setIdNo(firstIdNumber);
			result.setIdType(firstIdType);
		} else if (secondIdNumber != null) {
			result.setIdNo(secondIdNumber);
			result.setIdType(secondtIdType);
		} else if (thirdIdNumber != null) {
			result.setIdNo(thirdIdNumber);
			result.setIdType(thirdIdType);
		}

		result.setSubProfileID(rs.getLong(CUSTOMER_ID));
		result.setSubProfileReference(rs.getString(CUSTOMER_REF));
		result.setInstructionID(rs.getLong(BCA_ID));
		result.setInstructionRefNo(rs.getString(BCA_REF));
		result.setLosLimitProfileRef(rs.getString("LOS_BCA_REF_NUM"));
		// result.setTransactionID(rs.getString(TRX_ID));

		result.setBcaStatus(rs.getString(CMS_BCA_STATUS));
		OBBookingLocation bkg = new OBBookingLocation();
		bkg.setCountryCode(rs.getString(BCA_ORIG_CNTRY));
		bkg.setOrganisationCode(rs.getString(BCA_ORIG_ORG));
		result.setBcaOrigLocation(bkg);
		result.setCamType(rs.getString("CAM_TYPE")); // A Shiv 190911

		bkg = new OBBookingLocation();
		bkg.setCountryCode(rs.getString("CMS_SUB_ORIG_COUNTRY"));
		bkg.setOrganisationCode(rs.getString("CMS_SUB_ORIG_ORGANISATION"));
		result.setInstrOrigLocation(bkg);

		result.setNoLimitsInd((result.getInstructionRefNo() == null));

		Timestamp ts = rs.getTimestamp(BCA_APPROVED_DATE, DateUtil.getCalendar());
		if (null != ts) {
			result.setInstructionApprovedDate(new java.util.Date(ts.getTime()));
		}

		String aaType = rs.getString(LMT_PROFILE_TYPE);
		result.setLmtProfileType(aaType);

		String createInd = rs.getString(CMS_CREATE_IND);
		result.setCMSCreateInd((ICMSConstant.TRUE_VALUE.equals(createInd)));

		if ((aaType != null) && aaType.equals(ICMSConstant.AA_TYPE_BANK)) {
			// String hasActiveLimitStr = rs.getString("HAS_ACTIVE_LIMIT");
			// result.setCanDeleteLmtProfile( hasActiveLimitStr == null );

		} else if ((aaType != null) && aaType.equals(ICMSConstant.AA_TYPE_TRADE)) {
			result.setAgreementID(rs.getLong(AGREEMENT_ID));
			result.setAgreementType(rs.getString(AGREEMENT_TYPE));
		}

		return result;
	}

	/**
	 * Method to check if a customer is a co borrower
	 *
	 * @param customerID
	 * @return
	 * @throws SQLException
	 */
	public boolean isCoBorrower(long customerID) {

        int count = getJdbcTemplate()
                .queryForInt(SELECT_COUNT_CO_BORROWER_LIMIT, new Object[]{new Long(customerID)});

		return (count > 0);
	}

	public List searchCustomerForPlgLink(MILeSearchCriteria criteria) throws SearchDAOException {

		String sql = "SELECT PROF.LMP_LE_ID, PROF.LMP_SHORT_NAME, PROF.SOURCE_ID, "
				+ " PROF.LMP_ID_TYPE_NUM, PROF.LMP_ID_TYPE_VALUE, "
				+ " PROF.LMP_LE_ID_SRC_NUM, PROF.LMP_LE_ID_SRC_VALUE, PROF.LMP_INC_NUM_TEXT "
				+ "FROM SCI_LE_MAIN_PROFILE PROF WHERE ";
		if ((criteria.getSourceId() != null) && !criteria.getSourceId().trim().equals("")) {
			sql = sql + "PROF.SOURCE_ID = '" + criteria.getSourceId() + "' AND ";
		}
		if ((criteria.getLeId() != null) && !criteria.getLeId().trim().equals("")) {
			sql = sql + "PROF.LMP_LE_ID LIKE '" + criteria.getLeId() + "%' AND ";
		}
		if ((criteria.getCustomerName() != null) && !criteria.getCustomerName().trim().equals("")) {
			sql = sql + "UPPER(PROF.LMP_SHORT_NAME) LIKE '" + criteria.getCustomerName().toUpperCase() + "%' AND ";
		}
		if ((criteria.getIDType() != null) && !criteria.getIDType().trim().equals("")) {
			sql = sql + "PROF.LMP_ID_TYPE_VALUE = '" + criteria.getIDType() + "' AND ";
		}
		if ((criteria.getIDNo() != null) && !criteria.getIDNo().trim().equals("")) {
			sql = sql + "PROF.LMP_INC_NUM_TEXT LIKE '" + criteria.getIDNo() + "%' AND ";
		}
		if (sql.endsWith("WHERE ")) {
			int len = sql.length();
			sql = sql.substring(0, len - 6);
		}
		if (sql.endsWith("AND ")) {
			int len = sql.length();
			sql = sql.substring(0, len - 4);
		}
		sql = sql + "ORDER BY PROF.LMP_SHORT_NAME";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] stringArray = new String[8];
				stringArray[0] = rs.getString("LMP_LE_ID");
				stringArray[1] = rs.getString("LMP_SHORT_NAME");
				stringArray[2] = rs.getString("SOURCE_ID");
				stringArray[3] = rs.getString("LMP_INC_NUM_TEXT");
				stringArray[4] = rs.getString("LMP_ID_TYPE_NUM");
				stringArray[5] = rs.getString("LMP_ID_TYPE_VALUE");
				stringArray[6] = rs.getString("LMP_LE_ID_SRC_NUM");
				stringArray[7] = rs.getString("LMP_LE_ID_SRC_VALUE");

                return stringArray;
            }
        }
        );

		return resultList;
	}

	// inner class
	private class DAPFilterException extends Exception {

		private static final long serialVersionUID = -8859656721532390989L;

		public DAPFilterException(String msg) {
			super(msg);
		}
	}

	public Long searchCustomerByIDNumber(String idNumber) throws CustomerException, SearchDAOException {
		return (Long) getJdbcTemplate().query(SELECT_CUSTOMER_BY_IDNO, new Object[] { idNumber },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						HashMap map = new HashMap();
						while (rs.next()) {
							long subProfileId = rs.getLong(1);
							String sourceID = rs.getString(2);
							map.put(sourceID, new Long(subProfileId));
						}
						Long foundSubProfileId = (Long) map.get(ICMSConstant.SOURCE_ID_ARBS);
						if (foundSubProfileId != null) {
							return foundSubProfileId;
						}
						foundSubProfileId = (Long) map.get(ICMSConstant.SOURCE_ID_SEMA);
						if (foundSubProfileId != null) {
							return foundSubProfileId;
						}
						foundSubProfileId = (Long) map.get(ICMSConstant.SOURCE_ID_BOST);
						if (foundSubProfileId != null) {
							return foundSubProfileId;
						}
						foundSubProfileId = (Long) map.get(ICMSConstant.SOURCE_ID_QUAN);
						if (foundSubProfileId != null) {
							return foundSubProfileId;
						}
						return null;
					}
				});
	}

    /**
     * @see com.integrosys.cms.app.customer.bus.ICustomerDAO#getCustomerMainDetails
     */
    public Map getCustomerMainDetails(List subProfileIDList) throws SearchDAOException {
        final Map custList = new HashMap();
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select lsp.CMS_LE_SUB_PROFILE_ID, lsp.LSP_LE_ID, lsp.LSP_SHORT_NAME, ");
        queryBuffer.append(" lmp.LMP_INC_NUM_TEXT, lmp.LMP_INC_CNTRY_ISO_CODE, lmp.LMP_INC_DATE, lmp.SOURCE_ID, lmp.LMP_LONG_NAME ");
        queryBuffer.append(" from SCI_LE_SUB_PROFILE lsp, SCI_LE_MAIN_PROFILE lmp ");
        queryBuffer.append(" where ");
        queryBuffer.append(" lsp.CMS_LE_MAIN_PROFILE_ID = lmp.CMS_LE_MAIN_PROFILE_ID");
        queryBuffer.append(" and ( lsp.CMS_LE_SUB_PROFILE_ID ) ");

		if (subProfileIDList == null || subProfileIDList.size() == 0) {
			return custList;
		}

		ArrayList params = new ArrayList();
		CommonUtil.buildSQLInList(subProfileIDList, queryBuffer, params);
		queryBuffer.append(" ORDER BY LSP_SHORT_NAME");

        return (Map) getJdbcTemplate().query(queryBuffer.toString(), params.toArray(new Object[0]), new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ICMSCustomer obCustomer = new OBCMSCustomer();
                    long custID = rs.getLong("CMS_LE_SUB_PROFILE_ID");
                    obCustomer.setCustomerID(custID);
                    obCustomer.setCustomerName(rs.getString("LSP_SHORT_NAME"));

                    ICMSLegalEntity obLegalEntity = new OBCMSLegalEntity();
                    obLegalEntity.setLegalName(rs.getString("LMP_LONG_NAME"));
                    obLegalEntity.setLEReference(rs.getString("LSP_LE_ID"));
                    obLegalEntity.setLegalRegNumber(rs.getString("LMP_INC_NUM_TEXT"));
                    obLegalEntity.setSourceID(rs.getString("SOURCE_ID"));
                    obLegalEntity.setLegalRegCountry(rs.getString("LMP_INC_CNTRY_ISO_CODE"));
                    obLegalEntity.setIncorporateDate(rs.getDate("LMP_INC_DATE"));

                    obCustomer.setCMSLegalEntity(obLegalEntity);
                    custList.put(new Long(custID), obCustomer);
                }
                return custList;
            }
        });
    }

	public ArrayList getMBlistByCBleId(long cbLeId) throws SearchDAOException {
		return (ArrayList) getJdbcTemplate().query(getMBByCBSql, new Object[] { new Long(cbLeId) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCustomerSearchResult oCustomer = new OBCustomerSearchResult();
				oCustomer.setLegalReference(rs.getString(LEGAL_REF));
				oCustomer.setLegalName(rs.getString(LEGAL_NAME));
				OBBookingLocation bkg = new OBBookingLocation();
				bkg.setCountryCode(rs.getString(BCA_ORIG_CNTRY));
				bkg.setOrganisationCode(rs.getString(BCA_ORIG_ORG));
				oCustomer.setBcaOrigLocation(bkg);

				oCustomer.setInstructionRefNo(rs.getString(BCA_REF));

                Timestamp ts = rs.getTimestamp(BCA_APPROVED_DATE, DateUtil.getCalendar());
                if (null != ts) {
                    oCustomer.setInstructionApprovedDate(new java.util.Date(ts.getTime()));
                }
                return oCustomer;
            }
        });
    }
    
    public String searchPartyIDBySysID(String systemName, String sourceSystemId) throws SearchDAOException {
        String query="";
  
   	 
   if((sourceSystemId != null && !sourceSystemId.trim().equals("")) &&((systemName != null && !systemName.trim().equals("")))){
   		 query = SELECT_CUSTOMER_BY_OTHER_SYS_ID + "  AND S.CMS_LE_OTHER_SYS_CUST_ID = '"+sourceSystemId+"'" +"  AND S.CMS_LE_SYSTEM_NAME = '"+ systemName+"'";
   	 }
   	 else{
   		 query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME;
   	 }
   List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("LSP_LE_ID");
            }
        }  ); 
   
   if(resultList.size()==0 || resultList.get(0)==null){
		resultList.add("");
	}
    	return resultList.get(0).toString();
    }
    
    public ICMSCustomer getCustomerByLimitProfileId(long limitProfileId) throws CustomerException {

    	String query= new StringBuffer(" SELECT sp.cms_le_sub_profile_id AS partyId, ")
    	 .append("  sp.lsp_short_name             AS partyName, ")
    	 .append("  cc.entry_name as Segment, ")
    	.append("   cr.region_name as region ")
    	.append(" FROM sci_le_sub_profile sp, ")
    	 .append("  sci_lsp_lmt_profile cam, ")
    	.append("   common_code_category_entry cc , ")
    	.append("   SCI_LE_REG_ADDR addr , ")
    	.append("   CMS_REGION cr ")
    	.append(" WHERE ")
    	.append(" cam.cms_customer_id      =sp.cms_le_sub_profile_id ")
    	.append(" AND sp.status                  ='ACTIVE' ")
    	.append(" AND cc.entry_code              =sp.LSP_SGMNT_CODE_VALUE ")
    	.append(" AND cc.category_code           ='HDFC_SEGMENT' ")
    	.append(" AND addr.cms_le_main_profile_id=sp.cms_le_main_profile_id ")
    	.append(" AND addr.lra_type_value        ='CORPORATE' ") 
    	.append(" AND cr.id                      =addr.lra_region ")
    	.append(" AND cam.cms_lsp_lmt_profile_id = ? ").toString();
    	
    	List resultList = getJdbcTemplate().query(query, new Object[]{new Long(limitProfileId)},new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	 
            	ICMSCustomer obCustomer = new OBCMSCustomer();
                	obCustomer.setCustomerID(rs.getLong("partyId"));
                	obCustomer.setCustomerName(rs.getString("partyName"));
                	obCustomer.setCustomerSegment(rs.getString("Segment"));
                	obCustomer.setRegion(rs.getString("region"));
                return obCustomer;
            }
        }
        );
    	
    	if(resultList.size()>0){
    		return (ICMSCustomer) resultList.get(0);	
    		
    	}else{
    		return null;
    	}
		
	}
    
    
    public ICMSCustomer getCustomerByLimitProfileIdForCaseCreation(long limitProfileId) throws CustomerException {

    	String query= new StringBuffer(" SELECT sp.cms_le_sub_profile_id AS partyId, ")
    	 .append("  sp.lsp_short_name             AS partyName, ")
    	 .append("  cc.entry_name as Segment, ")
    	.append("   cr.region_name as region ")
    	.append(" FROM sci_le_sub_profile sp, ")
    	 .append("  sci_lsp_lmt_profile cam, ")
    	.append("   common_code_category_entry cc , ")
    	.append("   SCI_LE_REG_ADDR addr , ")
    	.append("   CMS_REGION cr ")
    	.append(" WHERE ")
    	.append(" cam.cms_customer_id      =sp.cms_le_sub_profile_id ")
    	.append(" AND cc.entry_code              =sp.LSP_SGMNT_CODE_VALUE ")
    	.append(" AND cc.category_code           ='HDFC_SEGMENT' ")
    	.append(" AND addr.cms_le_main_profile_id=sp.cms_le_main_profile_id ")
    	.append(" AND addr.lra_type_value        ='CORPORATE' ") 
    	.append(" AND cr.id                      =addr.lra_region ")
    	.append(" AND cam.cms_lsp_lmt_profile_id = ? ").toString();
    	
    	List resultList = getJdbcTemplate().query(query, new Object[]{new Long(limitProfileId)},new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	 
            	ICMSCustomer obCustomer = new OBCMSCustomer();
                	obCustomer.setCustomerID(rs.getLong("partyId"));
                	obCustomer.setCustomerName(rs.getString("partyName"));
                	obCustomer.setCustomerSegment(rs.getString("Segment"));
                	obCustomer.setRegion(rs.getString("region"));
                return obCustomer;
            }
        }
        );
    	
    	if(resultList.size()>0){
    		return (ICMSCustomer) resultList.get(0);	
    		
    	}else{
    		return null;
    	}
		
	}

	public List getTransactionHistoryList(String transactionId) throws SearchDAOException {
		String sql = "select * from(select * from trans_history where transaction_id  = '"+transactionId+"'" +" order by tr_history_id desc) where rownum <=2";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[4];

				String theDate = "";
				String createDate = "";

				stringArray[0] = rs.getString("LOGIN_ID");
				try {
					theDate = rs.getTimestamp("TRANSACTION_DATE").toString();

				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					// createDate = rs.getTimestamp("CREATION_DATE").toString();
					createDate = rs.getTimestamp("TRANSACTION_DATE").toString();
				} catch (Exception e) {
					// TODO: handle exception
				}

				stringArray[1] = theDate;
				stringArray[2] = rs.getString("TO_STATE");
				stringArray[3] = createDate;
				return stringArray;

			}
		});
		
		/*if(resultList!=null && resultList.size()==1)
		{
			String[] list = new String[3];
			String[] stringArray = new String[3];
			list = (String[])resultList.get(0);
			resultList.remove(0);
		    stringArray[0] = "-";
		    stringArray[1] = "-";
		    stringArray[2] = "ACTIVE";
		    resultList.add(stringArray);
		    
		    resultList.add(list);
		}*/
		if(resultList!=null && resultList.size()==1)
		{
			//String[] list = new String[3];
			String[] stringArray = new String[4];
			// list = (String[])resultList.get(0);
			// resultList.remove(0);
			stringArray[0] = "-";
			stringArray[1] = "-";
			stringArray[2] = "-";
			stringArray[3] = "-";
			resultList.add(stringArray);

			// resultList.add(list);
		}
		return resultList;
	}

	public List getTransactionCAMHistoryList(String transactionId,String partyId) throws SearchDAOException {
		String sql = "select * from (select * from (select * from (select * from trans_history where transaction_id  = '"+transactionId+"'" + "order by tr_history_id desc) where rownum <=2" + 
		"					union all" + 
		"					select * from TRANS_HISTORY where TRANSACTION_ID=(" + 
		"					select TRANSACTION_ID from   TRANSACTION where REFERENCE_ID =" + 
		"					(select max(FILE_ID) from cms_partycam_file_upload where PARTY_ID = '"+partyId+"' ) ))order by tr_history_id desc) where rownum <= 2 ";
		
		
		

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[4];

				String theDate = "";
				String createDate = "";

				stringArray[0] = rs.getString("LOGIN_ID");
				try {
					theDate = rs.getTimestamp("TRANSACTION_DATE").toString();

				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					// createDate = rs.getTimestamp("CREATION_DATE").toString();
					createDate = rs.getTimestamp("TRANSACTION_DATE").toString();
				} catch (Exception e) {
					// TODO: handle exception
				}

				stringArray[1] = theDate;
				stringArray[2] = rs.getString("TO_STATE");
				stringArray[3] = createDate;
				return stringArray;

			}
		});
		
		if(resultList!=null && resultList.size()==1)
		{
			//String[] list = new String[3];
			String[] stringArray = new String[4];
			// list = (String[])resultList.get(0);
			// resultList.remove(0);
			stringArray[0] = "-";
			stringArray[1] = "-";
			stringArray[2] = "-";
			stringArray[3] = "-";
			resultList.add(stringArray);

			// resultList.add(list);
		}
		return resultList;
	}
	
	public boolean getPartyMigreted (String tableName , long key) throws Exception
	 {
	   //  tableName = "SCI_LE_SUB_PROFILE";
	     String sql = "SELECT count(*) as count  "+
	                  "  FROM  "+tableName+"" +
	                  " WHERE CMS_LE_SUB_PROFILE_ID = "+ key +" and ISMIGRATED = 'Y'"  ;
	  //   " WHERE CMS_COLLATERAL_ID = "+key+" and --ISMIGRATED = 'Y'"  ;
	     
	     String value = null;
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 dbUtil = new DBUtil();
				//println(strBuffer.toString());
				try {
					dbUtil.setSQL(strBuffer.toString());
					
				}
				catch (SQLException e) {
					throw new SearchDAOException("Could not set SQL query statement", e);
				}
				ResultSet rs = dbUtil.executeQuery();
				long  count = 0;
				//return count;
				while (rs.next()) {					
					count = Long.parseLong(rs.getString("count"));						
				}
				
	         if(count>0)
	         {
	         return true;
	         }
	         else
	         {
	        	 return false;
	         }
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve MIGRATED PARTY ", e);
	     }
	     finally{
	    	 dbUtil.close();
	     }
	 }
	
	
	public boolean getSublineCount (long subprofileID) throws Exception
	 {
	   //  tableName = "SCI_LE_SUB_PROFILE";
	   /*  String sql = "SELECT count(1) as count  "+
	                  "  FROM  SCI_LE_SUBLINE ln" +
	                 " WHERE ln.CMS_LE_SUBLINE_PARTY_ID = "+ subprofileID   ;
		*/
		
		String sql = "select count(1)as count from sci_le_sub_profile where cms_le_main_profile_id in "+
		" ( SELECT cms_le_main_profile_id "+
			                    " FROM  SCI_LE_SUBLINE ln"+
			                   " WHERE ln.CMS_LE_SUBLINE_PARTY_ID = "+ subprofileID +")"+
		                     " AND status = 'ACTIVE'"
		                     ;
	  //   " WHERE CMS_COLLATERAL_ID = "+key+" and --ISMIGRATED = 'Y'"  ;
	     
	     String value = null;
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 dbUtil = new DBUtil();
				//println(strBuffer.toString());
				try {
					dbUtil.setSQL(strBuffer.toString());
					
				}
				catch (SQLException e) {
					throw new SearchDAOException("Could not set SQL query statement", e);
				}
				ResultSet rs = dbUtil.executeQuery();
				long  count = 0;
				//return count;
				while (rs.next()) {					
					count = Long.parseLong(rs.getString("count"));						
				}
				
	         if(count>0)
	         {
	         return true;
	         }
	         else
	         {
	        	 return false;
	         }
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve SUBLINE details  ", e);
	     }
	     finally{
	    	 dbUtil.close();
	     }
	 }

		
	// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Starts
	
	public Date getCamStartDate(String llPLeIdForCam)
	{
		Date camStartDate=null;
		ResultSet rs = null;
		DBUtil dbUtil =null;
		String sql = "SELECT LLP_BCA_REF_APPR_DATE AS CAM_START_DATE FROM SCI_LSP_LMT_PROFILE where LLP_LE_ID ='"+llPLeIdForCam+"'" ;
				
				/*"SELECT  LLP_BCA_REF_APPR_DATE AS CAM_START_DATE FROM   "+
		             "CMS_LIMIT_SECURITY_MAP MAP , SCI_LSP_LMT_PROFILE PROF, SCI_LSP_APPR_LMTS LMTS WHERE   "+
		             "MAP.CMS_LSP_APPR_LMTS_ID = LMTS.CMS_LSP_APPR_LMTS_ID AND LMTS.CMS_LIMIT_PROFILE_ID = PROF.CMS_LSP_LMT_PROFILE_ID AND MAP.CMS_COLLATERAL_ID= "+collateralId;*/
				
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 dbUtil = new DBUtil();
			 dbUtil.setSQL(strBuffer.toString());
			 rs = dbUtil.executeQuery();
			 while (rs.next()) 
			 {					
				 camStartDate = rs.getDate("CAM_START_DATE");						
			 }
	         
	     }
	     catch(Exception e){}
	     finally{
	    	 try {
				dbUtil.close();
			} catch (SQLException e) {

			}
		}

		return camStartDate;
	}
	
	// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Ends

	//Added to get Party which has same PAN of Current Party
	public List<String> getPanDetails(String partyId,String pan){
		String sql="select lsp_short_name from sci_le_sub_profile where status='ACTIVE' and lsp_le_id!= ? and pan=?";  //need to chk whether party in inactive also chk from UI whether close and use subtable
		
		DefaultLogger.debug(this,  "Executing getPanDetails query:"+sql+"...partyID-"+partyId+"....pan-"+pan);
		
		
		DBUtil dbutil=null;
		ResultSet rs=null;
		List<String> duplicatePartyList=new ArrayList<String>();
		String party;
		try {
			dbutil = new DBUtil();
			dbutil.setSQL(sql);
			dbutil.setString(1,  partyId);
			dbutil.setString(2,  pan);
			rs = dbutil.executeQuery();
			while (rs.next()) {
				// int numberOfRecord=rs.getFetchSize()
				String name = rs.getString(1);
				duplicatePartyList.add(name);
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return duplicatePartyList;
	}
	//END:Added to get Party which has same PAN of Current Party

	
	//Uma Khot:start:Added to update wrong data of security 10000 records
	public void updateGcInsurance() throws Exception{
		try{
	   DefaultLogger.debug(this,"updateGcInsurance Start");
	   String sql="update CMS_STAGE_GC_INSURANCE set IS_PROCESSED='Y' where id in (select csgi.id from CMS_STAGE_GC_INSURANCE csgi ,CMS_GC_INSURANCE cgi"+
            	" where csgi.CMS_COLLATERAL_ID=cgi.CMS_COLLATERAL_ID and cgi.IS_PROCESSED='Y' and cgi.DEPRECATED='Y'" +
            	" and cgi.INS_CODE=csgi.INS_CODE and  csgi.DEPRECATED='Y' and csgi.is_processed='N') ";
	 getJdbcTemplate().update(sql);
	 DefaultLogger.debug(this,"updateGcInsurance End");
		}
		catch(Exception e){
			DefaultLogger.debug(this,"Error in updateGcInsurance:"+e.getMessage());
			throw new Exception(e);
		}
		return;
	}
	// Uma Khot:End:Added to update wrong data of security 10000 records

	public Boolean checkSystemExists(String systemName, String systemId) throws SQLException {

		Boolean isSystemAvailable = false;
		
		try{
		    String query = "SELECT count(1)  " +
				" FROM SCI_LE_OTHER_SYSTEM SYS, SCI_LE_SUB_PROFILE SP "+
				" where SYS.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE'" +
				" and SYS.CMS_LE_SYSTEM_NAME ='"+systemName+"' " +
				" and SYS.CMS_LE_OTHER_SYS_CUST_ID ='"+systemId+"' ";
		 
		    dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count >= 1) {
					isSystemAvailable = true;
				}
			}
			rs.close();
		 }
		 catch(Exception e)
		 {
			 isSystemAvailable = false;
			 e.printStackTrace();
			 throw new SQLException("Unable to retrieve data for method checkSystemExists()!!! "+e.getMessage());
		 }
		 finally{
			 dbUtil.close();
		 }
		 
		 return isSystemAvailable;
	}
	
	public Boolean checkSystemExistsInOtherParty(String systemName,String systemId,String partyId) throws SQLException {
		
		Boolean isSystemAvailable = false;
		
		try{
			String query = "SELECT count(1)  " +
			" FROM SCI_LE_OTHER_SYSTEM SYS, SCI_LE_SUB_PROFILE SP "+
			" where SYS.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE'" +
			" and SYS.CMS_LE_SYSTEM_NAME ='"+systemName+"' " +
			" and SYS.CMS_LE_OTHER_SYS_CUST_ID ='"+systemId+"' "
			+ " and SP.LSP_LE_id <> '"+partyId+"' ";
			
			dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count >= 1) {
					isSystemAvailable = true;
				}
			}
			rs.close();
		}
		catch(Exception e)
		{
			isSystemAvailable = false;
			e.printStackTrace();
			throw new SQLException("Unable to retrieve data for method checkSystemExistsInOtherParty()!!! "+e.getMessage());
		}
		finally{
			dbUtil.close();
		}

		return isSystemAvailable;
	}

	public Boolean checkVendorExists(String vendorName) throws SQLException {
	   	
		Boolean isVendorAvailable = false;
		
		try{
		    String query = "SELECT count(1)  " +
				" FROM SCI_LE_VENDOR_DETAILS VEN, SCI_LE_SUB_PROFILE SP "+
				" where VEN.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE'" +
				" and VEN.CMS_LE_VENDOR_NAME ='"+vendorName+"' ";
		 
		    dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();

			if(rs.next()){
				int count = rs.getInt(1);
				if(count>=1){
					isVendorAvailable = true;
				}
			}
			rs.close();
		 }
		 catch(Exception e)
		 {
			 isVendorAvailable = false;
			 e.printStackTrace();
			 throw new SQLException("Unable to retrieve data for method checkVendorExists()!!! "+e.getMessage());
		 }
		 finally{
			 dbUtil.close();
		 }
		 
		 return isVendorAvailable;
	}
	
	public Boolean checkVendorExistsInSameParty(String vendorName,String partyId) throws SQLException {
		
		Boolean isVendorAvailable = false;
		
		try{
			
			String query = " SELECT count(1) FROM SCI_LE_VENDOR_DETAILS VEN, SCI_LE_SUB_PROFILE SP " +
			" where VEN.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID and SP.STATUS = 'ACTIVE' " +
			" and VEN.CMS_LE_VENDOR_NAME ='"+vendorName+"' and SP.LSP_LE_id = '"+partyId+"' ";
			
			dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();
			
			if(rs.next()){
				int count = rs.getInt(1);
				if(count>=1){
					isVendorAvailable = true;
				}
			}
			rs.close();
		}
		catch(Exception e)
		{
			isVendorAvailable = false;
			e.printStackTrace();
			throw new SQLException("Unable to retrieve data for method checkVendorExistsInOtherParty()!!! "+e.getMessage());
		}
		finally{
			dbUtil.close();
		}
		
		return isVendorAvailable;
	}
	
	//Start:Uma:Valid Rating CR
	//Added to get Party which has same PAN of Current Party
	public Map<String,String> getPanDetails(String partyId){
		String sql="select lsp_short_name , pan from sci_le_sub_profile where status='ACTIVE' and lsp_le_id!= '"+partyId+"' and pan is not null";  //need to chk whether party in inactive also chk from UI whether close and use subtable
		
		DBUtil dbutil=null;
		ResultSet rs=null;
		Map<String,String> partyNamePanMap=new HashMap<String,String>();
		String party;
		try {
			dbutil = new DBUtil();
			dbutil.setSQL(sql);
			rs = dbutil.executeQuery();
			while (rs.next()) {
				// int numberOfRecord=rs.getFetchSize()
				String partyName = rs.getString(1);
				String pan = rs.getString(2);

				if (partyNamePanMap.containsKey(pan)) {
					String name = partyNamePanMap.get(pan);
					name = name + "," + partyName;
					partyNamePanMap.put(pan, name);

				} else {

					partyNamePanMap.put(pan, partyName);
				}
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return partyNamePanMap;
	}
	//End:Uma:Valid Rating CR
	
	//Below method is to validate existance of PAN details with other parties
	/*commented this because wanted to create parameterised method
	 * public List<String> checkIfPANExistsInOtherParty(String partyId,String pan) throws SearchDAOException, DBConnectionException, SQLException{
		
		StringBuffer query = new StringBuffer("select lsp_short_name from sci_le_sub_profile where status='ACTIVE'");

		if (partyId != null && !partyId.trim().isEmpty()) {
			query.append("and lsp_le_id!= '" + partyId + "'");
		}
		if (pan != null && !pan.trim().isEmpty()) {
			query.append("and pan='" + pan + "'");
		}

		List<String> listOfRecrods = new ArrayList<String>();

		listOfRecrods = getJdbcTemplate().query(query.toString(), new RowMapper() {
	            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	                return rs.getString("lsp_short_name");
	            }
	        }
	        );
		
		return listOfRecrods;
	}*/
	
	public List<String> checkIfPANExistsInOtherParty(String partyId, String pan) throws SearchDAOException, DBConnectionException, SQLException {
	    StringBuffer query = new StringBuffer("SELECT lsp_short_name FROM sci_le_sub_profile WHERE status='ACTIVE'");
	    ArrayList params = new ArrayList();
	    
	    if (partyId != null && !partyId.trim().isEmpty()) {
	        query.append(" AND lsp_le_id != ?");
	        params.add(partyId);
	    }
	    if (pan != null && !pan.trim().isEmpty()) {
	        query.append(" AND pan = ?");
	        params.add(pan);
	    }
	    
	    List<String> listOfRecords = new ArrayList<String>();
	    
	    listOfRecords = getJdbcTemplate().query(query.toString(), params.toArray(), new RowMapper() {
	        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return rs.getString("lsp_short_name");
	        }
	    });
	    
	    return listOfRecords;
	}

	public List getCollateralMappedCustomerLimitIdList(Long collateralId) throws Exception {
		List result = new ArrayList();
		try{
			String query =  "select CMS_LSP_LMT_PROFILE_ID from SCI_LSP_LMT_PROFILE where cms_customer_id in ("+
							"select cms_le_sub_profile_id from sci_le_sub_profile where status='ACTIVE' and CMS_LE_SUB_PROFILE_ID in ("+
							"select cms_customer_id from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID in ("+
							"select CMS_LIMIT_PROFILE_ID from sci_lsp_appr_lmts where CMS_LSP_APPR_LMTS_ID in ( "+
							"select CMS_LSP_APPR_LMTS_ID from CMS_LIMIT_SECURITY_MAP where CMS_COLLATERAL_ID='"+collateralId+"'"+
							"and update_status_ind='I') and cms_limit_status='ACTIVE')))";
			
			 result = getJdbcTemplate().query(query, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String stringArray = new String();
					stringArray = rs.getString("CMS_LSP_LMT_PROFILE_ID");

		                return stringArray;
		            }
		        }
		        );

		}
		catch(Exception ex){
			DefaultLogger.error(this, "############# error in getCollateralMappedCustomerLimitIdList",ex);
		}
		return result;
	}

	public String getSegment1(String partySegment){
		String sql="select entry_code from common_code_category_entry where category_code = 'SEGMENT_1' and entry_name='"+partySegment+"'";
		
		DefaultLogger.debug(this, "getSegment1 started");
		DefaultLogger.debug(this, "getSegment1 sql: " + sql);
		DBUtil dbutil = null;
		ResultSet rs = null;
		String segmentCode = "";
		try {
			dbutil = new DBUtil();
			dbutil.setSQL(sql);
			rs = dbutil.executeQuery();
			while (rs.next()) {
				segmentCode = rs.getString(1);
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, "getSegment1 completed segmentCode: " + segmentCode);
		return segmentCode;
	}

	
	
	public String getPartySegment(String limitProfileId){
		String sql="select lsp_sgmnt_code_value from sci_le_sub_profile  where lsp_le_id in (select llp_le_id from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id='"+limitProfileId+"')"; 
		
		DefaultLogger.debug(this, "getPartySegment started");
		DBUtil dbutil = null;
		ResultSet rs = null;
		String segmentCode = "";
		String segmentValue = "";
		try {
			dbutil = new DBUtil();
			dbutil.setSQL(sql);
			rs=dbutil.executeQuery();
			while(rs.next()){
				segmentCode=rs.getString(1);
				}
			
			if(null!= segmentCode && !"".equalsIgnoreCase(segmentCode)){
				
				String sqlQuery = "select entry_name from common_code_category_entry where category_code = 'HDFC_SEGMENT' and entry_code = '"+segmentCode+"'";
				dbutil=new DBUtil();
				dbutil.setSQL(sqlQuery);
				rs = dbutil.executeQuery();
				while (rs.next()) {
					segmentValue = rs.getString(1);
				}
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, "getPartySegment completed");
		return segmentValue;
	}
	
	public String getPartyCapitalMarExp(String limitProfileId){
		String sql="select is_capital_market_expos from sci_le_cri where cms_le_main_profile_id in (select cms_le_main_profile_id from sci_le_main_profile where lmp_le_id in (select llp_le_id from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id='"+limitProfileId+"'))";
		
		DefaultLogger.debug(this, "getPartyCapitalMarExp started");
		DBUtil dbutil = null;
		ResultSet rs = null;
		String capitalMarketExpo = "";
		try {
			dbutil = new DBUtil();
			dbutil.setSQL(sql);
			rs=dbutil.executeQuery();
			while(rs.next()){
				capitalMarketExpo=rs.getString(1);
				}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, "getPartyCapitalMarExp completed");
		return capitalMarketExpo;
	}
	
	public List<LabelValueBean> getMainLineCode(String limitProfileId,String subPartyName, String facilitySystem,String systemId){
		String sql="select line_no,serial_no from sci_lsp_sys_xref where CMS_LSP_SYS_XREF_ID in ( "+
"select CMS_LSP_SYS_XREF_ID from sci_lsp_lmts_xref_map where CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from SCI_LSP_APPR_LMTS "+
"where CMS_LIMIT_PROFILE_ID='"+limitProfileId+"' and  cms_limit_status='ACTIVE' and (LMT_TYPE_VALUE = 'No' or LMT_TYPE_VALUE is null)))" +
		" and (action <> 'NEW' or action is null)";
		
		String sql2="select distinct line_no ,serial_no from ( "+
				" select line_no,serial_no from sci_lsp_sys_xref where CMS_LSP_SYS_XREF_ID in ( "+
				" select CMS_LSP_SYS_XREF_ID from sci_lsp_lmts_xref_map where CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from SCI_LSP_APPR_LMTS "+
				" where CMS_LIMIT_PROFILE_ID='"+limitProfileId+"' and  cms_limit_status='ACTIVE' and (LMT_TYPE_VALUE = 'No' or LMT_TYPE_VALUE is null))) "+
				"  and (action <> 'NEW' or action is null) "+
				" union all   "+ 
				" select line_no,serial_no from sci_lsp_sys_xref where CMS_LSP_SYS_XREF_ID in (  "+
				" select CMS_LSP_SYS_XREF_ID from sci_lsp_lmts_xref_map where CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from SCI_LSP_APPR_LMTS  "+
				" where CMS_LIMIT_PROFILE_ID in (select CMS_LSP_LMT_PROFILE_ID from sci_lsp_lmt_profile where  CMS_CUSTOMER_ID in (select CMS_LE_SUB_PROFILE_ID  "+
				" from sci_le_sub_profile where CMS_LE_SUB_PROFILE_ID='"+subPartyName+"')) and  cms_limit_status='ACTIVE' and (LMT_TYPE_VALUE = 'No' or LMT_TYPE_VALUE is null))) "+
				"  and (action <> 'NEW' or action is null) and facility_system='"+facilitySystem+"' and facility_system_id='"+systemId+"') ";
		
		DefaultLogger.debug(this, "getMainLineCode started");
		DBUtil dbutil = null;
		ResultSet rs = null;
		List<LabelValueBean> mainLineCodeList = new ArrayList<LabelValueBean>();
		try {
			dbutil = new DBUtil();

			if (!"".equals(subPartyName) && !"".equals(facilitySystem) && !"".equals(systemId)) {
				dbutil.setSQL(sql2);
			} else {
				dbutil.setSQL(sql);
			}
			rs = dbutil.executeQuery();
			while (rs.next()) {
				String lineNo = "";
				String serialNo = "";

				if (null != rs.getString(1)) {
					lineNo = rs.getString(1);
				}
				if (null != rs.getString(2)) {
					serialNo = rs.getString(2);
				}
			
			String mainLineCode=lineNo+serialNo;
			LabelValueBean lb =new LabelValueBean(mainLineCode,mainLineCode);
			mainLineCodeList.add(lb);
		}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, "getMainLineCode completed");
		return mainLineCodeList;
	}

	public String getIfscCodeList(String table, String id) {
		String query = "";
		if ("stage".equals(table))
			query = "select id from CMS_STAGE_IFSC_CODE where cms_le_main_profile_id='" + id + "'";
		else
			query = "select id from CMS_IFSC_CODE where cms_le_main_profile_id='" + id + "'";

		String ifscCodeList = "";
		DefaultLogger.debug(this, "inside getIfscCodeList");

		try {
			List ifscList = getJdbcTemplate().queryForList(query);
			for (int i = 0; i < ifscList.size(); i++) {
				Map map = (Map) ifscList.get(i);
				String ifscId = map.get("id").toString();

				if (i == ifscList.size() - 1) {
					ifscCodeList = ifscCodeList + ifscId.trim();
				} else {
					ifscCodeList = ifscCodeList + ifscId.trim() + ",";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  getIfscCodeList:" + e.getMessage());
		}
		DefaultLogger.debug(this, "completed getIfscCodeList");

		return ifscCodeList;
	}

	@Override
	public List getAllRamratingDocumentlist() {
		String query = "";

		query = "select document_code,document_description from cms_document_globallist where statement_type='RAM_RATING'";

		List resultList = getJdbcTemplate().query(query, new RowMapper() {

	            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	           	 
	           	 OBSystem result = new OBSystem();
	    			
	    			result.setSystem(rs
	    					.getString("document_code"));
	    			result.setSystemCustomerId(rs
	    					.getString("document_description"));
	    			
	                return result;
	            }
	        }
	        );

		return resultList;

	}
	
	@Override
	public String getRamYear(String llpLeId) {
		String query = "SELECT RATING_YEAR FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID=?";
		return (String) getJdbcTemplate().query(query, new Object[]{(llpLeId)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "";
			}
		});
	}
	
	@Override
	public void updateRamDueDate(String checkListId,String ramDueDate,String status){
		String queryStr="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
		 queryStr = "update cms_checklist_item set status='"+status+"',EXPIRY_DATE='"+ramDueDate+"' where checklist_id='"+checkListId+"' and statement_type='RAM_RATING'";
		 System.out.println("IN updateRamDueDate() sql: "+queryStr);
		 
		 dbUtil=new DBUtil();
		 dbUtil.setSQL(queryStr);
		 int noOfRecords=dbUtil.executeUpdate();
		 System.out.println("Out from updateRamDueDate()"+noOfRecords);
		}  catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in updateRamDueDate:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in updateRamDueDate:" + e.getMessage());
		} finally {
			try {
				finalize(dbUtil, rs);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	
	@Override
	public String getChecklistId(long limitProfileID) {
	
			String queryStr = "select checklist_id from cms_checklist where cms_lsp_lmt_profile_id=? and CATEGORY='REC' ";
				
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(limitProfileID)},
					new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "";
			}
		});

	}

	
	@Override
	public String getLimitProfileID(String partyID) {
	
			String queryStr = "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE where LLP_LE_ID=? ";
				
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(partyID)},
					new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "";
			}
		});

	}

	public String getPartyId(String limitProfileIDStr){
		String partyId=null;
		String partyIdSql="select LLP_LE_ID from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id='"+limitProfileIDStr+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(partyIdSql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				partyId = rs.getString(1);

			}
		} catch (Exception e) {
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return partyId;
	}
	
	public int checkPanStatus(String partyId,String panNo,String partyNameAsPerPan, String dateOfIncorporation){
		String panSql= "select count(1) from PAN_VALIDATION_LOG where panno ='"+panNo+"' and partyId ='"+partyId+"' and status = 'Success' "
				+ " and ispannovalidated = 'Y' and partyNameAsPerPan ='"+partyNameAsPerPan+"' and dateOfIncorporation ='"+dateOfIncorporation+"'";
		System.out.println("CheckPanStatus"+panSql);
		int count = 0;
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(panSql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
				System.out.println("CheckPanStatus count " + count);

			}
		} catch (Exception e) {
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				dbUtil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public int checkLeiStatus(String partyId,String leiCode){
		String leiSql= "select count(1) from LEI_VALIDATION_LOG where leiCode ='"+leiCode+"' and partyId ='"+partyId+"' and status = 'Success' and isLEICodeValidated = 'Y'";
		System.out.println("CheckLeiStatus"+leiSql);
		int count = 0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(leiSql);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
				System.out.println("CheckLeiStatus count "+count);
				
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	

	public void updatePartyRMDetails(String empCode, IRegion region) {
		// String empCode = data[0];
		Long rmRegion = 0L;
		// IRegion region = getRegionByRegionName(data[10]);
		if (null != region) {
			rmRegion = region.getIdRegion();
		}
		DBUtil dbUtil1 = null;
		DBUtil dbUtil2 = null;
		try {
			String query1 = "update SCI_LE_SUB_PROFILE set RM_REGION = '"+rmRegion+"' where RELATION_MGR_EMP_CODE = '"+empCode+"'"; 
			String query2 =	"update SCI_LE_MAIN_PROFILE set RM_REGION = '"+rmRegion+"' where RELATION_MGR_EMP_CODE = '"+empCode+"'";
			
	
			 
			 dbUtil1=new DBUtil();
			 dbUtil1.setSQL(query1);
			 dbUtil1.executeUpdate();

			dbUtil2 = new DBUtil();
			dbUtil2.setSQL(query2);
			dbUtil2.executeUpdate();

			// getJdbcTemplate().update(query1);
			// getJdbcTemplate().update(query2);
			// getHibernateTemplate().bulkUpdate(query1);
			// getHibernateTemplate().bulkUpdate(query2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dbUtil1 != null) {
					dbUtil1.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (dbUtil2 != null) {
					dbUtil2.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void updateSanctionedAmountUpdatedFlag(String partyId, String updateFlag) {
		DefaultLogger.info(this, "Updating Sanctioned Amount Flag for partyId :"+partyId+" | updateFlag :"+updateFlag);
		if(StringUtils.isNotBlank(updateFlag) && StringUtils.isNotBlank(partyId)) {
			String actualQuery = " update Sci_Le_Sub_Profile set SANCTIONED_AMT_UPDATED_FLAG =? where LSP_LE_ID = ? ";
			try {
				this.getJdbcTemplate().update(actualQuery, new Object[] {updateFlag, partyId});	
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception caught in updateSanctionedAmountUpdatedFlag: "+e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	public String getSanctionedAmtUpdatedFlag(long customerId) {
		if (customerId > 0) {
			String query = "Select SANCTIONED_AMT_UPDATED_FLAG From Sci_Le_Sub_Profile Where CMS_LE_SUB_PROFILE_ID = ?";
			return (String) getJdbcTemplate().queryForObject(query, new Object[] { customerId }, String.class);
		}
		return null;
	}

	public String getCustomerSegmentByLimitProfileId(long limitProfileId) {
		if (limitProfileId > 0) {
			StringBuffer sb = new StringBuffer()
							  .append("Select Sub_Prof.Lsp_Sgmnt_Code_Value From Sci_Lsp_Lmt_Profile Lmt_Prof ")
							  .append("Inner Join Sci_Le_Sub_Profile Sub_Prof on Sub_Prof.CMS_LE_SUB_PROFILE_ID = Lmt_Prof.CMS_CUSTOMER_ID ")
							  .append("where lmt_prof.CMS_LSP_LMT_PROFILE_ID = ? ");
							  
			return (String) getJdbcTemplate().queryForObject(sb.toString(), new Object[] {limitProfileId},String.class );
		}
		return null;
	}

	
	public String getCustomerSegmentByCustomerId(long customerId) {
		if(customerId>0) {
			StringBuffer sb = new StringBuffer()
							  .append("Select Sub_Prof.Lsp_Sgmnt_Code_Value From Sci_Lsp_Lmt_Profile Lmt_Prof ")
							  .append("Inner Join Sci_Le_Sub_Profile Sub_Prof on Sub_Prof.CMS_LE_SUB_PROFILE_ID = Lmt_Prof.CMS_CUSTOMER_ID ")
							  .append("and Lmt_Prof.CMS_CUSTOMER_ID  = ? ");
							  
			return (String) getJdbcTemplate().queryForObject(sb.toString(), new Object[] {customerId},String.class );
		}
		return null;
	}

	@Override
	public List<String> getCoBorrowerLiabId(String partyId) {
		if (StringUtils.isBlank(partyId))
			return Collections.emptyList();
		
		String sql = "SELECT bor.co_borrower_liab_id FROM stage_sci_le_co_borrower bor " + 
				"INNER JOIN stage_sci_le_sub_profile sub ON sub.cms_le_main_profile_id = bor.cms_le_main_profile_id " + 
				"INNER JOIN transaction tr ON tr.staging_reference_id = sub.cms_le_sub_profile_id AND transaction_type='CUSTOMER' " + 
				"where sub.lsp_le_id=?";

		return getJdbcTemplate().query(sql, new Object[] { partyId }, new RowMapper() {

			@Override
			public String mapRow(ResultSet rs, int rn) throws SQLException {
				return rs.getString("co_borrower_liab_id");
			}
		});
	}

	public List getCoBorrowerListWS(String partyId) throws SearchDAOException {
		String sql = "SELECT CO_BORROWER_LIAB_ID, CO_BORROWER_NAME FROM SCI_LE_CO_BORROWER coBorrower, "
				+ " SCI_LE_SUB_PROFILE sub_profile"
				+ "	WHERE coBorrower.CMS_LE_MAIN_PROFILE_ID=sub_profile.CMS_LE_MAIN_PROFILE_ID "
				+ "	and sub_profile.LSP_LE_ID =?";

		List resultList = getJdbcTemplate().query(sql, new Object[] { new String(partyId) }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CoBorrowerDetailsRequestDTO result = new CoBorrowerDetailsRequestDTO();

				result.setCoBorrowerLiabId(rs.getString("CO_BORROWER_LIAB_ID"));
				result.setCoBorrowerName(rs.getString("CO_BORROWER_NAME"));
				return result;
			}
		});

		return resultList;
	}
	

	
	/*
	 * udfFlagQuery=
	 * select udf.UDF98 from SCI_LE_UDF udf 
	 * left join SCI_LE_MAIN_PROFILE party 
	 * on udf.CMS_LE_MAIN_PROFILE_ID=party.CMS_LE_MAIN_PROFILE_ID where party.LMP_LE_ID = 'CUS0004412';
	 */
	
	public String getEcbfFlagUsingMainProfileId(String partyId) {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String udfName = bundle.getString("psl.upload.ecbf.udf");

		String sqlUdf = "select "+udfName+" from SCI_LE_UDF udf "
				+ "left join SCI_LE_MAIN_PROFILE party "
				+ "on udf.CMS_LE_MAIN_PROFILE_ID = party.CMS_LE_MAIN_PROFILE_ID "
				+ "where party.LMP_LE_ID = ?";
		
		try {		
			return (String) getJdbcTemplate().queryForObject(sqlUdf, new Object[] { partyId }, String.class);		
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	public String getScfFlagUsingMainProfileId(String partyId) {

		String sqlUdf = "select UDF97 from SCI_LE_UDF udf "
				+ "left join SCI_LE_MAIN_PROFILE party "
				+ "on udf.CMS_LE_MAIN_PROFILE_ID = party.CMS_LE_MAIN_PROFILE_ID "
				+ "where party.LMP_LE_ID = ?";
		
		try {		
			return (String) getJdbcTemplate().queryForObject(sqlUdf, new Object[] { partyId }, String.class);		
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	public String getEcbfLineUsingSID(String sID) {
		String sql_lineId = "select MAX(CMS_LSP_LMTS_XREF_MAP_ID) AS CMS_LSP_LMTS_XREF_MAP_ID from SCI_LSP_LMTS_XREF_MAP where CMS_SID = ? AND CMS_STATUS = 'ACTIVE' ";
		return (String) getJdbcTemplate().queryForObject(sql_lineId, new Object[] { sID }, String.class);
	}


	
	
	public int getSIDCount(long sID) {
		int count = 0;
		DefaultLogger.debug(this, "calling getSIDCount");

		try {
			String sql = "select count(*) from SCI_LSP_LMTS_XREF_MAP where CMS_SID = ? AND CMS_STATUS = 'ACTIVE' ";
			count = getJdbcTemplate().queryForInt(sql, new Object[] { sID });
			DefaultLogger.debug(this, "completed getSIDCount count:" + count);

		} catch (Exception ex) {
			DefaultLogger.debug(this, "Exception in getSIDCount:" + ex.getMessage());
			ex.printStackTrace();
		}
		return count;
	}
	
	
	
	public int getLineCount(String lineId) {
		int count = 0;
		DefaultLogger.debug(this, "calling getLineCount");

		try {
			String sql = "select count(*) from CMS_ECBF_LMT_INTERFACE_LOG where LINEID = ? ";
			count = getJdbcTemplate().queryForInt(sql, new Object[] { lineId });
			DefaultLogger.debug(this, "completed getLineCount count:" + count);

		} catch (Exception ex) {
			DefaultLogger.debug(this, "Exception in getLineCount:" + ex.getMessage());
			ex.printStackTrace();
		}
		return count;
	}



	private String dateFormater(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(d.toString());
			System.out.println("Date is: " + date);
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
			String strDate = formatter1.format(date);
			System.out.println("Date Format with MM/dd/yyyy : " + strDate);
			return strDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public String getCamtypeForParty(String legalName) {
		String sql= "SELECT  " + 
				"SLLP.CAM_TYPE AS CAM_TYPE " + 
				"FROM  " + 
				"SCI_LE_SUB_PROFILE SLSP , " + 
				"SCI_LSP_LMT_PROFILE SLLP " + 
				"WHERE  " + 
				"SLSP.LSP_SHORT_NAME = '"+legalName+"' " + 
				"AND SLSP.LSP_LE_ID = SLLP.LLP_LE_ID ";
		
		String camType = "";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				camType = rs.getString("CAM_TYPE");
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Exception caught in getCamtypeForParty(String legalName). ==> e ==>"+e);
				e.printStackTrace();
			}
			catch (Exception e) {
				System.out.println("Exception caught in getCamtypeForParty(String legalName). ==> e =>"+e);
				e.printStackTrace();
			}
		}
		return camType;
	}
	
		public List getUpdatedResReqUsingLineId(String lineId) {
		String sql = "select RESPONSEDATETIME, REQUESTDATETIME from CMS_ECBF_LMT_INTERFACE_LOG where LINEID = ?  order by id desc fetch FIRST row only";



		List resultList = getJdbcTemplate().query(sql, new Object[] { new String(lineId) }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Date resDate, reqDate;
				String res, req;
				String[] resreq = new String[2];
				resDate = rs.getDate(1);
				res = dateFormater(resDate);
				resreq[0] = res;

				reqDate = rs.getDate(2);
				req = dateFormater(reqDate);
				resreq[1] = req;

				return resreq;
			}
		});
		return resultList;
	
		}
		




	
	


	
	
	
	public List getScfAndEcbfStatusById(final String module, String type, String id) { // module-scf/ecbf
																						// type-cust/line
																						// id-partyId/lineId

		String sql = "";

		if (module.equals(ICMSConstant.SCF)) {
			if (type.equals(ICMSConstant.CUSTOMER)) {
				sql = "select STATUS, ERRORMESSAGE, SCMFLAG from CMS_JS_INTERFACE_LOG where MODULENAME = 'CUSTOMER' and PARTYID=?  order by id desc FETCH FIRST 1 ROW ONLY ";
			} 
			/*else if (type.equals(ICMSConstant.LINE)) {
				sql = "select STATUS, ERRORMESSAGE, SCMFLAG from CMS_JS_INTERFACE_LOG where MODULENAME not in ('CUSTOMER', 'CAM') and PARTYID=? "
						+ "order by id desc FETCH FIRST 1 ROW ONLY ";
			}*/

		} else if (module.equals(ICMSConstant.ECBF)) {

			if (type.equals(ICMSConstant.CUSTOMER)) { // should not be hardparse query, convert in parameterised query
				sql = "select INTERFACESTATUS, ERRORMESSAGE  from CMS_ECBF_CUST_INTERFACE_LOG where PARTYID=? order by id desc FETCH FIRST ROW ONLY ";
			}
		}
		System.out.println("_________Module Name_________:" + module + "_________Query_________:" + sql);

		List resultList = getJdbcTemplate().query(sql, new Object[] { new String(id) }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				String[] statusMsgFlag = null;

				if (module.equals(ICMSConstant.SCF)) {
					statusMsgFlag = new String[3];
					statusMsgFlag[0] = rs.getString(1);
					statusMsgFlag[1] = rs.getString(2);
					statusMsgFlag[2] = rs.getString(3);

				} else if (module.equals(ICMSConstant.ECBF)) {
					statusMsgFlag = new String[2];
					statusMsgFlag[0] = rs.getString(1);
					statusMsgFlag[1] = rs.getString(2);
				}
				return statusMsgFlag;
			}
		});

		return resultList;

	}	

	public Date getCamExpiryORExtensionDate(int val , String llPLeIdForCam)
	{
		Date camExpiryOrExtensionDate=null;
		ResultSet rs = null;
		DBUtil dbUtil =null;
		String sql = "" ;
		if(val == 1) {
		sql = "SELECT LLP_NEXT_ANNL_RVW_DATE as expiry_date FROM SCI_LSP_LMT_PROFILE where LLP_LE_ID ='"+llPLeIdForCam+"'" ;
		}else {
		sql = "SELECT LLP_EXTD_NEXT_RVW_DATE as extension_date FROM SCI_LSP_LMT_PROFILE where LLP_LE_ID ='"+llPLeIdForCam+"'" ;
		}
				
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 dbUtil = new DBUtil();
			 dbUtil.setSQL(strBuffer.toString());
			 rs = dbUtil.executeQuery();
			 while (rs.next()) 
			 {		if(val == 1) {			
				 camExpiryOrExtensionDate = rs.getDate("expiry_date");	
			 }else {
				 camExpiryOrExtensionDate = rs.getDate("extension_date");	
			 }
			 }
	         
	     }
	     catch(Exception e){
	    	 System.out.println("Exception in getCamExpiryORExtensionDate(int val , String llPLeIdForCam)=>e=>"+e);
	     }
	     finally{
	    	 try {
				dbUtil.close();
			} catch (SQLException e) {
				System.out.println("Exception in getCamExpiryORExtensionDate(int val , String llPLeIdForCam) line 3465=>e=>"+e);
			}
	     }
	 
		return camExpiryOrExtensionDate;
	}

	
	public List getCoBorrowerListWSRest(String partyId) throws SearchDAOException {
		String sql = "SELECT CO_BORROWER_LIAB_ID, CO_BORROWER_NAME FROM SCI_LE_CO_BORROWER coBorrower, "
				+ " SCI_LE_SUB_PROFILE sub_profile"
				+"	WHERE coBorrower.CMS_LE_MAIN_PROFILE_ID=sub_profile.CMS_LE_MAIN_PROFILE_ID "
				+"	and sub_profile.LSP_LE_ID =?";

		 List resultList = getJdbcTemplate().query(sql,new Object[] { new String(partyId) }, new RowMapper() {

             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 CoBorrowerDetailsRestRequestDTO result = new CoBorrowerDetailsRestRequestDTO();
     			
            	 result.setCoBorrowerLiabId(rs.getString("CO_BORROWER_LIAB_ID"));
     			result.setCoBorrowerName(rs.getString("CO_BORROWER_NAME"));
     			 return result;
             }
         }
         );

         return resultList;
	}
	
	
	@Override
	public String getDocumentCount() {
		try {
			String queryStr = "select COUNT(1) AS CNT from CMS_DOCUMENT_GLOBALLIST ";
			System.out.println("Inside getDocumentCount=>queryStr=>"+queryStr);
			
			return (String) getJdbcTemplate().query(queryStr,
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "";
				}
			});
		}catch(Exception e) {
			System.out.println("Exception while fetching Seq getDocumentCount...query empty.e=>"+e);
			e.printStackTrace();
		}
		return "";
			
		}
	
	
	@Override
	public String getfileuploadidFromSeq() {
	
		try {
			String queryStr = "select concat (to_char(sysdate,'YYYYMMDD'), LPAD(CMS_FILE_UPLOAD_SEQ.nextval, 9,'0')) sequence from dual ";
			System.out.println("Inside getfileuploadidFromSeq=>queryStr=>"+queryStr);
			return (String) getJdbcTemplate().query(queryStr,
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "";
				}
			});
		}catch(Exception e) {
			System.out.println("Exception while fetching Seq getfileuploadidFromSeq...query empty.e=>"+e);
			e.printStackTrace();
		}
		return "";
			
		}
	
		
	}