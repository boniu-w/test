package wg.application.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description: 推荐算法
 * @params:
 * @return:
 * @createTime: 16:41  2022/6/7
 * @updateTime: 16:41  2022/6/7
 ************************************************************************/
@RestController
@Slf4j
@RequestMapping("/")
public class MahoutController {

    @GetMapping
    public List<RecommendedItem> mahout(@RequestParam String userId, @RequestParam int type) {
        //数据模型
        DataModel model = null;
        List<RecommendedItem> list = Lists.newArrayList();
        File cvsFile = null;
        try {
            long startTime = System.currentTimeMillis();
//            File bookCsvFile = ResourceUtils.getFile("classpath:csv/book_cvs_file.csv");
            if (type == 1) {
                cvsFile = new File("E:\\code\\python\\to_csv\\book_csv_file.csv");
            } else if (type == 2) {
                cvsFile = new File("E:\\code\\python\\to_csv\\read_csv_file.csv");
            } else if (type == 3) {
                cvsFile = new File("E:\\code\\python\\to_csv\\album_csv_file.csv");
            }
            model = new GenericBooleanPrefDataModel(
                    GenericBooleanPrefDataModel
                            .toDataMap(new FileDataModel(cvsFile)));
            ItemSimilarity item = new LogLikelihoodSimilarity(model);
            //物品推荐算法
            Recommender r = new GenericItemBasedRecommender(model, item);
            list = r.recommend(Long.parseLong(userId), 10);
            list.forEach(System.out::println);
            long endTime = System.currentTimeMillis();
<<<<<<< HEAD
            // log.info((endTime - startTime) + "");
=======
            log.info((endTime - startTime) + "");
>>>>>>> master
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }

        return list;
    }

    @GetMapping("/user")
    public List<RecommendedItem> mahoutUser(@RequestParam String userId, @RequestParam int type) {
        //数据模型
        DataModel model = null;
        List<RecommendedItem> list = Lists.newArrayList();
        File cvsFile = null;
        try {
            long startTime = System.currentTimeMillis();
//            File bookCsvFile = ResourceUtils.getFile("classpath:csv/book_cvs_file.csv");
            if (type == 1) {
                cvsFile = new File("E:\\code\\python\\to_csv\\book_csv_file.csv");
            } else if (type == 2) {
                cvsFile = new File("E:\\code\\python\\to_csv\\read_csv_file.csv");
            } else if (type == 3) {
                cvsFile = new File("E:\\code\\python\\to_csv\\album_csv_file.csv");
            }
            model = new FileDataModel(cvsFile);
            //用户相识度算法
            UserSimilarity userSimilarity = new LogLikelihoodSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, userSimilarity, model);
            Recommender r = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);

            list = r.recommend(Long.parseLong(userId), 10);
            list.forEach(System.out::println);
            long endTime = System.currentTimeMillis();
<<<<<<< HEAD
            // log.info((endTime - startTime) + "");
=======
            log.info((endTime - startTime) + "");
>>>>>>> master
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return list;
    }
}

