package util;

import java.util.concurrent.atomic.AtomicInteger;

public class SeqNoUtil {
	public static void main(String[] args) {
		SeqNoUtil seqNoUtil = new SeqNoUtil(8, 99999998);
		for (int i = 0; i < 10000000; i++) {
			System.out.println(seqNoUtil.createSeqNo());
		}
	}
	
	AtomicInteger seqNo;
	int seqNoLength;
	int maxSeqNo;
	SeqNoUtil(int seqNoLength){
		init(seqNoLength, 0);
	}
	SeqNoUtil(int seqNoLength, int initNo){
		init(seqNoLength, initNo);
	}
	public void init(int seqNoLength, int initNo){
		seqNo = new AtomicInteger(initNo);
		this.seqNoLength = seqNoLength;
		setMaxSeqNo();
	}
	
	private void setMaxSeqNo() {
		String maxSeqNoStr = "";
		for(int i = 0; i < seqNoLength; i++){
			maxSeqNoStr = maxSeqNoStr + "9";
		}
		maxSeqNo = Integer.parseInt(maxSeqNoStr);
	}
	public String createSeqNo(){
		int currSeqNoInt = seqNo.incrementAndGet();
		if(currSeqNoInt > maxSeqNo){
			seqNo = new AtomicInteger(0);
			currSeqNoInt = seqNo.incrementAndGet();
		}
		String currSeqNo = currSeqNoInt + "";
		currSeqNo = fillZero(currSeqNo);
		return currSeqNo;
	}
	private String fillZero(String currSeqNo) {
		for(int i = currSeqNo.length(); i < seqNoLength; i++){
			currSeqNo = "0" + currSeqNo;
		}
		return currSeqNo;
	}
	
}
