package co.mv.stm.service.impl.dom;

import co.mv.stm.Resource;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.ResourceLoader;
import co.mv.stm.service.ResourceLoaderFault;
import java.io.StringReader;
import java.util.Map;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class DomResourceLoader implements ResourceLoader
{
	private static final String ELT_RESOURCE = "resource";
		private static final String ATT_RESOURCE_ID = "id";
		private static final String ATT_RESOURCE_TYPE = "type";
		private static final String ATT_RESOURCE_NAME = "name";
	private static final String ELT_STATES = "states";
	private static final String ELT_STATE = "state";
		private static final String ATT_STATE_ID = "id";
		private static final String ATT_STATE_LABEL = "label";
	private static final String ELT_ASSERTIONS = "assertions";
		private static final String ATT_ASSERTION_ID = "id";
		private static final String ATT_ASSERTION_TYPE = "type";
		private static final String ATT_ASSERTION_NAME = "name";
	private static final String ELT_TRANSITIONS = "transitions";
	
	public DomResourceLoader(
		Map<String, ResourceBuilder> resourceBuilders,
		String resourceXml)
	{
		this.setResourceBuilders(resourceBuilders);
		this.setResourceXml(resourceXml);
	}

	// <editor-fold desc="ResourceBuilders" defaultstate="collapsed">

	private Map<String, ResourceBuilder> m_resourceBuilders = null;
	private boolean m_resourceBuilders_set = false;

	private Map<String, ResourceBuilder> getResourceBuilders() {
		if(!m_resourceBuilders_set) {
			throw new IllegalStateException("resourceBuilders not set.  Use the HasResourceBuilders() method to check its state before accessing it.");
		}
		return m_resourceBuilders;
	}

	private void setResourceBuilders(Map<String, ResourceBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceBuilders cannot be null");
		}
		boolean changing = !m_resourceBuilders_set || m_resourceBuilders != value;
		if(changing) {
			m_resourceBuilders_set = true;
			m_resourceBuilders = value;
		}
	}

	private void clearResourceBuilders() {
		if(m_resourceBuilders_set) {
			m_resourceBuilders_set = true;
			m_resourceBuilders = null;
		}
	}

	private boolean hasResourceBuilders() {
		return m_resourceBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceXml" defaultstate="collapsed">

	private String m_resourceXml = null;
	private boolean m_resourceXml_set = false;

	private String getResourceXml() {
		if(!m_resourceXml_set) {
			throw new IllegalStateException("resourceXml not set.  Use the HasResourceXml() method to check its state before accessing it.");
		}
		return m_resourceXml;
	}

	private void setResourceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceXml cannot be null");
		}
		boolean changing = !m_resourceXml_set || m_resourceXml != value;
		if(changing) {
			m_resourceXml_set = true;
			m_resourceXml = value;
		}
	}

	private void clearResourceXml() {
		if(m_resourceXml_set) {
			m_resourceXml_set = true;
			m_resourceXml = null;
		}
	}

	private boolean hasResourceXml() {
		return m_resourceXml_set;
	}

	// </editor-fold>

	@Override public Resource load()
	{
		InputSource inputSource = new InputSource(new StringReader(this.getResourceXml()));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new ResourceLoaderFault(e);
		}
		
		Document resourceXd = null;
		try
		{
			resourceXd = db.parse(inputSource);
		}
		catch (Exception e)
		{
			throw new ResourceLoaderFault(e);
		}
		
		Element resourceXe = resourceXd.getDocumentElement();
		Resource resource = buildResource(this.getResourceBuilders(), resourceXe);

		if (ELT_RESOURCE.equals(resourceXe.getLocalName()))
		{
			for (int i = 0; i < resourceXe.getChildNodes().getLength(); i ++)
			{
				Element childXe = (Element)resourceXe.getChildNodes().item(i);

				if (ELT_STATES.equals(childXe.getLocalName()))
				{
				}
				
				if (ELT_TRANSITIONS.equals(childXe.getLocalName()))
				{
				}
			}
		}
				
		return resource;
	}
	
	private static Resource buildResource(
		Map<String, ResourceBuilder> resourceBuilders,
		Element resourceElement)
	{
		if (resourceBuilders == null) { throw new IllegalArgumentException("resourceBuilders cannot be null"); }
		if (resourceElement == null) { throw new IllegalArgumentException("resourceElement cannot be null"); }
		
		UUID id = UUID.fromString(resourceElement.getAttribute(ATT_RESOURCE_ID));
		String type = resourceElement.getAttribute(ATT_RESOURCE_TYPE);
		String name = resourceElement.getAttribute(ATT_RESOURCE_NAME);

		DomResourceBuilder builder = (DomResourceBuilder)(ResourceBuilder)resourceBuilders.get(type);
		builder.reset();
		builder.setResourceElement(resourceElement);
		return builder.build(id, name);
	}
}