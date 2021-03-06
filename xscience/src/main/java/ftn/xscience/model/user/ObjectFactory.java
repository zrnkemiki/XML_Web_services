//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.01 at 11:32:37 PM CET 
//


package ftn.xscience.model.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.www_xscience_com.data.user package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _User_QNAME = new QName("https://www.xscience.com/data/user.xsd", "User");
    private final static QName _TUserPublicationsForReviewForReviewID_QNAME = new QName("https://www.xscience.com/data/user.xsd", "ForReviewID");
    private final static QName _TUserPublicationsPublicationID_QNAME = new QName("https://www.xscience.com/data/user.xsd", "PublicationID");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.www_xscience_com.data.user
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TUser }
     * 
     */
    public TUser createTUser() {
        return new TUser();
    }

    /**
     * Create an instance of {@link TUser.Expertises }
     * 
     */
    public TUser.Expertises createTUserExpertises() {
        return new TUser.Expertises();
    }

    /**
     * Create an instance of {@link TUser.Publications }
     * 
     */
    public TUser.Publications createTUserPublications() {
        return new TUser.Publications();
    }

    /**
     * Create an instance of {@link TUser.PublicationsForReview }
     * 
     */
    public TUser.PublicationsForReview createTUserPublicationsForReview() {
        return new TUser.PublicationsForReview();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://www.xscience.com/data/user.xsd", name = "User")
    public JAXBElement<TUser> createUser(TUser value) {
        return new JAXBElement<TUser>(_User_QNAME, TUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://www.xscience.com/data/user.xsd", name = "ForReviewID", scope = TUser.PublicationsForReview.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    public JAXBElement<String> createTUserPublicationsForReviewForReviewID(String value) {
        return new JAXBElement<String>(_TUserPublicationsForReviewForReviewID_QNAME, String.class, TUser.PublicationsForReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://www.xscience.com/data/user.xsd", name = "PublicationID", scope = TUser.Publications.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    public JAXBElement<String> createTUserPublicationsPublicationID(String value) {
        return new JAXBElement<String>(_TUserPublicationsPublicationID_QNAME, String.class, TUser.Publications.class, value);
    }

}
