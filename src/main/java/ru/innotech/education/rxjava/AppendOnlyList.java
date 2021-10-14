package ru.innotech.education.rxjava;

import java.util.*;
import java.util.stream.Collectors;

public class AppendOnlyList<T> extends AbstractCollection<T>
        implements Collection<T> {
    private final List<T> elementData;
    private final List<Boolean> listOfDelete;
    private int countList;

    public AppendOnlyList() {
        elementData = new ArrayList<>();
        listOfDelete = new ArrayList<>();
    }

    public AppendOnlyList(Collection<T> c) {
        this();
        addAll(c);
    }

    @Override
    public int size() {
        /*countList = 0;
        for (Boolean i : listOfDelete) {
            if (!i) {
                countList++;
            }
        }*/
        return countList;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < elementData.size(); i++) {
            if (elementData.get(i).equals(o) && !listOfDelete.get(i)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        /*List<T> tempList = new ArrayList<>();

        for (T temp : elementData) {
            if (contains(temp))
                tempList.add(temp);
        }*/
        return elementData.stream().filter(this::contains).collect(Collectors.toList()).iterator();
                //tempList.iterator();
    }

    @Override
    public Object[] toArray() {
        return elementData.stream().filter(this::contains).toArray();
    }

    @Override
    public <K> K[] toArray(K[] a) {
        return (K[]) elementData.stream().filter(this::contains).collect(Collectors.toList()).toArray(a);
    }

    @Override
    public boolean add(T t) {
        elementData.add(t);
        listOfDelete.add(false);
        countList++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < elementData.size(); i++) {
            if (elementData.get(i).equals(o) && !listOfDelete.get(i)) {
                listOfDelete.set(i, true);
                countList--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if(!contains(element)){
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
            if(remove(element))
                findElement = true;
        }
        return findElement;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        /*boolean findElement = false;
        for(int i = 0; i < elementData.size(); i ++){
            for(Object t : c) {
                if(!contains(t))
            }
        }
        for (Object t : c) {
            if(!contains(t)){
                remove(t);
                findElement = true;
            }
        }
        return findElement;*/

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
}
