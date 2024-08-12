/**
 * Generate relationships.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

public class cCreateChartRels extends cCreateElement {

  static private cCreateChartRels rscInstance = null;

  public String toString() {
    return this.xml;
  }

  public static cCreateChartRels getInstance() {
    if (cCreateChartRels.rscInstance == null) {
      cCreateChartRels.rscInstance = new cCreateChartRels();
    }
    return rscInstance;
  }

  protected void fGenerateRELATIONSHIPS() {
    this.xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><Relationships xmlns='http://schemas.openxmlformats.org/package/2006/relationships'>__GENERATERELATIONSHIPS__</Relationships>";
  }

  protected void fGenerateRELATIONSHIP(String idChart, String id) {
    if ("".equals(id)) {
      id = "1";
    }
    this.xml = this.xml.replace("__GENERATERELATIONSHIPS__", "<Relationship Id='rId" + id + "' Type='http://schemas.openxmlformats.org/officeDocument/2006/relationships/package' Target='../embeddings/datos" + idChart + ".xlsx'></Relationship>__GENERATECHARTSPACE__");
  }

  public void fCreateRelationship(String idChart) {
    this.fGenerateRELATIONSHIPS();
    this.fGenerateRELATIONSHIP(idChart, "");
    this.fCleanTemplate();
  }
}
