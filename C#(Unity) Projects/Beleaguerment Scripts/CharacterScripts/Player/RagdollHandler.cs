using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RagdollHandler : MonoBehaviour {
    Vector3 pushValue;
    bool pushing = false;
    public Rigidbody RB;
    public GameObject pauseMenu;
    PauseGameScript pauseGameScript;
    public int floorValue;
    // Start is called before the first frame update
    void Start () {
        if (gameObject.tag == "EnemyRagdoll") {
            
            GameObject eSystem = GameObject.Find("EventSystem");
            FloorHandler fHandler = (FloorHandler) eSystem.GetComponent (typeof (FloorHandler));
            fHandler.addEnemy (gameObject);
            
            
        }
    }

    // Update is called once per frame
    void Update () {
        
        if (pushing) {
            RB.velocity = pushValue * 20;
            pushing = false;
        }
    }

    public void pushRagdoll (Vector3 pushValue) {
        this.pushValue = pushValue;
        pushing = true;
    }

    public void editFloorValue (int x) {
        floorValue += x;
    }

}