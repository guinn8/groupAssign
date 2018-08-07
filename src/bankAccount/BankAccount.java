package bankAccount;
/**
 * BankAccount manages the balance of an account.
 * The included methods allow for depositing and withdrawing 
 * from an account and ensure the overdraft limit is not exceeded
 * 
 * T01 Group 4
 * @author Gavin Guinn
 * @since July 24, 2018
 * @version 1.2
 */

public class BankAccount{

	
	
	/** amount of money in the account */
	private double balance = 0;
	private Customer customer = new Customer();  
	
	public static void main(String[]args){
		Customer c = new Customer("John Doe", 321);
		BankAccount b1 = new BankAccount(c,100.0);
		ChequingAccount b2 = new ChequingAccount(c, 200.0, 12.0);
		b2.setOverdraftAmount(150.0);
		BankAccount b3 = b2;

		System.out.println(b1.getBalance() + ", " + b3.getBalance());

		b1.withdraw(110);
		System.out.println(b1.getBalance() + ", " + b3.getBalance());

		b2.withdraw(300.0);
		System.out.println(b1.getBalance() + ", " + b3.getBalance());

		b1.transfer(50.0, b2);
		System.out.println(b1.getBalance() + ", " + b3.getBalance());

		b2.transfer(88,b1);
		System.out.println(b1.getBalance() + ", " + b3.getBalance());
	}
	
	/**
	 * Constructor
	 * @param setBal
	 */
	BankAccount(double setBal){
		balance=setBal;
	}
	 /**
	  * Constructor
	  * @param serCust takes a instance of the Customer object and sets the instance variable customer
	  * @param setBal takes a double and sets the instance variable balance
	  */
	public BankAccount (Customer setCust, double setBal) {
		Customer c= new Customer();
		c.setCustomerID(setCust.getID());
		c.setName(setCust.getName());
		customer= c;
		balance= setBal;
	}
	
	/**
	  * Constructor
	  * @param setCust takes a instance of the Customer object and sets the instance variable customer
	  */
	public BankAccount(Customer setCust) {
		customer= setCust;
	}
	
	/**
	  * blank constructor
      */
	public BankAccount() {
	}
	
	/**
	 * The method deposit adds the parameter passed to it  to the balance (must <0)
	 * @param amount This is the amount added to the account
	 */
	public void deposit(double amount) {
		if (amount>0 && amount<Double.POSITIVE_INFINITY) this.balance=this.balance+amount;
	}
	
	/**
	 * This method subtracts the parameter passed to it from the account balance
	 * Also checks that amount > 0 and that overdraft limit not exceeded 
	 * @param withdrawal This is the amount subtracted
	 */
	public void withdraw(double withdrawal) { 
		if (balance-withdrawal>=0 && withdrawal>0 && withdrawal<Double.POSITIVE_INFINITY) balance=balance-withdrawal;
	}	
	
	/**
	 * Getter method for balance instance variable
	 * @return value of instance variable balance
	 */		
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Getter method for the current instance of the customer object 
	 * @return Customer instance
	 */
	public void setCustomer(Customer setCustomer) {
		 customer=setCustomer;
	}
	
	/**
	 * getter method for the customer object created with every new BankAccount
	 * clones the information in the customer object for pass-by-value
	 * @return copy of customer
	 */
	public Customer getCustomer (){
		Customer c = new Customer();
		c.setName(customer.getName());
		c.setCustomerID(customer.getID());
		return c;
	}
	
	/**
	 * Transfers money to the bankaccount taken as a parameter
	 * @param amount amount of money to transfer
	 * @param toAccount the account to transfer the money to 
	 */
	public void transfer(double amount, BankAccount toAccount){	
		if(amount>0 && amount<Double.POSITIVE_INFINITY){
			if( this instanceof ChequingAccount ){
				withdraw(amount);
				toAccount.deposit(amount);
			}
			else if (balance-amount>=0){
				withdraw(amount);
				toAccount.deposit(amount);
			}
		}
	}
	
	/**
	 * manually set balance
	 * @param amount what you want balance to be set to
	 */
	protected void setBalance(double amount){		
		balance=amount;
	}
}

