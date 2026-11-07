import com.raylib.Raylib.Vector3;

import static com.raylib.Raylib.Vector3Distance;
import static com.raylib.Raylib.Vector3Lerp;

public class EnemyRestState implements EnemyState{
    public void update(EnemyWasp wasp, float deltaTime){
        if(wasp.getRandPos()==null){
            wasp.getNewRandPos();
        }
        //go towards random posiiton
        wasp.setPos(Vector3Lerp(wasp.getPos(), wasp.getRandPos(), deltaTime*wasp.getRestSpeed()));
        //once the wasp gets close, choose a new randPos
        if(Vector3Distance(wasp.getPos(), wasp.getRandPos())<0.5f){ 
            wasp.getNewRandPos();
        }
        wasp.restTimer+=deltaTime;
        //chance to attack
        if(wasp.restTimer>=wasp.restInterval){
            wasp.restTimer=0f;
            if(Math.random()<0.3+deltaTime){
                wasp.setState(new EnemyAttackState());
            }
        }
    }
}
