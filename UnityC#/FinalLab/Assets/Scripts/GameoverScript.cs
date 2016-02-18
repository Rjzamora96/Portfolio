using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using UnityEngine.SceneManagement;

public class GameoverScript : MonoBehaviour {

    public GameObject gameCanvas;

    public Text scoreDisplay;
    public Text timeDisplay;

    public AudioClip gameOver;
    public AudioClip battleMusic;

    public GameObject congrats;
    public GameObject failure;

	// Use this for initialization
	void Start () {
        DontDestroyOnLoad(this.gameObject);
        SceneManager.LoadScene("MainMenu");
	}

    public void ActivateScreen()
    {
        gameCanvas.SetActive(true);
        GetComponent<AudioSource>().clip = gameOver;
        GetComponent<AudioSource>().Play();
        ScoreManager scoreManager = GameObject.Find("ScoreManager").GetComponent<ScoreManager>();
        int score  = scoreManager.Score;
        TimeManagerScript timeManager = GameObject.Find("TimeManager").GetComponent<TimeManagerScript>();
        scoreDisplay.text = "You scored " + score + " points!";
        int tempMin = (int)Math.Floor(timeManager.timerMinutes);
        int tempSec = (int)Math.Floor(timeManager.timerSeconds);
        timeDisplay.text = "Your run lasted " + String.Format("{0:D2}:{1:D2}", tempMin, tempSec) + " minutes!";
        if(PlayerPrefs.HasKey("LowestTimeMin") && PlayerPrefs.HasKey("LowestTimeSec") && PlayerPrefs.HasKey("HighestScore"))
        {
            if(score > PlayerPrefs.GetInt("HighestScore"))
            {
                PlayerPrefs.SetInt("HighestScore", score);
                PlayerPrefs.SetInt("LowestTimeMin", tempMin);
                PlayerPrefs.SetInt("LowestTimeSec", tempSec);
                PlayerPrefs.Save();
                congrats.SetActive(true);
                failure.SetActive(false);
            }
            else if(score == PlayerPrefs.GetInt("HighestScore"))
            {
                if(tempMin < PlayerPrefs.GetInt("LowestTimeMin") || (tempMin == PlayerPrefs.GetInt("LowestTimeMin") && tempSec < PlayerPrefs.GetInt("LowestTimeSec")))
                {
                    PlayerPrefs.SetInt("HighestScore", score);
                    PlayerPrefs.SetInt("LowestTimeMin", tempMin);
                    PlayerPrefs.SetInt("LowestTimeSec", tempSec);
                    PlayerPrefs.Save();
                    congrats.SetActive(true);
                    failure.SetActive(false);
                }
                else
                {
                    congrats.SetActive(false);
                    failure.SetActive(true);
                }
            }
            else
            {
                congrats.SetActive(false);
                failure.SetActive(true);
            }
        }
        else
        {
            PlayerPrefs.SetInt("HighestScore", score);
            PlayerPrefs.SetInt("LowestTimeMin", tempMin);
            PlayerPrefs.SetInt("LowestTimeSec", tempSec);
            PlayerPrefs.Save();
            congrats.SetActive(true);
            failure.SetActive(false);
        }
        foreach(GameObject enemy in GameObject.FindGameObjectsWithTag("Enemy"))
        {
            Destroy(enemy);
        }
        Destroy(GameObject.FindGameObjectWithTag("Player"));
        Destroy(scoreManager.gameObject);
        Destroy(timeManager.gameObject);
    }

    public void ExitButton()
    {
        ScoreManager scoreManager = GameObject.Find("ScoreManager").GetComponent<ScoreManager>();
        TimeManagerScript timeManager = GameObject.Find("TimeManager").GetComponent<TimeManagerScript>();
        foreach (GameObject enemy in GameObject.FindGameObjectsWithTag("Enemy"))
        {
            Destroy(enemy);
        }
        Destroy(GameObject.FindGameObjectWithTag("Player"));
        Destroy(scoreManager.gameObject);
        Destroy(timeManager.gameObject);
        SceneManager.LoadScene("MainMenu");
        GetComponent<AudioSource>().clip = battleMusic;
        GetComponent<AudioSource>().Play();
    }

    public void ReturnToMenu()
    {
        SceneManager.LoadScene("MainMenu");
        gameCanvas.SetActive(false);
        GetComponent<AudioSource>().clip = battleMusic;
        GetComponent<AudioSource>().Play();
    }

    public void Restart()
    {
        SceneManager.LoadScene("Stage1");
        gameCanvas.SetActive(false);
        GetComponent<AudioSource>().clip = battleMusic;
        GetComponent<AudioSource>().Play();
    }
	
	// Update is called once per frame
	void Update () {
	
	}
}
