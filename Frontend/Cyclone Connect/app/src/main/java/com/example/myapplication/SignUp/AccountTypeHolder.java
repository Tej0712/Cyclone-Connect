package com.example.myapplication.SignUp;
/**
 * AccountTypeHolder is a singleton class used to hold and manage the account type state across the application.
 * It provides a global access point to get or set the user's account type, ensuring that the account type
 * is consistent and accessible from any part of the application once set.
 *
 * This class is particularly useful in scenarios where the application needs to adapt its behavior or user
 * interface based on the type of user account, without needing to repeatedly pass the account type between
 * activities or storing it in a persistent storage.
 *
 * Usage example:
 * AccountTypeHolder.getInstance().setAccountType("STUDENT"); // Setting the account type
 * String accountType = AccountTypeHolder.getInstance().getAccountType(); // Retrieving the account type
 */
public class AccountTypeHolder {
    private static final AccountTypeHolder instance = new AccountTypeHolder();
    private String accountType;
    /**
     * Private constructor to prevent instantiation from outside the class.
     * Ensures that AccountTypeHolder is a singleton.
     */
    private AccountTypeHolder() { }
    /**
     * Provides access to the singleton instance of the AccountTypeHolder.
     *
     * @return The single instance of AccountTypeHolder.
     */
    public static AccountTypeHolder getInstance() {
        return instance;
    }
    /**
     * Retrieves the current account type stored in the singleton.
     *
     * @return A String representing the account type.
     */
    public String getAccountType() {
        return accountType;
    }
    /**
     * Updates the account type stored in the singleton.
     *
     * @param accountType A String representing the new account type to be set.
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

