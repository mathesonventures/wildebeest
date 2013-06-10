package co.mv.stm.service.dom;

import co.mv.stm.service.dom.DomResourceLoader;
import co.mv.stm.Resource;
import co.mv.stm.impl.AssertExtensions;
import co.mv.stm.impl.FakeResource;
import co.mv.stm.impl.FakeTransition;
import co.mv.stm.service.AssertionBuilder;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.TransitionBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DomResourceLoaderTests
{
	@Test public void loadResource()
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
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "MV AAA Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 0, resource.getStates().size());
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Test public void loadResourceForStateWithLabel()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "MV AAA Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Test public void loadResourceForStateWithNoLabel()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString()).closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "MV AAA Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, resource.getStates().get(0), "state[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Test public void loadResourceForMultipleStates()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
					.openElement("state", "id", state2Id.toString(), "label", "Bar").closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "MV AAA Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 2, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Ignore @Test public void loadResourceForStateWithOneAssertion()
	{
		throw new UnsupportedOperationException();
	}
	
	@Ignore @Test public void loadResourceForStateWithMultipleAssertions()
	{
		throw new UnsupportedOperationException();
	}
	
	@Test public void loadResourceForTransitionWithFromStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID fromStateId = UUID.randomUUID();
		UUID transitionId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", fromStateId.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
				.openElement("transitions")
					.openElement("transition",
						"type", "Fake",
						"id", transitionId.toString(),
						"fromStateId", fromStateId.toString())
						.openElement("tag").text("Blah").closeElement("tag")
					.closeElement("transition")
				.closeElement("transitions")
			.closeElement("resource");

		
		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		transitionBuilders.put("Fake", new DomFakeTransitionBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "MV AAA Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(fromStateId, "Foo", resource.getStates().get(0), "state[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 1, resource.getTransitions().size());
		AssertExtensions.assertFakeTransition(
			transitionId, fromStateId, null, "Blah",
			(FakeTransition)resource.getTransitions().get(0),
			"resource.transitions[0]");
		
	}
	
	@Ignore @Test public void loadResourceForTransitionWithToStateId()
	{
		throw new UnsupportedOperationException();
	}
	
	@Ignore @Test public void loadResourceForTransitionWithFromStateIdAndToStateId()
	{
		throw new UnsupportedOperationException();
	}
}