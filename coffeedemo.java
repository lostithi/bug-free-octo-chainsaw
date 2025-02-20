
// import java.io.*;
// import java.nio.file.*;
// import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.*;

class menuItemList{
        public String itemid;
        public String name;
        public String description;
        public String category;
        public double price;

        public menuItemList(String itemid, String name, String description, String category, double price){
            this.itemid=itemid;
            this.name=name;
            this.description=description;
            this.category=category;
        }
}
class orderList{
        public String orderid;
        public String customerid;
        public String itemid;
        public double finalprice;
        private LocalDateTime timestamp;


        public orderList(String orderid, String customerid, String itemid, double finalprice, LocalDateTime timestamp){
            this.orderid=orderid;
            this.customerid=customerid;
            this.itemid=itemid;
            this.finalprice=finalprice;
            this.timestamp=timestamp;
        }
}
class customerList{
        public String customerid;
        public String name;
        public String phonenumber;


        public customerList(String customerid, String name, String phonenumber){
            this.customerid=customerid;
            this.name=name;
            this.phonenumber=phonenumber;
        }
}
interface operations {
    void loadMenu(String Menulist);

    void loadOrder(String Orderlist);

    void newOrder();

    void report();
}

class coffeeShop {
    private List<Order> menu = new ArrayList<>();
    private Map<String, MenuItem> orders = new HashMap<>();
    private List<Order> customers = new ArrayList<>();

    public void loadMenu(String Menulist) {
    }

    public void loadOrder(String Orderlist) {
    }

    public void newOrder() {
    }

    public void report() {
    }

}

public class coffeedemo {
    public static void main(String[] args) {
        coffeeShop shop = new coffeeShop();
    do{
        System.out.println("\nCOFFEE SHOP SIMULATION");
        System.out.println("\n1.MENU list");
        System.out.println("\n2.Order now");
        System.out.println("\n3.Report");
        System.out.println("\n4.Exit");
        System.out.println("\nEnter your choice: ");

    switch(choice){
        case 1->{}
        case 2->{}
        case 3->{}
        case 4->{System.out.println("EXITING.TY!!");}
        default->{System.out.println("INVALID OPTION!!");}
        
    }
    }while(choice!=4);
    }

}