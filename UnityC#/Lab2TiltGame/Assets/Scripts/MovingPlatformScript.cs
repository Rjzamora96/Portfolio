using UnityEngine;
using System.Collections;

public class MovingPlatformScript : MonoBehaviour {

    public Vector3 start;
    public Vector3 end;
    public float moveSpeed;

    public float initialPercent;
    public bool goesBothWays = true;
    public bool alignBall = true;

    private float percentage = 0;
    private bool goingForwards = true;
    private Vector3 lastPosition;

    void Start()
    {
        percentage = initialPercent;
    }
	
	// Update is called once per frame
	void Update () {
        lastPosition = transform.position;
	    if(goingForwards)
        {
            percentage += Time.deltaTime * moveSpeed;
            if (percentage >= 1)
            {
                if (goesBothWays)
                {
                    percentage = 1;
                    goingForwards = false;
                }
                else
                {
                    percentage = 0;
                }
            }
            this.gameObject.transform.localPosition = Vector3.Lerp(start, end, percentage);
        }
        else
        {
            percentage -= Time.deltaTime * moveSpeed;
            if(percentage <= 0)
            {
                percentage = 0;
                goingForwards = true;
            }
            this.gameObject.transform.localPosition = Vector3.Lerp(start, end, percentage);
        }
	}
    void OnCollisionStay(Collision col)
    {
        MarbleScript marble = col.gameObject.GetComponent<MarbleScript>();
        if(alignBall && marble)
        {
            marble.transform.position += transform.position - lastPosition;
        }
    }
}
