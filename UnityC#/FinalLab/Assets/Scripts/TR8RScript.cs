using UnityEngine;
using System.Collections;

public class TR8RScript : EnemyScript {

    private bool thinking = true;
    public GameObject weapon;
	// Use this for initialization
	protected override void Start () {
        base.Start();
        StartCoroutine(StopAndThink());
	}
	
	// Update is called once per frame
    private IEnumerator StopAndThink()
    {
        thinking = true;
        yield return new WaitForSeconds(1f);
        if (transform.position.y > 0)
            transform.position += new Vector3(0, -2);
        else if (transform.position.y < 0)
            transform.position += new Vector3(0, 2);
        else
        {
            int dir = Random.Range(0, 2);
            if(dir == 0)
            {
                transform.position += new Vector3(0, -2);
            }
            else
            {
                transform.position += new Vector3(0, 2);
            }
        }
        yield return new WaitForSeconds(1f);
        thinking = false;
    }
	protected override void Update () {
        base.Update();
        if (!thinking)
        {
            GameObject missile = Instantiate(weapon, transform.position, Quaternion.identity) as GameObject;
            Destroy(missile, 5);
            StartCoroutine(StopAndThink());
        }
	}

}