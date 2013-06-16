package co.mv.stm.service.dom;

import co.mv.stm.model.Instance;
import co.mv.stm.service.InstanceBuilder;
import co.mv.stm.service.InstanceLoader;
import co.mv.stm.service.ResourceLoaderFault;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class DomInstanceLoader implements InstanceLoader
{
	private static String ELT_INSTANCE = "instance";
		private static String ATT_INSTANCE_TYPE = "type";
	private static String ELT_HOST_NAME = "hostName";
	private static String ELT_PORT = "port";
	private static String ELT_ADMIN_USERNAME = "adminUsername";
	private static String ELT_ADMIN_PASSWORD = "adminPassword";
	private static String ELT_SCHEMA_NAME = "schemaName";
	
	public DomInstanceLoader(
		Map<String, InstanceBuilder> instanceBuilder,
		String instanceXml)
	{
		this.setInstanceBuilders(instanceBuilder);
		this.setInstanceXml(instanceXml);
	}

	// <editor-fold desc="InstanceBuilders" defaultstate="collapsed">

	private Map<String, InstanceBuilder> m_instanceBuilders = null;
	private boolean m_instanceBuilders_set = false;

	private Map<String, InstanceBuilder> getInstanceBuilders() {
		if(!m_instanceBuilders_set) {
			throw new IllegalStateException("instanceBuilders not set.  Use the HasInstanceBuilders() method to check its state before accessing it.");
		}
		return m_instanceBuilders;
	}

	private void setInstanceBuilders(Map<String, InstanceBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceBuilders cannot be null");
		}
		boolean changing = !m_instanceBuilders_set || m_instanceBuilders != value;
		if(changing) {
			m_instanceBuilders_set = true;
			m_instanceBuilders = value;
		}
	}

	private void clearInstanceBuilders() {
		if(m_instanceBuilders_set) {
			m_instanceBuilders_set = true;
			m_instanceBuilders = null;
		}
	}

	private boolean hasInstanceBuilders() {
		return m_instanceBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="InstanceXml" defaultstate="collapsed">

	private String m_instanceXml = null;
	private boolean m_instanceXml_set = false;

	private String getInstanceXml() {
		if(!m_instanceXml_set) {
			throw new IllegalStateException("instanceXml not set.  Use the HasInstanceXml() method to check its state before accessing it.");
		}
		return m_instanceXml;
	}

	private void setInstanceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceXml cannot be null");
		}
		boolean changing = !m_instanceXml_set || m_instanceXml != value;
		if(changing) {
			m_instanceXml_set = true;
			m_instanceXml = value;
		}
	}

	private void clearInstanceXml() {
		if(m_instanceXml_set) {
			m_instanceXml_set = true;
			m_instanceXml = null;
		}
	}

	private boolean hasInstanceXml() {
		return m_instanceXml_set;
	}

	// </editor-fold>

	@Override public Instance load()
	{
		InputSource inputSource = new InputSource(new StringReader(this.getInstanceXml()));
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
		
		Element instanceXe = resourceXd.getDocumentElement();
		Instance instance = null;

		if (ELT_INSTANCE.equals(instanceXe.getTagName()))
		{
			instance = buildInstance(this.getInstanceBuilders(), instanceXe);
		}
		
		return instance;
	}
	
	private static Instance buildInstance(
		Map<String, InstanceBuilder> instanceBuilders,
		Element instanceXe)
	{
		if (instanceBuilders == null) { throw new IllegalArgumentException("instanceBuilders"); }
		if (instanceXe == null) { throw new IllegalArgumentException("instanceXe"); }
		
		String type = instanceXe.getAttribute(ATT_INSTANCE_TYPE);
		
		InstanceBuilder builder = instanceBuilders.get(type);
			
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"instance builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(instanceXe);
		return builder.build();
	}
}