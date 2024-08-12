//package com.integrosys.cms.ui.genlad;
///**
// * Generate excel shared strings.
// * @author 2mdc
// */
//package com.javadocx;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.Iterator;
//import java.util.Map;
//
//class cCreateExcelSharedStrings extends cCreateElement {
//
//  private static cCreateExcelSharedStrings rscInstance = null;
//
//  
//  public String toString() {
//    return this.xml;
//  }
//
//  public static cCreateExcelSharedStrings getInstance() {
//    if (cCreateExcelSharedStrings.rscInstance == null) {
//      cCreateExcelSharedStrings.rscInstance = new cCreateExcelSharedStrings();
//    }
//    return cCreateExcelSharedStrings.rscInstance;
//  }
//
//  protected void fGenerateSST(String num) {
//    this.xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><sst xmlns='http://schemas.openxmlformats.org/spreadsheetml/2006/main' count='" + num + "' uniqueCount='" + num + "'>__GENERATESST__</sst>";
//  }
//
//  protected void fGenerateSI() {
//    this.xml = this.xml.replace("__GENERATESST__", "<si>__GENERATESI__</si>__GENERATESST__");
//  }
//
//  protected void fGenerateT(String name, String space) {
//    String xmlAux = "<t";
//    if (!"".equals(space)) {
//      xmlAux += " xml:space='" + space + "'";
//    }
//    xmlAux += ">" + name + "</t>";
//
//    this.xml = this.xml.replace("__GENERATESI__", xmlAux);
//  }
//
//  public void fCreateExcelSharedStrings(HashMap arrArgs) {
//    this.xml = "";
//    String type = arrArgs.get("type").toString();
//    HashMap datos = (HashMap) arrArgs.get("data");
//    int intTamDatos = datos.size();
//    int intTamCols = 0;
//    int i;
//    Set entries = datos.entrySet();
//    Iterator it = entries.iterator();
//    while (it.hasNext()) {
//      Map.Entry entry = (Map.Entry) it.next();
//      intTamCols = ((ArrayList) entry.getValue()).size();
//      break;
//    }
//    if (type.indexOf("pie") != -1) {
//      intTamCols = 1;
//    } else {
//      intTamDatos--;
//    }
//    String str = new String();
//    this.fGenerateSST(str.valueOf(intTamDatos + intTamCols + 2));
//    for (i = 0; i < intTamCols; i++) {
//      if (type.indexOf("pie") != -1) {
//        this.fGenerateSI();
//        this.fGenerateT("0", "");
//        break;
//      } else {
//        this.fGenerateSI();
//      }
//      this.fGenerateT(((ArrayList) datos.get("0")).get(i).toString(), "");
//    }
//
//
//    entries = datos.entrySet();
//    it = entries.iterator();
//    while (it.hasNext()) {
//      Map.Entry entry = (Map.Entry) it.next();
//      if (entry.getKey() == "0") {
//        continue;
//      }
//      this.fGenerateSI();
//      this.fGenerateT(entry.getKey().toString(), "");
//    }
//    this.fGenerateSI();
//    this.fGenerateT(" ", "preserve");
//
//    this.fGenerateSI();
//    this.fGenerateT("Para cambiar la dimension del rango de datos del chart, arrastre la esquina inferior derecha del rango.", "");
//
//    this.fCleanTemplate();
//  }
//}
