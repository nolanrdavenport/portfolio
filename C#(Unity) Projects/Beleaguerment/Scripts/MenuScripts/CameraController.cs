using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    public Transform[] views;
    public float transitionSpeed;
    Transform currentView;
    public GameObject startMenu;
    public GameObject mainMenu;
    // Start is called before the first frame update
    void Start(){
        currentView = views[0];
    }


    // Update is called once per frame
    void LateUpdate(){
        //Lerp position
        transform.position = Vector3.Lerp(transform.position, currentView.position, Time.deltaTime * transitionSpeed);
        transform.rotation = Quaternion.Lerp(transform.rotation, currentView.rotation, Time.deltaTime * transitionSpeed);
    }

    public void StartPressed() {
        currentView = views[1];
        StartCoroutine("showStart");
    }
    public void BackPressed() {
        startMenu.SetActive(false);
        currentView = views[0];
        StartCoroutine("showMain");
    }

    private IEnumerator showStart() {
        yield return new WaitForSeconds(.8f);
        startMenu.SetActive(true);
    }

    private IEnumerator showMain() {
        yield return new WaitForSeconds(.8f);
        mainMenu.SetActive(true);
    }
}
