using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WinHandler : MonoBehaviour{
    public int enemiesRemaining;
    GameObject[] enemies;
    public GameObject WinLose;
    // Start is called before the first frame update
    void Start(){
        enemies = GameObject.FindGameObjectsWithTag("Enemy");
        foreach (GameObject enemy in enemies) {
            enemiesRemaining++;
        }
    }

    // Update is called once per frame
    void Update(){
        if (enemiesRemaining <= 0) {
            WinLoseScript wlScript = (WinLoseScript)WinLose.GetComponent(typeof(WinLoseScript));
            wlScript.Win();
        }
    }

    public void enemyKilled() {
        enemiesRemaining--;
    }
}
