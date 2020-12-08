package jfitness.ipgeo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadIPGeo {
	static String datasource = "jdbc:mysql://localhost/ipgeo?user=root";
	final static int max_row = 3000;

	public static void main(String[] args) {

		if (args.length > 0 && args[0].toLowerCase().indexOf("jdbc") > -1) {
			datasource = args[0];
			System.out.println("==========================Start==================================");
			long startTime = System.currentTimeMillis();
			ReadGeoFile rf = new ReadGeoFile();
			HashMap<String, IPGEOCountry> geoCountries = rf.getCountryLocations();

			appendData2DB(rf.getCIDRNetork(geoCountries, "GeoLite2-Country-Blocks-IPv4.csv"), "IPv4");

			appendData2DB(rf.getCIDRNetork(geoCountries, "GeoLite2-Country-Blocks-IPv6.csv"), "IPv6");

			System.out.println("============== Total Execute Time : " + (System.currentTimeMillis() - startTime)
					+ " ms ===================");
		} else {
			System.out.println("Please put jdbc connection string, ex:jdbc:mysql://localhost/ipgeo?user=root.");
		}
	}

	public static void appendData2DB(ArrayList<CIDRIPAddress> datas, String ipType) {
		String sql = "INSERT INTO ipgeocidr (network,start_ip,end_ip,start_ip_bytes,end_ip_bytes,country_code,country_name,ip_type) VALUES";
		String values = "";
		int index = 0;
		while (index < datas.size()) {
			CIDRIPAddress data = datas.get(index);
			values += "('" + data.getNetwork() + "',";
			values += "'" + data.getStart_ip().replace("/", "") + "',";
			values += "'" + data.getEnd_ip().replace("/", "") + "',";
			if (ipType.equals("IPv4"))
				values += "inet_aton('" + data.getStart_ip().replace("/", "") + "'),";
			if (ipType.equals("IPv4"))
				values += "inet_aton('" + data.getEnd_ip().replace("/", "") + "'),";
			if (ipType.equals("IPv6"))
				values += "inet6_aton('" + data.getStart_ip().replace("/", "") + "'),";
			if (ipType.equals("IPv6"))
				values += "inet6_aton('" + data.getEnd_ip().replace("/", "") + "'),";
			values += "'" + data.getCountry_code() + "',";
			values += "'" + data.getCountry_name().replace("\"", "") + "',";
			values += "'" + ipType + "')";
			index++;
			if (index % max_row == 0 || index == datas.size()) {
				insertIntoDB(sql + values);
				values = "";
			}
			if (index % max_row != 0 && index < datas.size())
				values += ",";

		}
		System.out.println("============== " + ipType + " Total Records: " + index + " rows ===================");

	}

	public static void insertIntoDB(String insertSql) {
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement prepsInsertProduct = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(datasource);
			prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

			prepsInsertProduct.execute();
			resultSet = prepsInsertProduct.getGeneratedKeys();
			while (resultSet.next()) {
				System.out.println("Generated: " + resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			try {
				if (prepsInsertProduct != null)
					prepsInsertProduct.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {

			}
		}
	}

}
