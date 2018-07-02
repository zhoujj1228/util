package util;

public class TimeUtil {
	private long start;
	private long end;
	public void timeStart(){
		start = System.currentTimeMillis();
	}
	public void timeEnd(){
		end = System.currentTimeMillis();
	}
	public String getTimeUsed(){
		return "ºÄÊ±"+(end - start)+"ºÁÃë";
	}
}
