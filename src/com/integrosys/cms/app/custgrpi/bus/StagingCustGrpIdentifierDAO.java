package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.OBCCICounterparty;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StagingCustGrpIdentifierDAO extends CustGrpIdentifierDAO implements ICustGrpIdentifierDAO {


    /**
     * Default Constructor
     */
    public StagingCustGrpIdentifierDAO() {
    }

    public IGroupSubLimit[] getGroupSubLimit(long grpID) throws SearchDAOException {

        List list = new ArrayList();
        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();

        querySQL.append("SELECT * FROM CMS_STAGE_GROUP_SUBLIMIT ");
        querySQL.append(" where GRP_ID = ?  ");
        params.addLong(new Long(grpID));
        DBUtil dbUtil = null;
        ResultSet rs = null;

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                IGroupSubLimit obj = new OBGroupSubLimit();
                obj.setGroupSubLimitID(rs.getLong("GRP_SUBLIMIT_ID"));
                obj.setGrpID(rs.getLong("GRP_ID"));
                obj.setSubLimitTypeCD(rs.getString("SUB_LIMIT_TYPE_CD"));
                obj.setDescription(rs.getString("DESCRIPTION"));
                obj.setRemarks(rs.getString("REMARKS"));
                list.add(obj);
            }
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return (IGroupSubLimit[]) list.toArray(new IGroupSubLimit[0]);

    }

    public IGroupOtrLimit[] getGroupOtrLimit(long grpID) throws SearchDAOException {

        List list = new ArrayList();
        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();

        querySQL.append("SELECT * FROM CMS_STAGE_GROUP_OTRLIMIT ");
        querySQL.append(" where GRP_ID = ?  ");
        params.addLong(new Long(grpID));
        DBUtil dbUtil = null;
        ResultSet rs = null;

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                IGroupOtrLimit obj = new OBGroupOtrLimit();
                obj.setGroupOtrLimitID(rs.getLong("GRP_OTRLIMIT_ID"));
                obj.setGrpID(rs.getLong("GRP_ID"));
                obj.setOtrLimitTypeCD(rs.getString("OTR_LIMIT_TYPE_CD"));
                obj.setDescription(rs.getString("DESCRIPTION"));
                obj.setRemarks(rs.getString("REMARKS"));
                list.add(obj);
            }

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return (IGroupOtrLimit[]) list.toArray(new IGroupOtrLimit[0]);

    }

    protected static final String SEELCT_GROUP_SQL = "SELECT * FROM CMS_STAGE_CUST_GRP WHERE WHERE IS_MASTER_GRP<>'Y' AND GRP_ID= ?";
}
