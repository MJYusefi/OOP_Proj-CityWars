package Model;

public class Card {
    public static int IdCounter;

    public int id;
    public String name;
    public int defAtt;
    public int dur;
    public int playDamage;
    public int upLevel;
    public int upCost;
    public int curlevel;
    public String imagePath ;

    public Card(int id, String name, int defAtt, int dur, int playDamage, int upLevel, int upCost) {
        this.id = id;
        this.name = name;
        this.defAtt = defAtt;
        this.dur = dur;
        this.playDamage = playDamage;
        this.upLevel = upLevel;
        this.upCost = upCost;
    }

}
