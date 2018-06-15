// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.mv.wb.fixture;

import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.mysql.MySqlElementFixtures;

import java.util.UUID;

/**
 * A MySQL database fixture.
 *
 * @since                                       1.0
 */
public class ProductCatalogueMySqlDatabaseResource
{
	private final UUID resourceId;
	private final String resourceXml;

	public static final UUID ResourceId = UUID.fromString("199b7cc1-3cc6-48ca-b012-a70d05d5b5e7");
	
	public static final UUID StateIdDatabaseCreated = UUID.fromString("c70ce835-f519-40ab-b151-c3caa08f27f5");
		public static final UUID AssertionIdDatabaseExists = UUID.fromString("3b46fa14-0859-4d3e-8b98-e546ad2593da");

	public static final UUID StateIdCoreSchemaLoaded = UUID.fromString("662e9092-f070-4146-b6f2-f62f63882443");
		public static final UUID AssertionIdProductTypeTableExists = UUID.fromString("1ae38c8c-572d-47b7-abf5-3330449beb55");
	
	public static final UUID StateIdInitialReferenceDataLoaded = UUID.fromString("25503318-22a1-462e-84e5-6413bdaa2828");
		public static final UUID AssertionIdProductTypeHwRowExists = UUID.fromString("03122757-6ce5-47ef-863a-4afe6e32164c");
		public static final UUID AssertionIdProductTypeSwRowExists = UUID.fromString("e6f87f30-4d03-4e2c-9f00-0d13cab168d6");
		public static final UUID AssertionIdProductTableExists = UUID.fromString("3be5a4d3-6fc3-42e5-8b93-637475a568d1");
	
	public static final UUID MigrationIdCreateDatabase = UUID.fromString("8908ffbd-5fb2-4eeb-98db-853c8de756c5");
	public static final UUID MigrationIdLoadCoreSchema = UUID.fromString("cbdc0cab-b2ca-432b-b1e1-64708b41d749");
	public static final UUID MigrationIdLoadReferenceData = UUID.fromString("9e67ee1f-b123-46dc-99dc-768dffc90611");
	
	public ProductCatalogueMySqlDatabaseResource()
	{
		this.resourceId = ResourceId;

		this.resourceXml = buildXml(
			this.getResourceId(),
			StateIdDatabaseCreated,
			AssertionIdDatabaseExists,
			StateIdCoreSchemaLoaded,
			AssertionIdProductTypeTableExists,
			AssertionIdProductTableExists,
			StateIdInitialReferenceDataLoaded,
			AssertionIdProductTypeHwRowExists,
			AssertionIdProductTypeSwRowExists,
			MigrationIdCreateDatabase,
			MigrationIdLoadCoreSchema,
			MigrationIdLoadReferenceData);
	}
	
	public final UUID getResourceId()
	{
		return this.resourceId;
	}

	public String getResourceXml()
	{
		return this.resourceXml;
	}

	private static String buildXml(
		UUID resourceId,
		UUID stateIdDatabaseCreated,
			UUID assertionIdDatabaseExists,
		UUID stateIdCoreSchemaLoadedId,
			UUID assertionIdProductTypeTableExists,
			UUID assertionIdProductTableExists,
		UUID stateIdInitialReferenceDataLoaded,
			UUID assertionIdProductTypeHwRowExists,
			UUID assertionIdProductTypeSwRowExists,
		UUID migrationIdCreateDatabase,
		UUID migrationIdLoadSchema,
		UUID migrationIdLoadReferenceData)
	{
		if (resourceId == null) { throw new IllegalArgumentException("resourceId cannot be null"); }
		if (stateIdDatabaseCreated == null) { throw new IllegalArgumentException("stateIdDatabaseCreated cannot be null"); }
		if (assertionIdDatabaseExists == null) { throw new IllegalArgumentException("assertionIdDatabaseExists cannot be null"); }
		if (stateIdCoreSchemaLoadedId == null) { throw new IllegalArgumentException("stateIdCoreSchemaLoadedId cannot be null"); }
		if (assertionIdProductTypeTableExists == null) { throw new IllegalArgumentException("assertionIdProductTypeTableExists cannot be null"); }
		if (stateIdInitialReferenceDataLoaded == null) { throw new IllegalArgumentException("stateIdInitialReferenceDataLoaded cannot be null"); }
		if (assertionIdProductTypeHwRowExists == null) { throw new IllegalArgumentException("assertionIdProductTypeHwRowExists cannot be null"); }
		if (assertionIdProductTypeSwRowExists == null) { throw new IllegalArgumentException("assertionIdProductTypeSwRowExists cannot be null"); }
		if (assertionIdProductTableExists == null) { throw new IllegalArgumentException("assertionIdProductTableExists cannot be null"); }
		if (migrationIdCreateDatabase == null) { throw new IllegalArgumentException("migrationIdCreateDatabase cannot be null"); }
		if (migrationIdLoadSchema == null) { throw new IllegalArgumentException("migrationIdLoadSchema cannot be null"); }
		if (migrationIdLoadReferenceData == null) { throw new IllegalArgumentException("migrationIdLoadReferenceData cannot be null"); }
		
		String resourceXml = FixtureBuilder.create()
			.resource(Wildebeest.MySqlDatabase.getUri(), resourceId, "Product Catalogue Database")
				.state(stateIdDatabaseCreated, "Database created")
					.assertion("DatabaseExists", assertionIdDatabaseExists)
				.state(stateIdCoreSchemaLoadedId, "Core Schema Loaded")
					.assertion("MySqlTableExists", assertionIdProductTypeTableExists)
						.withInnerXml("<tableName>ProductType</tableName>")
					.assertion("MySqlTableExists", assertionIdProductTableExists)
						.withInnerXml("<tableName>Product</tableName>")
				.state(stateIdInitialReferenceDataLoaded, "Reference Data Loaded")
					.assertion("RowExists", assertionIdProductTypeHwRowExists)
						.appendInnerXml("<description>ProductType HW exists</description>")
						.appendInnerXml("<sql>SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';</sql>")
					.assertion("RowExists", assertionIdProductTypeSwRowExists)
						.appendInnerXml("<description>ProductType SW exists</description>")
						.appendInnerXml("<sql>SELECT * FROM ProductType WHERE ProductTypeCode = 'SW';</sql>")
				.migration("MySqlCreateDatabase", migrationIdCreateDatabase, null, stateIdDatabaseCreated)
				.migration("SqlScript", migrationIdLoadSchema, stateIdDatabaseCreated, stateIdCoreSchemaLoadedId)
					.withInnerXml("<sql><![CDATA[" + MySqlElementFixtures.productCatalogueDatabase() + "]]></sql>")
				.migration("SqlScript", migrationIdLoadReferenceData, stateIdCoreSchemaLoadedId, stateIdInitialReferenceDataLoaded)
					.withInnerXml("<sql><![CDATA[" + MySqlElementFixtures.productTypeRows() + "]]></sql>")
			.render();
			
		return resourceXml;
	}
}
