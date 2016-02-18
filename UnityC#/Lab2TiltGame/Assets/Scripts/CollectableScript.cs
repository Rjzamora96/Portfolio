using UnityEngine;
using System.Collections;

public class CollectableScript : MonoBehaviour {

    public GameObject gameManager;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
        this.transform.Rotate(new Vector3(0, 0, 1), 180 * Time.deltaTime);
	}

    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.GetComponent<MarbleScript>())
        {
            gameManager.GetComponent<GameManagerScript>().PointCollected();
            Destroy(this.gameObject);
        }
    }
}
