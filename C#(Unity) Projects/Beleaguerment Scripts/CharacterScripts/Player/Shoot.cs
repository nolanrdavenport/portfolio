using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shoot : MonoBehaviour
{

    public int gunDamage = 1;
    public float fireRate = .01f;
    public float weaponRange = 50f;
    public float hitForce = 100f;
    private WaitForSeconds shotDuration = new WaitForSeconds(0.05f);
    private LineRenderer shotLine;
    private float nextFire;
    private Light flash;
    private int wait;
    private bool alive = true;

    public AudioSource aS;
    public AudioClip aC;
    // Start is called before the first frame update
    void Start(){
        shotLine = GetComponent<LineRenderer>();
        flash = GetComponent<Light>();
        flash.intensity = 0;
        wait = 0;
        aS.clip = aC;
    }

    // Update is called once per frame
    void FixedUpdate()
    {
        if (alive)
        {

            if (Input.GetButton("Fire1") && wait >= 5)
            {
                aS.Play();

                wait = 0;

                StartCoroutine("DisplayLine");
                Vector3 rayOrigin = transform.position;
                RaycastHit hit;

                shotLine.SetPosition(0, rayOrigin);


                if (Physics.Raycast(rayOrigin, transform.forward, out hit, weaponRange))
                {
                    shotLine.SetPosition(1, hit.point);
                    GameObject hitPlayer = hit.transform.gameObject;
                    if (hitPlayer.tag == "Enemy")
                    {
                        EnemyAIController handler = (EnemyAIController)hitPlayer.GetComponent(typeof(EnemyAIController));
                        handler.gotHit(transform.forward);
                    }
                }
                else
                {
                    shotLine.SetPosition(1, rayOrigin + transform.forward * weaponRange);
                }
            }
            wait++;
        }
    }

    public void kill() {
        alive = false;
        Debug.Log("shooter.kill called");
    }
    private IEnumerator DisplayLine(){
        flash.intensity = 1.5f;
        shotLine.enabled = true;
        yield return shotDuration;
        shotLine.enabled = false;
        flash.intensity = 0;
    }
}
