package com.strongholdmc.blcore.storage;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource
{
	private static DataSource datasource;
	private ComboPooledDataSource cpds;

	private DataSource() throws IOException, SQLException, PropertyVetoException
	{
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc driver
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/rpg");
		cpds.setUser("root");
		cpds.setPassword("enter11284");
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(180);
	}

	public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException
	{
		if (datasource == null)
		{
			datasource = new DataSource();
			return datasource;
		}
		else
		{
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException
	{
		return this.cpds.getConnection();
	}
}