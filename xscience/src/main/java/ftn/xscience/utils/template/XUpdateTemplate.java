package ftn.xscience.utils.template;

import org.exist.xupdate.XUpdateProcessor;

/**
 * http://xmldb-org.sourceforge.net/xupdate/xupdate-wd.html
 * 
 */
public class XUpdateTemplate {

	// OVDE SAM IZMENIO, NEMA VISE TARGET_NAMESPACE VEC PRVI PARAM JER TARGET_NAMESPACE!!!!!
	// $1s
	
	public static final String TARGET_NS_PUBLICATION = "https://www.xscience.com/data/publication.xsd";
	public static final String TARGET_NS_COVERLETTER = "https://www.xscience.com/data/coverLetter.xsd";
	public static final String TARGET_NS_REVIEW = "https://www.xscience.com/data/review.xsd";
	public static final String TARGET_NS_USER = "https://www.xscience.com/data/user.xsd";
	
	/*
	 * There are two instructions in XUpdate that support insertion of nodes:
	 * xupdate:insert-before and xupdate:insert-after. Both elements have a required
	 * select attribute, which specifies the node selected by an XPath expression.
	 * This select expression must evaluate to a node-set. The appendix of before
	 * and after to the definition of insert is meant to specify the direction
	 * where, in relation to the selected context node, the new node will be
	 * inserted.
	 * 
	 */
	public static final String INSERT_AFTER = "<xu:modifications version=\"1.0\" xmlns:xu=\""
			+ XUpdateProcessor.XUPDATE_NS + "\" xmlns=\"%1$s\">"
			+ "<xu:insert-after select=\"%2$s\">%3$s</xu:insert-after>" + "</xu:modifications>";

	/*
	 * The xupdate:insert-before inserts the given node as the preceding sibling of
	 * the selected context node, where xupdate:insert-after inserts the given node
	 * as the following sibling of the selected context node.
	 */
	public static final String INSERT_BEFORE = "<xu:modifications version=\"1.0\" xmlns:xu=\""
			+ XUpdateProcessor.XUPDATE_NS + "\" xmlns=\"%1$s\">"
			+ "<xu:insert-before select=\"%2$s\">%3$s</xu:insert-before>" + "</xu:modifications>";

	/*
	 * The xupdate:append element allows a node to be created and appended as a
	 * child of the context node. An xupdate:append element must have a select
	 * attribute which selects the context node as the parent of the new child node.
	 * The select expression must evaluate to a node-set of element nodes. The
	 * optional child attribute specifies the position of the newly appended child
	 * node. The child expression must evaluate to an Integer value. If this
	 * attribute is omitted, the new child is appended as the last child of the
	 * selected context node.
	 */
	public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"%1$s\">" + "<xu:append select=\"%2$s\" child=\"last()\">%3$s</xu:append>"
			+ "</xu:modifications>";
	/*
	 * The xupdate:update element can be used to update the content of existing
	 * nodes. An xupdate:update element must have a select attribute, which selects
	 * the context node for update. This select expression must evaluate to a
	 * node-set.
	 */
	public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"%1$s\">" + "<xu:update select=\"%2$s\">%3$s</xu:update>"
			+ "</xu:modifications>";

	/*
	 * The xupdate:remove element allows a node to be removed from the result tree.
	 * The xupdate:remove element has a required select attribute, which specifies
	 * the node selected by an XPath expression. The select expression must evaluate
	 * to a node-set.
	 */
	public static final String REMOVE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"%1$s\">" + "<xu:remove select=\"%2$s\"/>" + "</xu:modifications>";
}
