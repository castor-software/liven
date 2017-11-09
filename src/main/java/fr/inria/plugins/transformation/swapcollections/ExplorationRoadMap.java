package fr.inria.plugins.transformation.swapcollections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spoon.compiler.Environment;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;
import java.util.stream.Collectors;


public class ExplorationRoadMap {

    private Set<TransformationPoint> roadMap = new HashSet<>();
    private Map<String,Set<String>> interfaces = new HashMap<>();

    public ExplorationRoadMap () {
        init();
    }

    public JSONObject getJSONRoadMap() throws JSONException {
        JSONObject res = new JSONObject();
        JSONArray a = new JSONArray();
        for(TransformationPoint tp: roadMap) {
            a.put(tp.toJSON());
        }
        res.put("TransformationPoints", a);
        return res;
    }


    public void processClass(CtClass clazz) {
        System.out.println("Transform class: " + clazz.getQualifiedName());
        try {
            clazz.getElements(new TypeFilter<>(CtConstructorCall.class)).stream().forEach(
                    cc -> processConstructorCall(cc)
            );
        } catch (Exception e) {
            System.err.println("Error when processing " + clazz.getQualifiedName());
        }
    }

    public boolean processConstructorCall(CtConstructorCall cc) {
        try {
            Factory f = cc.getFactory();
            CtTypedElement parent = cc.getParent(CtTypedElement.class);
            if (parent.getType() == null) return false;
            if (parent.getType().getModifiers().contains(ModifierKind.STATIC)) return false;
            if (cc.getType().getModifiers().contains(ModifierKind.STATIC)) return false;
            if (cc.getType().getQualifiedName() == parent.getType().getQualifiedName()) return false;
            if (interfaces.keySet().contains(parent.getType().getQualifiedName())) {
                TransformationPoint tp = new TransformationPoint(cc.getPosition().getCompilationUnit().getMainType().getQualifiedName(),
                        cc.getPosition().getLine(),cc.toString());

                String type = parent.getType().getActualTypeArguments().stream().
                        map(Object::toString).
                        collect(Collectors.joining(",")).toString();
                String param = cc.getArguments().stream().
                        map(Object::toString).
                        collect(Collectors.joining(",")).toString();
                for(String newType: interfaces.get(parent.getType().getQualifiedName())) {
                    String newCall = "new " + newType + "<" + type + ">("+ param + ")";
                    tp.addAlternative(newCall);
                }
                roadMap.add(tp);
                return true;
            }
            else return false;
        } catch (Exception e) {}
        return false;
    }


    private void init() {

        Set<String> lList = new HashSet<>();
        lList.add("java.util.ArrayList");
        lList.add("java.util.LinkedList");
        //lList.add("java.util.Stack");
        lList.add("java.util.Vector");
        lList.add("java.util.concurrent.CopyOnWriteArrayList");
        Set<String> lBlockingDeque = new HashSet<>();
        lBlockingDeque.add("java.util.concurrent.LinkedBlockingDeque");
        Set<String> lDeque = new HashSet<>();
        lDeque.addAll(lBlockingDeque);
        lDeque.add("java.util.ArrayDeque");
        lDeque.add("java.util.LinkedList");
        lDeque.add("java.util.concurrent.ConcurrentLinkedDeque");
        Set<String> lBlockingQueue = new HashSet<>();
        lBlockingQueue.addAll(lBlockingDeque);
        lBlockingQueue.add("java.util.concurrent.ArrayBlockingQueue");
        lBlockingQueue.add("java.util.concurrent.DelayQueue");
        lBlockingQueue.add("java.util.concurrent.LinkedBlockingQueue");
        lBlockingQueue.add("java.util.concurrent.LinkedTransferQueue");
        lBlockingQueue.add("java.util.concurrent.PriorityBlockingQueue");
        lBlockingQueue.add("java.util.concurrent.SynchronousQueue");
        Set<String> lTransferQueue = new HashSet<>();
        lTransferQueue.add("java.util.concurrent.LinkedTransferQueue");
        Set<String> lQueue = new HashSet<>();
        lQueue.addAll(lDeque);
        lQueue.addAll(lBlockingQueue);
        lQueue.addAll(lTransferQueue);
        lQueue.add("java.util.concurrent.ConcurrentLinkedQueue");
        lQueue.add("java.util.concurrent.PriorityBlockingQueue");
        lQueue.add("java.util.concurrent.SynchronousQueue");
        lQueue.add("java.util.PriorityQueue");


        Set<String> lNavigableSet = new HashSet<>();
        lNavigableSet.add("java.util.concurrent.ConcurrentSkipListSet");
        lNavigableSet.add("java.util.TreeSet");
        Set<String> lSortedSet = new HashSet<>();
        lSortedSet.addAll(lNavigableSet);
        Set<String> lSet = new HashSet<>();
        lSet.addAll(lSortedSet);
        lSet.add("java.util.concurrent.CopyOnWriteArraySet");
        lSet.add("java.util.HashSet");
        lSet.add("java.util.LinkedHashSet");

        Set<String> lCollection = new HashSet<>();
        lCollection.addAll(lSet);
        lCollection.addAll(lQueue);
        lCollection.addAll(lList);
        Set<String> lIterable = new HashSet<>();
        lIterable.addAll(lCollection);

        Set<String> lConcurrentNavigableMap = new HashSet<>();
        lConcurrentNavigableMap.add("java.util.concurrent.ConcurrentSkipListMap");
        Set<String> lConcurrentMap = new HashSet<>();
        lConcurrentMap.addAll(lConcurrentNavigableMap);
        lConcurrentMap.add("java.util.concurrent.ConcurrentHashMap");
        Set<String> lNavigableMap = new HashSet<>();
        lNavigableMap.addAll(lConcurrentNavigableMap);
        lNavigableMap.add("java.util.TreeMap");
        Set<String> lSortedMap = new HashSet<>();
        lSortedMap.addAll(lNavigableMap);
        Set<String> lMap = new HashSet<>();
        lMap.addAll(lConcurrentMap);
        lMap.addAll(lSortedMap);
        lMap.add("java.util.HashMap");
        lMap.add("java.util.Hashtable");
        lMap.add("java.util.HashMap");
        lMap.add("java.util.LinkedHashMap");
        lMap.add("java.util.WeakHashMap");

        //Commons collections

        lList.add("org.apache.commons.collections4.list.TreeList");
        lList.add("org.apache.commons.collections4.list.GrowthList");
        lList.add("org.apache.commons.collections4.list.NodeCachingLinkedList");
        lList.add("org.apache.commons.collections4.list.CursorableLinkedList");
        //lList.add("org.apache.commons.collections4.ArrayStack");

        lSet.add("org.apache.commons.collections4.set.ListOrderedSet");

        lQueue.add("org.apache.commons.collections4.queue.CircularFifoQueue");

        lMap.add("org.apache.commons.collections4.map.CaseInsensitiveMap");
        lMap.add("org.apache.commons.collections4.map.Flat3Map");
        lMap.add("org.apache.commons.collections4.map.HashedMap");
        lMap.add("org.apache.commons.collections4.map.LRUMap");
        lMap.add("org.apache.commons.collections4.map.LinkedMap");
        lMap.add("org.apache.commons.collections4.map.ListOrderedMap");
        lMap.add("org.apache.commons.collections4.map.PassiveExpiringMap");
        lMap.add("org.apache.commons.collections4.map.ReferenceIdentityMap");
        lMap.add("org.apache.commons.collections4.map.ReferenceMap");
        lMap.add("org.apache.commons.collections4.map.SingletonMap");
        lMap.add("org.apache.commons.collections4.map.StaticBucketMap");



        //Save
        interfaces.put("java.util.List", lList);
        interfaces.put("java.util.concurrent.BlockingDeque", lBlockingDeque);
        interfaces.put("java.util.Deque", lDeque);
        interfaces.put("java.util.concurrent.BlockingQueue", lBlockingQueue);
        interfaces.put("java.util.concurrent.TransferQueue", lTransferQueue);
        interfaces.put("java.util.Queue", lQueue);
        interfaces.put("java.util.NavigableSet", lNavigableSet);
        interfaces.put("java.util.SortedSet", lSortedSet);
        interfaces.put("java.util.Set", lSet);
        interfaces.put("java.util.Collection", lCollection);
        interfaces.put("java.util.Iterable", lIterable);
        interfaces.put("java.util.concurrent.ConcurrentNavigableMap", lConcurrentNavigableMap);
        interfaces.put("java.util.concurrent.ConcurrentMap", lConcurrentMap);
        interfaces.put("java.util.NavigableMap", lNavigableMap);
        interfaces.put("java.util.SortedMap", lSortedMap);
        interfaces.put("java.util.Map", lMap);

    }
}
