package ru.innotech.education.rxjava;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AppendOnlyList<T> extends AbstractCollection<T>
        implements Collection<T> {
    private final List<T> elementData;
    private final List<Boolean> listOfDelete;
    private final AtomicInteger countList = new AtomicInteger(0);
    private final Object sync = new Object();

    public AppendOnlyList() {
        this.elementData = new ArrayList<>();
        this.listOfDelete = new ArrayList<>();
    }

    public AppendOnlyList(final Collection<T> c) {
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
        for (int i = 0; i < elementData.size(); i++) {
            if (elementData.get(i).equals(o) && !listOfDelete.get(i))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return new ArrayList<>(elementData)
                .stream()
                .filter(this::contains)
                .toArray();
    }

    @Override
    public <K> K[] toArray(final K[] a) {
        return new ArrayList<>(elementData)
                .stream()
                .filter(this::contains)
                .collect(Collectors.toList())
                .toArray(a);
    }

    @Override
    public boolean add(final T t) {
        synchronized (sync) {
            elementData.add(t);
            listOfDelete.add(false);
            countList.incrementAndGet();
            return true;
        }
    }

    @Override
    public boolean remove(final Object o) {
        synchronized (sync) {
            int idx = elementData.indexOf(o);
            if (idx < 0) return false;
            listOfDelete.set(idx, true);
            countList.decrementAndGet();
            return true;
        }
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
            synchronized (sync) {
                int size = elementData.size();
                for (int n = cursor; n < size; n++) {
                    if (listOfDelete.get(n)) continue;
                    lastObj = n;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            synchronized (sync) {
                if (cursor >= elementData.size())
                    throw new NoSuchElementException();
                int i = cursor;
                if (hasNext()) {
                    cursor = lastObj;
                }
                if (listOfDelete.get(i)) {
                    i = lastObj;
                }
                cursor++;
                return elementData.get(i);
            }
        }
    }

}
