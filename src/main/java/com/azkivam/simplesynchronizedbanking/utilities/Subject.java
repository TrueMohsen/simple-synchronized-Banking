package com.azkivam.simplesynchronizedbanking.utilities;

import java.io.IOException;
import java.util.Map;

public interface Subject {
    void attach(TransactionObserver observer);
    void detach(TransactionObserver observer);
    void Notify() throws IOException;

    Map<Material,String> getState();
    void setState(String accountNumber, String transactionType, String amount);

    Subject returnSelf();



}
