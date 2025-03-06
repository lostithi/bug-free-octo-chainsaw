import java.util.Scanner;

interface UserFns{
    void Deposit(int amount);
    void Withdraw(int amount);
}
enum AccType{
    Saving,
    Current
}
public class bankApp{
    String name;
    int acc_no;

    UserDetails(String name,int acc_no){
        this.name=name;
        this.acc_no=acc_no;        
    }

    public void Display(){
        System.out.println(String.format("name : %s\nAcc_no: %s\n",this.name,this.acc_no));
    }
}

class User extends UserDetails implements UserFns{ 
    private  int balance;
    private AccType acc_type;

    void DisplayAccount(){
        System.out.println("\n==================\nBank Details\n==================");
       super.Display();
        System.out.println("Balance : "+this.balance);
        System.out.println("Acc Type : "+acc_type);
    }
    User(String name,int acc_no){
        super(name,acc_no);
         this.balance=0;
         this.acc_type=AccType.Current;
    }
    public void Deposit(int amount){
        this.balance+=amount;
        System.out.println("\n==================\nDeposit Details\n==================");
        System.out.println("Deposited amount : "+amount);
        System.out.println("The new balance is : "+this.balance);
    }

    public void Withdraw(int amount){
        if(amount>balance){
            // throw new Error("Insufficient balance");
            System.out.println("Insufficient Balance\n");
            return;
        }
        this.balance-=amount;
        System.out.println("\n==================\nWithdraw Details\n==================");
        System.out.println("Withdrew amount "+amount);
        System.out.println("The new balance is : "+this.balance);
    }
}
final class Bank<U extends User>{
    U user;
    Bank(U user){
        this.user =user;
    }
    void showDetails(){
        this.user.DisplayAccount();
    }
    void Deposit(){
        Integer amount;
        Scanner scanner = new Scanner(System.in); 
            System.out.println("\nEnter the amount to Deposit : ");
            amount=scanner.nextInt();
        this.user.Deposit(amount);
    }
    void Withdraw(){
        Integer amount;
        Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter the amount to Withdraw : ");
            amount=scanner.nextInt();
        this.user.Withdraw(amount);
    }
}
class Banking{
    public static void main(String[] args){
       
        User user=createuser();
        Bank<User> bank=new Bank<User>(user);   
        int choice=0;
        Scanner scanner =new Scanner(System.in);
        while(choice!=4){
            System.out.println("Enter the operation\n");
            System.out.println("1.show Details\n2.Deposit Amount\n3.Withdraw amount\n4.Exit\n");
            choice=scanner.nextInt();
            switch(choice){
                case 1:
                    bank.showDetails();
                    break;
                case 2:
                    bank.Deposit();
                    break;
                case 3:
                    bank.Withdraw();
                    break;
                case 4:
                    System.out.println("Exiting .. : ) ");
                    break;
            }
        }
        scanner.close();
    }
    public static User createuser(){
         String name;
        int acc_no;
         System.out.println("\n==================\nCreate Account\n==================");
        Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter your name");
            name=scanner.nextLine();
            System.out.println("Enter your acc_no");
            acc_no=scanner.nextInt();
        User user=new User(name,acc_no);
        return user;
    }
}