package infrastructure.collections;

public class SwapOnRemoveIntArray {

    public int[] compressed;
    private int[] data;
    private int size;

    public SwapOnRemoveIntArray() {
        this.data = new int[10];
        this.compressed = new int[0];
    }

    public void add(int element) {
        if (size == data.length) {
            int[] copy = new int[data.length * 2];
            for (int i = 0; i < data.length; i++)
                copy[i] = data[i];
            data = copy;
        }
        data[size++] = element;
    }

    public int get(int index) {
        return data[index];
    }

    public void remove(int index) {
        data[index] = data[--size];
        data[size] = 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(int index) {
        for (int i : data)
            if (i == index)
                return true;
        return false;
    }

    public int[] getData() {
        return data;
    }
}
