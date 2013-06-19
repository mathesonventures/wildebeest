package co.mv.stm.service.dom;

import java.util.UUID;

public class XmlBuilder
{
	public XmlBuilder()
	{
		this.setStringBuilder(new StringBuilder());
	}
	
	// <editor-fold desc="StringBuilder" defaultstate="collapsed">

	private StringBuilder m_stringBuilder = null;
	private boolean m_stringBuilder_set = false;

	public StringBuilder getStringBuilder() {
		if(!m_stringBuilder_set) {
			throw new IllegalStateException("stringBuilder not set.  Use the HasStringBuilder() method to check its state before accessing it.");
		}
		return m_stringBuilder;
	}

	private void setStringBuilder(
		StringBuilder value) {
		if(value == null) {
			throw new IllegalArgumentException("stringBuilder cannot be null");
		}
		boolean changing = !m_stringBuilder_set || m_stringBuilder != value;
		if(changing) {
			m_stringBuilder_set = true;
			m_stringBuilder = value;
		}
	}

	private void clearStringBuilder() {
		if(m_stringBuilder_set) {
			m_stringBuilder_set = true;
			m_stringBuilder = null;
		}
	}

	private boolean hasStringBuilder() {
		return m_stringBuilder_set;
	}

	// </editor-fold>
	
	public XmlBuilder processingInstruction()
	{
		this.getStringBuilder().append("<?xml version=\"1.0\"?>");
		
		return this;
	}
	
	public XmlBuilder element(String name)
	{
		this.getStringBuilder().append("<").append(name).append(" />");
		
		return this;
	}
	
	public XmlBuilder element(
		String name,
		String attr1Name, String attr1Value,
		String attr2Name, String attr2Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\"")
			.append(" />");
		
		return this;
	}
	
	public XmlBuilder openElement(String name)
	{
		this.getStringBuilder().append("<").append(name).append(">");
		
		return this;
	}
	
	public XmlBuilder openElement(
		String name,
		String attr1Name, String attr1Value,
		String attr2Name, String attr2Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\"")
			.append(">");
		
		return this;
	}
	
	public XmlBuilder openElement(
		String name,
		String attr1Name, String attr1Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\"")
			.append(">");
		
		return this;
	}
	
	public XmlBuilder openElement(
		String name,
		String attr1Name, String attr1Value,
		String attr2Name, String attr2Value,
		String attr3Name, String attr3Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\" ")
			.append(attr3Name).append("=\"").append(attr3Value).append("\"")
			.append(">");
		
		return this;
	}
	
	public XmlBuilder openElement(
		String name,
		String attr1Name, String attr1Value,
		String attr2Name, String attr2Value,
		String attr3Name, String attr3Value,
		String attr4Name, String attr4Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\" ")
			.append(attr3Name).append("=\"").append(attr3Value).append("\" ")
			.append(attr4Name).append("=\"").append(attr4Value).append("\"")
			.append(">");
		
		return this;
	}
	
	public XmlBuilder closeElement(String name)
	{
		this.getStringBuilder().append("</").append(name).append(">");

		return this;
	}
	
	public XmlBuilder openCdata()
	{
		this.getStringBuilder().append("<![CDATA[");

		return this;
	}
	
	public XmlBuilder closeCdata()
	{
		this.getStringBuilder().append("]]>");

		return this;
	}
	
	public XmlBuilder text(String text)
	{
		this.getStringBuilder().append(text);
		
		return this;
	}
	
	public XmlBuilder openResource(
		UUID resourceId,
		String type,
		String name)
	{
		if (resourceId == null) { throw new IllegalArgumentException("resourceId"); }
		if (type == null) { throw new IllegalArgumentException("type"); }
		if ("".equals(type)) { throw new IllegalArgumentException("type cannot be empty"); }
		if (name == null) { throw new IllegalArgumentException("name"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		return this.openElement("resource", "id", resourceId.toString(), "type", type, "name", name);
	}
	
	public XmlBuilder closeResource()
	{
		return this.closeElement("resource");
	}
	
	public XmlBuilder openStates()
	{
		return this.openElement("states");
	}
	
	public XmlBuilder closeStates()
	{
		return this.closeElement("states");
	}
	
	public XmlBuilder state(
		UUID stateId,
		String label)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }
		if (label == null) { throw new IllegalArgumentException("label"); }
		if ("".equals(label)) { throw new IllegalArgumentException("label cannot be empty"); }
		
		this.element("state", "id", stateId.toString(), "label", label);
		
		return this;
	}
	
	public XmlBuilder openState(
		UUID stateId,
		String label)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }
		if (label == null) { throw new IllegalArgumentException("label"); }
		if ("".equals(label)) { throw new IllegalArgumentException("label cannot be empty"); }
		
		return this.openElement("state", "id", stateId.toString(), "label", label);
	}
	
	public XmlBuilder closeState()
	{
		return this.closeElement("state");
	}
	
	public XmlBuilder openAssertions()
	{
		return this.openElement("assertions");
	}
	
	public XmlBuilder closeAssertions()
	{
		return this.closeElement("assertions");
	}
	
	public XmlBuilder openAssertion(
		String type,
		UUID assertionId,
		String name)
	{
		if (type == null) { throw new IllegalArgumentException("type cannot be null"); }
		if ("".equals(type)) { throw new IllegalArgumentException("type cannot be empty"); }
		if (assertionId == null) { throw new IllegalArgumentException("assertionId cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
	
		return this.openElement(
			"assertion",
			"type", type,
			"id", assertionId.toString(),
			"name", name);
	}
	
	public XmlBuilder closeAssertion()
	{
		return this.closeElement("assertion");
	}
	
	public XmlBuilder openMigrations()
	{
		return this.openElement("migrations");
	}
	
	public XmlBuilder closeMigrations()
	{
		return this.closeElement("migrations");
	}
	
	public XmlBuilder openMigration(
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		if (type == null) { throw new IllegalArgumentException("type cannot be null"); }
		if ("".equals(type)) { throw new IllegalArgumentException("type cannot be empty"); }
		if (migrationId == null) { throw new IllegalArgumentException("migrationId cannot be null"); }
		if (fromStateId == null && toStateId == null)
		{
			throw new IllegalArgumentException("at least one of fromStateId and toStateId must be provided");
		}

		if (fromStateId != null && toStateId != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"fromStateId", fromStateId.toString(),
				"toStateId", toStateId.toString());
		}
		else if (fromStateId != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"fromStateId", fromStateId.toString());
		}
		else if (toStateId != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"toStateId", toStateId.toString());
		}
		
		return this;
	}
	
	public XmlBuilder closeMigration()
	{
		return this.closeElement("migration");
	}
	
	@Override public String toString()
	{
		return this.getStringBuilder().toString();
	}
}
