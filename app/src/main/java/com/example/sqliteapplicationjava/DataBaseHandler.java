package com.example.sqliteapplicationjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "COLUMN_CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "COLUMN_CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public DataBaseHandler(@Nullable Context context) {
        super(context, "customers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModelObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customerModelObj.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModelObj.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModelObj.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOne(CustomerModel customerModel) {
        //Find customerModel in database. if found, delete and return true
        //if not, return false
        SQLiteDatabase db = this.getWritableDatabase();
        String query_DeleteOne = "DELETE FROM "+ CUSTOMER_TABLE + " WHERE " + ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(query_DeleteOne, null);
        if (cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public List<CustomerModel> getFilteredChecked() {
        List<CustomerModel> returnChecked = new ArrayList<>();
        String query_SelectChecked = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE ACTIVE_CUSTOMER=1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query_SelectChecked, null);

        if (cursor.moveToFirst()) {
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true: false;

                CustomerModel newCustomers = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnChecked.add(newCustomers);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnChecked;
    }

    public List<CustomerModel> getAllData() {
        List<CustomerModel> returnList = new ArrayList<>();
        String query_SelectAll = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query_SelectAll, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor result set and create new customer object, put them into returnList
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true: false;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);

            } while (cursor.moveToNext());
        }else {
            //failure. do nothing
        }
        //close cursor and db when done
        cursor.close();
        db.close();
        return returnList;
    }
}
