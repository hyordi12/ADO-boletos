package com.example.boletosado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "boletos.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_BOLETOS = "boletos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORIGEN = "origen";
    private static final String COLUMN_DESTINO = "destino";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_HORA = "hora";
    private static final String COLUMN_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_BOLETOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORIGEN + " TEXT, " +
                COLUMN_DESTINO + " TEXT, " +
                COLUMN_FECHA + " TEXT, " +
                COLUMN_HORA + " TEXT, " +
                COLUMN_TOTAL + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOLETOS);
        onCreate(db);
    }


    public long insertBoleto(String origen, String destino, String fecha, String hora, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORIGEN, origen);
        values.put(COLUMN_DESTINO, destino);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_HORA, hora);
        values.put(COLUMN_TOTAL, total);


        long newId = getNextId(db);
        values.put(COLUMN_ID, newId);

        return db.insert(TABLE_BOLETOS, null, values);
    }


    private long getNextId(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_BOLETOS, null);
        long nextId = 1;

        if (cursor.moveToFirst()) {
            long maxId = cursor.getLong(0);
            if (!cursor.isNull(0)) {
                nextId = maxId + 1;
            }
        }
        cursor.close();

        return nextId;
    }


    public Cursor getBoletoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_ORIGEN, COLUMN_DESTINO, COLUMN_FECHA, COLUMN_HORA, COLUMN_TOTAL};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.query(TABLE_BOLETOS, columns, selection, selectionArgs, null, null, null);
    }


    public int updateBoleto(int id, String origen, String destino, String fecha, String hora, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORIGEN, origen);
        values.put(COLUMN_DESTINO, destino);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_HORA, hora);
        values.put(COLUMN_TOTAL, total);

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.update(TABLE_BOLETOS, values, selection, selectionArgs);
    }


    public void deleteBoleto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(TABLE_BOLETOS, selection, selectionArgs);
    }
}
