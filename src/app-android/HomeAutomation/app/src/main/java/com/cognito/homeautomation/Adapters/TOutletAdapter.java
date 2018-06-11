package com.cognito.homeautomation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cognito.homeautomation.Database.TOutlet;
import com.cognito.homeautomation.Database.TOutletDAO;
import com.cognito.homeautomation.View.OutletSettings;
import com.cognito.homeautomattion.R;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class TOutletAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<TOutlet> lista;
    private Context context;
    private TOutletDAO tOutletDAO;
    private TOutlet tOutlet;
    private ArrayList<Boolean> isPressed1 = new ArrayList<>();
    private ArrayList<Boolean> isPressed2 = new ArrayList<>();
    private ArrayList<Boolean> isPressed3 = new ArrayList<>();
    private ArrayList<ImageButton> Outlet1 = new ArrayList<>();
    private ArrayList<ImageButton> Outlet2 = new ArrayList<>();
    private ArrayList<ImageButton> Outlet3 = new ArrayList<>();

    public TOutletAdapter(Context context, ArrayList<TOutlet> dados) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lista = dados;
        this.context = context;
        tOutletDAO = new TOutletDAO(context);
    }

    @Override
    public int getCount() {
        try {
            return lista.size();
        }catch(NullPointerException e){
            Log.i("TOutletSize","Recebeu lista nula como parametro");
            return 0;
        }
    }

    @Override
    public TOutlet getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.outlet, null);
            tOutlet = tOutletDAO.get(i);

            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            TextView txtLocale = (TextView) view.findViewById(R.id.txtLocal);
            TextView txtEnergy = (TextView) view.findViewById(R.id.txtEnergy);
            TextView txtTemp = (TextView) view.findViewById(R.id.txtSetTemp);

            txtName.setText(tOutlet.getName());
            txtLocale.setText(tOutlet.getLocale());
            txtTemp.setText(String.valueOf(tOutlet.getTemperature()) + "C°");
            txtEnergy.setText(String.valueOf(tOutlet.getEnergy()) + "W");

            isPressed1.add(tOutlet.isState0());
            isPressed2.add(tOutlet.isState1());
            isPressed3.add(tOutlet.isState2());

            Outlet1.add((ImageButton) view.findViewById(R.id.imgbPlug1));
            Outlet2.add((ImageButton) view.findViewById(R.id.imgbPlug2));
            Outlet3.add((ImageButton) view.findViewById(R.id.imgbPlug3));
            ImageButton Settings = (ImageButton) view.findViewById(R.id.btnSettings);

            Outlet1.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tOutletDAO.updateState1(isPressed1.get(i),i);
                    if (isPressed1.get(i))
                        Outlet1.get(i).setImageResource(R.drawable.ic_on_35);
                    else
                        Outlet1.get(i).setImageResource(R.drawable.ic_off_35);
                    isPressed1.set(i,!isPressed1.get(i));
                }
            });
            Outlet2.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tOutletDAO.updateState2(isPressed2.get(i),i);
                    if (isPressed2.get(i))
                        Outlet2.get(i).setImageResource(R.drawable.ic_on_35);
                    else
                        Outlet2.get(i).setImageResource(R.drawable.ic_off_35);
                    isPressed2.set(i,!isPressed2.get(i));
                }
            });
            Outlet3.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tOutletDAO.updateState3(isPressed3.get(i),i);
                    if (isPressed3.get(i))
                        Outlet3.get(i).setImageResource(R.drawable.ic_on_35);
                    else
                        Outlet3.get(i).setImageResource(R.drawable.ic_off_35);
                    isPressed3.set(i,!isPressed3.get(i));
                }
            });

            Settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("DeviceID", tOutletDAO.get(i).getDeviceID());
                    Intent i = new Intent(context, OutletSettings.class);
                    i.putExtras(b);
                    startActivity(context,i,b);
                }
            });

            if (isPressed1.get(i))
                Outlet1.get(i).setImageResource(R.drawable.ic_on_35);
            else
                Outlet1.get(i).setImageResource(R.drawable.ic_off_35);
            isPressed1.set(i,!isPressed1.get(i));

            if (isPressed2.get(i))
                Outlet2.get(i).setImageResource(R.drawable.ic_on_35);
            else
                Outlet2.get(i).setImageResource(R.drawable.ic_off_35);
            isPressed2.set(i,!isPressed2.get(i));

            if (isPressed3.get(i))
                Outlet3.get(i).setImageResource(R.drawable.ic_on_35);
            else
                Outlet3.get(i).setImageResource(R.drawable.ic_off_35);
            isPressed3.set(i,!isPressed3.get(i));

        return view;
    }

    public void add(TOutlet registro){
        try{
            this.lista.add(registro);
        }catch(NullPointerException e){
            Log.i("TOutletAdd","Não adicionou");
        }
    }

    public void addAll(ArrayList<TOutlet> todosDados){
        try{
            lista.addAll(todosDados);
        }catch(NullPointerException e){
            Log.i("TOutletAddArray","Não adicionou");
        }
    }

    public void clear(){
        this.lista.clear();
    }
}
