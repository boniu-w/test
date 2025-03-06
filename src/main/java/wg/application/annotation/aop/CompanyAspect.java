// package wg.application.annotation.aop;
//
// import ceet.ufps.pl.pia.api.defect.BasicDataDTO;
// import ceet.ufps.pl.pia.common.utils.CommonUtils;
// import ceet.ufps.pl.pia.defect.service.BasicDataService;
// import jakarta.annotation.Resource;
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.stereotype.Component;
// import org.springframework.util.CollectionUtils;
//
// import java.util.*;
// import java.util.stream.Collectors;
//
// /**
//  * author wg
//  * description CompanyAspect
//  * createTime 10:44 2025/2/14
//  * updateTime 10:44 2025/2/14
//  */
// @Aspect
// @Component
// public class CompanyAspect {
//
//     @Resource
//     BasicDataService basicDataService;
//
//     @Pointcut("@annotation(ceet.ufps.pl.pia.annotation.Company)")
//     public void initAnnotationCompany() {
//     }
//
//     @AfterReturning(pointcut = "initAnnotationCompany()", returning = "list")
//     public void setCompanyData(List<?> list) {
//         if (!CollectionUtils.isEmpty(list)) {
//             Set<String> basicDataIdSet = list.stream()
//                     .filter(e -> CommonUtils.getter("basicDataId", e) != null)
//                     .map(e -> Objects.requireNonNull(CommonUtils.getter("basicDataId", e)).toString())
//                     .collect(Collectors.toSet());
//
//             Map<String, BasicDataDTO> basicDataIdMap = getBasicDataIdMap(basicDataIdSet);
//             for (Object obj : list) {
//                 String basicDataId = Objects.requireNonNull(CommonUtils.getter("basicDataId", obj)).toString();
//                 BasicDataDTO basicData = basicDataIdMap.get(basicDataId);
//                 String branch = basicData.getBranch();    // 分公司
//                 String region = basicData.getRegion();    // 作业公司
//                 String field = basicData.getField();      //油气田
//
//                 if (CommonUtils.hasField(obj, "branch")) {
//                     CommonUtils.setter(obj, "branch", branch);
//                 }
//                 if (CommonUtils.hasField(obj, "region")) {
//                     CommonUtils.setter(obj, "region", region);
//                 }
//                 if (CommonUtils.hasField(obj, "field")) {
//                     CommonUtils.setter(obj, "field", field);
//                 }
//             }
//         }
//     }
//
//     public Map<String, BasicDataDTO> getBasicDataIdMap(Collection<String> ids) {
//         List<BasicDataDTO> list = new ArrayList<>();
//         if (CollectionUtils.isEmpty(ids)) {
//             list = basicDataService.list(new BasicDataDTO());
//         } else {
//             list = basicDataService.getByIds(ids);
//         }
//
//         Map<String, BasicDataDTO> basicDataIdMap = list.stream()
//                 .collect(Collectors.toMap(BasicDataDTO::getId, m -> m));
//         return basicDataIdMap;
//     }
// }
