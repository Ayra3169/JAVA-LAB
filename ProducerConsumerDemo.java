
class SharedBuffer {

    int data;
    boolean available = false;

    synchronized void produce(int value) {

        try {
            while (available) {
                wait();
            }

            data = value;
            available = true;

            System.out.println("Producer produced: " + data);

            notify();

        } catch (InterruptedException e) {
            System.out.println("Producer interrupted!");
        }
    }

    synchronized void consume() {

        try {
            while (!available) {
                wait();
            }

            System.out.println("Consumer consumed: " + data);

            available = false;

            notifyAll();

        } catch (InterruptedException e) {
            System.out.println("Consumer interrupted!");
        }
    }
}

class Producer extends Thread {

    SharedBuffer buffer;

    Producer(SharedBuffer buffer) {
        super("Producer");
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 1; i <= 5; i++) {
            buffer.produce(i);
        }
    }
}

class Consumer extends Thread {

    SharedBuffer buffer;

    Consumer(SharedBuffer buffer) {
        super("Consumer");
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 1; i <= 5; i++) {
            buffer.consume();
        }
    }
}

public class ProducerConsumerDemo {

    public static void main(String[] args)
            throws InterruptedException {

        SharedBuffer buffer = new SharedBuffer();

        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);

        // Set thread priorities
        p.setPriority(7);
        c.setPriority(5);

        System.out.println("Producer Priority: " + p.getPriority());
        System.out.println("Consumer Priority: " + c.getPriority());

        // Start both threads
        p.start();
        c.start();

        // Wait for both threads
        p.join();
        c.join();

        System.out.println("Producer-Consumer process completed!");
    }
}
