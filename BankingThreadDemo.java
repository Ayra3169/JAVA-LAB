
class BankAccount {
    int balance = 1000;

    synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName()
                + " is withdrawing Rs. " + amount);

        if (balance >= amount) {
            balance = balance - amount;
            System.out.println("Transaction successful!");
            System.out.println("Remaining Balance: Rs. " + balance);
        } else {
            System.out.println("Insufficient Balance!");
        }
    }
}

class BankTransaction extends Thread {

    BankAccount account;
    int amount;

    BankTransaction(BankAccount account, String name, int amount) {
        super(name);
        this.account = account;
        this.amount = amount;
    }

    public void run() {
        System.out.println("Thread Name: " + getName());
        System.out.println("Thread Priority: " + getPriority());

        try {
            Thread.sleep(500);
            account.withdraw(amount);
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted!");
        }
    }
}

public class BankingThreadDemo {

    public static void main(String[] args)
            throws InterruptedException {

        BankAccount account = new BankAccount();

        BankTransaction t1 =
                new BankTransaction(account, "Customer 1", 700);

        BankTransaction t2 =
                new BankTransaction(account, "Customer 2", 500);

        // Set thread priorities
        t1.setPriority(8);
        t2.setPriority(5);

        // Display thread names and priorities
        System.out.println("Customer 1 Priority: " + t1.getPriority());
        System.out.println("Customer 2 Priority: " + t2.getPriority());

        // Start both threads
        t1.start();
        t2.start();

        // Main thread waits for both transactions
        t1.join();
        t2.join();

        System.out.println("Final Bank Balance: Rs. " + account.balance);
        System.out.println("All transactions completed!");
    }
}
