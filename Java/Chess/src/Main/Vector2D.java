package Main;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Vector2D {
    public int x = 0;
    public int y = 0;

    private static String[] _columns = { "A", "B", "C", "D", "E", "F", "G", "H" };

    public Vector2D()
    {
        x = 0;
        y = 0;
    }

    public Vector2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public static Vector2D sum(Vector2D base, Vector2D add)
    {
        return new Vector2D(base.x + add.x, base.y + add.y);
    }

    public static Vector2D difference(Vector2D base, Vector2D subtract)
    {
        return new Vector2D(base.x - subtract.x, base.y - subtract.y);
    }

    public static Vector2D parseLocation(String text)
    {
        Vector2D location = new Vector2D();
        for(int i = 0; i < _columns.length; i++)
        {
            if(text.substring(0, 1).equals(_columns[i]))
            {
                location = new Vector2D(i, Integer.parseInt(text.substring(1, 2)) - 1);
            }
        }
        return location;
    }

    public static String parseVector(Vector2D vector)
    {
        return (_columns[vector.x] + (vector.y + 1));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Vector2D.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Vector2D other = (Vector2D) obj;
        if(this.x == other.x && this.y == other.y)
        {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + x;
        hash = 53 * hash + y;
        return hash;
    }
}
