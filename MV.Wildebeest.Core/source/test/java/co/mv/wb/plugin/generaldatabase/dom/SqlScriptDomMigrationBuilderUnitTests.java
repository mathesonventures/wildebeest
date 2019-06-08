package co.mv.wb.plugin.generaldatabase.dom;

import co.mv.wb.Asserts;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.LoaderFault;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.fixture.Fixtures;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.base.dom.DomPlugins;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.postgresql.PostgreSqlConstants;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public class SqlScriptDomMigrationBuilderUnitTests
{
	@Test
	public void loadSqlScript_withNoSplit_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{
		// Setup
		UUID migrationId = UUID.randomUUID();
		UUID toStateId = UUID.randomUUID();

		String xml = Fixtures
			.resourceXmlBuilder()
			.resource(PostgreSqlConstants.PostgreSqlDatabase.getUri(), UUID.randomUUID(), "Foo")
			.migration("SqlScript", migrationId, null, toStateId.toString())
			.withInnerXml("<sql>UPDATE foo SET bar = 'bup';</sql>")
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			xml);

		// Execute
		Resource resource = loader.load(new File("."));

		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		SqlScriptMigration mT = ModelExtensions.as(
			resource.getMigrations().get(0),
			SqlScriptMigration.class);
		Assert.assertNotNull(
			"resourceWithPlugin.resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration",
			mT);
		Asserts.assertSqlScriptMigration(
			migrationId,
			Optional.empty(),
			Optional.of(toStateId.toString()),
			"UPDATE foo SET bar = 'bup';",
			true,
			mT,
			"migration0");
	}

	@Test
	public void loadSqlScript_withSplitTrue_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{
		// Setup
		UUID migrationId = UUID.randomUUID();
		UUID toStateId = UUID.randomUUID();

		String xml = Fixtures
			.resourceXmlBuilder()
			.resource(PostgreSqlConstants.PostgreSqlDatabase.getUri(), UUID.randomUUID(), "Foo")
			.migration("SqlScript", migrationId, null, toStateId.toString())
			.withInnerXml("<split>true</split><sql>UPDATE foo SET bar = 'bup';</sql>")
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			xml);

		// Execute
		Resource resource = loader.load(new File("."));

		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		SqlScriptMigration mT = ModelExtensions.as(
			resource.getMigrations().get(0),
			SqlScriptMigration.class);
		Assert.assertNotNull(
			"resourceWithPlugin.resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration",
			mT);
		Asserts.assertSqlScriptMigration(
			migrationId,
			Optional.empty(),
			Optional.of(toStateId.toString()),
			"UPDATE foo SET bar = 'bup';",
			true,
			mT,
			"migration0");
	}

	@Test
	public void loadSqlScript_withSplitFalse_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{
		// Setup
		UUID migrationId = UUID.randomUUID();
		UUID toStateId = UUID.randomUUID();

		String xml = Fixtures
			.resourceXmlBuilder()
			.resource(PostgreSqlConstants.PostgreSqlDatabase.getUri(), UUID.randomUUID(), "Foo")
			.migration("SqlScript", migrationId, null, toStateId.toString())
			.withInnerXml("<split>false</split><sql>UPDATE foo SET bar = 'bup';</sql>")
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			xml);

		// Execute
		Resource resource = loader.load(new File("."));

		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		SqlScriptMigration mT = ModelExtensions.as(
			resource.getMigrations().get(0),
			SqlScriptMigration.class);
		Assert.assertNotNull(
			"resourceWithPlugin.resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration",
			mT);
		Asserts.assertSqlScriptMigration(
			migrationId,
			Optional.empty(),
			Optional.of(toStateId.toString()),
			"UPDATE foo SET bar = 'bup';",
			false,
			mT,
			"migration0");
	}
}
