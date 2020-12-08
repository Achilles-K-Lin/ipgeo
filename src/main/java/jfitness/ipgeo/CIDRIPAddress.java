package jfitness.ipgeo;

import java.math.BigInteger;

public class CIDRIPAddress {
	private String network;
	private String start_ip;
	private String end_ip;
	private BigInteger start_ip_decimal;
	private BigInteger end_ip_decimal;
	private String country_code;
	private String country_name;

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getStart_ip() {
		return start_ip;
	}

	public void setStart_ip(String start_ip) {
		this.start_ip = start_ip;
	}

	public String getEnd_ip() {
		return end_ip;
	}

	public void setEnd_ip(String end_ip) {
		this.end_ip = end_ip;
	}

	public BigInteger getStart_ip_decimal() {
		return start_ip_decimal;
	}

	public void setStart_ip_decimal(BigInteger start_ip_decimal) {
		this.start_ip_decimal = start_ip_decimal;
	}

	public BigInteger getEnd_ip_decimal() {
		return end_ip_decimal;
	}

	public void setEnd_ip_decimal(BigInteger end_ip_decimal) {
		this.end_ip_decimal = end_ip_decimal;
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

	@Override
	public String toString() {
		return "CIDRIPAddress [network=" + network + ", start_ip=" + start_ip + ", end_ip=" + end_ip
				+ ", start_ip_decimal=" + start_ip_decimal + ", end_ip_decimal=" + end_ip_decimal + ", country_code="
				+ country_code + ", country_name=" + country_name + "]";
	}

}
