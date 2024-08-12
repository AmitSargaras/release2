package com.integrosys.cms.app.propertyindex.bus;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 11, 2008
 * Time: 7:15:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPropertyIdxDao {
    // entity name for actual property index
    public static final String ACTUAL_PROPERTY_IDX_ENTITY_NAME = "actualPropertyIdx";
    // entity name for stage property index
    public static final String STAGE_PROPERTY_IDX_ENTITY_NAME = "stagePropertyIdx";
    // entity name for actual property index item
    public static final String ACTUAL_PROPERTY_IDX_ITEM_ENTITY_NAME = "actualPropertyIdxItem";
    // entity name for stage property index item
    public static final String STAGE_PROPERTY_IDX_ITEM_ENTITY_NAME = "stagePropertyIdxItem";
    // entity name for actual property index security subtype
    public static final String ACTUAL_PROPERTY_IDX_SEC_SUBTYPE_ENTITY_NAME = "actualPropertyIdxSecSubType";
    // entity name for stage property index security subtype
    public static final String STAGE_PROPERTY_IDX_SEC_SUBTYPE_ENTITY_NAME = "stagePropertyIdxSecSubType";
    // entity name for actual property index mukim
    public static final String ACTUAL_PROPERTY_IDX_MUKIM_ENTITY_NAME = "actualPropertyIdxMukim";
    // entity name for stage property index mukim
    public static final String STAGE_PROPERTY_IDX_MUKIM_ENTITY_NAME = "stagePropertyIdxMukim";
    // entity name for actual property index district
    public static final String ACTUAL_PROPERTY_IDX_DISTRICT_ENTITY_NAME = "actualPropertyIdxDistrict";
    // entity name for stage property index district
    public static final String STAGE_PROPERTY_IDX_DISTRICT_ENTITY_NAME = "stagePropertyIdxDistrict";
    // entity name for actual property index property type
    public static final String ACTUAL_PROPERTY_IDX_PROPERTY_TYPE_ENTITY_NAME = "actualPropertyIdxPropertyType";
    // entity name for stage property index property type
    public static final String STAGE_PROPERTY_IDX_PROPERTY_TYPE_ENTITY_NAME = "stagePropertyIdxPropertyType";

    
    IPropertyIdx createPropertyIdx(String entityName, IPropertyIdx propertyIdx);

    IPropertyIdx getPropertyIdx(String entityName, long key);

    IPropertyIdx updatePropertyIdx(String entityName, IPropertyIdx propertyIdx);
}
