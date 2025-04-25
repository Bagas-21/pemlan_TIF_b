/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mavenproject26;

/**
 *
 * @author user
 */
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String name;
    private double balance;
    private int transactionCount;

    public Account(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.transactionCount = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
    public void deposit(double amount){
        balance+=amount;
        transactionCount++;
    }
    public void withdraw(double amount){
        balance-=amount;
        transactionCount++;
    }
    public boolean limit(double limitTransaksi){
        return transactionCount>=limitTransaksi;
    }
}
class Transaction{
    private static Account[] listAccount;
    private static int acccnt;
    private static final int MAX_ACCNT = 5;
    private static final int LIMIT_TRANSACTION = 3;
    private static final double MAX_PENARIKAN = 5000000;

    public Transaction() {
        this.listAccount = new Account[MAX_ACCNT];
        this.acccnt = 0;
    }
    private Account findAcc(String accStringNum){
        for(int i=0;i<acccnt;i++){
            if(listAccount[i].equals(accStringNum)){
                return listAccount[i];
            }
        }
        return null;
    }
    public void buatAccount(String accNum, String nama, double initialAmount){
        if(acccnt >= MAX_ACCNT){
            System.out.println("ERROR: BankFullException - Bank telah mencapai batas maksimal 5 akun");
            return;
        }
        if(findAcc(accNum)!=null){
            System.out.println("ERROR: Nomor akun " + accNum + " sudah digunakan");
            return;
        }
        if(initialAmount < 0){
            System.out.println("ERROR: InvalidAmountException - Saldo awal tidak boleh negatif");
            return;
        }
        listAccount[acccnt++] = new Account(accNum, nama, initialAmount);
        System.out.printf("Akun %s berhasil dibuat untuk %s dengan saldo %.1f%n", accNum, nama, initialAmount);
    }
    public void deposit(String accStringNum, double amount){
        Account account = findAcc(accStringNum);
        if(account == null){
            System.out.println("ERROR: AccountNotFoundException - Akun " + accStringNum + " tidak ditemukan");
            return;
        }
        if (amount < 0){
            System.out.println("ERROR: InvalidAmountException - Jumlah deposit harus lebih dari 0");
            return;
        }
        if(account.limit(LIMIT_TRANSACTION)){
            System.out.println("ERROR: DailyLimitExceededException - Akun " + accStringNum+ " telah mencapai batas 3 transaksi harian");
            return;
        }
        account.deposit(amount);
        System.out.printf("Deposit ke %s berhasil. Saldo sekarang: %.1f\n", accStringNum, account.getBalance());
    }
    public void withdraw(String accStringNum, double amount){
        Account account = findAcc(accStringNum);
        if(account == null){
            System.out.println("ERROR: AccountNotFoundException - Akun " + accStringNum + " tidak ditemukan");
            return;
        }
        if(amount<=0){
            System.out.println("ERROR: InvalidAmountException - Jumlah penarikan harus lebih dari 0");
            return;
        }
        if(amount > MAX_PENARIKAN){
            System.out.printf("ERROR: InvalidAmountException - Jumlah penarikan maksimal %.1f\n", MAX_PENARIKAN);
            return;
        }
        if(account.limit(LIMIT_TRANSACTION)){
            System.out.println("ERROR: DailyLimitExceededException - Akun "+ accStringNum +"telah mencapai batas 3 transaksi harian");
            return;
        }
        if(account.getBalance() < amount){
            System.out.printf("ERROR: InsufficientFundsException - Saldo tidak mencukupi untuk penarikan sebesar %.1f\n", amount);
            return;
        }
        account.withdraw(amount);
        System.out.printf("Penarikan dari %s berhasil. Saldo sekarang: %.1f\n ", accStringNum, account.getBalance());
    }
    public void checkBalance(String accStringNum){
        Account account = findAcc(accStringNum);
        if(account == null){
            System.out.println("ERROR: AccountNotFoundException - Akun " + accStringNum + " tidak ditemukan");
            return;
        }
        System.out.printf("Saldo %s: %.1f\n", accStringNum, account.getBalance());
    }
}
public class BankToyib{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Transaction bank = new Transaction();
        
        while(true){
            try{
                int check = in.nextInt();
                switch(check){
                    case 1 ->{
                        try {
                            String accNum = in.next();
                            String name = in.next();
                            double balance = in.nextDouble();
                            bank.buatAccount(accNum, name, balance);
                        } catch (Exception e) {
                            System.out.println("ERROR: Format tidak valid untuk membuat akun");
                        }
                        break;
                    }
                    case 2 ->{
                        try {
                            String accNum = in.next();
                            double amount = in.nextDouble();
                            bank.deposit(accNum, amount);
                        } catch (Exception e) {
                            System.out.println("ERROR: Format tidak valid untuk deposit");
                        }
                        break;
                    }
                    case 3 ->{
                        try {
                            String accNum = in.next();
                            double amount = in.nextDouble();
                            bank.withdraw(accNum, amount);
                        } catch (Exception e) {
                            System.out.println("ERROR: Format tidak valid untuk penarikan");
                        }
                        break;
                    }
                    case 4 ->{
                        try {
                            String accNum = in.next();
                            bank.checkBalance(accNum);
                        } catch (Exception e) {
                            System.out.println("ERROR: Format tidak valid untuk cek saldo");
                        }
                        break;
                    }
                    case 5->{
                        System.out.println("Program selesai!");
                        return;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("ERROR: Kode operasi harus berupa angka");
                in.nextLine();
            }
        }
    }
}