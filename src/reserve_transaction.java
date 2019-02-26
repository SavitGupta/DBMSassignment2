public class reserve_transaction implements Runnable{
    Flight flight;
    Passenger passenger;
    Lock l1;
    Thread t1;
    static Integer cnt = 0;
    int mycnt;

    public reserve_transaction(Lock l1){
//        this.flight = flight;
//        this.passenger = passenger;
        this.l1 = l1;
        mycnt = cnt ++;
    }
    public void run(){

        System.out.println("thread created" + mycnt);
        l1.acquire();


        System.out.println("acquired lock" + mycnt);

        try{
            Thread.sleep(1000);

        }
        catch (InterruptedException e){
            System.out.println("interupted ...." + mycnt);
        }
        finally {
            System.out.println("l1 attempts release " + mycnt);
            l1.release();
        }

        System.out.println("done " + mycnt);


    }





}
