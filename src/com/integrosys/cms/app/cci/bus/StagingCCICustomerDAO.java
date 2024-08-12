package com.integrosys.cms.app.cci.bus;

import com.integrosys.cms.app.customer.bus.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class StagingCCICustomerDAO extends CCICustomerDAO  implements ICCICustomerDAO {


    /**
     * Default Constructor
     */
    public StagingCCICustomerDAO() {
    }



     public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws SearchDAOException {


//         System.out.println("getCCICounterpartyByGroupCCINo  inSIDE " + groupCCINo);

         ICCICounterpartyDetails counterPartyDetails =null;
         StringBuffer querySQL = new StringBuffer();
         ResultSet rs = null;
         DBUtil util =null;
         SQLParameter params = SQLParameter.getInstance();

         querySQL.append(SELECT_CCI_PART_STAGING);
         querySQL.append(FROM_WHERE_CCI_STAGING);
         querySQL.append("AND CMS_CCI_NO");
         querySQL.append(" = ? ");
         querySQL.append(" ) ");
         params.addLong(new Long(groupCCINo));

          DefaultLogger.debug(this, "getStagingCCICounterpartyDetails  :\n " + querySQL.toString());

         try {
           util = new DBUtil();
           util.setSQL(querySQL.toString());
           CommonUtil.setSQLParams(params, util);
           rs = util.executeQuery();
           counterPartyDetails = this.processCCICounterpartyDetails(rs) ;

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in searchCCICustomer", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in searchCCICustomer", ex);
        } finally {
            finalize(util,rs) ;
        }

         return counterPartyDetails;
     }


    public ICCICounterpartyDetails processCCICounterpartyDetails(ResultSet rs) throws SQLException {

             ICCICounterpartyDetails  counterPartyDetails = null;
             ICCICounterparty[]  iCCICounterparty = null;
             List  iCCICounterpartyList = new ArrayList();
             long  CCINo = ICMSConstant.LONG_INVALID_VALUE;
             long  groupCCINoRef = ICMSConstant.LONG_INVALID_VALUE;

                    while (rs.next()){
                        ICCICounterparty obj = new OBCCICounterparty();
                        CCINo = rs.getLong("CMS_CCI_NO");
                        groupCCINoRef = rs.getLong("CCI_REF_ID");
                        obj.setGroupCCIMapID(rs.getLong("CCI_MAP_ID"));
                        obj.setVersionTime(rs.getLong("VERSION_TIME"));
                        String delInd = rs.getString("IS_DELETED");
                        if (null !=delInd && !"".equals(delInd) && delInd.equals("Y")){
                           obj.setDeletedInd(true);
                        }
                        obj.setGroupCCINo(CCINo);
                        obj.setGroupCCINoRef(groupCCINoRef);

                        obj.setLegalName(rs.getString(LEGAL_NAME));
                        //obj.setDob(formatDate(rs.getDate("LMP_INC_DATE")));
                        obj.setDob(rs.getTimestamp("LMP_INC_DATE"));
                        obj.setLegalID(rs.getLong(LEGAL_ID));
                        obj.setCustomerName(rs.getString(CUSTOMER_NAME));
                        obj.setSubProfileID(rs.getLong(CUSTOMER_ID));
                        obj.setSourceID(rs.getString("SOURCE_ID"));
                        obj.setLmpLeID(rs.getString("LMP_LE_ID"));
//                        obj.setLimitProfileID(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
                        //obj.setIdNO(rs.getString("LMP_ID_NUM"));
                        //if (rs.getString("LMP_ID_NUM") == null && "".equals(rs.getString("LMP_ID_NUM"))) {
                            obj.setIdNO(rs.getString("LMP_INC_NUM_TEXT"));
                        //}
                       iCCICounterpartyList.add(obj);
                     }
                  if (iCCICounterpartyList.size() > 0)  {
                      iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                      counterPartyDetails = new OBCCICounterpartyDetails();
                      counterPartyDetails.setICCICounterparty(iCCICounterparty);
                      counterPartyDetails.setGroupCCINo(CCINo);
                      counterPartyDetails.setGroupCCINoRef(groupCCINoRef);
                      counterPartyDetails.setStagingGroupCCINoRef(groupCCINoRef);
                  }
                 return counterPartyDetails ;
           }



     public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef) throws SearchDAOException {


//         System.out.println("getCCICounterpartyByGroupCCINoRef  inSIDE groupCCINoRef " + groupCCINoRef);

         ICCICounterpartyDetails counterPartyDetails =null;
         StringBuffer querySQL = new StringBuffer();
         ResultSet rs = null;
         DBUtil util =null;
         SQLParameter params = SQLParameter.getInstance();

         querySQL.append(SELECT_CCI_PART_STAGING);
         querySQL.append(FROM_WHERE_CCI_STAGING);
         querySQL.append("AND CCI_REF_ID");
         querySQL.append(" = ? ");
         querySQL.append(" ) ");
         params.addLong(new Long(groupCCINoRef));

          DefaultLogger.debug(this, "getCCICounterpartyByGroupCCINoRef  :\n " + querySQL.toString());

         try {
           util = new DBUtil();
           util.setSQL(querySQL.toString());
           CommonUtil.setSQLParams(params, util);
           rs = util.executeQuery();
           counterPartyDetails = this.processCCICounterpartyDetails(rs) ;

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCCICounterpartyByGroupCCINoRef", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCCICounterpartyByGroupCCINoRef", ex);
        } finally {
            finalize(util,rs) ;
        }

         return counterPartyDetails;
     }



}
