package Assets;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class Player {

	private int x, y, width, height;
	private Image image;
	private double xvel, yvel;
	private boolean onSurface;
	
	private Rectangle hitbox;
	
	public Player(int xcord, int ycord, int w, int h) {
		image = new ImageIcon("img\\Witch.png").getImage();
		x = xcord;
		y = ycord;
		width = w;
		height = h;
		hitbox = new Rectangle(x, y, w, h);
		
		xvel = 0;
		yvel = 0;
		onSurface = false;
	}
	
	public void act(Platform[] platforms) {
		xvel *= 0.95;
		
		if(yvel < 10)
			yvel += 0.5;
		
		onSurface = false;
		
		for(Platform p : platforms) {
			boolean temp = true;
			Rectangle pHitbox = p.getHitbox();
			
			if ((y + height) < (pHitbox.getY() - 5) || (pHitbox.getY() < (y + height))) 
				temp = false;
			else if ((x + width < pHitbox.getX()) ||(pHitbox.getX() + pHitbox.getWidth() < x))
				temp = false;
			
			if(temp) {
				onSurface = true;
				yvel = 0;
			}
		}
		
		moveBy((int)xvel, (int)yvel);
	}
	
	public void jump() {
		if(onSurface) {
			yvel = -15;
			moveBy(0, (int)yvel);
		}
	}
	
	public void move(int direction) {
		if(Math.abs(xvel) < 10)
			xvel += direction;
	}
	
	public void moveBy(int xval, int yval) {
		x += xval;
		y += yval;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public boolean checkForOrb(Rectangle orbHitbox) {
		if(hitbox.intersects(orbHitbox))
			return true;
		return false;
	}
	
	public void draw(Graphics g, ImageObserver obs) {
		g.drawImage(image, x, y, width, height, obs);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}