using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class escapeKey : MonoBehaviour{
    public GameObject pauseMenu;
    PauseGameScript pauseGameScript;
    public bool winLose = false;
    // Start is called before the first frame update
    void Start(){
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape) && winLose == false)
        {
            pauseGameScript = (PauseGameScript)pauseMenu.GetComponent(typeof(PauseGameScript));
            pauseGameScript.pause();
        }
    }
}
