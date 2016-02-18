using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

    private Vector2 startPos;
    public GameObject weapon;
    private TextMesh myHPDisplay;
    private bool endFound = true;
    private bool canSwipe = true;
    private bool canShoot = true;
    public bool isInvulnerable = false;
    private int _hp = 100;
    public int HP { get { return _hp; } set { if (!isInvulnerable) { _hp = value; StartCoroutine(TriggerInvulnerability()); } } }
    void Start()
    {
        myHPDisplay = gameObject.GetComponentInChildren<TextMesh>();
        DontDestroyOnLoad(this.gameObject);
    }

    private IEnumerator CooldownPeriod()
    {
        canSwipe = false;
        yield return new WaitForSeconds(0.1f);
        canSwipe = true;
    }

    private IEnumerator FirePeriod()
    {
        canShoot = false;
        yield return new WaitForSeconds(0.25f);
        canShoot = true;
    }

    public IEnumerator TriggerInvulnerability()
    {
        GetComponent<AudioSource>().Play();
        isInvulnerable = true;
        for(int j = 0; j < 4; j++)
        {
            GetComponent<SpriteRenderer>().color = new Color(1, 1, 1, 0);
            yield return new WaitForSeconds(0.25f);
            GetComponent<SpriteRenderer>().color = new Color(1, 1, 1, 1);
            yield return new WaitForSeconds(0.25f);
        }
        isInvulnerable = false;
    }
    protected void UpdateHP()
    {
        if (myHPDisplay != null) myHPDisplay.text = "HP:" + HP;
        if (HP <= 0)
        {
            Destroy(this.gameObject);
            GameObject.Find("GameOver").GetComponent<GameoverScript>().ActivateScreen();
        }
    }

    void Update ()
    {
        UpdateHP();
#if UNITY_EDITOR
        if (canSwipe)
        {
            if (Input.GetKeyDown(KeyCode.W))
            {
                if (gameObject.transform.position.y < 2)
                {
                    gameObject.transform.position += new Vector3(0, 2.0f, 0);
                    StartCoroutine(CooldownPeriod());
                }
            }
            if (Input.GetKeyDown(KeyCode.A))
            {
                if (gameObject.transform.position.x > -5)
                {
                    gameObject.transform.position -= new Vector3(2.0f, 0, 0);
                    StartCoroutine(CooldownPeriod());
                }
            }
            if (Input.GetKeyDown(KeyCode.S))
            {
                if (gameObject.transform.position.y > -2)
                {
                    gameObject.transform.position -= new Vector3(0, 2.0f, 0);
                    StartCoroutine(CooldownPeriod());
                }
            }
            if (Input.GetKeyDown(KeyCode.D))
            {
                if (gameObject.transform.position.x < -1)
                {
                    gameObject.transform.position += new Vector3(2.0f, 0, 0);
                    StartCoroutine(CooldownPeriod());
                }
            }
        }
        if(Input.GetKeyDown(KeyCode.Space) && canShoot)
        {
            GameObject missile = Instantiate(weapon, transform.position, Quaternion.identity) as GameObject;
            Destroy(missile, 5);
            StartCoroutine(FirePeriod());
        }
#endif
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            GameObject.Find("GameOver").GetComponent<GameoverScript>().ExitButton();
        }
        if (Input.touchCount == 0) return;
        if(Input.GetTouch(0).phase == TouchPhase.Began || endFound)
        {
            startPos = Input.GetTouch(0).position;
            endFound = false;
        }
        if (Input.GetTouch(0).phase == TouchPhase.Ended)
        {
            Vector2 delta = Input.GetTouch(0).position - startPos;
            endFound = true;
            if (delta.sqrMagnitude > 10000 && canSwipe)
            {
                if (Mathf.Abs(delta.x) >= Mathf.Abs(delta.y))
                {
                    if (delta.x >= 0 && gameObject.transform.position.x < -1)
                    {
                        gameObject.transform.position += new Vector3(2.0f, 0, 0);
                    }
                    else if(delta.x < 0 && gameObject.transform.position.x > -5)
                    {
                        gameObject.transform.position -= new Vector3(2.0f, 0, 0);
                    }
                }
                else
                {
                    if(delta.y >= 0 && gameObject.transform.position.y < 2)
                    {
                        gameObject.transform.position += new Vector3(0, 2.0f, 0);
                    }
                    else if(delta.y < 0 && gameObject.transform.position.y > -2)
                    {
                        gameObject.transform.position -= new Vector3(0, 2.0f, 0);
                    }
                }
                StartCoroutine(CooldownPeriod());
            }
            if(delta.sqrMagnitude <= 10000 && canShoot)
            {
                GameObject missile = Instantiate(weapon, transform.position, Quaternion.identity) as GameObject;
                Destroy(missile, 5);
                StartCoroutine(FirePeriod());
            }
        }
    }
}
