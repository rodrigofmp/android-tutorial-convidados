package com.devmasterteam.meusconvidados.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.devmasterteam.meusconvidados.constants.DatabaseConstants;

public class GuestDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MeusConvidados.db";

    private static final String SQL_CREATE_TABLE_GUEST =
            "create table " + DatabaseConstants.GUEST.TABLE_NAME + " ("
            + DatabaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, "
            + DatabaseConstants.GUEST.COLUMNS.NAME + " text, "
            + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " integer, "
            + DatabaseConstants.GUEST.COLUMNS.DOCUMENT + " text null);";

    private static final String DROP_TABLE_GUEST =
            "drop table if exists "
                    + DatabaseConstants.GUEST.TABLE_NAME;

    public GuestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_GUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_GUEST);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_GUEST);
    }
}
