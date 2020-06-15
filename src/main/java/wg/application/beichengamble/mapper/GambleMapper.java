package wg.application.beichengamble.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wg
 * @Package wg.application.beichengamble.mapper
 * @date 2020/5/9 15:26
 * @Copyright
 */
@Mapper
public interface GambleMapper {

    List selectForCardNo();
    List selectForDuiShouCardNo();
}
