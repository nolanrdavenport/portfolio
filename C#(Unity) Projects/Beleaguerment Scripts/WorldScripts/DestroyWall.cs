using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DestroyWall : MonoBehaviour{
    public GameObject destroyedWall;
    // Start is called before the first frame update
    void Start(){
        
    }

    // Update is called once per frame
    void Update(){
        if(Input.GetKeyDown(KeyCode.Space)){
            Instantiate(destroyedWall, transform.position, transform.rotation);
            Destroy(gameObject);
        }
    }
}
