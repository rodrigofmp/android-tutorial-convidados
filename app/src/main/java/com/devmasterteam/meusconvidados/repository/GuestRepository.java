package com.devmasterteam.meusconvidados.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devmasterteam.meusconvidados.constants.DatabaseConstants;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestCount;
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
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.DOCUMENT, guestEntity.getDocument());

            sqLiteDatabase.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues);

            return true;

        } catch (Exception ex) {
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
                    guestEntity.setDocument(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.DOCUMENT)));

                    list.add(guestEntity);
                }
            }

            if (cursor != null) {
                cursor.close();
            }

        } catch (Exception ex) {

        } finally {
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
                    DatabaseConstants.GUEST.COLUMNS.PRESENCE,
                    DatabaseConstants.GUEST.COLUMNS.DOCUMENT
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
                guestEntity.setDocument(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.DOCUMENT)));

            }

            if (cursor != null) {
                cursor.close();
            }

        } catch (Exception ex) {
        } finally {
            return guestEntity;
        }
    }

    public boolean update(GuestEntity guestEntity) {
        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guestEntity.getName());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.DOCUMENT, guestEntity.getDocument());

            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(guestEntity.getId())};

            sqLiteDatabase.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, selectionArgs);

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public boolean remove(int id) {
        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getWritableDatabase();

            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            sqLiteDatabase.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, selectionArgs);

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public GuestCount loadDashboard() {

        GuestCount guestCount = new GuestCount(0, 0, 0);

        Cursor cursor = null;

        try {

            SQLiteDatabase sqLiteDatabase = this.mGuestDatabaseHelper.getReadableDatabase();

            // PRESENCE

            String sqlPresence =
                    "select count(*) " +
                            "  from " + DatabaseConstants.GUEST.TABLE_NAME +
                            " where " + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstants.CONFIRMATION.PRESENT;
            cursor = sqLiteDatabase.rawQuery(sqlPresence, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                guestCount.setPresentCount(cursor.getInt(0));
            }

            // ABSENT

            String sqlAbsent =
                    "select count(*) " +
                            "  from " + DatabaseConstants.GUEST.TABLE_NAME +
                            " where " + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstants.CONFIRMATION.ABSENT;
            cursor = sqLiteDatabase.rawQuery(sqlAbsent, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                guestCount.setAbsentCount(cursor.getInt(0));
            }

            // ALL

            String sqlAll =
                    "select count(*) " +
                            "  from " + DatabaseConstants.GUEST.TABLE_NAME;
            cursor = sqLiteDatabase.rawQuery(sqlAll, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                guestCount.setAllInvitedCount(cursor.getInt(0));
            }

        } catch (Exception ex) {

        } finally {

            if (cursor != null) {
                cursor.close();
            }

            return guestCount;
        }

    }
}
