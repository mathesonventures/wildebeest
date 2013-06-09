package co.mv.stm.service.impl.dom;

import co.mv.stm.Resource;
import co.mv.stm.impl.FakeResource;
import co.mv.stm.service.ResourceBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class DomResourceLoaderTests
{
	@Test public void loadResourceSucceeds()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.class", FakeResource.class, resource.getClass());
		Assert.assertEquals("resource.resourceId", resourceId, resource.getResourceId());
		Assert.assertEquals("resource.name", "MV AAA Database", resource.getName());
		
	}
}