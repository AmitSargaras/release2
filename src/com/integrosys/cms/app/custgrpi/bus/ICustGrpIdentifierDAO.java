package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public interface ICustGrpIdentifierDAO {

    public IGroupSubLimit[] getGroupSubLimit(long grpID) throws SearchDAOException;

    public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria) throws SearchDAOException;

    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria) throws SearchDAOException;

    public IGroupMember setCustomerDetails(IGroupMember obj) throws SearchDAOException;

    public IGroupMember setGroupDetails(IGroupMember obj) throws SearchDAOException;

    public Map getGroupAccountMgr(Map map) throws SearchDAOException;

    public IGroupOtrLimit[] getGroupOtrLimit(long grpID) throws SearchDAOException;

    public Amount getGroupLimit(String intLmt, String rating) throws SearchDAOException;
	
	public List retrieveBGELGroup(long subprofileID) throws SearchDAOException;
	
	public List retrieveMasterGroupBySubGroupID (List subgroupIDList) throws SearchDAOException;

	public HashMap retrieveMemberByGroupID (List groupIDList) throws SearchDAOException;

}
