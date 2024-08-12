package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public interface ISegmentWiseEmailJdbc {
	
	SearchResult getAllSegmentWiseEmail()throws SegmentWiseEmailException;

	List getAllSegment()throws SegmentWiseEmailException;

	SearchResult getSegmentWiseEmail(String segment)throws SegmentWiseEmailException;
	
	public void insertDataToEmailTable(String refId,List emails,String segment)throws SQLException;
	
	public SearchResult getStageEmail(long id) throws SegmentWiseEmailException;
	
	public void insertDataToActualEmailTable(String refsId,List<OBSegmentWiseEmail> objList)throws SQLException;
	
	public void deleteStageEmailIDs(String refId,List emails,String segment)throws SQLException;
	
	public void deleteActualEmailIDs(String refsId,List<OBSegmentWiseEmail> objList)throws SQLException;
	
	public long getSegmentId(String segment)throws SearchDAOException;
	
}
