using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManagerScript : MonoBehaviour {
    private bool gameEnded = false;

    private int points = 0;

    public GameObject startPoint;
    public GameObject marble;
    public Text collectableCount;

	// Use this for initialization
	void Start () {
        Instantiate(marble, startPoint.transform.position, Quaternion.identity);
	}
	
	// Update is called once per frame
	void Update () {
	    if(gameEnded && Input.GetMouseButtonDown(0))
        {
            gameEnded = false;
            Time.timeScale = 1;
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
	}

    public void EndTriggered()
    {
        gameEnded = true;
        Time.timeScale = 0;
    }

    public void InitializeWimpProtocol()
    {
        SceneManager.LoadScene("EasyMode");
    }
    public void ImCoolNow()
    {
        SceneManager.LoadScene("Tutorial");
    }
    public void PointCollected()
    {
        points++;
        collectableCount.text = "Collectables: " + points;
    }
}
