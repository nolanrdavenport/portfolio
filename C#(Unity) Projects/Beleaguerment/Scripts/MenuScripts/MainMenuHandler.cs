using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainMenuHandler : MonoBehaviour{
    GameObject cam;
    CameraController camController;

    void Start(){
        cam = GameObject.Find("Camera");
        camController = (CameraController)cam.GetComponent(typeof(CameraController));
    }

    public void StartPressed() {
        camController.StartPressed();
        
        gameObject.SetActive(false);
    }

    public void QuitPressed() {
        Application.Quit();
    }
}
