//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.18 at 08:19:05 PM CET 
//


package ftn.xscience.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="List_item" type="{https://www.xscience.com/data/publication.xsd}TStyle" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ordered" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ordered_style" default="1">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="A"/>
 *             &lt;enumeration value="a"/>
 *             &lt;enumeration value="I"/>
 *             &lt;enumeration value="i"/>
 *             &lt;enumeration value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="unordered_style" default="BULLET">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="BULLET"/>
 *             &lt;enumeration value="CIRCLE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TList", propOrder = {
    "listItem"
})
public class TList {

    @XmlElement(name = "List_item", required = true)
    protected List<TStyle> listItem;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "ordered")
    protected Boolean ordered;
    @XmlAttribute(name = "ordered_style")
    protected String orderedStyle;
    @XmlAttribute(name = "unordered_style")
    protected String unorderedStyle;

    /**
     * Gets the value of the listItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TStyle }
     * 
     * 
     */
    public List<TStyle> getListItem() {
        if (listItem == null) {
            listItem = new ArrayList<TStyle>();
        }
        return this.listItem;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the ordered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrdered() {
        return ordered;
    }

    /**
     * Sets the value of the ordered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrdered(Boolean value) {
        this.ordered = value;
    }

    /**
     * Gets the value of the orderedStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderedStyle() {
        if (orderedStyle == null) {
            return "1";
        } else {
            return orderedStyle;
        }
    }

    /**
     * Sets the value of the orderedStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderedStyle(String value) {
        this.orderedStyle = value;
    }

    /**
     * Gets the value of the unorderedStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnorderedStyle() {
        if (unorderedStyle == null) {
            return "BULLET";
        } else {
            return unorderedStyle;
        }
    }

    /**
     * Sets the value of the unorderedStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnorderedStyle(String value) {
        this.unorderedStyle = value;
    }

}
