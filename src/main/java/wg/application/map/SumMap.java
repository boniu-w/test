package wg.application.map;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/************************************************************************
 * @description: map 键相同 值求和
 * @author: wg
 * @date: 14:18  2021/12/7
 * @params:
 * @return:
 ************************************************************************/
public class SumMap {

    /************************************************************************
     * @description: map 键相同 值求和
     * @author: wg
     * @date: 14:18  2021/12/7
     * @params:
     * @return:
     ************************************************************************/
    // @Override
    // public void getData() {
    //     QueryWrapper<BasicDataEntity> queryWrapper = new QueryWrapper<>();
    //     List<BasicDataEntity> basicDataEntityList = basicDataDao.selectList(queryWrapper);
    //     Map<String, List<BasicDataEntity>> listMap = basicDataEntityList.stream()
    //             .filter(e -> StringUtils.isNotBlank(e.getBranch()))
    //             .collect(Collectors.groupingBy(BasicDataEntity::getBranch));
    //
    //     Set<Map.Entry<String, List<BasicDataEntity>>> entrySet = listMap.entrySet();
    //     // 分区
    //     HashMap<String, Map<Integer, Long>> resultMap = new HashMap<>();
    //     for (Map.Entry<String, List<BasicDataEntity>> entry : entrySet) {
    //         System.out.println(entry.getKey() + "=" + entry.getValue());
    //         List<String> ids = entry.getValue().stream().map(BasicDataEntity::getId).collect(Collectors.toList());
    //         HashMap<Integer, Long> sumMap = Maps.newHashMap();
    //         // 分 id
    //         ArrayList<Map<Integer, Long>> list = new ArrayList<>();
    //         for (String basicDataId : ids) {
    //             Map<Integer, Long> corrosionMap = TableUtil.getMap(TableUtil.getHistory(CorrosionAssessmentHistoryEntity.class, basicDataId));
    //             Map<Integer, Long> dentMap = TableUtil.getMap(TableUtil.getHistory(DentAssessmentHistoryEntity.class, basicDataId));
    //             Map<Integer, Long> crackMap = TableUtil.getMap(TableUtil.getHistory(CrackAssessmentHistoryEntity.class, basicDataId));
    //             Map<Integer, Long> weldMap = TableUtil.getMap(TableUtil.getHistory(WeldAssessmentHistoryEntity.class, basicDataId));
    //             Map<Integer, Long> scratchMap = TableUtil.getMap(TableUtil.getHistory(ScratchAssessmentHistoryEntity.class, basicDataId));
    //             Map<Integer, Long> manufacturingMap = TableUtil.getMap(TableUtil.getHistory(ManufacturingAssessmentHistoryEntity.class, basicDataId));
    //             list.add(corrosionMap);
    //             list.add(dentMap);
    //             list.add(crackMap);
    //             list.add(weldMap);
    //             list.add(scratchMap);
    //             list.add(manufacturingMap);
    //         }
    //
    //         list.stream().map(item -> {
    //             item.forEach((k, v) -> {
    //                 Long aLong = sumMap.get(k);
    //                 if (aLong == null) {
    //                     sumMap.put(k, v);
    //                 } else {
    //                     aLong = v + aLong;
    //                     sumMap.put(k, aLong);
    //                 }
    //             });
    //             return sumMap;
    //         }).collect(Collectors.toList());
    //
    //         list.forEach(System.out::println);
    //         System.out.println("===============");
    //         System.out.println("sumMap = " + sumMap);
    //         resultMap.put(entry.getKey(), sumMap);
    //     }
    //
    //     System.out.println("resultMap" + resultMap);
    // }
}
