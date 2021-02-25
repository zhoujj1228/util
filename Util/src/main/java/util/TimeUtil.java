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
		return "��ʱ" + (end.get() - start.get()) + "����";
	}

	public Long getTimeUsedMillis() {
		return end.get() - start.get();
	}
}
