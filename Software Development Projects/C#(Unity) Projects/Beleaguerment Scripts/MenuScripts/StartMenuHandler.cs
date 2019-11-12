using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class StartMenuHandler : MonoBehaviour{
    GameObject cam;
    CameraController camController;
    void Start(){
        cam = GameObject.Find("Camera");
        camController = (CameraController)cam.GetComponent(typeof(CameraController));
    }

    public void StartLevel() {
        SceneManager.LoadScene(1);
    }

    public void Back() {
        camController.BackPressed();
    }
}
