using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FadeIfUnder : MonoBehaviour {

    // Start is called before the first frame update
    public MeshRenderer[] renderers;
    GameObject player;
    PlayerController pController;
    EnemyAIController eController;
    PlayerRagdollHandler prController;
    RagdollHandler erController;
    void Start () {
        renderers = GetComponentsInChildren<MeshRenderer> ();
        player = GameObject.Find ("Player_fab");
        pController = (PlayerController) player.GetComponent (typeof (PlayerController));
    }

    private void OnTriggerEnter (Collider other) {
        if (other.gameObject.tag == "Enemy" && other.GetType () == typeof (CapsuleCollider)) {
            eController = (EnemyAIController) other.gameObject.GetComponent (typeof (EnemyAIController));
            eController.editFloorValue (1);
        } else if (other.gameObject.tag == "Player" && other.gameObject.tag != "Ragdoll") {
            pController.editFloorValue (1);
            foreach (MeshRenderer mesh in renderers) {
                mesh.enabled = false;
            }
        } else if (other.gameObject.name == "ChestP") {
            GameObject pRagdoll = GameObject.FindWithTag("PlayerRagdoll");
            prController = (PlayerRagdollHandler) pRagdoll.gameObject.GetComponent (typeof (PlayerRagdollHandler));
            prController.editFloorValue (1);
            foreach (MeshRenderer mesh in renderers) {
                mesh.enabled = false;
            }
        } else if (other.gameObject.name == "PelvisE") {
            GameObject parent1 = other.transform.parent.gameObject;
            GameObject parent2 = parent1.transform.parent.gameObject;
            erController = (RagdollHandler) parent2.gameObject.GetComponent (typeof (RagdollHandler));
            erController.editFloorValue (1);
        }
    }

    private void OnTriggerExit (Collider other) {
        if (other.gameObject.tag == "Enemy" && other.GetType () == typeof (CapsuleCollider)) {
            eController = (EnemyAIController) other.gameObject.GetComponent (typeof (EnemyAIController));
            eController.editFloorValue (-1);
        } else if (other.gameObject.tag == "Player" && other.gameObject.tag != "Ragdoll") {
            pController.editFloorValue (-1);
            foreach (MeshRenderer mesh in renderers) {
                mesh.enabled = true;
            }
        }
        else if (other.gameObject.name == "ChestP")
        {
            GameObject pRagdoll = GameObject.FindWithTag("PlayerRagdoll");
            prController = (PlayerRagdollHandler)pRagdoll.gameObject.GetComponent(typeof(PlayerRagdollHandler));
            prController.editFloorValue(-1);
            foreach (MeshRenderer mesh in renderers)
            {
                mesh.enabled = false;
            }
        }
        else if (other.gameObject.name == "PelvisE")
        {
            GameObject parent1 = other.transform.parent.gameObject;
            GameObject parent2 = parent1.transform.parent.gameObject;
            erController = (RagdollHandler)parent2.gameObject.GetComponent(typeof(RagdollHandler));
            erController.editFloorValue(-1);
        }
    }
}