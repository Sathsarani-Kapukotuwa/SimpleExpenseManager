package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DataBaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends DataBaseHelper implements TransactionDAO {

    public PersistentTransactionDAO(@Nullable Context context) {
        super(context);
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TRANSACTION_DATE, String.valueOf(date));
        cv.put(ACCOUNT_ID, accountNo);
        cv.put(TRANSACTION_AMOUNT, amount);
        cv.put(EXPENSE_TYPE, String.valueOf(expenseType));

        db.insert(TRANSACTION_TABLE, null, cv);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getAllTransactionLogs() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionLogs = new ArrayList<>();

        String queryString = "SELECT * FROM " + TRANSACTION_TABLE + ";" ;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String date1 = cursor.getString(1);
                String account_ID = cursor.getString(2);
                Double amount = cursor.getDouble(3);
                String type = cursor.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = simpleDateFormat.parse(date1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(date, account_ID, expenseType, amount);
                transactionLogs.add(transaction);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return transactionLogs;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> paginatedTransactionLogs = new ArrayList<>();

        String queryString = "SELECT * FROM " + TRANSACTION_TABLE + " LIMIT " + limit + ";" ;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String date1 = cursor.getString(1);
                String account_ID = cursor.getString(2);
                Double amount = cursor.getDouble(3);
                String type = cursor.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = null;

                try {
                    date = simpleDateFormat.parse(date1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(date, account_ID, expenseType, amount);
                paginatedTransactionLogs.add(transaction);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return paginatedTransactionLogs;
    }


}
