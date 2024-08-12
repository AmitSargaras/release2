package com.integrosys.cms.batch.common.filereader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.batch.common.posidex.templateparser.PosidexTemplateOut;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.SyncMasterTemplateOut;
import com.integrosys.cms.batch.eod.IPosidexFileGenConstants;
/**
 * 
 * @author anil.pandey
 * 
 * @createdOn Sep 12, 2014 11:17:09 AM
 */
public class PosidexTemplateParser implements IPosidexFileGenConstants{
	/**
	 * This method parses the xml template by processing key i.e. master name.
	 * 
	 * @param processingKey
	 * @return {@link SyncMasterTemplateOut}
	 */
	protected PosidexTemplateOut getTemplateObj(String processingKey) {
		
		String templateKey= processingKey+"_Template.xml";

		String templateBasePath=PropertyManager.getValue(INTEGROSYS_POSIDEX_TEMPLATE_BASEPATH);
		String templatePath=templateBasePath+templateKey;
		
		PosidexTemplateOut posidexTemplateOut=null;
		try {
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			posidexTemplateOut = (PosidexTemplateOut) Unmarshaller.unmarshal(PosidexTemplateOut.class, read);
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
		return posidexTemplateOut;
	}
}
