package ru.innotech.education.rxjava;

import java.util.*;
import java.util.stream.Collectors;

public class AppendOnlyListOfMap<T> extends AbstractCollection<T>
        implements Collection<T> {
    private final List<Map.Entry<T, Boolean>> elementData;
    private int countList;

    public AppendOnlyListOfMap() {
        this.elementData = new ArrayList<>();
    }

    public AppendOnlyListOfMap(Collection<T> c) {
        this();
        addAll(c);
    }

    @Override
    public int size() {
        return countList;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Map.Entry<T, Boolean> elementDatum : elementData) {
            if (elementDatum.getKey().equals(o) && !elementDatum.getValue())
                return true;
        }
        return false;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
         return new ArrayList<>(elementData).stream()
                 .map(Map.Entry::getKey)
                 .filter(this::contains)
                 .toArray();
    }

    @Override
    public <K> K[] toArray(K[] a) {
        return new ArrayList<>(elementData).stream()
                .map(Map.Entry::getKey)
                .filter(this::contains)
                .collect(Collectors.toList())
                .toArray(a);
    }

    @Override
    public synchronized boolean add(T t) {
        elementData.add(Map.entry(t, false));
        countList++;
        return true;
    }

    @Override
    public synchronized boolean remove(Object o) {
        int idx = elementData.indexOf(Map.entry(o, false));
        if (idx < 0) return false;
        elementData.set(idx, Map.entry((T) o, true));
        countList--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean findElement = false;
        for (Object element : c) {
            if (remove(element))
                findElement = true;
        }
        return findElement;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean findElement = false;
        for (Object o : this) {
            if (!c.contains(o)) {
                remove(o);
                findElement = true;
            }
        }
        return findElement;
    }

    @Override
    public void clear() {
        removeAll(elementData);
    }


    private final class Itr implements Iterator<T> {
        volatile int cursor = 0;
        int lastObj = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            for (int n = cursor; n < elementData.size(); n++) {
                if (elementData.get(n).getValue()) continue;
                lastObj = n;
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            synchronized (this) {
                if (cursor >= elementData.size())
                    throw new NoSuchElementException();
                int i = cursor;
                if (hasNext()) {
                    cursor = lastObj;
                }
                if (elementData.get(i).getValue()) {
                    i = lastObj;
                }
                cursor++;
                return elementData.get(i).getKey();
            }
        }
    }

}
