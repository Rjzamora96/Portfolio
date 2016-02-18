using UnityEngine;
using System.Collections;

public class Spinemy : EnemyScript {

    private Vector3 rotatePoint;
    public float angle = 0;
    private bool reverse = false;
    private bool thinking = false;
    private bool initialized = false;
    private float startY = 0;
	// Use this for initialization
	protected override void Start () {
        base.Start();
        startY = transform.position.y;
	}
	
	// Update is called once per frame
	protected override void Update () {
        base.Update();
        if(!initialized)
        {
            StartCoroutine(StopAndThink());
            initialized = true;
        }
        gameObject.transform.Rotate(new Vector3(0, 0, 1000 * Time.deltaTime));
        if (!thinking)
        {
            Vector3 nextLocation = new Vector3(rotatePoint.x + (3 * Mathf.Cos(angle * Mathf.Deg2Rad)), rotatePoint.y + (10 * Mathf.Sin(angle * Mathf.Deg2Rad)), 0);
            gameObject.transform.position = nextLocation;
            if (!reverse) angle += 75 * Time.deltaTime;
            else angle -= 75 * Time.deltaTime;
            if (angle < 0 || angle > 360)
            {
                angle = 0;
                reverse = false;
                StartCoroutine(StopAndThink());
            }
        }
	}

    private IEnumerator StopAndThink()
    {
        transform.position = new Vector3(Mathf.Round(transform.position.x), startY);
        thinking = true;
        yield return new WaitForSeconds(1);
        while (transform.position.x - player.transform.position.x != 6)
        {
            if (transform.position.x - player.transform.position.x > 6) transform.position -= new Vector3(2, 0, 0);
            if (transform.position.x - player.transform.position.x < 6) transform.position += new Vector3(2, 0, 0);
            yield return new WaitForSeconds(1);
        }
        rotatePoint = new Vector3(((player.transform.position.x + transform.position.x) / 2), transform.position.y, 0);
        thinking = false;
    }

    void OnTriggerEnter2D(Collider2D coll)
    {
        if (coll.gameObject.tag == "Player")
        {
            coll.gameObject.GetComponent<PlayerScript>().HP -= 10;
            reverse = true;
            if(!coll.gameObject.GetComponent<PlayerScript>().isInvulnerable) StartCoroutine(coll.gameObject.GetComponent<PlayerScript>().TriggerInvulnerability());
        }
    }
}
