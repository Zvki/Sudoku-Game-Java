package pl.polsl.lab.bartosz.sosnica.sudoku.model;

public class PlayerModel {

    private String _username;
    public int score;

    public PlayerModel(String username){
        _username = username;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public String getUsername() {
        return _username;
    }

    public void setScore(int _score) {
        score = _score;
    }

    public int getScore() {
        return score;
    }

}
