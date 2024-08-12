package com.integrosys.cms.host.eai.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Persistent Dao used within EAI Framework. All sub class can have it's own
 * separate methods for individual module purpose.
 * 
 * @author Chong Jun Yong
 * @since 14.08.2008
 */
public interface IPersistentDao {
	/**
	 * Store object into persistent storage.
	 * 
	 * @param object object to be stored into persistent storage
	 * @param classRequired class type of the object, must match
	 * @return the key of the object
	 */
	public Serializable store(Object object, Class classRequired);

	/**
	 * Save the object to persistent storage
	 * 
	 * @param object object to be saved
	 * @param classRequired
	 * @param entityName for the case where one class is used to map with more
	 *        than one table
	 * @return the object match the key passed in, else null
	 */
	public Serializable store(Object object, Class classRequired, String entityName);

	/**
	 * Retrieve object based on the key passed in
	 * 
	 * @param id they key of objects
	 * @param classRequired class type of the object required.
	 * @return the object match the key passed in, else null
	 */
	public Object retrieve(Serializable id, Class classRequired);

	/**
	 * Retrieve first object based on the parameters map passed in
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param classRequired class type of the object required
	 * @return the object match all the paramters passed in, else null
	 */
	public Object retrieveObjectByParameters(Map parameters, Class classRequired);

	/**
	 * Retrieve the object based on the primary key and the entity name
	 * 
	 * @param id the primary key of the object
	 * @param entityName (For cases when we use the same class with for two or
	 *        more different table)
	 * @return the object match the key passed in, else null
	 */
	public Object retrieve(Serializable id, String entityName);

	/**
	 * Retrieve first object based on the parameters map passed in which status
	 * is not deleted
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param statusIndicatorFieldName object parameter name for status
	 *        indicator.
	 * @param classRequired class type of the object required
	 * @return the object match all the paramters passed in, else null
	 */
	public Object retrieveNonDeletedObjectByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired);

	/**
	 * Retrieve non deleted object from the persistent storage
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param statusIndicatorFieldName object parameter name for status
	 *        indicator.
	 * @param classRequired class type of the object required
	 * @param entityName this is the entity to differentate the table name
	 * @return the object match all the paramters passed in, else null
	 */
	public Object retrieveNonDeletedObjectByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired, String entityName);

	/**
	 * Retrieve all objects based on the parameters map passed in, which status
	 * is not deleted
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param statusIndicatorFieldName object parameter name for status
	 *        indicator.
	 * @param classRequired class type of the object required
	 * @return the object match all the paramters passed in or else empty list
	 */
	public List retrieveNonDeletedObjectsListByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired);

	/**
	 * Retrieve all objects based on the parameters map passed in
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param classRequired class type of the object required
	 * @return the objects match all the parameters passed in or else empty list
	 */
	public List retrieveObjectListByParameters(Map parameters, Class classRequired);

	/**
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param classRequired class type of the object required
	 * @param entityName this is to differentiate the table name
	 * @return the object match all the parameters passed in or else null
	 */
	public Object retrieveObjectByParameters(Map parameters, Class classRequired, String entityName);

	/**
	 * Update the object into persistent storage
	 * 
	 * @param object object to be updated into persistent storage
	 * @param classRequired class type of the object, must match
	 */
	public void update(Object object, Class classRequired);

	/**
	 * Update the object into persistent storage
	 * @param object object to be updated into persistent storage
	 * @param classRequired class type of the object, must match
	 * @param entityName is to differentiate the table name
	 */
	public void update(Object object, Class classRequired, String entityName);

	/**
	 * Remove the object from persistent storage
	 * 
	 * @param object object to be removed from persistent storage
	 * @param classRequired class type of the object, must match
	 */
	public void remove(Object object, Class classRequired);

	/**
	 * 
	 * @param parameters <b>&lt;String, Object&gt;</b> map pair to compare with
	 *        the persistent storage
	 * @param classRequired classRequired class type of the object required
	 * @return the objects match all the parameters passed in or else empty list
	 */
	public List retrieveObjectsListByParameters(Map parameters, Class classRequired);
	
	/**
	 * Retrieve the list of object by using entity name
	 * @param parameters
	 * @param statusIndicatorFieldName
	 * @param entityName
	 * @return
	 */
	public List retrieveNonDeletedObjectsListByParameters(Map parameters, String statusIndicatorFieldName,
			String entityName);
}
