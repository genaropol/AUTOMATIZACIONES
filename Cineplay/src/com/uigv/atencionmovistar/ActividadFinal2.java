package com.uigv.atencionmovistar;



import com.uigv.municipalidadlima.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;


public class ActividadFinal2 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_actividad_final2);
		setTitle("Movilidad y Transporte");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_final2, menu);
		return true;
	}
     public void regresar(View v){
    	 finish();
    	 
     }
}
