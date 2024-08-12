package com.integrosys.cms.app.newTat.bus;

import java.util.ArrayList;
import java.io.Serializable;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;

/**
 * This interface defines the constant specific to the new Tat table and the
 * methods required by the new Tat
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2013/09/19  $ Tag: $Name: $
 */
public interface INewTatDAO  {
	
	public ArrayList getListNewTat() throws SearchDAOException ;
	public INewTat createTAT(String entityName,INewTat tat,long id)throws TatException ;
	public void updateTAT(String entityName,INewTat tat,long id)throws TatException ;
	public INewTat getTat(String entityName, Serializable key)throws TatException ;
	public INewTat load(String entityName, long id)throws TatException;
	public void createTATReportCaseBase(String entityName,OBNewTatReportCase tatReportCase,long id)	throws TatException ;
}
