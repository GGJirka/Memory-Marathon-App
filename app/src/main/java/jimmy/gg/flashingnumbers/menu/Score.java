package jimmy.gg.flashingnumbers.menu;


public class Score  {
    private String level;
    private String highScore;

    public Score(String level, String highScore){
        this.level = level;
        this.highScore = highScore;
    }
    public void setHighScore(String highScore){
        this.highScore = highScore;
    }
    public String getHighScore(){
        return highScore;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
