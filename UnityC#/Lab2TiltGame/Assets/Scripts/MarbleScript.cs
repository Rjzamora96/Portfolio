using UnityEngine;
using System.Collections;

public class MarbleScript : MonoBehaviour {

    private const float MOVESPEED = 400;
    private const float JUMPHEIGHT = 150;
    private Rigidbody rigidBody;
    private Camera myCamera;
	// Use this for initialization
	void Start () {
        rigidBody = this.gameObject.GetComponent<Rigidbody>();
        myCamera = GameObject.Find("Main Camera").GetComponent<Camera>();
        Input.gyro.enabled = true;
	}
	
	// Update is called once per frame
	void Update () {
        myCamera.transform.position = this.transform.position + new Vector3(0, 4, 0);
	    if(Input.GetKey(KeyCode.W))
        {
            rigidBody.AddForce(new Vector3(0, 0, MOVESPEED) * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.A))
        {
            rigidBody.AddForce(new Vector3(-MOVESPEED, 0, 0) * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.S))
        {
            rigidBody.AddForce(new Vector3(0, 0, -MOVESPEED) * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.D))
        {
            rigidBody.AddForce(new Vector3(MOVESPEED, 0, 0) * Time.deltaTime);
        }
        if(Input.GetKeyDown(KeyCode.Space) && Physics.Raycast(transform.position, -Vector3.up, 0.25f))
        {
            rigidBody.AddForce(new Vector3(0, JUMPHEIGHT, 0));
        }
        rigidBody.AddForce(new Vector3(Input.acceleration.x, 0, Input.acceleration.y) * MOVESPEED * 2.75f * Time.deltaTime);
        if(Input.GetMouseButtonDown(0) && Physics.Raycast(transform.position, -Vector3.up, 0.25f))
        {
            rigidBody.AddForce(new Vector3(0, JUMPHEIGHT, 0));
        }
    }
}
