package jfitness.ipgeo;

public class TimeZone {
	private String name;
	private int offset;
	private String current_time;
	private double current_time_unix;
	private boolean is_dst;
	private int dst_savings;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}

	public double getCurrent_time_unix() {
		return current_time_unix;
	}

	public void setCurrent_time_unix(double current_time_unix) {
		this.current_time_unix = current_time_unix;
	}

	public boolean isIs_dst() {
		return is_dst;
	}

	public void setIs_dst(boolean is_dst) {
		this.is_dst = is_dst;
	}

	public int getDst_savings() {
		return dst_savings;
	}

	public void setDst_savings(int dst_savings) {
		this.dst_savings = dst_savings;
	}

}
