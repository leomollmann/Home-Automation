package com.cognito.homeautomation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

    private SQLiteDatabase database;

    public final static String COLUMN_NAME_ID = "UserID";
    public final static String SQL_ROW_USER = "Username";
    public final static String SQL_ROW_PASSWORD = "Pass";
    public final static String SQL_ROW_PHONE = "Phone";
    public final static String TABLE_NAME = "User";

    public UserDAO(Context context) {
        //helper = DatabaseHelper.getDatabaseHelper(context);
        database = DatabaseHelper.getDatabase(context);
    }

    private ContentValues putValues(String user, String pass, String phone){
        ContentValues v = new ContentValues();
        v.put(SQL_ROW_USER,user);
        v.put(SQL_ROW_PASSWORD,pass);
        v.put(SQL_ROW_PHONE,phone);
        return v;
    }

    public void insert(String user, String pass, String phone) {
        database.insert(
                TABLE_NAME,   // 1 - nome do banco de dados
                null,         // 2 - valor a ser adicionado nas colunas que
                //     não estão no objeto values
                putValues(user,pass,phone)   // 3 - mapa com conteúdos a serem inseridos
        );
    }

    public int check(String user, String pass){
        String where = SQL_ROW_USER +" = ? AND "+ SQL_ROW_PASSWORD + " = ?";
        String[] valoresWhere = {
            user,pass
        };
        String[] colunasParaSelecionar = {
            COLUMN_NAME_ID
        };
        Cursor resposta = database.query(
            TABLE_NAME,     // 1 - nome da tabela
            colunasParaSelecionar, // 2 - colunas selecionadas
            where,          // 3 - parte WHERE do SQL
            valoresWhere,   // 4 - valores do WHERE
            null,           // 5 - parte GROUP BY do SQL
            null,           // 6 - valores do GROUP BY
            null,           // 7 - parte ORDER BY do SQL
            null            // 8 - parte LIMIT do SQL
        );
        if(resposta.moveToNext()){
            return resposta.getInt(0);
        }
        return -1;
    }

    public void excluir(int id) {
        // especificar a parte WHERE da consulta
        String where = COLUMN_NAME_ID + " = ? ";
        // criar um array dos valores de WHERE
        String valoresWhere[] = {String.valueOf(id)};
        // método excluir
        database.delete(
                TABLE_NAME, // 1 - nome da tabela
                where,      // 2 - parte WHERE do SQL
                valoresWhere// 3 - valores da parte WHERE do SQL
        );
    }
}
