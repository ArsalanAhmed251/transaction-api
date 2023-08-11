package com.smallworld;

public class TransactionApplication {

    //Class to test functions
    public static void main(String[] args) {

        TransactionManager transactionManager = TransactionManager.getInstance();
        transactionManager.initialize("transactions.json");
        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        System.out.println("Total Transactions amount : " + td.getTotalTransactionAmount());
        System.out.println("Total Transactions amount by user Tom Shelby : "
                + td.getTotalTransactionAmountSentBy("Tom Shelby"));
        System.out.println("Max transaction amount is : " +td.getMaxTransactionAmount());
        System.out.println("Unique client count is : " + td.countUniqueClients());
        System.out.println("Luca Changretta has issue in transaction : "
                + td.hasOpenComplianceIssues("Luca Changretta"));
        System.out.println("transaction map is : " + td.getTransactionsByBeneficiaryName().toString());
        System.out.println("unsolved issues are : "+ td.getUnsolvedIssueIds());
        System.out.println("solved issue messages : " + td.getAllSolvedIssueMessages());
        System.out.println("Top 3 transactions by amount : " + td.getTop3TransactionsByAmount());
        System.out.println("Top sender by total amount : " + td.getTopSender());

    }
}
