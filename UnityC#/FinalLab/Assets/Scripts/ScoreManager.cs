using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ScoreManager : MonoBehaviour {

    public Text score;
    private int _score = 0;

    public int Score { get { return _score; } set { _score = value; } }
	// Use this for initialization
	void Start () {
        DontDestroyOnLoad(this.gameObject);
	}
	
	// Update is called once per frame
	void Update () {
        if (score == null)
        {
            score = GameObject.Find("Score").GetComponent<Text>();
        }
        score.text = "Score: " + Score;
	}


}
