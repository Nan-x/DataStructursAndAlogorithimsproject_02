//AUTHORS: Rhianna Paine (20076268) and CeriAnne Walsh (20076451)


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteFinder extends Application{
	
	static GraphNodeAL2<String> a = new GraphNodeAL2<>("Arklow");
	static GraphNodeAL2<String> b = new GraphNodeAL2<>("Woodenbridge");
	static GraphNodeAL2<String> c = new GraphNodeAL2<>("Templerainy");
	static GraphNodeAL2<String> d = new GraphNodeAL2<>("Ballynattin");
	static GraphNodeAL2<String> e = new GraphNodeAL2<>("Ballycoog");
	static GraphNodeAL2<String> f = new GraphNodeAL2<>("Kilcarra");
	static GraphNodeAL2<String> g = new GraphNodeAL2<>("Johnstown");
	static GraphNodeAL2<String> h = new GraphNodeAL2<>("Scarnagh");
	static GraphNodeAL2<String> i = new GraphNodeAL2<>("Ballyfad");
	static GraphNodeAL2<String> j = new GraphNodeAL2<>("Coolgreany");
	static GraphNodeAL2<String> k = new GraphNodeAL2<>("Inch");
	
	static GraphNodeAL2<?> source;
	static String dest;
	static float time;
	
	
	@Override
	public void start(Stage stage) throws Exception {

		//Create box
		BorderPane root = new BorderPane();
		//set size


		FileInputStream input = new FileInputStream("C:\\Users\\rhian\\Downloads\\RouteFinderAssignment2 (1)\\RouteFinderAssignment2\\MAP.png");
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		HBox hbox = new HBox(imageView);




		
		ComboBox sourceBox = new ComboBox();
		ComboBox destBox = new ComboBox();

		sourceBox.getItems().addAll("Arklow", "Woodenbridge", "Templerainy", "Ballynattin", "Ballycoog", "Kilcarra", "Johnstown", "Scarnagh", "Ballyfad", "Coolgreany", "Inch");
		sourceBox.setPromptText("Departure");
		destBox.getItems().addAll("Arklow", "Woodenbridge", "Templerainy", "Ballynattin", "Ballycoog", "Kilcarra", "Johnsstown", "Scarnagh", "Ballyfad", "Coolgreany", "Inch");
   		destBox.setPromptText("Destination");
		Button searchDepthFirstShowingTotalCost = new Button("Search Sum of Routes Length");
		Button searchDepthFirstCheapestPath = new Button("Search Shortest Path (DFS)");
		Button searchDijkstra = new Button("Search Shortest Path (Dijkstra)");
		Button findAllPaths = new Button("Find All Possible Routes");
        Button searchSpeed = new Button("Search Speed");

		
		
		searchDepthFirstShowingTotalCost.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doAction(sourceBox.getValue().toString());
				traverseGraphDepthFirstShowingTotalLength(source, null, 0);
			}

		});
		
		searchDepthFirstCheapestPath.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doAction(sourceBox.getValue().toString());
				dest = destBox.getValue().toString();
				SizedRoute sr = searchGraphDepthFirstShortesRoute(source, null, 0, dest);
				for(GraphNodeAL2<?> n : sr.pathList)
					System.out.println(n.data);
				System.out.println("the shortest route is: " + sr.routeLength / 1000 + "km");

			}

		});


		  findAllPaths.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doAction(sourceBox.getValue().toString());
				dest = destBox.getValue().toString();
				System.out.println("These are all the possible routes you could take");
				List<List<GraphNodeAL2<?>>> allPaths=findAllPaths(source, null, dest);
				int pCount = 1;
				for(List<GraphNodeAL2<?>> n : allPaths){
					System.out.println("\nRoute" + (pCount++) + "\n---------" );
					for(GraphNodeAL2<?> p : n)
						System.out.println((p.data));

				}


			}

		});


		searchSpeed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doAction(sourceBox.getValue().toString());
				dest = destBox.getValue().toString();
				SizedRoute sr = findFastesRouteDijkstra(source, dest);

				for(GraphNodeAL2<?> n : sr.pathList)
					System.out.println(n.data);

				System.out.println("This is the fastest route and is: " + sr.routeTime/10 + "minutes long");
			}

		});

		searchDijkstra.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doAction(sourceBox.getValue().toString());
				dest = destBox.getValue().toString();
				SizedRoute sr = findShortestRouteDijkstra(source, dest);
				for(GraphNodeAL2<?> n : sr.pathList)
					System.out.println(n.data);
				System.out.println("the shortest route is: " + sr.routeLength / 1000 + "km");
			}

		});



		VBox vbox = new VBox(searchDepthFirstCheapestPath, searchDijkstra, searchDepthFirstShowingTotalCost, findAllPaths, searchSpeed);
		Scene scene1 = new Scene(root,700,700);


		root.setTop(sourceBox);
		root.setBottom(destBox);
		root.setLeft(hbox);
		root.setRight(vbox);

		stage.setTitle("Route Finder");
		stage.setScene(scene1);
		stage.show();
	}

	private void doAction(String sourceList) {
		switch(sourceList) {
		case "Arklow" : source = a;
			break;
		case "Woodenbridge" : source = b;
			break;
		case "Templerainy" : source = c;
			break;
		case "Ballynattin" : source = d;
			break;
		case "Ballycoog" : source = e;
			break;
		case "Kilcarra" : source = f;
			break;
		case "Johnstown" : source = g;
			break;
		case "Scarnagh" : source = h;
			break;
		case "Ballyfad" : source = i;
			break;
		case "Coolgreany" : source = j;
			break;
		case "Inch" : source = k;
			break;
		}
		
	}

	public static void main(String[] args) {


		a.connectToNodeUndirected(b, 7700, 80, 7700/80);
		a.connectToNodeUndirected(c, 3900, 50, 3900/50);
		a.connectToNodeUndirected(d, 1800, 80, 1800/80);
		b.connectToNodeUndirected(e, 4000,60, 4400/60);
		b.connectToNodeUndirected(f, 4400,80, 4400/80);
		b.connectToNodeUndirected(i, 4700,100, 4700/100);
		d.connectToNodeUndirected(g, 2000,100, 2000/100);
		d.connectToNodeUndirected(h, 4800,100, 4800/100);
		f.connectToNodeUndirected(i, 2200, 100, 2200/100);
		g.connectToNodeUndirected(j, 3200,80, 3200/80);
		h.connectToNodeUndirected(k, 2800,80, 2800/80);
		j.connectToNodeUndirected(i, 5800,60, 5800/60);
		j.connectToNodeUndirected(k, 2900,60, 2900/60);
		
		launch(args);
				
	}
	
	public static <T> void traverseGraphDepthFirstShowingTotalLength(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, int totalDistance) {
		System.out.println(from.data + " (Total distance of reaching destination: " + totalDistance + ")");
		
		if(encountered == null) encountered = new ArrayList<>();
		encountered.add(from);
	
		Collections.sort(from.adjList, (x, y) -> x.distance - y.distance);
		
		for(GraphLinkAL adjLink : from.adjList)
			if(!encountered.contains(adjLink.destNode)) 
				traverseGraphDepthFirstShowingTotalLength(adjLink.destNode, encountered, totalDistance+adjLink.distance);
		
	}
	
	public static class SizedRoute {
		public int routeLength = 0;
		public int routeSpeedLimit = 0;
		public int routeTime = 0;
		public List<GraphNodeAL2<?>> pathList = new ArrayList<>();
	}
	
	public static <T> SizedRoute searchGraphDepthFirstShortesRoute(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, int totalDistance, T lookingfor) {
		if(from.data.equals(lookingfor)) {
			SizedRoute sr = new SizedRoute();
			sr.pathList.add(from);
			sr.routeLength = totalDistance;
			return sr;
		}
		
		if(encountered == null) encountered = new ArrayList<>();
		encountered.add(from);
		List<SizedRoute> allPaths = new ArrayList<>();
		
		for(GraphLinkAL adjLink : from.adjList)
			if(!encountered.contains(adjLink.destNode)) {
				SizedRoute temp = searchGraphDepthFirstShortesRoute(adjLink.destNode, encountered, totalDistance + adjLink.distance, lookingfor);
				if(temp == null) continue;
				temp.pathList.add(0, from);
				allPaths.add(temp);
			}
		return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1, p2) -> p1.routeLength - p2.routeLength);
	}
	
	public static <T> SizedRoute findShortestRouteDijkstra(GraphNodeAL2<?> startNode, T lookingfor) {
		SizedRoute sr = new SizedRoute();
		List<GraphNodeAL2<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
		startNode.nodeValue = 0;
		unencountered.add(startNode);
		GraphNodeAL2<?> currentNode;
		
		do {
			currentNode = unencountered.remove(0);
			encountered.add(currentNode);
			
			if(currentNode.data.equals(lookingfor)) {
				sr.pathList.add(currentNode);
				sr.routeLength = currentNode.nodeValue;
				
				while(currentNode != startNode) {
					boolean foundPrevPathNode = false;
					for(GraphNodeAL2<?> n : encountered) {
						for(GraphLinkAL e : n.adjList)
							if(e.destNode == currentNode && currentNode.nodeValue - e.distance == n.nodeValue) {
								sr.pathList.add(0,n);
								currentNode = n;
								foundPrevPathNode = true;
								break;
							}
						if(foundPrevPathNode) break;
					}
				}
				
				for(GraphNodeAL2<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
				for(GraphNodeAL2<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;
				
				return sr;
			}
			
			for(GraphLinkAL e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.distance);
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue);
		}while(!unencountered.isEmpty());
		return null;
	
	}

	public static <T> List<List<GraphNodeAL2<?>>> findAllPaths(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, T lookingfor){

		List<List<GraphNodeAL2<?>>> result = null, temp2;

		if (from.data.equals(lookingfor)) {
			List <GraphNodeAL2 <?>> temp = new ArrayList <>();
			temp.add(from);
			result = new ArrayList <>();
			result.add(temp);
			return result;
		} else {
			if (encountered == null) encountered = new ArrayList<>();
			encountered.add(from);

			for (GraphNodeAL2 <?> adjNode : from.adjList) {
				if (!encountered.contains(adjNode)) {
					temp2 = findAllPaths(adjNode, new ArrayList <>(encountered), lookingfor);

					if (temp2 != null) {
						for (List<GraphNodeAL2 <?>> x : temp2)
							x.add(0, from);
						if (result == null) result = temp2;
						else result.addAll(temp2);
					}
                    }
                }

			return result;
		}
	}
	public static <T> SizedRoute findFastesRouteDijkstra(GraphNodeAL2<?> startNode, T lookingfor) {
		SizedRoute sr = new SizedRoute();
		List<GraphNodeAL2<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
		startNode.nodeValue = 0;
		unencountered.add(startNode);
		GraphNodeAL2<?> currentNode;

		do {
			currentNode = unencountered.remove(0);
			encountered.add(currentNode);

			if(currentNode.data.equals(lookingfor)) {
				sr.pathList.add(currentNode);
				sr.routeTime = currentNode.nodeValue;

				while(currentNode != startNode) {
					boolean foundPrevPathNode = false;
					for(GraphNodeAL2<?> n : encountered) {
						for(GraphLinkAL e : n.adjList)
							if(e.destNode == currentNode && currentNode.nodeValue - e.time == n.nodeValue) {
								sr.pathList.add(0,n);
								currentNode = n;
								foundPrevPathNode = true;
								break;
							}
						if(foundPrevPathNode) break;
					}
				}

				for(GraphNodeAL2<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
				for(GraphNodeAL2<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;

				return sr;
			}

			for(GraphLinkAL e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.time);
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue);
		}while(!unencountered.isEmpty());
		return null;

	}



}

