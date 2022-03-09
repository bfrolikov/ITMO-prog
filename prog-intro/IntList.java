import java.util.Arrays;

public class IntList {
    private int[] array = new int[10];
    private int pointer = 0;

    private void resize() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    public IntList add(int a) {
        if (pointer == array.length - 1)
            resize();
        array[pointer++] = a;
        return this;
    }

    public int get(int i) {
        return array[i];
    }

    public int size() {
        return pointer;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pointer; i++) {
            builder.append(Integer.toString(array[i]));
            if (i != pointer - 1)
                builder.append(" ");
        }
        return builder.toString();
    }
}