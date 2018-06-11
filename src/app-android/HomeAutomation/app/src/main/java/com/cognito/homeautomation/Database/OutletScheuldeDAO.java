package com.cognito.homeautomation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class OutletScheuldeDAO {

    // os atributos para gerenciar os dados do banco de dados
    private SQLiteDatabase database;

    // criar constantes da tabela clientes
    public final static String COLUMN_NAME_ID = "id";
    public final static String SQL_ROW_TYPE = "type";
    public final static String SQL_ROW_DEVICEID = "deviceID";
    public final static String SQL_ROW_RELAYS = "relays";
    public final static String SQL_ROW_STATE = "State";
    public final static String SQL_ROW_SECONDS = "Seconds";
    public final static String SQL_ROW_SMILLIS = "SMillis";
    public final static String SQL_ROW_INTERVAL = "Interval";
    public final static String SQL_ROW_IMILLIS = "IMillis";
    public final static String SQL_ROW_FREQUENCY = "Frequency";
    public final static String SQL_ROW_FMILLIS = "FMillis";
    public final static String SQL_ROW_REPEAT = "Repeat";
    public static String TABLE_NAME = "OutletScheulde";

    public OutletScheuldeDAO(Context context) {
        //helper = DatabaseHelper.getDatabaseHelper(context);
        database = DatabaseHelper.getDatabase(context);
    }

    private ContentValues putValues(OutletScheulde c){
        ContentValues values = new ContentValues();
        values.put(SQL_ROW_TYPE, c.type);
        values.put(SQL_ROW_DEVICEID, c.deviceID);
        values.put(SQL_ROW_RELAYS, c.relays);
        values.put(SQL_ROW_STATE, c.State);
        values.put(SQL_ROW_SECONDS, c.Seconds);
        values.put(SQL_ROW_SMILLIS, c.SMillis);
        values.put(SQL_ROW_INTERVAL, c.Interval);
        values.put(SQL_ROW_IMILLIS, c.IMillis);
        values.put(SQL_ROW_FREQUENCY, c.Frequency);
        values.put(SQL_ROW_FMILLIS, c.FMillis);
        values.put(SQL_ROW_REPEAT, c.Repeat);
        return values;
    }

    public void inserir(OutletScheulde c) {
        database.insert(
                TABLE_NAME,   // 1 - nome do banco de dados
                null,         // 2 - valor a ser adicionado nas colunas que
                //     não estão no objeto values
                putValues(c)   // 3 - mapa com conteúdos a serem inseridos
        );
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


    public OutletScheulde get(int id) {
        OutletScheulde c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
            SQL_ROW_TYPE,
            SQL_ROW_DEVICEID,
            SQL_ROW_RELAYS,
            SQL_ROW_STATE,
            SQL_ROW_SECONDS,
            SQL_ROW_SMILLIS,
            SQL_ROW_INTERVAL,
            SQL_ROW_IMILLIS,
            SQL_ROW_FREQUENCY,
            SQL_ROW_FMILLIS,
            SQL_ROW_REPEAT,
        };
        // especificar a parte WHERE da consulta
        String where = COLUMN_NAME_ID + " = ? ";
        // criar um array dos valores de WHERE
        String valoresWhere[] = {String.valueOf(id)};
        // realizar a consulta
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
        // verificar se tem um resultado
        if (resposta.moveToNext()) {
            // instancia o cliente
            c = new OutletScheulde();
            // adiciona os valores no objeto
            c.id = id;
            c.type = resposta.getInt(0);
            c.deviceID = resposta.getInt(1);
            c.relays = resposta.getInt(2);
            c.State = Boolean.valueOf(resposta.getString(3));
            c.Seconds = resposta.getInt(4);
            c.SMillis = resposta.getInt(5);
            c.Interval= resposta.getInt(6);
            c.IMillis = resposta.getInt(7);
            c.Frequency = resposta.getInt(8);
            c.FMillis = resposta.getInt(9);
            c.Repeat = resposta.getInt(10);
        }
        return c;
    }

    public ArrayList<OutletScheulde> getByDeviceID(int DeviceID) {
        ArrayList<OutletScheulde> all = new ArrayList<OutletScheulde>();
        OutletScheulde c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
                COLUMN_NAME_ID,
                SQL_ROW_TYPE,
                SQL_ROW_RELAYS,
                SQL_ROW_STATE,
                SQL_ROW_SECONDS,
                SQL_ROW_SMILLIS,
                SQL_ROW_INTERVAL,
                SQL_ROW_IMILLIS,
                SQL_ROW_FREQUENCY,
                SQL_ROW_FMILLIS,
                SQL_ROW_REPEAT,
        };
        // especificar a parte WHERE da consulta
        String where = DeviceID + " = ? ";
        // criar um array dos valores de WHERE
        String valoresWhere[] = {String.valueOf(DeviceID)};
        // realizar a consulta
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
        // verificar se tem um resultado
        while (resposta.moveToNext()) {
            // instancia o cliente
            c = new OutletScheulde();
            // adiciona os valores no objeto
            c.id = resposta.getInt(0);;
            c.type = resposta.getInt(1);
            c.deviceID = DeviceID;
            c.relays = resposta.getInt(2);
            c.State = Boolean.valueOf(resposta.getString(3));
            c.Seconds = resposta.getInt(4);
            c.SMillis = resposta.getInt(5);
            c.Interval= resposta.getInt(6);
            c.IMillis = resposta.getInt(7);
            c.Frequency = resposta.getInt(8);
            c.FMillis = resposta.getInt(9);
            c.Repeat = resposta.getInt(10);

            all.add(c);
        }
        return all;
    }

    public ArrayList<OutletScheulde> getAll() {
        // inicializa variáveis
        ArrayList<OutletScheulde> all = new ArrayList<OutletScheulde>();
        OutletScheulde c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
                COLUMN_NAME_ID,
                SQL_ROW_TYPE,
                SQL_ROW_DEVICEID,
                SQL_ROW_RELAYS,
                SQL_ROW_STATE,
                SQL_ROW_SECONDS,
                SQL_ROW_SMILLIS,
                SQL_ROW_INTERVAL,
                SQL_ROW_IMILLIS,
                SQL_ROW_FREQUENCY,
                SQL_ROW_FMILLIS,
                SQL_ROW_REPEAT,
        };
        // realizar a consulta
        Cursor resposta = database.query(
                TABLE_NAME,     // 1 - nome da tabela
                colunasParaSelecionar, // 2 - colunas selecionadas
                null,          // 3 - parte WHERE do SQL
                null,   // 4 - valores do WHERE
                null,           // 5 - parte GROUP BY do SQL
                null,           // 6 - valores do GROUP BY
                null,           // 7 - parte ORDER BY do SQL
                null            // 8 - parte LIMIT do SQL
        );
        // verificar se tem um resultado
        while(resposta.moveToNext()) {
            // instancia o cliente
            c = new OutletScheulde();
            // adiciona os valores no objeto
            c.id = resposta.getInt(0);
            c.type = resposta.getInt(1);
            c.deviceID = resposta.getInt(2);
            c.relays = resposta.getInt(3);
            c.State = Boolean.valueOf(resposta.getString(4));
            c.Seconds = resposta.getInt(5);
            c.SMillis = resposta.getInt(6);
            c.Interval= resposta.getInt(7);
            c.IMillis = resposta.getInt(8);
            c.Frequency = resposta.getInt(9);
            c.FMillis = resposta.getInt(10);
            c.Repeat = resposta.getInt(11);
            //adiciona o objeto na lista
            all.add(c);
        }
        return all;
    }
}
