package com.integrosys.cms.app.newtatmaster.bus;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;


public interface ITatMasterJdbc {
	
	public SearchResult getAllTatEvents() throws TatMasterException;
	public INewTatMaster getTatEvent(String event) throws TatMasterException ;
	public HashMap getTatTimings(String event) throws TatMasterException ;
}
