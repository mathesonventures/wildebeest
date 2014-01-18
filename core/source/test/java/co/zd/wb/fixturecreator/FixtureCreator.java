package co.zd.wb.fixturecreator;

import java.util.UUID;

public class FixtureCreator
{
	public static FixtureCreator create()
	{
		return new FixtureCreator();
	}

	private FixtureCreator()
	{
		this.setRendered(false);
	}
	
	// <editor-fold desc="Rendered" defaultstate="collapsed">

	private boolean _rendered = false;
	private boolean _rendered_set = false;

	private boolean getRendered() {
		if(!_rendered_set) {
			throw new IllegalStateException("rendered not set.");
		}
		return _rendered;
	}

	private void setRendered(
		boolean value) {
		boolean changing = !_rendered_set || _rendered != value;
		if(changing) {
			_rendered_set = true;
			_rendered = value;
		}
	}

	private void clearRendered() {
		if(_rendered_set) {
			_rendered_set = true;
			_rendered = false;
		}
	}

	private boolean hasRendered() {
		return _rendered_set;
	}

	// </editor-fold>
	
	public ResourceCreator resource(String type, UUID resourceId, String name)
	{
		if (this.hasResourceCreator())
		{
			throw new RuntimeException("resource already created");
		}
		
		this.setResourceCreator(new ResourceCreator(this, type, resourceId, name));
		return this.getResourceCreator();
	}
	
	// <editor-fold desc="ResourceCreator" defaultstate="collapsed">

	private ResourceCreator _resourceCreator = null;
	private boolean _resourceCreator_set = false;

	private ResourceCreator getResourceCreator() {
		if(!_resourceCreator_set) {
			throw new IllegalStateException("resourceCreator not set.");
		}
		if(_resourceCreator == null) {
			throw new IllegalStateException("resourceCreator should not be null");
		}
		return _resourceCreator;
	}

	private void setResourceCreator(
		ResourceCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceCreator cannot be null");
		}
		boolean changing = !_resourceCreator_set || _resourceCreator != value;
		if(changing) {
			_resourceCreator_set = true;
			_resourceCreator = value;
		}
	}

	private void clearResourceCreator() {
		if(_resourceCreator_set) {
			_resourceCreator_set = true;
			_resourceCreator = null;
		}
	}

	private boolean hasResourceCreator() {
		return _resourceCreator_set;
	}

	// </editor-fold>
	
	public String render()
	{
		if (this.getRendered())
		{
			throw new RuntimeException("already rendered");
		}
		
		XmlBuilder xml = new XmlBuilder().processingInstruction();
		
		// Resource
		xml.openElement(
			"resource",
			"type", this.getResourceCreator().getType(),
			"id", this.getResourceCreator().getResourceId().toString(),
			"name", this.getResourceCreator().getName());
		
		xml.openElement("states");
		
		// States
		for (StateCreator state : this.getResourceCreator().getStates())
		{
			if (state.hasLabel())
			{
				xml.openElement("state", "id", state.getStateId().toString(), "label", state.getLabel());
			}
			else
			{
				xml.openElement("state", "id", state.getStateId().toString());
			}
			
			// Assertions
			xml.openElement("assertions");
			for (AssertionCreator assertion : state.getAssertions())
			{
				xml.openAssertion(assertion.getType(), assertion.getAssertionId());
				xml.append(assertion.getInnerXml());
				xml.closeAssertion();
			}
			xml.closeElement("assertions");
			
			xml.closeElement("state");
		}
		
		xml.closeElement("states");
		
		// Migrations
		xml.openElement("migrations");
		
		for (MigrationCreator migration : this.getResourceCreator().getMigrations())
		{
			xml.openMigration(
				migration.getType(),
				migration.getMigrationId(),
				migration.hasFromStateId() ? migration.getFromStateId() : null,
				migration.hasToStateId() ? migration.getToStateId() : null);
			xml.append(migration.getInnerXml());
			xml.closeMigration();
		}
		
		xml.closeElement("migrations");
		
		xml.closeElement("resource");
		
		return xml.toString();
	}

	private String openElement(String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		return openElement(name, new String[] { }, new String[] { });
	}
	
	private String openElement(
		String name,
		String attrName1, String attrValue1,
		String attrName2, String attrValue2,
		String attrName3, String attrValue3)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		if (attrName1 == null) { throw new IllegalArgumentException("attrName1 cannot be null"); }
		if ("".equals(attrName1)) { throw new IllegalArgumentException("attrName1 cannot be empty"); }
		if (attrValue1 == null) { throw new IllegalArgumentException("attrValue1 cannot be null"); }
		if (attrName2 == null) { throw new IllegalArgumentException("attrName2 cannot be null"); }
		if ("".equals(attrName2)) { throw new IllegalArgumentException("attrName2 cannot be empty"); }
		if (attrValue2 == null) { throw new IllegalArgumentException("attrValue2 cannot be null"); }
		if (attrName3 == null) { throw new IllegalArgumentException("attrName3 cannot be null"); }
		if ("".equals(attrName3)) { throw new IllegalArgumentException("attrName3 cannot be empty"); }
		if (attrValue3 == null) { throw new IllegalArgumentException("attrValue3 cannot be null"); }

		return openElement(
			name,
			new String[] { attrName1, attrName2, attrName3 },
			new String[] { attrValue1, attrValue2, attrValue3 });
	}
	
	private String openElement(
		String name,
		String[] attrNames,
		String[] attrValues)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		if (attrNames == null) { throw new IllegalArgumentException("attrNames cannot be null"); }
		if (attrValues == null) { throw new IllegalArgumentException("attrValues cannot be null"); }
		
		if (attrNames.length != attrValues.length)
		{
			throw new IllegalArgumentException("attrNames and attrValues must be the same length");
		}
		
		StringBuilder result = new StringBuilder();
		result.append("<").append(name);

		for (int i = 0; i < attrNames.length; i ++)
		{
			result.append(" ").append(attrNames[i]).append("=\"").append(attrValues[i]).append("\"");
		}
		
		result.append(">\n");
		
		return result.toString();
	}
	
	private String closeElement(
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		return String.format("</%s>", name);
	}
}
