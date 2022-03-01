package wg.application.jvm;

import wg.application.jvm.entity.ApplicationMonitorMessage;
import wg.application.jvm.util.JVMGCUtils;
import wg.application.jvm.util.JVMInfoUtils;
import wg.application.jvm.util.JVMMemoryUtils;
import wg.application.jvm.util.JVMThreadUtils;

/**
 * 应用状态监控,包括应用类型,版本,所在的tomcat名以及数据库连接等信息(代码有删减)
 * <p>
 * extends AbstractMonitorTask<ApplicationMonitorMessage>
 *
 * @Override protected ApplicationMonitorMessage doRun() {
 * return this.createMessage();
 * }
 */
public class ApplicationMonitorTask {

    private ApplicationMonitorMessage jvmInfo;

    private ApplicationMonitorMessage createMessage() {
        ApplicationMonitorMessage message = new ApplicationMonitorMessage();
        // APP
        // message.setVersion(ErlangMonitorConfigManager.getConfig().getAppVersion());
        // JVM
        setJVMInfo(message);
        // DB
        // setDBInfo(message);
        return message;
    }

    public ApplicationMonitorMessage getJvmInfo() {
        jvmInfo = new ApplicationMonitorMessage();
        try {
            jvmInfo.setPid(Integer.parseInt(JVMInfoUtils.getPID()));
        } catch (Exception e) {
        }
        jvmInfo.setJavaVersion(JVMInfoUtils.getJavaVersion());
        jvmInfo.setRunTime(JVMInfoUtils.getJVMUpTimeMs());
        jvmInfo.setLoadedClassCount(JVMInfoUtils.getJVMLoadedClassCount());
        jvmInfo.setUnloadedClassCount(JVMInfoUtils.getJVMUnLoadedClassCount());
        JVMMemoryUtils.JVMMemoryUsage heapMemoryUsage = JVMMemoryUtils.getHeapMemoryUsage();
        if (heapMemoryUsage != null) {
            jvmInfo.setHeapTotal(heapMemoryUsage.getMax());
            jvmInfo.setHeapUsed(heapMemoryUsage.getUsed());
            jvmInfo.setHeapUsedPercent(heapMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage nonHeapMemoryUsage = JVMMemoryUtils.getNonHeapMemoryUsage();
        if (nonHeapMemoryUsage != null) {
            jvmInfo.setNonHeapTotal(nonHeapMemoryUsage.getMax());
            jvmInfo.setNonHeapUsed(nonHeapMemoryUsage.getUsed());
            jvmInfo.setNonHeapUsedPercent(nonHeapMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage edenMemoryUsage = JVMMemoryUtils.getEdenSpaceMemoryUsage();
        if (edenMemoryUsage != null) {
            jvmInfo.setEdenTotal(edenMemoryUsage.getMax());
            jvmInfo.setEdenUsed(edenMemoryUsage.getUsed());
            jvmInfo.setEdenUsedPercent(edenMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage edenPeakMemoryUsage = JVMMemoryUtils.getAndResetEdenSpaceMemoryPeakUsage();
        if (edenPeakMemoryUsage != null) {
            jvmInfo.setEdenPeakUsedPercent(edenPeakMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage survivorMemoryUsage = JVMMemoryUtils.getSurvivorSpaceMemoryUsage();
        if (survivorMemoryUsage != null) {
            jvmInfo.setSurvivorTotal(survivorMemoryUsage.getMax());
            jvmInfo.setSurvivorUsed(survivorMemoryUsage.getUsed());
            jvmInfo.setSurvivorUsedPercent(survivorMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage survivorPeakMemoryUsage = JVMMemoryUtils.getAndResetSurvivorSpaceMemoryPeakUsage();
        if (survivorPeakMemoryUsage != null) {
            jvmInfo.setSurvivorPeakUsedPercent(survivorPeakMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage oldGenMemoryUsage = JVMMemoryUtils.getOldGenMemoryUsage();
        if (oldGenMemoryUsage != null) {
            jvmInfo.setOldTotal(oldGenMemoryUsage.getMax());
            jvmInfo.setOldUsed(oldGenMemoryUsage.getUsed());
            jvmInfo.setOldUsedPercent(oldGenMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage oldGenPeakMemoryUsage = JVMMemoryUtils.getAndResetOldGenMemoryPeakUsage();
        if (oldGenPeakMemoryUsage != null) {
            jvmInfo.setOldPeakUsedPercent(oldGenPeakMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage permGenMemoryUsage = JVMMemoryUtils.getPermGenMemoryUsage();
        if (permGenMemoryUsage != null) {
            jvmInfo.setPermTotal(permGenMemoryUsage.getMax());
            jvmInfo.setPermUsed(permGenMemoryUsage.getUsed());
            jvmInfo.setPermUsedPercent(permGenMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage permGenPeakMemoryUsage = JVMMemoryUtils.getAndResetPermGenMemoryPeakUsage();
        if (permGenPeakMemoryUsage != null) {
            jvmInfo.setPermPeakUsedPercent(permGenPeakMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage codeCacheGenMemoryUsage = JVMMemoryUtils.getCodeCacheMemoryUsage();
        if (codeCacheGenMemoryUsage != null) {
            jvmInfo.setCodeCacheTotal(codeCacheGenMemoryUsage.getMax());
            jvmInfo.setCodeCacheUsed(codeCacheGenMemoryUsage.getUsed());
            jvmInfo.setCodeCacheUsedPercent(codeCacheGenMemoryUsage.getUsedPercent());
        }
        JVMMemoryUtils.JVMMemoryUsage codeCacheGenPeakMemoryUsage = JVMMemoryUtils.getAndResetCodeCacheMemoryPeakUsage();
        if (codeCacheGenPeakMemoryUsage != null) {
            jvmInfo.setCodeCachePeakUsedPercent(codeCacheGenPeakMemoryUsage.getUsedPercent());
        }

        jvmInfo.setYgcName(JVMGCUtils.getYoungGCName());
        jvmInfo.setYgc(JVMGCUtils.getYoungGCCollectionCount());
        jvmInfo.setYgcTime(JVMGCUtils.getYoungGCCollectionTime());
        jvmInfo.setFgcName(JVMGCUtils.getFullGCName());
        jvmInfo.setFgc(JVMGCUtils.getFullGCCollectionCount());
        jvmInfo.setFgcTime(JVMGCUtils.getFullGCCollectionTime());

        jvmInfo.setThreadCount(JVMThreadUtils.getThreadCount());
        jvmInfo.setThreadPeakCount(JVMThreadUtils.getAndResetPeakThreadCount());
        jvmInfo.setUserThreadCount(jvmInfo.getThreadCount() - JVMThreadUtils.getDaemonThreadCount());
        jvmInfo.setDeadLockedThreadCount(JVMThreadUtils.getDeadLockedThreadCount());

        return jvmInfo;
    }

    public void setJVMInfo(ApplicationMonitorMessage message) {
        this.jvmInfo = message;
    }

}