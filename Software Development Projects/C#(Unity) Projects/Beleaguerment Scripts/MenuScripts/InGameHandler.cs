using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class InGameHandler : MonoBehaviour{
    // Start is called before the first frame update
    public GameObject healthUI;
    public Component[] rtf;

    GameObject health;
    GameObject healthS;
    void Start(){
        rtf = GetComponentsInChildren<RectTransform>();
        foreach (RectTransform child in rtf) {
            //child.sizeDelta = new Vector2 (Screen.width, Screen.height);
        }
        health = GameObject.Find("Health");
        healthS = GameObject.Find("HealthSymbol");

    }

    // Update is called once per frame
    void Update(){
        
    }

    public void updateHealth(int health) {
        healthUI.GetComponent<UnityEngine.UI.Text>().text = health.ToString();
    }

    public void updateHealthColor(int h){
        float red = (1.0f * (100 - (float)h))/100;
        float green = (1.0f * (float)h)/100;
        
        health.GetComponent<UnityEngine.UI.Text>().color = new Color(red, green, 0);
        healthS.GetComponent<UnityEngine.UI.Text>().color = new Color(red, green, 0);

    }
}
