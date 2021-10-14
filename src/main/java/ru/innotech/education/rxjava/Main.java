package ru.innotech.education.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;

public class Main {
    public static void main(String[] args) {

        AppendOnlyList<Integer> list = new AppendOnlyList<>(of(1,2,3,4,5));
        System.out.println("Size: " + list.size());
        System.out.println("isEmpty: " + list.isEmpty());
        list.remove(5);
        System.out.println("Contains: " + list.contains(5));
        System.out.println("Size: " + list.size());
        System.out.println("ContainsAll: " + list.containsAll(of(4,5,1)));
        System.out.println(list.stream().collect(Collectors.toList()));

        List<Integer> i = new ArrayList<>(of(1,2,3,4));

        System.out.println(i.stream().collect(Collectors.toList()));
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
