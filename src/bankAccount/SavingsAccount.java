package bankAccount;

/**
 * <h1>SavingsAccount<h1>
 * 
 * SavingsAccount is an extension of BankAccount
 * The class manages the amount of interest to be deposited to the account
 * including setting the desired interest rate
 * <p>
 * @author T01 Group 4
 * @author Gavin Guinn
 * @since July 17, 2018
 * @version 1.1
 */
public class SavingsAccount extends BankAccount{
	
	/** The interest rate applied to the account
	 * when used the annualInterestRate/100 as to act like a %
	 * must be >0
	 */
	private static double annualInterestRate;
	
	
	/**
	 * Constructor
	 * @param newDeposit calls on constructor in BankAccount to initialize the balance variable
	 * @param setRate Initializes the instance variable annualInterestRate
	 */
	public SavingsAccount(double setBal, double setRate){
		super(setBal);
		annualInterestRate= setRate;
	}
	
	/**
	 * Call to Constructor in BankAccount
	 * @param setCust Customer object to be assigned to thisSavingsAccount
	 * @param setBal Initial balance of the SavingsAccount 
	 */
	public SavingsAccount(Customer setCust, double setBal){
		super(setCust,setBal);
	}
	
	/**
	 * blank constructor
	 */
	public SavingsAccount() {
	}

	/**
	 * Getter method for annualInterestRate variable
	 * @return annualInterestRate
	 */
	public double getAnnualInterestRate() {
		return getBalance()*((annualInterestRate/100))/12;
	}
	
	/**
	 * setter method for setRate variable 
	 * @param newRate sets the Annual interest rate
	 */
	public static void setAnnualInterestRate(double newRate) {
		if(newRate>=0 && newRate<Double.POSITIVE_INFINITY) annualInterestRate=newRate;
	}
	
	/**
	 * Method deposits the monthly interest amount to the account
	 */
	public void depositMonthlyInterest() {
		if (getBalance()>0) deposit(getBalance()*((annualInterestRate/100))/12);//"annualInterestRate/100" because annualInterestRate acts as a % 
	}
}
