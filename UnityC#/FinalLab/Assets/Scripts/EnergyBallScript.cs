using UnityEngine;
using System.Collections;

public class EnergyBallScript : MonoBehaviour {

    private Rigidbody2D myBody;

    public Vector2 direction;
    void Start()
    {
        myBody = gameObject.GetComponent<Rigidbody2D>();
    }
    void Update()
    {
        myBody.AddForce(direction * 1000 * Time.deltaTime);
    }

    void OnTriggerEnter2D(Collider2D coll)
    {
        if (coll.gameObject.tag == "Player")
        {
            coll.gameObject.GetComponent<PlayerScript>().HP -= 15;
            Destroy(this.gameObject);
        }
    }
}
