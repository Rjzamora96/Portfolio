using UnityEngine;
using System.Collections;

public class EnemyScript : MonoBehaviour {

    protected TextMesh myHPDisplay;
    protected int _hp = 100;
    protected Quaternion initRotation;
    protected Vector3 initPosition;

    protected GameObject scoreManager;

    public GameObject explosion;

    public PlayerScript player;

    public int HP { get { return _hp; } set { _hp = value; } }

    protected void InitializeEnemy()
    {
        myHPDisplay = gameObject.GetComponentInChildren<TextMesh>();
        initRotation = myHPDisplay.transform.rotation;
        initPosition = myHPDisplay.transform.localPosition;
        scoreManager = GameObject.Find("ScoreManager");
        if(player == null)
        {
            player = GameObject.Find("Player").GetComponent<PlayerScript>();
        }

    }

    protected void StillHPDisplay()
    {
        myHPDisplay.transform.rotation = initRotation;
        myHPDisplay.transform.position = gameObject.transform.position + initPosition;
    }
	// Use this for initialization
	protected virtual void Start () {
        InitializeEnemy();
	}
	
    protected void UpdateHP()
    {
        if(myHPDisplay != null) myHPDisplay.text = "HP:" + HP;
        if (HP <= 0) { Destroy(Instantiate(explosion, transform.position, Quaternion.identity), 1.5f); GameObject.Find("ScoreManager").GetComponent<ScoreManager>().Score += 100; GameObject.Find("StageManager").GetComponent<StageScript>().EnemyDied(); Destroy(this.gameObject); }
    }

	// Update is called once per frame
	protected virtual void Update () {
        UpdateHP();
	}

    protected virtual void LateUpdate()
    {
        StillHPDisplay();
    }
}
