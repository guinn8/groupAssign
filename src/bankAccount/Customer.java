package bankAccount;

/**
 * <h1>Customer<h1>
 *Customer holds data about customer 
 *information and formats it for return
 * <p>
 * @author Gavin Guinn
 * @author T01 Group 4
 * @since July 24, 2018
 * @version 1.1
 */


public class Customer {
	/** name of the customer*/
	private String name="";
	
	/** ID of a customer*/
	private int customerID;
	
	/**
	 * Constructor
     * @param nothing
     */
	public Customer() {		
	}
	
	/**
	  * Copy Constructor
	  * sets the instance variables in Customer to be identical to the values of the customer passed to it
	  * @param copyCustomer takes an instance of the customer class
	  */
	public Customer(Customer copyCustomer) {
		name=copyCustomer.getName();
		customerID=copyCustomer.getID();
	}
	
	 /**
	  * Constructor
	  * @param setName
	  * @param setCustomerID Integer to be used as a customer ID
	  */
	public Customer(String setName, int setCustomerID) {
		name= setName;
		customerID= setCustomerID;
	}
	
	/**
	 * Setter method for instance variable name
	 * @param setName sets the instance variable name
	 */
	public void setName(String setName){
		name= setName;
	}
	
	/**
	 * Setter method for instance variable customerID
	 * @param setCustomerID is the value of the customer ID to be set
	 */
	public void setCustomerID(int setCustomerID){
		customerID= setCustomerID;
	}
	
	/**
	 * Getter method for instance variable name
	 * @return instance variable name 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method for instance variable customerID
	 * @return name (String)
	 */
	public int getID() {
		return customerID;
	}
	
	/**
	 * Method formats the name and customer ID variables into a readable string
	 * overrides the default toString method
	 * @return formatted string with name and ID
	 */
	public String toString() {
		return (name + " has the account number: " + customerID );
	}
}
