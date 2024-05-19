package my_paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPaint extends JFrame implements ActionListener {

    private enum ShapeType { RECTANGLE, CIRCLE, LINE }

    private ShapeType currentShape = ShapeType.RECTANGLE;

    private Point startPoint;
    private Point endPoint;

    public MyPaint() {
        setTitle("Simple Paint App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu shapeMenu = new JMenu("Shape");
        
        JMenuItem rectangleItem = new JMenuItem("Rectangle");
        JMenuItem circleItem = new JMenuItem("Circle");
        JMenuItem LineItem = new JMenuItem("Line");
        
        shapeMenu.add(rectangleItem);
        shapeMenu.add(circleItem);
        shapeMenu.add(LineItem);
        
        rectangleItem.addActionListener(this);
        circleItem.addActionListener(this);
        LineItem.addActionListener(this);
        
        menuBar.add(shapeMenu);

        setJMenuBar(menuBar);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentShape == ShapeType.RECTANGLE) {
                    g.drawRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
                }else if(currentShape == ShapeType.LINE) {
                    g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                }else {
                    int diameter = Math.min(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
                    g.drawOval(startPoint.x, startPoint.y, diameter, diameter);
                }
            }
        };

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = e.getPoint();
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                drawingPanel.repaint();
            }
        });

        add(drawingPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Rectangle".equals(command)) {
            currentShape = ShapeType.RECTANGLE;
        } else if ("Circle".equals(command)) {
            currentShape = ShapeType.CIRCLE;
        } else if ("Line".equals(command)) {
            currentShape = ShapeType.LINE;
        }
    }

    public static void main(String[] args) {
        MyPaint sp = new MyPaint();
        sp.setVisible(true);
    }
}
