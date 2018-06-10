package com.devmasterteam.meusconvidados.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devmasterteam.meusconvidados.constants.DatabaseConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private static GuestRepository INSTANCE;
    private GuestDatabaseHelper mGuestDatabaseHelper;

    private GuestRepository(Context context) {
        this.mGuestDatabaseHelper = new GuestDatabaseHelper(context);
    }

    public static synchronized GuestRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GuestRepository(context);
        }
        return INSTANCE;
    }

    public Boolean insert(GuestEntity guestEntity) {
        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guestEntity.getName());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());

            sqLiteDatabase.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues);

            return true;

        }
        catch (Exception ex) {
            return false;
        }
    }

    public List<GuestEntity> getGuestsByQuery(String query) {

        List<GuestEntity> list = new ArrayList<>();

        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    GuestEntity guestEntity = new GuestEntity();
                    guestEntity.setId(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)));
                    guestEntity.setName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)));
                    guestEntity.setConfirmed(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)));
                    list.add(guestEntity);
                }
            }

            if (cursor != null) {
                cursor.close();
            }

        }
        catch (Exception ex) {

        }
        finally {
            return list;
        }

    }

    public GuestEntity load(int id) {
        GuestEntity guestEntity = new GuestEntity();

        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getReadableDatabase();

            String[] projection = {
                    DatabaseConstants.GUEST.COLUMNS.ID,
                    DatabaseConstants.GUEST.COLUMNS.NAME,
                    DatabaseConstants.GUEST.COLUMNS.PRESENCE
            };

            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = sqLiteDatabase.query(
                    DatabaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                guestEntity.setId(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)));
                guestEntity.setName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)));
                guestEntity.setConfirmed(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)));

            }

            if (cursor != null) {
                cursor.close();
            }

        }
        catch (Exception ex) {
        }
        finally {
            return guestEntity;
        }
    }

    public boolean update(GuestEntity guestEntity) {
        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guestEntity.getName());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());

            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(guestEntity.getId())};

            sqLiteDatabase.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, selectionArgs);

            return true;

        }
        catch (Exception ex) {
            return false;
        }
    }
}
