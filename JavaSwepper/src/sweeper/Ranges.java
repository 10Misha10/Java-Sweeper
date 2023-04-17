package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges
{
    private static Coord size;//размеры экрана
    private static ArrayList<Coord> allCoords;//создаем колеекцию координат
    private static Random random = new Random();//генерируем случайное число для координат бомб
    static void setSize(Coord _size)
    {
        size = _size;
        allCoords = new ArrayList<Coord>();
        for(int y = 0; y < size.y; y++)
        {
            for(int x = 0; x < size.x; x++)
            {
                allCoords.add(new Coord(x, y));//добавляем в список эелемент с параметрами x y
            }
        }
    }
    public static Coord getSize() {
        return size;
    }
    public static ArrayList<Coord> getAllCoords()//дает нам очень удобный способ перебора всех координат
    {
        return allCoords;
    }
    static boolean inRange(Coord coord)
    {
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }
    static Coord getRandomCoord()
    {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));//генерирует случайную координату
    }
    static ArrayList<Coord> getCoordsAround(Coord coord)//вернуть влетки вокруг координаты
    {
        Coord around;
        ArrayList<Coord> list = new ArrayList<Coord>();
        for(int x = coord.x - 1; x <= coord.x + 1; x++)
        {
            for(int y = coord.y - 1; y <= coord.y + 1; y++)
            {
                if(inRange(around = new Coord(x, y)))//проверка есть ли такая координата на экранне
                {
                    if(!around.equals(coord))//не равна ли наша координата исходноой, то есть центральной
                    {
                        list.add(around);
                    }

                }
            }

        }
        return list;
    }
}
