package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentExpenseManager extends ExpenseManager {

    private Context context;

    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        setup();
    }


    @Override
    public void setup() throws ExpenseManagerException {
        AccountDAO accountDAO = new PersistentAccountDAO(context);
        TransactionDAO transactionDAO = new PersistentTransactionDAO(context);

        setAccountsDAO(accountDAO);
        setTransactionsDAO(transactionDAO);
    }


}
