/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.base.techinfra.diff;

import java.io.IOException;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * <p>
 * This class will load two set of castor mapping, first contain object
 * comparison mappings and the second contains the object id mappings
 * 
 * <p>
 * Property key to be set in ofa.properties is
 * <code>castor.compare.master.mapping.file</code> and
 * <code>castor.compare.master.id.mapping.file</code>
 * 
 * @author Ravi Vegiraju
 * @author Chong Jun Yong
 * @since 2003/06/13
 */
public class LoadMaps {

	private final static LoadMaps SINGLETON_INSTANCE = new LoadMaps();

	private final Mapping castorMapping;

	private final Mapping idCastorMapping;

	private LoadMaps() {
		// "Mapping_Master.xml"
		String mappingMasterFile = PropertyManager.getValue("castor.compare.master.mapping.file");
		// "Id-Mapping_Master.xml"
		String idMappingMasterFile = PropertyManager.getValue("castor.compare.master.id.mapping.file");

		ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
		URL mappingMasterFileUrl = ctxClassLoader.getResource(mappingMasterFile);
		URL idMappingMasterFileUrl = ctxClassLoader.getResource(idMappingMasterFile);

		castorMapping = new Mapping();
		try {
			castorMapping.loadMapping(mappingMasterFileUrl);
		}
		catch (IOException e) {
			throw new IllegalStateException("the mapping file [" + mappingMasterFileUrl
					+ "] doesn't exist or corrupted; nested exception is " + e);
		}
		catch (MappingException e) {
			throw new IllegalStateException("failed to load the mapping file [" + mappingMasterFileUrl
					+ "]; nested exception is " + e);
		}

		idCastorMapping = new Mapping();
		try {
			idCastorMapping.loadMapping(idMappingMasterFileUrl);
		}
		catch (IOException e) {
			throw new IllegalStateException("the mapping file [" + idMappingMasterFileUrl
					+ "] doesn't exist or corrupted; nested exception is " + e);
		}
		catch (MappingException e) {
			throw new IllegalStateException("failed to load the mapping file [" + idMappingMasterFileUrl
					+ "]; nested exception is " + e);
		}
	}

	public static LoadMaps getInstance() {
		return SINGLETON_INSTANCE;
	}

	/**
	 * @return mapping file for item comparison, should include all mapping
	 *         files
	 */
	protected Mapping getMapping() {
		return castorMapping;
	}

	/**
	 * @return mapping file for id comparison, should include all mapping files
	 */
	protected Mapping getIdMapping() {
		return idCastorMapping;
	}
}
