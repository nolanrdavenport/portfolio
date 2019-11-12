using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class WinLoseScript : MonoBehaviour
{
    public GameObject inGame;
    public GameObject winLose;
    public GameObject resultObject;
    private string result = "";
    public GameObject eSys;
    escapeKey escKey;

  

    public void QuitLevelPressed(){
        SceneManager.LoadScene(0);
    }

    public void Lose(){
        escKey = (escapeKey)eSys.GetComponent(typeof(escapeKey));
        escKey.winLose = true;
        result = "You Lost!";
        resultObject.GetComponent<Text>().text = result;
        Cursor.visible = true;
        inGame.SetActive(false);
        winLose.SetActive(true);
    }

    public void Win(){
        escKey = (escapeKey)eSys.GetComponent(typeof(escapeKey));
        escKey.winLose = true;
        result = "You Win!";
        resultObject.GetComponent<Text>().text = result;
        Cursor.visible = true;
        inGame.SetActive(false);
        winLose.SetActive(true);
    }

    public void restart(){
        SceneManager.LoadScene(1);
    }

}
