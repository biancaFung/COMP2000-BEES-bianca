import static com.raylib.Raylib.Vector3Distance;
import static com.raylib.Raylib.Vector3Lerp;

import java.util.Random;

import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector3;

public class EnemyWasp extends Enemy {
    //attributes based off UML
    private int beesKilled=0;
    private int honeyStolen=0;

    public Texture VisualTexture;
    Bee target;
    float huntTimer=0f; //time since last target pos update
    float huntInterval = 5f;    //update target pos after 5s
    float stingTimer=0f;
    float stingInterval=1.5f;
    float restTimer=0f;
    float restInterval=5f;
    
    //used when resting
    Vector3 randPos;
    Vector3 offsetPos;
    float fieldSize = Field.fieldSize *5;
    float restSpeed = 0.8f;

    public EnemyWasp(String name, Vector3 spawnPos, Texture texture) {
        super(name, spawnPos, texture);
        texture=VisualTexture;
        target=pickTarget(); 
        setState(new EnemyRestState());       
    }
    
     void Sting(){
        //check if bee is already dead, or in the hive
        if(!target.isAlive ||target.InHive){
            stingTimer=0f;
            setState(new EnemyRestState());
        }
        else{   //bee is alive --> sting = damage bee health
            target.Health-=25f;
            if(target.Health<=0){   //bee dead
                target.isAlive=false;
                honeyStolen+=((BeeWorker)target).getNectorCount();
                beesKilled++;
                totalBeesKilled++;
                stingTimer=0f;
                setState(new EnemyRestState());
            }
        }
    }

    public void getNewRandPos(){
        float randX = new Random().nextFloat(-fieldSize/5,fieldSize/5);
        float randY = new Random().nextFloat(0,fieldSize/5);
        float randZ = new Random().nextFloat(-fieldSize/5,fieldSize/5);
        randPos= new Vector3().y(randY).x(randX).z(randZ); 
    }

    @Override
    public void update (float deltaTime) {
        currentState.update(this,deltaTime);    
    }
    public int getBeesKilled(){
        return this.beesKilled;
    }

    public int getHoneyStolen(){
        return this.honeyStolen;
    }
    public Vector3 getRandPos(){
        return this.randPos;
    }
    public void setPos(Vector3 pos){
        Position=pos;
    }
    public Vector3 getPos(){
        return Position;
    }
    public float getRestSpeed(){
        return restSpeed;
    }
    public Bee returnTarget(){
        return this.target;
    }
    public void setTarget(Bee b){
        target=b;
    }
}
