package wg.application.util;


import wg.application.vo.PageResult;

import java.util.Collections;
import java.util.List;

public class PageUtil {
    public static <T> List<T> getPage(List<T> list, int pageNo, int pageSize) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        int total = list.size();
        int fromIndex = (pageNo - 1) * pageSize;

        if (fromIndex >= total) {
            return Collections.emptyList();
        }

        int toIndex = Math.min(fromIndex + pageSize, total);

        return list.subList(fromIndex, toIndex);
    }

    public static <T> PageResult<T> paginate(List<T> list, int pageNo, int pageSize) {
        int total = list.size();
        int totalPages = (total + pageSize - 1) / pageSize;

        int fromIndex = (pageNo - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        List<T> pageData = fromIndex >= total
                ? Collections.emptyList()
                : list.subList(fromIndex, toIndex);

        PageResult<T> result = new PageResult<>();
        result.setData(pageData);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setTotalPages(totalPages);

        return result;
    }
}
