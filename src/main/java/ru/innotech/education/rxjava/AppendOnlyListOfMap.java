package ru.innotech.education.rxjava;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AppendOnlyListOfMap<T> extends AbstractCollection<T>
        implements Collection<T> {
    private final List<Map.Entry<T, Boolean>> elementData;
    private final AtomicInteger countList = new AtomicInteger(0);

    public AppendOnlyListOfMap() {
        this.elementData = new ArrayList<>();
    }

    public AppendOnlyListOfMap(final Collection<T> c) {
        this();
        addAll(c);
    }

    @Override
    public int size() {
        return countList.get();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for (final Map.Entry<T, Boolean> elementDatum : elementData) {
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
    public <K> K[] toArray(final K[] a) {
        return new ArrayList<>(elementData).stream()
                .map(Map.Entry::getKey)
                .filter(this::contains)
                .collect(Collectors.toList())
                .toArray(a);
    }

    @Override
    public synchronized boolean add(final T t) {
        elementData.add(Map.entry(t, false));
        countList.incrementAndGet();
        return true;
    }

    @Override
    public synchronized boolean remove(final Object o) {
        final int idx = elementData.indexOf(Map.entry(o, false));
        if (idx < 0) return false;
        elementData.set(idx, Map.entry((T) o, true));
        countList.decrementAndGet();
        return true;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean findElement = false;
        for (final Object element : c) {
            if (remove(element))
                findElement = true;
        }
        return findElement;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        Objects.requireNonNull(c);
        boolean findElement = false;
        for (final Object o : this) {
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
        private volatile int cursor = 0;
        private int lastObj = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            synchronized (this) {
                for (int n = cursor; n < elementData.size(); n++) {
                    if (elementData.get(n).getValue()) continue;
                    lastObj = n;
                    return true;
                }
                return false;
            }
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
