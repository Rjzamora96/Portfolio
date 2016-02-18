using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using System;

public class StartMenuScript : MonoBehaviour {

    public GameObject leaderboard;
    public Text leaderboardText;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	    if(Input.GetKeyDown(KeyCode.Escape))
        {
            Application.Quit();
        }
	}

    public void StartGame()
    {
        SceneManager.LoadScene("Stage1");
    }
    public void ShowLeaderboard()
    {
        leaderboard.SetActive(true);
        if (PlayerPrefs.HasKey("HighestScore")) leaderboardText.text = "Highest score is " + PlayerPrefs.GetInt("HighestScore") + " points!\nRun took " + String.Format("{0:D2}:{1:D2}", PlayerPrefs.GetInt("LowestTimeMin"), PlayerPrefs.GetInt("LowestTimeSec")) + "!";
        else leaderboardText.text = "Empty";
    }
    public void HideLeaderboard()
    {
        leaderboard.SetActive(false);
    }

    public void QuitGame()
    {
        Application.Quit();
    }
}
