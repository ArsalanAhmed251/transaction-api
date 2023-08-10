package com.smallworld;

import com.smallworld.data.Transaction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    TransactionManager transactionManager;
    public static TransactionDataFetcher instance;

    private TransactionDataFetcher(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static TransactionDataFetcher getInstance(TransactionManager transactionManager){
        if (instance == null) {
            instance = new TransactionDataFetcher(transactionManager);
        }
        return instance;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {

        return transactionManager.getTransactions().stream()
                .distinct()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {

        return transactionManager.getTransactions().stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .distinct()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {

        return transactionManager.getTransactions().stream()
                .mapToDouble(Transaction::getAmount)
                .max().getAsDouble();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {

        long count = 0;

        //holding clients list
        List<String> clientsList = new ArrayList<>();

        //holding count of transaction by client
        Map<String, Long> clientCountMap = new HashMap<>();

        //saving client in clientList as per their occurrence in transactions
        transactionManager.getTransactions().stream().distinct().forEach(transaction -> {
            clientsList.add(transaction.getBeneficiaryFullName());
            clientsList.add(transaction.getSenderFullName());
        });

        //Iterating over client list and saving their occurrence count in map
        clientsList.forEach(client ->
            clientCountMap.put(client,  (clientCountMap.getOrDefault(client, 0L) + 1)));

        //Updating total count when client occurrence is equals to one
        for (String name : clientCountMap.keySet()) {
            if (clientCountMap.get(name) == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {

        Optional<Transaction> issuedTransaction = transactionManager.getTransactions().stream()
                .filter(transaction -> transaction.getSenderFullName().equals(clientFullName)
                        || transaction.getBeneficiaryFullName().equals(clientFullName))
                .filter(transaction -> !transaction.getIssueSolved())
                .findFirst();
        return issuedTransaction.isPresent();
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {

        return transactionManager.getTransactions()
                .stream()
                .distinct()
                .collect(Collectors
                        .toMap(Transaction::getBeneficiaryFullName, transaction -> transaction));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {

        return transactionManager.getTransactions()
                .stream()
                .filter(transaction -> !transaction.getIssueSolved())
                .map(Transaction::getIssueId)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {

        return transactionManager.getTransactions()
                .stream()
                .filter(Transaction::getIssueSolved)
                .filter(transaction -> transaction.getIssueMessage() != null)
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {

        return transactionManager.getTransactions()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(3)
                .toList();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {

        Map.Entry<String, Double> map = transactionManager.getTransactions().stream()
                .distinct()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName,
                        Collectors.summingDouble(Transaction::getAmount)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        return Optional.of(map.getKey());
    }

}
