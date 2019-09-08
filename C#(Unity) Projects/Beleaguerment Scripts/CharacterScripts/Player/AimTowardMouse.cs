using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AimTowardMouse : MonoBehaviour {
    public Camera mainCamera;
    public bool alive = true;
    public GameObject bulletEmitter;
	// Use this for initialization
	void Start () {
        mainCamera = FindObjectOfType<Camera>();
	}
	
	// Update is called once per frame
	void Update () {
        Ray cameraRay = mainCamera.ScreenPointToRay(Input.mousePosition);
        
        Plane groundPlane = new Plane(Vector3.up, Vector3.zero);
        float rayLength;
        if (groundPlane.Raycast(cameraRay, out rayLength)) {
            Vector3 pointToAim = cameraRay.GetPoint(rayLength);
            Debug.DrawLine(cameraRay.origin, pointToAim, Color.blue);
            if (alive) {
                transform.LookAt(new Vector3(pointToAim.x, transform.position.y, pointToAim.z));
                bulletEmitter.transform.LookAt(new Vector3(pointToAim.x, bulletEmitter.transform.position.y, pointToAim.z));
            }
            
        }
	}

    public void kill() {
        alive = false;
    }

    public void revive() {
        alive = true;
    }
}
