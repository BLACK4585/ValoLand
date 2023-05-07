package tk.blackdev;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. It should boot up your game and do
 * initial initialisation Move your Logic into AppStates or Controls or other
 * java classes
 */
public class ValoLand extends SimpleApplication {
    
    private Spatial car;
    private Geometry geom;
    private Geometry geom2;
    private boolean isRunning = true;
    BitmapText text;

    public static void main(String[] args) {
        ValoLand app = new ValoLand();
        app.start();
    }
    
    public void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_L));
        
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Rotate",
                "Up", "Down");
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
                    geom.rotate(0, value, 0);
                }
                if (name.equals("Right")) {
                    geom.move(new Vector3f(value, 0, 0));
                }
                if (name.equals("Left")) {
                    geom.move(new Vector3f(-value, 0,0));
                }
                if (name.equals("Up")) {
                    geom.move(new Vector3f(0, value, 0));
                }
                if (name.equals("Down")) {
                    geom.move(new Vector3f(0, -value, 0));
                }
            } else {
                text.setText("Drücke P um fortzusetzen");
            }
        }
    };

    @Override
    public void simpleInitApp() {
        initKeys();
        
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        geom.setMaterial(mat);

        Box b2 = new Box(1, 1, 1);
        geom2 = new Geometry("Box2", b2);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Orange);
        geom2.setMaterial(mat2);
        geom2.setLocalTranslation(2, 2, 0);

        Node pivot = new Node("pivot");
        pivot.attachChild(geom);
        pivot.attachChild(geom2);
        
        car = assetManager.loadModel("Models/bugatti.j3o");
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        car.setMaterial(mat3);
        car.setLocalTranslation(-10, -5, 0);
        car.rotate(0, 180, 0);

        AmbientLight light = new AmbientLight(ColorRGBA.White);
        
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        text = new BitmapText(guiFont);
        text.setSize(40);
        text.setLocalTranslation(200, text.getLineHeight(), 0);
        
        assetManager.registerLocator("town.zip", ZipLocator.class);
        Spatial gameLevel = assetManager.loadModel("main.scene");
        gameLevel.setLocalTranslation(0, -5, 90);
        gameLevel.setLocalScale(2);
        
        rootNode.attachChild(pivot);
        rootNode.attachChild(car);
        rootNode.addLight(light);
        rootNode.attachChild(gameLevel);
        guiNode.attachChild(text);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        car.rotate(0, 2*tpf, 0);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        geom2.setMaterial(mat);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
