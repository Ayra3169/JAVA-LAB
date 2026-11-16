class FoodOrder extends Thread {    
    FoodOrder(String name) {        
        super(name);    
    }   
     public void run() {       
         try {            
            System.out.println(getName() + " preparation started"); 
           // Give other threads a chance            
           // Thread.yield();            
           for (int i = 1; i <= 3; i++) {               
             System.out.println(getName() +                       
              " preparing step " + i);               
               // Simulate preparation time 
               // // Simulate preparation time                
               Thread.sleep(1000);            
            }            
            System.out.println(getName() + " is ready!");        
        } catch (InterruptedException e) {            
            System.out.println(getName() + " was cancelled!");        
        }    
    } 
} 
public class CanteenThreadDemo {    
    public static void main(String[] args)            
    throws InterruptedException {       
         FoodOrder order1 = new FoodOrder("Pizza");        
         FoodOrder order2 = new FoodOrder("Burger");        
         // NEW state        
         System.out.println("Before start: "                
         + order1.getState());        
         // Start both threads        
         order1.start();        
         order2.start();       
          // RUNNABLE or TIMED_WAITING       
           Thread.sleep(200);        
           System.out.println("After start: "               
            + order1.getState());       
             // Cancel Burger order        
             order2.interrupt();       
              // Main thread waits for both orders       
               order1.join();        
               order2.join();       
                // TERMINATED state        
                System.out.println("Pizza final state: "                
                + order1.getState());        
                System.out.println("Burger final state: "                
                + order2.getState());       
                 System.out.println("All orders processed!");   
                 } 
                }
