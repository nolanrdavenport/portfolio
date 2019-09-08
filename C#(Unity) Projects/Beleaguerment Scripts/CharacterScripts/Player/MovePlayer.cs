using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovePlayer : MonoBehaviour {
    public Rigidbody rb;
    public Transform ctf;
    public Transform tf;
    public float walkSpeed, runSpeed;
    private float speed;
    public float ctfDiff;
    private bool alive = true;
    Animator animator;

    public GameObject pauseMenu;
    PauseGameScript pauseGameScript;
    // Use this for initialization
    void Start () {
        rb = GetComponent<Rigidbody>();
        tf = GetComponent<Transform>();
        speed = walkSpeed;
        animator = GetComponent<Animator>();
        ctfDiff = ctf.position.y - tf.position.y;

        //lel
	}

    // Update is called once per frame
    void Update(){
        if (Input.GetKeyDown(KeyCode.Escape)) {
            pauseGameScript = (PauseGameScript)pauseMenu.GetComponent(typeof(PauseGameScript));
            pauseGameScript.pause();
        }   
    }

    void FixedUpdate () {
        

        if (alive)
        {
            float horizontal = Input.GetAxis("Horizontal");
            float vertical = Input.GetAxis("Vertical");

            if (horizontal != 0 || vertical != 0)
            {
                if (speed == runSpeed)
                {

                    animator.SetBool("isRunning", true);
                    animator.SetBool("isWalking", false);
                    animator.SetBool("isIdle", false);
                }
                else
                {
                    animator.SetBool("isWalking", true);
                    animator.SetBool("isIdle", false);
                    animator.SetBool("isRunning", false);
                }
            }
            else
            {
                animator.SetBool("isIdle", true);
                animator.SetBool("isRunning", false);
                animator.SetBool("isWalking", false);
            }

            Vector3 movement = new Vector3(horizontal * speed * Time.deltaTime, 0, vertical * speed * Time.deltaTime);


            rb.MovePosition(transform.position + movement);

            Vector3 cameraSpot = new Vector3(tf.position.x, tf.position.y + ctfDiff, tf.position.z);
            ctf.position = cameraSpot;
            if (Input.GetKey(KeyCode.LeftShift))
            {
                speed = runSpeed;

            }
            if (!Input.GetKey(KeyCode.LeftShift))
            {
                speed = walkSpeed;
            }
        }
    }

    public void kill() {
        alive = false;
    }
}
