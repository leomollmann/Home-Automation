package com.cognito.homeautomation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cognito.homeautomation.Holders.IDHolder;

import java.util.ArrayList;

public class TOutletDAO {

    // os atributos para gerenciar os dados do banco de dados
    private SQLiteDatabase database;

    // criar constantes da tabela clientes
    public final static String COLUMN_NAME_ID = "DeviceID";
    public final static String SQL_ROW_NAME = "Name";
    public final static String SQL_ROW_STATE0 = "State0";
    public final static String SQL_ROW_STATE1 = "State1";
    public final static String SQL_ROW_STATE2 = "State2";
    public final static String SQL_ROW_TEMPERATURE = "Temperature";
    public final static String SQL_ROW_HUMIDITY = "Humidity";
    public final static String SQL_ROW_ENERGY = "Energy";
    public final static String SQL_ROW_LOCALE = "Locale";
    public final static String SQL_ROW_PLUGNAME1 = "Plug1";
    public final static String SQL_ROW_PLUGNAME2 = "Plug2";
    public final static String SQL_ROW_PLUGNAME3 = "Plug3";
    public final static String SQL_ROW_USERID = "userID";
    public final static String TABLE_NAME = "TOutlet";

    public TOutletDAO(Context context) {
        //helper = DatabaseHelper.getDatabaseHelper(context);
        database = DatabaseHelper.getDatabase(context);
}

    private ContentValues putValues(TOutlet c){
        ContentValues v = new ContentValues();
        v.put(COLUMN_NAME_ID,c.getDeviceID());
        v.put(SQL_ROW_NAME,c.getName());
        v.put(SQL_ROW_STATE0,c.isState0());
        v.put(SQL_ROW_STATE1,c.isState1());
        v.put(SQL_ROW_STATE2,c.isState2());
        v.put(SQL_ROW_TEMPERATURE,c.getTemperature());
        v.put(SQL_ROW_HUMIDITY,c.getHumidity());
        v.put(SQL_ROW_ENERGY,c.getEnergy());
        v.put(SQL_ROW_LOCALE,c.getLocale());
        v.put(SQL_ROW_PLUGNAME1,c.getPlug1());
        v.put(SQL_ROW_PLUGNAME2,c.getPlug2());
        v.put(SQL_ROW_PLUGNAME3,c.getPlug3());
        v.put(SQL_ROW_USERID,c.getUserID());
        return v;
    }

    public void insert(TOutlet c) {
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


    public TOutlet get(int DeviceID) {
        TOutlet c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
            SQL_ROW_NAME,
            SQL_ROW_STATE0,
            SQL_ROW_STATE1,
            SQL_ROW_STATE2,
            SQL_ROW_TEMPERATURE,
            SQL_ROW_HUMIDITY,
            SQL_ROW_ENERGY,
            SQL_ROW_LOCALE,
            SQL_ROW_PLUGNAME1,
            SQL_ROW_PLUGNAME2,
            SQL_ROW_PLUGNAME3
        };
        // especificar a parte WHERE da consulta
        String where = COLUMN_NAME_ID + " = ? ";
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
        if (resposta.moveToNext()) {
            // instancia o cliente
            c = new TOutlet();
            // adiciona os valores no objeto
            c.setDeviceID(DeviceID);
            c.setName(resposta.getString(0));
            c.setState0(resposta.getInt(1) == 1);
            c.setState1(resposta.getInt(2) == 1);
            c.setState2(resposta.getInt(3) == 1);
            c.setTemperature(resposta.getFloat(4));
            c.setHumidity(resposta.getFloat(5));
            c.setEnergy(resposta.getFloat(6));
            c.setLocale(resposta.getString(7));
            c.setPlug1(resposta.getString(8));
            c.setPlug2(resposta.getString(9));
            c.setPlug3(resposta.getString(10));
        }
        return c;
    }

    public ArrayList<TOutlet> getAll() {
        int UserID = IDHolder.getHolder().ID;
        // inicializa variáveis
        String where = SQL_ROW_USERID + " = " + UserID;
        ArrayList<TOutlet> all = new ArrayList<TOutlet>();
        TOutlet c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
                COLUMN_NAME_ID,
                SQL_ROW_NAME,
                SQL_ROW_STATE0,
                SQL_ROW_STATE1,
                SQL_ROW_STATE2,
                SQL_ROW_TEMPERATURE,
                SQL_ROW_HUMIDITY,
                SQL_ROW_ENERGY,
                SQL_ROW_LOCALE,
                SQL_ROW_PLUGNAME1,
                SQL_ROW_PLUGNAME2,
                SQL_ROW_PLUGNAME3
        };
        // realizar a consulta
        Cursor resposta = database.query(
                TABLE_NAME,     // 1 - nome da tabela
                colunasParaSelecionar, // 2 - colunas selecionadas
                where,          // 3 - parte WHERE do SQL
                null,   // 4 - valores do WHERE
                null,           // 5 - parte GROUP BY do SQL
                null,           // 6 - valores do GROUP BY
                null,           // 7 - parte ORDER BY do SQL
                null            // 8 - parte LIMIT do SQL
        );
        // verificar se tem um resultado
        while (resposta.moveToNext()) {
            // instancia o cliente
            c = new TOutlet();
            // adiciona os valores no objeto
            c.setDeviceID(resposta.getInt(0));
            c.setName(resposta.getString(1));
            c.setState0(resposta.getInt(2) == 1);
            c.setState1(resposta.getInt(3) == 1);
            c.setState2(resposta.getInt(4) == 1);
            c.setTemperature(resposta.getFloat(5));
            c.setHumidity(resposta.getFloat(6));
            c.setEnergy(resposta.getFloat(7));
            c.setLocale(resposta.getString(8));
            c.setPlug1(resposta.getString(9));
            c.setPlug2(resposta.getString(10));
            c.setPlug3(resposta.getString(11));

            //adiciona o objeto na lista
            all.add(c);
        }
        return all;
    }

    public void updateStates(boolean state1,boolean state2,boolean state3, int id){
        updateState1(state1,id);
        updateState2(state2,id);
        updateState3(state3,id);
    }

    public void updateState1(boolean state, int id){
        ContentValues cv = new ContentValues();
        cv.put(SQL_ROW_STATE0,state);
        database.update(TABLE_NAME, cv, "DeviceID ="+id, null);
    }

    public void updateState2(boolean state, int id){
        ContentValues cv = new ContentValues();
        cv.put(SQL_ROW_STATE1,state);
        database.update(TABLE_NAME, cv, "DeviceID ="+id, null);
    }

    public void updateState3(boolean state, int id){
        ContentValues cv = new ContentValues();
        cv.put(SQL_ROW_STATE2,state);
        database.update(TABLE_NAME, cv, "DeviceID ="+id, null);
    }

    public void updateSensors(float temperature, float humidity, float energy, int id){
        ContentValues cv = new ContentValues();
        cv.put(SQL_ROW_TEMPERATURE,temperature);
        cv.put(SQL_ROW_HUMIDITY,humidity);
        cv.put(SQL_ROW_ENERGY,energy);
        database.update(TABLE_NAME, cv, "DeviceID ="+id, null);
    }

    public void updateNames(String[] names, int id){
        ContentValues cv = new ContentValues();
        cv.put(SQL_ROW_NAME,names[0]);
        cv.put(SQL_ROW_LOCALE,names[1]);
        cv.put(SQL_ROW_PLUGNAME1,names[2]);
        cv.put(SQL_ROW_PLUGNAME2,names[3]);
        cv.put(SQL_ROW_PLUGNAME3,names[4]);
        database.update(TABLE_NAME, cv, "DeviceID ="+id, null);
    }
}
