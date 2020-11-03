package wg.application.service.impl;

import org.neo4j.driver.v1.*;
import org.springframework.stereotype.Service;
import wg.application.entity.Movie;
import wg.application.service.MovieInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.neo4j.driver.v1.Values.parameters;

/*************************************************************
 * @Package wg.application.service.impl
 * @author wg
 * @date 2020/6/22 9:34
 * @version
 * @Copyright
 *************************************************************/
@Service
public class MovieImpl implements MovieInterface {


    private Driver createDriver() {
        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("wg-0608", "123456"));
    }


    @Override
    public void testNeo4j(HttpServletRequest request, HttpServletResponse response) {

        try {
            Driver driver = createDriver();
            System.out.println("><><><><>");

            Session session = driver.session();

            session.run("CREATE (a:Person {name: {name}, title: {title}})",
              parameters("name", "刘德华", "title", "King001"));

            StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
                "RETURN a.name AS name, a.title AS title",
              parameters("name", "刘德华"));

            System.out.println("/////////////////");

            while (result.hasNext()) {
                Record record = result.next();
                System.out.println(record.get("title").asString() + " " + record.get("name").asString() + " " + record.get("id").asString());
            }

            System.out.println("\\\\\\\\\\\\\\\\\\\\");

            session.close();
            driver.close();

        } catch (Exception e) {

        }

    }

}
