﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MouseCursor : MonoBehaviour {
    void Start(){
        Cursor.visible = false;
    }
    // Update is called once per frame
    void Update(){
        Vector2 cursorPos = Input.mousePosition;
        transform.position = cursorPos;
    }
}
