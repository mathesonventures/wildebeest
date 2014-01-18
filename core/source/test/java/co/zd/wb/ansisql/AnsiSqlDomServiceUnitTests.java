package co.zd.wb.ansisql;

import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.MigrationBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.service.dom.ansisql.AnsiSqlCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourceBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class AnsiSqlDomServiceUnitTests
{
	@Test public void ansiSqlCreateMigrationLoadFromValidDocumentSucceeds() throws MessagesException
	{
		
		//
		// Setup
		//
		
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\"?>\n")
			.append("<resource type=\"PostgreSqlDatabase\" id=\"38d0eabd-ab40-4c37-96a7-fcacb43bd059\" ")
				.append("name=\"Product Catalogue Database\">\n")
			
				.append("<states>\n")
					.append("<state id=\"5e9981fa-3b1b-4ea9-8547-b50f7ef2aa79\" label=\"Database Created\">\n")
					.append("</state>\n")
				.append("</states>\n")
			
				.append("<migrations>\n")
					.append("<migration type=\"AnsiSqlCreateDatabase\" id=\"637d1065-caa6-41a0-81b1-ac95af43380f\" ")
						.append("toStateId=\"5e9981fa-3b1b-4ea9-8547-b50f7ef2aa79\">\n")
					.append("</migration>\n")
				.append("</migrations>\n")
			
			.append("</resource>\n");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourceBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("AnsiSqlCreateDatabase", new AnsiSqlCreateDatabaseDomMigrationBuilder());
		
		DomResourceLoader loader = new DomResourceLoader(
			resourceBuilders,
			new HashMap<String, AssertionBuilder>(),
			migrationBuilders,
			xml.toString());

		//
		// Execute
		//
		
		Resource resource = loader.load();
		
		//
		// Verify
		//
		
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AnsiSqlCreateDatabaseMigration mT = ModelExtensions.As(
			resource.getMigrations().get(0),
			AnsiSqlCreateDatabaseMigration.class);
		Assert.assertNotNull("resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration", mT);
		Assert.assertEquals(
			"resource.migrations[0].id",
			UUID.fromString("637d1065-caa6-41a0-81b1-ac95af43380f"),
			mT.getMigrationId());
		Assert.assertFalse(
			"resource.migrations[0].hasFromStateId",
			mT.hasFromStateId());
		Assert.assertEquals(
			"resource.migrations[0].toStateId",
			UUID.fromString("5e9981fa-3b1b-4ea9-8547-b50f7ef2aa79"),
			mT.getToStateId());
		
	}
}
