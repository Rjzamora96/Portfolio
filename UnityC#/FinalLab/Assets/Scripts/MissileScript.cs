using UnityEngine;
using System.Collections;

public class MissileScript : MonoBehaviour {

    private Rigidbody2D myBody;
    public bool isEnemies = false;
    void Start()
    {
        myBody = gameObject.GetComponent<Rigidbody2D>();
    }
	void Update () {
        if(!isEnemies)myBody.AddForce(new Vector2(1000, 0) * Time.deltaTime);
        if (isEnemies) myBody.AddForce(new Vector2(-1000, 0) * Time.deltaTime);
	}

    void OnTriggerEnter2D(Collider2D coll)
    {
        if(coll.gameObject.tag == "Enemy")
        {
            coll.gameObject.GetComponent<EnemyScript>().HP -= 10;
            Destroy(this.gameObject);
        }
        if (coll.gameObject.tag == "Player")
        {
            coll.gameObject.GetComponent<PlayerScript>().HP -= 10;
            Destroy(this.gameObject);
        }
    }
}
