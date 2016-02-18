using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Collections.Generic;
using System;

public class MatchManagerScript : MonoBehaviour {

    public Color[] colorList = new Color[6];
    public GameObject card;
    public GameObject beginButton;
    public Sprite altCard1;
    public Sprite altCard2;
    public List<GameObject> cardList;

    private bool gameStarted = false;
    public bool cardsFlipped = false;
    private MatchCardScript lastClicked;
    private AudioSource audioSource;

    private float timerSeconds;
    private float timerMinutes;
    public Text timer;
    public Text endScreen;
    public AudioClip startSound;
    public AudioClip errorSound;
    public AudioClip matchSound;
    public AudioClip allClear;
    public AudioClip dealCard;
    public AudioClip cardFlipSound;
    public AudioClip secretSong;
    
    private struct ColorIndexer
    {
        public Color color;
        public int colorID;
        public Sprite frontSprite;
    }

    private GameObject mainCanvas;
	// Use this for initialization
	void Start () {
        mainCanvas = GameObject.Find("Canvas");
        cardList = new List<GameObject>();
        audioSource = this.gameObject.GetComponent<AudioSource>();
        timerSeconds = 0;
        timerMinutes = 0;
	}

    public void HappySong()
    {
        GameObject.Find("Camera").GetComponent<AudioSource>().clip = secretSong;
        GameObject.Find("Camera").GetComponent<AudioSource>().Play();
        GameObject.Find("Secret").GetComponent<Button>().interactable = false;
    }
    public void StartButtonPressed()
    {
        StartCoroutine("StartGame");
    }
    public IEnumerator StartGame()
    {
        beginButton.SetActive(false);
        audioSource.clip = startSound;
        audioSource.Play();
        while (audioSource.isPlaying) yield return null;
        List<ColorIndexer> tempColors = new List<ColorIndexer>();
        for (int i = 0; i < 12; ++i)
        {
            Sprite sprite = altCard1;
            if(i > 5)
            {
                sprite = altCard2;
            }
            ColorIndexer indexer = new ColorIndexer();
            indexer.frontSprite = sprite;
            indexer.color = colorList[i%6];
            indexer.colorID = i;
            tempColors.Add(indexer);
        }
        tempColors.RemoveAt(UnityEngine.Random.Range(0, tempColors.Count));
        tempColors.RemoveAt(UnityEngine.Random.Range(0, tempColors.Count));
        for (int i = 0; i < 10; ++i)
        {
            tempColors.Add(tempColors[i]);
        }
        for (int j = 0; j < 4; ++j)
        {
            for (int k = 0; k < 5; ++k)
            {
                //Vector3 dest = new Vector3(-150 + (100 * j), 200 - (100 * k), 0);
                GameObject matchCard = (GameObject)Instantiate(card, new Vector3(-135 + (90 * j), 200 - (100 * k), 0), Quaternion.identity);
                matchCard.transform.SetParent(mainCanvas.transform, false);
                matchCard.GetComponent<Button>().interactable = false;
                int chosenColor = UnityEngine.Random.Range(0, tempColors.Count);
                matchCard.GetComponent<MatchCardScript>().SetCardColor(tempColors[chosenColor].color, tempColors[chosenColor].colorID);
                matchCard.GetComponent<MatchCardScript>().frontSprite = tempColors[chosenColor].frontSprite;
                cardList.Add(matchCard);
                tempColors.RemoveAt(chosenColor);
                StartCoroutine(matchCard.GetComponent<MatchCardScript>().Deal(new Vector3(-135 + (90 * j), 200 - (100 * k), 0)));
                audioSource.clip = dealCard;
                audioSource.volume = 0.5f;
                audioSource.Play();
                while (audioSource.isPlaying) yield return null;
            }
        }
        foreach (var myCard in cardList) myCard.GetComponent<Button>().interactable = true;
        audioSource.volume = 1;
        timerMinutes = 0;
        timerSeconds = 0;
        timer.gameObject.SetActive(true);
        gameStarted = true;
    }

    public IEnumerator CardClicked(MatchCardScript matchCard)
    {
        audioSource.clip = cardFlipSound;
        audioSource.Play();
        if (!lastClicked)
        {
            lastClicked = matchCard;
        }
        else
        {
            cardsFlipped = true;
            if (matchCard.GetColorIndex() == lastClicked.GetColorIndex())
            {
                yield return new WaitForSeconds(2);
                audioSource.clip = matchSound;
                audioSource.Play();
                StartCoroutine(matchCard.Fade());
                StartCoroutine(lastClicked.Fade());
                lastClicked = null;
                cardsFlipped = false;
            }
            else
            {
                yield return new WaitForSeconds(2);
                audioSource.clip = errorSound;
                audioSource.Play();
                StartCoroutine(matchCard.FaceDown());
                StartCoroutine(lastClicked.FaceDown());
                lastClicked = null;
                cardsFlipped = false;
            }
        }
    }

    IEnumerator EndGame()
    {
        gameStarted = false;
        timer.gameObject.SetActive(false);
        endScreen.gameObject.SetActive(true);
        int tempMin = (int)Math.Floor(timerMinutes);
        int tempSec = (int)Math.Floor(timerSeconds);
        endScreen.text = "You took\n" + tempMin + " minutes and " + tempSec + " seconds!";
        while (audioSource.isPlaying)
        {
            yield return null;
        }
        audioSource.clip = allClear;
        audioSource.Play();
        yield return new WaitForSeconds(3);
        endScreen.gameObject.SetActive(false);
        beginButton.SetActive(true);
    }
	// Update is called once per frame
	void Update () {
        if (gameStarted)
        {
            timerSeconds += Time.deltaTime;
            if(timerSeconds >= 60)
            {
                timerMinutes += 1;
                timerSeconds -= 60;
            }
            int tempMin = (int) Math.Floor(timerMinutes);
            int tempSec = (int)Math.Floor(timerSeconds);
            timer.text = String.Format("{0:D2}:{1:D2}", tempMin, tempSec);
            if (cardList.Count == 0)
            {
                StartCoroutine(EndGame());
            }
        }
	}
}
