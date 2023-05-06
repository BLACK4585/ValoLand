package tk.blackdev;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
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
    private Geometry geom2;
    private boolean grow = true;

    public static void main(String[] args) {
        ValoLand app = new ValoLand();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
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
        car.setLocalTranslation(-5, -5, 0);
        car.rotate(0, 180, 0);

        AmbientLight light = new AmbientLight(ColorRGBA.White);
        
        assetManager.registerLocator("town.zip", ZipLocator.class);
        Spatial gameLevel = assetManager.loadModel("main.scene");
        gameLevel.setLocalTranslation(0, -10, 90);
        gameLevel.setLocalScale(2);
        
        rootNode.attachChild(pivot);
        rootNode.attachChild(car);
        rootNode.addLight(light);
        rootNode.attachChild(gameLevel);

        pivot.rotate(.4f, .4f, .0f);
    }

    @Override
    public void simpleUpdate(float tpf) {
        car.rotate(0, 2*tpf, 0);
        if (grow) {
            geom2.scale(1 + (3f * tpf));
        } else {
            geom2.scale(1 - (3f * tpf));
        }

        Vector3f s = geom2.getLocalScale();
        if (s.getX() > 1.2f) {
            grow = false;
        } else if (s.getX() < 0.8f) {
            grow = true;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
