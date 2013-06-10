package co.mv.stm.service.dom;

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
	
	public XmlBuilder text(String text)
	{
		this.getStringBuilder().append(text);
		
		return this;
	}
	
	@Override public String toString()
	{
		return this.getStringBuilder().toString();
	}
}
