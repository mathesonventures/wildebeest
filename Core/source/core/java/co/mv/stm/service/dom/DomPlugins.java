package co.mv.stm.service.dom;

import co.mv.stm.service.AssertionBuilder;
import co.mv.stm.service.InstanceBuilder;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.MigrationBuilder;
import co.mv.stm.service.dom.database.RowExistsDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlCreateDatabaseDomMigrationBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseDoesNotExistDomAssertionBuilder;
import co.mv.stm.service.dom.database.SqlScriptDomMigrationBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseDomInstanceBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseDomResourceBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseExistsDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlTableDoesNotExistDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlTableExistsDomAssertionBuilder;
import java.util.HashMap;
import java.util.Map;

public class DomPlugins
{
	public static Map<String, ResourceBuilder> resourceBuilders()
	{
		Map<String, ResourceBuilder> result = new HashMap<String, ResourceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomResourceBuilder());
		
		return result;
	}
	
	public static Map<String, AssertionBuilder> assertionBuilders()
	{
		Map<String, AssertionBuilder> result = new HashMap<String, AssertionBuilder>();
		
		result.put("RowExists", new RowExistsDomAssertionBuilder());
		result.put("MySqlDatabaseDoesNotExist", new MySqlDatabaseDoesNotExistDomAssertionBuilder());
		result.put("MySqlDatabaseExists", new MySqlDatabaseExistsDomAssertionBuilder());
		result.put("MySqlTableDoesNotExist", new MySqlTableDoesNotExistDomAssertionBuilder());
		result.put("MySqlTableExists", new MySqlTableExistsDomAssertionBuilder());
		
		return result;
	}
	
	public static Map<String, MigrationBuilder> migrationBuilders()
	{
		Map<String, MigrationBuilder> result = new HashMap<String, MigrationBuilder>();
		
		result.put("SqlScript", new SqlScriptDomMigrationBuilder());
		result.put("MySqlCreateDatabase", new MySqlCreateDatabaseDomMigrationBuilder());

		return result;
	}
	
	public static Map<String, InstanceBuilder> instanceBuilders()
	{
		Map<String, InstanceBuilder> result = new HashMap<String, InstanceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomInstanceBuilder());
		
		return result;
	}
}
