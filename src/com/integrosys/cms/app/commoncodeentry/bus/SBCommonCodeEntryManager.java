/**
 * SBCommonCodeParamManager.java
 *
 * Created on January 30, 2007, 10:46 AM
 *
 * Purpose: the remote interface
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJBObject;

public interface SBCommonCodeEntryManager extends EJBObject {

	public Collection getEditableCodes() throws RemoteException;

	public Collection getCommonCodeEntries(String categoryCodeId) throws CommonCodeEntriesException, RemoteException;

	public OBCommonCodeEntry getCommonCodeEntry(String categoryCodeId, String entryCode, String country)
			throws CommonCodeEntriesException, RemoteException;

	public Collection getCommonCodeCategories() throws RemoteException;

	// public void updateCommonCodeEntry(OBCommonCodeEntry ob)
	// throws CommonCodeEntriesException;

	public OBCommonCodeEntries setStaging(ICommonCodeEntries entries, Long oldStagingRefId)
			throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntries getStagingData(String stageId) throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntries updateCommonCodeEntries(ICommonCodeEntries entries) throws CommonCodeEntriesException,
			RemoteException;

	public HashMap getValuesAndLabelsForCountry() throws RemoteException;

	public HashMap getValuesAndLabelsForStates(String code[]) throws RemoteException;

	public HashMap getValuesAndLabelsForDistrict(String code[]) throws RemoteException;

	public HashMap getValuesAndLabelsForMukim(String code[]) throws RemoteException;

	public HashMap getValuesAndLabelsForCodeAndReference(String categoryCode, String refCode) throws RemoteException;

	public Collection getOBCollectionForCodeAndReference(String categoryCode, String refCode) throws RemoteException;

	public Collection getCommonCodeEntriesByCode(String categoryCode) throws RemoteException;

	public void restoreStageCommonCodeEntry(Long stageRefId) throws CommonCodeEntriesException, RemoteException;
}
