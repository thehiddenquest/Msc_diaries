public class Main_thread 
{
    public static void main(String args[])
    {
        Thread t = Thread.currentThread();
        System.out.println("Current Thread = " + t );
        t.setName("My Main Thread");
        System.out.println("After changing name: " + t.getName());

        try {
            for(int i =0; i<=5; i++)
            {
                System.out.println(i);
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(t+ "is stopped by " + e );
        }
    } 
}
