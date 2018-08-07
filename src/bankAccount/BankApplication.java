package bankAccount;

import javafx.application.Application;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
/**
 * <h1>BankApplication<h1>
 * 
 * Creates a windowed application that can handle withdrawing and depositing from an account 
 * inherits methods from the application class in jFX
 * 
 * <p>
 * @author T01 Group 4
 * @author Gavin Guinn
 * @since July 24, 2018
 * @version 1.0
 */
public class BankApplication extends Application {

	BufferedReader reader = null;
	BankAccount account;
	Customer customer;
	
	/** pre-set customer savings account */
	SavingsAccount savings= new SavingsAccount(150.00,0.00);


	
	/**
	 * blank constructor 
	 * assigns the customer to the SavingsAccount 
	 */
	public BankApplication(){
		savings.setCustomer(customer);
	}
	
	
	public static void main(String[] args){
		launch(args);
	}
	   
	@Override
	/**
	 * method creates and populates a javaFX scene
	 * @param primaryStage is a Stage object
	 */
	public void start(Stage primaryStage) throws Exception {
		String[] accountArr= new String[6];
		int counter = 0;
		
		try {
			File accountFile=new File("accountFiles/account1");
			reader=new BufferedReader(new FileReader(accountFile));
			
			String line =reader.readLine();
			while( line != null) {
				accountArr[counter]=line;
				line =reader.readLine();
				counter++;
			}
			
			reader.close();
			TextField depositTextField = new TextField();
			TextField withdrawTextField = new TextField();
			
			Button executeButton= new Button("Execute");
			customer= new Customer(accountArr[0],Integer.parseInt(accountArr[2]));
			if (accountArr[1].equals("SavingsAccount")) account = new SavingsAccount(customer,Double.parseDouble(accountArr[2]));
			else if (accountArr[1].equals("ChequingAccount")) account = new ChequingAccount(customer, Double.parseDouble(accountArr[2]), Double.parseDouble(accountArr[5]));
			
			Label customerNameLabel= new Label("Name: " +customer.getName());
			Label customerIDLabel = new Label("Account ID: " + Integer.toString(customer.getID()));
			Label balanceLabel = new Label("Balance: " +Double.toString(savings.getBalance()));
			
			depositTextField.setPromptText("Enter Deposit amount: ");
			depositTextField.setPrefColumnCount(15);
			withdrawTextField.setPromptText("Enter Withdrawal amount: ");
			withdrawTextField.setPrefColumnCount(15);
			
			//hbox implements the withdraw/deposit text fields
			HBox hbox=new HBox(3);
			hbox.getChildren().add(depositTextField);
			hbox.getChildren().add(withdrawTextField);
			
			//root is the main organizational element in the scene handles all other elements
			VBox root = new VBox(6);
			root.getChildren().add(customerNameLabel);
			root.getChildren().add(customerIDLabel);
			root.getChildren().add(hbox);
			root.getChildren().add(executeButton);
			root.getChildren().add(balanceLabel);
			
			Scene scene = new Scene(root,400,200);
			primaryStage.setTitle("BankApplication");
			
			executeButton.setOnAction(new EventHandler<ActionEvent>() { //creates a new event handler object
				
				/**
				 * this method processes the data in the text fields and ensures it is a double
				 * @param e is a ActionEvent object
				 */
			    public void handle(ActionEvent e) {
			    
			    	boolean isBadInput=true; //if there is no input the method assumes it is bad
			    	
			    	// tries to parse data in deposit field
			    	try{ 
			    		double deposit=Double.parseDouble(depositTextField.getText());
			    		savings.deposit(deposit);
			    		depositTextField.clear();
			    		balanceLabel.setText("Balance: " +Double.toString(savings.getBalance()));
			    		isBadInput=false; // if the parse doesn't fail input is good
			    		
			    	   }catch(Exception badInputDeposit){
			    		   depositTextField.clear();
			    	   }
			    	
			    	// tries to parse data in withdraw field	
			    	try{
			    		double withdraw=Double.parseDouble(withdrawTextField.getText());
			    		savings.withdraw(withdraw);
			    		withdrawTextField.clear();
			    		balanceLabel.setText("Balance: " +Double.toString(savings.getBalance()));
			    		isBadInput=false; // if the parse doesn't fail input is good
			    		
			    		}catch(Exception badInputWithdraw){
			    			depositTextField.clear();
			    		}		
			    	
			    	if (isBadInput==true) System.out.println("cannot parse input");
			    }
				});
			
			primaryStage.setScene(scene);
			primaryStage.show();
		
			
		}catch(IOException e) {
			File account=new File("accountFiles/account1");
			PrintWriter writer = new PrintWriter(account);
			writer.println("hello");
			writer.close();
			
			VBox vbox=new VBox(3);
			HBox radioBox= new HBox(2);
			
			 ToggleGroup tGroup = new ToggleGroup();
			
			RadioButton chequing =new RadioButton("Chequing Account");
			RadioButton saving =new RadioButton("Savings Account");
			
			vbox.getChildren().add(radioBox);
			
			chequing.setToggleGroup(tGroup);
			saving.setToggleGroup(tGroup);
			
			radioBox.getChildren().add(chequing);
			radioBox.getChildren().add(saving);
			
			Scene scene = new Scene(vbox,400,200);
			primaryStage.setTitle("BankApplication");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		
		
}
}
