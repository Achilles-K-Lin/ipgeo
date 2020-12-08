package jfitness.ipgeo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadGeoFile {
	public HashMap<String, IPGEOCountry> getCountryLocations() {
		HashMap<String, IPGEOCountry> countries = new HashMap<String, IPGEOCountry>();
		InputStream is = null;
		BufferedReader br = null;

		try {
			is = this.getClass().getResourceAsStream("/ipgeo/GeoLite2-Country-Locations-en.csv");
			br = new BufferedReader(new InputStreamReader(is));			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("geoname_id") > -1)
					continue;
				IPGEOCountry cntry = new IPGEOCountry();
				String[] data = line.split(",");
				cntry.setGeoname_id(data[0]);
				cntry.setLocale_code(data[1]);
				cntry.setContinent_code(data[2]);
				cntry.setContinent_name(data[3]);
				cntry.setCountry_code(data[4]);
				cntry.setCountry_name(data[5]);
				cntry.setIs_in_european_union(data[6] == "0" ? false : true);
				countries.put(data[0], cntry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return countries;
	}

	public ArrayList<CIDRIPAddress> getCIDRNetork(HashMap<String, IPGEOCountry> countries, String fileName) {
		ArrayList<CIDRIPAddress> networks = new ArrayList<CIDRIPAddress>();
		InputStream is = null;
		BufferedReader br = null;
		try {

			is = this.getClass().getResourceAsStream("/ipgeo/" + fileName);
			br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("geoname_id") > -1)
					continue;
				String[] data = line.split(",");

				String geoNameID = getGeoNameID(data[1], data[2]);
				if (geoNameID.length() == 0) {
					System.out.println("Can't find Geo-Country ID : " + line);
					continue;
				}

				CIDRUtils CIDR = new CIDRUtils(data[0]);
				CIDRIPAddress net = new CIDRIPAddress();
				net.setNetwork(data[0]);
				net.setCountry_code(countries.get(geoNameID).getCountry_code());
				net.setCountry_name(countries.get(geoNameID).getCountry_name());
				net.setStart_ip(CIDR.getStartAddress().toString());
				net.setStart_ip_decimal(CIDR.getStartIp());
				net.setEnd_ip(CIDR.getEndAddress().toString());
				net.setEnd_ip_decimal(CIDR.getEndIp());
				networks.add(net);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return networks;
	}

	public String getGeoNameID(String geoNameID, String registerGeoNameID) {

		if (geoNameID.length() > 0)
			return geoNameID;
		else if (registerGeoNameID.length() > 0)
			return registerGeoNameID;
		else
			return "";
	}
}
