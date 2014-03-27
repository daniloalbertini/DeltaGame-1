package com.example.gameteste;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity1 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}

	public void startGame(View view) {
		
		MediaPlayer  inicio = MediaPlayer.create(this, R.raw.start);
		inicio.start();
		
		Intent start = new Intent(MainActivity1.this, Level1.class);
		startActivity(start);		
		
	}
	
}
