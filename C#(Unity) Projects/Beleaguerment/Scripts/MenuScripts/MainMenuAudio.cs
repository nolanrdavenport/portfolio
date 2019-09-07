using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainMenuAudio : MonoBehaviour
{
    public AudioSource musicSource;
    public AudioClip musicClip;
    void Start(){
        musicSource.clip = musicClip;
        musicSource.Play();
    }

    // Update is called once per frame
    void Update(){
        
    }
}
