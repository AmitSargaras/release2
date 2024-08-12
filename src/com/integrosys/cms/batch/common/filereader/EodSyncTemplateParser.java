package com.integrosys.cms.batch.common.filereader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.SyncMasterTemplateOut;
import com.integrosys.cms.batch.eod.IEodSyncConstants;
/**
 * 
 * @author anil.pandey
 * 
 * @createdOn Sep 12, 2014 11:17:09 AM
 */
public class EodSyncTemplateParser implements IEodSyncConstants{
	/**
	 * This method parses the xml template by processing key i.e. master name.
	 * 
	 * @param processingKey
	 * @return {@link SyncMasterTemplateOut}
	 */
	protected SyncMasterTemplateOut getMasterTemplateObj(String processingKey) {
		
		String templateKey= processingKey+"_Template.xml";

		String templateBasePath=PropertyManager.getValue(EOD_SYNC_CLIMSTOCPS_TEMPLATE_BASE_PATH);
		String templatePath=templateBasePath+templateKey;
		
		SyncMasterTemplateOut syncMasterTemplateOut=null;
		try {
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			syncMasterTemplateOut = (SyncMasterTemplateOut) Unmarshaller.unmarshal(SyncMasterTemplateOut.class, read);
		} catch (MarshalException e) {
			e.printStackTrace();
			throw new EODSyncStatusException("Error in marshaling report template.",e);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new EODSyncStatusException("Validating report template failed.",e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new EODSyncStatusException("Template not found.",e);
		}
		return syncMasterTemplateOut;
	}
}
