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
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.system.AppSettings;

public class ValoLand extends SimpleApplication {

    Spatial player;
    BitmapText text;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1920, 1080);
        ValoLand app = new ValoLand();
        app.setSettings(settings);
        app.start();
    }
    
    public void initKeys(){
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
                player.move(new Vector3f(-value, 0, 0));
            }
            if (name.equals("Right")) {
                player.move(new Vector3f(value, 0, 0));
            }
            if (name.equals("Forwards")) {
                player.move(new Vector3f(0, 0, -value));
            }
            if (name.equals("Backwards")) {
                player.move(new Vector3f(0, 0, value));
            }
        }
    };

    @Override
    public void simpleInitApp() {
        initKeys();
        setDisplayStatView(false);
        flyCam.setEnabled(false);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

        Spatial ground = assetManager.loadModel("/Scenes/level1.j3o");
        player = assetManager.loadModel("/Models/character.j3o");
        player.setMaterial(mat);
        player.rotate(0, 160, 0);
        AmbientLight al = new AmbientLight();

        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setInvertVerticalAxis(true);

        rootNode.attachChild(ground);
        rootNode.attachChild(player);
        rootNode.addLight(al);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        // live update
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
