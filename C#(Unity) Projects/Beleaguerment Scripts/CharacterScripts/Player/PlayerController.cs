using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{   
    //values
    public int health = 100;
    Vector3 pushValue;
    public int floorValue = 0;

    //objects
    GameObject bulletEmitter;
    public GameObject ragdoll;
    public GameObject winLose;

    //states
    private bool alive = true;
    void Start(){
        bulletEmitter = gameObject.transform.Find("Bullet Emitter").gameObject;
    }

    // Update is called once per frame
    void Update(){
        //dies
        if (health <= 0 && alive){
            WinLoseScript wlScript = (WinLoseScript)winLose.GetComponent(typeof(WinLoseScript));
            wlScript.Lose();
            alive = false;
            health--;
            AimTowardMouse aimer = (AimTowardMouse)GetComponent(typeof(AimTowardMouse));
            MovePlayer mover = (MovePlayer)GetComponent(typeof(MovePlayer));
            Shoot shooter = (Shoot)bulletEmitter.GetComponent(typeof(Shoot));
            aimer.kill();
            mover.kill();
            shooter.kill();


            GameObject tempRD = Instantiate(ragdoll, transform.position, transform.rotation);
            PlayerRagdollHandler rHandler = (PlayerRagdollHandler)tempRD.GetComponent(typeof(PlayerRagdollHandler));
            rHandler.pushRagdoll(pushValue);
            Destroy(gameObject);
        }
    }

    public void editFloorValue(int x){

        floorValue += x;
    }

    public void gotHit(Vector3 pushValue){
        if (alive){
            this.pushValue = pushValue;
            health -= 10;
            GameObject inGame = GameObject.Find("InGame");
            InGameHandler igHandler = (InGameHandler)inGame.GetComponent(typeof(InGameHandler));
            igHandler.updateHealthColor(health);

            GameObject tempInGame = GameObject.Find("InGame");
            InGameHandler inGameHandler = (InGameHandler)tempInGame.GetComponent(typeof(InGameHandler));
            inGameHandler.updateHealth(health);
            
        }
        
    }
}
