package util;

public class TimeUtil {
	ThreadLocal<Long> start = new ThreadLocal<Long>();
	ThreadLocal<Long> end = new ThreadLocal<Long>();

	public void timeStart() {
		start.set(System.currentTimeMillis());
	}

	public void timeEnd() {
		end.set(System.currentTimeMillis());
	}

	public String getTimeUsed() {
		return "ºÄÊ±" + (end.get() - start.get()) + "ºÁÃë";
	}

	public Long getTimeUsedMillis() {
		return end.get() - start.get();
	}
}
