/**
 * StartExeByAttach.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.kepler.executionWS.xsd;

public class StartExeByAttach  implements java.io.Serializable {
    private java.lang.Object startExeByAttach;

    public StartExeByAttach() {
    }

    public StartExeByAttach(
           java.lang.Object startExeByAttach) {
           this.startExeByAttach = startExeByAttach;
    }


    /**
     * Gets the startExeByAttach value for this StartExeByAttach.
     * 
     * @return startExeByAttach
     */
    public java.lang.Object getStartExeByAttach() {
        return startExeByAttach;
    }


    /**
     * Sets the startExeByAttach value for this StartExeByAttach.
     * 
     * @param startExeByAttach
     */
    public void setStartExeByAttach(java.lang.Object startExeByAttach) {
        this.startExeByAttach = startExeByAttach;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StartExeByAttach)) return false;
        StartExeByAttach other = (StartExeByAttach) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.startExeByAttach==null && other.getStartExeByAttach()==null) || 
             (this.startExeByAttach!=null &&
              this.startExeByAttach.equals(other.getStartExeByAttach())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getStartExeByAttach() != null) {
            _hashCode += getStartExeByAttach().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StartExeByAttach.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://executionWS.kepler.org/xsd", ">startExeByAttach"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startExeByAttach");
        elemField.setXmlName(new javax.xml.namespace.QName("http://executionWS.kepler.org/xsd", "startExeByAttach"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
