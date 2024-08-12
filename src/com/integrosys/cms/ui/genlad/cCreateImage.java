/**
 * Generate images.
 * @author 2mdc
 */

package com.integrosys.cms.ui.genlad;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.ImageIcon;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateImage extends cCreateElement {

  protected static final String NAMESPACE = "w";
  protected static final String IMGNAMESPACE = "wp";
  protected static final String NAMESPACE1 = "a";
  protected static final String NAMESPACE2 = "pic";
  protected static final int CONSTWORD = 360000;
  protected static final int TAMBORDER = 12700;
  protected int defaultDpi = 2835;
  private static cCreateImage rscInstance = null;
  public String name;
  public String rId;
  
  public cCreateImage() {
    this.name = new String("");
    this.rId = new String("");
  }

  public String toString() {
    return this.xml;
  }

  public static cCreateImage getInstance() {
    if (rscInstance == null) {
      rscInstance = new cCreateImage();
    }
    return rscInstance;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setRId(String rId) {
    this.rId = rId;
  }

  public String getRId() {
    return this.rId;
  }
  
  public void setDefaultDpi(int dpi){
	  this.defaultDpi = dpi;
  }

  protected void fGenerateBLIP(String cstate) {
    if (cstate == null) {
      cstate = "print";
    }

    this.xml = this.xml.replace("__GENERATEBLIPFILL__", "<" + this.NAMESPACE1 + ":blip r:embed='rId" + this.rId + "' cstate='" + cstate + "'></" + this.NAMESPACE1 + ":blip>__GENERATEBLIPFILL__");
  }

  public int getDpiXJpg(String path) {
    try {
      InputStream is = new BufferedInputStream(new FileInputStream(path));
      byte[] buf = new byte[30];
      int numLeidos = is.read(buf, 0, 20);
      String strDpiX = Integer.toString((buf[14] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[15] & 0xff) + 0x100, 16).substring(1);
      return Integer.parseInt(strDpiX, 16);
    } catch (Exception e) {
      return this.defaultDpi;
    }
  }

  public int getDpiYJpg(String path) {
    try {
      InputStream is = new BufferedInputStream(new FileInputStream(path));
      byte[] buf = new byte[30];
      int numLeidos = is.read(buf, 0, 20);
      String strDpiY = Integer.toString((buf[16] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[17] & 0xff) + 0x100, 16).substring(1);
      return Integer.parseInt(strDpiY, 16);
    } catch (Exception e) {
      return this.defaultDpi;
    }
  }
  
  public int getDpiXPng(String path){
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(path));
			byte[] buf = new byte[100];
			int numLeidos = is.read(buf, 0,100);
			String strBuf = new String(buf);
			int indice = strBuf.indexOf("pHYs");
			String dpiX = Integer.toString((buf[indice+4] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+5] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+6] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+7] & 0xff) + 0x100, 16).substring(1);
			String activo = Integer.toString((buf[indice+12] & 0xff) + 0x100, 16).substring(1);
			if(activo.equals("01"))
				return Integer.parseInt(dpiX,16);
			else
				return this.defaultDpi;
		} catch (Exception e) {
			return this.defaultDpi;
	    }		
	}

	public int getDpiYPng(String path){
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(path));
			byte[] buf = new byte[100];
			int numLeidos = is.read(buf, 0,100);
			String strBuf = new String(buf);
			int indice = strBuf.indexOf("pHYs");
			String dpiY = Integer.toString((buf[indice+8] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+9] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+10] & 0xff) + 0x100, 16).substring(1) + Integer.toString((buf[indice+11] & 0xff) + 0x100, 16).substring(1);
			String activo = Integer.toString((buf[indice+12] & 0xff) + 0x100, 16).substring(1);
			if(activo.equals("01"))
				return Integer.parseInt(dpiY,16);
			else
				return this.defaultDpi;
		} catch (Exception e) {
			return this.defaultDpi;
	    }		
	}

  public boolean fCreateImage(HashMap arrArgs) {
    this.xml = new String("");
    if (arrArgs.containsKey("rId") && arrArgs.containsKey("name")) {
      int intAjusteTexto;
      Image img = new ImageIcon(arrArgs.get("name").toString()).getImage();
      int tamPxX = img.getWidth(null);
      int tamPxY = img.getHeight(null);
      if (arrArgs.containsKey("sizeX")) {
        tamPxX = Integer.parseInt(arrArgs.get("sizeX").toString());
      } else if (arrArgs.containsKey("scaling")) {
        tamPxX = Integer.parseInt(arrArgs.get("scaling").toString()) * tamPxX / 100;
      }
      if (arrArgs.containsKey("sizeY")) {
        tamPxY = Integer.parseInt(arrArgs.get("sizeY").toString());
      } else if (arrArgs.containsKey("scaling")) {
        tamPxY = Integer.parseInt(arrArgs.get("scaling").toString()) * tamPxY / 100;
      }
      this.setName(arrArgs.get("name").toString());
      this.setRId(arrArgs.get("rId").toString());
      double top = 0;
      double bottom = 0;
      double left = 0;
      double right = 0;
      File file;
      try {
        file = new File(arrArgs.get("name").toString());
      } catch (NullPointerException e) {
    	  DefaultLogger.debug(this,"The imagen does not exist.");
        return false;
      }
      String mimeType = new MimetypesFileTypeMap().getContentType(file);
      double dpi_y = 100;
      double dpi_x = 100;
      double tamWordX = 1;
      double tamWordY = 1;
      if (mimeType.equals("image/png") || mimeType.equals("application/octet-stream")) {
        // la formula es : pixeles por la inversa de la resolucion(viene
        // dado en pixeles/metros) por la 100 (para reducir metros a
        // centimetros) por la constante de word (360000)
    	dpi_x = this.getDpiXPng(arrArgs.get("name").toString());    	
    	dpi_y = this.getDpiYPng(arrArgs.get("name").toString());
    	tamWordX = Math.round(tamPxX * 100 / dpi_x * cCreateImage.CONSTWORD);
        tamWordY = Math.round(tamPxY * 100 / dpi_y * cCreateImage.CONSTWORD);
      } else if (mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")) {
        // la formula es : pixeles por la inversa de la resolucion(viene
        // dado en pixeles/inch) por la 2.54 (1 inch son 2.54 cm) por la
        // constante de word (cCreateImage.CONSTWORD)
        dpi_y = this.getDpiYJpg(arrArgs.get("name").toString());
        dpi_x = this.getDpiXJpg(arrArgs.get("name").toString());
//        System.out.println();
        tamWordX = Math.round(tamPxX * 2.54 / dpi_x * cCreateImage.CONSTWORD * 1.0);
        tamWordY = Math.round(tamPxY * 2.54 / dpi_y * cCreateImage.CONSTWORD);
      } else if (mimeType.equals("image/gif")) {
        // la formula es : pixeles por la inversa de la resolucion de la
        // pantalla(96 pixeles por inch) por la 2.54 (1 inch son 2.54
        // cm) por la constante de word (cCreateImage.CONSTWORD)
        tamWordX = Math.round(tamPxX * 2.54 / 96 * cCreateImage.CONSTWORD);
        tamWordY = Math.round(tamPxY * 2.54 / 96 * cCreateImage.CONSTWORD);
      }
      String align = "";
      double tamPageX;
      this.fGenerateP();
      this.fGenerateR();
      this.fGenerateRPR();
      this.fGenerateNOPROOF();
      this.fGenerateDRAWING();
      this.fGenerateINLINE("", "", "", "");
      this.fGenerateEXTENT("" + Math.round(tamWordX), "" + Math.round(tamWordY));
      this.fGenerateEFFECTEXTENT("" + Math.round(left), "" + Math.round(top), "" + Math.round(right), "" + Math.round(bottom));
      this.fGenerateDOCPR("", "");
      this.fGenerateCNVGRAPHICFRAMEPR();
      this.fGenerateGRAPHICPRAMELOCKS("1");
      this.fGenerateGRAPHIC("");
      this.fGenerateGRAPHICDATA("");
      this.fGeneratePIC("");
      this.fGenerateNVPICPR();
      this.fGenerateCNVPR("", "image");
      this.fGenerateCNVPICPR();
      this.fGenerateBLIPFILL();
      this.fGenerateBLIP("print");
      this.fGenerateSTRETCH();
      this.fGenerateFILLRECT();
      this.fGenerateSPPR();
      this.fGenerateXFRM();
      this.fGenerateOFF("", "");
      this.fGenerateEXT("" + Math.round(tamWordX), "" + Math.round(tamWordY));
      this.fGeneratePRSTGEOM("");
      this.fGenerateAVLST();
      this.fCleanTemplate();
      return true;
    } else {
    	DefaultLogger.debug(this,"There is not path of the image.");
      return false;
    }
  }
}
