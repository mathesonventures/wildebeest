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

import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.plugin.mysql.MySqlElementFixtures;

import java.util.UUID;

/**
 * A MySQL database fixture.
 *
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class ProductCatalogueMySqlDatabaseResource
{
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
		this.setResourceId(ResourceId);
		this.setStateIdDatabaseCreated(StateIdDatabaseCreated);
			this.setAssertionIdDatabaseExists(AssertionIdDatabaseExists);
		this.setStateIdCoreSchemaLoaded(StateIdCoreSchemaLoaded);
			this.setAssertionIdProductTypeTableExists(AssertionIdProductTypeTableExists);
			this.setAssertionIdProductTableExists(AssertionIdProductTableExists);
		this.setStateIdInitialReferenceDataLoaded(StateIdInitialReferenceDataLoaded);
			this.setAssertionIdProductTypeHwRowExists(AssertionIdProductTypeHwRowExists);
			this.setAssertionIdProductTypeSwRowExists(AssertionIdProductTypeSwRowExists);
		this.setMigrationIdCreateDatabase(MigrationIdCreateDatabase);
		this.setMigrationIdLoadCoreSchema(MigrationIdLoadCoreSchema);
		this.setMigrationIdLoadReferenceData(MigrationIdLoadReferenceData);
		
		this.setResourceXml(buildXml(
			this.getResourceId(),
			this.getStateIdDatabaseCreated(),
				this.getAssertionIdDatabaseExists(),
			this.getStateIdCoreSchemaLoaded(),
				this.getAssertionIdProductTypeTableExists(),
				this.getAssertionIdProductTableExists(),
			this.getStateIdInitialReferenceDataLoaded(),
				this.getAssertionIdProductTypeHwRowExists(),
				this.getAssertionIdProductTypeSwRowExists(),
			this.getMigrationIdCreateDatabase(),
			this.getMigrationIdLoadCoreSchema(),
			this.getMigrationIdLoadReferenceData()));
	}
	
	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID _resourceId = null;
	private boolean _resourceId_set = false;

	public final UUID getResourceId() {
		if(!_resourceId_set) {
			throw new IllegalStateException("resourceId not set.  Use the HasResourceId() method to check its state before accessing it.");
		}
		return _resourceId;
	}

	private void setResourceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceId cannot be null");
		}
		boolean changing = !_resourceId_set || _resourceId != value;
		if(changing) {
			_resourceId_set = true;
			_resourceId = value;
		}
	}

	private void clearResourceId() {
		if(_resourceId_set) {
			_resourceId_set = true;
			_resourceId = null;
		}
	}

	private boolean hasResourceId() {
		return _resourceId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdDatabaseCreated" defaultstate="collapsed">

	private UUID _stateIdDatabaseCreated = null;
	private boolean _stateIdDatabaseCreated_set = false;

	public final UUID getStateIdDatabaseCreated() {
		if(!_stateIdDatabaseCreated_set) {
			throw new IllegalStateException("stateIdDatabaseCreated not set.  Use the HasStateIdDatabaseCreated() method to check its state before accessing it.");
		}
		return _stateIdDatabaseCreated;
	}

	private void setStateIdDatabaseCreated(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdDatabaseCreated cannot be null");
		}
		boolean changing = !_stateIdDatabaseCreated_set || _stateIdDatabaseCreated != value;
		if(changing) {
			_stateIdDatabaseCreated_set = true;
			_stateIdDatabaseCreated = value;
		}
	}

	private void clearStateIdDatabaseCreated() {
		if(_stateIdDatabaseCreated_set) {
			_stateIdDatabaseCreated_set = true;
			_stateIdDatabaseCreated = null;
		}
	}

	private boolean hasStateIdDatabaseCreated() {
		return _stateIdDatabaseCreated_set;
	}

	// </editor-fold>

	// <editor-fold desc="AssertionIdDatabaseExists" defaultstate="collapsed">

	private UUID _assertionIdDatabaseExists = null;
	private boolean _assertionIdDatabaseExists_set = false;

	public final UUID getAssertionIdDatabaseExists() {
		if(!_assertionIdDatabaseExists_set) {
			throw new IllegalStateException("assertionIdDatabaseExists not set.  Use the HasAssertionIdDatabaseExists() method to check its state before accessing it.");
		}
		return _assertionIdDatabaseExists;
	}

	private void setAssertionIdDatabaseExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdDatabaseExists cannot be null");
		}
		boolean changing = !_assertionIdDatabaseExists_set || _assertionIdDatabaseExists != value;
		if(changing) {
			_assertionIdDatabaseExists_set = true;
			_assertionIdDatabaseExists = value;
		}
	}

	private void clearAssertionIdDatabaseExists() {
		if(_assertionIdDatabaseExists_set) {
			_assertionIdDatabaseExists_set = true;
			_assertionIdDatabaseExists = null;
		}
	}

	private boolean hasAssertionIdDatabaseExists() {
		return _assertionIdDatabaseExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdCoreSchemaLoaded" defaultstate="collapsed">

	private UUID _stateIdCoreSchemaLoaded = null;
	private boolean _stateIdCoreSchemaLoaded_set = false;

	public final UUID getStateIdCoreSchemaLoaded() {
		if(!_stateIdCoreSchemaLoaded_set) {
			throw new IllegalStateException("stateIdCoreSchemaLoaded not set.  Use the HasStateIdCoreSchemaLoaded() method to check its state before accessing it.");
		}
		return _stateIdCoreSchemaLoaded;
	}

	private void setStateIdCoreSchemaLoaded(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdCoreSchemaLoaded cannot be null");
		}
		boolean changing = !_stateIdCoreSchemaLoaded_set || _stateIdCoreSchemaLoaded != value;
		if(changing) {
			_stateIdCoreSchemaLoaded_set = true;
			_stateIdCoreSchemaLoaded = value;
		}
	}

	private void clearStateIdCoreSchemaLoaded() {
		if(_stateIdCoreSchemaLoaded_set) {
			_stateIdCoreSchemaLoaded_set = true;
			_stateIdCoreSchemaLoaded = null;
		}
	}

	private boolean hasStateIdCoreSchemaLoaded() {
		return _stateIdCoreSchemaLoaded_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeTableExists" defaultstate="collapsed">

	private UUID _assertionIdProductTypeTableExists = null;
	private boolean _assertionIdProductTypeTableExists_set = false;

	public final UUID getAssertionIdProductTypeTableExists() {
		if(!_assertionIdProductTypeTableExists_set) {
			throw new IllegalStateException("assertionIdProductTypeTableExists not set.  Use the HasAssertionIdProductTypeTableExists() method to check its state before accessing it.");
		}
		return _assertionIdProductTypeTableExists;
	}

	private void setAssertionIdProductTypeTableExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeTableExists cannot be null");
		}
		boolean changing = !_assertionIdProductTypeTableExists_set || _assertionIdProductTypeTableExists != value;
		if(changing) {
			_assertionIdProductTypeTableExists_set = true;
			_assertionIdProductTypeTableExists = value;
		}
	}

	private void clearAssertionIdProductTypeTableExists() {
		if(_assertionIdProductTypeTableExists_set) {
			_assertionIdProductTypeTableExists_set = true;
			_assertionIdProductTypeTableExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeTableExists() {
		return _assertionIdProductTypeTableExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdInitialReferenceDataLoaded" defaultstate="collapsed">

	private UUID _stateIdInitialReferenceDataLoaded = null;
	private boolean _stateIdInitialReferenceDataLoaded_set = false;

	public final UUID getStateIdInitialReferenceDataLoaded() {
		if(!_stateIdInitialReferenceDataLoaded_set) {
			throw new IllegalStateException("stateIdInitialReferenceDataLoaded not set.  Use the HasStateIdInitialReferenceDataLoaded() method to check its state before accessing it.");
		}
		return _stateIdInitialReferenceDataLoaded;
	}

	private void setStateIdInitialReferenceDataLoaded(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdInitialReferenceDataLoaded cannot be null");
		}
		boolean changing = !_stateIdInitialReferenceDataLoaded_set || _stateIdInitialReferenceDataLoaded != value;
		if(changing) {
			_stateIdInitialReferenceDataLoaded_set = true;
			_stateIdInitialReferenceDataLoaded = value;
		}
	}

	private void clearStateIdInitialReferenceDataLoaded() {
		if(_stateIdInitialReferenceDataLoaded_set) {
			_stateIdInitialReferenceDataLoaded_set = true;
			_stateIdInitialReferenceDataLoaded = null;
		}
	}

	private boolean hasStateIdInitialReferenceDataLoaded() {
		return _stateIdInitialReferenceDataLoaded_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeHwRowExists" defaultstate="collapsed">

	private UUID _assertionIdProductTypeHwRowExists = null;
	private boolean _assertionIdProductTypeHwRowExists_set = false;

	public final UUID getAssertionIdProductTypeHwRowExists() {
		if(!_assertionIdProductTypeHwRowExists_set) {
			throw new IllegalStateException("assertionIdProductTypeHwRowExists not set.  Use the HasAssertionIdProductTypeHwRowExists() method to check its state before accessing it.");
		}
		return _assertionIdProductTypeHwRowExists;
	}

	private void setAssertionIdProductTypeHwRowExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeHwRowExists cannot be null");
		}
		boolean changing = !_assertionIdProductTypeHwRowExists_set || _assertionIdProductTypeHwRowExists != value;
		if(changing) {
			_assertionIdProductTypeHwRowExists_set = true;
			_assertionIdProductTypeHwRowExists = value;
		}
	}

	private void clearAssertionIdProductTypeHwRowExists() {
		if(_assertionIdProductTypeHwRowExists_set) {
			_assertionIdProductTypeHwRowExists_set = true;
			_assertionIdProductTypeHwRowExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeHwRowExists() {
		return _assertionIdProductTypeHwRowExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeSwRowExists" defaultstate="collapsed">

	private UUID _assertionIdProductTypeSwRowExists = null;
	private boolean _assertionIdProductTypeSwRowExists_set = false;

	public final UUID getAssertionIdProductTypeSwRowExists() {
		if(!_assertionIdProductTypeSwRowExists_set) {
			throw new IllegalStateException("assertionIdProductTypeSwRowExists not set.  Use the HasAssertionIdProductTypeSwRowExists() method to check its state before accessing it.");
		}
		return _assertionIdProductTypeSwRowExists;
	}

	private void setAssertionIdProductTypeSwRowExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeSwRowExists cannot be null");
		}
		boolean changing = !_assertionIdProductTypeSwRowExists_set || _assertionIdProductTypeSwRowExists != value;
		if(changing) {
			_assertionIdProductTypeSwRowExists_set = true;
			_assertionIdProductTypeSwRowExists = value;
		}
	}

	private void clearAssertionIdProductTypeSwRowExists() {
		if(_assertionIdProductTypeSwRowExists_set) {
			_assertionIdProductTypeSwRowExists_set = true;
			_assertionIdProductTypeSwRowExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeSwRowExists() {
		return _assertionIdProductTypeSwRowExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTableExists" defaultstate="collapsed">

	private UUID _assertionIdProductTableExists = null;
	private boolean _assertionIdProductTableExists_set = false;

	public final UUID getAssertionIdProductTableExists() {
		if(!_assertionIdProductTableExists_set) {
			throw new IllegalStateException("assertionIdProductTableExists not set.  Use the HasAssertionIdProductTableExists() method to check its state before accessing it.");
		}
		return _assertionIdProductTableExists;
	}

	private void setAssertionIdProductTableExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTableExists cannot be null");
		}
		boolean changing = !_assertionIdProductTableExists_set || _assertionIdProductTableExists != value;
		if(changing) {
			_assertionIdProductTableExists_set = true;
			_assertionIdProductTableExists = value;
		}
	}

	private void clearAssertionIdProductTableExists() {
		if(_assertionIdProductTableExists_set) {
			_assertionIdProductTableExists_set = true;
			_assertionIdProductTableExists = null;
		}
	}

	private boolean hasAssertionIdProductTableExists() {
		return _assertionIdProductTableExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdCreateDatabase" defaultstate="collapsed">

	private UUID _migrationIdCreateDatabase = null;
	private boolean _migrationIdCreateDatabase_set = false;

	public final UUID getMigrationIdCreateDatabase() {
		if(!_migrationIdCreateDatabase_set) {
			throw new IllegalStateException("migrationIdCreateDatabase not set.  Use the HasMigrationIdCreateDatabase() method to check its state before accessing it.");
		}
		return _migrationIdCreateDatabase;
	}

	private void setMigrationIdCreateDatabase(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdCreateDatabase cannot be null");
		}
		boolean changing = !_migrationIdCreateDatabase_set || _migrationIdCreateDatabase != value;
		if(changing) {
			_migrationIdCreateDatabase_set = true;
			_migrationIdCreateDatabase = value;
		}
	}

	private void clearMigrationIdCreateDatabase() {
		if(_migrationIdCreateDatabase_set) {
			_migrationIdCreateDatabase_set = true;
			_migrationIdCreateDatabase = null;
		}
	}

	private boolean hasMigrationIdCreateDatabase() {
		return _migrationIdCreateDatabase_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdLoadCoreSchema" defaultstate="collapsed">

	private UUID _migrationIdLoadCoreSchema = null;
	private boolean _migrationIdLoadCoreSchema_set = false;

	public final UUID getMigrationIdLoadCoreSchema() {
		if(!_migrationIdLoadCoreSchema_set) {
			throw new IllegalStateException("migrationIdLoadCoreSchema not set.  Use the HasMigrationIdLoadCoreSchema() method to check its state before accessing it.");
		}
		return _migrationIdLoadCoreSchema;
	}

	private void setMigrationIdLoadCoreSchema(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdLoadCoreSchema cannot be null");
		}
		boolean changing = !_migrationIdLoadCoreSchema_set || _migrationIdLoadCoreSchema != value;
		if(changing) {
			_migrationIdLoadCoreSchema_set = true;
			_migrationIdLoadCoreSchema = value;
		}
	}

	private void clearMigrationIdLoadCoreSchema() {
		if(_migrationIdLoadCoreSchema_set) {
			_migrationIdLoadCoreSchema_set = true;
			_migrationIdLoadCoreSchema = null;
		}
	}

	private boolean hasMigrationIdLoadCoreSchema() {
		return _migrationIdLoadCoreSchema_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdLoadReferenceData" defaultstate="collapsed">

	private UUID _migrationIdLoadReferenceData = null;
	private boolean _migrationIdLoadReferenceData_set = false;

	public final UUID getMigrationIdLoadReferenceData() {
		if(!_migrationIdLoadReferenceData_set) {
			throw new IllegalStateException("migrationIdLoadReferenceData not set.  Use the HasMigrationIdLoadReferenceData() method to check its state before accessing it.");
		}
		return _migrationIdLoadReferenceData;
	}

	private void setMigrationIdLoadReferenceData(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdLoadReferenceData cannot be null");
		}
		boolean changing = !_migrationIdLoadReferenceData_set || _migrationIdLoadReferenceData != value;
		if(changing) {
			_migrationIdLoadReferenceData_set = true;
			_migrationIdLoadReferenceData = value;
		}
	}

	private void clearMigrationIdLoadReferenceData() {
		if(_migrationIdLoadReferenceData_set) {
			_migrationIdLoadReferenceData_set = true;
			_migrationIdLoadReferenceData = null;
		}
	}

	private boolean hasMigrationIdLoadReferenceData() {
		return _migrationIdLoadReferenceData_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="ResourceXml" defaultstate="collapsed">

	private String _resourceXml = null;
	private boolean _resourceXml_set = false;

	public String getResourceXml() {
		if(!_resourceXml_set) {
			throw new IllegalStateException("resourceXml not set.");
		}
		if(_resourceXml == null) {
			throw new IllegalStateException("resourceXml should not be null");
		}
		return _resourceXml;
	}

	private void setResourceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceXml cannot be null");
		}
		boolean changing = !_resourceXml_set || !_resourceXml.equals(value);
		if(changing) {
			_resourceXml_set = true;
			_resourceXml = value;
		}
	}

	private void clearResourceXml() {
		if(_resourceXml_set) {
			_resourceXml_set = true;
			_resourceXml = null;
		}
	}

	private boolean hasResourceXml() {
		return _resourceXml_set;
	}

	// </editor-fold>

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
		
		String resourceXml = FixtureCreator.create()
			.resource(FactoryResourceTypes.MySqlDatabase.getUri(), resourceId, "Product Catalogue Database")
				.state(stateIdDatabaseCreated, "Database created")
					.assertion("DatabaseExists", assertionIdDatabaseExists)
				.state(stateIdCoreSchemaLoadedId, "Core Schema Loaded")
					.assertion("MySqlTableExists", assertionIdProductTypeTableExists)
						.innerXml("<tableName>ProductType</tableName>")
					.assertion("MySqlTableExists", assertionIdProductTableExists)
						.innerXml("<tableName>Product</tableName>")
				.state(stateIdInitialReferenceDataLoaded, "Reference Data Loaded")
					.assertion("RowExists", assertionIdProductTypeHwRowExists)
						.appendInnerXml("<description>ProductType HW exists</description>")
						.appendInnerXml("<sql>SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';</sql>")
					.assertion("RowExists", assertionIdProductTypeSwRowExists)
						.appendInnerXml("<description>ProductType SW exists</description>")
						.appendInnerXml("<sql>SELECT * FROM ProductType WHERE ProductTypeCode = 'SW';</sql>")
				.migration("MySqlCreateDatabase", migrationIdCreateDatabase, null, stateIdDatabaseCreated)
				.migration("SqlScript", migrationIdLoadSchema, stateIdDatabaseCreated, stateIdCoreSchemaLoadedId)
					.innerXml("<sql><![CDATA[" + MySqlElementFixtures.productCatalogueDatabase() + "]]></sql>")
				.migration("SqlScript", migrationIdLoadReferenceData, stateIdCoreSchemaLoadedId, stateIdInitialReferenceDataLoaded)
					.innerXml("<sql><![CDATA[" + MySqlElementFixtures.productTypeRows() + "]]></sql>")
			.render();
			
		return resourceXml;
	}
}
