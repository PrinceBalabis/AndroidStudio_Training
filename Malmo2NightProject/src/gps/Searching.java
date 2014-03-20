package gps;

import java.util.ArrayList;
import java.util.List;

public class Searching {

    /**
     * This class includes two sets of methods, used for searching values inside
     * an ArrayList.
     * @version 1.0 14/2 2013
     * @author Simon Cedergren, AB1539
     *
     */

    /**
     * Returns position of element. Utilizes binary search.
     *
     * @param list
     * @param element
     * @return true = index of found element
     */
    public static int binarySearch(ArrayList list, Object element) {
        int min = 0, max = list.size() - 1, pos;
        Comparable compare = (Comparable) element;
        while (min <= max) {
            pos = ((min + max) / 2);
            if (compare.compareTo(list.get(pos)) < 0) {
                max = pos - 1;
            } else if (compare.compareTo(list.get(pos)) > 0) {
                min = pos + 1;
            } else {
                return pos;
            }
        }
        return -1;
    }

    /**
     * Returns position of element. Utilizes linear search.
     *
     * @param list
     * @param element
     * @return true = index of found element
     */
    public static <E> int linearSearch(List<E> list, E element) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>(1000);
        for (int i = 0; i < 999; i += 10) {
            list.add(i);
        }
        System.out.println(list);
        System.out.println(binarySearch(list, 5));
        System.out.println(linearSearch(list, 30));
    }
}
