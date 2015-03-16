package View;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
 
public class BarChart
{
   private int width;
   private int height;
   private ArrayList<Double> data;
 
   public BarChart(int aWidth, int aHeight)
   {
      width = aWidth;
      height = aHeight;
      data = new ArrayList<Double>();
   }
 
   public void add(double value)
   {
      data.add(value);
   }
 
   public void draw(Graphics2D g2)
   {
      int i = 0;
      double max = 0;
 
      for (Double wrapper : data)
         if(max < wrapper)
            max = wrapper;
 
      int xwidth = width - 1;
      int yheight = height - 1;
 
      int xleft = 0;
 
      for (i = 0; i < data.size(); i++)
      {
         int xright = xwidth * (i + 1) / data.size();
         int barWidth = xwidth / data.size();
         int barHeight = (int) Math.round(yheight * data.get(i) / max);
 
         Rectangle bar =
            new Rectangle(xleft, yheight - barHeight,
               barWidth, barHeight);
         g2.draw(bar);
 
         xleft = xright;
      }
   }
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
 
      final int FRAME_WIDTH = 300;
      final int FRAME_HEIGHT = 400;
 
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setTitle("BarChart");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
      Bar component = new Bar();
      frame.add(component);
 
      frame.setVisible(true);
   }
}