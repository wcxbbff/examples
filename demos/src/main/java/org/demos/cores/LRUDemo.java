package org.demos.cores;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LRU是Least Recently Used
 * 的缩写，翻译过来就是“最近最少使用”，LRU缓存就是使用这种原理实现，简单的说就是缓存一定量的数据，当超过设定的阈值时就把一些过期的数据删除掉
 * ，比如我们缓存10000条数据
 * ，当数据小于10000时可以随意添加，当超过10000时就需要把新的数据添加进来，同时要把过期数据删除，以确保我们最大缓存10000条
 * ，那怎么确定删除哪条过期数据呢，采用LRU算法实现的话就是将最老的数据删掉
 * 
 * @Description TODO
 * @Date Dec 14, 2018 10:04:19 AM
 * @Author allen
 */
public class LRUDemo extends LinkedHashMap<String, String> {

	/**
	 * @Description TODO
	 * @Author allen
	 * @Date Dec 14, 2018 10:17:03 AM
	 * @Fields serialVersionUID TODO
	 */
	private static final long serialVersionUID = -3493152764673260891L;
	private static final int MAX_SIZE = 4;

	LRUDemo() {
		/**
		 * 当参数accessOrder为true时，即会按照访问顺序排序，最近访问的放在后面，最早访问的前面 重写removeEldestEntry
		 */
		super((int) Math.ceil(MAX_SIZE / 0.75) + 1, 0.75f, true);
	}

	@Override
	public boolean removeEldestEntry(Map.Entry<String, String> eldest) {
		return  size() > MAX_SIZE;
	}

	public static void main(String[] args) {
		//方法1
		Map<String,String> linkedHashMap = new LRUDemo();
		//多线程下实现同步
		linkedHashMap=Collections.synchronizedMap(linkedHashMap);
		linkedHashMap.put("1", "1");
		linkedHashMap.put("2", "2");
		linkedHashMap.put("3", "3");
		linkedHashMap.put("4", "4");
		linkedHashMap.put("5", "5");
		linkedHashMap.get("3");
		linkedHashMap.forEach((key, value) -> {
			System.out.print(" '"+key + ":" + value+"'");
		});
		//方法2 更简单
		Map  LinkedHashMap2=new LinkedHashMap<String,String>((int) Math.ceil(MAX_SIZE / 0.75) + 1, 0.75f, true){
			@Override
		    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
		    return size() > MAX_SIZE;
		    }
		};
		//多线程下实现同步
		LinkedHashMap2=Collections.synchronizedMap(LinkedHashMap2);
		Map dd=new ConcurrentHashMap<>();
		 
	}
	
}
