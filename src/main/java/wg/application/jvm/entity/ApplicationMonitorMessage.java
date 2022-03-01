package wg.application.jvm.entity;

public class ApplicationMonitorMessage {

    private String version;
    private Integer pid;
    private String javaVersion;
    private Long runTime;
    private Long loadedClassCount;
    private Long unloadedClassCount;
    private Long heapTotal;
    private Long heapUsed;
    private Float heapUsedPercent;
    private Long nonHeapTotal;
    private Long nonHeapUsed;
    private Float nonHeapUsedPercent;


    /**
     * Eden区大小(字节)
     */
    private Long edenTotal;
    /**
     * Eden区已使用(字节)
     */
    private Long edenUsed;
    /**
     * Eden区使用率
     */
    private Float edenUsedPercent;
    /**
     * Eden区使用率峰值(从上次采集统计)
     */
    private Float edenPeakUsedPercent;
    /**
     * Survivor区大小(字节)
     */
    private Long survivorTotal;
    /**
     * Survivor区已使用(字节)
     */
    private Long survivorUsed;
    /**
     * Survivor区已使用率
     */
    private Float survivorUsedPercent;
    /**
     * Survivor区已使用率峰值(从上次采集统计)
     */
    private Float survivorPeakUsedPercent;
    /**
     * 老区大小(字节)
     */
    private Long oldTotal;
    /**
     * 老区已使用(字节)
     */
    private Long oldUsed;
    /**
     * 老区已使用率峰值
     */
    private Float oldUsedPercent;
    /**
     * 老区已使用率峰值(从上次采集统计)
     */
    private Float oldPeakUsedPercent;
    /**
     * 永久区大小(字节)
     */
    private Long permTotal;
    /**
     * 永久区已使用(字节)
     */
    private Long permUsed;
    /**
     * 永久区使用率
     */
    private Float permUsedPercent;
    /**
     * 永久区使用率峰值(从上次采集统计)
     */
    private Float permPeakUsedPercent;
    /**
     * CodeCache区大小(字节)
     */
    private Long codeCacheTotal;
    /**
     * CodeCache区已使用(字节)
     */
    private Long codeCacheUsed;
    /**
     * CodeCache区使用率
     */
    private Float codeCacheUsedPercent;
    /**
     * CodeCache区使用率峰值(从上次采集统计)
     */
    private Float codeCachePeakUsedPercent;
    /**
     * young gc名称
     */
    private String ygcName;
    /**
     * young gc次数
     */
    private Long ygc;
    /**
     * young gc总时间 (ms)
     */
    private Long ygcTime;
    /**
     * full gc名称
     */
    private String fgcName;
    /**
     * full gc次数
     */
    private Long fgc;
    /**
     * full gc总时间 (ms)
     */
    private Long fgcTime;
    /**
     * JVM当前线程数量
     */
    private Integer threadCount;
    /**
     * JVM线程数量峰值
     */
    private Integer threadPeakCount;
    /**
     * JVM当前用户线程数量
     */
    private Integer userThreadCount;
    /**
     * JVM死锁线程数量
     */
    private Integer deadLockedThreadCount;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public Long getLoadedClassCount() {
        return loadedClassCount;
    }

    public void setLoadedClassCount(Long loadedClassCount) {
        this.loadedClassCount = loadedClassCount;
    }

    public Long getUnloadedClassCount() {
        return unloadedClassCount;
    }

    public void setUnloadedClassCount(Long unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public Long getHeapTotal() {
        return heapTotal;
    }

    public void setHeapTotal(Long heapTotal) {
        this.heapTotal = heapTotal;
    }

    public Long getHeapUsed() {
        return heapUsed;
    }

    public void setHeapUsed(Long heapUsed) {
        this.heapUsed = heapUsed;
    }

    public Float getHeapUsedPercent() {
        return heapUsedPercent;
    }

    public void setHeapUsedPercent(Float heapUsedPercent) {
        this.heapUsedPercent = heapUsedPercent;
    }

    public Long getNonHeapTotal() {
        return nonHeapTotal;
    }

    public void setNonHeapTotal(Long nonHeapTotal) {
        this.nonHeapTotal = nonHeapTotal;
    }

    public Long getNonHeapUsed() {
        return nonHeapUsed;
    }

    public void setNonHeapUsed(Long nonHeapUsed) {
        this.nonHeapUsed = nonHeapUsed;
    }

    public Float getNonHeapUsedPercent() {
        return nonHeapUsedPercent;
    }

    public void setNonHeapUsedPercent(Float nonHeapUsedPercent) {
        this.nonHeapUsedPercent = nonHeapUsedPercent;
    }

    public Long getEdenTotal() {
        return edenTotal;
    }

    public void setEdenTotal(Long edenTotal) {
        this.edenTotal = edenTotal;
    }

    public Long getEdenUsed() {
        return edenUsed;
    }

    public void setEdenUsed(Long edenUsed) {
        this.edenUsed = edenUsed;
    }

    public Float getEdenUsedPercent() {
        return edenUsedPercent;
    }

    public void setEdenUsedPercent(Float edenUsedPercent) {
        this.edenUsedPercent = edenUsedPercent;
    }

    public Float getEdenPeakUsedPercent() {
        return edenPeakUsedPercent;
    }

    public void setEdenPeakUsedPercent(Float edenPeakUsedPercent) {
        this.edenPeakUsedPercent = edenPeakUsedPercent;
    }

    public Long getSurvivorTotal() {
        return survivorTotal;
    }

    public void setSurvivorTotal(Long survivorTotal) {
        this.survivorTotal = survivorTotal;
    }

    public Long getSurvivorUsed() {
        return survivorUsed;
    }

    public void setSurvivorUsed(Long survivorUsed) {
        this.survivorUsed = survivorUsed;
    }

    public Float getSurvivorUsedPercent() {
        return survivorUsedPercent;
    }

    public void setSurvivorUsedPercent(Float survivorUsedPercent) {
        this.survivorUsedPercent = survivorUsedPercent;
    }

    public Float getSurvivorPeakUsedPercent() {
        return survivorPeakUsedPercent;
    }

    public void setSurvivorPeakUsedPercent(Float survivorPeakUsedPercent) {
        this.survivorPeakUsedPercent = survivorPeakUsedPercent;
    }

    public Long getOldTotal() {
        return oldTotal;
    }

    public void setOldTotal(Long oldTotal) {
        this.oldTotal = oldTotal;
    }

    public Long getOldUsed() {
        return oldUsed;
    }

    public void setOldUsed(Long oldUsed) {
        this.oldUsed = oldUsed;
    }

    public Float getOldUsedPercent() {
        return oldUsedPercent;
    }

    public void setOldUsedPercent(Float oldUsedPercent) {
        this.oldUsedPercent = oldUsedPercent;
    }

    public Float getOldPeakUsedPercent() {
        return oldPeakUsedPercent;
    }

    public void setOldPeakUsedPercent(Float oldPeakUsedPercent) {
        this.oldPeakUsedPercent = oldPeakUsedPercent;
    }

    public Long getPermTotal() {
        return permTotal;
    }

    public void setPermTotal(Long permTotal) {
        this.permTotal = permTotal;
    }

    public Long getPermUsed() {
        return permUsed;
    }

    public void setPermUsed(Long permUsed) {
        this.permUsed = permUsed;
    }

    public Float getPermUsedPercent() {
        return permUsedPercent;
    }

    public void setPermUsedPercent(Float permUsedPercent) {
        this.permUsedPercent = permUsedPercent;
    }

    public Float getPermPeakUsedPercent() {
        return permPeakUsedPercent;
    }

    public void setPermPeakUsedPercent(Float permPeakUsedPercent) {
        this.permPeakUsedPercent = permPeakUsedPercent;
    }

    public Long getCodeCacheTotal() {
        return codeCacheTotal;
    }

    public void setCodeCacheTotal(Long codeCacheTotal) {
        this.codeCacheTotal = codeCacheTotal;
    }

    public Long getCodeCacheUsed() {
        return codeCacheUsed;
    }

    public void setCodeCacheUsed(Long codeCacheUsed) {
        this.codeCacheUsed = codeCacheUsed;
    }

    public Float getCodeCacheUsedPercent() {
        return codeCacheUsedPercent;
    }

    public void setCodeCacheUsedPercent(Float codeCacheUsedPercent) {
        this.codeCacheUsedPercent = codeCacheUsedPercent;
    }

    public Float getCodeCachePeakUsedPercent() {
        return codeCachePeakUsedPercent;
    }

    public void setCodeCachePeakUsedPercent(Float codeCachePeakUsedPercent) {
        this.codeCachePeakUsedPercent = codeCachePeakUsedPercent;
    }

    public String getYgcName() {
        return ygcName;
    }

    public void setYgcName(String ygcName) {
        this.ygcName = ygcName;
    }

    public Long getYgc() {
        return ygc;
    }

    public void setYgc(Long ygc) {
        this.ygc = ygc;
    }

    public Long getYgcTime() {
        return ygcTime;
    }

    public void setYgcTime(Long ygcTime) {
        this.ygcTime = ygcTime;
    }

    public String getFgcName() {
        return fgcName;
    }

    public void setFgcName(String fgcName) {
        this.fgcName = fgcName;
    }

    public Long getFgc() {
        return fgc;
    }

    public void setFgc(Long fgc) {
        this.fgc = fgc;
    }

    public Long getFgcTime() {
        return fgcTime;
    }

    public void setFgcTime(Long fgcTime) {
        this.fgcTime = fgcTime;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public Integer getUserThreadCount() {
        return userThreadCount;
    }

    public void setUserThreadCount(Integer userThreadCount) {
        this.userThreadCount = userThreadCount;
    }

    public Integer getThreadPeakCount() {
        return threadPeakCount;
    }

    public void setThreadPeakCount(Integer threadPeakCount) {
        this.threadPeakCount = threadPeakCount;
    }

    public Integer getDeadLockedThreadCount() {
        return deadLockedThreadCount;
    }

    public void setDeadLockedThreadCount(Integer deadLockedThreadCount) {
        this.deadLockedThreadCount = deadLockedThreadCount;
    }

    @Override
    public String toString() {
        return "ApplicationMonitorMessage{" +
                "version='" + version + '\'' +
                ", pid=" + pid +
                ", javaVersion='" + javaVersion + '\'' +
                ", runTime=" + runTime +
                ", loadedClassCount=" + loadedClassCount +
                ", unloadedClassCount=" + unloadedClassCount +
                ", heapTotal=" + heapTotal +
                ", heapUsed=" + heapUsed +
                ", heapUsedPercent=" + heapUsedPercent +
                ", nonHeapTotal=" + nonHeapTotal +
                ", nonHeapUsed=" + nonHeapUsed +
                ", nonHeapUsedPercent=" + nonHeapUsedPercent +
                ", edenTotal=" + edenTotal +
                ", edenUsed=" + edenUsed +
                ", edenUsedPercent=" + edenUsedPercent +
                ", edenPeakUsedPercent=" + edenPeakUsedPercent +
                ", survivorTotal=" + survivorTotal +
                ", survivorUsed=" + survivorUsed +
                ", survivorUsedPercent=" + survivorUsedPercent +
                ", survivorPeakUsedPercent=" + survivorPeakUsedPercent +
                ", oldTotal=" + oldTotal +
                ", oldUsed=" + oldUsed +
                ", oldUsedPercent=" + oldUsedPercent +
                ", oldPeakUsedPercent=" + oldPeakUsedPercent +
                ", permTotal=" + permTotal +
                ", permUsed=" + permUsed +
                ", permUsedPercent=" + permUsedPercent +
                ", permPeakUsedPercent=" + permPeakUsedPercent +
                ", codeCacheTotal=" + codeCacheTotal +
                ", codeCacheUsed=" + codeCacheUsed +
                ", codeCacheUsedPercent=" + codeCacheUsedPercent +
                ", codeCachePeakUsedPercent=" + codeCachePeakUsedPercent +
                ", ygcName='" + ygcName + '\'' +
                ", ygc=" + ygc +
                ", ygcTime=" + ygcTime +
                ", fgcName='" + fgcName + '\'' +
                ", fgc=" + fgc +
                ", fgcTime=" + fgcTime +
                ", threadCount=" + threadCount +
                ", threadPeakCount=" + threadPeakCount +
                ", userThreadCount=" + userThreadCount +
                ", deadLockedThreadCount=" + deadLockedThreadCount +
                '}';
    }
}
