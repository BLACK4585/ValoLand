package tk.blackdev;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

public class ValoLand extends SimpleApplication {

    Spatial player;
    ChaseCamera chaseCam;
    BitmapText text;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1920, 1080);
        settings.setResizable(true);
        ValoLand app = new ValoLand();
        app.setSettings(settings);
        app.start();
    }

    public void initKeys() {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Forwards", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backwards", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(analogListener, "Up", "Down", "Left", "Right", "Forwards", "Backwards");
    }


    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Up")) {
                player.move(new Vector3f(0, value, 0));
            }
            if (name.equals("Down")) {
                player.move(new Vector3f(0, -value, 0));
            }
            if (name.equals("Left")) {
                player.move(new Vector3f(-value * 4, 0, 0));
            }
            if (name.equals("Right")) {
                player.move(new Vector3f(value * 4, 0, 0));
            }
            if (name.equals("Forwards")) {
                player.move(new Vector3f(0, 0, -value * 4));
            }
            if (name.equals("Backwards")) {
                player.move(new Vector3f(0, 0, value * 4));
            }
        }
    };

    @Override
    public void simpleInitApp() {
        initKeys();
        setDisplayStatView(false);
        flyCam.setEnabled(false);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

        Spatial ground = assetManager.loadModel("/Scenes/newScene.j3o");
        player = assetManager.loadModel("/Models/character.j3o");
        player.setMaterial(mat);
        AmbientLight al = new AmbientLight();

        chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setDefaultHorizontalRotation(1.5f);

        rootNode.attachChild(ground);
        rootNode.attachChild(player);
        rootNode.addLight(al);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //add update code here
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
