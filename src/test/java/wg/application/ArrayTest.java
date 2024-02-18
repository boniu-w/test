package wg.application;

import org.junit.Test;
import wg.application.util.ArrayUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArrayTest {

    @Test
    public void test() {
        String[] types = {"jpg", "txt", "xlsx", "png"};
        List<String> collect = Arrays.stream(types).collect(Collectors.toList());

        collect.add("aa");
        System.out.println(collect);

        collect.remove("aa");
        String[] array = collect.toArray(new String[collect.size()]);
        System.out.println(array.length);
    }

    @Test
    public void test1() {
        String[] types0 = {"xbd_equipment_type", "xbd_facility_type", "xeq_inspection_type", "xeq_expenseaccount", "xeq_self_in_or_out", "xeq_wh_or_not_the_ap"};
        String[] types1 = {"xbd_equipment_type", "xeq_idle_reason"};
        String[] types2 = {"xeq_idle_reason", "xbd_equipment_type"};
        String[] types3 = {"xeq_material_type", "BPM_Status", "unit_lg"};
        String[] types4 = {"xbd_facility_type", "xbd_equipment_type"};
        String[] types5 = {"xbd_facility_type", "xbd_equipment_type"};
        String[] types6 = {"xeq_risk_level"};
        String[] types7 = {"PPM_Shutdown_Type", "PPM_Shutdown_Lever", "PPM_Shutdown_Range", "PPM_Shutdown_Reason", "xeq_priority", "xeq_system", "xeq_discipline", "xeq_equipment_facility_type", "xeq_expenseaccount"};
        String[] types8 = {"xeq_expenseaccount"};
        String[] types9 = {"xeq_running_status"};
        String[] types10 = {"BPM_Event_Type"};
        String[] types11 = {"xeq_system", "xeq_risk_level"};
        String[] types12 = {"xbd_equipment_type"};
        String[] types13 = {"xbd_equipment_type", "BPM_Equipment_Status"};
        String[] types14 = {"xeq_event_level"};
        String[] types15 = {"xeq_boe_loss_type", "xeq_equipment_repair_cost_type", "xeq_facility_repair_costs_type", "xeq_system", "xeq_root_cause", "xeq_event_level"};
        String[] types16 = {"month_work_type_individual_BU", "month_work_type"};

        String[] strings = ArrayUtil.mergeAndRemoveDuplicates(types0, types1, types2, types3, types4, types5, types6, types7, types8, types9, types10, types11, types12, types13, types14, types15, types16);
        System.out.println("strings = " + Arrays.toString(strings));

        // 添加所有数组元素到Set并加上双引号
        Set<String> uniqueTypes = new HashSet<>();
        for (String[] typeArray : new String[][]{types0, types1, types2, types3, types4, types5, types6, types7, types8, types9, types10, types11, types12, types13, types14, types15, types16}) {
            for (String type : typeArray) {
                uniqueTypes.add("\"" + type + "\"");
            }
        }

        // 转为数组
        String[] result = uniqueTypes.toArray(new String[0]);
        System.out.println("Arrays.toString(result) = " + Arrays.toString(result));
    }
}
