package wg.application.entity;

import java.util.List;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/6/22 9:31
 * @version
 * @Copyright
 *************************************************************/
public class Movie {


    private String movieName;

    private String director;

    private List<String> performer;


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getPerformer() {
        return performer;
    }

    public void setPerformer(List<String> performer) {
        this.performer = performer;
    }
}
