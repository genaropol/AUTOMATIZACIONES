package com.uigv.atencionmovistar;

import com.uigv.municipalidadlima.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Inicio_Actividad extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_inicio__actividad);
	}

	public void iniciar(View v) {
		Intent k = new Intent(this, BusquedaActividad.class);
		startActivity(k);
		Toast.makeText(this, "Bienvenido al Sistema Movilidad y Transporte",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio__actividad, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getOrder()) {
		case 1:  
			Toast.makeText(this, "Finaliza el programa",Toast.LENGTH_LONG).show();
				 finish();
				 break;
		case 2:

			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int quien, int porque, Intent datos) {
		// TODO Auto-generated method stub

		// if(quien== MENU){
		// if(porque == RESULT_OK){
		// boolean final1= datos.getBooleanExtra("salida", false);
		// if(final1){
		// Toast.makeText(this, "Finaliza el programa",Toast.LENGTH_LONG
		// ).show();
		// finish();
		// }
		// }
		// }
	}

}
