package util.comparator;

import java.util.Comparator;

public class StringNumComparator implements Comparator<String>{
	boolean isUp = true;;
	public static void main(String[] args) {

	}
	
	public StringNumComparator(){
	}
	
	public StringNumComparator(boolean isUp){
		this.isUp = isUp;
	}

	@Override
	public int compare(String o1, String o2) {
		double double1 = Double.parseDouble(o1);
		double double2 = Double.parseDouble(o2);
		if(double1 > double2){
			if(isUp){
				return 1;
			}else{
				return -1;
			}
		}
		if(double1 < double2){
			if(isUp){
				return -1;
			}else{
				return 1;
			}
		}
		return 0;
	}

}
