/**
 * 
 */
package com.yc.rm.caas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author stephen
 * 
 */
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int zu = 3;
		List<String> nan = new ArrayList<String>();
		nan.add("N1");
		nan.add("N2");nan.add("N3");nan.add("N4");nan.add("N5");
		List<String> nv = new ArrayList<String>();

		nv.add("v1");
		nv.add("v2");nv.add("v3");nv.add("v4");nv.add("v5");
		nv.add("v12");nv.add("v13");nv.add("v14");nv.add("v15");
		Map<Integer, List> map = new HashMap();
		for (int i = 0; i < zu; i++) {
			List<String> list = new ArrayList();
			map.put(i, list);
		}
		System.out.println(map);
		while (nan.size() > 0 || nv.size() > 0) {
			for (int i = 0; i < zu; i++) {
				String string = null;
				if (i % 2 == 0) {
					string = nan.get(RandomUtils.nextInt(0, nan.size()-1));
					nan.remove(string);
				} else {
					string = nv.get(RandomUtils.nextInt(0, nv.size()-1));
					nv.remove(string);
				}
				if (string == null) {
					break;
				}
				map.get(i).add(string);
			}
		}
		System.out.println(map);
	}

}
