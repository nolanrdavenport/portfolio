using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyAIController : MonoBehaviour{
    //moving variables
    GameObject player;
    Vector3 pushValue;
    int health = 100;
    public float removeBodyTimeframe = 5f;
    public float walkCycleTimeframe = 3f;
    public float idleCycleTimeframe = 1.5f;
    public float moveVelocity = 0f;
    private int targetAngle;
    GameObject sightHandler;
    Rigidbody RB;
    bool moveCycleStart = true;

    //shooting variables
    GameObject bulletEmitter;
    public int gunDamage = 1;
    public float fireRate = .01f;
    public float weaponRange = 50f;
    public float hitForce = 100f;
    private WaitForSeconds shotDuration = new WaitForSeconds(0.05f);
    private LineRenderer shotLine;
    private float nextFire;
    private Light flash;
    private int wait;
    private bool shooting = false;
    private bool waited = false;
    public AudioSource aS;
    public AudioClip aC;
    //other variables
    public int floorValue = 0;
    FloorHandler floorHandler;
    bool alive = true;
    public GameObject ragdoll;
    void Start(){
        player = GameObject.Find("Player_fab");
        sightHandler = gameObject.transform.Find("PlayerSightHandler").gameObject;
        RB = GetComponent<Rigidbody>();

        //shooting
        bulletEmitter = gameObject.transform.Find("BulletEmitter").gameObject;
        shotLine = bulletEmitter.GetComponent<LineRenderer>();
        flash = bulletEmitter.GetComponent<Light>();
        flash.intensity = 0;
        wait = 0;
        aS.clip = aC;

        //handling floor
        floorHandler = (FloorHandler)player.GetComponent(typeof(FloorHandler));
    }

    // Update is called once per frame
    void Update() { 
        if (health <= 0 && alive){
            GameObject eSys = GameObject.Find("EventSystem");
            WinHandler wHand = (WinHandler)eSys.GetComponent(typeof(WinHandler));
            wHand.enemyKilled();
            alive = false;
            floorHandler.removeEnemy(gameObject);

            GameObject tempRD = Instantiate(ragdoll, transform.position, transform.rotation);
            RagdollHandler rHandler = (RagdollHandler)tempRD.GetComponent(typeof(RagdollHandler));
            rHandler.pushRagdoll(pushValue);
            Destroy(gameObject);
        }
        else if (health > 0) {
            if (GameObject.Find("Player_fab") != null) {
                sightHandler.transform.LookAt(player.transform);
            }
            
            Vector3 rayOrigin = sightHandler.transform.position;
            RaycastHit hit;
            if(Physics.Raycast(rayOrigin, sightHandler.transform.forward, out hit, 1000) && hit.transform.gameObject.name == "Player_fab"){
                //gameObject.transform.LookAt(player.transform);
                var q = Quaternion.LookRotation(player.transform.position - transform.position);
                transform.rotation = Quaternion.RotateTowards(transform.rotation, q, 750 * Time.deltaTime);
                shooting = true;
                StartCoroutine("WaitBeforeShooting");
            }else if(moveCycleStart){
                shooting = false;
                waited = false;
                StartCoroutine("MoveCycle1");
                RB.velocity = transform.forward * moveVelocity;
            }else{
                shooting = false;
                waited = false;
                RB.velocity = transform.forward * moveVelocity;
                
            }
        }
    }

    void FixedUpdate(){
        if(health > 0){
            if(targetAngle > 0 && targetAngle <= 180){
                gameObject.transform.Rotate(0, -20, 0);
                targetAngle -= 20;
            }else if(targetAngle > 180 && targetAngle <= 360){
                gameObject.transform.Rotate(0, 20, 0);
                targetAngle += 20;
            }else{
               targetAngle = 0;
            }
        }

        if(shooting && waited && wait >= 10){
            aS.Play();

            wait = 0;
            
            StartCoroutine("DisplayLine");
            Vector3 rayOrigin = bulletEmitter.transform.position;
            RaycastHit hit;

            shotLine.SetPosition(0, rayOrigin);


            if (Physics.Raycast(rayOrigin, bulletEmitter.transform.forward, out hit, weaponRange))
            {
                shotLine.SetPosition(1, hit.point);
                
                GameObject hitPlayer = hit.transform.gameObject;
                if (hitPlayer.name == "Player_fab") {
                    PlayerController pController = (PlayerController)hitPlayer.GetComponent(typeof(PlayerController));
                    pController.gotHit(transform.forward);
                }
                
            }
            else {
                shotLine.SetPosition(1, rayOrigin + bulletEmitter.transform.forward * weaponRange);
            }
        }
        wait++;

        

    }

    public void gotHit(Vector3 pushValue){
        this.pushValue = pushValue;
        health -= 20;
    }

    public void editFloorValue(int newFloorValue){
        floorValue += newFloorValue;
    }

    private IEnumerator MoveCycle1(){
        moveCycleStart = false;
        targetAngle = (int)Random.Range(0f, 360f);
        moveVelocity = 2f;
        
        yield return new WaitForSeconds(walkCycleTimeframe);
        moveVelocity = 0f;
        StartCoroutine("MoveCycle2");
        
    }

    private IEnumerator MoveCycle2(){
        yield return new WaitForSeconds(idleCycleTimeframe);
        moveCycleStart = true;
    }

    private IEnumerator DisplayLine(){
        flash.intensity = 1.5f;
        shotLine.enabled = true;
        yield return shotDuration;
        shotLine.enabled = false;
        flash.intensity = 0;
    }

    private IEnumerator WaitBeforeShooting() {
        float reactionTime = Random.Range(.5f, 1.5f);
        yield return new WaitForSeconds(reactionTime);
        waited = true;
    }

    private void OnTriggerEnter(Collider other){
        if(health > 0){
            targetAngle = (int)Random.Range(160, 200);
        }
        
    }
}
