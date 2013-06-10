package co.mv.stm.service.dom;

import co.mv.stm.Assertion;
import co.mv.stm.Resource;
import co.mv.stm.State;
import co.mv.stm.Transition;
import co.mv.stm.impl.ImmutableState;
import co.mv.stm.service.AssertionBuilder;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.ResourceLoader;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.TransitionBuilder;
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
		private static final String ATT_RESOURCE_TYPE = "type";
		private static final String ATT_RESOURCE_ID = "id";
		private static final String ATT_RESOURCE_NAME = "name";
	private static final String ELT_STATES = "states";
	private static final String ELT_STATE = "state";
		private static final String ATT_STATE_ID = "id";
		private static final String ATT_STATE_LABEL = "label";
	private static final String ELT_ASSERTIONS = "assertions";
		private static final String ATT_ASSERTION_TYPE = "type";
		private static final String ATT_ASSERTION_ID = "id";
		private static final String ATT_ASSERTION_NAME = "name";
	private static final String ELT_TRANSITIONS = "transitions";
		private static final String ATT_TRANSITION_TYPE = "type";
		private static final String ATT_TRANSITION_ID = "id";
		private static final String ATT_TRANSITION_FROM_STATE_ID = "fromStateId";
		private static final String ATT_TRANSITION_TO_STATE_ID = "toStateId";
	
	public DomResourceLoader(
		Map<String, ResourceBuilder> resourceBuilders,
		Map<String, AssertionBuilder> assertionBuilders,
		Map<String, TransitionBuilder> transitionBuilders,
		String resourceXml)
	{
		this.setResourceBuilders(resourceBuilders);
		this.setAssertionBuilders(assertionBuilders);
		this.setTransitionBuilders(transitionBuilders);
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

	// <editor-fold desc="AssertionBuilders" defaultstate="collapsed">

	private Map<String, AssertionBuilder> m_assertionBuilders = null;
	private boolean m_assertionBuilders_set = false;

	private Map<String, AssertionBuilder> getAssertionBuilders() {
		if(!m_assertionBuilders_set) {
			throw new IllegalStateException("assertionBuilders not set.  Use the HasAssertionBuilders() method to check its state before accessing it.");
		}
		return m_assertionBuilders;
	}

	private void setAssertionBuilders(Map<String, AssertionBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionBuilders cannot be null");
		}
		boolean changing = !m_assertionBuilders_set || m_assertionBuilders != value;
		if(changing) {
			m_assertionBuilders_set = true;
			m_assertionBuilders = value;
		}
	}

	private void clearAssertionBuilders() {
		if(m_assertionBuilders_set) {
			m_assertionBuilders_set = true;
			m_assertionBuilders = null;
		}
	}

	private boolean hasAssertionBuilders() {
		return m_assertionBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="TransitionBuilders" defaultstate="collapsed">

	private Map<String, TransitionBuilder> m_transitionBuilders = null;
	private boolean m_transitionBuilders_set = false;

	private Map<String, TransitionBuilder> getTransitionBuilders() {
		if(!m_transitionBuilders_set) {
			throw new IllegalStateException("transitionBuilders not set.  Use the HasTransitionBuilders() method to check its state before accessing it.");
		}
		return m_transitionBuilders;
	}

	private void setTransitionBuilders(Map<String, TransitionBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("transitionBuilders cannot be null");
		}
		boolean changing = !m_transitionBuilders_set || m_transitionBuilders != value;
		if(changing) {
			m_transitionBuilders_set = true;
			m_transitionBuilders = value;
		}
	}

	private void clearTransitionBuilders() {
		if(m_transitionBuilders_set) {
			m_transitionBuilders_set = true;
			m_transitionBuilders = null;
		}
	}

	private boolean hasTransitionBuilders() {
		return m_transitionBuilders_set;
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
		Resource resource = null;

		if (ELT_RESOURCE.equals(resourceXe.getTagName()))
		{
			resource = buildResource(this.getResourceBuilders(), resourceXe);

			for (int i = 0; i < resourceXe.getChildNodes().getLength(); i ++)
			{
				Element childXe = (Element)resourceXe.getChildNodes().item(i);

				if (ELT_STATES.equals(childXe.getTagName()))
				{
					for (int stateIndex = 0; stateIndex < childXe.getChildNodes().getLength(); stateIndex ++)
					{
						Element stateXe = (Element)childXe.getChildNodes().item(stateIndex);
						State state = buildState(stateXe);
						resource.getStates().add(state);
						
						for (int stChildIndex = 0; stChildIndex < stateXe.getChildNodes().getLength(); stChildIndex ++)
						{
							Element stChildXe = (Element)stateXe.getChildNodes().item(stChildIndex);
							if (ELT_ASSERTIONS.equals(stChildXe.getTagName()))
							{
								for (int asrIndex = 0; asrIndex < stChildXe.getChildNodes().getLength(); asrIndex ++)
								{
									Element asrXe = (Element)stChildXe.getChildNodes().item(asrIndex);
									Assertion asr = buildAssertion(this.getAssertionBuilders(), asrXe, asrIndex);
									state.getAssertions().add(asr);
								}
							}
						}
					}
				}
				
				if (ELT_TRANSITIONS.equals(childXe.getTagName()))
				{
					for (int tranIndex = 0; tranIndex < childXe.getChildNodes().getLength(); tranIndex ++)
					{
						Element transitionXe = (Element)childXe.getChildNodes().item(tranIndex);
						resource.getTransitions().add(buildTransition(this.getTransitionBuilders(), transitionXe));
					}
				}
			}
		}
				
		return resource;
	}
	
	private static Resource buildResource(
		Map<String, ResourceBuilder> resourceBuilders,
		Element resourceXe)
	{
		if (resourceBuilders == null) { throw new IllegalArgumentException("resourceBuilders cannot be null"); }
		if (resourceXe == null) { throw new IllegalArgumentException("resourceXe cannot be null"); }
		
		String type = resourceXe.getAttribute(ATT_RESOURCE_TYPE);
		UUID id = UUID.fromString(resourceXe.getAttribute(ATT_RESOURCE_ID));
		String name = resourceXe.getAttribute(ATT_RESOURCE_NAME);

		DomResourceBuilder builder = (DomResourceBuilder)resourceBuilders.get(type);
		builder.reset();
		builder.setElement(resourceXe);
		return builder.build(id, name);
	}
	
	private static State buildState(
		Element element)
	{
		if (element == null) { throw new IllegalArgumentException("element"); }

		UUID id = UUID.fromString(element.getAttribute(ATT_STATE_ID));
		String label = null;
		if (element.hasAttribute(ATT_STATE_LABEL))
		{
			label = element.getAttribute(ATT_STATE_LABEL);
		}
		
		State result = null;
		
		if (label == null)
		{
			result = new ImmutableState(id);
		}
		else
		{
			result = new ImmutableState(id, label);
		}
		
		return result;
	}
	
	private static Assertion buildAssertion(
		Map<String, AssertionBuilder> assertionBuilders,
		Element element,
		int seqNum)
	{
		if (assertionBuilders == null) { throw new IllegalArgumentException("assertionBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(ATT_ASSERTION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(ATT_ASSERTION_ID));
		String name = element.getAttribute(ATT_ASSERTION_NAME);
		
		DomAssertionBuilder builder = (DomAssertionBuilder)assertionBuilders.get(type);
		builder.reset();
		builder.setElement(element);
		return builder.build(id, name, seqNum);
	}
	
	private static Transition buildTransition(
		Map<String, TransitionBuilder> transitionBuilders,
		Element element)
	{
		if (transitionBuilders == null) { throw new IllegalArgumentException("transitionBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(ATT_TRANSITION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(ATT_TRANSITION_ID));
		UUID fromStateId = null;
		if (element.hasAttribute(ATT_TRANSITION_FROM_STATE_ID))
		{
			fromStateId = UUID.fromString(element.getAttribute(ATT_TRANSITION_FROM_STATE_ID));
		}
		UUID toStateId = null;
		if (element.hasAttribute(ATT_TRANSITION_TO_STATE_ID))
		{
			toStateId = UUID.fromString(element.getAttribute(ATT_TRANSITION_TO_STATE_ID));
		}
		
		DomTransitionBuilder builder = (DomTransitionBuilder)transitionBuilders.get(type);
		builder.reset();
		builder.setElement(element);
		return builder.build(id, fromStateId, toStateId);
	}
}