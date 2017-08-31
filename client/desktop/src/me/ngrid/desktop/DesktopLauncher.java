package me.ngrid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.ngrid.examples.Basic3D;
import me.ngrid.examples.DropGame;
import me.ngrid.examples.RainDropsExample;
import me.ngrid.examples.ShaderTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new SensojiGame(), config);
//		new LwjglApplication(new RainDropsExample(), config);
//		new LwjglApplication(new DropGame(), config);
//        new LwjglApplication(new Basic3D(), config);
        new LwjglApplication(new ShaderTest(), config);
	}
}
