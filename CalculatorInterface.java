/*
Author: Andrew Chen, Max Linnert
Student No: 3409802, 3409778
Date: 01-06-2022 
Description: 
final - Calculator Interface class - Contains all the methods used for user input. Also displays information to user.
*/ 

//import GUI and printwriterg
import javax.swing.*;
import java.io.*;

//Calculator Interface Class
public class CalculatorInterface
{
    // private instance variables
    // client has been set as a global, just to account for extra methods below.
    private Client[] client = new Client[5];
    private int noClients = 0;
    private String fileName = "output.txt";
    private PrintWriter outputStream;

    //run method
    public void run() 
    {
        //variables
        int choice;
        String name;

        //try catch for saving file.
        try 
        {
            outputStream = new PrintWriter(fileName);
        } 
        

        //initializing each client
        for(int i = 0; i<5; i++)
        {
            client[i] = new Client(); 
        }
        
        //user inputs their selection here:
        choice = Integer.parseInt(JOptionPane.showInputDialog("Select and enter\n1 - add a new client\n2 - delete a client\n3 - display a client\n4 - display all clients\n5 - add an account\n6 - display an account\n7 - delete an account\n8 - save file as .txt\n9 - exit"));
        while(choice != 9)  {
            //start switch that runs a method or breaks depending on user input.
            switch(choice) {
                case 1: addClient();
                        break;
                case 2: name = JOptionPane.showInputDialog("inform the name of the client: ");
                        deleteClient(name);
                        break;
                case 3: name = JOptionPane.showInputDialog("inform the name of the client: ");
                        displayClient(name);
                        break;
                case 4: displayAllClients();
                        break;
                case 5: name = JOptionPane.showInputDialog("inform the name of the client: ");
                        addAccount(name);
                        break;
                case 6: name = JOptionPane.showInputDialog("inform the name of the client: ");
                        displayAccount(name);
                        break;
                case 7: name = JOptionPane.showInputDialog("inform the name of the client: ");
                        deleteAccount(name);
                        break;
                case 8: saveFile();
                        break;                        
                case 9: break;
                default: System.out.println("Invalid Selection");
            }//end switch    
            choice = Integer.parseInt(JOptionPane.showInputDialog("Select and enter\n1 - add a new client\n2 - delete a client\n3 - display a client\n4 - display all clients\n5 - add an account\n6 - display an account\n7 - delete an account\n8 - save file as .txt\n9 - exit"));;
        }
    }      
    
    //main method. Just runs the run method
    public static void main(String[] args)
    {
        CalculatorInterface calc = new CalculatorInterface();
        calc.run();
    }

    //method that adds client
    public void addClient()
    {
        //variables
        String name, resident, checkIncome;
        Double grossSalary, weeklyExpenses;

        //if statement to make sure user cannot input more than 5 clients
        if (noClients<5)
        {
            //user enters name. Code sets it into client
            name = JOptionPane.showInputDialog("Full Name: ");
            //user enters this loop if there is no space, or the space is at the start or end.
            while (name.indexOf(" ") <= 0 || name.lastIndexOf(" ") == name.length()-1)
            { 
                name = JOptionPane.showInputDialog("Must have a first and a last name. Enter name again: ");
            } 
            client[noClients].setName(name);

            //user enters income. Code sets it into client.
            grossSalary = Double.parseDouble(JOptionPane.showInputDialog("Gross salary: "));
            //if user enters a number that is less than zero the program would ask them to enter the value again.
            while (grossSalary <= 0)
            {
                grossSalary = Double.parseDouble(JOptionPane.showInputDialog("Must be a number greater than 0. Enter gross salary again: "));
            }
            client[noClients].setGrossSalary(grossSalary);

            //user enters residency. Code sets it into client
            resident = JOptionPane.showInputDialog("Are you a resident (yes or no): ");
            //if user doent input "yes" or "no", then the program asks them to enter again.
            while (resident.equals("yes")==false && resident.equals("no")==false)
            {
                resident = JOptionPane.showInputDialog("You must enter a either \"yes\" or \"no\", try again.\nAre you a resident (yes or no): ");
            }
            client[noClients].setGrossSalary(grossSalary);

            //user enters weekly expenses. Code sets it into client
            weeklyExpenses = Double.parseDouble(JOptionPane.showInputDialog("How much do you require each week for living expenses?: "));
            //while loop for the potential user input errors. If weekly expenses is over the users weekly salary or if its less than 0, the program would enter the loop
            while (weeklyExpenses <= 0 || weeklyExpenses > client[noClients].netSalary()/52)
            {  
                //checks which error. If weekly expenses is less than 0, program will just ask for user to re-enter the value.
                if(weeklyExpenses<=0)
                {
                    weeklyExpenses = Double.parseDouble(JOptionPane.showInputDialog("Must be a number greater than 0. Enter weekly expenses again: "));
                }
                //else if user enters a value over what they can afford, program would ask if they want to enter a new weekly expense.
                else
                {
                    checkIncome = JOptionPane.showInputDialog(null, "The number that you have entered as your weekly expenses is too large as your net salary doesn't cover it, would you like to enter a new weekly expenses (yes or no): ");
                    //if the user answers without a "yes" or a "no", than the program would enter a loop and ask them to do so
                    while(checkIncome.equals("yes") == false && checkIncome.equals("no") == false){
                        JOptionPane.showMessageDialog(null, "You must enter either \"yes\" or \"no\"\n");
                        checkIncome = JOptionPane.showInputDialog(null, "The number that you have entered as your weekly expenditure is too large as your net salary doesn't cover it, would you like to enter a new weekly expenditure (yes or no): ");
                    }
                    //if user enters "no", the program would terminate
                    if (checkIncome.equals("no") == true)
                    {
                        JOptionPane.showMessageDialog(null, "This program is being terminated as you don't have enough money to invest");
                        System.exit(0);
                    }
                    //else if user enters "yes", the program would ask again and check that value again in the loop.
                    else
                    weeklyExpenses = Double.parseDouble(JOptionPane.showInputDialog("How much do you require each week for living expenses?: "));
                }
            }
            client[noClients].setWeeklyExpenses(weeklyExpenses);
            //sets clients remaining amount available to invest.
            client[noClients].setRemainingAmount(client[noClients].netSalary()-weeklyExpenses);
            //adds to number of clients
            noClients++;
        }
        //if more than 5 clients, print message
        else
        {
            JOptionPane.showMessageDialog(null, "could not add client as you have too many clients.");
        }
    }
    
    // Method that deletes client
    public void deleteClient(String name)
    {
        //sets counter in case the client does not exist 
        int counter = 0;

        //loops through all the clients and checks name.
        for(int i = 0; i < noClients-1; i++)
        {
            if(name.equals(client[i].getName())==true)
            {
                //if name matchs, deletes by shifting all the clients to the left
                client[i] = client[i+1];
                noClients--;
                JOptionPane.showMessageDialog(null, "The client was deleted");
                break;
            }
            //using a counter to track if loop had cycled through all clients.
            counter++;
        }
        //extra if statment for if the selected client is that last in the array. This is needed as the client to the right of this doesnt exist.
        if(name.equals(client[noClients-1].getName())==true)
        {
            noClients--;
            JOptionPane.showMessageDialog(null, "The client was deleted");
            counter++;
        }
        //if counter reaches 5, that means the client doesn't exist.
        else if (counter == 5)
            JOptionPane.showMessageDialog(null, "The client does not exist");
            
    }
    
    // Method to display client.
    public void displayClient(String name)
    {
        //again uses the counter method to track clients.
        int counter = 0;
    
        // Go's through all the clients    
        for(int i = 0; i < 5; i++){
            if(name.equals(client[i].getName())==true && i<noClients)
            {
                //when a name matches, it runs the displayClient method for the selected client found in Client.java 
                JOptionPane.showMessageDialog(null, client[i].displayClient());
                break;
            }
            //updates counter
            counter++;
        }
        //if counter reaches 5, client doesn't exist.
        if(counter == 5)
            JOptionPane.showMessageDialog(null, "The client does not exist");
        
    }

    //Method that displays all clients.
    public void displayAllClients()
    {
        // starts by setting a string variable for the output. This makes it easier to concatenate variables.
        String output = "";
        // if there are no clients, show error message.
        if(noClients == 0)
            JOptionPane.showMessageDialog(null, "There are no clients");
        else
        {
            //loops through each client and adds their info to the output variable.
            for(int i=0; i<noClients; i++)
            {
                output += String.format("\n\nClient %o\nName: %s\nResident: $%b\nGross Salary per week: $%.2f",(i+1), client[i].getName(), client[i].getResident(), client[i].getGrossSalary()/52);
                output += String.format("\nNet Salary per week: $%.2f\nTax per week: $%.2f\nMedicare per week", client[i].getNetSalary()/52, client[i].getTax()/52, client[i].getMedicare()/52);
                output += String.format("\nWeekly Expenses: $%.2f", client[i].getWeeklyExpenses());
                //check if client has accounts, if so access them through the Client.java and add to output.
                if(client[i].getNoAccounts() == 0){
                    output += ("\nNo Accounts");
                }
                else
                    output += ("\nNumber of Accounts: $" + client[i].getNoAccounts());
            }
            //at the end, print output.
            JOptionPane.showMessageDialog(null, output);
        }
    }

    //method that adds an account. *all references to account first go through client.java
    public void addAccount(String name)
    {
        //using counter method as before to track clients.
        int counter = 0;

        //loops through each of the clients to find selected client. 
        for(int i = 0; i < 5; i++){
            if(name.equals(client[i].getName())==true && i<noClients)
            {
                //checks if the client already has two accounts, if so prints error.
                if (client[i].getNoAccounts()>1)
                    JOptionPane.showMessageDialog(null, "This client already has two accounts!");
                else // else goes through user inputs to set an account.
                {
                    //initially asks for the invesment amount.
                    Double amount = Double.parseDouble(JOptionPane.showInputDialog("Investment Amount (per week): "));
                    // if investment amount is less than 0 or if user cannot affort, the program would enter the loop and ask for amount again.
                    System.out.println(client[i].getRemainingAmount());
                    while (amount <= 0 || amount >= client[i].getRemainingAmount())
                    {
                        amount = Double.parseDouble(JOptionPane.showInputDialog("Amount must be affordable and over 0. Enter Amount again: "));
                    }
                    client[i].setAccountAmount(client[i].getNoAccounts(), amount);
                    client[i].setRemainingAmount(client[i].getRemainingAmount()-amount);
                    //initially asks for interest rate.
                    Double rate = Double.parseDouble(JOptionPane.showInputDialog("Interest Rate (%): "));
                    //if rate is outside of the 1% to 20% range, program would ask for the input again.
                    while (rate < 1 && rate > 20)
                    { 
                        rate = Double.parseDouble(JOptionPane.showInputDialog("Must be between 1% and 20%. Enter Interest Rate again: "));
                    }
                    client[i].setAccountRate(client[i].getNoAccounts(), rate);

                    //initially asks for user to input investment length.
                    int numberOfWeeks = Integer.parseInt(JOptionPane.showInputDialog("Number of weeks: "));
                    //if investment length is less than 0, program would ask for user to re-enter.
                    while (numberOfWeeks <= 0)
                    { 
                        numberOfWeeks = Integer.parseInt(JOptionPane.showInputDialog("Must be a number greater than 0. Enter Number of Weeks again: "));
                    }
                    client[i].setAccountNumberOfWeeks(client[i].getNoAccounts(), numberOfWeeks);
                    client[i].addNoAccounts();
                }
                break;
            }
            // update counter
            counter++;
        }
        //checks if counter is 5, if so the client doesn't exist.
        if(counter == 5)
            JOptionPane.showMessageDialog(null, "The client does not exist"); 
    }

    //method that displays account. *again all accessing of account.java variables first pass through client.java
    public void displayAccount(String name)
    {
        //counter method.
        int counter = 0;

        //loops through each client.
        for(int i = 0; i < 5; i++){
            //if name matches, program would ask which account the user would like to display.
            if(name.equals(client[i].getName())==true && i<noClients)
            {
                int accountNumber = Integer.parseInt(JOptionPane.showInputDialog( "Which account would you like to display (1 or 2): "));
                //if the account doesn;t exist of user inputs negative.
                if(accountNumber>client[i].getNoAccounts() || accountNumber<= 0)
                    JOptionPane.showMessageDialog(null, "That account does not exist.");
                else // else displays account through the client display account method.
                {
                    JOptionPane.showMessageDialog(null, client[i].displayAccount(accountNumber));
                }
                break;
            }
            //update counter
            counter++;
        }
        //if counter is 5, client doesn't exist
        if(counter == 5)
            JOptionPane.showMessageDialog(null, "The client does not exist");
    }

    //method that deletes account.
    public void deleteAccount(String name)
    {
        //counter method
        int counter = 0;

        //loops through each client
        for(int i = 0; i < 5; i++){
            //if name matches, program will ask user which account it would like to delete.
            if(name.equals(client[i].getName())==true && i<noClients)
            {
                int accountNumber = Integer.parseInt(JOptionPane.showInputDialog( "Which account would you like to delete (1 or 2): "));
                //checks if account exists.
                if(accountNumber>client[i].getNoAccounts() || accountNumber<= 0)
                    JOptionPane.showMessageDialog(null, "That account does not exist.");
                else // if it does, deletes account through client.java deleteAccount method.
                {
                    client[i].deleteAccount(accountNumber);
                    JOptionPane.showMessageDialog(null, "This account has been deleted");
                }
                break;
            }
            // updates counter
            counter++;
        }
        //if counter is 5, client doesn't exist.
        if(counter == 5)
            JOptionPane.showMessageDialog(null, "The client does not exist");
    }

    //method that saves all clients to output.txt file
    public void saveFile()
    {
        //if there are no clients, just prints no clients to the outputStream.
        if(noClients == 0)
            outputStream.println("There are no clients");
        else
        {
            //otherwise, loops through all the clients and prints their all of their info to the output stream.
            for(int i = 0; i<noClients; i++)
            {
                outputStream.println(        
                "\nName: " +client[i].getName()+
                "\nYearly Gross Salary: $" +String.format("%.2f",client[i].getGrossSalary())+
                "\nWeekly Gross Salary: $" +String.format("%.2f", client[i].getGrossSalary()/ 52)+
                "\nResidency Status: " +client[i].getResident()+
                "\nYear Tax: $" +String.format("%.2f",client[i].calcTax())+
                "\nWeekly Tax: $" +String.format("%.2f",client[i].calcTax()/52)+
                "\nYearly Net Salary $" + String.format("%.2f",client[i].netSalary())+
                "\nWeekly Net Salary $"+ String.format("%.2f", client[i].netSalary()/ 52)+
                "\nMedicare fee $"+String.format("%.2f",client[i].calcMedicare())+
                "\nWeekly Expenses $"+String.format("%.2f",client[i].getWeeklyExpenses()));
                
                //checks if the client has accounts. // if not prints no accounts.
                if(client[i].getNoAccounts() == 0)
                        outputStream.println("This client has no accounts");
                else //else loops through all the accounts of the client.
                {
                    for(int y = 1; y<=client[i].getNoAccounts(); y++)
                        outputStream.println(client[i].displayAccount(y));
                }
            }
        }    
        //closes the output stream immediatly after updating it.
        outputStream.close();
        //success message.
        JOptionPane.showMessageDialog(null, "Those lines were written to: " + fileName);

    }
}   