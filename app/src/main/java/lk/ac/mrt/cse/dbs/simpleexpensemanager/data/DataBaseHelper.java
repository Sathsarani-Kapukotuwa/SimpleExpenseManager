package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String ACCOUNT_TABLE = "Account_Table";
    public static final String TRANSACTION_ID = "ID";
    public static final String ACCOUNT_ID = "Account_ID";
    public static final String BANK_NAME = "Bank_Name";
    public static final String ACCOUNT_HOLDER_NAME = "Account_Holder_Name";
    public static final String BALANCE = "Balance";

    public static final String TRANSACTION_TABLE = "Transaction_Table";
    public static final String TRANSACTION_DATE = "Date";
    public static final String TRANSACTION_ACCOUNT_ID = "Account_ID";
    public static final String TRANSACTION_AMOUNT = "Amount";
    public static final String EXPENSE_TYPE = "Expense_Type";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "190297X.db", null, 1);
    }


    //create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createAccountTable = "CREATE TABLE " + ACCOUNT_TABLE + "(" + ACCOUNT_ID + " TEXT PRIMARY KEY," + BANK_NAME + " TEXT," + ACCOUNT_HOLDER_NAME + " TEXT," + BALANCE + " REAL )";
        db.execSQL(createAccountTable);

        String createTransactionTable = "CREATE TABLE " + TRANSACTION_TABLE + "(" + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TRANSACTION_DATE + " DATE, " + TRANSACTION_ACCOUNT_ID + " REFERENCES ACCOUNT_TABLE(Account_ID) , " + TRANSACTION_AMOUNT + " REAL, " + EXPENSE_TYPE + " TEXT )";
        db.execSQL(createTransactionTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // method to add a new account to the database
    public boolean addNewAccount(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ACCOUNT_ID, account.getAccountNo());
        cv.put(BANK_NAME, account.getBankName());
        cv.put(ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
        cv.put(BALANCE, account.getBalance());

        long insert = db.insert(ACCOUNT_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


    // method to delete an account from the database
    public boolean deleteAccount(String account_no){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ACCOUNT_ID, account_no);

        long delete = db.delete(ACCOUNT_TABLE, ACCOUNT_ID + "=?", new String[]{account_no});

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }


}
