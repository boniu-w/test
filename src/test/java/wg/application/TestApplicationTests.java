package wg.application;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.WgJsonUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        WgJsonUtil.jsonDataToFile2(path, jsonString);

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

    @Test
    public void instanceTest() {
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
        for (int j = 0; j < useridList.size(); j++) {
            for (int i = 0; i < list.size(); i++) {
                String[] playId = list.get(i).split(",");

                for (int k = 0; k < playId.length; k++) {
                    if (playId[k].equals(useridList.get(j))) {
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
            hashMap.put(useridList.get(j), count);
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

        int i = 1 / 3;
        System.out.println(i);

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

    @Test
    public void layoutTest() {
        String[] arr = {"展昭", "小黑", "二哈", "张无忌", "张三丰", "123", "小323", "二23哈", "张232无忌", "2323三丰"};
        String tr = "";
        for (int i = 0; i < arr.length; i++) {
            tr += " " + arr[i];
            if ((i + 1) % 3 == 0) {
                System.err.println(tr.replaceFirst(",", ""));
                tr = "";
            }
        }
        System.err.println(tr != null ? tr.replaceFirst(",", "") : "");
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
     * @param input 任意字符串
     * @return 半角字符串
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

        System.out.println(s.replace(","," "));
        System.out.println(s.replace(",",""));


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

        System.out.println(me.replace(" ",""));
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

        int a=0;
        String as="0";
        System.out.println(as.equals(a));

    }


}
