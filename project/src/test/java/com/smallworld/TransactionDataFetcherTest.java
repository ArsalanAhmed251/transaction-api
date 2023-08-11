package com.smallworld;

import com.smallworld.data.Transaction;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TransactionDataFetcherTest extends TestCase {

    TransactionManager transactionManager = TransactionManager.getInstance();
    TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);

    @BeforeClass
    public void setUp(){
        transactionManager.initialize("src/test/resources/test-data.json");
    }
    @Test
    public void testTotalTransactionAmount() {


        Double actual = td.getTotalTransactionAmount();
        Double expected = 2889.17;
        assertEquals(expected,actual);

    }

    @Test
    public void testTotalTransactionAmountSentBy() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Double actual = td.getTotalTransactionAmountSentBy("Tom Shelby");
        Double expected = 678.06;
        assertEquals(expected,actual);

    }

    @Test
    public void testMaxTransactionAmount() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Double actual = td.getMaxTransactionAmount();
        Double expected = 985.0;
        assertEquals(expected,actual);

    }

    @Test
    public void testCountUniqueClients() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        long actual = td.countUniqueClients();
        long expected = 10;
        assertEquals(expected,actual);

    }

    @Test
    public void testOpenComplianceIssues() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Boolean actual = td.hasOpenComplianceIssues("Tom Shelby");
        Boolean expected = true;
        assertEquals(expected,actual);

    }

    @Test
    public void testTransactionsByBeneficiaryName() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Map<String, Transaction> actual = td.getTransactionsByBeneficiaryName();
        Map<String, Transaction> expected = TestUtils.getMockTransactionsByBeneficiaryName();
        assertEquals(expected,actual);

    }

    @Test
    public void testUnsolvedIssueIds() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Set<Integer> actual = td.getUnsolvedIssueIds();
        Set<Integer> expected = TestUtils.getMockUnsolvedIssueIds();
        assertEquals(expected,actual);

    }

    @Test
    public void testAllSolvedIssueMessages() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        List<String> actual = td.getAllSolvedIssueMessages();
        List<String> expected = TestUtils.getMockSolvedIssueMessages();
        assertEquals(expected,actual);

    }

    @Test
    public void testTop3TransactionsByAmount() {

        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        List<Transaction> actual = td.getTop3TransactionsByAmount();
        List<Transaction> expected = TestUtils.getMockTop3Transactions();
        assertEquals(expected,actual);

    }

    @Test
    public void testTopSender() {
        TransactionDataFetcher td = TransactionDataFetcher.getInstance(transactionManager);
        Optional<String> actual = td.getTopSender();
        Optional<String> expected = Optional.of("Arthur Shelby");
        assertEquals(expected,actual);

    }

}
