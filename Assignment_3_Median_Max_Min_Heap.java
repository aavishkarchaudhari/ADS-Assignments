import java.util.Scanner;

class MaxHeap {
    int[] heap = new int[100];
    int size;

    public MaxHeap() {
        size = 0;
    }

    public void add(int value) {
        int i = size;

        heap[i] = value;

        while (i > 0 && heap[i] > heap[(i - 1) / 2]) {
            int temp = heap[i];
            heap[i] = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = temp;

            i = (i - 1) / 2;
        }

        size++;
    }

    public int remove() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return -1;
        }

        int ele = heap[0];

        heap[0] = heap[size - 1];
        size--;

        int i = 0;

        while ((2 * i) + 1 < size) {
            int largest = i;
            int left = (2 * i) + 1;
            int right = (2 * i) + 2;

            if (left < size && heap[left] > heap[largest])
                largest = left;

            if (right < size && heap[right] > heap[largest])
                largest = right;

            if (largest != i) {
                int temp = heap[i];
                heap[i] = heap[largest];
                heap[largest] = temp;

                i = largest;
            } else {
                break;
            }
        }

        return ele;
    }

    public int peek() {
        if (isEmpty()) {
            return -1;
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void display() {
        System.out.print("Max Heap elements are: ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}

class MinHeap {
    int[] heap = new int[100];
    int size;

    public MinHeap() {
        size = 0;
    }

    public void add(int value) {
        int i = size;

        heap[i] = value;

        while (i > 0 && heap[i] < heap[(i - 1) / 2]) {
            int temp = heap[i];
            heap[i] = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = temp;

            i = (i - 1) / 2;
        }

        size++;
    }

    public int remove() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return -1;
        }

        int ele = heap[0];

        heap[0] = heap[size - 1];
        size--;

        int i = 0;

        while ((2 * i) + 1 < size) {
            int smallest = i;
            int left = (2 * i) + 1;
            int right = (2 * i) + 2;

            if (left < size && heap[left] < heap[smallest])
                smallest = left;

            if (right < size && heap[right] < heap[smallest])
                smallest = right;

            if (smallest != i) {
                int temp = heap[i];
                heap[i] = heap[smallest];
                heap[smallest] = temp;

                i = smallest;
            } else {
                break;
            }
        }

        return ele;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (isEmpty()) {
            return -1;
        }
        return heap[0];
    }

    public void display() {
        System.out.print("Min Heap elements are: ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}

public class Assignment_3_Median_Max_Min_Heap {

    MaxHeap maxHeap;
    MinHeap minHeap;

    Assignment_3_Median_Max_Min_Heap() {
        maxHeap = new MaxHeap();
        minHeap = new MinHeap();
    }

    public void insert(int val) {
        if (maxHeap.isEmpty() || val <= maxHeap.peek()) {
            maxHeap.add(val);
        } else {
            minHeap.add(val);
        }

        if (maxHeap.size > minHeap.size + 1) {
            minHeap.add(maxHeap.remove());
        } else if (minHeap.size > maxHeap.size) {
            maxHeap.add(minHeap.remove());
        }
    }

    public double findMedian() {
        if (maxHeap.isEmpty()) {
            return 0.0;
        }

        if (maxHeap.size == minHeap.size) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Assignment_3_Median_Max_Min_Heap median = new Assignment_3_Median_Max_Min_Heap();

        while (true) {
            System.out.println("\n1. Insert a value");
            System.out.println("2. Find current median");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value to insert: ");
                    int x = sc.nextInt();
                    median.insert(x);
                    break;

                case 2:
                    System.out.println("Current Median: " + median.findMedian());
                    break;

                case 3:
                    sc.close();
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}