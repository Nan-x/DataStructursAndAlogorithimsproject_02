
public class GraphLinkAL extends GraphNodeAL2<Object> {

	public GraphNodeAL2<?> destNode; 
	public int distance;
	public int speed;
	public int time;

	public GraphLinkAL(GraphNodeAL2<?> destNode, int distance, int speed, int time){
		super();

		this.destNode=destNode;
		this.distance = distance;
		this.speed = speed;
		this.time = time;

	}

}
