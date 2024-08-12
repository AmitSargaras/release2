package com.integrosys.cms.host.eai.castor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * <p>
 * Property editor to resolve a castor mapping resource location (eg.
 * <code>classpath:customer.cm.xml</code>) to a castor Mapping object
 * <tt>org.exolab.castor.mapping.Mapping</tt>.
 * <p>
 * The location follow the Spring resource location pattern, see
 * {@link ResourceLoader#getResource(String)} on how to load the castor mapping
 * resource.
 * <p>
 * After the mapping has been located and constructed, the mapping instance can
 * be injected into castor marshaller and unmarshaller instance, which then
 * ready to be used for XOM (XML Object Mapping).
 * 
 * @author Chong Jun Yong
 * @see org.exolab.castor.mapping.Mapping
 * @see org.exolab.castor.xml.Marshaller
 * @see org.exolab.castor.xml.Unmarshaller
 */
public class CastorMappingPropertyEditor extends PropertyEditorSupport {

	/** Spring resource loader to load the castor mapping resource */
	private final ResourceLoader resourceLoader = new DefaultResourceLoader();

	public void setAsText(String text) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("mapping file must be provided");
		}

		Resource mappingResource = resourceLoader.getResource(text);
		Mapping castorMapping = new Mapping();
		try {
			castorMapping.loadMapping(mappingResource.getURL());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Failed to load mapping resource from [" + mappingResource
					+ "], nested exception is " + e);
		}
		catch (MappingException e) {
			throw new IllegalArgumentException("There is error in mapping resource [" + mappingResource
					+ "], nested exception is " + e);
		}

		setValue(castorMapping);
	}
}
