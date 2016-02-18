using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class StageScript : MonoBehaviour {

    public string NextStage;

    public bool isLastStage;
	// Use this for initialization
	void Start () {
	}
	
    public void EnemyDied()
    {
        if (GameObject.FindGameObjectsWithTag("Enemy").Length == 1)
        {
            if (isLastStage) GameObject.Find("GameOver").GetComponent<GameoverScript>().ActivateScreen();
            else SceneManager.LoadScene(NextStage);
        }
    }
	// Update is called once per frame
	void Update () {
	    
	}
}
