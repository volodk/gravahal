package com.test.gravahal.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MutexPool {
    
    // !!! Proper housekeeping is MANDATORY !!!
    private static ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();
    
    public static Object mutex(Object obj){
        Object newMutex = new Object();
        Object existingMutex = map.putIfAbsent(obj, newMutex ); 
        return existingMutex != null ? existingMutex : newMutex ;
    }
}
