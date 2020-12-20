package gameClient;

import api.edge_data;
import com.google.gson.Gson;
import gameClient.util.Point3D;

/**
 * This class represents a pokemon in the game.
 */
public class MyPokemon {
    private edge_data _edge;
    private double _value;
    private int _type;
    private Point3D _pos;
    private int chase;
    private static int keyGenerator;
    private int key;
    /**
     * This method allows changing the chase parameter of this instance.
     * @param chase
     */
    public void setChase(int chase) {
        this.chase = chase;
    }
    /**
     * This method returns the location of this pokemon.
     * @return location of this pokemon
     */
    public Point3D get_pos() {
        return _pos;
    }
    /**
     * This method returns the chase parameter of this pokemon.
     * @return chase
     */
    public int getChase() {
        return chase;
    }
    /**
     * Constructor for this class, initializes a new instance with the location p,
     * the value v,  the edge e and the type t. Initializes default parameters to the rest of the fields.
     * @param p
     * @param t
     * @param v
     * @param e
     */
    public MyPokemon(Point3D p, int t, double v, edge_data e) {
        key=keyGenerator++;
        chase=-1;
        _type = t;
        _value = v;
        set_edge(e);
        _pos = p;
    }
    /**
     * This method returns the key object of this pokemon.
     * @return key
     */
    public int getKey() {
        return key;
    }
    /**
     * This method returns true iff this pokemon and the pokemon o has the same location.
     * @param o
     * @return true iff the pokemons has the same location.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPokemon myPokemon = (MyPokemon) o;
        return myPokemon.getLocation().equals(_pos);
    }
    /**
     * This method returns a string representing this pokemon in a json format.
     * @return  a string representing this pokemon in a json format
     */
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns the edge of the graph this pokemon is located on.
     * @return  _edge
     */
    public edge_data get_edge() {
        return _edge;
    }
    /**
     * This method allows changing the edge this pokemon is located on.
     * @param  _edge
     */
    public void set_edge(edge_data _edge) {
        this._edge = _edge;
    }
    /**
     * This method returns the location of this pokemon in a 3D Point.
     * @return  _pos
     */
    public Point3D getLocation() {
        return _pos;
    }
    /**
     * This method returns the type of this pokemon.
     * @return  _type
     */
    public int getType() {return _type;}
    /**
     * This method returns the value of this pokemon.
     * @return _value
     */
    public double getValue() {return _value;}

}
