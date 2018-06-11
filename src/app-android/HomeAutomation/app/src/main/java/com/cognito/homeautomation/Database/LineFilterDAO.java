package com.cognito.homeautomation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class LineFilterDAO {

    // os atributos para gerenciar os dados do banco de dados
    private SQLiteDatabase database;

    // criar constantes da tabela clientes
    public final static String COLUMN_NAME_ID = "DeviceID";
    public final static String SQL_ROW_NAME = "Name";
    public final static String SQL_ROW_STATE0 = "State0";
    public final static String SQL_ROW_STATE1 = "State1";
    public final static String SQL_ROW_STATE2 = "State2";
    public final static String SQL_ROW_STATE3 = "State3";
    public final static String SQL_ROW_STATE4 = "State4";
    public final static String SQL_ROW_STATE5 = "State5";
    public final static String SQL_ROW_STATE6 = "State6";
    public final static String SQL_ROW_STATE7 = "State7";
    public final static String SQL_ROW_TEMPERATURE = "Temperature";
    public final static String SQL_ROW_HUMIDITY = "Humidity";
    public final static String SQL_ROW_ENERGY = "Energy";
    public final static String SQL_ROW_LOCALE = "Locale";
    public static String TABLE_NAME = "LineFilter";

    public LineFilterDAO(Context context) {
        //helper = DatabaseHelper.getDatabaseHelper(context);
        database = DatabaseHelper.getDatabase(context);
    }

    private ContentValues putValues(LineFilter c) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, c.getDeviceID());
        values.put(SQL_ROW_NAME, c.getName());
        values.put(SQL_ROW_STATE0, c.isState0());
        values.put(SQL_ROW_STATE1, c.isState1());
        values.put(SQL_ROW_STATE2, c.isState2());
        values.put(SQL_ROW_STATE3, c.isState3());
        values.put(SQL_ROW_STATE4, c.isState4());
        values.put(SQL_ROW_STATE5, c.isState5());
        values.put(SQL_ROW_STATE6, c.isState6());
        values.put(SQL_ROW_STATE7, c.isState7());
        values.put(SQL_ROW_TEMPERATURE, c.getTemperature());
        values.put(SQL_ROW_HUMIDITY, c.getHumidity());
        values.put(SQL_ROW_ENERGY, c.getEnergy());
        values.put(SQL_ROW_LOCALE, c.getLocale());
        return values;
    }

    public void inserir(LineFilter c) {
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


    public LineFilter get(int DeviceID) {
        LineFilter c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
            SQL_ROW_NAME,
            SQL_ROW_STATE0,
            SQL_ROW_STATE1,
            SQL_ROW_STATE2,
            SQL_ROW_STATE3,
            SQL_ROW_STATE4,
            SQL_ROW_STATE5,
            SQL_ROW_STATE6,
            SQL_ROW_STATE7,
            SQL_ROW_TEMPERATURE,
            SQL_ROW_HUMIDITY,
            SQL_ROW_ENERGY,
            SQL_ROW_LOCALE
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
            c = new LineFilter();
            // adiciona os valores no objeto
            c.setDeviceID(DeviceID);
            c.setName(resposta.getString(0));
            c.setState0(Boolean.valueOf(resposta.getString(1)));
            c.setState1(Boolean.valueOf(resposta.getString(2)));
            c.setState2(Boolean.valueOf(resposta.getString(3)));
            c.setState3(Boolean.valueOf(resposta.getString(4)));
            c.setState4(Boolean.valueOf(resposta.getString(5)));
            c.setState5(Boolean.valueOf(resposta.getString(6)));
            c.setState6(Boolean.valueOf(resposta.getString(7)));
            c.setState7(Boolean.valueOf(resposta.getString(8)));
            c.setTemperature(resposta.getFloat(9));
            c.setHumidity(resposta.getFloat(10));
            c.setEnergy(resposta.getFloat(11));
            c.setLocale(resposta.getString(12));
        }
        return c;
    }

    public ArrayList<LineFilter> getAll() {
        // inicializa variáveis
        ArrayList<LineFilter> all = new ArrayList<LineFilter>();
        LineFilter c = null;
        // especificar colunas a serem selecionadas
        String colunasParaSelecionar[] = {
                COLUMN_NAME_ID,
                SQL_ROW_NAME,
                SQL_ROW_STATE0,
                SQL_ROW_STATE1,
                SQL_ROW_STATE2,
                SQL_ROW_STATE3,
                SQL_ROW_STATE4,
                SQL_ROW_STATE5,
                SQL_ROW_STATE6,
                SQL_ROW_STATE7,
                SQL_ROW_TEMPERATURE,
                SQL_ROW_HUMIDITY,
                SQL_ROW_ENERGY,
                SQL_ROW_LOCALE
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
        while (resposta.moveToNext()) {
            // instancia o cliente
            c = new LineFilter();
            // adiciona os valores no objeto
            c.setDeviceID(resposta.getInt(0));
            c.setName(resposta.getString(1));
            c.setState0(Boolean.valueOf(resposta.getString(2)));
            c.setState1(Boolean.valueOf(resposta.getString(3)));
            c.setState2(Boolean.valueOf(resposta.getString(4)));
            c.setState3(Boolean.valueOf(resposta.getString(5)));
            c.setState4(Boolean.valueOf(resposta.getString(6)));
            c.setState5(Boolean.valueOf(resposta.getString(7)));
            c.setState6(Boolean.valueOf(resposta.getString(8)));
            c.setState7(Boolean.valueOf(resposta.getString(9)));
            c.setTemperature(resposta.getFloat(10));
            c.setHumidity(resposta.getFloat(11));
            c.setEnergy(resposta.getFloat(12));
            c.setLocale(resposta.getString(13));
            //adiciona o objeto na lista
            all.add(c);
        }
        return all;
    }
}
