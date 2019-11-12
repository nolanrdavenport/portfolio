using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseGameScript : MonoBehaviour{
    public GameObject inGame;
    public GameObject pauseMenu;

    public void QuitLevelPressed(){
        SceneManager.LoadScene(0);
    }

    public void pause() {
        Cursor.visible = true;
        inGame.SetActive(false);
        pauseMenu.SetActive(true);
    }

    public void resume() {
        Cursor.visible = false;
        inGame.SetActive(true);
        pauseMenu.SetActive(false);
    }
}
