package giftwrapping;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class IncrementAlgo {
    private Stack<Point> res =  new Stack<Point>();
    private LinkedList<Edge> el=new LinkedList<Edge>();
    private Point centroid;
    private GraphicsContext gc;
    private long sleeptime;
    String INIT="Randomly select the point % from point sets, draw two bucket edges";
    String Dominated="The point % is dominated";
    String Ndominated="The conflict edge of the point % is the red edge";
    String Remove="find the range of bucket edges after insert point %";
    String Insert="Insert new edges after insert point %";
    public IncrementAlgo(ArrayList<Point> input,GraphicsContext gc,ObservableList<Point> points,CanvasDraw cd,Giftwrapping gw, InitGui gui) throws InterruptedException{
		gc.clearRect(0, 0, 800, 600);
		cd.repaint(gc, points);
    	init(input);
    	gw.getSteps().add(new Step(gw.StringMod(INIT, Integer.toString(input.get(0).getIndex()))));
    	for(int i=1;i<input.size();i++){
    		for(int j=0;j<el.size();j++){
    			if(input.get(i).getAxisX()>el.get(j).getPoint1().getAxisX()&&input.get(i).getAxisX()<=el.get(j).getPoint2().getAxisX()){
    				if(input.get(i).getAxisY()>el.get(j).getPoint1().getAxisY()){
    					input.get(i).setConflictEdge(el.get(j));
    					//graphic.draw(gcin,input.get(i));
    				}
    			}
    		}
    	}
    	
    	
    	for(int i=1;i<input.size();i++){
    		int d1=0;//same y
    		int d2=0;//same x
    		if(gw.isFinish==false){
                drawLineSet(gc, cd);
            Thread.sleep((long)gui.getSlider().getValue());
                }
    		if(input.get(i).getConflictEdge()==null){
                    if(gw.isFinish==false){
                    cd.highlightVertice(gc, input.get(i));
                        Thread.sleep((long)gui.getSlider().getValue());}
            	gw.getSteps().add(new Step(gw.StringMod(Dominated, Integer.toString(input.get(i).getIndex()))));
    			System.out.println("dominated!");
    		}
    		else{
    			int startIndex=el.indexOf(input.get(i).getConflictEdge());
    			int endIndex=startIndex;
                        if(gw.isFinish==false){
                        cd.highlightVertice(gc, input.get(i));
	            Thread.sleep((long)gui.getSlider().getValue());
				cd.highlightLine(gc, el.get(endIndex).getPoint1(), el.get(endIndex).getPoint2());
	            Thread.sleep((long)gui.getSlider().getValue());}
	            if(gw.isPause==true){
	                gw.ie.diableWhilePause();
                        while(gw.isPause==true){
	                    Thread.sleep(200);
	                }
                        gw.ie.enableAfterPause();
	            }
	            gw.getSteps().add(new Step(gw.StringMod(Ndominated, Integer.toString(input.get(i).getIndex()))));
	            while(endIndex>0){
    				if(input.get(i).getAxisY()>el.get(endIndex-1).getPoint1().getAxisY()){
    		            //Thread.sleep((long)gui.getSlider().getValue());
    					//cd.highlightLine(gc, el.get(endIndex).getPoint1(), el.get(endIndex).getPoint2());
    					endIndex--;
    				}
    				else if(input.get(i).getAxisY()==el.get(endIndex-1).getPoint1().getAxisY()){
    					d1=1;
    					break;
    				}
    				else{
    					break;
    				}    				
    			}
	            if(input.get(i).getX()==el.get(startIndex).getPoint2().getX()){
	            	d2=1;
	            }
    			/*degenerate case*/
	            if(d1+d2!=0){
	            	if(d1==1&&d2==0){
	            		endIndex--;
	        			Point e1left=new Point(-1,el.get(endIndex).getPoint1().getX(),input.get(i).getY());
	        			Edge e1=new Edge(e1left,input.get(i));
	        			Point e2left=new  Point(-1,input.get(i).getX(),el.get(startIndex).getPoint1().getY());
	        			Edge e2=new Edge(e2left,el.get(startIndex).getPoint2());
	        			
	        			for(int j=1;j<input.size();j++){
	        				int eIndex=el.indexOf(input.get(j).getConflictEdge());//Index of the current conflict edge
	        				if((eIndex>=endIndex&&eIndex<startIndex)||
	        						(eIndex==startIndex&&input.get(j).getX()<=input.get(i).getX())){
	        					if(input.get(j).getAxisY()<=input.get(i).getAxisY()){
	            					input.get(j).setConflictEdge(null);
	        					}
	        					else{
	        						input.get(j).setConflictEdge(e1);
	        					}
	        				}
	        				else if(eIndex==startIndex&&input.get(j).getX()>input.get(i).getX()){
	        					input.get(j).setConflictEdge(e2);
	        				}
	        			}
	        			
	        			while(startIndex>=endIndex){
	        				el.remove(endIndex);
	        				startIndex--;
	        			}
	        			el.add(endIndex, e2);
	        			el.add(endIndex, e1);
	        			
	            	}
	            	else if(d1==0&&d2==1){
	        			Point e1left=new Point(-1,el.get(endIndex).getPoint1().getX(),input.get(i).getY());
	        			Edge e1=new Edge(e1left,input.get(i));
	        			
	        			for(int j=1;j<input.size();j++){
	        				int eIndex=el.indexOf(input.get(j).getConflictEdge());//Index of the current conflict edge
	        				if((eIndex>=endIndex&&eIndex<startIndex)||
	        						(eIndex==startIndex&&input.get(j).getX()<=input.get(i).getX())){
	        					if(input.get(j).getAxisY()<=input.get(i).getAxisY()){
	            					input.get(j).setConflictEdge(null);
	        					}
	        					else{
	        						input.get(j).setConflictEdge(e1);
	        					}
	        				}
	        			}
	        			while(startIndex>=endIndex){
	        				el.remove(endIndex);
	        				startIndex--;
	        			}
	        			el.add(endIndex, e1);	        			
	            	}
	            	else{
	            		endIndex--;
	        			Point e1left=new Point(-1,el.get(endIndex).getPoint1().getX(),input.get(i).getY());
	        			Edge e1=new Edge(e1left,input.get(i));
	        			
	        			for(int j=1;j<input.size();j++){
	        				
	        				int eIndex=el.indexOf(input.get(j).getConflictEdge());//Index of the current conflict edge
	        				if((eIndex>=endIndex&&eIndex<startIndex)||
	        						(eIndex==startIndex&&input.get(j).getX()<=input.get(i).getX())){
	        					if(input.get(j).getAxisY()<=input.get(i).getAxisY()){
	            					input.get(j).setConflictEdge(null);
	        					}
	        					else{
	        						input.get(j).setConflictEdge(e1);
	        					}
	        				}
	        			}
	        			
	        			while(startIndex>=endIndex){
	        				el.remove(endIndex);
	        				startIndex--;
	        			}
	        			el.add(endIndex, e1);	
	            	}
	            	
	            	}
	            /**/
	            
	            else{
    			//the insert of the point leads to 2 edges
    			System.out.println(endIndex);
    			Point e1left=new Point(-1,el.get(endIndex).getPoint1().getX(),input.get(i).getY());
    			Edge e1=new Edge(e1left,input.get(i));
    			Point e2left=new  Point(-1,input.get(i).getX(),el.get(startIndex).getPoint1().getY());
    			Edge e2=new Edge(e2left,el.get(startIndex).getPoint2());
    	        if(gw.isFinish==false){
                        Thread.sleep((long)gui.getSlider().getValue());
    			cd.drawGreenLine(gc, e1left,input.get(i));
    	        Thread.sleep((long)gui.getSlider().getValue());
    			cd.drawGreenLine(gc, input.get(i),e2left);
    	        Thread.sleep((long)gui.getSlider().getValue());}
    			//reassign the points between start-end index
    	    	gw.getSteps().add(new Step(gw.StringMod(Remove, Integer.toString(input.get(i).getIndex()))));
    			for(int j=1;j<input.size();j++){
    	            if(gw.isPause==true){
    	                                    gw.ie.diableWhilePause();
                        while(gw.isPause==true){
    	                    Thread.sleep(200);
    	                }
                        gw.ie.enableAfterPause();
    	            }
    				int eIndex=el.indexOf(input.get(j).getConflictEdge());//Index of the current conflict edge
    				if((eIndex>=endIndex&&eIndex<startIndex)||
    						(eIndex==startIndex&&input.get(j).getX()<=input.get(i).getX())){
    					if(input.get(j).getAxisY()<=input.get(i).getAxisY()){
        					input.get(j).setConflictEdge(null);
    					}
    					else{
    						input.get(j).setConflictEdge(e1);
    					}
    				}
    				else if(eIndex==startIndex&&input.get(j).getX()>input.get(i).getX()){
    					input.get(j).setConflictEdge(e2);
    				}
    			}
				

				
    			while(startIndex>=endIndex){
    				el.remove(endIndex);
    				startIndex--;
    			}
    			el.add(endIndex, e2);
    			el.add(endIndex, e1);
    	        if(gw.isFinish==false){
                        Thread.sleep((long)gui.getSlider().getValue());
        		gc.clearRect(0, 0, 800, 600);
    			cd.repaint(gc, points);
        		//drawLineSet(gc, cd);
        		cd.highlightVertice(gc, input.get(i));
    			cd.drawBlackLine(gc, e1.getPoint1(),e1.getPoint2());
    	        Thread.sleep((long)gui.getSlider().getValue());
    			cd.drawBlackLine(gc,e2.getPoint1(),e2.getPoint2());
    	        Thread.sleep((long)gui.getSlider().getValue());}
    	    	gw.getSteps().add(new Step(gw.StringMod(Insert, Integer.toString(input.get(i).getIndex()))));   			
    			//System.out.println(endIndex);
    		}}
            if(gw.isPause==true){
                                    gw.ie.diableWhilePause();
                while(gw.isPause==true){
                    Thread.sleep(200);
                }
                gw.ie.enableAfterPause();
            }
    	if(gw.isFinish==false){	
            gc.clearRect(0, 0, 800, 600);
			cd.repaint(gc, points);}
    	}
        if(gw.isFinish==false){
        Thread.sleep((long)gui.getSlider().getValue());}
    	//show result
                    gc.clearRect(0, 0, 800, 600);
			cd.repaint(gc, points);
    	for(int j=0;j<el.size()-1;j++){
    		cd.highlightResult(gc, el.get(j).getPoint2());
    	}
        gw.ie.enableAfterRuning();
    }
    
    public void drawLineSet(GraphicsContext gc,CanvasDraw cd){
    	for(int i=0;i<el.size();i++){
    		 try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		cd.drawLine(gc, el.get(i).getPoint1(), el.get(i).getPoint2());
    	}
    }
    
    
    public void init(ArrayList<Point> input){
    	Collections.shuffle(input);
    	Point left=new Point(-1,0,input.get(0).getY());
    	Point right=new Point(-1,800,590);
    	Point temp=new Point(-1,input.get(0).getX(),590);
    	Edge e1=new Edge(left,input.get(0));
    	el.add(e1);
    	Edge e2=new Edge(temp,right);
    	el.add(e2);
    }
    
    public LinkedList<Edge> getel(){
    	return el;
    }
    
}
