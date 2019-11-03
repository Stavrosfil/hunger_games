package hungerGames;

class Weapon {
    int id; //Weapon's
    int x,y;
    int playerId; //Who the weapon belongs to
    String type;

    //Constructors

    public Weapon () {
        
    }

    public Weapon (int id, int x, int y, int pId, String t) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.playerId = pId;
        this.type = t;
    }
    
    public Weapon (Weapon weapon) {
        this.id = weapon.id;
        this.x = weapon.x;
        this.y = weapon.y;
        this.playerId = weapon.playerId;
        this.type = weapon.type;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;    
    }
   
    public void setY(int y) {
        this.y = y;
    }

    public void setPlayerId(int pId) {
        this.playerId = pId;
    }

    public void setType(String t) {
        this.type = t;
    }

    //Getters

    public int getId (){
        return this.id;
    }
    
    public int getX (){
        return this.x;
    }

    public int getY (){
        return this.y;
    }

    public int getPlayerId (){
        return this.playerId;
    }

    public String getType (){
        return this.type;
    }


}