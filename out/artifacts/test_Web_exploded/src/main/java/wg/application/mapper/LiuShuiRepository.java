package wg.application.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import wg.application.entity.LiuShui;

import java.util.List;
import java.util.Optional;

/*************************************************************
 * @Package wg.application.mapper
 * @author wg
 * @date 2020/7/15 16:42
 * @version
 * @Copyright
 *************************************************************/
public interface LiuShuiRepository extends JpaRepository<LiuShui,Long> {

    List<LiuShui> getByJiaoYiJinErBetween(double min, double max);
}
