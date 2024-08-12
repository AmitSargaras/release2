
package com.integrosys.cms.app.securityenvelope.bus;

import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS 
 * Description: Data object for EBSecEnvelopeItem.
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong 
 * Date: Jan 25, 2010
 */

public class OBSecEnvelopeItem implements ISecEnvelopeItem
{
   private long secEnvelopeItemId;
   private long secEnvelopeId;
   private java.lang.String secEnvelopeItemAddr;
   private java.lang.String secEnvelopeItemCab;
   private java.lang.String secEnvelopeItemDrw;
   private java.lang.String secEnvelopeItemBarcode;
   private java.lang.String status;
   private long secEnvelopeRefId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

  /* begin value object */

  /* end value object */

   public OBSecEnvelopeItem()
   {
   }

   public OBSecEnvelopeItem( long secEnvelopeItemId,java.lang.String secEnvelopeItemAddr,java.lang.String secEnvelopeItemCab,java.lang.String secEnvelopeItemDrw, java.lang.String status,long secEnvelopeRefId )
   {
      setSecEnvelopeItemId(secEnvelopeItemId);
      setSecEnvelopeItemAddr(secEnvelopeItemAddr);
      setSecEnvelopeItemCab(secEnvelopeItemCab);
      setSecEnvelopeItemDrw(secEnvelopeItemDrw);
      setSecEnvelopeItemBarcode(secEnvelopeItemBarcode); 
      setStatus(status);
      setSecEnvelopeRefId(secEnvelopeRefId);
   }

   public OBSecEnvelopeItem( OBSecEnvelopeItem otherData )
   {
      setSecEnvelopeItemId(otherData.getSecEnvelopeItemId());
      setSecEnvelopeItemAddr(otherData.getSecEnvelopeItemAddr());
      setSecEnvelopeItemCab(otherData.getSecEnvelopeItemCab());
      setSecEnvelopeItemDrw(otherData.getSecEnvelopeItemDrw());
      setSecEnvelopeItemBarcode(otherData.getSecEnvelopeItemBarcode());
      setStatus(otherData.getStatus());
      setSecEnvelopeRefId(otherData.getSecEnvelopeRefId());

   }

   public long getPrimaryKey() {
     return  getSecEnvelopeItemId();
   }

   public long getSecEnvelopeItemId()
   {
      return this.secEnvelopeItemId;
   }
   public void setSecEnvelopeItemId( long secEnvelopeItemId )
   {
      this.secEnvelopeItemId = secEnvelopeItemId;
   }

   public long getSecEnvelopeId()
   {
      return this.secEnvelopeId;
   }
   public void setSecEnvelopeId( long secEnvelopeId )
   {
      this.secEnvelopeId = secEnvelopeId;
   }

   public java.lang.String getSecEnvelopeItemAddr()
   {
      return this.secEnvelopeItemAddr;
   }
   public void setSecEnvelopeItemAddr( java.lang.String secEnvelopeItemAddr )
   {
      this.secEnvelopeItemAddr = secEnvelopeItemAddr;
   }

   public java.lang.String getSecEnvelopeItemCab()
   {
      return this.secEnvelopeItemCab;
   }
   public void setSecEnvelopeItemCab( java.lang.String secEnvelopeItemCab )
   {
      this.secEnvelopeItemCab = secEnvelopeItemCab;
   }

   public java.lang.String getSecEnvelopeItemDrw()
   {
      return this.secEnvelopeItemDrw;
   }
   public void setSecEnvelopeItemDrw( java.lang.String secEnvelopeItemDrw )
   {
      this.secEnvelopeItemDrw = secEnvelopeItemDrw;
   }

   public java.lang.String getSecEnvelopeItemBarcode()
   {
      return this.secEnvelopeItemBarcode;
   }
   public void setSecEnvelopeItemBarcode( java.lang.String secEnvelopeItemBarcode )
   {
      this.secEnvelopeItemBarcode = secEnvelopeItemBarcode;
   }

    public java.lang.String getStatus()
   {
      return this.status;
   }
   public void setStatus( java.lang.String status )
   {
      this.status = status;
   }

   public long getSecEnvelopeRefId()
   {
      return this.secEnvelopeRefId;
   }
   public void setSecEnvelopeRefId( long secEnvelopeRefId )
   {
      this.secEnvelopeRefId = secEnvelopeRefId;
   }

   public String toString()
   {
      StringBuffer str = new StringBuffer("{");

      str.append("secEnvelopeItemId=" + getSecEnvelopeItemId() + " " + "secEnvelopeItemAddr=" + getSecEnvelopeItemAddr() + " " + "secEnvelopeItemCab=" + getSecEnvelopeItemCab() + " " + "secEnvelopeItemDrw=" + getSecEnvelopeItemDrw() + " " + "status=" + getStatus() + " " + "secEnvelopeRefId=" + getSecEnvelopeRefId());
      str.append('}');

      return(str.toString());
   }

    public boolean equals( Object pOther )
    {
       if( pOther instanceof OBSecEnvelopeItem )
       {
          OBSecEnvelopeItem lTest = (OBSecEnvelopeItem) pOther;
          boolean lEquals = true;

					//------------------
          if( this.secEnvelopeItemAddr == null )
          {
             lEquals = lEquals && ( lTest.secEnvelopeItemAddr == null );
          }
          else
          {
             lEquals = lEquals && this.secEnvelopeItemAddr.equals( lTest.secEnvelopeItemAddr );
          }
          
          //------------------
          if( this.secEnvelopeItemCab == null )
          {
             lEquals = lEquals && ( lTest.secEnvelopeItemCab == null );
          }
          else
          {
             lEquals = lEquals && this.secEnvelopeItemCab.equals( lTest.secEnvelopeItemCab );
          }
          
          //------------------
          if( this.secEnvelopeItemDrw == null )
          {
             lEquals = lEquals && ( lTest.secEnvelopeItemDrw == null );
          }
          else
          {
             lEquals = lEquals && this.secEnvelopeItemDrw.equals( lTest.secEnvelopeItemDrw );
          }
          
          //--------------------
          if( this.status == null )
          {
             lEquals = lEquals && ( lTest.status == null );
          }
          else
          {
             lEquals = lEquals && this.status.equals( lTest.status );
          }
          
          //---------------------
          if( this.secEnvelopeRefId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE )
          {
             lEquals = lEquals && ( lTest.secEnvelopeRefId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE );
          }
          else
          {
             lEquals = lEquals && this.secEnvelopeRefId == lTest.secEnvelopeRefId;
          }

          return lEquals;
       }
       else
       {
          return false;
       }
    }
}
