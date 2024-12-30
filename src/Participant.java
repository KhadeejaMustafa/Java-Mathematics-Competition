public class Participant {

    private int id;
    private String name;
    private int score;

    // constructors
    public Participant() {
    }

    public Participant(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Participant(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // setter and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
} // end of Participant class
