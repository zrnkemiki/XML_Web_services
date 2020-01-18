//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.18 at 08:19:05 PM CET 
//


package ftn.xscience.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TRevisionEvaluation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TRevisionEvaluation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STRONGLY AGREE"/>
 *     &lt;enumeration value="AGREE"/>
 *     &lt;enumeration value="NEUTRAL"/>
 *     &lt;enumeration value="DISAGREE"/>
 *     &lt;enumeration value="STRONGLY DISAGREE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TRevisionEvaluation", namespace = "https://www.xscience.com/data/review.xsd")
@XmlEnum
public enum TRevisionEvaluation {

    @XmlEnumValue("STRONGLY AGREE")
    STRONGLY_AGREE("STRONGLY AGREE"),
    AGREE("AGREE"),
    NEUTRAL("NEUTRAL"),
    DISAGREE("DISAGREE"),
    @XmlEnumValue("STRONGLY DISAGREE")
    STRONGLY_DISAGREE("STRONGLY DISAGREE");
    private final String value;

    TRevisionEvaluation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TRevisionEvaluation fromValue(String v) {
        for (TRevisionEvaluation c: TRevisionEvaluation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
