package co.mv.stm.service.dom.mysql;

import co.mv.stm.model.Instance;
import co.mv.stm.model.mysql.MySqlDatabaseInstance;
import co.mv.stm.service.dom.BaseDomInstanceBuilder;

public class MySqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build()
	{
		String hostName = this.getString("hostName");
		int port = this.getInteger("port");
		String adminUsername = this.getString("adminUsername");
		String adminPassword = this.getString("adminPassword");
		String schemaName = this.getString("schemaName");

		Instance result = new MySqlDatabaseInstance(hostName, port, adminUsername, adminPassword, schemaName);
		
		return result;
	}
}