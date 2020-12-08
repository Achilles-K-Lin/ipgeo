package jfitness.ipgeo;

public class IPGEOCountry {
	private String geoname_id;
	private String locale_code;
	private String continent_code;
	private String continent_name;
	private String country_code;
	private String country_name;
	private boolean is_in_european_union;

	public String getGeoname_id() {
		return geoname_id;
	}

	public void setGeoname_id(String geoname_id) {
		this.geoname_id = geoname_id;
	}

	public String getLocale_code() {
		return locale_code;
	}

	public void setLocale_code(String locale_code) {
		this.locale_code = locale_code;
	}

	public String getContinent_code() {
		return continent_code;
	}

	public void setContinent_code(String continent_code) {
		this.continent_code = continent_code;
	}

	public String getContinent_name() {
		return continent_name;
	}

	public void setContinent_name(String continent_name) {
		this.continent_name = continent_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public boolean isIs_in_european_union() {
		return is_in_european_union;
	}

	public void setIs_in_european_union(boolean is_in_european_union) {
		this.is_in_european_union = is_in_european_union;
	}

}
