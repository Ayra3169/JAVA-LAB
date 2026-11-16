
class SharedCounter {

    int count = 0;

    void increment() {
        int temp = count;

        // Give another thread a chance to execute
        Thread.yield();

        count = temp + 1;
    }
}

class CounterThread extends Thread {

    SharedCounter counter;

    CounterThread(SharedCounter counter, String name) {
        super(name);
        this.counter = counter;
    }

    public void run() {

        for (int i = 1; i <= 1000; i++) {
            counter.increment();
        }

        System.out.println(getName() + " completed!");
    }
}

public class RaceConditionDemo {

    public static void main(String[] args)
            throws InterruptedException {

        SharedCounter counter = new SharedCounter();

        CounterThread t1 =
                new CounterThread(counter, "Thread 1");

        CounterThread t2 =
                new CounterThread(counter, "Thread 2");

        // Start both threads
        t1.start();
        t2.start();

        // Main thread waits for both threads
        t1.join();
        t2.join();

        System.out.println("Expected Count: 2000");
        System.out.println("Actual Count: " + counter.count);

        System.out.println("Program Completed!");
    }
}
