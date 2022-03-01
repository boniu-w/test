package wg.application.leeCode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/8/6 14:05
 * @version
 * @Copyright
 * @description
 * 给定一组 互不相同 的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
 * 输入：["abcd","dcba","lls","s","sssll"]
 * 输出：[[0,1],[1,0],[3,2],[2,4]]
 * 解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
 *************************************************************/
@Controller
@RequestMapping(value = "/palindromePairsSolution")
public class PalindromePairsSolution {

    private TrieNode root;

    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    /****************************************************************
     * 3毫秒
     * @author: wg
     * @time: 2020/9/7 16:30
     ****************************************************************/
    @RequestMapping(value = "/palindromePairs")
    @ResponseBody
    public List<List<Integer>> palindromePairs() {
        String[] words = new String[]{"ag", "dadbcah", "heeeahacdahj", "feg", "dadficbhih", "ihdggfeefh", "fbedcahdfeab", "djaacjcfj", "haijah", "hbaebhd", "d", "cbedeei", "gc", "jddgeccbdhgdiccfdigb", "edgchajgjbhch", "djdebb", "b", "egiegejggbgi", "acdjabiddcjce", "dggeeffebiehdbgg", "dbdefhgaejaghehcb", "cjhgbgjecdfbddccjehf", "bdgjage", "ggfeggg", "hajc", "bjdgjhdedefifdihg", "afhgdhacdjfa", "iigadadg", "gjadachfdiaejijdfdgd", "fiaeajiaebahfjdagc", "iagjfdfcghehfjia", "afd", "bgfgcajfhddajiceeaaf", "iefcfgfdjecg", "ebceadaaigdbc", "igbcdbdfdjegff", "jfabjebjge", "hedchddj", "eehgggbifbfbg", "gjefdjfahib", "hadgj", "bfde", "dg", "bidedjffcdegh", "gejbefeaiieidha", "ajgcejcfgidafghdejdj", "aafbcdh", "hicif", "fgidijdfbgjfacjcf", "iciajjdiggejedgd", "jjdfbedjifj", "hhfbfh", "bibbefhjbbbjjejjgi", "egeigdhfbgfehfbhgbga", "fiiddfegdahbajcaba", "dfjfjebhdj", "jg", "hafahdbgijfjhafc", "djafgehi", "agbd", "fgchciaeeidfjagefahe", "jhifccfhbgechaefjbdb", "bahfcecjjgejbibajd", "idgdgcidigbedbafa", "fddejfjddeeiga", "jijhjcci", "fbdedaciahjcicei", "aababeiaeh", "eideccaddgcicifd", "ea", "faehegabdgadc", "fihajceafdd", "cagbehagajc", "cgje", "ighdcefhe", "cbbiaigci", "ggfejebhbhfdbifb", "bfabjjfjcegdbjebcjid", "bcdgehfjdfdb", "hdcihijbahfjcjdfb", "jfjfhgjdjbgihgabjhbh", "bfaaebjdj", "acgbcjebedghfi", "fdcehcgbgdchifjaahd", "eiajbjfgec", "eiigjidjjd", "gbie", "ihccaajfdjdjidceebi", "eceegacbgigd", "fff", "bdjgfgggccgfbfbd", "gchiafbbggdccgc", "aefehccgcaccgd", "cebhgac", "jegcfegj", "aggcdjic", "egaejbejefch", "ibfge", "ejjhg", "ijfhaibfhhd", "chag", "j", "di", "gieicbfai", "hhbjaeeachdiijh", "jicajhfhdjhbfhjicch", "gbfjabfe", "babddicdhdiigfjgcga", "ihbgjfbgjh", "ijbjbhgchhcdacgeig", "bdbhigbfbebcah", "hghgdich", "hdd", "aebjeh", "cchadbg", "gjdjjhihjg", "cidibijif", "cehefdedgdjdafg", "hchca", "ffdfbidfhchhe", "edgjhcdib", "cedcdgjdcggdajcbejib", "gbjjjaiijjiijii", "agdhfbgehfcidadd", "acgfaacfgehhihibcbb", "jfebeihiacgjggicgij", "cbgadaiffjcj", "abi", "dbfehfffcaa", "gdbeddaccffd", "he", "jdcfjjdceaf", "bhabdahe", "fiehffebffd", "dhdgcechbdcbfj", "efbiiaeahifda", "jgjhijeegajd", "dh", "jigeijbcddhf", "e", "fahcehhhjhf", "iffechadhjbbjgfgi", "fccbgdeffjjhceh", "jiefceggii", "hhjhijiijihh", "fibeb", "cb", "dgjcfdfbjjjg", "ghcidcgjiicaab", "cjhc", "abh", "hccajge", "hagbjcjhhgahadabffj", "ehccgaficdiajib", "jiajgf", "ch", "fcehajcfafceafbdjea", "adjdhcdjibh", "decjbjecg", "baeidjbbajjffiabgcjb", "bjibfiij", "edfa", "jjiba", "gcjijifd", "edejihjahbjf", "ehfdjfeebjgij", "ecgbcebaacgiifgj", "djgchbcjcfabfii", "bbgahefbjiihfciifdc", "edjhie", "iaijicfdhdhiigiech", "jeaiacicajdacabggghj", "ffidgghe", "fi", "igcdigbcbhgfac", "igdbhfhbheceagfhifec", "fbgbabaadjfg", "hejjjahed", "fhfehgfibb", "dcjecbccggeheeaddfh", "dcgcjffagajfhb", "gfj", "aca", "aegfifc", "dgjbeijdhjagcfejeae", "g", "cfhhdfcde", "gb", "ijjaij", "fcfdahjaadgf", "cgddfddjcigc", "ccjibdabg", "ffg", "bhgccdjaheiffge", "dagdiahhe", "ibefjebjgijggfbcd", "gijcijaiifeddhjafg", "ei", "gbeiga", "fbc", "cffeigheehaecfefaf", "hbfb", "ebhehcaaafdgghacdcc", "gfbjchdagjcdhiffeg", "afciafahcggbfghabjcc", "jdbgjb", "gefaa", "gfeifhdhaia", "c", "chjbfcde", "ffbdjbda", "hjcafagejecjbdeghbfi", "hfejebjaffbi", "f", "bdjhddji", "feafhche", "jggjhibddejigjehah", "caeddaace", "caceb", "acajhdebh", "ggeadaa", "dbeif", "ihdifhijgcjbdijia", "fibjfeecdchj", "febihiaciahbii", "gjcafcbgfejdcidag", "bidgh", "ajcacfhcjedjegi", "ejfaghcebhhhicgcjfe", "ccfbdafigibdeggg", "dghd", "efhfbfajea", "ggfegichia", "igccjieidbebcccicid", "ggcb", "gdbbdjdi", "h", "ebdeegcddc", "fieebfbeeigcjcb", "ijfdeibijbjeaa", "jahahgcfidb", "ejhdhjagi", "jcfiddhjdcggeeedjjg", "cgdcecffhgiceceafbgj", "icgiaehcejabefbg", "eiechjcjajdjjjcbhi", "eefjcjggbjda", "fihbiaafghbjacdggej", "gaheadcdfefef", "fcdjgggaagaccfhj", "jbhbj", "dbeadfjdgdhc", "gdjcic", "cifjdcfeedab", "jacdggchfgfjf", "edggieccfgfebicdh", "ghfajadffjeicieg", "fgdhgffgb", "jicdgggahdhdhbi", "bbgg", "fhdhfjigbbjfejdhcc", "haieafgdaabhiiifc", "aajiagjhhhgeagehcdef", "hgdafaehjiifdif", "adebdcb", "gecbgigc", "dfdjjdccjdifggidhbbh", "ji", "acdhabebefdabdig", "jjfhccbb", "ieifihffcjgedegcie", "efdhhghb", "jic", "iibjbccgi", "ffffajgbbcgiaabdja", "efifj", "jfhghfcjccgdjhhaijf", "ejbcfhgigge", "jbhibci", "igiejg", "fhaj", "hccfjiihdheihfgadjh", "eeb", "cahijghj", "cdigbcbedcfjb", "ciegfgdgeejcij", "i", "bjhjcbddggie", "cdgjjdecbehb", "ccgdcdbfgeccegaeacbb", "ieejeb", "iecdcbd", "chii", "fcbgfaibffjabjg", "iidegjdigaiebeddjdh", "gachcjabh", "fijjebdefdhfgcjccbei", "jediheaif", "cdfihjgeegfdg", "bdj", "cebfejajgbg", "ghcbdfieiigcdh", "cfbjbg", "ebfhfeigeghfi", "chjihe", "abeagebeffdifjg", "hcebjgaffeehgfb", "idhgjbcfjfbaj", "hijbgbiebbcfgihbf", "ijib", "dcedeeidj", "fbdjj", "jciijbdjgbiahiefb", "gfiaddehdgagba", "jhbebgj", "hfcbcejabbjbce", "icdcccbddcjigdhbdfi", "fdfbjcdifjdebdifff", "fhcidafb", "iccjhchahhheejcda", "adfcbdgdaaeefbab", "jbcfbg", "bjdaicdjbjcgbcbbcbh", "dice", "fhhicbdeeahaibfhjf", "ccdeabbfebjihbhgfag", "bheaccdgfbjfjcjei", "jjfiafba", "cadcdeegbbcchii", "ffdegjgjjadhe", "ii", "dcbhdaaeibdieijbcab", "egbeegdffdcjjeegg", "dcbihcfhigif", "ijghbbhhfbbcdb", "daajgfahdiifjhaifaif", "ghdgjbfdhbafdab", "iabfaejjfaie", "bbbci", "gfghfidach", "fccafhidc", "ebfbefg", "jidbhidij", "ihba", "jciciehiffgjhijjjedc", "gdacdjebffjeeie", "hibdech", "ci", "dgce", "bfdjdiffdgjdj", "ffbajddhdi", "aae", "ffbigadaaa", "gfgdfcaiadeaagbafb", "caghbfhjddfeaedd", "acfcebbjfhdjjde", "idgdjbbe", "dhc", "cegg", "aebcihdicbaggejef", "djifcbbffeidhb", "ieadcadcgjjdcbejbcae", "baheaichgigfgfece", "dhbfejcihgchii", "badbbaceijdehe", "ajghfjadicijabe", "hfjifghcjdaifgcaej", "ecceebeaigadj", "bcbbbajigd", "gjahfabebdd", "ig", "gjjefgajggidgjbg", "igcdhddegcccbiagjc", "bibjddacghi", "hegbiahdjgegdibc", "dhchfcgcdhafejb", "ffbfijceaf", "fehd", "fedadadbddahdeaecj", "jdfdgddfihbcidh", "gig", "haceghcdcebbbfccdi", "jaagbiieibebehb", "eegjhebdd", "a", "degecadgiedecib", "hfaejeeieaijabijb", "hgciaef", "fjehbde", "cehedadfbhijh", "ff", "afef", "cgfjhicbiafhcac", "iebha", "djgagdigbbegebidj", "aajdcdhaihcac", "hfecbjhhbgfa", "jegifhi", "jchgadbjghaifi", "eccabiagg", "aifccc", "dhefcihcaf", "df", "adjidjidejedbbg", "ffgicfidjgcbbcf", "ggbicjaigbccfef", "gccibedgfhbihbeajibj", "daaehih", "bh", "jagiajijeic", "jeacebgcj", "iccff", "jjeab", "gjabifjhh", "dahjfahacadajgiig", "jfgihdah", "ggfbfhdhjcbdhecj", "bfeehccbgf", "iaeigffd", "ded", "djgjcaejei", "habibida", "hfhhafaj", "ghbheddcaagbjefg", "geidbcihihihfi", "jhjfjjifbdfaiaecjggi", "acjd", "ahbcjgddicj", "ecfeebhfce", "ajbbaegad", "dhahcfjdaaeajahgacfh", "jdhjhbiciechbghdcc", "ifabaacdfjied", "gdjdfcadfb", "ecjebjhg", "cbhebajfaaaegh", "hjagd", "ga", "cfhdibcgfdeehhgjjiaa", "jhegjbeidhaff", "afgcjfebgjjdaebh", "ifgjihbgecjjicif", "ccbiefbjgdbefbaefad", "db", "jcdicigebheeieiafb", "cjfhbbhgdbahdeja", "afgbbdhed", "cbegb", "chhidjf", "ibdgbedcfagbjd", "cabfeicafced", "aa", "behj", "cdghdhcifih", "cgjfjbfdbjbijcffdf", "ahfefejdfegec", "fjbgdicifb", "ggejjgafhegibg", "fhaghihbfjgecah", "ifejbjfecighefb", "ijci", "dhihdjjjcjdhjabjfei", "abdedeigfb", "gdjcefcibjeebdeb", "eccdcbeagb", "hgebcjcfcbcccbhid", "eecacechbcecajj", "fdjbdhhidbgcehigjfc", "jeaihefgejjgjigi", "dhcje", "accccdigcdddceca", "djjbcdccj", "hjcgaibfjhjiaf", "dhajjihdeff", "hejebj", "gehifc", "jhcjabgibcaagecfjdfb", "gdgfjcfc", "egdh", "jgej", "did", "ehjbaifiaa", "ceihedadgbhahhe", "edcegedhh", "ihedc", "jcaeaicgfjj", "jcgfcecbgcag", "iiceeihedhfgbj", "aajejefghheg", "fcjee", "fdbb", "fcjfijhagjiecfa", "jfbfedgagfedheb", "hhiagi", "jfgjadjiihjjefchha", "cegida", "jgegghhb", "hbjbhhfjchfi", "adbfcijdjbidg", "icf", "hjcgge", "hieedgaaaffg", "ajgageheebid", "jdbebdc", "aeaagf", "dagccfcgeiid", "hg", "afceebiafjceadc", "cfhaagebfc", "fihdiff", "jcajjhd", "ddbdfjjdc", "jbecijhdfbhcfjab", "fggfgbafhjejd", "hijadacifgdchegh", "jid", "adjhf", "gadfcija", "bdeidi", "jfccehfhbecchgccg", "gbbdbb", "jidjhbdhcccdbdhcbbf", "bgheejgddeejejej", "hbhefdj", "jacgi", "fifjbfciiejbc", "jieii", "bafgacjifhighf", "jcbhiaigcai", "bhjeh", "ghigdbbjcgiijjfcb", "ijgffdheiaefebhgh", "ghijeidgjggbb", "hcfdacebgjgjf", "cdfe", "iehchadfeiada", "dbg", "ihjceafefh", "fahehgbcejjfc", "gdeaefgjgjedfdgha", "jeh", "ifd", "jig", "cgjhifgg", "cddgibcdiiaecfiihgfe", "cfjhibbccije", "deeiafdie", "bdedifdjghjjccegg", "gcgfaejadida", "idjeehhe", "fadehe", "abjjdddhfdfj", "bicef", "adcgdeagaijicghijhi", "aggiididcbi", "igfbibbjgh", "bfjccccfjicgdbdgah", "fcaciedhcbb", "cjaiaehd", "faic", "hiaef", "bjjhfjgdiidcbfbf", "jacjheceg", "djeejiehgh", "bdjag", "bfcfjigbjfcccbihj", "jefdef", "bjgcbahf", "bbjidcbfgafhifheihc", "hcajfcd", "ifjhahbdahgacgdejg", "cfjehhijfcdfeb", "agffcfacib", "jfagbaifeabbgjaaag", "ieicegjjebagdjic", "ghhjgcc", "hjiccgcjgcace", "gbehefggiebj", "bdhdghjicfaadhbhjgf", "cefdcdibdjaja", "aegib", "cgfiiifaeaiehijhja", "fhfibahdhifbbag", "ciceaghdhcaaicdfh", "egehic", "ccfjddhedc", "fjhgfiicgbjbhaf", "fhacidf", "fdgdcbjccbdc", "chb", "ibbajefiajdedbg", "dc", "cefefafffeef", "cjfiehfciejeihfc", "dieaihbfeebihhfb", "fgdfeiifi", "dggjbihci", "eidchjdecfe", "fjceb", "ibdccb", "cfdhgifb", "jchiccchaebdeajbjdg", "fbgdgjcj", "cdiechfgiabagbcgfegd", "egceicbhddaif", "jhbjghbg", "aebfa", "ihibjcehbcgdg", "fiih", "iiidebhaajgfegc", "bebhgfeegahibbe", "bbdcdggfihhihhb", "deehhgjh", "gdhchigfgf", "cchicidi", "gbddcgcfafeie", "didaicgbfcgdbha", "jbigcjegeghiahd", "ja", "ejbaijgbeedf", "gjdghidedbijhj", "efabghiedfgahggdhffa", "dcfjfffebhddhdajghhe", "jdbccjje", "hhehcahigjafjj", "ejbdahecgb", "agbcjihfaafddfbbjj", "cjjdjbceih", "cjgcihghijebadeh", "idfefdb", "icfejcfdifca", "jghdccegfiibajedcb", "eahh", "ijg", "hbgcceg", "echdgbbagagccbhjehdg", "aahajadjchbdd", "ghjdbfafbeeaicjija", "ijfgijifjcbjefdidga", "bcffbagdgecjbib", "eegbacihcfffgcac", "bhbhehahgg", "gichfjieafdg", "hjjfiecfdcicedcedg", "edccg", "gbb", "ifagie", "ffbbbbibdef", "agfgfahicjajhbibbccb", "jfabejcegjifdaaegcfi", "gjajdihcigeigfieaa", "bjgfifaaedfhabbgcjhd", "cgeebjdfibci", "gdijjecddhfihdcad", "jfbajjjdebdfdjegg", "iicegfidfaahdgg", "eaabjfejdbfhhgga", "ccjj", "fecbechbjgf", "cehbigaifjggb", "efcfaa", "idcjca", "heifjaffdagihjdhhje", "hhhg", "bcceabajhibcgge", "cficadfjafehcbfbe", "ddgac", "fgcbhchccgidhibjd", "cfejacedhcbfbbdei", "aadicbdgdeag", "hbiidhd", "jdhejfddigaig", "dijjfhdehhgi", "jbhbh", "decf", "abhdfbdjdgaigdbcdac", "eadbghibfchhegjegaa", "iajeahcdihdahjchejcj", "aehbjdajff", "efejhaihchjjjdaj", "afaabaedac", "jgecfabhfhacegcce", "jbacedhij", "jfhfeaigbehfeddgj", "aibiebfjbbjihdafe", "daefghfdghhdha", "cgdgbiaijcchcdbhdg", "fhhej", "ihahff", "hdiafcdg", "bhgeigbdcjiaidfg", "ebfcgbicei", "jigfgfbif", "ecfgic", "be", "jbfedbj", "hfgeadbdffb", "cdadbhfggbdcfg", "gfbibhfid", "dggjhibedefcidcidh", "djgdebibbeihdb", "idjead", "jddfbhfiigihi", "dahhejdh", "aigai", "gihjfjhbjga", "dcdhhchgcifij", "hbdbffehccggcahgdij", "aicgf", "bjdbgjjbihjfjjjei", "cghdceieadgb", "feeabeajdia", "chgdgdidhac", "bi", "jecedadaiia", "ifdhddbhegbhgbbhb", "dahhdbaahgdadfdjegh", "gbgajagegifg", "ibeageaagibci", "ajj", "adabbjadgaibibadbh", "cjgghfbfhafddahjgfdg", "iffah", "dcbifaaijhc", "bd", "ffghbj", "afg", "fadgedcjiedfjedaehf", "edagghhggcbdbegeae", "afcfibhcbbjbdeg", "aaggi", "aijcijjfjhb", "jiejbjdiideibae", "bggagfdejjaigeggagd", "adfaecebifgdgdi", "ejecgjhjeiiiagjchei", "efhifgfddhhhfcdfhcai", "djja", "ehdfdidgahgafi", "ejdbhd", "ageeediidj", "gjeachdhcibc", "fddbaaagfacbjhch", "ide", "echagfgciaadhe", "fheegadifechfad", "ehadcaefjbifdfjjg", "hchajigibaaei", "didbgajbggiadeeha", "gab", "djjbafiai", "dhajgdacchjefd", "ce", "caihabbfdcadhedigji", "ahbgjhheijjdhjhe", "ajaahaadcbgdaab", "fghed", "cijggfih", "ifdbfegfibeeii", "gcjbdjbbichcbjigadh", "ibgjheegabjgdj", "fidhdhjjedfecfbdid", "dcbeeadg", "ihja", "iedhgehbgjhjfibhg", "bdiefchcfcebff", "ifjdddeaj", "hbfiigihhbd", "haifch", "cijiee", "agahdjjfbffhajjh", "jehabbgihejcdbg", "gbcibgibcbb", "hbdiciccjbcdhgie", "jfbdg", "iedejciga", "hfhcba", "aicidjdabdaaaeb", "ageeabaaeejdad", "ecdb", "egejcicbdhc", "cfiafhjbbi", "dfcifebiei", "djig", "dgbbhfedcbccafd", "eahjbefdjgeggifdiahc", "jbiefcfdcfdjfjef", "if", "jhfdhgeidei", "jcehfafffieheajchccf", "cgghi", "cghfffhceihdcaihic", "ibaiajgcbig", "hedeaddfbahhaaccfef", "abfecegeciggaad", "ceiafhibhefgafjjeb", "ggcgajfdacgbh", "dgjj", "abiceabf", "dhhbhhdhhgifjacaae", "fajchbgfbicfad", "bagjjageadffgafhjf", "bgcfjjeefchhjih", "iibjigdddi", "bda", "fdg", "iidjbdfded", "cidcbjh", "fbeibcjjddbcch", "bgiif", "jaijdebhffe", "biiiegd", "jgjjhe", "gcdidbbfddjeabfad", "jegibaaab", "hiidegaa", "hdccdcdfhcaibiajdecd", "aihjgehhcdfacf", "fbeggaihfcijgdjjf", "beef", "jjjgjjf", "fgidffifc", "hciacchdbggdjcgbibfe", "fbcfjgbeddaeadhh", "jahejfffiehijhjaedjd", "efjggaaj", "hcjjbjjgagjegedgf", "ddhbhjeedcbfiejigd", "dffddbgbefdeibdigfjf", "feijgcj", "bggadgiehgbhhgefd", "addbeiggicjcbidc", "fdjjcdheaacjgdae", "jjbh", "ggddgdcbagjbdbjag", "acchhgbdfih", "bge", "jjfbigbgd", "baggfhdeeaibeigjfgag", "bjjcaihicjecahh", "eibg", "fffhfijeiffeafgg", "afjeeddgciab", "djafb", "biegdfidfjgbecc", "efhehhbgjg", "ejfbjjeabjf", "ddibajeejdbfda", "cggdfcdijjig", "cea"};
        this.root = new TrieNode();
        int n = words.length;

        long l = System.currentTimeMillis();

        //build TrieNode Tree
        for (int i = 0; i < n; i++) {
            String word = new StringBuilder(words[i]).reverse().toString();
            TrieNode cur = root;

            if (isPalindrome(word.substring(0))) {
                cur.suffixs.add(i);
            }
            for (int j = 0; j < word.length(); j++) {
                int index = word.charAt(j) - 'a';
                if (cur.children[index] == null) {
                    cur.children[index] = new TrieNode();
                }

                cur = cur.children[index];
                if (isPalindrome(word.substring(j + 1))) {
                    cur.suffixs.add(i);
                }
            }
            cur.index = i;
        }

        //search
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String word = words[i];
            TrieNode cur = root;

            int j = 0;
            for (; j < word.length(); j++) {
                if (isPalindrome(word.substring(j)) && cur.index != -1) {
                    res.add(Arrays.asList(i, cur.index));
                }

                int index = word.charAt(j) - 'a';
                if (cur.children[index] == null)
                    break;
                cur = cur.children[index];
            }

            if (j == word.length()) {
                for (int k : cur.suffixs) {
                    if (k != i) {
                        res.add(Arrays.asList(i, k));
                    }
                }
            }
        }

        long l1 = System.currentTimeMillis();

        System.out.println(l1 - l);
        System.out.println("res.size()  -> " + res.size());
        return res;
    }
}

class TrieNode {
    public TrieNode[] children;
    public int index;
    public List<Integer> suffixs;

    public TrieNode() {
        this.children = new TrieNode[26];
        this.index = -1;
        this.suffixs = new ArrayList<>();
    }
}