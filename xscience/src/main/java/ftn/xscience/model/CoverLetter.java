//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.4.0-b180830.0438 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.16 at 09:20:46 PM CET 
//


package ftn.xscience.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Publication_title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Editor" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Journal"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="0"/&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Author" type="{https://www.xscience.com/data/publication.xsd}TAuthor"/&gt;
 *         &lt;element name="Corresponding_author" type="{https://www.xscience.com/data/publication.xsd}TAuthor"/&gt;
 *         &lt;element name="Content"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CLSection" type="{https://www.xscience.com/data/coverLetter.xsd}TCLSection" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="submission_date" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "publicationTitle",
    "editor",
    "journal",
    "author",
    "correspondingAuthor",
    "content"
})
@XmlRootElement(name = "CoverLetter", namespace = "https://www.xscience.com/data/coverLetter.xsd")
public class CoverLetter {

    @XmlElement(name = "Publication_title", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
    protected String publicationTitle;
    @XmlElement(name = "Editor", namespace = "https://www.xscience.com/data/coverLetter.xsd")
    protected String editor;
    @XmlElement(name = "Journal", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
    protected String journal;
    @XmlElement(name = "Author", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
    protected TAuthor author;
    @XmlElement(name = "Corresponding_author", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
    protected TAuthor correspondingAuthor;
    @XmlElement(name = "Content", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
    protected CoverLetter.Content content;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "submission_date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar submissionDate;

    /**
     * Gets the value of the publicationTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicationTitle() {
        return publicationTitle;
    }

    /**
     * Sets the value of the publicationTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicationTitle(String value) {
        this.publicationTitle = value;
    }

    /**
     * Gets the value of the editor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditor() {
        return editor;
    }

    /**
     * Sets the value of the editor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditor(String value) {
        this.editor = value;
    }

    /**
     * Gets the value of the journal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournal() {
        return journal;
    }

    /**
     * Sets the value of the journal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournal(String value) {
        this.journal = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link TAuthor }
     *     
     */
    public TAuthor getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAuthor }
     *     
     */
    public void setAuthor(TAuthor value) {
        this.author = value;
    }

    /**
     * Gets the value of the correspondingAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link TAuthor }
     *     
     */
    public TAuthor getCorrespondingAuthor() {
        return correspondingAuthor;
    }

    /**
     * Sets the value of the correspondingAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAuthor }
     *     
     */
    public void setCorrespondingAuthor(TAuthor value) {
        this.correspondingAuthor = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link CoverLetter.Content }
     *     
     */
    public CoverLetter.Content getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoverLetter.Content }
     *     
     */
    public void setContent(CoverLetter.Content value) {
        this.content = value;
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
     * Gets the value of the submissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the value of the submissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubmissionDate(XMLGregorianCalendar value) {
        this.submissionDate = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="CLSection" type="{https://www.xscience.com/data/coverLetter.xsd}TCLSection" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "clSection"
    })
    public static class Content {

        @XmlElement(name = "CLSection", namespace = "https://www.xscience.com/data/coverLetter.xsd", required = true)
        protected List<TCLSection> clSection;

        /**
         * Gets the value of the clSection property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clSection property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCLSection().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TCLSection }
         * 
         * 
         */
        public List<TCLSection> getCLSection() {
            if (clSection == null) {
                clSection = new ArrayList<TCLSection>();
            }
            return this.clSection;
        }

    }

}
