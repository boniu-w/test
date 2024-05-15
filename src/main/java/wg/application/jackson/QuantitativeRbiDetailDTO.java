package wg.application.jackson;

import lombok.Data;

import java.io.Serializable;

/**
 * RBI定量评估明细表
 *
 * @author Seven ME info@7-me.net
 * @since v1.0.0 2022-09-28
 */
@Data
public class QuantitativeRbiDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CarbonSteelSandErosion carbonSteelSandErosion;

    public CarbonSteelSandErosion getCarbonSteelSandErosion() {
        return carbonSteelSandErosion;
    }

    public void setCarbonSteelSandErosion(CarbonSteelSandErosion carbonSteelSandErosion) {
        this.carbonSteelSandErosion = carbonSteelSandErosion;
    }
}