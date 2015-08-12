package com.uigv.atencionmovistar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.uigv.municipalidadlima.R;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TextView;

public class ActividadFinal extends Activity {
 ArrayList<String[]> vehiculos=new ArrayList<String[]>();
 ArrayList<String> modo=new ArrayList<String>();
 TableLayout tabla;
 int[] etiqs={R.id.lplaca,R.id.lmodo,R.id.lclase,R.id.lfabricacion,R.id.lmarca,R.id.lserie,R.id.lcapacidad};
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Movilidad y Transporte");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_actividad_final);
		tabla= (TableLayout) findViewById(R.id.tabla);
		tabla.setVisibility(View.INVISIBLE);
		
		cargar_vehiculos();
		for (String[] valor1 : vehiculos) {
			modo.add(valor1[2]);
		}
		
		ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,modo);
		AutoCompleteTextView autoc1=(AutoCompleteTextView) findViewById(R.id.autoc1);
		autoc1.setAdapter(adaptador);
		autoc1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int posicion,
					long id) {
				// TODO Auto-generated method stub
				tabla.setVisibility(View.VISIBLE);
				TextView modo= (TextView) v;
				String xmodo= modo.getText().toString();
				mostrar(xmodo);
				
			}

			
		});
}

	protected void mostrar(String xmodo) {
		// TODO Auto-generated method stub
		
		int count =0;
		for (String modo1 : modo) {
			if(modo1.equals(xmodo)){
				String[] zmodo= vehiculos.get(count);
				for (int i = 0; i < zmodo.length; i++) {
					TextView temporal= (TextView) findViewById(etiqs[i]);
					temporal.setText(zmodo[i]);
				}
				break;
			}
			count++;
		}
		
	}



	private void cargar_vehiculos() {
		// TODO Auto-generated method stub
		String linea;
		try {
			InputStream entrada=getAssets().open("vehiculos.txt");
			InputStreamReader ingreso=new InputStreamReader(entrada);
			BufferedReader contenido=new BufferedReader(ingreso);
			linea=contenido.readLine();
			while(linea!=null && linea.length()>0){
				StringTokenizer corte=new StringTokenizer(linea,",");
				String[] valor=new String[7];
				for (int i = 0; i < valor.length; i++) {
					valor[i]= corte.nextToken();
				}
				vehiculos.add(valor);
				linea=contenido.readLine();
			}
			contenido.close();
			ingreso.close();
			entrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_final, menu);
		return true;
	}
	
	public void regresar (View v){
		finish();
		
	}

}
