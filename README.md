# Ex2
## Introduction

Structure of the project:

data directory - contains 6 files, of a directed weighted graphs in json format. Those graphs are used as maps in the pokemon game.

doc directory - contains javadoc files of the project.

libs directory - contains all the libraries necessary to the project. 

test directory - contains tests for most of the classes in the project.

src directory - contains the classes and interfaces of the project.

Ex2.jar file - an executable file that runs the pokemon game.

This project consists of two parts:

1) The api package inside the src package contains interfaces and their implementations of a direceted weighted graph and it's methods.
 This package contains implementations of vertices and edges in a direceted weighted graph and some more complicated implementations of
 simple methods applicable on the same graph and even more complicated such as calculating the shortest path between vertices, the package
 also contains a simple implementation of a 3D Point class that will be relevant in the next part of the project.
 
 2) In this part of the project we will introduce a simple solution to the pokemon game. 
 The gameClient package inside the src package contains multiple classes, the classes are divided to two:
 - First is the gui of the game, responsible to show to the user the process of the game, displaying a login screen ,updated results
 of the agents chasing the pokemons and the time left for the game.
 - The main code of the game, responsible to move the agents according to the location of the pokemons and use the move() command 
 to catch the pokemons when the agents are close enough.
 
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Table of contents

Part 1:

1) NodeData  

2) EdgeData 

3) GeoLocation 

4) DWGraph_DS

5) DWGraph_Algo


Part 2:

6) Point 3D

7) Range

8) Range2D

9) Range2Range

10) MyAgent

11) MyPokemon

12) MyArena

13) MyLabel & MyLabelError

14) MyGui & MyPanel

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Part 1

### NodeData class - implements the node_data interface
This class represents a single vertex in a weighted directed graph and the set of methods applicable on it. Each vertex has a unique integer key 
that differentiate it from other vertices. Also, each vertex contains a string to meta-data, 3D point object that represents it's location, a tag
object which is used in more advanced algorithms and weight.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### EdgeData class - implements the edge_data interface
This class represents a single edge in a weighted directed graph and the set of methods applicable on it. The edge is characterized by the key of it's source vertex (or node)
and by the key of it's destination vertex (or dest node). Each edge contains a weight parameter that represents the weight of the edge between the src and dest node in the 
grapg. Also, the edge has a tag parameter to be used in advanced algorithms and string parameter for meta-data.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### GeoLocation class - implements the geo_location interface
This class represents a 3D Point in space, each instance contains 3 double parameter - x, y and z. The <x,y,z> parameters represent coordinates in space. This class
will be useful for the second part of the project, in order to measure distance between two objects- we need to know their respective locations.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### DWGraph_DS class - implements the directed_weighted_graph interface
This class represents a directed weighted graph, this class uses the EdgeData and NodeData classes to construct a graph. The methods applicable on the graph
are quite simple, such as: adding vertices, connecting two vertices by an edge, removing edge or a vertex and retrieving the graph vertices or a specific vertex 
group of edges, and few more simple methods which are discussed in length in the wike page.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### DWGraph_Algo - implements the dw_graph_algorithms interface
This class contains a few more complicated algorithms applicable on a directed weighted graph (the DWGraph_DS class), here are some methods:
- Copy - created a deep copy of the underlying graph this class works on.
- isConnected - checks if the graph is strongly connected - or in other words if every vertex can reach every vertex. 
- shortPath/shortPathDist - receives a source vertex and a destination vertex, iterates through the graph and retrieves the shortest path/distance between
the said vertices.
- GraphComponents-  returns the number of connectivity components in the graph.

In case you are interested or doesn't fully understand the mentioned above methods, your are more then welcome to visit the wiki page which explains in detail every method!

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Part 2

### Point 3D
This class is a simple class that represents a point in space, similar to the GeoLocation class. Used to determine the pokemon and agent location in the game and move
the agent accordingly in order to catch the pokemon.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Range
As the name suggests, this class represents a range in a 1-dimension space. It contains 2 field of real numbers which determine the min value of the axis and the max value.
This class is used to rescale a point in a given dimension to a limited screen size.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Range2D
Like the Range class, this class consists of two Range objects and represents a 2D space. Each of the Range objects represent a single axis, the x and y Range objects 
represent the x and y axis respectively. This class is used to rescale a point in a given dimension to a limited screen size.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Range2Range
This class is mainly responsibly to rescale a given 3D Point in space to a 2D point on screen. It consists of two Range2D objects, the first object scale is converted into
the second object scale. 

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### MyAgent
This class represents an agent in the game. The agent instance consists of a 3D Point that represents it's location, src and dest fields that represent the vertices on which
the agent is now traveling between, the pokemon the agent is chasing and a few more objects needed for the methods of the game.


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### MyPokemon
This class represents a pokemon in the game. Like the agent it consists of a 3D Point representing it's location on the graph (arena of the game), the edge on which the pokemon 
is on, a type field to determine if the pokemon is on an edge connecting a vertices with keys in an ascending or descending order and value of each pokemon.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### MyArena
This class represents the arena on which the game is occurring. The arena consists of a list of the agents currently in the game, a list of the updated pokemons and the graph
of the game. The arena constantly changes according to the game and the situations of the agents and the pokemons. By using different methods on the arena instance we can
order the agents to catch the pokemons and win the game.


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### MyLabel & MyLabelError
This classes are responsible for the login screen, in order to start the game the user needs to insert his ID and level of the game he desire to play. In the case the user
enters invalid values an error message pops to let the user know. The game cannot start until the user inserts valid ID and level. After the user inserts valid ID and level 
the login screen closes and the game screen automatically pops.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### MyGui & MyPanel
This classes are responsible for displaying the game screen. The screen displays an updated situation of the game, the gui object getting it's information from the arena
object, that way it how to draw the graph, the current location of agents and status of the pokemons. In addition the screen displays the time left for the game and current
information about the agent values in the game.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
This is a result table of our code, if you think you can do better then you are more then welcome to try!

![ResultTable](https://user-images.githubusercontent.com/74153075/102724205-add9b680-4316-11eb-93de-95bc189ec732.jpeg)


In case you are intersted in more detailed information about the project, and the implementation of the mentioned above method you can enter our wiki page.
You can always contact us for more information or updates through our github profile, we hope this page was helpful to you!



