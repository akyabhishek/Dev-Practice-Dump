package com.spring.Framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BinarySearchImpl {
	
	@Autowired
	@Qualifier("quick")
	private SortAlgorithm sortAlgorithm;	



	public int binarySerach(int[] numbers,int numberToSearchFor) {
		

		int[] numbers1 =sortAlgorithm.sort(numbers);
		return 3;
	}

}
