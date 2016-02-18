using UnityEngine;
using System.Collections;

public class BossScript : EnemyScript {

    private GameObject leftEngine;
    private GameObject rightEngine;

    public GameObject weapon;

	// Use this for initialization
	protected override void Start () {
        base.Start();
        leftEngine = GameObject.Find("LeftEngine");
        rightEngine = GameObject.Find("RightEngine");
        leftEngine.SetActive(false);
        StartCoroutine(AttackPattern());
        _hp = 500;
	}
	
	// Update is called once per frame
	protected override void Update () {
        base.Update();
	}

    private IEnumerator AttackPattern()
    {
        while (HP > 0)
        {
            yield return new WaitForSeconds(1.5f);
            if (rightEngine.activeSelf)
            {
                weapon.GetComponent<EnergyBallScript>().direction = (player.transform.position - new Vector3(1.6f, 1.82f)).normalized;
                Destroy(Instantiate(weapon, new Vector3(1.6f, 1.82f, -1), Quaternion.identity), 5);
                leftEngine.SetActive(!leftEngine.activeSelf);
                rightEngine.SetActive(!rightEngine.activeSelf);
            }
            else
            {
                weapon.GetComponent<EnergyBallScript>().direction = (player.transform.position - new Vector3(1.6f, -1.82f)).normalized;
                Destroy(Instantiate(weapon, new Vector3(1.6f, -1.82f, -1), Quaternion.identity), 5);
                leftEngine.SetActive(!leftEngine.activeSelf);
                rightEngine.SetActive(!rightEngine.activeSelf);
            }
        }
    }
}
