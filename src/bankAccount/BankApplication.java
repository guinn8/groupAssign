package bankAccount;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
/**
 * <h1>BankApplication<h1>
 * 
 * a windowed application that can handle withdrawing and depositing from an account 
 * if no account exists the program prompts the user for input and saves it to a text file to be read later
 * inherits methods from the application class in jFX
 * 
 * <p>
 * @author T01 Group 4
 * @author Gavin Guinn
 * @since Aug 8, 2018
 * @version 2.0
 */
public class BankApplication extends Application {
	Scene scene;
	/**
	 * Launches the javaFX app.
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
	   
	/**
	 * method creates and populates a javaFX scene with a scene chosen by the try block
	 * @param primaryStage is a Stage object
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BankAccount account = null;
			try{
				account=readFile();
			}
			catch(Exception IOException ) {
				getInput();
			}
			if(account!=null) {
				interact(account);
			}		
		primaryStage.setTitle("BankApplication");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * This class attempts to read a text file called account
	 * @return account is the account object created from the text file 
	 * @throws IOException if the file cannot be found
	 */
	public static BankAccount readFile() throws IOException{
		
		BankAccount account = null;
		Customer customer = null;
		String[] accountArr= new String[6];
		int counter = 0;
		
		try {
			File accountFile=new File("accountFiles/account");
			BufferedReader reader=new BufferedReader(new FileReader(accountFile));
			String line =reader.readLine();
			
			while( line != null) {
				accountArr[counter]=line;
				line =reader.readLine();
				counter++;
			}
			reader.close();
			customer= new Customer(accountArr[0],Integer.parseInt(accountArr[2]));
			
			if (accountArr[1].equals("SavingsAccount")) account = new SavingsAccount(customer,Double.parseDouble(accountArr[3]));
			else if (accountArr[1].equals("ChequingAccount")) account = new ChequingAccount(customer, Double.parseDouble(accountArr[3]), Double.parseDouble(accountArr[4]));
			
			return account;
			
		}catch (Exception IOException){ 
			throw IOException;
		}
	}
	
	
	/**
	 * This method populates a javaFX scene with a interface that prompts the user to 
	 * create a new account 
	 */
	public void getInput() {
		
		Customer c = new Customer();
		c.setCustomerID((int)(Math.random()*((9999-2000)+1))+2000);

		HBox radioBox= new HBox(2);
		ToggleGroup tGroup = new ToggleGroup();
		
		RadioButton chequing =new RadioButton("Chequing Account");
		chequing.setToggleGroup(tGroup);
		chequing.setUserData("ChequingAccount");
		radioBox.getChildren().add(chequing);
		
		RadioButton saving =new RadioButton("Savings Account");
		saving.setUserData("SavingsAccount");
		saving.setToggleGroup(tGroup);
		radioBox.getChildren().add(saving);
		
		Label id= new Label("Customer ID: "+Integer.toString(c.getID()));
		TextField name = new TextField();
		name.setPromptText("Enter your name");
	
		Button execute= new Button("execute");
		
		VBox root = new VBox (3);
		root.getChildren().add(radioBox);
		root.getChildren().add(id);
		root.getChildren().add(name);
		root.getChildren().add(execute);

		scene = new Scene(root,400,200);
		
		execute.setOnAction(new EventHandler<ActionEvent>() {
		
		    public void handle(ActionEvent e) {
		    	if(name.getText()!=null) {
		    		 c.setName(name.getText());
		    		 Platform.exit();
		    	}
		    	
		    	File account=new File("accountFiles/account");
				PrintWriter writer;
				try {
					writer = new PrintWriter(account);
					writer.println(c.getName());
					writer.println((String) tGroup.getSelectedToggle().getUserData());
					writer.println(c.getID());
					writer.println(0.00);
					writer.print(0.00);
					writer.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		tGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			}
		});
	}
	
	/**
	 * This method populates a javaFX scene with a interface that prompts the user to 
	 * create manipulate an existing account 
	 * @param account
	 * @throws IOException
	 */
	public void interact(BankAccount account) throws IOException{
		String accountType = null;
		
		if (account instanceof SavingsAccount) accountType="Saving Account";
		else if (account instanceof ChequingAccount)accountType="Chequing Account";
		
		Button executeButton= new Button("Execute");
		TextField depositTextField = new TextField();
		TextField withdrawTextField = new TextField();
		
		Label badInput = new Label("Please enter a valid number");
		badInput.setTextFill(Color.RED);
		badInput.setVisible(false);
		
		Label customerNameLabel= new Label("Name: " +account.getCustomer().getName());
		Label customerIDLabel = new Label("Account ID: " + Integer.toString(account.getCustomer().getID()));
		Label balanceLabel = new Label("Balance: " +Double.toString(account.getBalance()));
		Label accountLabel = new Label(accountType);

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
		root.getChildren().add(badInput);
		root.getChildren().add(accountLabel);
		
		scene = new Scene(root,400,200);
		
		executeButton.setOnAction(new EventHandler<ActionEvent>() { //creates a new event handler object
			
			/**
			 * this method processes the data in the text fields and ensures it is a double
			 * @param e is a ActionEvent object
			 */
		    public void handle(ActionEvent e) {
		    	badInput.setVisible(false);
		    
		    	try{ 
		    		if(depositTextField.getText().length()>0) {
			    		double deposit=Double.parseDouble(depositTextField.getText());
			    		account.deposit(deposit);
			    		depositTextField.clear();
			    		balanceLabel.setText("Balance: " +Double.toString(account.getBalance()));
		    		}
		    		
		    	   }catch(Exception badInputDeposit){
		    		   depositTextField.clear();
		    			badInput.setVisible(true);
		    	   }
		    	
		    	// tries to parse data in withdraw field	
		    	try{
		    		if(withdrawTextField.getText().length()>0) {
			    		double withdraw=Double.parseDouble(withdrawTextField.getText());
			    		account.withdraw(withdraw);
			    		withdrawTextField.clear();
			    		balanceLabel.setText("Balance: " +Double.toString(account.getBalance()));
		    		}
		    		}catch(Exception badInputWithdraw){
		    			depositTextField.clear();
		    			badInput.setVisible(true);
		    		}			
		    }
		});
	}
	
	
}
