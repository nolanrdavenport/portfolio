using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerRagdollHandler : MonoBehaviour{
    //values
    Vector3 pushValue;
    public int floorValue = 0;

    //states
    bool pushing = false;

    //objects
    public Rigidbody RB;
    PauseGameScript pauseGameScript;
    void Start(){
         
    }

    void Update(){
        //just died
        if (pushing) {
            RB.velocity = pushValue * 20;
            pushing = false;
        }
    }

    public void pushRagdoll(Vector3 pushValue) {
        this.pushValue = pushValue;
        pushing = true;
    }

    public void editFloorValue(int x){
        floorValue += x;
    }
}
