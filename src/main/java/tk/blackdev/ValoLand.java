package tk.blackdev;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. It should boot up your game and do
 * initial initialisation Move your Logic into AppStates or Controls or other
 * java classes
 */
public class ValoLand extends SimpleApplication {
    
    private Geometry geom;
    private Spatial character;
    private boolean isRunning = true;
    Node loadedNode;
    BitmapText text;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1908, 1080);
        ValoLand app = new ValoLand();
        app.setSettings(settings);
        app.start();
    }
    
    public void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_L));
        
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Rotate",
                "Up", "Down", "CamSwitch");
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
               isRunning = !isRunning; 
               if (isRunning) {
                   text.setText("");
               } else {
                   text.setText("Das Spiel ist pausiert!");
               }
            }
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                if (name.equals("Rotate")) {
                    loadedNode.rotate(0, value, 0);
                }
                if (name.equals("Right")) {
                    loadedNode.move(new Vector3f(value, 0, 0));
                }
                if (name.equals("Left")) {
                    loadedNode.move(new Vector3f(-value, 0,0));
                }
                if (name.equals("Up")) {
                    loadedNode.move(new Vector3f(0, value, 0));
                }
                if (name.equals("Down")) {
                    loadedNode.move(new Vector3f(0, -value, 0));
                }
            } else {
                text.setText("Drücke P um fortzusetzen");
            }
        }
    };

    @Override
    public void simpleInitApp() {
        initKeys();
        setDisplayStatView(false);        
        
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        text = new BitmapText(guiFont);
        text.setSize(40);
        text.setLocalTranslation(200, text.getLineHeight(), 0);

        guiNode.attachChild(text);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        // live upate
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
