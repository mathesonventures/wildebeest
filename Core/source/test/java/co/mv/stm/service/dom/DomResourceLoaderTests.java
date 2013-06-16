package co.mv.stm.service.dom;

import co.mv.stm.AssertExtensions;
import co.mv.stm.model.Resource;
import co.mv.stm.model.base.FakeAssertion;
import co.mv.stm.model.base.FakeResource;
import co.mv.stm.model.base.FakeTransition;
import co.mv.stm.service.AssertionBuilder;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.TransitionBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
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
	
	@Test public void loadResourceForStateWithOneAssertion()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo")
						.openElement("assertions")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion1Id.toString(),
								"name", "Tag is Foo")
								.openElement("tag").text("Foo").closeElement("tag")
							.closeElement("assertion")
						.closeElement("assertions")
					.closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
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
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		AssertExtensions.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Test public void loadResourceForStateWithMultipleAssertions()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		UUID assertion2Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo")
						.openElement("assertions")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion1Id.toString(),
								"name", "Tag is Foo")
								.openElement("tag").text("Foo").closeElement("tag")
							.closeElement("assertion")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion2Id.toString(),
								"name", "Tag is Bar")
								.openElement("tag").text("Bar").closeElement("tag")
							.closeElement("assertion")
						.closeElement("assertions")
					.closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
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
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			2,
			resource.getStates().get(0).getAssertions().size());
		AssertExtensions.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		AssertExtensions.assertFakeAssertion(
			assertion2Id, "Tag is Bar", 1, "Bar",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(1),
			"resource.states[0].assertions[1]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 0, resource.getTransitions().size());
		
	}
	
	@Test public void loadResourceForTransitionWithFromStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID transitionId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
				.openElement("transitions")
					.openElement("transition",
						"type", "Fake",
						"id", transitionId.toString(),
						"fromStateId", state1Id.toString())
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
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 1, resource.getTransitions().size());
		AssertExtensions.assertFakeTransition(
			transitionId, state1Id, null, "Blah",
			(FakeTransition)resource.getTransitions().get(0),
			"resource.transitions[0]");
		
	}
	
	@Test public void loadResourceForTransitionWithToStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID transitionId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "MV AAA Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
				.openElement("transitions")
					.openElement("transition",
						"type", "Fake",
						"id", transitionId.toString(),
						"toStateId", state1Id.toString())
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
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 1, resource.getTransitions().size());
		AssertExtensions.assertFakeTransition(
			transitionId, null, state1Id, "Blah",
			(FakeTransition)resource.getTransitions().get(0),
			"resource.transitions[0]");
		
	}
	
	@Test public void loadResourceForTransitionWithFromStateIdAndToStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID transitionId = UUID.randomUUID();
		
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
				.openElement("transitions")
					.openElement("transition",
						"type", "Fake",
						"id", transitionId.toString(),
						"fromStateId", state1Id.toString(),
						"toStateId", state2Id.toString())
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
		Assert.assertEquals("resource.states.size", 2, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 1, resource.getTransitions().size());
		AssertExtensions.assertFakeTransition(
			transitionId, state1Id, state2Id, "Blah",
			(FakeTransition)resource.getTransitions().get(0),
			"resource.transitions[0]");
		
	}
}