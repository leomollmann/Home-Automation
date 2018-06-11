package com.cognito.homeautomation.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static SQLiteDatabase database = null;
    private static DatabaseHelper helper = null;

    // criar constantes para conexão com o banco de dados
    public static final String NOME_BANCO_DADOS = "HomeAuto(indef).db";
    public static final int VERSAO = 3;
    public static final String SQL_CRIAR_OutletScheulde =

            "CREATE TABLE IF NOT EXISTS OutletScheulde(\n" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "deviceID INTEGER NOT NULL,\n" +
            "relays INTEGER NOT NULL,\n" +
            "State BOOLEAN NOT NULL,\n" +
            "Seconds INTEGER NOT NULL,\n" +
            "SMillis INTEGER,\n" +
            "Interval INTEGER,\n" +
            "IMillis INTEGER,\n" +
            "Frequency INTEGER,\n" +
            "FMillis INTEGER,\n" +
            "Repeat INTEGER\n" +
            ")";

    public static final String SQL_CRIAR_FirstLogin =

            "CREATE TABLE IF NOT EXISTS FirstLogin(\n" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "FirstTime BOOLEAN NOT NULL\n" +
            ")";

    public static final String SQL_CRIAR_User =

            "CREATE TABLE IF NOT EXISTS User(\n" +
            "UserID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "Username VARCHAR NOT NULL,\n" +
            "Pass VARCHAR NOT NULL,\n" +
            "Phone VARCHAR NOT NULL\n" +
            ")";


    public static final String SQL_CRIAR_LineFilter =

            "CREATE TABLE IF NOT EXISTS Linefilter(\n" +
            "DeviceID INTEGER NOT NULL PRIMARY KEY,\n" +
            "Name VARCHAR NOT NULL,\n" +
            "State0 BOOLEAN NOT NULL,\n" +
            "State1 BOOLEAN NOT NULL,\n" +
            "State2 BOOLEAN NOT NULL,\n" +
            "State3 BOOLEAN NOT NULL,\n" +
            "State4 BOOLEAN NOT NULL,\n" +
            "State5 BOOLEAN NOT NULL,\n" +
            "State6 BOOLEAN NOT NULL,\n" +
            "State7 BOOLEAN NOT NULL,\n" +
            "Temperature FLOAT NOT NULL,\n" +
            "Humidity FLOAT NOT NULL,\n" +
            "Energy FLOAT NOT NULL,\n" +
            "Locale VARCHAR NOT NULL\n" +
            ")";

    public static final String SQL_CRIAR_TOutlet =
            "CREATE TABLE IF NOT EXISTS TOutlet(\n" +
            "DeviceID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "Name VARCHAR NOT NULL,\n" +
            "State0 BOOLEAN NOT NULL,\n" +
            "State1 BOOLEAN NOT NULL,\n" +
            "State2 BOOLEAN NOT NULL,\n" +
            "Temperature FLOAT NOT NULL,\n" +
            "Humidity FLOAT NOT NULL,\n" +
            "Energy FLOAT NOT NULL,\n" +
            "Locale VARCHAR NOT NULL,\n" +
            "Plug1 VARCHAR NOT NULL,\n" +
            "Plug2 VARCHAR NOT NULL,\n" +
            "Plug3 VARCHAR NOT NULL,\n" +
            "userID int NOT NULL\n" +
            ")";

    public DatabaseHelper(Context context) {
        super(
                context,            // 1 - a activity executada
                NOME_BANCO_DADOS,   // 2 - nome do banco de dados
                null,               // 3 - factory, usar null
                VERSAO              // 4 - versão do banco
        );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CRIAR_OutletScheulde);
        sqLiteDatabase.execSQL(SQL_CRIAR_FirstLogin);
        sqLiteDatabase.execSQL(SQL_CRIAR_User);
        sqLiteDatabase.execSQL(SQL_CRIAR_TOutlet);
        sqLiteDatabase.execSQL(SQL_CRIAR_LineFilter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public static DatabaseHelper getDatabaseHelper(Context context){
        if(helper == null){
            helper = new DatabaseHelper(context);
        }
        return helper;
    }

    public static SQLiteDatabase getDatabase(Context context){
        if(database == null){
            database = getDatabaseHelper(context).getWritableDatabase();

        }
        return database;
    }
}