package Pages;

import java.util.Iterator;
import java.util.StringTokenizer;

public class javacode {

	public static void main(String[] args) {
		int[] arr= {5,8,9,3,2,4};
	
		int i = 0; int j= arr.length-1;
		
		while (i<j) {
			int temp=arr[i];
			arr[i]=arr[j];
			arr[j]=temp;
			i++;
			j--;
			
		}
	for (int j1 : arr) {
		System.out.println(j1);
	}
	}
}