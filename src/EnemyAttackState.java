import static com.raylib.Raylib.Vector3Distance;
import static com.raylib.Raylib.Vector3Lerp;

import com.raylib.Raylib.Vector3;

public class EnemyAttackState implements EnemyState{
    public void update(EnemyWasp wasp, float deltaTime){
            if(wasp.returnTarget()==null||wasp.returnTarget().InHive){
                wasp.setTarget(null);
                try{
                    wasp.setTarget(wasp.pickTarget());
                }
                catch(EnemyWasp.NoAvailableBeeTarget e){
                    wasp.getNewRandPos();
                    wasp.setTarget(null);
                    wasp.stingTimer=0;
                    wasp.setState(new EnemyRestState());
                    return;
                }
            }
        // update target position
        wasp.offsetPos = new Vector3().y (0.8f).x(wasp.target.Position.x()).z(wasp.target.Position.z());
        wasp.huntTimer+=deltaTime;
        if (wasp.huntTimer>=wasp.huntInterval) {
            //update target pos after 5s
                    wasp.offsetPos = new Vector3().y(0.8f).x(wasp.target.Position.x()).z(wasp.target.Position.z());
                    wasp.huntTimer=0f;
        }
        wasp.setPos(Vector3Lerp(wasp.getPos(), wasp.offsetPos, deltaTime * wasp.speed));
        //if wasp is close enough to bee, sting it
        if (Vector3Distance (wasp.getPos(), wasp.offsetPos) < 0.1 / wasp.speed){
            wasp.stingTimer+=deltaTime;
            if(wasp.stingTimer>=wasp.stingInterval){
                wasp.target.beingAttacked=true;
                wasp.Sting();
                wasp.stingTimer=0;
            }
            if(wasp.getState() instanceof EnemyRestState){
                wasp.target.beingAttacked=false;
                wasp.setTarget(null);
                wasp.getNewRandPos();
            }
        }
    }
}
