using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class TimeManagerScript : MonoBehaviour {

    public Text timer;
    public float timerSeconds;
    public float timerMinutes;
	// Use this for initialization
	void Start () {
        DontDestroyOnLoad(this.gameObject);
	}
	
	// Update is called once per frame
	void Update () {
        if(timer == null)
        {
            timer = GameObject.Find("Time").GetComponent<Text>();
        }
        timerSeconds += Time.deltaTime;
        if (timerSeconds >= 60)
        {
            timerMinutes += 1;
            timerSeconds -= 60;
        }
        int tempMin = (int)Math.Floor(timerMinutes);
        int tempSec = (int)Math.Floor(timerSeconds);
        timer.text = String.Format("{0:D2}:{1:D2}", tempMin, tempSec);
    }
}
