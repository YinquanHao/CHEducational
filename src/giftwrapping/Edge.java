package giftwrapping;

public class Edge {
	private Point p1;
	private Point p2;
	public Edge(Point p1,Point p2){
		this.p1=p1;
		this.p2=p2;
	}
	public void printe(){
		System.out.println("from "+p1.getX()+","+p1.getY()+"to"+p2.getX()+","+p2.getY());
	}
	public Point getPoint1(){
		return p1;
	}
	public Point getPoint2(){
		return p2;
	}
}
