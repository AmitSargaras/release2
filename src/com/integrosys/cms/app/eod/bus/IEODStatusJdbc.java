package com.integrosys.cms.app.eod.bus;

import java.util.List;

public interface IEODStatusJdbc {

	public void recpColLimitLink()throws EODStatusException;
	public void recpColProductLink()throws EODStatusException ;
	public void recpCollateral()throws EODStatusException ;
	public void recpCustLegal()throws EODStatusException;
	public void recpCustomer()throws EODStatusException ;
	public void recpParty()throws EODStatusException ;
	public void recpRbiAdfAllProcedure()throws EODStatusException ;
	public String[] getStatusEODActivities() throws EODStatusException;	
	public void recpFinconParty() throws EODStatusException;
	public void recpFinconCustomer() throws EODStatusException;
	public void recpFinconCustLegal() throws EODStatusException;
	public void recpFinconColProductLink() throws EODStatusException;
	public void recpFinconColLimitLink() throws EODStatusException;
	public void recpFinconCollateral() throws EODStatusException;	
	// Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Starts
	public void recpFinconCustWiseSec() throws EODStatusException;
	public void recpFinconMasterIndustry() throws EODStatusException;	
	//Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Ends
	
	
	
}
