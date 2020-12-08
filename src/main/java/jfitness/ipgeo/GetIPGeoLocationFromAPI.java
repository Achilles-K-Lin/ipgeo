package jfitness.ipgeo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GetIPGeoLocationFromAPI {
	private static String datasource = "jdbc:mysql://localhost/ipgeo?user=root";
	private final static String url = "https://api.ipgeolocation.io/ipgeo?apiKey={apiKey}&ip={ip}";
	private final static String apiKey = "bd2275f522584b8686e9d800f193bf59";
	private static Gson gson = new Gson();

	public static void main(String[] args) {
		String ip = "211.20.122.191";
		String ipType = "IPv4";
		if (args.length > 0) {
			if (args[0].toLowerCase().indexOf("jdbc") > -1) {
				datasource = args[0];
				if (args[1] != null && args[1].length() > 0) {
					ip = args[1];
					if (ip.indexOf(":") > -1)
						ipType = "IPv6";
					IPGeoLocationResponse res = getIPGeoLocation(ip);
					if (res.getMessage() != null && res.getMessage().length() > 0)
						System.out.println(res.getMessage());
					else
						insertIntoDB(appendData2DB(res, ipType));
				} else {
					System.out.println("Please put currect ip Address, ex:172.16.168.1");
				}
			}
		} else {
			System.out.println("Please put jdbc connection string, ex:jdbc:mysql://localhost/ipgeo?user=root.");
		}
	}

	public static IPGeoLocationResponse getIPGeoLocation(String ip) {
		ClientResponse response = null;
		IPGeoLocationResponse res = null;
		long startTime = System.currentTimeMillis();
		try {

			WebResource resource = Client.create().resource(url.replace("{apiKey}", apiKey).replace("{ip}", ip));
			response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String json = response.getEntity(String.class);
			res = gson.fromJson(json, IPGeoLocationResponse.class);
			res.setJson(json);
			System.out.println(json);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		System.out.println("==============API Response Time: " + (System.currentTimeMillis() - startTime)
				+ " ms ===================");
		return res;
	}

	public static String appendData2DB(IPGeoLocationResponse res, String ipType) {
		String sql = "INSERT INTO ipaddress (ipaddress ,country_code,country_name,ip_type ,out_response) VALUES";
		String values = "";

		values += "('" + res.getIp() + "',";
		values += "'" + res.getCountry_code2() + "',";
		values += "'" + res.getCountry_name() + "',";
		values += "'" + ipType + "',";
		values += "'" + res.getJson() + "')";

		return sql + values;

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
