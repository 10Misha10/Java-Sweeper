package sweeper;

public class Game
{
    private Bomb bomb;//**матрица с бомбпми

    private Flag flag;
    private GameState state;//состояние игры

    public GameState getState() {
        return state;
    }
    public Game(int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }//размеры экрана и количества бомб
    public void start()//метод для запуска игры
    {
        bomb.start();

        flag.start();

        state = GameState.PLAYED;//когда игра начинается ставим значение играем
    }
    public Box getBox(Coord coord)//говорит то что нужно изобразить в том или ином местн экрана
    {
        //return Box.values()[(coord.x + coord.y)%Box.values().length];
        //return bombMap.get(coord);

        //return bomb.get(coord);//возвращал бомбы(нижний слой)
        if(flag.get(coord) == Box.OPENED)//если верхний слой закрыт то показываем верхний слой
            return bomb.get(coord);
        else//если нет то нижний
            return flag.get(coord);//теперь возвращает нижний слой то есть флаги
    }

    public void pressLeftButton(Coord coord)
    {

        //flag.setOpenedToBox(coord);
        if(gameOver()) return;//играем ли мы еще
        openBox(coord);

        checkWinner();//после кадого открытия проверяем не победили ли мы
    }

    private void checkWinner()
    {
         if(state == GameState.PLAYED)//проверяем играем ли мы еще сейчас
         {
             if(flag.getCountofClosedBoxes() == bomb.getTotalBombs())//равно ли количество закрытых клеток колву бомб
             {
                 state = GameState.WINNER;
             }
         }
    }
    private void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case OPENED : setOpenedToClosedBoxesAroundNumber(coord);return;
            case FLAGED : return;
            case CLOSED :
                switch (bomb.get(coord))
                {
                    case ZERO : openBoxesAround(coord);return;
                    case BOMB : openBombs(coord); return;
                    default : flag.setOpenedToBox(coord);return;//только если не бомба и не пусто може открывать
                }

        }
    }
    private void openBombs(Coord bombed)
    {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for(Coord coord : Ranges.getAllCoords())
        {
            if(bomb.get(coord) == Box.BOMB)
            {
                flag.setOpenedToClosedBombBox(coord);
            }
            else
            {
                flag.setNoBombToFlagedSafeBox(coord);
            }
        }
    }
    private void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for(Coord around : Ranges.getCoordsAround(coord))//рекурсия но с проверкой вдруг не пусто
        {
            openBox(around);
        }
    }

    public void pressRghtButton(Coord coord)
    {
        //flag.setFlagedToBox(coord);
        if(gameOver()) return;//играем ли мы еще
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver()
    {
        if(state == GameState.PLAYED){return false;}
        start();
        return true;
    }
    void setOpenedToClosedBoxesAroundNumber(Coord coord)//открывает закрытые ячейки вокруг числа
    {
        if(bomb.get(coord) != Box.BOMB)
            if(flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
            {
                for(Coord around : Ranges.getCoordsAround(coord))
                {
                    if(flag.get(around) == Box.CLOSED)
                    {
                        openBox(around);
                    }
                }
            }
    }

}
