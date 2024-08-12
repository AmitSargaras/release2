/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/bus/RegionDAO.java,v 1.2 2005/09/19 09:02:07 czhou Exp $
 */

package com.integrosys.cms.app.concentrationreport.bus;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Represents a region DAO.
 * @author $Author: czhou $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */
public class RegionDAO implements IRegionDAO {

	/**
	 * Gets all the region information.
	 * @return An array of information for each region. This array can be zero
	 *         in length.
	 */
	public IRegion[] getAllRegions() {

		// Hold the region info temporarily.
		List list = new ArrayList();
		DBUtil dbUtil =null;
		ResultSet resultSet =null;
		try {
			dbUtil = new DBUtil();
			String query = "SELECT " + COL_REGION_CODE + ", " + COL_REGION_NAME + ", " + COL_REGION_DESCRIPTION
					+ " FROM " + TBL_REGION;

			dbUtil.setSQL(query);

			resultSet = dbUtil.executeQuery();

			IRegion region = null;
			while (resultSet.next()) {
				region = new OBRegion();
				region.setCode(resultSet.getString(COL_REGION_CODE));
				region.setName(resultSet.getString(COL_REGION_NAME));
				region.setDescription(resultSet.getString(COL_REGION_DESCRIPTION));
				list.add(region);
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "exception when querying region table", e);
			// Continue to return.
		}
		finalize(dbUtil,resultSet);

		return (IRegion[]) list.toArray(new IRegion[0]);
	}
	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String COL_REGION_CODE = "REGION_CODE";

	private static final String COL_REGION_NAME = "REGION_NAME";

	private static final String COL_REGION_DESCRIPTION = "REGION_DESCRIPTION";

	private static final String TBL_REGION = "REGION";
}
