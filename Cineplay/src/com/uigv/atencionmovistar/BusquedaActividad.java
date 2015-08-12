package com.uigv.atencionmovistar;

import com.uigv.municipalidadlima.R;

import android.net.Uri;
import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class BusquedaActividad extends ListActivity {
	
	

	String[] datos = { "Directorio vehiculos(2013)", "Ciclovias","Red Vial de Lima",
			"Rutas de Lima", "Transporte Urbano",
			"Directorio Sanciones", "Salir" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Movilidad y Transporte");
		super.onCreate(savedInstanceState);
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, datos);
		setListAdapter(adaptador);
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int posicion, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Opcion escogida:" + datos[posicion],
						Toast.LENGTH_SHORT).show();
				switch (posicion) {
				case 0:  
					Intent r=new Intent(getApplicationContext(), ActividadFinal.class);
                          startActivity(r); 

					break;
				case 1:    
					Intent p=new Intent(getApplicationContext(), ActividadFinal2.class);
                 		  startActivity(p);

					break;
				case 2:  Intent q=new Intent(Intent.ACTION_VIEW,
             		   Uri.parse("http://lima.datosabiertos.pe/datastreams/79500/rutas-de-lima/"));
                startActivity(q);

					break;
				case 3: Intent l=new Intent(Intent.ACTION_VIEW,
             		   Uri.parse("http://lima.datosabiertos.pe/datastreams/79522/directorio-de-vehiculos-2013/"));
                startActivity(l);

					break;
				case 4: Intent m=new Intent(Intent.ACTION_VIEW,
             		   Uri.parse("http://lima.datosabiertos.pe/datastreams/79522/directorio-de-vehiculos-2013/"));
                startActivity(m);

					break;
				case 5:      Intent s=new Intent(Intent.ACTION_VIEW,
             		   Uri.parse("http://lima.datosabiertos.pe/datastreams/79522/directorio-de-vehiculos-2013/"));
                startActivity(s);      

					break;
				case 6:  Intent n=new Intent();
                           n.putExtra("salida", true);
                         setResult(RESULT_OK, n);
                         finish();
                           
					break;
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busqueda_actividad,menu);
		return true;
	}

}
