using UnityEngine;
using System.Collections;

public class DeathZoneScript : MonoBehaviour {

    public GameObject respawnLocation;

	// Use this for initialization
	void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.GetComponent<MarbleScript>())
        {
            col.gameObject.transform.position = respawnLocation.transform.position;
            col.gameObject.GetComponent<Rigidbody>().velocity = Vector3.zero;
            col.gameObject.GetComponent<Rigidbody>().angularVelocity = Vector3.zero;
        }
    }
}
