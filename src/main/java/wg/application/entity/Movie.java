package wg.application.entity;

import lombok.Data;

import java.util.List;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/6/22 9:31
 * @version
 * @Copyright
 *************************************************************/
@Data
public class Movie {


    private String movieName;

    private String director;

    private List<String> performer;




}
