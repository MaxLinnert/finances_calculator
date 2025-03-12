/*
Author: Andrew Chen, Max Linnert
Student No: 3409802, 3409778
Date: 01-06-2022 
Description: 
final - Account class - stores values: investment amount, interest rate and investment length. Also creates an investment table.
*/ 

//Account class
public class Account
{
    //setting instance variable
    private double rate;
    private int numberOfWeeks;
    private double amount;
 
   //default account constructor
    public Account()
    {
        rate = 0;
        numberOfWeeks = 0;
        amount = 0;
    }
    // constructor with parameters.
    public Account(double rate, int numberOfWeeks, double amount)
    {
        this.rate = rate;
        this.numberOfWeeks = numberOfWeeks;
        this.amount = amount;
    }
     
    //change and access rate variable
    public void setRate(double inputRate)
    {
        rate = inputRate;
    }
    public double getRate()
    {
        return(rate);
    }

    //change and access number of weeks variable.
    public void setNumberOfWeeks(int inputNumberOfWeeks)
    {
        numberOfWeeks = inputNumberOfWeeks;
    }
    public double getNumberOfWeeks()
    {
        return(numberOfWeeks);
    }
    
    //change and access amount variable
    public void setAmount(double inputAmount)
    {
        amount = inputAmount;
    }
    public double getAmount()
    {
        return(amount);
    }

    //method to print investment table
    public String calcInvestment()
    {
        //first we divide rate by 13 to get the number of 4 weeks blocks there are in a year
        rate /= 13;
        //then we create a new variable called money. This would be the amount from the investment
        double money = 0;
        //then we create another variable called output. This will be the ouput displayed
        // we use String.format to right align the "Balance"
        String output = String.format("Weeks %15s\n---------------------", "Balance");
        //loop that allows us to calculate the money for each of the 4 week blocks
        for (int set = 1; set <= numberOfWeeks / 4; set++)
        {
            //after every loop, money and output both get updated
            money = (money + amount * 4) * (rate/100+1);
            output = String.format(output+"\n"+"%-5d %14.2f", set*4, money);
        }
        //we add a final row to the output for any remaining weeks. If statement used so we don't repeat outputs for weeks divible by 4.
        if(numberOfWeeks%4>0)
        {
            output = String.format(output+"\n"+"%-5d %14.2f", numberOfWeeks, (money + amount * (numberOfWeeks%4)));
        }
        return output;
    }
}
