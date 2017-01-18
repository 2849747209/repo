package test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/1/12.
 */
public class HeapOOM {
	static class OOMObject {

	}

	public static void main(String[] args) {
		List<OOMObject> list = new LinkedList<OOMObject>();

		while (true) {
			list.add(new OOMObject());
		}
	}
}
