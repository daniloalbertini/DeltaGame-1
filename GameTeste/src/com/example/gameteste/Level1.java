package com.example.gameteste;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;


public class Level1 extends SimpleBaseGameActivity {
		
		private static final int CAMERA_WIDTH = 800;
		private static final int CAMERA_HEIGHT = 480;
		
		private ITexture bolaTextura;
		private ITexture circuloTextura;
		private ITexture fundo;
		private ITextureRegion bolaTextureRegion;
		private ITextureRegion circuloTexturaRegion;
		private ITextureRegion fundoTextura;	
		Music lv1;
		Sound colisao;
		int pontos = 0;
		
		@Override
		public EngineOptions onCreateEngineOptions() {
			
			final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
			
			final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
					new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
			
			engineOptions.getAudioOptions().setNeedsMusic(true);
			engineOptions.getAudioOptions().setNeedsSound(true);
			
			return engineOptions;
			
		}
		
		@Override
		public void onCreateResources() {
			
			try {
				
				//inicia a musica
				MusicFactory.setAssetBasePath("mfx/");
				this.lv1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "lv1bgsong.mp3");
				this.lv1.setLooping(true);
				this.lv1.setVolume(10);
				lv1.play();
				
				//colisao da bola
				SoundFactory.setAssetBasePath("mfx/");
				this.colisao = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "acerto.mp3");
				this.colisao.setLooping(false);
				this.colisao.setVolume(100);
							
				//fundo
				this.fundo = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
					
					@Override
					public InputStream open() throws IOException {
						// TODO Auto-generated method stub
						return getAssets().open("gfx/bg.jpg");
					}
				});
				
				//bola
				this.bolaTextura = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
					
					@Override
					public InputStream open() throws IOException {
						// TODO Auto-generated method stub
						return getAssets().open("gfx/bola_modelo.png");
					}
				});
				
				//argola
				this.circuloTextura = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
					
					@Override
					public InputStream open() throws IOException {
						// TODO Auto-generated method stub
						return getAssets().open("gfx/argola.png");
					}
				});
				
				this.bolaTextura.load();
				this.bolaTextureRegion = TextureRegionFactory.extractFromTexture(this.bolaTextura);
				
				this.circuloTextura.load();
				this.circuloTexturaRegion = TextureRegionFactory.extractFromTexture(this.circuloTextura);
				
				this.fundo.load();
				this.fundoTextura = TextureRegionFactory.extractFromTexture(this.fundo);
			
			} catch (IOException e ) {
				
			}	
		}
		
		@Override
		protected Scene onCreateScene() {
			
			this.mEngine.registerUpdateHandler(new FPSLogger());
			
			final Scene scene = new Scene();
			
			//Ajusta cordenada do item adicionado a tela
			//Bola
			final float bolaX = (100 - this.bolaTextureRegion.getWidth() / 2);
			final float bolaY = (100 - this.bolaTextureRegion.getHeight() / 2);
			
			//Circulo
			final float circuloX = (500 - this.bolaTextureRegion.getWidth() / 2);
			final float circuloY = (100 - this.bolaTextureRegion.getHeight() / 2);
			
			//Cria a imagem e adiciona a cena
			final Sprite bola = new Sprite(bolaX, bolaY, this.bolaTextureRegion, this.getVertexBufferObjectManager()) {
			
				@Override
				//Adiciona função de movimento do item
				public boolean onAreaTouched(final TouchEvent bolaTouchedEvent, 
						final float bolaTouchedX, final float bolaTouchedY) {
					
					this.setPosition(bolaTouchedEvent.getX() - this.getWidth() / 2, 
							bolaTouchedEvent.getY() - this.getHeight() / 2);
					
					int eventoaction = bolaTouchedEvent.getAction();
					
					float X = bolaTouchedEvent.getX();
					float Y = bolaTouchedEvent.getY();
					
					switch (eventoaction) {
					case TouchEvent.ACTION_DOWN: {
						
						this.setScale(2.0f);
						break;
						
					}
					
					case TouchEvent.ACTION_UP:{						
						
						this.setScale(1.0f);
						break;
						
					}
					
					case TouchEvent.ACTION_MOVE: {
						
						this.setPosition(X - this.getWidth() / 2, Y - this.getHeight() / 2);
						break;
						
						}
					}
					
					return true;
					
				}
			
			};
			final Sprite fundo1 = new Sprite(0, 0, this.fundoTextura, this.getVertexBufferObjectManager());
			final Sprite circulo = new Sprite(circuloX, circuloY, this.circuloTexturaRegion, this.getVertexBufferObjectManager());
			
			//Adiciona a cena os itens
			bola.setSize(70, 70);
			circulo.setSize(70, 70);
			scene.attachChild(fundo1);
			scene.registerTouchArea(bola);
			scene.attachChild(bola);
			scene.attachChild(circulo);
						
			Font fonte;
			final Text ptexto;
			
			fonte = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, 
					this.getAssets(), "fnt/COMPLEX_.TTF", 34, true, android.graphics.Color.BLACK);
			
			fonte.load();
			
			ptexto = new Text(670, 20, fonte, "0000", this.getVertexBufferObjectManager());
			ptexto.setText("0000");
			scene.attachChild(ptexto);
			
			
			//Ação de colisão
			scene.registerUpdateHandler(new IUpdateHandler() {
				
				@Override
				public void onUpdate(float pSecondElapsed) {	
					
					if (bola.collidesWith(circulo)) {				
						
						colisao.play();
						
						pontos = pontos +1;
												
						ptexto.setText(Integer.toString(pontos));
						bola.setPosition(100, 100);
						bola.setScale(1.0f);
						
					}
					
				}
				@Override
				public void reset() {
					
					
				}
				
			});
			
			return scene;
			
		}

	}

