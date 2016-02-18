using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class MatchCardScript : MonoBehaviour {

    public Sprite backSprite;
    public Sprite frontSprite;
    public bool onFront = false;
    private Color frontColor = new Color(0.5f, 0.0f, 0.0f);
    private RectTransform rectTransform;
    private Image image;
    private int colorID;
	// Use this for initialization
	void Awake () {
        rectTransform = this.gameObject.GetComponent<RectTransform>();
        image = this.gameObject.GetComponent<Image>();
	}
    
    public IEnumerator Deal(Vector3 dest)
    {

        float percentage = 0;
        Vector3 offset = new Vector3(dest.x + 100, dest.y - 100, 0);
        while (percentage < 1)
        {
            percentage += 2 * Time.deltaTime;
            rectTransform.anchoredPosition = Vector3.Lerp(offset, dest, percentage);
            rectTransform.localScale = Vector3.Lerp(new Vector3(3, 3, 3), new Vector3(1, 1, 1), percentage);
            yield return null;
        }
        rectTransform.anchoredPosition = dest;
        rectTransform.localScale = new Vector3(1, 1, 1);
    }
	
    public IEnumerator FaceUp()
    {
        this.gameObject.GetComponent<Button>().interactable = false;
        float flipAmount = 0;
        while(flipAmount < 180)
        {
            float change = 180 * Time.deltaTime;
            flipAmount += change;
            if(!onFront && flipAmount > 90)
            {
                image.sprite = frontSprite;
                image.color = frontColor;
                rectTransform.rotation = Quaternion.AngleAxis(270 + change, Vector3.up);
                onFront = true;
            }
            rectTransform.Rotate(Vector3.up, change);
            yield return null;
        }
        rectTransform.rotation = Quaternion.AngleAxis(0, Vector3.up);
    }
    public IEnumerator Fade()
    {
        while (image.color.a > 0)
        {
            image.color = new Color(image.color.r, image.color.g, image.color.b, image.color.a - (1 * Time.deltaTime));
            yield return null;
        }
        image.color = new Color(0, 0, 0, 0);
        GameObject.Find("MatchManager").GetComponent<MatchManagerScript>().cardList.Remove(this.gameObject);
        Destroy(this.gameObject);
    }
    public IEnumerator FaceDown()
    {
        float flipAmount = 0;
        while (flipAmount < 180)
        {
            float change = 180 * Time.deltaTime;
            flipAmount += change;
            if (onFront && flipAmount > 90)
            {
                image.sprite = backSprite;
                image.color = new Color(1, 1, 1);
                rectTransform.rotation = Quaternion.AngleAxis(270 + change, Vector3.up);
                onFront = false;
            }
            rectTransform.Rotate(Vector3.up, change);
            yield return null;
        }
        rectTransform.rotation = Quaternion.AngleAxis(0, Vector3.up);
        this.gameObject.GetComponent<Button>().interactable = true;
    }
    public void Flip()
    {
        if (!GameObject.Find("MatchManager").GetComponent<MatchManagerScript>().cardsFlipped)
        {
            if (!onFront)
            {
                StartCoroutine("FaceUp");
                StartCoroutine(GameObject.Find("MatchManager").GetComponent<MatchManagerScript>().CardClicked(this));
            }
        }
    }
    public void SetCardColor(Color value, int colorValue)
    {
        frontColor = value;
        colorID = colorValue;
    }

    public int GetColorIndex()
    {
        return colorID;
    }
}
