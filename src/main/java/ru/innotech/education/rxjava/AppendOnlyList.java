package ru.innotech.education.rxjava;

import java.util.*;

public class AppendOnlyList<T>
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
        countList = 0;
        for (Boolean i : listOfDelete) {
            if (!i) {
                countList++;
            }
        }
        return countList;
    }

    @Override
    public boolean isEmpty() {
        if (listOfDelete.isEmpty()) return true;
        for (Boolean i : listOfDelete) {
            if (!i) return false;
        }
        return true;
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

        for(T temp : list){

        }*/
        return elementData.iterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not Implemented toArray");
    }

    @Override
    public <K> K[] toArray(K[] a) {
        throw new UnsupportedOperationException("Not Implemented toArray K");
    }

    @Override
    public boolean add(T t) {
        try {
            elementData.add(t);
            listOfDelete.add(false);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < elementData.size(); i++) {
            if (elementData.get(i).equals(o) && !listOfDelete.get(i)) {
                listOfDelete.set(i, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean findElement;
        for (Object element : c) {
            findElement = false;
            for (int i = 0; i < elementData.size(); i++) {
                if (elementData.get(i).equals(element) && !listOfDelete.get(i)) {
                    findElement = true;
                    break;
                }
            }
            if (!findElement) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T t : c){
            add(t);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean findElement = false;
        for (Object element : c) {
            for (int i = 0; i < elementData.size(); i++) {
                if (elementData.get(i).equals(element) && !listOfDelete.get(i)) {
                    listOfDelete.set(i, true);
                    findElement = true;
                }
            }
        }
        return findElement;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(Object t : c){
            for(int i = 0; i < elementData.size(); i++) {
                if(elementData.get(i).equals(c) && !listOfDelete.get(i)){

                }
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for(Boolean i : listOfDelete){
            
        }
    }
}
