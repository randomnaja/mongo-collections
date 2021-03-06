package com.github.mongoutils.collections;

import java.util.Map.Entry;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoMapEntry<K, V> implements Entry<K, V> {
    
    DBCollection collection;
    DBObject document;
    K key;
    DBObjectSerializer<V> serializer;
    
    public MongoMapEntry(K key, DBCollection collection, DBObject document, DBObjectSerializer<V> serializer) {
        this.document = document;
        this.collection = collection;
        this.key = key;
        this.serializer = serializer;
    }
    
    @Override
    public K getKey() {
        return key;
    }
    
    @Override
    public V getValue() {
        return serializer.toElement(document);
    }
    
    @Override
    public V setValue(V value) {
        V old = getValue();
        document.putAll(serializer.toDBObject(value, false, false));
        collection.save(document);
        return old;
    }
    
}
