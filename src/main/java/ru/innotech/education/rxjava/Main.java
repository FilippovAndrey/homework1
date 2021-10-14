package ru.innotech.education.rxjava;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.List.of;

public class Main {
    public static void main(String[] args) {

        List<Integer> lt = new ArrayList<>(of(2,3,4,5,6));

        AppendOnlyList<Integer> list = new AppendOnlyList<>(of(1,2,3,4,5));
        System.out.println("Size: " + list.size());
        System.out.println("isEmpty: " + list.isEmpty());
        list.remove(5);
        System.out.println("Contains: " + list.contains(5));
        System.out.println("Size: " + list.size());
        System.out.println("ContainsAll: " + list.containsAll(of(4,5,1)));
        System.out.println(list);
        System.out.println(list.retainAll(lt));
        System.out.println(list);
        System.out.println((long) list.size());

        /*final AppendOnlyList<Integer> list = new AppendOnlyList<>();
        final List<Integer> newList = new ArrayList<>(of(1, 2, 3, 4, 5, 6));
        for (final Integer i: newList) {
            if (i == 2) {
                newList.remove(1);
            }
            if (i == 3) {
                newList.add(7);
            }
            if (i == 4) {
                newList.remove(4);
            }
            list.add(i);
        }
        Iterator<Integer> iterator = newList.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            System.out.println(i + " " + iterator.next());
            if(i++ == 10)break;
        }*/


        /*List<Integer> l = new LinkedList<>(of(1,2,3,4));
        System.out.println("l: " + l);
        l.add(2);
        l.add(5);
        l.remove((Object) 2);
        //l.remove();
        System.out.println("l: " + l);*/
/*
        List<Integer> i = new ArrayList<>(of(1,2,3,4));

        System.out.println(i.stream().collect(Collectors.toList()));*/
/*        System.out.println(i.removeAll(of(3,5)));
        System.out.println(i);*/

        /*final AppendOnlyList<Integer> list = new AppendOnlyList<>(of(1, 2, 3, 4, 5, 6));
        list.remove(2);
        list.removeAll(of(4, 5));*/
        /*assertThat(list).containsExactly(1, 3, 6);*/
/*        final AppendOnlyList<Integer> list = new AppendOnlyList<>(of(1, 2, 3, 4, 5, 6));
        final List<Integer> newList = new ArrayList<>();
        for (final Integer i: list) {
            if (i == 2) {
                list.remove(1);
            }
            if (i == 3) {
                list.add(7);
            }
            if (i == 4) {
                list.remove(4);
            }
            newList.add(i);
        }
        System.out.println(list);*/
        /*assertThat(newList).containsExactly(1, 2, 3, 4, 5, 6, 7);
        assertThat(new ArrayList<>(list)).containsExactly(2, 3, 5, 6, 7);*/
    }
}
