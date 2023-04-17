package sweeper;

public class Bomb
{
    private Matrix bombMap;
    private int totalBombs;//количество бомб
    Bomb(int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBombsCount();//чтобы нельзя было создать бомб больше чем нужно
    }
    void start()//в этой функции будем размещать элементы
    {
        bombMap = new Matrix(Box.ZERO);
        for(int j = 0; j < totalBombs; j++)//генерируем бомбы в случайном месте
        {
            placeBomb();
        }

    }
    Box get(Coord coord)//чтобы узнавать что находтся в той или иной клетке
    {
        return bombMap.get(coord);
    }
    private void fixBombsCount()//для того чтобы нельзя было сгенерировать число бомб больше чем нужно
    {
        int maxBombs = Ranges.getSize().x*Ranges.getSize().y/2;//чтобы не больше полвины карты было бомб
        if(totalBombs > maxBombs)
        {
            totalBombs = maxBombs;
        }
    }
    private void placeBomb()//для размещения одной бомбы
    {
        while (true)
        {
            Coord coord = Ranges.getRandomCoord();
            if(bombMap.get(coord) == Box.BOMB){continue;}//если уже есть бомба, то пропускаем
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }

        /*for(Coord around : Ranges.getCoordsAround(coord))//перебирает все клетки вокруг клетки текущей и устанавливает единички
        {
            bombMap.set(around, Box.NUM1);
        }*/

    }
    private void incNumbersAroundBomb(Coord coord)
    {
        for(Coord around : Ranges.getCoordsAround(coord))
        {
            if(Box.BOMB != bombMap.get(around))
            {
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
            }


        }

    }

    int getTotalBombs()
    {
        return totalBombs;
    }

}
