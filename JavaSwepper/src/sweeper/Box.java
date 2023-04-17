package sweeper;

public enum Box
{
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image;// object потому что он у нас в пакете свипер и не привязывается к конкретной реализации


    Box getNextNumberBox()
    {
        return Box.values()[this.ordinal() + 1];//this.ordinal() то текущий
    }

    int getNumber()
    {
        return this.ordinal();
    }
}
