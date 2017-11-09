package fr.inria;

import java.util.*;
import java.util.concurrent.*;

public class App
{

    public static void main( String[] args ) {
        Deque<String> c0 = new java.util.concurrent.LinkedBlockingDeque<>();
        BlockingDeque<String> c1= new java.util.concurrent.LinkedBlockingDeque<>();
        //BlockingQueue<String> c2 = new java.util.concurrent.ArrayBlockingQueue<>();
        List<String> c3 = new java.util.ArrayList<>();
        TransferQueue<String> c4 = new java.util.concurrent.LinkedTransferQueue<>();
        Queue<String> c5 = new java.util.PriorityQueue<>();
        NavigableSet<String> c6 = new java.util.TreeSet<>();
        SortedSet<String> c7 = new java.util.TreeSet<>();
        Set<String> c8 = new HashSet<>();
        Collection<String> c9 = new ArrayList<>();
        Iterable<String> c10 = new ArrayList<>();
        ConcurrentNavigableMap<String,String> c11 = new java.util.concurrent.ConcurrentSkipListMap<>();
        ConcurrentMap<String,String> c12 = new ConcurrentHashMap<>();
        NavigableMap<String,String> c13 = new TreeMap<>();
        SortedMap<String,String> c14 = new TreeMap<>();
        Map<String,String> c15 = new Hashtable<>();

    }
}