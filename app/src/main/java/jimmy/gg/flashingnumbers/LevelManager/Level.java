package jimmy.gg.flashingnumbers.LevelManager;



public class Level {

    private String level;
    private String numbers;
    private String time;
    private String difficult;

    public Level(String level, String numbers, String time,String difficult){
        this.level = level;
        this.numbers = numbers;
        this.time = time;
        this.difficult = difficult;
    }
    public String getDifficult(){
        return difficult;
    }

    public void setDifficult(String difficult){
        this.difficult = difficult;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String difficult) {
        this.numbers = difficult;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
