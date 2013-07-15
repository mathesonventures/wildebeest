package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Instance;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.service.dom.BaseDomInstanceBuilder;

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