package com.fcc.util;

import java.util.Comparator;

import com.fcc.model.OpeningClosingTimeDto;

public class SortTimings implements Comparator<OpeningClosingTimeDto>  {

	@Override
	public int compare(OpeningClosingTimeDto o1, OpeningClosingTimeDto o2) {
		// TODO Auto-generated method stub
		return o1.getRank() - o2.getRank();
	} 

}
