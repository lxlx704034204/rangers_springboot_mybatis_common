package cn.rangers.develop.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author VenCano
 *
 */
public class Monitor {
        private static ThreadLocal<Long> beginThread = new ThreadLocal<Long>();
     
        public static void begin() {
            beginThread.set(System.currentTimeMillis());
        }
     
        public static void end(String name) {
            double time = (System.currentTimeMillis() - beginThread.get()) / 1000.0;
            System.out.println(name + "所用时间（秒）：" + time);
        }
        
        public static Double end(){
        	return (System.currentTimeMillis() - beginThread.get()) / 1000.0;
        }
        
        @SuppressWarnings("rawtypes")
		public static void printCollection(Object object){
        	if(object instanceof List){
        		for(int i=0;i<((List) object).size();i++){
        			System.out.println(((List) object).get(i));
        		}
        	}
        	if(object instanceof Map){
        		for (Iterator iterator = ((HashMap) object).entrySet().iterator(); iterator.hasNext();) {
					@SuppressWarnings("unchecked")
					Entry<Object,Object> entry = (Entry<Object, Object>) iterator.next();
					System.out.println(entry.getKey()+"====="+entry.getValue());
				}
        	}
        }
}
