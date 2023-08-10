package com.smallworld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TransactionManager {

    private static TransactionManager instance;

    private static ArrayList<Transaction> transactions;

    private TransactionManager() {

    }

    public void initialize(String fileName){
        loadTransactionData(fileName);
    }

    public static TransactionManager getInstance() {

        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    //we are reading this data at point of initializing since json data is not changing In case of reading from DB this logic will change
    private static void loadTransactionData(String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            transactions = objectMapper.readValue(new File(fileName).getAbsoluteFile(),
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Transaction.class));

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }

}
