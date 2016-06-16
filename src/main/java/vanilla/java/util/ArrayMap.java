package vanilla.java.util;

import java.util.*;

/**
 * Created by Peter on 16/06/2016.
 */
public class ArrayMap<K, V> extends AbstractMap<K, V> {
    final Map<K, Integer> index;
    V[] values;

    public ArrayMap(Map<K, Integer> index, V[] values) {
        this.index = index;
        this.values = values;
    }

    @Override
    public int size() {
        return index.size();
    }

    @Override
    public boolean containsValue(Object value) {
        for (V v : values) {
            if (Objects.equals(v, value))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return index.containsKey(key);
    }

    @Override
    public V get(Object key) {
        Integer idx = index.get(key);
        return idx == null || idx >= values.length ? null : values[idx];
    }

    @Override
    public V put(K key, V value) {
        Integer idx = index.get(key);
        if (idx == null)
            throw new IllegalArgumentException("Cannot add a key " + key);
        if (values.length <= idx)
            values = Arrays.copyOf(values, idx + 1);
        V prev = values[idx];
        values[idx] = value;
        return prev;
    }

    @Override
    public Set<K> keySet() {
        return index.keySet();
    }

    @Override
    public Collection<V> values() {
        return Arrays.asList(values);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (V value : values) {
            hashCode ^= Objects.hashCode(value);
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Map<K, V> map = new LinkedHashMap<>(size(), 1.0f);
        for (Entry<K, Integer> entry : index.entrySet()) {
            K key = entry.getKey();
            Integer idx = entry.getValue();
            V value = idx < values.length ? values[idx] : null;
            map.put(key, value);
        }
        return Collections.unmodifiableMap(map).entrySet();
    }
}
