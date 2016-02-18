using UnityEngine;
using System.Collections;

public class EndZoneScript : MonoBehaviour {

    private GameObject gameManager;
	// Use this for initialization
	void Start () {
        gameManager = GameObject.Find("GameManager");
	}
	
	// Update is called once per frame
	void Update () {
	
	}
    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.GetComponent<MarbleScript>())
        {
            gameManager.GetComponent<GameManagerScript>().EndTriggered();
        }
    }
}
