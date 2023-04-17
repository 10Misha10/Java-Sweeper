import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Ranges;
import sweeper.Game;

public class JavaSweeper extends JFrame
{
    private Game game;
    private JLabel label;//сообщение о статусе игры

    private JPanel panel;//добавляем в форму панель, на которой можно будет рисовать
    private final int COLS = 9;//ПОТОМУ ЧТО 15 КАРТИНОК(было 15);
    private final int ROWS = 9;//было 1
    private final int BOMBS = 10;//колво бомб
    private final int IMAGE_SIZE = 50;
    public static void main(String[] args)
    {
        new JavaSweeper();
    }
    private JavaSweeper()
    {
        game = new Game(COLS, ROWS, BOMBS );
        game.start();
        setImages();

        initLabel();

        initPanel();
        initFrame();
    }

    /*************************************************/
    private void initLabel()
    {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);//добавляем на нашу форму в низ
    }
    private void initPanel()
    {
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g)//эта функция рисует все
            {
                super.paintComponent(g);

                for(/*Box box : Box.values()*/Coord coord : Ranges.getAllCoords())//чтобы отображались наши картинки
                {
                    //Coord coord = new Coord(box.ordinal()*IMAGE_SIZE, 0);
                    g.drawImage((Image)game.getBox(coord).image/*Box.values()[(coord.x + coord.y)%Box.values().length].image*/, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);//ordinal дает нам порядковый номер в списке
                }
            }

        };

        /**********добавляем мышечный адаптер**********/
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);//было по дефолту

                int x = e.getX()/IMAGE_SIZE;//получаем координаты куда мфшка ткнулась
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x, y);//создаем коорднату с этими x y
                if(e.getButton() == MouseEvent.BUTTON1)//левая кнопка мыши
                {
                    game.pressLeftButton(coord);
                }
                if(e.getButton() == MouseEvent.BUTTON3)//правая
                {
                    game.pressRghtButton(coord);
                }
                if(e.getButton() == MouseEvent.BUTTON2)//колесо
                {
                    game.start();
                }

                label.setText(getMessage());//изображает сообщение

                panel.repaint();//перерисовываем форму, иначе изменеия не будут видны

            }
        });
        /************************************************/

        panel.setPreferredSize(new Dimension(/*COLS*IMAGE_SIZE*/Ranges.getSize().x*IMAGE_SIZE, /*ROWS*IMAGE_SIZE*/Ranges.getSize().y*IMAGE_SIZE));//установить размер
        add(panel);//добавить панель
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED : return "Think twice!";
            case BOMBED : return "YOU LOSE!";
            case WINNER : return "Congratulations!";
            default : return "Welcome";
        }
    }
    private void initFrame()
    {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //чтобы программа закрывалась при нажатии на крстик
        setTitle("JavaSweeper");

        setResizable(false);//нельзя менять размер
        setVisible(true);

        pack();//изменяет размеры формы так, чтоб все поместилось
        setLocationRelativeTo(null);//установка окна по центру
        setIconImage(getImage("icon"));//устанавливаем иконку
    }
    private void setImages()
    {
        for(Box box : Box.values())
        {
            box.image = getImage(box.name());
        }
    }
    private Image getImage(String name)
    {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));//создаем объект ImageIcon из файла
        return icon.getImage();//возвращаем картинку с помощью getImage()
    }
}
