package com.integrosys.cms.ui.genlad;
///**
// * Generate excel tables.
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
//public class cCreateExcelTable extends cCreateElement {
//
//  private static cCreateExcelTable rscInstance = null;
//
//  
//  public String toString() {
//    return this.xml;
//  }
//
//  public static cCreateExcelTable getInstance() {
//    if (cCreateExcelTable.rscInstance == null) {
//      cCreateExcelTable.rscInstance = new cCreateExcelTable();
//    }
//    return cCreateExcelTable.rscInstance;
//  }
//
//  protected void fGenerateTABLE(int intRows, int intCols) {
//    char letra = 'A';
//    for (int i = 0; i < intCols; i++) {
//      letra += 1;
//    }
//    intRows++;
//    this.xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><table xmlns='http://schemas.openxmlformats.org/spreadsheetml/2006/main' id='1' name='Tabla1' displayName='Tabla1' ref='A1:" + letra + intRows + "' totalsRowShown='0' tableBorderDxfId='0'>__GENERATETABLE__</table>";
//  }
//
//  protected void fGenerateTABLECOLUMNS(String count) {
//    if ("".equals(count)) {
//      count = "2";
//    }
//    this.xml = this.xml.replaceAll("__GENERATETABLE__", "<tableColumns count='" + count + "'>__GENERATETABLECOLUMNS__</tableColumns>__GENERATETABLE__");
//  }
//
//  protected void fGenerateTABLECOLUMN(String id, String name) {
//    if ("".equals(id)) {
//      id = "2";
//    }
//    this.xml = this.xml.replaceAll("__GENERATETABLECOLUMNS__", "<tableColumn id='" + id + "' name='" + name + "'></tableColumn >__GENERATETABLECOLUMNS__");
//  }
//
//  protected void fGenerateTABLESTYLEINFO(String showFirstColumn, String showLastColumn, String showRowStripes, String showColumnStripes) {
//    if ("".equals(showFirstColumn)) {
//      showFirstColumn = "0";
//    }
//    if ("".equals(showLastColumn)) {
//      showLastColumn = "0";
//    }
//    if ("".equals(showRowStripes)) {
//      showRowStripes = "1";
//    }
//    if ("".equals(showColumnStripes)) {
//      showColumnStripes = "0";
//    }
//    this.xml = this.xml.replaceAll("__GENERATETABLE__", "<tableStyleInfo   showFirstColumn='" + showFirstColumn + "' showLastColumn='" + showLastColumn + "' showRowStripes='" + showRowStripes + "' showColumnStripes='" + showColumnStripes + "'></tableStyleInfo >");
//  }
//
//  public void fCreateExcelTable(HashMap arrArgs) {
//    this.xml = "";
//    String type = arrArgs.get("type").toString();
//    HashMap datos = (HashMap) arrArgs.get("data");
//    int intTamDatos = datos.size();
//    int intTamCols = 0;
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
//    this.fGenerateTABLE(intTamDatos, intTamCols);
//    this.fGenerateTABLECOLUMNS("" + (intTamCols + 1));
//    this.fGenerateTABLECOLUMN("1", " ");
//    for (int i = 0; i < intTamCols; i++) {
//      if (type.indexOf("pie") != -1) {
//        int num = i + 2;
//        this.fGenerateTABLECOLUMN("" + num, "0");
//        break;
//      } else {
//        int num = i + 2;
//        ArrayList arr = (ArrayList) datos.get("0");
//
//        String strAux = arr.get(i).toString();
//
//        this.fGenerateTABLECOLUMN("" + num, strAux);
//      }
//    }
//    this.fGenerateTABLESTYLEINFO("", "", "", "");
//    this.fCleanTemplate();
//  }
//}
