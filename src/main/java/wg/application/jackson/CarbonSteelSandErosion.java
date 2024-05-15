package wg.application.jackson;

import lombok.Data;
import wg.application.validator.CalculateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/************************************************************************
 * author: wg
 * description: CarbonSteelSandErosion 碳钢 沙蚀
 * createTime: 9:47 2024/5/15
 * updateTime: 9:47 2024/5/15
 ************************************************************************/
@Data
public class CarbonSteelSandErosion implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Pipe Outsidel Diameter:
     * 管道外径(mm)
     */
    @NotNull(groups = {CalculateGroup.class}, message = "管道外径 不可为空")
    private Double pipeOutsideDiameter;

    /**
     * Thickness
     * 壁厚(mm)
     */
    @NotNull(groups = {CalculateGroup.class}, message = "壁厚 不可为空")
    private Double thickness;

    /**
     * 钢材密度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "钢材密度 不可为空")
    private Double steelDensity;

    /**
     * 曲率半径
     */
    @NotNull(groups = {CalculateGroup.class}, message = "曲率半径 不可为空")
    private Double curvatureRadius;

    /**
     * 颗粒密度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "颗粒密度 不可为空")
    private Double particleDensity;

    /**
     * 颗粒直径
     */
    @NotNull(groups = {CalculateGroup.class}, message = "颗粒直径 不可为空")
    private Double particleDiameter;

    /**
     * 介质中的含沙量
     */
    @NotNull(groups = {CalculateGroup.class}, message = "介质中的含沙量 不可为空")
    private Double mp;

    /**
     * 液相粘度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "液相粘度 不可为空")
    private Double liquidPhaseViscosity;

    /**
     * 气相粘度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "气相粘度 不可为空")
    private Double gasPhaseViscosity;

    /**
     * 液相密度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "液相密度 不可为空")
    private Double liquidDensity;

    /**
     * 气相密度
     */
    @NotNull(groups = {CalculateGroup.class}, message = "气相密度 不可为空")
    private Double gasPhaseDensity;

    /**
     * 管中气体实际体积流量
     */
    @NotNull(groups = {CalculateGroup.class}, message = "管中气体实际体积流量 不可为空")
    private Double vgs;

    /**
     * 管中液体实际体积流量
     */
    @NotNull(groups = {CalculateGroup.class}, message = "管中液体实际体积流量 不可为空")
    private Double vls;
}
