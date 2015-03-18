package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

class Slice {
   double value;
   Color color;
   String name;
   public Slice(double value, Color color,String name) {  
      this.value = value;
      this.color = color;
      this.name=name;
   }
}
class MyComponent extends JComponent {
   Slice[] slices;

   MyComponent(int[]values,String[] names) {
	   slices=new Slice[values.length];
	   Color[] colors={Color.black,Color.green,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.MAGENTA,Color.ORANGE,Color.YELLOW,Color.WHITE,Color.RED,Color.PINK,Color.GRAY,Color.LIGHT_GRAY};
	   for(int i=0;i<values.length;i++){
		   slices[i]=new Slice(values[i],colors[i],names[i]);
	   }
		   
	   
   }
   public void paint(Graphics g) {
      drawPie((Graphics2D) g, getBounds(), slices);
   }
   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
      double total = 0.0D;
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].value;
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].value * 360 / total);
         Font titleFont = new Font("SansSerif", Font.BOLD, 20);
         FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
         g.setFont(titleFont);
         g.setColor(slices[i].color);
         g.drawString(slices[i].name,area.x,area.y);
         g.fillArc(area.x, area.y, area.width, area.height, 
         startAngle, arcAngle);
         curValue += slices[i].value;
      }
   }
}
public class PieChart {
  /* public static void main(String[] argv) {
      JFrame frame = new JFrame();
      frame.getContentPane().add(new MyComponent());
      frame.setSize(300, 200);
      frame.setVisible(true);
   }*/
}