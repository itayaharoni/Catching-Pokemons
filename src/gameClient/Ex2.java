package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import static java.lang.Thread.sleep;
/**
 * This class is an executable main that runs the pokemon game.
 */

public class Ex2 implements Runnable {
    private static MyArena ar;
    private static MyGui win;
    private static MyLabel label;
    private static String[] arg;

    public static void main(String[] args) {
        if (args.length == 0) {
            Thread gui = new Thread( new Runnable() {
                @Override
                public void run() {
                    label = new MyLabel();
                }
            });
            gui.run();
        } else {
            Thread client = new Thread(new Ex2());
            client.start();
            arg = args;
        }
    }

    @Override
    public void run() {
        game_service game;
        if (label != null) {
            int scenario_num = label.getLevel();
            game = Game_Server_Ex2.getServer(scenario_num);
            game.login(label.getId());
        } else {
            long id = 0;
            int level = 0;
            try {
                id = Long.parseLong(arg[0]);
                level = Integer.parseInt(arg[1]);
            } catch (Exception error) {
                error.printStackTrace();
            }
            game = Game_Server_Ex2.getServer(level);
            game.login(id);
        }
        ar = new MyArena();
        DWGraph_Algo graph_algo = new DWGraph_Algo();
        graph_algo.load2(game.getGraph());
        ar.setGraph(graph_algo.getGraph());
        ar.setPokemons(MyArena.json2Pokemons(game.getPokemons()));
        List<List<node_data>> Components = graph_algo.GraphComponents();
        if (Components.size() == 1) {
            init(game, graph_algo);
        } else {
            multiInit(game, graph_algo, Components);
        }
        game.startGame();
        long dt2 = 60000;
        while (game.isRunning()) {
            win.repaint();
            ar.updatePokemon(game.getPokemons());
            ar.updateAgents(game.getAgents());
            MyAgent Ash = readyToCatch(ar.getAgents());
            if (Ash != null) {
                try {
                    Ash.set_sg_dt(timeFromPokemon(graph_algo.getGraph(), Ash.get_curr_fruit().get_pos(), Ash.get_curr_fruit().get_edge(), Ash.getSpeed()));
                    ar.updateTime(Ash);
                    dt2 = game.timeToEnd() - Ash.getTimeForNext();
                    game.chooseNextEdge(Ash.getID(), Ash.get_curr_fruit().get_edge().getDest());
                    Ash.setDest(Ash.get_curr_fruit().get_edge().getDest());
                    double val = Ash.getValue();
                    if (Ash.get_sg_dt() > 0) {
                        long tt = (game.timeToEnd() - Ash.get_sg_dt());
                        if (Ash.get_sg_dt() - 20 > 0) sleep(Ash.get_sg_dt() - 20);
                        while (tt < game.timeToEnd() && game.isRunning()) {
                            win.repaint();
                            game.move();
                            sleep(5);
                            ar.updateAgents(game.getAgents());
                            if (Ash.getValue() > val) {
                                ar.removePoke(Ash.get_curr_fruit().getKey());
                                break;
                            }
                        }
                        if (Ash.getValue() <= val) {
                            Ash.get_curr_fruit().setChase(-1);
                        }
                    }
                    if (Ash.get_sg_dt() == 0) game.move();
                    ar.updateTime(game.timeToEnd());
                    long dt3 = game.timeToEnd() - Ash.getTimeForNext();
                    if (dt3 > 0) {
                        sleep(dt3);
                        game.move();
                        win.repaint();
                    }
                    ar.updatePokemon(game.getPokemons());
                    ar.updateAgents(game.getAgents());
                    chooseNextPoke(Ash);
                    if (readyToCatch(ar.getAgents()) != null && game.isRunning()) continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ar.updatePokemon(game.getPokemons());
            ar.updateAgents(game.getAgents());
            MyAgent Misty;
            do {
                Misty = isAgentStuck(game);
                if (Misty != null) {
                    moveAllAgents(game);
                }
            } while (Misty != null && game.isRunning());
            if (readyToCatch(ar.getAgents()) != null && game.isRunning()) continue;
            ar.updateAgents(game.getAgents());
            ar.updateTime(game.timeToEnd());
            long dt = game.timeToEnd() - ar.getMinTime();
            try {
                if (dt > 0) sleep(dt);
                game.move();
                win.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(game);
        win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
    }
    /**
     * This method returns an agent that is stuck on it's destination node
     * and has no new destination. Returns null if none.
     * @param game
     * @return a
     */
    public static MyAgent isAgentStuck(game_service game) {
        ar.updateAgents(game.getAgents());
        for (MyAgent a : ar.getAgents()) {
            if (a.getDest() == -1 && a.getSrc() != a.get_curr_edge().getSrc()) return a;
        }
        return null;
    }
    /**
     * This method sets a new destination node to the received agent.
     * @param game
     * @param a
     */
    public static void chooseNextNode(MyAgent a, game_service game) {
        List<node_data> path = a.getPath();
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getKey() == a.getSrc()) {
                a.setDest(path.get(i + 1).getKey());
                game.chooseNextEdge(a.getID(), a.getDest());
                game.move();
                break;
            }
        }
    }
    /**
     * This method swaps the i and j index in the received MyPokemon array - arr.
     * @param i
     * @param j
     * @param arr
     */
    public static void swap(int i, int j, MyPokemon arr[]) {
        MyPokemon temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    /**
     * This method sorts the received MyPokemon array - arr, according to the pokemon
     * values in a descending order.
     * @param arr
     */
    public static void sortPoke(MyPokemon arr[]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j].getValue() < arr[j + 1].getValue()) swap(j, j + 1, arr);
            }
        }
    }
    /**
     * This method initializes this class objects according to the game graph and info, this method is called
     * iff the graph of the game is not strongly connected.
     * @param game
     * @param graph_algo
     * @param Components
     */
    public static void multiInit(game_service game, dw_graph_algorithms graph_algo, List<List<node_data>> Components) {
        MyPokemon arr[] = new MyPokemon[ar.getPokemons().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ar.getPokemons().get(i);
        }
        sortPoke(arr);
        for (int i = 0; i < arr.length; i++) {
            MyArena.updateEdge(arr[i], ar.getGraph());
        }
        sortMegaList(Components);
        for (int i = 0; i < Components.size(); i++) {
            game.addAgent(Components.get(i).get(0).getKey());
        }
        int numOfAgents = numOfAgents(game);
        if(numOfAgents>Components.size()){
            int counter=numOfAgents-Components.size();
            for (int i = 0; i < numOfAgents-Components.size(); i++) {
                for (int j = 0; j < Components.size(); j++) {
                    for (int k = 1; k < Components.get(j).size(); k++) {
                        game.addAgent(Components.get(j).get(k).getKey());
                        counter--;
                        if (counter==0) break;
                    }
                    if (counter==0) break;
                }
                if (counter==0) break;
            }
        }
        ar.setAgents(MyArena.getAgents(game.getAgents(), ar.getGraph()));
        for (MyAgent a : ar.getAgents()) {
            for (MyPokemon p : ar.getPokemons()) {
                if (p.get_edge().getSrc() == a.getSrc() && p.getChase() == -1) {
                    gottaCatchHim(p, a, graph_algo);
                    a.setDest(a.getSrc());
                    break;
                }
            }
        }
        win = new MyGui("Pokemon Game");
        win.update(ar, game);
    }
    /**
     * This method sorts list of lists according to the number of
     * nodes contained in each list, the sort is in a descending order.
     * @param Components
     */
    public static void sortMegaList(List<List<node_data>> Components){
        for (int i = 0; i < Components.size(); i++) {
            for (int j = 0; j < Components.size()-1; j++) {
                if(Components.get(j).size()<Components.get(j+1).size()) {
                    List<node_data> temp=Components.get(j);
                    Components.set(j,Components.get(j+1));
                    Components.set(j+1,temp);
                }
            }
        }
    }
    /**
     * This method initializes this class objects according to the game graph and info, this method is called
     * iff the graph of the game is strongly connected.
     * @param game
     * @param graph_algo
     */
    public static void init(game_service game, dw_graph_algorithms graph_algo) {
        MyPokemon arr[] = new MyPokemon[ar.getPokemons().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ar.getPokemons().get(i);
        }
        sortPoke(arr);
        for (int i = 0; i < arr.length; i++) {
            MyArena.updateEdge(arr[i], ar.getGraph());
        }
        for (int i = 0; i < arr.length; i++) {
            game.addAgent(arr[i].get_edge().getSrc());
        }
        int numOfAgents = numOfAgents(game);
        if (ar.getPokemons().size() < numOfAgents) {
            int counter=numOfAgents - ar.getPokemons().size();
            for (int i = 0; i < numOfAgents - ar.getPokemons().size(); i++) {
                Iterator<node_data> itr = graph_algo.getGraph().getV().iterator();
                while (itr.hasNext()) {
                    node_data n = itr.next();
                    game.addAgent(n.getKey());
                    counter--;
                    if (counter==0) break;
                }
                if (counter==0) break;
            }
        }
        ar.setAgents(MyArena.getAgents(game.getAgents(), ar.getGraph()));
        for (MyAgent a : ar.getAgents()) {
            for (MyPokemon p : ar.getPokemons()) {
                if (p.get_edge().getSrc() == a.getSrc() && p.getChase() == -1) {
                    gottaCatchHim(p, a, graph_algo);
                    a.setDest(a.getSrc());
                    break;
                }
            }
        }
        win = new MyGui("Pokemon Game");
        win.update(ar, game);
    }
    /**
     * This method sets the next destination nodes to all the agents
     * that are currently not moving.
     * @param game
     */
    public static void moveAllAgents(game_service game) {
        for (MyAgent a : ar.getAgents()) {
            if (a.getDest() == -1) {
                List<node_data> path = a.getPath();
                for (int i = 0; i < path.size(); i++) {
                    if (a.getSrc() == path.get(i).getKey() && a.getSrc() != a.get_curr_edge().getSrc()) {
                        a.setDest(path.get(i + 1).getKey());
                        game.chooseNextEdge(a.getID(), a.getDest());
                    }
                }
            }
        }
    }
    /**
     * This method returns the number of agents which should be added in order
     * to start the game.
     * @param game
     * @return num
     */
    public static int numOfAgents(game_service game) {
        int num = 0;
        String s = game.toString();
        try {
            JSONObject gameJson = new JSONObject(s);
            JSONObject G = gameJson.getJSONObject("GameServer");
            num = G.getInt("agents");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return num;
    }
    /**
     * This method chooses the pokemon with the highest value (which is not being chases by any other agent)
     * to the received agent.
     * @param a
     */
    public static void chooseNextPokeMax(MyAgent a) {
        DWGraph_Algo ga = new DWGraph_Algo();
        MyPokemon chosenOne = null;
        double currVal = -1;
        double maxVal = -1;
        ga.init(ar.getGraph());
        for (MyPokemon p : ar.getPokemons()) {
            if (p.getChase() == -1) {
                currVal = p.getValue();
                if (currVal > maxVal) {
                    maxVal = currVal;
                    chosenOne = p;
                }
            }
        }
        gottaCatchHim(chosenOne, a, ga);
    }
    /**
     * This method chooses the closest pokemon (which is not being chases by any other agent)
     * to the received agent.
     * @param agent
     */
    public static void chooseNextPoke(MyAgent agent) {
        DWGraph_Algo ga = new DWGraph_Algo();
        MyPokemon chosenOne = null;
        double minDist = Double.MAX_VALUE;
        double currDist = 0;
        ga.init(ar.getGraph());
        for (MyPokemon p : ar.getPokemons()) {
            if (p.getChase() == -1) {
                currDist = ga.shortestPathDist(agent.getSrc(), p.get_edge().getSrc());
                if (currDist != -1) {
                    if (currDist < minDist) {
                        minDist = currDist;
                        chosenOne = p;
                    }
                }
            }
        }
        gottaCatchHim(chosenOne, agent, ga);
    }
    /**
     * This method receives a pokemon and an agent and sets their parameters
     * so that the agent will chase the pokemon.
     * @param a
     * @param p
     * @param ga
     */
    public static void gottaCatchHim(MyPokemon p, MyAgent a, dw_graph_algorithms ga) {
        a.setCurrNode(a.getSrc());
        a.set_curr_fruit(p);
        a.set_gg(ga.getGraph());
        a.set_curr_edge(p.get_edge());
        a.set_sg_dt(timeFromPokemon(ga.getGraph(), p.get_pos(), p.get_edge(), a.getSpeed()));
        p.setChase(0);
        a.setPath(ga.shortestPath(a.getSrc(), p.get_edge().getSrc()));
    }
    /**
     * This method return an agent that is on the source node of the
     * edge of the pokemon he's chasing. Returns null if none.
     * @param list
     * @return a
     */
    public static MyAgent readyToCatch(List<MyAgent> list) {
        for (MyAgent a : list) {
            if (a.getSrc() == a.get_curr_edge().getSrc()) return a;
        }
        return null;
    }
    /**
     * This method receives a pokemon and an agent and return the needed time
     * for the agent to reach the pokemon.
     * @param g
     * @param p_pos
     * @param speed
     * @param p_edge
     * @return dt
     */
    public static long timeFromPokemon(directed_weighted_graph g, geo_location p_pos, edge_data p_edge, double speed) {
        long dt = 0;
        double distNodes = g.getNode(p_edge.getSrc()).getLocation().distance(g.getNode(p_edge.getDest()).getLocation());
        double dist = g.getNode(p_edge.getSrc()).getLocation().distance(p_pos);
        double ratio = dist / distNodes;
        double ratioOnEdge = ratio * p_edge.getWeight();
        dt = (long) ((ratioOnEdge / speed) * 1000);
        return dt;
    }
}
