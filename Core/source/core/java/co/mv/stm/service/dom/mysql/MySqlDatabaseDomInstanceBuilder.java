package co.mv.stm.service.dom.mysql;

import co.mv.stm.model.Instance;
import co.mv.stm.model.mysql.MySqlDatabaseInstance;
import co.mv.stm.service.InstanceLoaderFault;
import co.mv.stm.service.dom.BaseDomInstanceBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class MySqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build()
	{
		Instance result = null;
		
		try
		{
			XPath xpath = XPathFactory.newInstance().newXPath();
			String hostName = (String)xpath.compile("hostName").evaluate(this.getElement());
			int port = Integer.parseInt((String)xpath.compile("port").evaluate(this.getElement()));
			String adminUsername = (String)xpath.compile("adminUsername").evaluate(this.getElement());
			String adminPassword = (String)xpath.compile("adminPassword").evaluate(this.getElement());
			String schemaName = (String)xpath.compile("schemaName").evaluate(this.getElement());
		
			result = new MySqlDatabaseInstance(hostName, port, adminUsername, adminPassword, schemaName);
		}
		catch (XPathExpressionException e)
		{
			throw new InstanceLoaderFault(e);
		}
		
		return result;
	}
}