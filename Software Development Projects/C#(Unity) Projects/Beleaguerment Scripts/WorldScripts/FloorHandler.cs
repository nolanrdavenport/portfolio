using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FloorHandler : MonoBehaviour {
    GameObject[] enemies;
    List<GameObject> enemyList;
    PlayerController pController;
    void Start () {
        enemyList = new List<GameObject> ();
        enemies = GameObject.FindGameObjectsWithTag ("Enemy");
        foreach (GameObject enemy in enemies) {
            enemyList.Add (enemy);
        }

    }

    void Update () {
        int pFloorValue = 0;
        //finds proper player gameobject
        if (GameObject.FindWithTag ("Player") != null) {
            GameObject player = GameObject.FindWithTag ("Player");
            PlayerController pController = (PlayerController) player.GetComponent (typeof (PlayerController));
            pFloorValue = pController.floorValue;
            
        } else if (GameObject.FindWithTag ("PlayerRagdoll") != null) {
            GameObject player = GameObject.FindWithTag ("PlayerRagdoll");
            PlayerRagdollHandler pController = (PlayerRagdollHandler) player.GetComponent (typeof (PlayerRagdollHandler));
            pFloorValue = pController.floorValue;
            //Debug.Log("Player ragdoll floorValue: " + pFloorValue);
        }
        

        //hides enemies based on floor values
        foreach (GameObject enemy in enemyList) {
            if (enemy != null) {
                int eFloorValue = 0;
                //finds proper enemy controller script
                if (enemy.tag =="Enemy") {
                    EnemyAIController controller = (EnemyAIController) enemy.GetComponent (typeof (EnemyAIController));
                    if(controller != null){
                        eFloorValue = controller.floorValue;
                    }
                } else if (enemy.tag == "EnemyRagdoll") {
                    RagdollHandler controller = (RagdollHandler) enemy.GetComponent (typeof (RagdollHandler));
                    if(controller != null){
                        eFloorValue = controller.floorValue;
                    }
                }
                //hides enemies based on floor values
                if (eFloorValue > 0 && eFloorValue < pFloorValue) {
                    foreach (Transform child in enemy.transform) {
                        if (child.gameObject.name == "Body") {
                            child.gameObject.GetComponent<SkinnedMeshRenderer> ().enabled = false;
                        }
                        if (child.gameObject.name == "Gun") {
                            child.gameObject.GetComponent<MeshRenderer> ().enabled = false;
                        }
                        if (child.gameObject.name == "Cube") {
                            child.gameObject.GetComponent<SkinnedMeshRenderer>().enabled = false;
                        }
                    }
                } else {
                    foreach (Transform child in enemy.transform) {
                        if (child.gameObject.name == "Body") {
                            child.gameObject.GetComponent<SkinnedMeshRenderer> ().enabled = true;
                        }
                        if (child.gameObject.name == "Gun") {
                            child.gameObject.GetComponent<MeshRenderer> ().enabled = true;
                        }
                        if (child.gameObject.name == "Cube")
                        {
                            child.gameObject.GetComponent<SkinnedMeshRenderer>().enabled = true;
                        }
                    }
                }
            }
        }
    }

    public void removeEnemy (GameObject enemy) {
        enemyList.Remove (enemy);
    }

    public void addEnemy (GameObject enemy) {
        enemyList.Add (enemy);
    }
}