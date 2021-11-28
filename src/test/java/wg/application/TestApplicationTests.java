package wg.application;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.javac.util.Context;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;
import wg.application.entity.*;
import wg.application.gc.GcEntity;
import wg.application.security.CommonEncryption;
import wg.application.thread.TaskTest;
import wg.application.util.CalendarUtil;
import wg.application.util.WgJsonUtil;
import wg.application.util.WgUtil;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class TestApplicationTests {


    /****************************************************************
     * 下面两个方法都是将hashmap 转成 字符串 ,自己写的util 不行;
     * @author: wg
     * @time: 2020/6/19 16:07
     ****************************************************************/
    @Test
    public void contextLoads() {

        System.out.println("ddddddd");

        // hashmap 转字符串
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "123123");
        hashMap.put("2", "123123");

        String s = WgJsonUtil.hashMapToJsonString(hashMap);
        System.out.println(s);


        // 将字符串写入 文件 通过打印流
        String path = "D:\\ideaprojects\\test\\src\\main\\resources\\static\\json\\jsonData.json";
        WgJsonUtil.jsonDataToFile(path, s);

    }


    @Test
    public void jsonTest() {
        // hashmap 转 字符串
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "123123");
        hashMap.put("age", "123123");

        String jsonString = JSONUtils.toJSONString(hashMap);

        System.out.println(jsonString);

        // 将字符串写入 文件 通过buffer流 D:\ideaprojects\test\src\main\resources\static\json
        String path = "D:\\ideaprojects\\test\\src\\main\\resources\\static\\json\\jsonData.json";
        WgJsonUtil.jsonDataToFileByBufferedWritter(path, jsonString);

    }

    @Test
    public void defaultValue() {
        boolean b;
        b = 0 < 0;
        System.out.println(b);
    }


    @Test
    public void dateTest() {
        wg.application.controller.Test test = new wg.application.controller.Test();

        test.dateTest();
    }

    /*****************************************************
     * @params:
     * @description: 反射
     * @author: wg
     * @date: 2021/7/12 14:38
     *****************************************************/
    @Test
    public void instanceTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String s = "123";

        if (s.getClass().isInstance(String.class)) {  // false
            System.out.println("...........true");
        }

        if (String.class.isInstance(s)) {  // true
            System.out.println(true);
        }

        if (s instanceof String) {  // true
            System.out.println("<<<<<<<<<<<<<<<<<");
        }

        Student student = new Student();
        student.setAge(123);
        student.setName("wg");

        Field field = Student.class.getField("id");
        System.out.println(field.get(student));

        // Class<?> aClass = Class.forName("wg.application.entity.Student");
        // Field[] declaredFields = aClass.getDeclaredFields();
        // for (Field field: declaredFields){
        //     System.out.println(field);
        //     try {
        //         Object o = field.get(aClass.newInstance());
        //         System.out.println(o.toString());
        //     } catch (IllegalAccessException | InstantiationException e) {
        //         e.printStackTrace();
        //     }
        // }


    }

    @Test
    public void duboTest() {
        // 局
        List<String> list = new ArrayList<>();
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,1");

        //int frequency = 0;
        //HashMap<Object, Object> map = new HashMap<>();
        //for (int i = 0; i < list.size(); i++) {
        //    frequency = Collections.frequency(list, list.get(i));
        //    map.put(list.get(i), frequency);
        //}
        //
        //System.out.println(map);

        // 人员id
        List<String> useridList = new ArrayList<>();
        useridList.add("300028");
        useridList.add("319485");
        useridList.add("708949");
        useridList.add("542246");
        useridList.add("1");


        int count = 0;
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String s : useridList) {
            for (String value : list) {
                String[] playId = value.split(",");

                for (String item : playId) {
                    if (item.equals(s)) {
                        count++;
                    }
                }

                //String playids = list.get(i);
                //String reg = "" + useridList.get(j) + "";
                //Pattern compile = Pattern.compile(reg);
                //Matcher matcher = compile.matcher(playids);
                //boolean b = matcher.find();
                //if (b == true) {
                //    count++;
                //}
            }
            hashMap.put(s, count);
            count = 0;
        }
        System.out.println(hashMap);
    }

    @Test
    public void sdfsf() {
        String[] words = new String[]{"ag", "dadbcah", "heeeahacdahj", "feg", "dadficbhih", "ihdggfeefh", "fbedcahdfeab", "djaacjcfj", "haijah", "hbaebhd", "d", "cbedeei", "gc", "jddgeccbdhgdiccfdigb", "edgchajgjbhch", "djdebb", "b", "egiegejggbgi", "acdjabiddcjce", "dggeeffebiehdbgg", "dbdefhgaejaghehcb", "cjhgbgjecdfbddccjehf", "bdgjage", "ggfeggg", "hajc", "bjdgjhdedefifdihg", "afhgdhacdjfa", "iigadadg", "gjadachfdiaejijdfdgd", "fiaeajiaebahfjdagc", "iagjfdfcghehfjia", "afd", "bgfgcajfhddajiceeaaf", "iefcfgfdjecg", "ebceadaaigdbc", "igbcdbdfdjegff", "jfabjebjge", "hedchddj", "eehgggbifbfbg", "gjefdjfahib", "hadgj", "bfde", "dg", "bidedjffcdegh", "gejbefeaiieidha", "ajgcejcfgidafghdejdj", "aafbcdh", "hicif", "fgidijdfbgjfacjcf", "iciajjdiggejedgd", "jjdfbedjifj", "hhfbfh", "bibbefhjbbbjjejjgi", "egeigdhfbgfehfbhgbga", "fiiddfegdahbajcaba", "dfjfjebhdj", "jg", "hafahdbgijfjhafc", "djafgehi", "agbd", "fgchciaeeidfjagefahe", "jhifccfhbgechaefjbdb", "bahfcecjjgejbibajd", "idgdgcidigbedbafa", "fddejfjddeeiga", "jijhjcci", "fbdedaciahjcicei", "aababeiaeh", "eideccaddgcicifd", "ea", "faehegabdgadc", "fihajceafdd", "cagbehagajc", "cgje", "ighdcefhe", "cbbiaigci", "ggfejebhbhfdbifb", "bfabjjfjcegdbjebcjid", "bcdgehfjdfdb", "hdcihijbahfjcjdfb", "jfjfhgjdjbgihgabjhbh", "bfaaebjdj", "acgbcjebedghfi", "fdcehcgbgdchifjaahd", "eiajbjfgec", "eiigjidjjd", "gbie", "ihccaajfdjdjidceebi", "eceegacbgigd", "fff", "bdjgfgggccgfbfbd", "gchiafbbggdccgc", "aefehccgcaccgd", "cebhgac", "jegcfegj", "aggcdjic", "egaejbejefch", "ibfge", "ejjhg", "ijfhaibfhhd", "chag", "j", "di", "gieicbfai", "hhbjaeeachdiijh", "jicajhfhdjhbfhjicch", "gbfjabfe", "babddicdhdiigfjgcga", "ihbgjfbgjh", "ijbjbhgchhcdacgeig", "bdbhigbfbebcah", "hghgdich", "hdd", "aebjeh", "cchadbg", "gjdjjhihjg", "cidibijif", "cehefdedgdjdafg", "hchca", "ffdfbidfhchhe", "edgjhcdib", "cedcdgjdcggdajcbejib", "gbjjjaiijjiijii", "agdhfbgehfcidadd", "acgfaacfgehhihibcbb", "jfebeihiacgjggicgij", "cbgadaiffjcj", "abi", "dbfehfffcaa", "gdbeddaccffd", "he", "jdcfjjdceaf", "bhabdahe", "fiehffebffd", "dhdgcechbdcbfj", "efbiiaeahifda", "jgjhijeegajd", "dh", "jigeijbcddhf", "e", "fahcehhhjhf", "iffechadhjbbjgfgi", "fccbgdeffjjhceh", "jiefceggii", "hhjhijiijihh", "fibeb", "cb", "dgjcfdfbjjjg", "ghcidcgjiicaab", "cjhc", "abh", "hccajge", "hagbjcjhhgahadabffj", "ehccgaficdiajib", "jiajgf", "ch", "fcehajcfafceafbdjea", "adjdhcdjibh", "decjbjecg", "baeidjbbajjffiabgcjb", "bjibfiij", "edfa", "jjiba", "gcjijifd", "edejihjahbjf", "ehfdjfeebjgij", "ecgbcebaacgiifgj", "djgchbcjcfabfii", "bbgahefbjiihfciifdc", "edjhie", "iaijicfdhdhiigiech", "jeaiacicajdacabggghj", "ffidgghe", "fi", "igcdigbcbhgfac", "igdbhfhbheceagfhifec", "fbgbabaadjfg", "hejjjahed", "fhfehgfibb", "dcjecbccggeheeaddfh", "dcgcjffagajfhb", "gfj", "aca", "aegfifc", "dgjbeijdhjagcfejeae", "g", "cfhhdfcde", "gb", "ijjaij", "fcfdahjaadgf", "cgddfddjcigc", "ccjibdabg", "ffg", "bhgccdjaheiffge", "dagdiahhe", "ibefjebjgijggfbcd", "gijcijaiifeddhjafg", "ei", "gbeiga", "fbc", "cffeigheehaecfefaf", "hbfb", "ebhehcaaafdgghacdcc", "gfbjchdagjcdhiffeg", "afciafahcggbfghabjcc", "jdbgjb", "gefaa", "gfeifhdhaia", "c", "chjbfcde", "ffbdjbda", "hjcafagejecjbdeghbfi", "hfejebjaffbi", "f", "bdjhddji", "feafhche", "jggjhibddejigjehah", "caeddaace", "caceb", "acajhdebh", "ggeadaa", "dbeif", "ihdifhijgcjbdijia", "fibjfeecdchj", "febihiaciahbii", "gjcafcbgfejdcidag", "bidgh", "ajcacfhcjedjegi", "ejfaghcebhhhicgcjfe", "ccfbdafigibdeggg", "dghd", "efhfbfajea", "ggfegichia", "igccjieidbebcccicid", "ggcb", "gdbbdjdi", "h", "ebdeegcddc", "fieebfbeeigcjcb", "ijfdeibijbjeaa", "jahahgcfidb", "ejhdhjagi", "jcfiddhjdcggeeedjjg", "cgdcecffhgiceceafbgj", "icgiaehcejabefbg", "eiechjcjajdjjjcbhi", "eefjcjggbjda", "fihbiaafghbjacdggej", "gaheadcdfefef", "fcdjgggaagaccfhj", "jbhbj", "dbeadfjdgdhc", "gdjcic", "cifjdcfeedab", "jacdggchfgfjf", "edggieccfgfebicdh", "ghfajadffjeicieg", "fgdhgffgb", "jicdgggahdhdhbi", "bbgg", "fhdhfjigbbjfejdhcc", "haieafgdaabhiiifc", "aajiagjhhhgeagehcdef", "hgdafaehjiifdif", "adebdcb", "gecbgigc", "dfdjjdccjdifggidhbbh", "ji", "acdhabebefdabdig", "jjfhccbb", "ieifihffcjgedegcie", "efdhhghb", "jic", "iibjbccgi", "ffffajgbbcgiaabdja", "efifj", "jfhghfcjccgdjhhaijf", "ejbcfhgigge", "jbhibci", "igiejg", "fhaj", "hccfjiihdheihfgadjh", "eeb", "cahijghj", "cdigbcbedcfjb", "ciegfgdgeejcij", "i", "bjhjcbddggie", "cdgjjdecbehb", "ccgdcdbfgeccegaeacbb", "ieejeb", "iecdcbd", "chii", "fcbgfaibffjabjg", "iidegjdigaiebeddjdh", "gachcjabh", "fijjebdefdhfgcjccbei", "jediheaif", "cdfihjgeegfdg", "bdj", "cebfejajgbg", "ghcbdfieiigcdh", "cfbjbg", "ebfhfeigeghfi", "chjihe", "abeagebeffdifjg", "hcebjgaffeehgfb", "idhgjbcfjfbaj", "hijbgbiebbcfgihbf", "ijib", "dcedeeidj", "fbdjj", "jciijbdjgbiahiefb", "gfiaddehdgagba", "jhbebgj", "hfcbcejabbjbce", "icdcccbddcjigdhbdfi", "fdfbjcdifjdebdifff", "fhcidafb", "iccjhchahhheejcda", "adfcbdgdaaeefbab", "jbcfbg", "bjdaicdjbjcgbcbbcbh", "dice", "fhhicbdeeahaibfhjf", "ccdeabbfebjihbhgfag", "bheaccdgfbjfjcjei", "jjfiafba", "cadcdeegbbcchii", "ffdegjgjjadhe", "ii", "dcbhdaaeibdieijbcab", "egbeegdffdcjjeegg", "dcbihcfhigif", "ijghbbhhfbbcdb", "daajgfahdiifjhaifaif", "ghdgjbfdhbafdab", "iabfaejjfaie", "bbbci", "gfghfidach", "fccafhidc", "ebfbefg", "jidbhidij", "ihba", "jciciehiffgjhijjjedc", "gdacdjebffjeeie", "hibdech", "ci", "dgce", "bfdjdiffdgjdj", "ffbajddhdi", "aae", "ffbigadaaa", "gfgdfcaiadeaagbafb", "caghbfhjddfeaedd", "acfcebbjfhdjjde", "idgdjbbe", "dhc", "cegg", "aebcihdicbaggejef", "djifcbbffeidhb", "ieadcadcgjjdcbejbcae", "baheaichgigfgfece", "dhbfejcihgchii", "badbbaceijdehe", "ajghfjadicijabe", "hfjifghcjdaifgcaej", "ecceebeaigadj", "bcbbbajigd", "gjahfabebdd", "ig", "gjjefgajggidgjbg", "igcdhddegcccbiagjc", "bibjddacghi", "hegbiahdjgegdibc", "dhchfcgcdhafejb", "ffbfijceaf", "fehd", "fedadadbddahdeaecj", "jdfdgddfihbcidh", "gig", "haceghcdcebbbfccdi", "jaagbiieibebehb", "eegjhebdd", "a", "degecadgiedecib", "hfaejeeieaijabijb", "hgciaef", "fjehbde", "cehedadfbhijh", "ff", "afef", "cgfjhicbiafhcac", "iebha", "djgagdigbbegebidj", "aajdcdhaihcac", "hfecbjhhbgfa", "jegifhi", "jchgadbjghaifi", "eccabiagg", "aifccc", "dhefcihcaf", "df", "adjidjidejedbbg", "ffgicfidjgcbbcf", "ggbicjaigbccfef", "gccibedgfhbihbeajibj", "daaehih", "bh", "jagiajijeic", "jeacebgcj", "iccff", "jjeab", "gjabifjhh", "dahjfahacadajgiig", "jfgihdah", "ggfbfhdhjcbdhecj", "bfeehccbgf", "iaeigffd", "ded", "djgjcaejei", "habibida", "hfhhafaj", "ghbheddcaagbjefg", "geidbcihihihfi", "jhjfjjifbdfaiaecjggi", "acjd", "ahbcjgddicj", "ecfeebhfce", "ajbbaegad", "dhahcfjdaaeajahgacfh", "jdhjhbiciechbghdcc", "ifabaacdfjied", "gdjdfcadfb", "ecjebjhg", "cbhebajfaaaegh", "hjagd", "ga", "cfhdibcgfdeehhgjjiaa", "jhegjbeidhaff", "afgcjfebgjjdaebh", "ifgjihbgecjjicif", "ccbiefbjgdbefbaefad", "db", "jcdicigebheeieiafb", "cjfhbbhgdbahdeja", "afgbbdhed", "cbegb", "chhidjf", "ibdgbedcfagbjd", "cabfeicafced", "aa", "behj", "cdghdhcifih", "cgjfjbfdbjbijcffdf", "ahfefejdfegec", "fjbgdicifb", "ggejjgafhegibg", "fhaghihbfjgecah", "ifejbjfecighefb", "ijci", "dhihdjjjcjdhjabjfei", "abdedeigfb", "gdjcefcibjeebdeb", "eccdcbeagb", "hgebcjcfcbcccbhid", "eecacechbcecajj", "fdjbdhhidbgcehigjfc", "jeaihefgejjgjigi", "dhcje", "accccdigcdddceca", "djjbcdccj", "hjcgaibfjhjiaf", "dhajjihdeff", "hejebj", "gehifc", "jhcjabgibcaagecfjdfb", "gdgfjcfc", "egdh", "jgej", "did", "ehjbaifiaa", "ceihedadgbhahhe", "edcegedhh", "ihedc", "jcaeaicgfjj", "jcgfcecbgcag", "iiceeihedhfgbj", "aajejefghheg", "fcjee", "fdbb", "fcjfijhagjiecfa", "jfbfedgagfedheb", "hhiagi", "jfgjadjiihjjefchha", "cegida", "jgegghhb", "hbjbhhfjchfi", "adbfcijdjbidg", "icf", "hjcgge", "hieedgaaaffg", "ajgageheebid", "jdbebdc", "aeaagf", "dagccfcgeiid", "hg", "afceebiafjceadc", "cfhaagebfc", "fihdiff", "jcajjhd", "ddbdfjjdc", "jbecijhdfbhcfjab", "fggfgbafhjejd", "hijadacifgdchegh", "jid", "adjhf", "gadfcija", "bdeidi", "jfccehfhbecchgccg", "gbbdbb", "jidjhbdhcccdbdhcbbf", "bgheejgddeejejej", "hbhefdj", "jacgi", "fifjbfciiejbc", "jieii", "bafgacjifhighf", "jcbhiaigcai", "bhjeh", "ghigdbbjcgiijjfcb", "ijgffdheiaefebhgh", "ghijeidgjggbb", "hcfdacebgjgjf", "cdfe", "iehchadfeiada", "dbg", "ihjceafefh", "fahehgbcejjfc", "gdeaefgjgjedfdgha", "jeh", "ifd", "jig", "cgjhifgg", "cddgibcdiiaecfiihgfe", "cfjhibbccije", "deeiafdie", "bdedifdjghjjccegg", "gcgfaejadida", "idjeehhe", "fadehe", "abjjdddhfdfj", "bicef", "adcgdeagaijicghijhi", "aggiididcbi", "igfbibbjgh", "bfjccccfjicgdbdgah", "fcaciedhcbb", "cjaiaehd", "faic", "hiaef", "bjjhfjgdiidcbfbf", "jacjheceg", "djeejiehgh", "bdjag", "bfcfjigbjfcccbihj", "jefdef", "bjgcbahf", "bbjidcbfgafhifheihc", "hcajfcd", "ifjhahbdahgacgdejg", "cfjehhijfcdfeb", "agffcfacib", "jfagbaifeabbgjaaag", "ieicegjjebagdjic", "ghhjgcc", "hjiccgcjgcace", "gbehefggiebj", "bdhdghjicfaadhbhjgf", "cefdcdibdjaja", "aegib", "cgfiiifaeaiehijhja", "fhfibahdhifbbag", "ciceaghdhcaaicdfh", "egehic", "ccfjddhedc", "fjhgfiicgbjbhaf", "fhacidf", "fdgdcbjccbdc", "chb", "ibbajefiajdedbg", "dc", "cefefafffeef", "cjfiehfciejeihfc", "dieaihbfeebihhfb", "fgdfeiifi", "dggjbihci", "eidchjdecfe", "fjceb", "ibdccb", "cfdhgifb", "jchiccchaebdeajbjdg", "fbgdgjcj", "cdiechfgiabagbcgfegd", "egceicbhddaif", "jhbjghbg", "aebfa", "ihibjcehbcgdg", "fiih", "iiidebhaajgfegc", "bebhgfeegahibbe", "bbdcdggfihhihhb", "deehhgjh", "gdhchigfgf", "cchicidi", "gbddcgcfafeie", "didaicgbfcgdbha", "jbigcjegeghiahd", "ja", "ejbaijgbeedf", "gjdghidedbijhj", "efabghiedfgahggdhffa", "dcfjfffebhddhdajghhe", "jdbccjje", "hhehcahigjafjj", "ejbdahecgb", "agbcjihfaafddfbbjj", "cjjdjbceih", "cjgcihghijebadeh", "idfefdb", "icfejcfdifca", "jghdccegfiibajedcb", "eahh", "ijg", "hbgcceg", "echdgbbagagccbhjehdg", "aahajadjchbdd", "ghjdbfafbeeaicjija", "ijfgijifjcbjefdidga", "bcffbagdgecjbib", "eegbacihcfffgcac", "bhbhehahgg", "gichfjieafdg", "hjjfiecfdcicedcedg", "edccg", "gbb", "ifagie", "ffbbbbibdef", "agfgfahicjajhbibbccb", "jfabejcegjifdaaegcfi", "gjajdihcigeigfieaa", "bjgfifaaedfhabbgcjhd", "cgeebjdfibci", "gdijjecddhfihdcad", "jfbajjjdebdfdjegg", "iicegfidfaahdgg", "eaabjfejdbfhhgga", "ccjj", "fecbechbjgf", "cehbigaifjggb", "efcfaa", "idcjca", "heifjaffdagihjdhhje", "hhhg", "bcceabajhibcgge", "cficadfjafehcbfbe", "ddgac", "fgcbhchccgidhibjd", "cfejacedhcbfbbdei", "aadicbdgdeag", "hbiidhd", "jdhejfddigaig", "dijjfhdehhgi", "jbhbh", "decf", "abhdfbdjdgaigdbcdac", "eadbghibfchhegjegaa", "iajeahcdihdahjchejcj", "aehbjdajff", "efejhaihchjjjdaj", "afaabaedac", "jgecfabhfhacegcce", "jbacedhij", "jfhfeaigbehfeddgj", "aibiebfjbbjihdafe", "daefghfdghhdha", "cgdgbiaijcchcdbhdg", "fhhej", "ihahff", "hdiafcdg", "bhgeigbdcjiaidfg", "ebfcgbicei", "jigfgfbif", "ecfgic", "be", "jbfedbj", "hfgeadbdffb", "cdadbhfggbdcfg", "gfbibhfid", "dggjhibedefcidcidh", "djgdebibbeihdb", "idjead", "jddfbhfiigihi", "dahhejdh", "aigai", "gihjfjhbjga", "dcdhhchgcifij", "hbdbffehccggcahgdij", "aicgf", "bjdbgjjbihjfjjjei", "cghdceieadgb", "feeabeajdia", "chgdgdidhac", "bi", "jecedadaiia", "ifdhddbhegbhgbbhb", "dahhdbaahgdadfdjegh", "gbgajagegifg", "ibeageaagibci", "ajj", "adabbjadgaibibadbh", "cjgghfbfhafddahjgfdg", "iffah", "dcbifaaijhc", "bd", "ffghbj", "afg", "fadgedcjiedfjedaehf", "edagghhggcbdbegeae", "afcfibhcbbjbdeg", "aaggi", "aijcijjfjhb", "jiejbjdiideibae", "bggagfdejjaigeggagd", "adfaecebifgdgdi", "ejecgjhjeiiiagjchei", "efhifgfddhhhfcdfhcai", "djja", "ehdfdidgahgafi", "ejdbhd", "ageeediidj", "gjeachdhcibc", "fddbaaagfacbjhch", "ide", "echagfgciaadhe", "fheegadifechfad", "ehadcaefjbifdfjjg", "hchajigibaaei", "didbgajbggiadeeha", "gab", "djjbafiai", "dhajgdacchjefd", "ce", "caihabbfdcadhedigji", "ahbgjhheijjdhjhe", "ajaahaadcbgdaab", "fghed", "cijggfih", "ifdbfegfibeeii", "gcjbdjbbichcbjigadh", "ibgjheegabjgdj", "fidhdhjjedfecfbdid", "dcbeeadg", "ihja", "iedhgehbgjhjfibhg", "bdiefchcfcebff", "ifjdddeaj", "hbfiigihhbd", "haifch", "cijiee", "agahdjjfbffhajjh", "jehabbgihejcdbg", "gbcibgibcbb", "hbdiciccjbcdhgie", "jfbdg", "iedejciga", "hfhcba", "aicidjdabdaaaeb", "ageeabaaeejdad", "ecdb", "egejcicbdhc", "cfiafhjbbi", "dfcifebiei", "djig", "dgbbhfedcbccafd", "eahjbefdjgeggifdiahc", "jbiefcfdcfdjfjef", "if", "jhfdhgeidei", "jcehfafffieheajchccf", "cgghi", "cghfffhceihdcaihic", "ibaiajgcbig", "hedeaddfbahhaaccfef", "abfecegeciggaad", "ceiafhibhefgafjjeb", "ggcgajfdacgbh", "dgjj", "abiceabf", "dhhbhhdhhgifjacaae", "fajchbgfbicfad", "bagjjageadffgafhjf", "bgcfjjeefchhjih", "iibjigdddi", "bda", "fdg", "iidjbdfded", "cidcbjh", "fbeibcjjddbcch", "bgiif", "jaijdebhffe", "biiiegd", "jgjjhe", "gcdidbbfddjeabfad", "jegibaaab", "hiidegaa", "hdccdcdfhcaibiajdecd", "aihjgehhcdfacf", "fbeggaihfcijgdjjf", "beef", "jjjgjjf", "fgidffifc", "hciacchdbggdjcgbibfe", "fbcfjgbeddaeadhh", "jahejfffiehijhjaedjd", "efjggaaj", "hcjjbjjgagjegedgf", "ddhbhjeedcbfiejigd", "dffddbgbefdeibdigfjf", "feijgcj", "bggadgiehgbhhgefd", "addbeiggicjcbidc", "fdjjcdheaacjgdae", "jjbh", "ggddgdcbagjbdbjag", "acchhgbdfih", "bge", "jjfbigbgd", "baggfhdeeaibeigjfgag", "bjjcaihicjecahh", "eibg", "fffhfijeiffeafgg", "afjeeddgciab", "djafb", "biegdfidfjgbecc", "efhehhbgjg", "ejfbjjeabjf", "ddibajeejdbfda", "cggdfcdijjig", "cea"};

        String s = words[0] + words[384];
        System.out.println(s);
    }

    /****************************************************************
     * 进制
     * @author: wg
     * @time: 2020/9/7 15:10
     ****************************************************************/
    @Test
    public void radix() {
        int l = Integer.parseInt("12", 3);
        System.out.println("表明 参数 是 3进制数 , 转成十进制后是: " + l);  //

        String s = Integer.toString(10, 3);
        System.out.println("任意10进制数 转化成任意进制 : " + s);

        int i = 1 % 3;
        System.out.println(i);


        int wo = 5201314;
        String s1 = Integer.toString(5201314, 16);
        System.out.println(s1);

        int i1 = Integer.parseInt("4f5da2", 16);
        System.out.println(i1);

    }

    /****************************************************************
     * 字符串
     * @author: wg
     * @time: 2020/9/7 15:28
     ****************************************************************/
    @Test
    public void substr() {
        String s = "abcdefghijklmn";

        String substring = s.substring(2, 4);
        System.out.println(substring);

    }

    /************************************************************************
     * @description: 依据一行3个的样式 输出
     * @author:
     * @date: 9:45  2021/9/1
     ************************************************************************/
    @Test
    public void layoutTest() {
        String[] arr = {"展昭", "小黑", "二哈", "张无忌", "张三丰", "123", "小323", "二23哈", "张232无忌", "2323三丰"};
        StringBuilder tr = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            tr.append(" ").append(arr[i]);
            if ((i + 1) % 3 == 0) {
                System.err.println(tr.toString().replaceFirst(",", ""));
                tr = new StringBuilder();
            }
        }
        System.err.println(tr.toString().replaceFirst(",", ""));
    }

    public void getPrescription() {
        double sqrt = sqrt(24, 5);
        System.out.println(sqrt);
    }

    /**
     * 用二分法将正整数n开方
     *
     * @param n
     * @param precision 保留的小数精度
     * @return
     */
    public static double sqrt(int n, int precision) {
        double lower = 0;
        double high = n;
        double mid = 0;
        double threshold = Math.pow(10, -precision);
        do {
            mid = lower + (high - lower) / 2;
            if (mid * mid > n) {
                high = mid;
            } else {
                lower = mid;
            }

        } while (Math.abs(mid * mid - n) > threshold);

        return new BigDecimal(mid).setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /***************************************************
     * 排序
     * @author: wg
     * @time: 2020/11/14 0:12
     ***************************************************/
    @Test
    public void sort() {
        int[] arr = {1, 3, 56, 4, 67, 23, 43};

        int a = 0;
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - j - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    a = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = a;
                }
            }
            System.out.println(Arrays.toString(arr));
        }


        //for (int i = 0; i < arr.length - 1; i++) {
        //    if (arr[i] > arr[i + 1]) {
        //         a = arr[i];
        //        arr[i] = arr[i + 1];
        //        arr[i + 1] = a;
        //    }
        //System.out.println(Arrays.toString(arr));
        //}


        //System.out.println(Arrays.toString(arr));


    }

    /**
     * 转全角的方法(SBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input
     * @return
     */
    public static String toSBC(String input) {
        //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }


    /**
     * 转半角的函数(DBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    @Test
    public void testBanJiao() {

        String input = "asd,阿萨,分隔符";
        String toSBC = toSBC(input);

        System.out.println(toSBC);
    }

    /****************************************************************
     * 创建 arraylist
     * @author: wg
     * @time: 2020/11/26 17:51
     ****************************************************************/
    @Test
    public void arrayListTest() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(0, 1);
        System.out.println(arrayList.get(0));

    }

    /****************************************************************
     * 字符串去空格
     * @author: wg
     * @time: 2020/12/21 17:54
     ****************************************************************/
    @Test
    public void trimString() {

        String s = "哈 进  王帅虎            董 强（老院）";

        String[] split = s.trim().split("  ");
        System.out.println(Arrays.toString(split));

        String s1 = "谢  勇";
        String[] split1 = s1.trim().split("  ");
        System.out.println(Arrays.toString(split1));

    }

    /****************************************************************
     * 日期 字符串
     * @author: wg
     * @time: 2020/12/22 15:28
     ****************************************************************/
    @Test
    public void formatString() {

        try {
            String dateString = "12月2日";
            int year = LocalDate.now().getYear();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            Date parse = simpleDateFormat.parse(year + "年" + dateString);
            System.out.println(parse);

            // 获取今天是周几
            LocalDate now = LocalDate.now();
            DayOfWeek dayOfWeek = now.getDayOfWeek();
            System.out.println(dayOfWeek);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /****************************************************************
     * 去中间空串
     * @author: wg
     * @time: 2020/12/24 10:57
     ****************************************************************/
    @Test
    public void innerSpace() {

        String s = "杨培新,,,,,齐跃,刘绍偈,孟令皓,张楷婕,鞠伟,王淼,,刘瑶,李敏,";

        System.out.println(s.replace(",", " "));
        System.out.println(s.replace(",", ""));


        String[] members = s.split(",");
        System.out.println(Arrays.toString(members));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < members.length; i++) {
            if (com.alibaba.druid.util.StringUtils.isEmpty(members[i])) {
                continue;
            }
            stringBuilder.append(members[i].trim());
            stringBuilder.append(",");
        }

        System.out.println(stringBuilder.toString().substring(0, stringBuilder.length() - 1));

        /********************第二种情况*************************************/
        String me = "王  刚";

        System.out.println(me.replace(" ", ""));
        StringBuilder builder = new StringBuilder();
        char[] chars = me.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
            if (chars[i] == 32) {
                continue;
            }
            builder.append(chars[i]);
        }

        System.out.println(builder.toString());

        int a = 0;
        String as = "0";
        System.out.println(as.equals(a));

    }

    @Test
    public void test1() {
        TrafficRestriction trafficRestriction = new TrafficRestriction();
        Integer id = trafficRestriction.getId();
        System.out.println(id);

        System.out.println(trafficRestriction.getFridayRestrictedNum());
    }

    /****************************************************************
     * @description: 找出中文
     * @author: wg
     * @time: 2021/1/7 17:49
     ****************************************************************/
    @Test
    public void getChinese() {

        String s = "0不限行";


    }


    /****************************************************************
     * @description: json对象数组
     * @author: wg
     * @time: 2021/1/20 11:25
     ****************************************************************/
    @Test
    public void testJsonObject() {
        //json字符串
        String jsondata = "{\"contend\":[{\"bid\":\"22\",\"carid\":\"0\"},{\"bid\":\"23\",\"carid\":\"0\"}],\"result\":100,\"total\":2}";
        JSONObject jsonObject = JSON.parseObject(jsondata);
        //map对象
        Map<String, Object> data = new HashMap<>();
        //循环转换
        Iterator it = jsonObject.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            data.put(entry.getKey(), entry.getValue());
        }
        System.out.println("map对象:" + data.toString());

        Object contend = data.get("contend");
        System.out.println(contend);

        System.out.println();

        /************ -> 下面的简洁  ************/

        HashMap<String, String> hashMap = new HashMap<>();

        JSONArray ss = jsonObject.getJSONArray("contend");
        if (ss != null) {
            for (int i = 0; i < ss.size(); i++) {
                JSONObject jsonObject1 = ss.getJSONObject(i);
                String k = jsonObject1.getString("bid");
                String v = jsonObject1.getString("carid");
                hashMap.put(k, v);
            }

        }

        System.out.println(hashMap);

    }

    /****************************************************************
     * @description: localTime localDateTime localDate
     * @author: wg
     * @time: 2021/2/24 10:00
     ****************************************************************/
    @Test
    public void localTimeTest() {
        LocalTime now = LocalTime.now();
        System.out.println(now);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.plusHours(32L));

        LocalDateTime localDateTime1 = new Date(1469191785768L).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime1);
    }

    @Test
    public void app() {
        int i = 1 << 24;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));

        int maxValue = Integer.MAX_VALUE;
        System.out.println(Integer.toBinaryString(maxValue));
        System.out.println(maxValue);

        int solt = i & maxValue;
        System.out.println(solt);
    }

    String mysqlDriver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/wg?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
    String user = "root";
    String password = "root";
    Connection conn;

    @Test
    public void createDynamicTable() {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("tableName", "test_03");
        hashMap.put("id", new MyField("id", "int"));
        hashMap.put("userName", new MyField("user_name", "varchar(200)"));

        String dropTable = "DROP TABLE IF EXISTS " + hashMap.get("tableName");
        String createTableSql =
                "  CREATE TABLE " + hashMap.get("tableName") + "  (\n" +
                        ((MyField) hashMap.get("id")).getField() + "  " + ((MyField) hashMap.get("id")).getFieldType() + " not null, \n" +
                        ((MyField) hashMap.get("userName")).getField() + "  " + ((MyField) hashMap.get("userName")).getFieldType() + "  null, \n" +

                        "    `create_by` varchar(32) NULL DEFAULT NULL COMMENT '创建人',\n" +
                        "    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
                        "    `update_by` varchar(32) NULL DEFAULT NULL COMMENT '更新人',\n" +
                        "    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',\n" +

                        "        PRIMARY KEY (`id`) USING BTREE \n" +
                        "        )";


        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            boolean executeDrop = statement.execute(dropTable);
            boolean executeCreate = statement.execute(createTableSql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void insertValue() throws SQLException {
        String insertValueSql = "insert into user_01(id, create_by) values(123, 'admin') ";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(insertValueSql);
        System.out.println(i);

    }

    /***************************************************
     * @decription jdbc
     * @author: wg
     * @time: 2021/5/27 22:41
     ***************************************************/
    public Connection getConnection() {
        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                return conn;
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***************************************************
     * @decription 字符串 equals ==
     * @author: wg
     * @time: 2021/5/27 22:40
     ***************************************************/
    @Test
    public void objStringTest() {

        String s1 = "hello";
        String s2 = new String("hello");
        String s5 = "hello";

        System.out.println(s1 == s5); // true

        String s3 = "hello" + "world";
        String s4 = "helloworld";

        System.out.println(s1 == s2); // false

        System.out.println(s3 == s4); // true

        System.out.println(s1.intern() == s2); // false

        String s6 = "world";

        String s7 = s1 + s6;

        System.out.println(s4 == s7); // false

        Integer i = 1;
        if (i == 1) {
            System.out.println("Integer == 1");
        }

    }


    /***************************************************
     * @decription hashmap hashtable
     * @author: wg
     * @time: 2021/5/27 22:43
     ***************************************************/
    @Test
    public void mapTest() {

        HashMap<Object, Object> hashMap = new HashMap<>();
        Hashtable<Object, Object> hashtable = new Hashtable<>();

        hashMap.put(null, "123");

        // hashtable 不允许空键, 也不允许空值
        //hashtable.put(null, "123");
        hashtable.put("null", null);
    }

    /************************************************************************
     * @description: map test 
     * @author: wg
     * @date: 15:17  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void mapTest2() {
        HashMap<String, String> map = new HashMap<>();
        String a = map.remove("123");
        System.out.println(a);

        System.out.println(map.get("123"));

        map.put("123", "1234");
        System.out.println(map.remove("123"));
    }

    /***************************************************
     * @decription 根据对象单个属性去重
     * @author: wg
     * @time: 2021/5/28 2:07
     ***************************************************/
    @Test
    public void noRepeat() {

        Random random = new Random();

        List<User> a = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            User user = new User();
            int i1 = random.nextInt(10000);
            user.setAge(i1);

            a.add(user);
        }

        System.out.println("===  " + a.size());

        long l1 = System.currentTimeMillis();

        // 第一种 常用的方式
        HashSet<User> users = new HashSet<>(a);
        long l2 = System.currentTimeMillis();
        System.out.println("最简单的 用时: " + (l2 - l1));
        System.out.println("set -> " + users.size());

        long l3 = System.currentTimeMillis();
        // 第二种, stream 方式
        ArrayList<User> collect = a.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getAge))),
                ArrayList::new)
        );

        long l4 = System.currentTimeMillis();
        System.out.println("stream 用时: " + (l4 - l3));
        System.out.println("---  " + collect.size());
    }

    @Test
    public void randomTest() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int i1 = random.nextInt(10);
            System.out.println(i1);
        }
    }

    /*****************************************************
     * @params:
     * @description: char 测试
     * @author: wg
     * @date: 2021/6/29 10:14
     *****************************************************/
    @Test
    public void basicDataType() {

        char a = 1;
        System.out.println(a);

        char b = '1';
        System.out.println(b);

        char c = 2;
        char d = c;
        int e = c;
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);

    }

    /*****************************************************
     * @params:
     * @description: stream test
     * @author: wg
     * @date: 2021/6/30 15:03
     *****************************************************/
    @Test
    public void streamTest() {
        // 生成若干个随机数
        List<Double> limit = Stream.generate(Math::random).limit(5).collect(Collectors.toList());
        limit.forEach(System.out::println);

        // 取最小值
        double asDouble = limit.stream().mapToDouble(Double::valueOf).min().getAsDouble();
        System.out.println("最小值 " + asDouble);
        // T t1 = list.stream()
        //         .filter((T t) -> getter(detailFieldMap.get("remainStrengthDetailFieldName"), t) != null)
        //         .min(Comparator.comparing(e -> (BigDecimal) getter(detailFieldMap.get("remainStrengthDetailFieldName"), e)))
        //         .get();

        // 排序
        // limit.sort(Double::compare); // 从小到大
        limit.sort(Comparator.comparing(Double::valueOf, (e1, e2) -> e2.compareTo(e1)));  // 从大到小
        System.out.println("collect 排序:  " + limit);

        // 过滤
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(" ?? "));
        System.out.println("合并字符串: " + mergedString);

        // 转map
        Map<String, String> map = filtered.stream().collect(Collectors.toMap(item -> item, item -> item + "  wg", (k1, k2) -> k2));
        map.forEach((k, v) -> {
            System.out.print(k);
            System.out.print(" --- ");
            System.out.print(v);
            System.out.println();
        });

    }

    /*****************************************************
     * @params:
     * @description: PropertyUtils test
     * @author: wg
     * @date: 2021/6/30 17:38
     *****************************************************/
    @Test
    public void propertyTest() {
        BankFlow bankFlow = new BankFlow();
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(bankFlow);
        Stream<PropertyDescriptor> stream = Arrays.stream(propertyDescriptors);
        stream.forEach(System.out::println);

        for (int i = 0; i < propertyDescriptors.length; i++) {
            String fieldType = propertyDescriptors[i].getPropertyType().toString();
            System.out.println(fieldType);
        }

    }

    /*****************************************************
     * @params:
     * @description: stream filter sort 过滤 ,  排序
     * @author: wg
     * @date: 2021/7/8 11:15
     *****************************************************/
    @Test
    public void filterTest() {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("展昭");
        arrayList.add("包拯");
        arrayList.add("赵虎");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "展昭");
        hashMap.put("2", "包拯");

        boolean b = arrayList.stream().anyMatch(e -> {
            return e.contains(hashMap.get("1"));
        });
        System.out.println("b -> " + b);
        System.out.println();

        List<String> collect3 = hashMap.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        System.out.println("collect3 -> " + collect3);
        System.out.println();

        boolean b1 = arrayList.containsAll(collect3);
        System.out.println("b1 -> " + b1);
        System.out.println();


        List<String> collect2 = arrayList.stream().filter(e -> {
            System.out.println(e);
            return e.equals(collect3.get(0));
        }).collect(Collectors.toList());

        System.out.println("collect2 -> " + collect2);
        System.out.println();

        AtomicInteger count = new AtomicInteger();
        List<String> collect = arrayList.stream().filter(item -> {
            return hashMap.entrySet().stream().allMatch(entry -> {
                System.out.println(entry.getValue());
                System.out.println(count.getAndIncrement());
                return item.equals(entry.getValue());
            });
        }).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("-------");

        arrayList.forEach(System.out::println);
        System.out.println();

        List<String> collect1 = arrayList.stream().sorted().collect(Collectors.toList());
        collect1.forEach(System.out::println);
        System.out.println();

        Student student = new Student("111", 111);
        Student student1 = new Student("222", 111);
        Student student2 = new Student("333", 333);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student);

        System.out.println(students);
        System.out.println();

        students.sort(Comparator.comparing(Student::getAge).reversed());
        System.out.println(students);

        System.out.println("---group---");
        Map<Integer, List<Student>> group = students.stream().collect(Collectors.groupingBy(Student::getAge));
        group.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /*****************************************************
     * @params:
     * @description: 多字段联合 搜索 过滤
     * @author: wg
     * @date: 2021/8/11 18:24
     *****************************************************/
    @Test
    public void dutest() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("11", 11, 1));
        students.add(new Student("22", 22, 0));
        students.add(new Student("33", 33, 1));

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", 11);
        map.put("age", 11);
        map.put("sex", 1);

        List<Student> collect = new ArrayList<>();
        if (map.containsKey("name")) {
            collect = students.stream().filter(student -> student.getName().equals(String.valueOf(map.get("name")))).collect(Collectors.toList());
        }
        if (map.containsKey("age")) {
            collect = students.stream().filter(student -> student.getAge() == (Integer) map.get("age")).collect(Collectors.toList());
        }

        // List<Student> collect = students.stream().filter((Student student) -> {
        //             return student.getAge() == (Integer) map.get("age")
        //                     && student.getName().equals(String.valueOf(map.get("name")))
        //                     && student.getSex() == (Integer) map.get("sex");
        //         }
        // ).collect(Collectors.toList());

        System.out.println(collect);
    }


    /*****************************************************
     * @params:
     * @description: string to BigDecimal
     * @author: wg
     * @date: 2021/7/8 12:30
     *****************************************************/
    @Test
    public void decimalTest() {

        BigDecimal bigDecimal = new BigDecimal(11.0000);
        String s = "11.123344";

        System.out.println(bigDecimal.equals(s));

        System.out.println(bigDecimal.hashCode());
        System.out.println(s.hashCode());

        System.out.println(bigDecimal.toString());
        System.out.println(String.valueOf(bigDecimal));

        DecimalFormat decimalFormat = new DecimalFormat("0.0000");

        String format = decimalFormat.format(Float.valueOf(s));
        System.out.println(Float.valueOf(s));
        System.out.println(format);

        BigDecimal bigDecimal1 = new BigDecimal(format);

        System.out.println(bigDecimal1.compareTo(bigDecimal));

        System.out.println("new BigDecimal(format): " + new BigDecimal(format));

        DecimalFormat decimalFormat1 = new DecimalFormat("0.00");
        String s1 = decimalFormat1.format(Double.valueOf(s));
        System.out.println("new BigDecimal(s1): " + new BigDecimal(s1));

        Object aa = "  ";
        System.out.println(ObjectUtils.isEmpty(aa)); // false
        System.out.println(ObjectUtils.isEmpty(String.valueOf(aa).trim())); // true
        decimalFormat.format(Double.valueOf(String.valueOf(aa).trim()));

    }

    /*****************************************************
     * @params:
     * @description: decimalFormat 中 占位符 0 和 # 的区别
     * @author: wg
     * @date: 2021/7/9 15:31
     *****************************************************/
    @Test
    public void decimalTest2() {
        DecimalFormat format1 = new DecimalFormat("000,000.000");
        DecimalFormat format2 = new DecimalFormat("###,###.###");

        System.out.println("format1.format('11111111111') :  " + format1.format(new BigDecimal("11111111111")));

        System.out.println("format2.format('11111111111') :  " + format2.format(new BigDecimal("11111111111")));

        DecimalFormat format3 = new DecimalFormat("0,000.000");
        DecimalFormat format4 = new DecimalFormat("#,###.###");

        System.out.println("format3.format('0011111111111') :  " + format3.format(new BigDecimal("0011111111111")));

        System.out.println("format4.format('0011111111111') :  " + format4.format(new BigDecimal("0011111111111")));
    }

    /*****************************************************
     * @params:
     * @description:
     * @author: wg
     * @date: 2021/7/9 15:57
     *****************************************************/
    @Test
    public void zhuangxiangTest() {
        // 没有异常
        Double aDouble = Double.valueOf("0011111111111");
        System.out.println("aDouble:  " + aDouble);

        // 没有异常
        String s = "1123123";
        Double aDouble1 = Double.valueOf(s);
        System.out.println("aDouble1:  " + aDouble1);

        // 有异常 NumberFormatException , 因为数字里有逗号
        DecimalFormat decimalFormat = new DecimalFormat("0,000.000");
        String format = decimalFormat.format(new BigDecimal("0011111111111"));
        System.out.println("format:  " + format);
        Double aDouble2 = Double.valueOf(format);
        System.out.println("aDouble2:  " + aDouble2);

    }

    /*****************************************************
     * @params:
     * @description: 得出结论, optional 不能判断空串
     * @author: wg
     * @date: 2021/7/12 14:10
     *****************************************************/
    @Test
    public void optionalTest() {

        String name = "123";
        Optional<Object> o = Optional.ofNullable(name);

        System.out.println(o);
        System.out.println(o.get());
        System.out.println(o.isPresent());

        ArrayList<Object> list = new ArrayList<>();
        Optional.ofNullable("").ifPresent(it -> list.add(it));

        System.out.println(list.size());
    }

    /*****************************************************
     * @params:
     * @description:
     * @author: wg
     * @date: 2021/7/15 14:09
     *****************************************************/
    @Test
    public void twoDimensionalToOneDimensional() {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<ArrayList<String>> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add("wg" + i);
        }

        for (int i = 0; i < 10; i++) {
            list2.add(list1);
        }

        list2.forEach(System.out::println);
        System.out.println();

        ArrayList<Object> list3 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            ArrayList<String> strings = list2.get(i);
            for (int j = 0; j < strings.size(); j++) {
                String s = strings.get(j);
                list3.add(s);
            }
        }

        list3.forEach(System.out::println);
    }

    @Test
    public void size() {
        System.out.println(Integer.MAX_VALUE);
    }

    /*****************************************************
     * @params:
     * @description: string test
     * @author: wg
     * @date: 2021/7/23 15:30
     *****************************************************/
    @Test
    public void stringTest() {
        String str2 = String.format("Hi,%s:%s.%s", "老鹰", "是一种", "鸟类");
        System.out.println(str2);
    }

    /*****************************************************
     * @params:
     * @description: BeanUtils.copyProperties(destination, origin);
     * @author: wg
     * @date: 2021/7/26 13:47
     *****************************************************/

    /*****************************************************
     * @params:
     * @description: collectors.tomap
     * @author: wg
     * @date: 2021/8/4 15:49
     *****************************************************/
    @Test
    public void toMapTest() {
        List<Student> list = new ArrayList<>(3);

        list.add(new Student("name1", 1));
        list.add(new Student("name1", 2));
        list.add(new Student("name1", 3));

        // 生成的 map 集合中只有一个键值对：{version=6.28}
        Map<String, Object> map = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge, (v1, v2) -> v1));

        System.out.println(map);
    }

    /*****************************************************
     * @params:
     * @description: StringUtils
     * @author: wg
     * @date: 2021/8/5 15:21
     *****************************************************/
    @Test
    public void testStringUtils() {
        String s = " ";
        System.out.println(s.length());
        System.out.println(StringUtils.isEmpty(s));

        boolean blank = org.apache.commons.lang3.StringUtils.isBlank(s);
        System.out.println(blank);
        boolean empty = org.apache.commons.lang3.StringUtils.isEmpty(s);
        System.out.println(empty);

        System.out.println("~~~~~~~~~~~~~~~~");
        String s1 = "  ";

        System.out.println(s1.length());
        System.out.println(StringUtils.isEmpty(s1));

        boolean blank1 = org.apache.commons.lang3.StringUtils.isBlank(s1);
        System.out.println(blank1);
        boolean empty1 = org.apache.commons.lang3.StringUtils.isEmpty(s1);
        System.out.println(empty1);
    }

    /*****************************************************
     * @params:
     * @description: 科学计数法
     * @author: wg
     * @date: 2021/8/9 11:13
     *****************************************************/
    @Test
    public void kexuejishufa() {
        long u = 1_111_111_111_111L;
        double d = 6.22848E+18;
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal.toPlainString());

        long l = bigDecimal.longValue();
        String format = String.format("{0:C3}", l);
        System.out.println(format);

        double s = 1.7162910765000004E-5;
        System.out.println(new BigDecimal(s));
    }

    @Test
    public void switchTest() {
        String s = "123";
        switch (s) {
            case "123":
            case "1":
                System.out.println("----");
                break;
        }

        for (int i = 0; i < 5; i++) {
            if (i == 1) {
                System.out.println("111111");
            }
            System.out.println("000");
        }

        Double[] doubles = {1D, 2D, 3D, 4D};

        String ss = org.apache.commons.lang3.StringUtils.join(doubles, " ");
        System.out.println(ss);

    }

    /************************************
     * description:
     * String[] s1 = {"10", "20", "30", "40", "50"};
     *       给定一个数 问 这个数 离谁最近
     * author:
     * date:
     ************************************/
    @Test
    public void testM() {
        ArrayList<Integer> integers = new ArrayList<>();
        String a = "31";
        a = "90";
        String[] s1 = {"0", "10", "90", "100", "200"};
        int[] diff = new int[s1.length];

        for (int i = 0; i < s1.length; i++) {
            diff[i] = Integer.parseInt(s1[i]) - Integer.parseInt(a);
            ;
            if (diff[i] <= 0) {
                integers.add(diff[i]);
            }
        }

        int i = integers.indexOf(Collections.max(integers));

        System.out.println(i + " " + s1[i]);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("index", i);
        hashMap.put("value", s1[i]);
    }

    /************************************************************************
     * @description: 驼峰转下划线
     * @author:
     * @date: 11:22  2021/9/1
     ************************************************************************/
    @Test
    public void humpToLine() {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        String str = "sectionLayoutHistoryId";
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        System.out.println(sb.toString());

    }

    /************************************************************************
     * @description: 双三元
     * @author:
     * @date: 12:38  2021/9/1
     ************************************************************************/
    @Test
    public void testSan() {
        int a = 1;
        int b = 1;
        int c = 0;
        c = a == 1 ? a == b ? 0 : 2 : 1;

        System.out.println(c);

        String s = "---";
        Integer i = new Integer(1);
        Object d = 2D;
        Object e = 2.12;

        String s1 = s instanceof String ? (String) s : null;
        String s2 = d instanceof String ? (String) d : null;
        Double aDouble = d instanceof Double ? (Double) d : null;
        Double ee = e instanceof Double ? (Double) e : null;
        String s3 = e instanceof String ? ((String) e) : null;

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(aDouble);
        System.out.println(ee);
        System.out.println(s3);
    }

    /************************************************************************
     * @description: atomicReference
     * @author: wg
     * @date: 9:50  2021/9/2
     ************************************************************************/
    @Test
    public void testAtomicReference() {
        AtomicReference<User> userAtomicReference = new AtomicReference<>();

        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            User user = new User();
            user.setName("wg" + i);
        }
        long l1 = System.currentTimeMillis() - l;
        System.out.println(l1);

        long l2 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            userAtomicReference.set(new User());
            userAtomicReference.get().setName("ll" + i);
        }
        long l3 = System.currentTimeMillis() - l2;
        System.out.println(l3);
        System.out.println(userAtomicReference.get().getName());
    }

    /************************************************************************
     * @description: assert
     *  断言 只在 测试的时候有用, catch 捕获不到 断言异常 AssertionError 因为 他是 error 不是 异常
     * @author: wg
     * @date: 10:40  2021/9/2
     ************************************************************************/
    @Test
    public void testAssert() {
        try {
            // double x = Math.abs(-123.45);
            double x = Math.round(-123.45);
            assert x >= 0;
            System.out.println(x);
        } catch (Exception e) {
            System.out.println("------");
            System.out.println(e.getMessage());
        }

    }

    /************************************************************************
     * @description: MultiMap MultiValueMap hashbimap
     * @author: wg
     * @date: 14:58  2021/9/10
     ************************************************************************/
    @Test
    public void testMap() {
        MultiMap multiMap = new MultiValueMap();

        // new org.springframework.util.MultiValueMap<>()

    }

    @Test
    public void testTimeStamp() {
        long t = 1632880972614L;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(t), ZoneId.systemDefault());
        System.out.println(localDateTime);

        long me = 488118920000L;
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(me), ZoneId.systemDefault());
        System.out.println(localDateTime1);
    }

    /************************************************************************
     * @description: 农历
     * @author: wg
     * @date: 16:53  2021/9/29
     ************************************************************************/
    @Test
    public void lunarTest() {
        boolean gregorianLeapYear = CalendarUtil.isGregorianLeapYear(2000);
        System.out.println(gregorianLeapYear);
    }

    @Test
    public void pp() {
        int i = 1;
        int j = i;
        System.out.println(i++);
        System.out.println(i);
        System.out.println("i -> " + i + "  " + (++i) + " i -> " + i);
        for (i = 0; i < 10; ) {
            System.out.println(i);
            System.out.println(++i);
            System.out.println();
            // ++i;
        }
    }

    @Test
    public void test() {
        wg.application.controller.Test test = new wg.application.controller.Test();
        test.forPP();
    }

    @Test
    public void decrypt() {
        String s = CommonEncryption.displacementEncryption("123");
        System.out.println(s);
        String decrypt = CommonEncryption.displacementDecrypt(s, CommonEncryption.getStaticDigit());
        System.out.println(decrypt);
    }

    /************************************************************************
     * @description: gc 垃圾回收 test
     * @author: wg
     * @date: 14:43  2021/10/14
     ************************************************************************/
    @Test
    public void gcTest() {
        GcEntity gcEntity = new GcEntity(new BigDecimal("10.00"));
        new Object();
        new GcEntity(new BigDecimal("11.00"));
        new GcEntity(new BigDecimal("0.00"));
        System.gc();
    }

    /************************************************************************
     * @description:
     * @author: wg
     * @date: 9:58  2021/10/18
     ************************************************************************/
    @Test
    public void TimerTest() {
        TaskTest taskTest = new TaskTest();
        // taskTest.test1();
        taskTest.test2();

    }

    /************************************************************************
     * @description: 泛型
     * @author: wg
     * @date: 16:35  2021/11/5
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void fanxingTest() {
        User user = new User();
        user.setName("iii");

        wg.application.controller.Test test = new wg.application.controller.Test();
        test.testFanXing(User.class, user);

        try {
            Class<? extends User> aClass = user.getClass();
            Field field = aClass.getDeclaredField("name");
            String name1 = field.getName();
            System.out.println(name1);

            Class<?> type = field.getType();
            System.out.println(type);

            field.setAccessible(true);
            Object val = field.get(user);
            System.out.println(val);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////

        Class<? extends User> aClass = user.getClass();
        User cast = aClass.cast(user);
        System.out.println(cast);
        try {
            User instance = aClass.newInstance();
            System.out.println(instance);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Map<Class<?>, Context.Key<?>> kt = new HashMap<Class<?>, Context.Key<?>>();
        Context.Key<?> key = kt.get(aClass);
        System.out.println(key);

        /////////////////////////////////////////////

        test.setter(user, "name", "1111");
        test.setter(user, "createTime", new Date());
        System.out.println(user);

    }

    /************************************************************************
     * @description: 全角 半角 测试
     * @author: wg
     * @date: 11:12  2021/11/12
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testbc() {
        String s = WgUtil.toHalfAngle("a  b");
        System.out.println(s);
        String s1 = WgUtil.toFullAngle("a  b");
        System.out.println(s1);
    }

    /************************************************************************
     * @description:
     * 测试结论: 1. list for (Integer integer : integers)可以 break 和 continue
     * 2. map.forEach 不能 break 和 continue
     * 3. map for (Map.Entry<Integer, Integer> entry : map.entrySet()) 可以 break 和 continue
     * @author: wg
     * @date: 11:25  2021/11/12
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void forTest() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }

        int count = 0, count1 = 0, count2 = 0;
        for (Integer integer : integers) {
            count++;
            if (integer == 2) continue;
            count1++;
            if (integer == 4) break;
            count2++;
        }

        System.out.println("count = " + count);
        System.out.println("count1 = " + count1);
        System.out.println("count2 = " + count2);

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i);
        }

        // map.forEach((k,v)->{
        //     if (k==2) {
        //         continue;
        //     }
        // });

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getKey() == 2) {
                continue;
            }
        }
    }

    /************************************************************************
     * @description: PropertyUtils PropertyDescriptor
     * @author: wg
     * @date: 17:57  2021/11/17
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void test100() {

        WgUtil.test1(Test03.class);
    }

    @Test
    public void testFile() {
        File file = new File("D:\\java-project\\pipeline-integrity-assessment-system\\report-cad26ac70de64a7484d8d71a008c85b6.md");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
    }

    /************************************************************************
     * @description: boolean
     * 测试结果 :
     *  if 条件里的 boolean 不写成 等式形式的话, 就 必须 为 true
     *  即: if (flag) -> if (flag == true)
     *   if (!flag) -> if (flag == false)
     * @author: wg
     * @date: 18:02  2021/11/25
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testBoolean() {
        boolean d = false;

        if (d) {
            System.out.println("d = true");
        }

        if (d == false) {
            System.out.println("d = false ");
        }

        if (!d) {
            System.out.println("d = false ");
        }

        Boolean flag = false;
        if (flag) {
            System.out.println("flag = true");
        }

        if (flag == false) {
            System.out.println("flag = false ");
        }

        if (!flag) {
            System.out.println("flag = false ");
        }
    }

    @Test
    public void test5() {
        Object o = WgUtil.initEntity(User.class);

    }

}
