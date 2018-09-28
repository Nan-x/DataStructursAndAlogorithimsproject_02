import java.util.ArrayList;
import java.util.List;


public class GraphNodeAL2<T> {

	public T data;
	public int nodeValue = Integer.MAX_VALUE;
	public List<GraphLinkAL> adjList=new ArrayList<>();



	public GraphNodeAL2(T data) {
		this.data=data;
	}

	public GraphNodeAL2() {

	}


	public void connectToNodeDirected(GraphNodeAL2<T> destNode,int distance, int speed, int time) {
		adjList.add( new GraphLinkAL(destNode,distance, speed,  time) );
	}
	
	public void connectToNodeUndirected(GraphNodeAL2<T> destNode,int distance, int speed, int time) {
		adjList.add( new GraphLinkAL(destNode,distance, speed, time) );
		destNode.adjList.add( new GraphLinkAL(this,distance, speed, time) );
	}
}
