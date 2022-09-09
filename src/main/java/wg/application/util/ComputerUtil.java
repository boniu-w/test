package wg.application.util;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.util.Arrays;

/************************************************************************
 * @author: wg
 * @description: 获取计算机信息
 * @createTime: 14:15 2022/8/9
 * @updateTime: 14:15 2022/8/9
 ************************************************************************/
public class ComputerUtil {

    public static void getHardware(){
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        System.out.println();
        HWDiskStore[] diskStores = hardware.getDiskStores();
        System.out.println();

        NetworkIF[] networkIFs = hardware.getNetworkIFs();
        System.out.println(Arrays.toString(networkIFs));
        System.out.println();


    }
}
