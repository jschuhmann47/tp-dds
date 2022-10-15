package models.helpers;

public class ListHelper {

    public static <T> T getFirstElement(final Iterable<T> elements) {
        return elements.iterator().next();
    }

    public static <T> T getLastElement(final Iterable<T> elements) {
        T lastElement = null;

        for (T element : elements) {
            lastElement = element;
        }

        return lastElement;
    }
}
