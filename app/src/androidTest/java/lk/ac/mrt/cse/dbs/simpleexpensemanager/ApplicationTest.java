/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {

    private ExpenseManager expenseManager;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }


    @Test
    public void testAddAccount() {
        expenseManager.addAccount("190297X", "BOC", "Sathsarani", 2000);
        List<String> accountNumbersList = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbersList.contains("190297X"));
    }


    @Test
    public void testExpenseTransactions() throws InvalidAccountException {
        String accountNo = "190297X";
        String expense = "50.00";

        expenseManager.addAccount(accountNo, "People's", "Sanjana", 5000);

        double currentAmount = expenseManager.getAccountsDAO().getAccount(accountNo).getBalance();
        expenseManager.updateAccountBalance(accountNo, 22, 5, 2022, ExpenseType.EXPENSE, expense);

        double newAmount = expenseManager.getAccountsDAO().getAccount(accountNo).getBalance();

        assertTrue(newAmount == currentAmount - (Double.parseDouble(expense)));

    }


    @Test
    public void testIncomeTransactions() throws InvalidAccountException {
        String accountNo="190297X";
        String income="100.00";
        expenseManager.addAccount(accountNo,"HSBC","Themiya",3000);

        double currentAmount = expenseManager.getAccountsDAO().getAccount(accountNo).getBalance();
        expenseManager.updateAccountBalance(accountNo,23, 5, 2022, ExpenseType.INCOME, income) ;


        double newAmount=expenseManager.getAccountsDAO().getAccount(accountNo).getBalance();
        assertTrue(newAmount == (Double.parseDouble(income)) + currentAmount);
    }

}