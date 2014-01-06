// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb;

import co.mv.protium.system.ArgumentNullException;
import co.zd.wb.plugin.mysql.MySqlElementFixtures;
import co.zd.wb.service.dom.XmlBuilder;
import java.util.UUID;

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
		
		this.setXmlBuilder(buildXml(
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

	private UUID m_resourceId = null;
	private boolean m_resourceId_set = false;

	public UUID getResourceId() {
		if(!m_resourceId_set) {
			throw new IllegalStateException("resourceId not set.  Use the HasResourceId() method to check its state before accessing it.");
		}
		return m_resourceId;
	}

	private void setResourceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceId cannot be null");
		}
		boolean changing = !m_resourceId_set || m_resourceId != value;
		if(changing) {
			m_resourceId_set = true;
			m_resourceId = value;
		}
	}

	private void clearResourceId() {
		if(m_resourceId_set) {
			m_resourceId_set = true;
			m_resourceId = null;
		}
	}

	private boolean hasResourceId() {
		return m_resourceId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdDatabaseCreated" defaultstate="collapsed">

	private UUID m_stateIdDatabaseCreated = null;
	private boolean m_stateIdDatabaseCreated_set = false;

	public UUID getStateIdDatabaseCreated() {
		if(!m_stateIdDatabaseCreated_set) {
			throw new IllegalStateException("stateIdDatabaseCreated not set.  Use the HasStateIdDatabaseCreated() method to check its state before accessing it.");
		}
		return m_stateIdDatabaseCreated;
	}

	private void setStateIdDatabaseCreated(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdDatabaseCreated cannot be null");
		}
		boolean changing = !m_stateIdDatabaseCreated_set || m_stateIdDatabaseCreated != value;
		if(changing) {
			m_stateIdDatabaseCreated_set = true;
			m_stateIdDatabaseCreated = value;
		}
	}

	private void clearStateIdDatabaseCreated() {
		if(m_stateIdDatabaseCreated_set) {
			m_stateIdDatabaseCreated_set = true;
			m_stateIdDatabaseCreated = null;
		}
	}

	private boolean hasStateIdDatabaseCreated() {
		return m_stateIdDatabaseCreated_set;
	}

	// </editor-fold>

	// <editor-fold desc="AssertionIdDatabaseExists" defaultstate="collapsed">

	private UUID m_assertionIdDatabaseExists = null;
	private boolean m_assertionIdDatabaseExists_set = false;

	public UUID getAssertionIdDatabaseExists() {
		if(!m_assertionIdDatabaseExists_set) {
			throw new IllegalStateException("assertionIdDatabaseExists not set.  Use the HasAssertionIdDatabaseExists() method to check its state before accessing it.");
		}
		return m_assertionIdDatabaseExists;
	}

	private void setAssertionIdDatabaseExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdDatabaseExists cannot be null");
		}
		boolean changing = !m_assertionIdDatabaseExists_set || m_assertionIdDatabaseExists != value;
		if(changing) {
			m_assertionIdDatabaseExists_set = true;
			m_assertionIdDatabaseExists = value;
		}
	}

	private void clearAssertionIdDatabaseExists() {
		if(m_assertionIdDatabaseExists_set) {
			m_assertionIdDatabaseExists_set = true;
			m_assertionIdDatabaseExists = null;
		}
	}

	private boolean hasAssertionIdDatabaseExists() {
		return m_assertionIdDatabaseExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdCoreSchemaLoaded" defaultstate="collapsed">

	private UUID m_stateIdCoreSchemaLoaded = null;
	private boolean m_stateIdCoreSchemaLoaded_set = false;

	public UUID getStateIdCoreSchemaLoaded() {
		if(!m_stateIdCoreSchemaLoaded_set) {
			throw new IllegalStateException("stateIdCoreSchemaLoaded not set.  Use the HasStateIdCoreSchemaLoaded() method to check its state before accessing it.");
		}
		return m_stateIdCoreSchemaLoaded;
	}

	private void setStateIdCoreSchemaLoaded(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdCoreSchemaLoaded cannot be null");
		}
		boolean changing = !m_stateIdCoreSchemaLoaded_set || m_stateIdCoreSchemaLoaded != value;
		if(changing) {
			m_stateIdCoreSchemaLoaded_set = true;
			m_stateIdCoreSchemaLoaded = value;
		}
	}

	private void clearStateIdCoreSchemaLoaded() {
		if(m_stateIdCoreSchemaLoaded_set) {
			m_stateIdCoreSchemaLoaded_set = true;
			m_stateIdCoreSchemaLoaded = null;
		}
	}

	private boolean hasStateIdCoreSchemaLoaded() {
		return m_stateIdCoreSchemaLoaded_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeTableExists" defaultstate="collapsed">

	private UUID m_assertionIdProductTypeTableExists = null;
	private boolean m_assertionIdProductTypeTableExists_set = false;

	public UUID getAssertionIdProductTypeTableExists() {
		if(!m_assertionIdProductTypeTableExists_set) {
			throw new IllegalStateException("assertionIdProductTypeTableExists not set.  Use the HasAssertionIdProductTypeTableExists() method to check its state before accessing it.");
		}
		return m_assertionIdProductTypeTableExists;
	}

	private void setAssertionIdProductTypeTableExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeTableExists cannot be null");
		}
		boolean changing = !m_assertionIdProductTypeTableExists_set || m_assertionIdProductTypeTableExists != value;
		if(changing) {
			m_assertionIdProductTypeTableExists_set = true;
			m_assertionIdProductTypeTableExists = value;
		}
	}

	private void clearAssertionIdProductTypeTableExists() {
		if(m_assertionIdProductTypeTableExists_set) {
			m_assertionIdProductTypeTableExists_set = true;
			m_assertionIdProductTypeTableExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeTableExists() {
		return m_assertionIdProductTypeTableExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateIdInitialReferenceDataLoaded" defaultstate="collapsed">

	private UUID m_stateIdInitialReferenceDataLoaded = null;
	private boolean m_stateIdInitialReferenceDataLoaded_set = false;

	public UUID getStateIdInitialReferenceDataLoaded() {
		if(!m_stateIdInitialReferenceDataLoaded_set) {
			throw new IllegalStateException("stateIdInitialReferenceDataLoaded not set.  Use the HasStateIdInitialReferenceDataLoaded() method to check its state before accessing it.");
		}
		return m_stateIdInitialReferenceDataLoaded;
	}

	private void setStateIdInitialReferenceDataLoaded(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateIdInitialReferenceDataLoaded cannot be null");
		}
		boolean changing = !m_stateIdInitialReferenceDataLoaded_set || m_stateIdInitialReferenceDataLoaded != value;
		if(changing) {
			m_stateIdInitialReferenceDataLoaded_set = true;
			m_stateIdInitialReferenceDataLoaded = value;
		}
	}

	private void clearStateIdInitialReferenceDataLoaded() {
		if(m_stateIdInitialReferenceDataLoaded_set) {
			m_stateIdInitialReferenceDataLoaded_set = true;
			m_stateIdInitialReferenceDataLoaded = null;
		}
	}

	private boolean hasStateIdInitialReferenceDataLoaded() {
		return m_stateIdInitialReferenceDataLoaded_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeHwRowExists" defaultstate="collapsed">

	private UUID m_assertionIdProductTypeHwRowExists = null;
	private boolean m_assertionIdProductTypeHwRowExists_set = false;

	public UUID getAssertionIdProductTypeHwRowExists() {
		if(!m_assertionIdProductTypeHwRowExists_set) {
			throw new IllegalStateException("assertionIdProductTypeHwRowExists not set.  Use the HasAssertionIdProductTypeHwRowExists() method to check its state before accessing it.");
		}
		return m_assertionIdProductTypeHwRowExists;
	}

	private void setAssertionIdProductTypeHwRowExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeHwRowExists cannot be null");
		}
		boolean changing = !m_assertionIdProductTypeHwRowExists_set || m_assertionIdProductTypeHwRowExists != value;
		if(changing) {
			m_assertionIdProductTypeHwRowExists_set = true;
			m_assertionIdProductTypeHwRowExists = value;
		}
	}

	private void clearAssertionIdProductTypeHwRowExists() {
		if(m_assertionIdProductTypeHwRowExists_set) {
			m_assertionIdProductTypeHwRowExists_set = true;
			m_assertionIdProductTypeHwRowExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeHwRowExists() {
		return m_assertionIdProductTypeHwRowExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTypeSwRowExists" defaultstate="collapsed">

	private UUID m_assertionIdProductTypeSwRowExists = null;
	private boolean m_assertionIdProductTypeSwRowExists_set = false;

	public UUID getAssertionIdProductTypeSwRowExists() {
		if(!m_assertionIdProductTypeSwRowExists_set) {
			throw new IllegalStateException("assertionIdProductTypeSwRowExists not set.  Use the HasAssertionIdProductTypeSwRowExists() method to check its state before accessing it.");
		}
		return m_assertionIdProductTypeSwRowExists;
	}

	private void setAssertionIdProductTypeSwRowExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTypeSwRowExists cannot be null");
		}
		boolean changing = !m_assertionIdProductTypeSwRowExists_set || m_assertionIdProductTypeSwRowExists != value;
		if(changing) {
			m_assertionIdProductTypeSwRowExists_set = true;
			m_assertionIdProductTypeSwRowExists = value;
		}
	}

	private void clearAssertionIdProductTypeSwRowExists() {
		if(m_assertionIdProductTypeSwRowExists_set) {
			m_assertionIdProductTypeSwRowExists_set = true;
			m_assertionIdProductTypeSwRowExists = null;
		}
	}

	private boolean hasAssertionIdProductTypeSwRowExists() {
		return m_assertionIdProductTypeSwRowExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionIdProductTableExists" defaultstate="collapsed">

	private UUID m_assertionIdProductTableExists = null;
	private boolean m_assertionIdProductTableExists_set = false;

	public UUID getAssertionIdProductTableExists() {
		if(!m_assertionIdProductTableExists_set) {
			throw new IllegalStateException("assertionIdProductTableExists not set.  Use the HasAssertionIdProductTableExists() method to check its state before accessing it.");
		}
		return m_assertionIdProductTableExists;
	}

	private void setAssertionIdProductTableExists(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionIdProductTableExists cannot be null");
		}
		boolean changing = !m_assertionIdProductTableExists_set || m_assertionIdProductTableExists != value;
		if(changing) {
			m_assertionIdProductTableExists_set = true;
			m_assertionIdProductTableExists = value;
		}
	}

	private void clearAssertionIdProductTableExists() {
		if(m_assertionIdProductTableExists_set) {
			m_assertionIdProductTableExists_set = true;
			m_assertionIdProductTableExists = null;
		}
	}

	private boolean hasAssertionIdProductTableExists() {
		return m_assertionIdProductTableExists_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdCreateDatabase" defaultstate="collapsed">

	private UUID m_migrationIdCreateDatabase = null;
	private boolean m_migrationIdCreateDatabase_set = false;

	public UUID getMigrationIdCreateDatabase() {
		if(!m_migrationIdCreateDatabase_set) {
			throw new IllegalStateException("migrationIdCreateDatabase not set.  Use the HasMigrationIdCreateDatabase() method to check its state before accessing it.");
		}
		return m_migrationIdCreateDatabase;
	}

	private void setMigrationIdCreateDatabase(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdCreateDatabase cannot be null");
		}
		boolean changing = !m_migrationIdCreateDatabase_set || m_migrationIdCreateDatabase != value;
		if(changing) {
			m_migrationIdCreateDatabase_set = true;
			m_migrationIdCreateDatabase = value;
		}
	}

	private void clearMigrationIdCreateDatabase() {
		if(m_migrationIdCreateDatabase_set) {
			m_migrationIdCreateDatabase_set = true;
			m_migrationIdCreateDatabase = null;
		}
	}

	private boolean hasMigrationIdCreateDatabase() {
		return m_migrationIdCreateDatabase_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdLoadCoreSchema" defaultstate="collapsed">

	private UUID m_migrationIdLoadCoreSchema = null;
	private boolean m_migrationIdLoadCoreSchema_set = false;

	public UUID getMigrationIdLoadCoreSchema() {
		if(!m_migrationIdLoadCoreSchema_set) {
			throw new IllegalStateException("migrationIdLoadCoreSchema not set.  Use the HasMigrationIdLoadCoreSchema() method to check its state before accessing it.");
		}
		return m_migrationIdLoadCoreSchema;
	}

	private void setMigrationIdLoadCoreSchema(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdLoadCoreSchema cannot be null");
		}
		boolean changing = !m_migrationIdLoadCoreSchema_set || m_migrationIdLoadCoreSchema != value;
		if(changing) {
			m_migrationIdLoadCoreSchema_set = true;
			m_migrationIdLoadCoreSchema = value;
		}
	}

	private void clearMigrationIdLoadCoreSchema() {
		if(m_migrationIdLoadCoreSchema_set) {
			m_migrationIdLoadCoreSchema_set = true;
			m_migrationIdLoadCoreSchema = null;
		}
	}

	private boolean hasMigrationIdLoadCoreSchema() {
		return m_migrationIdLoadCoreSchema_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationIdLoadReferenceData" defaultstate="collapsed">

	private UUID m_migrationIdLoadReferenceData = null;
	private boolean m_migrationIdLoadReferenceData_set = false;

	public UUID getMigrationIdLoadReferenceData() {
		if(!m_migrationIdLoadReferenceData_set) {
			throw new IllegalStateException("migrationIdLoadReferenceData not set.  Use the HasMigrationIdLoadReferenceData() method to check its state before accessing it.");
		}
		return m_migrationIdLoadReferenceData;
	}

	private void setMigrationIdLoadReferenceData(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationIdLoadReferenceData cannot be null");
		}
		boolean changing = !m_migrationIdLoadReferenceData_set || m_migrationIdLoadReferenceData != value;
		if(changing) {
			m_migrationIdLoadReferenceData_set = true;
			m_migrationIdLoadReferenceData = value;
		}
	}

	private void clearMigrationIdLoadReferenceData() {
		if(m_migrationIdLoadReferenceData_set) {
			m_migrationIdLoadReferenceData_set = true;
			m_migrationIdLoadReferenceData = null;
		}
	}

	private boolean hasMigrationIdLoadReferenceData() {
		return m_migrationIdLoadReferenceData_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="XmlBuilder" defaultstate="collapsed">

	private XmlBuilder m_xmlBuilder = null;
	private boolean m_xmlBuilder_set = false;

	public XmlBuilder getXmlBuilder() {
		if(!m_xmlBuilder_set) {
			throw new IllegalStateException("xmlBuilder not set.  Use the HasXmlBuilder() method to check its state before accessing it.");
		}
		return m_xmlBuilder;
	}

	private void setXmlBuilder(
		XmlBuilder value) {
		if(value == null) {
			throw new IllegalArgumentException("xmlBuilder cannot be null");
		}
		boolean changing = !m_xmlBuilder_set || m_xmlBuilder != value;
		if(changing) {
			m_xmlBuilder_set = true;
			m_xmlBuilder = value;
		}
	}

	private void clearXmlBuilder() {
		if(m_xmlBuilder_set) {
			m_xmlBuilder_set = true;
			m_xmlBuilder = null;
		}
	}

	private boolean hasXmlBuilder() {
		return m_xmlBuilder_set;
	}

	// </editor-fold>

	private static XmlBuilder buildXml(
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
		if (resourceId == null) { throw new ArgumentNullException("resourceId"); }
		if (stateIdDatabaseCreated == null) { throw new ArgumentNullException("stateIdDatabaseCreated"); }
		if (assertionIdDatabaseExists == null) { throw new ArgumentNullException("assertionIdDatabaseExists"); }
		if (stateIdCoreSchemaLoadedId == null) { throw new ArgumentNullException("stateIdCoreSchemaLoadedId"); }
		if (assertionIdProductTypeTableExists == null) { throw new ArgumentNullException("assertionIdProductTypeTableExists"); }
		if (stateIdInitialReferenceDataLoaded == null) { throw new ArgumentNullException("stateIdInitialReferenceDataLoaded"); }
		if (assertionIdProductTypeHwRowExists == null) { throw new ArgumentNullException("assertionIdProductTypeHwRowExists"); }
		if (assertionIdProductTypeSwRowExists == null) { throw new ArgumentNullException("assertionIdProductTypeSwRowExists"); }
		if (assertionIdProductTableExists == null) { throw new ArgumentNullException("assertionIdProductTableExists"); }
		if (migrationIdCreateDatabase == null) { throw new ArgumentNullException("migrationIdCreateDatabase"); }
		if (migrationIdLoadSchema == null) { throw new ArgumentNullException("migrationIdLoadSchema"); }
		if (migrationIdLoadReferenceData == null) { throw new ArgumentNullException("migrationIdLoadReferenceData"); }
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openResource(resourceId, "MySqlDatabase", "Product Catalogue Database")
				.openStates()
					.openState(stateIdDatabaseCreated, "Database created")
						.openAssertions()
							.openAssertion("MySqlDatabaseExists", assertionIdDatabaseExists)
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(stateIdCoreSchemaLoadedId, "Core Schema Loaded")
						.openAssertions()
							.openAssertion("MySqlTableExists", assertionIdProductTypeTableExists)
								.openElement("tableName").text("ProductType").closeElement("tableName")
							.closeAssertion()
							.openAssertion("MySqlTableExists", assertionIdProductTableExists)
								.openElement("tableName").text("Product").closeElement("tableName")
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(stateIdInitialReferenceDataLoaded, "Reference Data Loaded")
						.openAssertions()
							.openAssertion("RowExists", assertionIdProductTypeHwRowExists)
								.openElement("description").text("ProductType HW exists").closeElement("description")
								.openElement("sql").openCdata()
									.text("SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';")
								.closeCdata().closeElement("sql")
							.closeAssertion()
							.openAssertion("RowExists", assertionIdProductTypeSwRowExists)
								.openElement("description").text("ProductType SW exists").closeElement("description")
								.openElement("sql").openCdata()
									.text("SELECT * FROM ProductType WHERE ProductTypeCode = 'SW';")
								.closeCdata().closeElement("sql")
							.closeAssertion()
						.closeAssertions()
					.closeState()
				.closeStates()
				.openMigrations()
					.openMigration("MySqlCreateDatabase", migrationIdCreateDatabase, null, stateIdDatabaseCreated)
					.closeMigration()
					.openMigration("SqlScript", migrationIdLoadSchema, stateIdDatabaseCreated, stateIdCoreSchemaLoadedId)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.productCatalogueDatabase())
						.closeCdata().closeElement("sql")
					.closeMigration()
					.openMigration("SqlScript", migrationIdLoadReferenceData, stateIdCoreSchemaLoadedId, stateIdInitialReferenceDataLoaded)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.productTypeRows())
						.closeCdata().closeElement("sql")
					.closeMigration()
				.closeMigrations()
			.closeResource();
		
		return resourceXml;
	}
}
