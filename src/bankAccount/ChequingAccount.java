package bankAccount;
/**
 * <h1>ChequingAccount<h1>
 * ChequingAccount creates a new child of bank account that implements features to
 * manage the overdraft of an account
 * <p>
 * @author Gavin Guinn
 * @author T01 Group 4
 * @since July 27, 2018
 * @version 1.0
 */

public class ChequingAccount extends BankAccount{
		
	
	/**cannot withdraw balance past -overdraftAmount*/
	private double overdraftAmount = 100;
	
	/**amount withdrawn from the account if overdraft limit is exceeded*/
	private double overdraftFee;
	
	/**
	 * Constructor. 
	 * @param setOverdraftFee sets the amount in the overdraftAmount variable
	 */
	public ChequingAccount(double setOverdraftFee){
		overdraftFee=setOverdraftFee;
	}
	
	/**
	 * Constructor.
	 * @param setCust The customer to be assigned to the chequing account
	 * @param setBal the balance of the account
	 * @param setOverdraftFee sets the amount in the overdraftAmount variable
	 */
	public ChequingAccount(Customer setCust,double setBal, double setOverdraftFee){
		super(setCust,setBal);
		overdraftFee=setOverdraftFee;
	}
	
	/**
	 * Checks for valid input and changes overdraft limit to parameter passed to it
	 * @param amount sets the amount of instance variable overDraftLimit
	 */	
	public void setOverdraftAmount(double amount) {
		if( amount<Double.POSITIVE_INFINITY ) overdraftAmount=amount;
	}
	
	/**
	 * getter for overdraftFee variable
	 * @return overdraftFee
	 */	
	public double getOverdraftFee(){
		return overdraftFee;
	}
	
	/**
	 * This method subtracts the parameter passed to it from the account balance
	 * Also checks that amount > 0 and that overdraft limit not exceeded 
	 * overrides withdraw method in bankaccount
	 * @param withdrawal This is the amount subtracted
	 */
	public void withdraw(double withdrawal) { 
		
		if(getBalance()-withdrawal>0 && withdrawal>0 && withdrawal<Double.POSITIVE_INFINITY) setBalance(getBalance()-withdrawal);
		else if(getBalance()-withdrawal>=-overdraftAmount && withdrawal>0 && withdrawal<Double.POSITIVE_INFINITY) setBalance(getBalance()-withdrawal-overdraftFee);
	}
}
