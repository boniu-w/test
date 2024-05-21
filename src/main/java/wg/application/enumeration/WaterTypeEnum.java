package wg.application.enumeration;

public enum WaterTypeEnum {
    a("RawSeawater", "未经处理的海水", "未经处理、正常氧气、细菌、海洋植物群等"),
    b("Seawater+biocide/chlorination", "海水+杀菌剂/氯化", "用紫外线/过滤或杀菌剂处理，氯化"),
    c("SeawaterLowOxygen", "低氧海水", "脱氧，最大50 ppb O2。无其他处理"),
    d("SeawaterLowOxygen+biocide", "低氧海水+杀菌剂", "脱氧，最大50 ppb O2，用紫外线/过滤或杀菌剂处理。无氯化"),
    f("SeawaterLowOxygen+chlorination", "低氧海水+氯化", "脱氧，最大50 ppb O2，氯化"),
    e("SeawaterLowOxygen+biocide+chlorination", "低氧海水+杀菌剂+氯化", "脱氧，最大50 ppb O2，用紫外线/过滤或杀菌剂处理，氯化"),
    g("FreshWater", "清水", "通常通过海水冷凝制备。工厂用水的基础，例如蒸汽产生、低盐含量、正常氧气"),
    h("ClosedLoop", "闭环", "本质上含氧量低的脱盐系统"),
    i("ExposedDeains", "外排", "从排水沟、水闸、洪水等处收集水的开放式系统，假定包含未经处理的未经处理海水"),
    j("SanitaryDrains", "生活污水", "来自卫生系统的排水管。含高细菌和有机物的淡水"),
    k("otherwise", "其他", "");

    private final String name;
    private final String type;
    private final String define;

    WaterTypeEnum(String name, String type, String define) {
        this.name = name;
        this.type = type;
        this.define = define;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDefine() {
        return define;
    }
    
}
