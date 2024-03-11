package com.azkivam.simplesynchronizedbanking.utilities;

import java.io.IOException;

public interface TransactionObserver {

    void onTransaction(String accountNumber, String transactionType, String amount) throws IOException;
    void update(Subject s) throws IOException;
}
