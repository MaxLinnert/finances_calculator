/*
Author: Andrew Chen, Max Linnert
Student No: 3409802, 3409778 
Date: 01-64-2022 
Description: 
final - Client class - stores values: name, gross salary, net salary, resident, tax, medicare and weekly expenses.
Also contains a account array and calculates tax as well as medicare and net salary
*/ 

//Client class
public class Client
{
    //setting instance variable *all private.
    private String name; 
    private Account[] account = new Account[2];
    private int noAccounts = 0;
    private double grossSalary;
    private double netSalary; 
    private boolean resident;
    private double tax;
    private double medicare;
    private double weeklyExpenses;
    private double remainingAmount;
    

    //client constructor
    public Client()
    {
        name = "";
        grossSalary = 0;
        netSalary = 0;
        resident = true;
        tax = 0;
        medicare = 0;
        weeklyExpenses = 0;
        remainingAmount = 0;
        for(int i = 0; i<2; i++)
        {
            account[i] = new Account(); 
        }
    }

//change and access name variable
    public String getName()
    {
        return (name);
    }
    public void setName(String inputName)
    {
        name = inputName;
    }

//change and access gross salary variable
    public double getGrossSalary()
    {
        return (grossSalary);
    }
    public void setGrossSalary(Double inputGrossSalary)
    {
        grossSalary = inputGrossSalary;
    }

//change and access resident variable
    public Boolean getResident()
    {
        return resident;
    }
    public void setResident(Boolean inputResident)
    {
        resident = inputResident;
    }
    
//Tax methods
    //Calculates tax according to specified tax table. Nested if else statements.
    public double calcTax()
    {
        //Taxes if user is a resident
        if (resident == true)
        {
            if(grossSalary <= 18200){
                tax = 0;
            }
            else if(grossSalary <= 45000){
                tax = (grossSalary - 18200) * 0.19;
            }
            else if(grossSalary <= 120000){
                tax = ((grossSalary - 45000) * 0.325) + 5092;
            }
            else if(grossSalary <= 180000){
                tax = ((grossSalary - 120000) * 0.37) + 29467;
            }
            else
            {
                tax = ((grossSalary - 180000) * 0.45) + 51667;
            } 
        }
        //Taxes if user isn't a resident
        else
        {
            if(grossSalary <= 120000){
                tax = grossSalary * 0.325;
            }
            else if(grossSalary <= 180000){
                tax = ((grossSalary - 120000) * 0.37) + 39000;
            }
            else
            {
                tax = ((grossSalary - 180000) * 0.45) + 61200;
            } 
        }
        //calling this method would return tax
        return tax;
    }

    //unused methods to access and change tax. Needed to include for the assessment.
    public double getTax(){
        return tax;
    }
    public void setTax(double inputTax)
    {
        tax = inputTax;
    }

//net salary methods
    //calculates net salary from gross salary and tax then returns.
    public double netSalary()
    {
        netSalary = grossSalary-tax-calcMedicare();
        return netSalary;
    }
    //unused methods to access and change net salary. Needed to include for the assessment.
    public double getNetSalary(){
        return netSalary;
    }
    public void setNetSalary(double inputNetSalary)
    {
        netSalary = inputNetSalary;
    }

//medicare methods
    //calculates medicare and returns it
    public double calcMedicare()
    {
        if(resident == true && grossSalary > 29032)
            return grossSalary * 0.02;
        else
            return 0;   
    }
    //unused methods to access and change medicare. Needed to include for the assessment.
    public double getMedicare(){
        return medicare;
    }
    public void setMedicare(double inputMedicare)
    {
        medicare = inputMedicare;
    }  

//change and access weekly expenses variable
    public double getWeeklyExpenses()
    {
        return (weeklyExpenses);
    }
    public void setWeeklyExpenses(Double inputWeeklyExpenses)
    {
        weeklyExpenses = inputWeeklyExpenses;
    }

//all methods that change and access account.
    //gets and sets remaining amount available to invest.
    public void setRemainingAmount(Double inputRemainingAmount)
    {
        remainingAmount = inputRemainingAmount;
    }
    public double getRemainingAmount()
    {
        return remainingAmount;
    }

    //gets and sets the number of accounts
    public int getNoAccounts()
    {
        return noAccounts;
    }
    public void addNoAccounts()
    {
        noAccounts++;
    }

//method that returns the string for displaying a client.
    public String displayClient(){
        String output = 
        "\nName: " +getName()+
        "\nYearly Gross Salary: $" +String.format("%.2f",getGrossSalary())+
        "\nWeekly Gross Salary: $" +String.format("%.2f", getGrossSalary()/ 52)+
        "\nResidency Status: " +getResident()+
        "\nYear Tax: $" +String.format("%.2f",calcTax())+
        "\nWeekly Tax: $" +String.format("%.2f",calcTax()/52)+
        "\nYearly Net Salary $" + String.format("%.2f",netSalary())+
        "\nWeekly Net Salary $"+ String.format("%.2f", netSalary()/ 52)+
        "\nMedicare fee $"+String.format("%.2f",calcMedicare())+
        "\nWeekly Expenses $"+String.format("%.2f",getWeeklyExpenses())+
        "\nRemaining Investment Amount $"+String.format("%.2f", getRemainingAmount());
        //loops through each account to display them.
        for(int i = 1; i <= noAccounts; i++)
            output += "\nAccount "+i+":\n"+displayAccount(i);
        //returns the string at the end.
        return output;
    }


//Methods that access from Calculator interface class and Account class to do investment.
    //collects rate input and sets it to account class
    public void setAccountRate(int position, double inputRate)
    {
        account[position].setRate(inputRate);
    }
    //collects number of weeks input and sets it to account class
    public void setAccountNumberOfWeeks(int position, int inputNumberOfWeeks)
    {
        account[position].setNumberOfWeeks(inputNumberOfWeeks);
    }
    //collects amount input and sets it to account class.
    public void setAccountAmount(int position, double inputAmount)
    {
        account[position].setAmount(inputAmount);
    }
    //gets the table from account and returns it to calculator interface to be printed.
    public String displayAccount(int position)
    {
        return account[position-1].calcInvestment();
    }
    // uses the method of shifting the account to the right of the selected account to the left.
    public void deleteAccount(int position)
    {
        if(position == 1){
            account[position-1] = account[position];
            noAccounts--;
        }
        else // if the selected account is the last one, program just reduces the number of accounts.
        {
            noAccounts--;
        }
    }
}


